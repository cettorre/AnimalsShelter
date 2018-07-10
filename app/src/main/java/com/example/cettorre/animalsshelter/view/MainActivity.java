package com.example.cettorre.animalsshelter.view;


import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.persistence.DbUtil;

public class MainActivity extends AppCompatActivity {

    Button mAddAnimal;
    ListView mList;
    static SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
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
    protected void onResume() {
        super.onResume();
        DbUtil.setCursor(this);
        DbUtil.setDBfield();

        mAdapter = DbUtil.getSimpleCursorAdapter(this);
        mList.setAdapter(mAdapter);
        //Refresh the list
        DbUtil.getmCursor().requery();
        DbUtil.getSimpleCursorAdapter(this).notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close all connections
        DbUtil.getDbConnection(this).close();
        DbUtil.getmCursor().close();
    }

    public void initComponents(){
        mAddAnimal= (Button) findViewById(R.id.addAnimal);
        mList = (ListView) findViewById(R.id.list);
    }
}
