package com.example.cettorre.animalsshelter.application;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import com.example.cettorre.animalsshelter.domain.Animal;
import com.example.cettorre.animalsshelter.persistence.DbUtil;
import com.example.cettorre.animalsshelter.utils.LocationUtility;
import com.example.cettorre.animalsshelter.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
     private static List<Animal> animalList=new ArrayList<>();
     public static LocationUtility locationUtility=new LocationUtility();
     public Utils utils=new Utils();


    public void addAnimal(
         String name,
         String type,
         int age,
         boolean hasChip,
         Date date,
         String photoB64,
         double latitude,
         double longitude) throws Exception {
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

    public AnimalDTO getCurrentAnimalDTO(){
        return getAnimalListDTO().get(animalList.size()-1);
    }

    public Utils getUtils() {
        return utils;
    }

    public String getEncodedImage(){
        return utils.getEncodedImage();
    }

    public void connectToGooglePlay(Context context){
        locationUtility.connectToGooglePlay(context);
    }

    public void createLocationRequest(){
        locationUtility.createLocationRequest();
    }

    public void checkIfGPSisEnabled(Activity activity){
        locationUtility.checkIfGPSisEnabled(activity);
    }

    public void startLocationUpdates(Context context){
        locationUtility.startLocationUpdates(context);
    }

    public double getCurrentLatitude(){
        return locationUtility.getLocation().getLatitude();
    }

    public double getCurrentLongitude(){
        return locationUtility.getLocation().getLongitude();
    }

    public void connectGoogleApiClient(){
        locationUtility.connectGoogleApiClient();
    }

    public void disconnectApiClient(){
        locationUtility.disconnectApiClient();
    }
    public Location getmCurrentLocation(){
        return locationUtility.mCurLocation;
    }
    public Location getLocation(){
        return locationUtility.getLocation();
    }
    public void requestAccessFineLocationPermission(Activity activity){
        locationUtility.requestAccessFineLocationPermission(activity);
    }

    public void setCurrentLocation(Location location){
        locationUtility.setmCurLocation(location);
    }

    public void persistCurrentAnimalToDB(Context context){
        DbUtil.persistCurrentAnimalToDB(context);
    }
}
