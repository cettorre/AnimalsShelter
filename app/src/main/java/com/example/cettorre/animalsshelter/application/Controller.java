package com.example.cettorre.animalsshelter.application;

import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import com.example.cettorre.animalsshelter.domain.Animal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
 private static List<Animal> animalList=new ArrayList<>();

    public void addAnimal(
         String name,
         String type,
         int age,
         Date date,
         String photoB64,
         float latitude,
         float longitude){
     Animal animal=new Animal(name,type,age,date,photoB64,latitude,longitude);
     animalList.add(animal);
    }

    public List<Animal> getAnimalListDTO() {
        List<AnimalDTO> animalDTOList= new ArrayList<>();


        return null;
    }
}
