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

    @Override
    public String toString() {
        return "AnimalDTO{" +
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
