package vehicleappraisal.com.vehicleappraisal.Tabs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import vehicleappraisal.com.vehicleappraisal.R;
import vehicleappraisal.com.vehicleappraisal.external.SingeltonData;

public class Sketch extends AppCompatActivity {

    private MyDrawView sketchImage;
    private Button saveButton,cancelButton,clearButton;

    public String indexOfImage = "";
    public Bitmap drawing;

    private SingeltonData my_SingeltonData = SingeltonData.getMy_SingeltonData_Reference();


//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);

        indexOfImage = getIntent().getStringExtra("IndexOfImage");



//        drawing = (Bitmap) getIntent().getParcelableExtra("BitmapImage");

//        sketchImage.setBackground(new BitmapDrawable(getResources(), bitmap));

//        File file = new File(Environment.getExternalStorageDirectory().toString() + "/sample"+indexOfImage+".png");
//        sketchImage.

//        sketchImage.setBackgroundDrawable(new BitmapDrawable(getResources(),drawing));

        sketchImage = (MyDrawView)findViewById(R.id.sketchImage);

        switch (indexOfImage) {
            case "1":
                if (my_SingeltonData.getVector1() != null) {
                    Drawable dr = new BitmapDrawable(getResources(), my_SingeltonData.getVector1());
                    sketchImage.setBackgroundDrawable(dr);
                }
                break;
            case "2":
                if (my_SingeltonData.getVector2() != null) {
                    Drawable dr = new BitmapDrawable(getResources(), my_SingeltonData.getVector2());
                    sketchImage.setBackgroundDrawable(dr);
                }
                break;
            case "3":
                if (my_SingeltonData.getVector3() != null) {
                    Drawable dr = new BitmapDrawable(getResources(), my_SingeltonData.getVector3());
                    sketchImage.setBackgroundDrawable(dr);
                }
                break;
            case "4":
                if (my_SingeltonData.getVector4() != null) {
                    Drawable dr = new BitmapDrawable(getResources(), my_SingeltonData.getVector4());
                    sketchImage.setBackgroundDrawable(dr);
                }
                break;
        }


        saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToSingelton();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(android.os.Build.VERSION.SDK_INT >= 21){
//                    finishAfterTransition();
//                }
                finish();
            }
        });

        clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (indexOfImage){

                    case "1":sketchImage.clear();
                        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.van1);
                        sketchImage.setBackgroundDrawable(new BitmapDrawable(getResources(),bmp1));
                        break;
                    case "2":sketchImage.clear();
                        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.van2);
                        sketchImage.setBackgroundDrawable(new BitmapDrawable(getResources(),bmp2));
                        break;
                    case "3":sketchImage.clear();
                        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.van3);
                        sketchImage.setBackgroundDrawable(new BitmapDrawable(getResources(),bmp3));
                        break;
                    case "4":sketchImage.clear();
                        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.van4);
                        sketchImage.setBackgroundDrawable(new BitmapDrawable(getResources(),bmp4));
                        break;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void saveToSingelton() {


        View targetView = sketchImage;

        Bitmap well = sketchImage.getBitmap();
        Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Canvas now = new Canvas(save);
        now.drawRect(new Rect(0, 0, 320, 480), paint);
        now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight()), new Rect(0, 0, 320, 480), null);
        switch (indexOfImage){
            case "1":my_SingeltonData.setVector1Bitmap(save);break;
            case "2":my_SingeltonData.setVector2Bitmap(save);break;
            case "3":my_SingeltonData.setVector3Bitmap(save);break;
            case "4":my_SingeltonData.setVector4Bitmap(save);break;
//            case "1":sketchImage.setBackground(new BitmapDrawable(getResources(),save));
        }



        Intent intent=new Intent();
        intent.putExtra("StringIndex", indexOfImage);
        setResult(99, intent);
//
//        if(android.os.Build.VERSION.SDK_INT >= 21){
//            finishAfterTransition();
//        }
        finish();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
