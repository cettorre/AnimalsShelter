package com.example.cettorre.animalsshelter.view;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.persistence.DbHelper;
import com.example.cettorre.animalsshelter.persistence.DbUtil;
import com.example.cettorre.animalsshelter.utils.Utils;


public class AnimalInfoActivity extends AppCompatActivity {

    Button delBtn;
    TextView iName;
    TextView iDate;
    TextView iAge;
    TextView iChip;
    TextView iType;
    ImageButton aPhoto;
    static int positionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);
        initComponents();

        aPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimalInfoActivity.this,ShowPhotoActivity.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        int pos = i.getIntExtra("position",1);
        //positionList=pos;
        Log.e("position",String.valueOf(pos));

        DbUtil.setCursor(this);
        DbUtil.getmCursor().moveToPosition(pos);

        //Get the id value of this row
        String name = DbUtil.getStringValueFromDB(DbHelper.COL_NAME);
        Log.e("column",name);
        String date = DbUtil.getStringValueFromDB(DbHelper.COL_DATE);
        Log.e("column",date);
        int age = DbUtil.getIntValueFromDB(DbHelper.COL_AGE);
        Log.e("column2",String.valueOf(age));
        String chip = (DbUtil.getIntValueFromDB(DbHelper.COL_CHIP)==1)?"yes":"no";
        Log.e("chip",chip);
        String type = DbUtil.getStringValueFromDB(DbHelper.COL_TYPE);
        Log.e("column",type);
        String photo = DbUtil.getStringValueFromDB(DbHelper.COL_PHOTO);

        if(photo!=null) {
            //         Log.e("column_photo", photo);
            aPhoto.setImageBitmap(Utils.decodeFromBase64ToBitmap(photo));
        }else {
            aPhoto.setBackgroundResource(R.drawable.no_pic);
        }

        //Refresh the list
        DbUtil.getmCursor().requery();

        iName.setText("name: "+name);
        iDate.setText("date: "+date);
        iAge.setText("age: "+String.valueOf(age));
        iChip.setText("chip: "+chip);
        iType.setText("type: "+type);



        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int pos = i.getIntExtra("position",2);
                Log.e("position",String.valueOf(pos));

                DbUtil.getmCursor().moveToPosition(pos);
                //Get the id value of this row
                String rowId = DbUtil.getmCursor().getString(0); //Column 0 of the cursor is the id
                Log.e("rowID",rowId);

                DbUtil.getDbConnection(AnimalInfoActivity.this).delete(DbHelper.TABLE_NAME, "_id = ?", new String[]{rowId});
                //Refresh the list
                DbUtil.getmCursor().requery();

                Intent i2 = new Intent(AnimalInfoActivity.this, MainActivity.class);
                startActivity(i2);

            }
        });

    }

    private void initComponents() {

        iName= findViewById(R.id.iName);
        iDate= findViewById(R.id.iDate);
        iAge=  findViewById(R.id.iAge);
        iChip= findViewById(R.id.iChip);
        iType= findViewById(R.id.iType);
        aPhoto=findViewById(R.id.aPhoto);
        delBtn=findViewById(R.id.iDelete);

    }

}