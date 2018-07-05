package com.example.cettorre.animalsshelter.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertAnimalActivity extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText type;
    CheckBox hasChip;
    int iChip;


    Button sendData;
    Button takePhoto;

    ImageView mImageView ;
    Uri photoUri = null;
    Controller controller=new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAniimalToController();

                List<AnimalDTO> list= controller.getAnimalListDTO();
                Log.e("list",list.toString());


                }
        });
    }

    private void addAniimalToController() {
         controller.addAnimal(
                name.getText().toString(),
                type.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                false,
                new Date(),
                "photoB64",
                23,
                18);
    }


    private void initComponents() {
        setContentView(R.layout.activity_insert_animal);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        type = (EditText) findViewById(R.id.type);
        hasChip=findViewById(R.id.hasChip);
        sendData=(Button) findViewById(R.id.sendData);
        takePhoto=findViewById(R.id.takePhoto);
        mImageView=findViewById(R.id.photo);
    }

}
