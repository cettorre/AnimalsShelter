package com.example.cettorre.animalsshelter.application;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import com.example.cettorre.animalsshelter.domain.Animal;
import com.example.cettorre.animalsshelter.persistence.DbHelper;
import com.example.cettorre.animalsshelter.persistence.DbUtil;
import com.example.cettorre.animalsshelter.utils.LocationUtility;
import com.example.cettorre.animalsshelter.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
     private static List<Animal> animalList=new ArrayList<>();
     public static LocationUtility locationUtility=new LocationUtility();
     public Utils utils=new Utils();
     public static String row;
     public static int pos;


    public static String getRow() {
        return row;
    }

    public static void setRow(String row) {
        Controller.row = row;
    }

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

    private Date getAnimalDateFromDB() {
        String string = DbUtil.getStringValueFromDB(DbHelper.COL_DATE);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    private int getAnimalAgeFromDB() {
        return DbUtil.getIntValueFromDB(DbHelper.COL_AGE);
    }

    private boolean getAnimalChipFromDB() {
        return  (DbUtil.getIntValueFromDB(DbHelper.COL_CHIP)==1)?true:false;
    }

    private String getAnimalTypeFromDB() {
        return DbUtil.getStringValueFromDB(DbHelper.COL_TYPE);
    }

    public String getAnimalPhotoFromDB() {
        return DbUtil.getStringValueFromDB(DbHelper.COL_PHOTO);
    }

    private double getAnimalLongitude(){
        return DbUtil.getRealValueFromDB(DbHelper.COL_LONGITUDE);
    }
    private double getAnimalLatitude(){
        return DbUtil.getRealValueFromDB(DbHelper.COL_LONGITUDE);
    }

    public AnimalDTO setCurrentAnimalFromDBtoDomain() throws Exception {
                String name=    getAnimalNameFromDB();
                String type=getAnimalTypeFromDB();
                int age=    getAnimalAgeFromDB();
                boolean chip=    getAnimalChipFromDB();
                Date date=    getAnimalDateFromDB();
                String photo=    getAnimalPhotoFromDB();
                double latitude=    getAnimalLatitude();
                double longitude= getAnimalLongitude();

    Animal animal=new Animal(name,type, age, chip,date,photo,latitude,longitude);
    AnimalDTO animalDTO=new AnimalDTO(animal);

        return animalDTO;
    }

    public void setCursor(Context context) {
        DbUtil.setCursor(context);
    }

    public void serDBfieldSize() {
        DbUtil.setDBfieldSize();
    }

    public SimpleCursorAdapter getSimpleCursorAdaper(Context context) {
        return    DbUtil.getSimpleCursorAdapter(context);
    }

    public Cursor getmCursor() {
        return DbUtil.getmCursor();
    }
}
