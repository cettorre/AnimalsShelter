package com.example.cettorre.animalsshelter.application;

import android.util.Log;

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
         boolean hasChip,
         Date date,
         String photoB64,
         float latitude,
         float longitude){
     Animal animal=new Animal(name,type,age,hasChip,date,photoB64,latitude,longitude);
     animalList.add(animal);
     Log.e("created_animal",animal.toString());
     Log.e("animallist",animalList.toString());
    }

    public List<AnimalDTO> getAnimalListDTO() {
        List<AnimalDTO> animalDTOList= new ArrayList<>();
        for(int i=0;i<animalList.size();i++){
            animalDTOList.add(new AnimalDTO(animalList.get(i)));
        }
        return animalDTOList;
    }
}
