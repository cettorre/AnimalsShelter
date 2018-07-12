package com.example.cettorre.animalsshelter.view;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.persistence.DbHelper;

public class MainActivity extends AppCompatActivity {

    private Button mAddAnimal;
    private ListView mList;
    private static SimpleCursorAdapter mAdapter;
    private Controller controller=new Controller();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    private View.OnClickListener createmAddAnimalOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InsertAnimalActivity.class);
                startActivity(i);
            }
        };
    }

    private AdapterView.OnItemClickListener createmListOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, AnimalInfoActivity.class);
                i.putExtra("position", position);
                controller.moveCursorToPosition(position);
                Controller.row=controller.getRowIdFromDB();
                Controller.pos=position;
                startActivity(i);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.setCursor(this);
        controller.setDBfieldSize();
        mAdapter=controller.getSimpleCursorAdaper(this);
        mAdapter.setViewBinder(createSetViewBinderForTheListAdapter());
        mList.setAdapter(mAdapter);
        controller.requeryDB();
        controller.getSimpleCursorAdaper(this).notifyDataSetChanged();

    }

    private SimpleCursorAdapter.ViewBinder createSetViewBinderForTheListAdapter() {
        return new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 1) {
                    TextView text = (TextView) view;  // get your View
                    text.setText(controller.getmCursor().getString(controller.getmCursor().getColumnIndexOrThrow(DbHelper.COL_NAME)));
                    // text.setTextColor(Color.rgb(0,255,255));
                    text.setTextSize(24);
                    if(controller.getmCursor().getInt(controller.getmCursor().getColumnIndexOrThrow(DbHelper.COL_CHIP))==1)
                        text.setTextColor(Color.rgb(0,255,255));
                }
                return false;
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.getDbConnection(this).close();
        controller.getmCursor().close();
    }

    public void initComponents(){
        setContentView(R.layout.activity_main);
        mAddAnimal= findViewById(R.id.addAnimal);
        mList =     findViewById(R.id.list);
        mAddAnimal.setOnClickListener(createmAddAnimalOnClickListener());
        mList.setOnItemClickListener(createmListOnItemClickListener());
    }
}
