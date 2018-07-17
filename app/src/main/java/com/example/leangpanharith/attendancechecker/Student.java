package com.example.leangpanharith.attendancechecker;


public class Student {

    public int id;
    public String name;
    public String qr;
    public Boolean isFemale;


    public Student (int id, String name, String qr, Boolean isFemale){
        this.id = id;
        this.name = name;
        this.qr = qr;
        this.isFemale = isFemale;
    }

    public Student(int id) {
        this.id = id;
    }

}
