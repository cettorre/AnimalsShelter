package com.example.cettorre.animalsshelter.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.utils.LocationUtility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    Button mAddAnimal;
    ListView mList;
    LocationUtility locationUtility=new LocationUtility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        locationUtility.connectToGooglePlay(getApplicationContext());

        mAddAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InsertAnimalActivity.class);
                startActivity(i);
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, AnimalInfoActivity.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }

    Location locData;
    @Override
    public void onRequestPermissionsResult(int reqCode, String[] perms, int[] results){

        if (reqCode == 1) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                 locData = locationUtility.getLocation();
                //todo check if null
                Log.e("location",String.valueOf(locData.getLatitude()));

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                //todo check if null
                Log.e("locationPermReq",String.valueOf(locData.getLatitude()));
                //TODO test on above api 23 if this works
                //TODO when null ask user to start GPS sensor and get current Location
            }
        }
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

    public void initComponents(){
        mAddAnimal= (Button) findViewById(R.id.addAnimal);
        mList = (ListView) findViewById(R.id.list);
    }
}
