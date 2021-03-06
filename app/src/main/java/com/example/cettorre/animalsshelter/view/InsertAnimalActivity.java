package com.example.cettorre.animalsshelter.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import java.util.Date;
import java.util.List;


public class InsertAnimalActivity extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private EditText type;
    private CheckBox hasChip;
    private TextView locationTv;
    private Button sendData;
    private Button takePhoto;
    private Button startLocation;
    private ImageView mImageView ;

    private String encodedImage;
    private Controller controller=new Controller();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        controller.connectToGooglePlay(getApplicationContext());
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        controller.createLocationRequest();
        controller.checkIfGPSisEnabled(InsertAnimalActivity.this);
    }

    private View.OnClickListener createTakePhotoOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                controller.getUtils().dispatchTakePictureIntent(takePictureIntent,getPackageManager(),getExternalFilesDir(Environment.DIRECTORY_PICTURES),InsertAnimalActivity.this);
                startActivityForResult(takePictureIntent, 1);
            }
        };
    }

    private View.OnClickListener createStartLocationOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controller.startLocationUpdates(getApplicationContext());
                    locationTv.setText("Latitude: "+String.valueOf(controller.getCurrentLatitude())+
                            " Longitude: "+controller.getCurrentLongitude());
                }catch (NullPointerException e){
                    Toast t= Toast.makeText(InsertAnimalActivity.this,"Wait a second location is not yet ready. Try again",Toast.LENGTH_LONG);
                    t.show();
                }
            }
        };
    }

    private View.OnClickListener createSendDataOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAniimalToController();
                    controller.persistCurrentAnimalToDB(getApplicationContext());
                } catch (Exception e) {
                    Toast t = Toast.makeText(InsertAnimalActivity.this, "Please insert correct values into form: "+e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
                List<AnimalDTO> list= controller.getAnimalListDTO();
                Log.e("list",list.toString());
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.connectGoogleApiClient();//
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.disconnectApiClient();
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] perms, int[] results){

        if (reqCode == 1) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
               controller.setCurrentLocation(controller.getLocation());
            }else{
                controller.requestAccessFineLocationPermission(this);
            }
        }
    }

    private void addAniimalToController() throws Exception {
        String animalName=name.getText().toString();
        String animalType=type.getText().toString();
        int animalAge=Integer.parseInt(age.getText().toString());
        boolean animalHasChip=hasChip.isChecked();
        Date animalDate=new Date();
        double animalLatitude=controller.getmCurrentLocation().getLatitude();
        double animalLongitude=controller.getmCurrentLocation().getLongitude();
        if(controller.getmCurrentLocation()==null) throw new NullPointerException("Invalid Location, please add animal location");

        controller.addAnimal(animalName, animalType, animalAge, animalHasChip,animalDate,encodedImage,animalLatitude,animalLongitude);

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mImageView.setImageURI(controller.getUtils().photoUri);
            encodedImage=  controller.getEncodedImage();
            Log.e("encoded_image",encodedImage);
        }
    }

    private void initComponents() {
        setContentView(R.layout.activity_insert_animal);
        name = findViewById(R.id.name);
        age =  findViewById(R.id.age);
        type = findViewById(R.id.type);
        hasChip=findViewById(R.id.hasChip);
        sendData=findViewById(R.id.sendData);
        takePhoto=findViewById(R.id.takePhoto);
        mImageView=findViewById(R.id.photo);
        startLocation=findViewById(R.id.startLocation);
        locationTv=findViewById(R.id.current_location);
        sendData.setOnClickListener(createSendDataOnClickListener());
        startLocation.setOnClickListener(createStartLocationOnClickListener());
        takePhoto.setOnClickListener(createTakePhotoOnClickListener());
    }


}
