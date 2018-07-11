package com.example.cettorre.animalsshelter.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;

import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import com.example.cettorre.animalsshelter.view.InsertAnimalActivity;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbUtil {

    private static DbHelper mHelper;
    private static SQLiteDatabase mDb;
    static Cursor mCursor;
    private static SimpleCursorAdapter mAdapter;
    private static ContentValues cv;
    private static Controller controller=new Controller();

    public static SQLiteDatabase getDbConnection(Context context){
        mHelper = new DbHelper(context);
        mDb = mHelper.getWritableDatabase();
        return mDb;
    }

    public static Cursor getmCursor() {
        return mCursor;
    }

    public static void setCursor(Context context){

       SQLiteDatabase sqLiteDatabase=getDbConnection(context);
       String[] columns = new String[]{
                "_id", DbHelper.COL_NAME,   DbHelper.COL_DATE,DbHelper.COL_AGE,
                DbHelper.COL_CHIP,          DbHelper.COL_TYPE,DbHelper.COL_PHOTO,
                DbHelper.COL_LATITUDE,      DbHelper.COL_LONGITUDE };

        mCursor = sqLiteDatabase
                .query(DbHelper.TABLE_NAME, columns,
                        null, null, null,
                        null, null, null);

    }

    public static SimpleCursorAdapter getSimpleCursorAdapter(Context context){

        String[] headers = new String[]{
                DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE,
                DbHelper.COL_CHIP,DbHelper.COL_TYPE,DbHelper.COL_PHOTO,
                DbHelper.COL_LATITUDE,      DbHelper.COL_LONGITUDE };

        mAdapter = new SimpleCursorAdapter(
                context, android.R.layout.two_line_list_item,
                mCursor, headers, new int[]{
                android.R.id.text1, android.R.id.text2});

        return mAdapter;
    }

    public static String getStringValueFromDB(String string){

        return mCursor.getString(mCursor.getColumnIndexOrThrow(string));
    }

    public static int getIntValueFromDB(String string){

        return  mCursor.getInt(mCursor.getColumnIndexOrThrow(string));
    }

    public static double getRealValueFromDB(String string){

        return  mCursor.getDouble(mCursor.getColumnIndexOrThrow(string));
    }



    public static void persistCurrentAnimalToDB(Context context) {
        cv = new ContentValues(2);
        AnimalDTO animalDTO= controller.getCurrentAnimalDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv.put(DbHelper.COL_NAME, animalDTO.getName());
        cv.put(DbHelper.COL_DATE, dateFormat.format(new Date()));
        cv.put(DbHelper.COL_AGE, animalDTO.getAge());
        cv.put(DbHelper.COL_TYPE, animalDTO.getType());
        cv.put(DbHelper.COL_PHOTO,animalDTO.getPhotoB64());//TODO
        cv.put(DbHelper.COL_LATITUDE,animalDTO.getLatitude());
        cv.put(DbHelper.COL_LONGITUDE,animalDTO.getLongitude());
        if(animalDTO.isHasChip()){
            cv.put(DbHelper.COL_CHIP,1);
        }else {
            cv.put(DbHelper.COL_CHIP,0);
        }

        DbUtil.getDbConnection(context).insert(DbHelper.TABLE_NAME, null, cv);
        }

    public static void setDBfield() {
        Field field = null;
        try {
            field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.set(null, 4096 * 4096);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}