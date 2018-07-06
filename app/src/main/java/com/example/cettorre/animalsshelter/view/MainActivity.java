package com.example.cettorre.animalsshelter.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.utils.LocationUtility;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    Button mAddAnimal;
    ListView mList;
    LocationUtility locationUtility=new LocationUtility();

    //TODO move
    protected GoogleApiClient mGoogleApiClient;

    private Location getLocation() {
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            return loc;
        }
        catch (SecurityException e) {
            return null;
        }
    }



//____________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        locationUtility.connectToGooglePlay(this);


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

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] perms, int[] results){
        if (reqCode == 1) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                Location locData = getLocation();

                Log.e("location",String.valueOf(locData.getLatitude()));


            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();//TODO move
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()){//TODO move
            mGoogleApiClient.disconnect();
        }
    }

    public void initComponents(){
        mAddAnimal= (Button) findViewById(R.id.addAnimal);
        mList = (ListView) findViewById(R.id.list);
    }
}
