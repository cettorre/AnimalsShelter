package com.example.cettorre.animalsshelter.domain;

import java.util.Date;

public class Animal {

    private  String name;
    private String type;
    private  int age;
    private Date date;
    private String photoB64;
    private float latitude;
    private float longitude;

    public Animal(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Animal(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public Animal(String name, int age, String type, Date date) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.date = date;
    }

    public Animal(String name, int age, String type, Date date, String photoB64) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.date = date;
        this.photoB64 = photoB64;
    }

    public Animal(String name,String type,int age, Date date, String photoB64, float latitude, float longitude) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.date = date;
        this.photoB64 = photoB64;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhotoB64() {
        return photoB64;
    }

    public void setPhotoB64(String photoB64) {
        this.photoB64 = photoB64;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
