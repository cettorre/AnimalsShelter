package com.example.cettorre.animalsshelter.domain;

import java.util.Date;

public class Animal {

    private  String name;
    private String type;
    private  int age;
    private Date date;
    private boolean hasChip;
    private String photoB64;
    private double latitude;
    private double longitude;

    public boolean isHasChip() {
        return hasChip;
    }

    public void setHasChip(boolean hasChip) {
        this.hasChip = hasChip;
    }

    public Animal(String name,String type,int age, boolean hasChip, Date date, String photoB64, double latitude, double longitude) throws Exception {
        if(name.length()<2)throw new Exception("Animal name cannot be < than 2");
        this.name = name;
        if(type.length()<2)throw new Exception("Animal type cannot be < than 2");
        this.type = type;
        if(age<0)throw new Exception("Animal name cannot be negative");
        this.age = age;
        this.hasChip=hasChip;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", photoB64='" + photoB64 + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
