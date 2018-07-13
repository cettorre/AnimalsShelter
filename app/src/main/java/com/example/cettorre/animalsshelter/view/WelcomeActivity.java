package com.example.cettorre.animalsshelter.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cettorre.animalsshelter.R;

public class WelcomeActivity extends AppCompatActivity {

    Button showGeneralMap;
    Button goToDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        showGeneralMap=findViewById(R.id.show_general_map);
        goToDatabase=findViewById(R.id.go_to_database);


        showGeneralMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this,GeneralMapActivity.class);
                startActivity(i);
            }
        });

        goToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(i);

            }
        });

    }
}
