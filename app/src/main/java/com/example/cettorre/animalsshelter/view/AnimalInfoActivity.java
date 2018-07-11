package com.example.cettorre.animalsshelter.view;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.utils.Utils;


public class AnimalInfoActivity extends AppCompatActivity {

    Button delBtn;
    Button showLocation;
    TextView iName;
    TextView iDate;
    TextView iAge;
    TextView iChip;
    TextView iType;
    ImageButton aPhoto;
    private Controller controller=new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);
        initComponents();

        aPhoto.setOnClickListener(createImageButtonShowPhotoListener());
        delBtn.setOnClickListener(createDeleteButtonListener());
        showLocation.setOnClickListener(createShowLocationButtonListener());

        controller.prepareCursor(this);
        controller.moveCursorToPosition(getListPositionItem());

        String photo = controller.getAnimalPhotoFromDB();;
        if(photo!=null) {
            aPhoto.setImageBitmap(Utils.decodeFromBase64ToBitmap(photo));
        }else {
         //   aPhoto.setBackgroundResource(R.drawable.no_pic); moved to xml
         // aPhoto.setImageResource(R.drawable.no_pic);
        }

        iName.setText("name: "+controller.getAnimalNameFromDB());
        iDate.setText("date: "+controller.getAnimalDateFromDB());
        iAge.setText("age: "+String.valueOf(controller.getAnimalAgeFromDB()));
        iChip.setText("chip: "+controller.getAnimalChipFromDB());
        iType.setText("type: "+controller.getAnimalTypeFromDB());

        controller.requeryDB();

    }
    private int getListPositionItem(){
        Intent i = getIntent();
        int pos = i.getIntExtra("position",1);

        return pos;
    }


    private View.OnClickListener createShowLocationButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimalInfoActivity.this, MapsActivity.class);
                startActivity(i);
            }
        };
    }

    private View.OnClickListener createDeleteButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controller.moveCursorToPosition(getListPositionItem());
                String rowId = controller.getRowIdFromDB();
                Log.e("rowID",rowId);
                controller.deleteRowByIDFromTable(AnimalInfoActivity.this,rowId);
                controller.requeryDB();

                Intent i2 = new Intent(AnimalInfoActivity.this, MainActivity.class);
                startActivity(i2);

            }
        };
    }

    private View.OnClickListener createImageButtonShowPhotoListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimalInfoActivity.this,ShowPhotoActivity.class);
                startActivity(i);
            }
        };
    }

    private void initComponents() {
        iName= findViewById(R.id.iName);
        iDate= findViewById(R.id.iDate);
        iAge=  findViewById(R.id.iAge);
        iChip= findViewById(R.id.iChip);
        iType= findViewById(R.id.iType);
        aPhoto=findViewById(R.id.aPhoto);
        delBtn=findViewById(R.id.iDelete);
        showLocation=findViewById(R.id.show_location);
    }

}