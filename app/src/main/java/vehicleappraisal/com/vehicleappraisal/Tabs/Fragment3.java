package vehicleappraisal.com.vehicleappraisal.Tabs;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import vehicleappraisal.com.vehicleappraisal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {

    private MyDrawView myDrawView;
    private Button saveButton;
    private Context myContext;

    public Fragment3(){
        // nothing
    }


    public static Fragment3 getInstance(){
        return (new Fragment3());
    }

    public void setMyContext(Context someContext){
        myContext = someContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
//        myDrawView = (MyDrawView)view.findViewById(R.id.myDrawer);
//        saveButton = (Button)view.findViewById(R.id.saveButton);

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }


}
