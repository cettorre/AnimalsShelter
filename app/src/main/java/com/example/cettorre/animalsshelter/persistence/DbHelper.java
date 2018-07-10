package com.example.cettorre.animalsshelter.persistence;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "animals";
    public static final String COL_NAME = "name";
    public static final String COL_DATE = "date";
    public static final String COL_AGE = "age";
    public static final String COL_CHIP = "chip";
    public static final String COL_TYPE = "type";
    public static final String COL_PHOTO = "photo";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    private static final String STRING_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME + " TEXT, " + COL_DATE + " DATE," + COL_AGE + " INTEGER,"+
                    COL_CHIP + " INTEGER, " + COL_TYPE + " TEXT,"+ COL_PHOTO + " TEXT," +
                    COL_LATITUDE + " REAL," +COL_LONGITUDE + " REAL" +
                    ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);
        //You may also load initial values into the database here// ContentValues cv = new ContentValues(10);
      }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //For now, clear the database and re-create
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}