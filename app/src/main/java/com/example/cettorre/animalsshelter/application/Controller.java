package com.example.cettorre.animalsshelter.application;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import android.view.View;

import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import com.example.cettorre.animalsshelter.domain.Animal;
import com.example.cettorre.animalsshelter.persistence.DbHelper;
import com.example.cettorre.animalsshelter.persistence.DbUtil;
import com.example.cettorre.animalsshelter.utils.LocationUtility;
import com.example.cettorre.animalsshelter.utils.Utils;
import com.example.cettorre.animalsshelter.view.AnimalInfoActivity;

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

    public void requeryDB() {
        DbUtil.getmCursor().requery();

    }

    public void prepareCursor(Context context) {
        DbUtil.setCursor(context);

    }
    public String getAnimalNameFromDB() {
        return DbUtil.getStringValueFromDB(DbHelper.COL_NAME);

    }

    public String getAnimalDateFromDB() {
        return  DbUtil.getStringValueFromDB(DbHelper.COL_DATE);
    }

    public int getAnimalAgeFromDB() {
        return DbUtil.getIntValueFromDB(DbHelper.COL_AGE);
    }

    public String getAnimalChipFromDB() {
        return  (DbUtil.getIntValueFromDB(DbHelper.COL_CHIP)==1)?"yes":"no";
    }

    public String getAnimalTypeFromDB() {
        return DbUtil.getStringValueFromDB(DbHelper.COL_TYPE);
    }

    public String getAnimalPhotoFromDB() {
        return DbUtil.getStringValueFromDB(DbHelper.COL_PHOTO);
    }

    public void moveCursorToPosition(int pos) {
        DbUtil.getmCursor().moveToPosition(pos);
    }

    public String getRowIdFromDB() {
        return DbUtil.getmCursor().getString(0);
    }

    public SQLiteDatabase getDbConnection(Context context) {
        return DbUtil.getDbConnection(context);
    }

    public void deleteRowByIDFromTable(Context context,String rowId) {
        DbUtil.getDbConnection(context).delete(DbHelper.TABLE_NAME, "_id = ?", new String[]{rowId});

    }
}
