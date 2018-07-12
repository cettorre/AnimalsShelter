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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Controller controller= new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        controller.moveCursorToPosition(Controller.pos);

        AnimalDTO animalDTO=null;
        try {
             animalDTO=controller.getAnimalDTOfromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LatLng latlng = new LatLng(animalDTO.getLatitude(), animalDTO.getLongitude());//TODO getAnimalLocation from DB

        mMap.addMarker(new MarkerOptions().position(latlng).title("Animal location"));
        CameraUpdate center=CameraUpdateFactory.newLatLng(latlng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

    }
}
