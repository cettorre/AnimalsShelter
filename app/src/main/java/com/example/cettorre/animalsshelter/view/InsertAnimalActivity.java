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
import com.example.cettorre.animalsshelter.utils.LocationUtility;
import com.example.cettorre.animalsshelter.utils.Utils;
import java.util.Date;
import java.util.List;


public class InsertAnimalActivity extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText type;
    CheckBox hasChip;
    TextView locationTv;
    int iChip;

    Button sendData;
    Button takePhoto;
    Button startLocation;

    ImageView mImageView ;
    Controller controller=new Controller();
    static LocationUtility locationUtility=new LocationUtility();
    Utils utils=new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        locationUtility.connectToGooglePlay(getApplicationContext());
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationUtility.createLocationRequest();
        locationUtility.checkIfGPSisEnabled(InsertAnimalActivity.this);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAniimalToController();
                } catch (Exception e) {
                    Toast t = Toast.makeText(InsertAnimalActivity.this, "Please insert correct values into form: "+e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
                List<AnimalDTO> list= controller.getAnimalListDTO();
                Log.e("list",list.toString());
                }
        });

        startLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    locationUtility.startLocationUpdates(getApplicationContext());
                        locationTv.setText("Latitude: "+String.valueOf(locationUtility.mCurLocation.getLatitude())+" Longitude: "+locationUtility.mCurLocation.getLongitude());
                }catch (NullPointerException e){
                    Toast t= Toast.makeText(InsertAnimalActivity.this,"Wait a second location is not yet ready. Try again",Toast.LENGTH_LONG);
                    t.show();
                }

            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                utils.dispatchTakePictureIntent(takePictureIntent,getPackageManager(),getExternalFilesDir(Environment.DIRECTORY_PICTURES),InsertAnimalActivity.this);
                startActivityForResult(takePictureIntent, 1);
                }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationUtility.connectGoogleApiClient();//

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationUtility.disconnectApiClient();
    }

    //TODO test on above api 23
   // Location locData;//locationUtility.mCurLocation
    @Override
    public void onRequestPermissionsResult(int reqCode, String[] perms, int[] results){

        if (reqCode == 1) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                locationUtility.mCurLocation = locationUtility.getLocation();

                if(locationUtility.mCurLocation!=null)
                    Log.e("location_from_perm_res",String.valueOf(locationUtility.mCurLocation.getLatitude()));

            }else{
                locationUtility.requestAccessFineLocationPermission(this);

                if(locationUtility.mCurLocation!=null)
                     Log.e("locationPermReq",String.valueOf(locationUtility.mCurLocation.getLatitude()));

            }
        }
    }

    private void addAniimalToController() throws Exception {
         controller.addAnimal(
                name.getText().toString(),
                type.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                hasChip.isChecked(),
                new Date(),
                "photoB64",
                 locationUtility.mCurLocation.getLatitude(),
                 locationUtility.mCurLocation.getLongitude());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            mImageView.setImageURI(utils.photoUri);
            String encodedImage=  utils.convertToBase64(utils.mCurrentPhotoPath);
            Log.e("encoded_image1",encodedImage);
        }
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
        startLocation=findViewById(R.id.startLocation);
        locationTv=findViewById(R.id.current_location);
    }


}
