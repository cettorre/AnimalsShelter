package com.example.cettorre.animalsshelter.application.dto;

import com.example.cettorre.animalsshelter.domain.Animal;

import java.util.Date;

public class AnimalDTO {
    private  String name;
    private String type;
    private  int age;
    private boolean hasChip;
    private Date date;
    private String photoB64;
    private double latitude;
    private double longitude;

    public AnimalDTO(Animal animal) {
        this.name = animal.getName();
        this.type = animal.getType();
        this.age = animal.getAge();
        this.date = animal.getDate();
        this.photoB64 = animal.getPhotoB64();
        this.latitude = animal.getLatitude();
        this.longitude = animal.getLongitude();
        this.hasChip=animal.isHasChip();
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

    public boolean isHasChip() {
        return hasChip;
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
        return "\nAnimalDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", \nlatitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
