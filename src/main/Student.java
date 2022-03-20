package main;

public class Student {

    private int index;
    private double grade;
    private String name;
    private String surname;

    public Student(int index, String name, String surname, double grade) {
        this.index = index;
        this.name = name;
        this.surname = surname;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return index + "\t" + surname + "\t" + name + "\t" + grade;
    }

    public double getGrade() {
        return grade;
    }

    public int getIndex() {
        return index;
    }
}
