package com.example.cettorre.animalsshelter.view;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.cettorre.animalsshelter.R;
import com.example.cettorre.animalsshelter.application.Controller;
import com.example.cettorre.animalsshelter.utils.Utils;

public class ShowPhotoActivity extends AppCompatActivity {

    private ImageView fullPhoto;
    private Bitmap bmp;
    private Controller controller=new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        controller.moveCursorToPosition(Controller.pos);
        String photo=controller.getAnimalPhotoFromDB();

        //controller.getDbConnection(this).query()//TODO use row ID select instead of moveCursorToPos

        if(photo!=null) {
            bmp = Utils.decodeFromBase64ToBitmap(photo);
            fullPhoto.setImageBitmap(bmp);

        }else {
            fullPhoto.setBackgroundResource(R.drawable.no_pic);
        }

    }

    private void initComponents() {
        setContentView(R.layout.activity_show_photo);
        fullPhoto=findViewById(R.id.fullPhoto);
    }
}
