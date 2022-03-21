package main;

public class Main {

    public static void main(String[] args) {

        TwoWayLinkedList<Student> list1 = new TwoWayLinkedList<>();
        TwoWayLinkedList<Student> list2 = new TwoWayLinkedList<>();

        Student s1 = new Student(1, "Jakub", "Seredynski", 4.0);
        Student s2 = new Student(2, "Piotr", "Krawiec", 4.0);
        Student s3 = new Student(3, "Jakub", "Prawy", 4.0);
        Student s4 = new Student(4, "Hubert", "Mily", 3.0);
        Student s5 = new Student(5, "Grzegorz", "Pewny", 4.0);
        Student s6 = new Student(6, "Jakub", "Lubek", 5.0);
        Student s7 = new Student(7, "Maciej", "Lewy", 5.0);
        Student s8 = new Student(8, "Jakub", "Lewy", 5.0);

        list1.add(s1);
        list2.add(s2);
        list1.add(s3);
        list2.add(s4);
        list1.add(s5);
        list2.add(s6);
        list1.add(s7);
        list2.add(s8);

        System.out.println(list1);
        System.out.println(list2);
        list1.insertListAtElement(s5, list2);
        System.out.println(list1);
    }
}
