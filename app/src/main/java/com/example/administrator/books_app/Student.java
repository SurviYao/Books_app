package com.example.administrator.books_app;

public class Student {
    private int id;
    private String number;
    private String name;
    private String psd;
    private String phone;

    public Student(int id, String number, String name, String psd, String phone) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.psd = psd;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
