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
        if(latitude==0&&longitude==0) throw new Exception("Please add animal location");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getAge() {
        return age;
    }
    public Date getDate() {
        return date;
    }
    public String getPhotoB64() {
        return photoB64;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", \nphotoB64='" + photoB64 + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
