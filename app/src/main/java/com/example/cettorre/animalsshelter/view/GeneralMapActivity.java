package com.example.cettorre.animalsshelter.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.application.dto.AnimalDTO;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeneralMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Controller controller= new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        controller.setCursor(this);
        controller.setDBfieldSize();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        controller.moveCursorToPosition(0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        AnimalDTO animalDTO=null;
        LatLng latLng=null;
        do {

            controller.moveCursorToPosition(Controller.pos);
            try {
                animalDTO=controller.getAnimalDTOfromDB();
            } catch (Exception e) {
                e.printStackTrace();
            }
            latLng = new LatLng(animalDTO.getLatitude(), animalDTO.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Name: "+animalDTO.getName()+" Type: "+animalDTO.getType()+" Age: "+animalDTO.getAge()));
            Log.e("MAP",String.valueOf(animalDTO.getLatitude()));

            Controller.pos++;

        }while (controller.getmCursor().moveToNext());
        Controller.pos=0;

        LatLng latlng = new LatLng(41.402794, 2.194551);
        CameraUpdate center=CameraUpdateFactory.newLatLng(latlng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
