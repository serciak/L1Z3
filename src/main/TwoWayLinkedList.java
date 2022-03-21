package main;

import java.util.Iterator;
import java.util.ListIterator;

public class TwoWayLinkedList<E> {

    private class Element {
        private E value; 
        private Element next;
        private Element prev;

        @Override
        public String toString() {
            return value.toString();
        }

        public E getValue() {
            return value;
        }
        public void setValue(E value) {
            this.value = value;
        }
        public Element getNext() {
            return next;
        }
        public void setNext(Element next) {
            this.next = next;
        }
        public Element getPrev() {
            return prev;
        }
        public void setPrev(Element prev) {
            this.prev = prev;
        }
        Element(E data){
            this.value=data;
        }
        public void insertAfter(Element elem){
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }
        public void insertBefore(Element elem){
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }
        public void remove(){
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }

    Element sentinel;

    public TwoWayLinkedList() {
        sentinel=new Element(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");

        if(!isEmpty()) {
            Element current = sentinel.getNext();
            while(current != sentinel) {
                stringBuilder.append(current);
                current = current.getNext();
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private Element getElement(int index){
        if(index<0)
            throw new IndexOutOfBoundsException();
        Element elem=sentinel.getNext();
        int counter=0;
        while(elem!=sentinel && counter<index) {
            counter++;
            elem=elem.getNext();
        }
        if(elem==sentinel)
            throw new IndexOutOfBoundsException();
        return elem;
    }

    private Element getElement(E value){
        Element elem=sentinel.getNext();

        while(elem!=sentinel && !value.equals(elem.getValue())) {
            elem=elem.getNext();
        }
        if(elem==sentinel)
            return null;
        return elem;
    }

    public boolean isEmpty() {
        return sentinel.getNext()==sentinel;
    }

    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    public boolean contains(E value) {
        return indexOf(value)!=-1;
    }
    public E get(int index) {
        Element elem=getElement(index); return elem.getValue();
    }
    public E set(int index, E value) {
        Element elem=getElement(index);
        E retValue=elem.getValue();
        elem.setValue(value);
        return retValue;

    }

    public boolean add(E value) {
        Element newElem=new Element(value);
        sentinel.insertBefore(newElem);
        return true;
    }

    public boolean add(int index, E value) {
        Element newElem=new Element(value);

        if(index==0) {
            sentinel.insertAfter(newElem);
        } else {
        Element elem=getElement(index-1);
        elem.insertAfter(newElem);
        }
        return true;
    }

    public int indexOf(E value) {
        Element elem=sentinel.getNext();
        int counter=0;
        while(elem!=sentinel && !elem.getValue().equals(value)) {
            counter++;
            elem=elem.getNext();
        }

        if(elem==sentinel) {
            return -1;
        }
        return counter;
    }

    public E remove(int index) {
        Element elem=getElement(index);
        elem.remove();
        return elem.getValue();
    }

    public boolean remove(E value) {
        Element elem=getElement(value);
        if(elem==null)
            return false;
        elem.remove();
        return true;
    }

    public int size() {
        Element elem=sentinel.getNext();
        int counter=0;
        while(elem!=sentinel){
            counter++;
            elem=elem.getNext();
        }
        return counter;
    }

    public Iterator<E> iterator() { return new TWIterator();}

    private class TWIterator implements Iterator<E>{
        Element _current = sentinel;

        public boolean hasNext() {
            return _current.getNext() != sentinel;
        }
        public E next() {
            _current=_current.getNext(); return _current.getValue();
        }
    }

    public ListIterator<E> listIterator() {
        return new TWCListIterator();
    }

    private class TWCListIterator implements ListIterator<E> {
        boolean wasNext = false;
        boolean wasPrevious = false;

        Element _current = sentinel;

        public boolean hasNext() {
            return _current.getNext() != sentinel;
        }

        public boolean hasPrevious() {
            return _current != sentinel;
        }

        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        public E next() {
            wasNext=true;
            wasPrevious=false;
            _current=_current.getNext();
            return _current.getValue();
        }

        public E previous() {
            wasNext=false;
            wasPrevious=true;
            E retValue=_current.getValue();
            _current=_current.getPrev();
            return retValue;
        }

        public void remove() {
            if(wasNext) {
                Element curr=_current.getPrev();
                _current.remove();
                _current=curr;
                wasNext=false;
            }
            if(wasPrevious) {
                _current.getNext().remove();
                wasPrevious=false;
            }
        }

        public void add(E data) {
            Element newElem=new Element(data);
            _current.insertAfter(newElem);
            _current=_current.getNext();
        }

        public void set(E data) {
            if(wasNext){
                _current.setValue(data);
                wasNext=false;
            }
            if(wasPrevious) {
                _current.getNext().setValue(data);
                wasNext=false;
            }
        }
    }

    public void insertListAtIndex(int index, TwoWayLinkedList<E> list) {
        if(size() - 1 >= index) {
            Iterator<E> iterator = list.iterator();
            E toAdd = iterator.next();

            while(!list.isEmpty()) {
                add(index, toAdd);
                list.remove(toAdd);
                toAdd = iterator.next();
                index++;
            }
        }
    }

    public void insertListAtElement(E elem, TwoWayLinkedList<E> list) {
        if(contains(elem)) {
            Iterator<E> iterator = list.iterator();
            E toAdd = iterator.next();
            int index = indexOf(elem);

            while(!list.isEmpty()) {
                add(index, toAdd);
                list.remove(toAdd);
                toAdd = iterator.next();
                index++;
            }
        }
    }

    public void insertList(TwoWayLinkedList<E> list) {
        Iterator<E> iterator = list.iterator();
        E toAdd = iterator.next();

        while(!list.isEmpty()) {
            add(toAdd);
            list.remove(toAdd);
            toAdd = iterator.next();
        }
    }
}
