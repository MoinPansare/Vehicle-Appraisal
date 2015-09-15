package vehicleappraisal.com.vehicleappraisal.Tabs;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vehicleappraisal.com.vehicleappraisal.R;
import vehicleappraisal.com.vehicleappraisal.external.SingeltonData;
import vehicleappraisal.com.vehicleappraisal.network.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawingFragment extends Fragment {

    private ButtonForSource my_ButtonForSource;

    private ViewGroup my_Parent;

    private ImageView myDrawView1;
    private ImageView myDrawView2,myDrawView3,myDrawView4;

    private Context myContext;

    private Button CameraButton,GalleryButton;

    private RecyclerView CameraRecyclerView;
    private CameraAdapter myAdapter;

    private List<Bitmap> data = new ArrayList<Bitmap>();

    private Button submitButton;

    private SingeltonData mySingeltonData = SingeltonData.getMy_SingeltonData_Reference();



    public DrawingFragment() {


        // Required empty public constructor
    }

    public void AddData(Bitmap someBitmap){
        data.add(someBitmap);
        myAdapter.notifyDataSetChanged();
    }

    public void setMy_ButtonForSource(ButtonForSource my_ButtonForSource) {
        this.my_ButtonForSource = my_ButtonForSource;
    }

    public void setMyDrawView1(final Bitmap someBitmap){

        ImageView imageView = new ImageView(MyApplication.getAppContext());
        imageView.setImageBitmap(someBitmap);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        myDrawView1.setImageBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap());

    }
    public void setMyDrawView2(Bitmap someBitmap){
        myDrawView2.setImageBitmap(someBitmap);
        myAdapter.notifyDataSetChanged();
        my_Parent.invalidate();
    }
    public void setMyDrawView3(Bitmap someBitmap){
        myDrawView3.setImageBitmap(someBitmap);
        myAdapter.notifyDataSetChanged();
        my_Parent.invalidate();
    }
    public void setMyDrawView4(Bitmap someBitmap){
        myDrawView4.setImageBitmap(someBitmap);
        myAdapter.notifyDataSetChanged();
        my_Parent.invalidate();
    }

    public static DrawingFragment getInstance(){
        return (new DrawingFragment());
    }

    public void setMyContext(Context someContext){
        myContext = someContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);

        my_Parent = (ViewGroup)view.findViewById(R.id.view_Layout);

        myDrawView1 = (ImageView)view.findViewById(R.id.img1);
        myDrawView2 = (ImageView)view.findViewById(R.id.img2);
        myDrawView3 = (ImageView)view.findViewById(R.id.img3);
        myDrawView4 = (ImageView)view.findViewById(R.id.img4);

        myDrawView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingeltonData.setVector1Bitmap(((BitmapDrawable) myDrawView1.getDrawable()).getBitmap());
                loadNext(myDrawView1,"1");
            }
        });

        myDrawView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingeltonData.setVector2Bitmap(((BitmapDrawable) myDrawView2.getDrawable()).getBitmap());
                loadNext(myDrawView2, "2");
            }
        });

        myDrawView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingeltonData.setVector3Bitmap(((BitmapDrawable) myDrawView3.getDrawable()).getBitmap());
                loadNext(myDrawView3, "3");
            }
        });

        myDrawView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingeltonData.setVector4Bitmap(((BitmapDrawable) myDrawView4.getDrawable()).getBitmap());
                loadNext(myDrawView4,"4");
            }
        });


        CameraButton = (Button)view.findViewById(R.id.cameraButton);

        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_ButtonForSource.cameraSelected();
            }
        });

        GalleryButton = (Button)view.findViewById(R.id.galleryButton);

        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_ButtonForSource.GallerySelected();
            }
        });

        CameraRecyclerView = (RecyclerView)view.findViewById(R.id.CameraRecyclerView);

//
        myAdapter = new CameraAdapter(MyApplication.getAppContext(),data);
        CameraRecyclerView.setLayoutManager(new GridLayoutManager(MyApplication.getAppContext(), 2));
//        myAdapter.setsomeListData(this);

        CameraRecyclerView.setAdapter(myAdapter);

        submitButton = (Button)view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send data to server

                prepareToSendData();

            }
        });

        return view;
    }

    private void loadNext(ImageView someImageView,String indexString){
        my_ButtonForSource.SomeImageSelected(someImageView,indexString);
    }



    public class CameraAdapter extends RecyclerView.Adapter<CameraCellViewHolder> {

        private List<Bitmap> data = Collections.emptyList();
        private Context myContext;
        private LayoutInflater inflator;


        public CameraAdapter(Context context,List<Bitmap>someData) {

            myContext = context;
            inflator = LayoutInflater.from(myContext);
            this.data = someData;
        }

        @Override
        public CameraCellViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflator.inflate(R.layout.camera_cell, viewGroup, false);
            CameraCellViewHolder holder = new CameraCellViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(CameraCellViewHolder cameraCellViewHolder, int i) {
            try{
                Bitmap thisBitmap = data.get(i);
                Drawable dr = new BitmapDrawable(getResources(),thisBitmap);
//                cameraCellViewHolder.mainImage.setBackgroundDrawable(dr);
                cameraCellViewHolder.mainImage.setImageBitmap(thisBitmap);
            }catch (Exception e){
//                cameraCellViewHolder.mainImage.setImageResource(R.drawable.bg123);
            }
        }

        @Override
        public int getItemCount() {
            if(data.size() == 0){
                return 1;
            }
            return data.size();
//            return ;
        }
    }

    public class CameraCellViewHolder extends RecyclerView.ViewHolder {


        ImageView mainImage;
        ImageView deleteButton;


        public CameraCellViewHolder(View itemView) {
            super(itemView);

            mainImage = (ImageView)itemView.findViewById(R.id.camera_Cell_ImageView);
            deleteButton = (ImageView)itemView.findViewById(R.id.deleteImage);

        }


    }

    private void showToast(String str){
        Toast.makeText(MyApplication.getAppContext(),str,Toast.LENGTH_LONG).show();
        my_ButtonForSource.navigateToPage1();
    }


    private void prepareToSendData(){
        if(mySingeltonData.regNo.equalsIgnoreCase("")){
            showToast("Please Enter Registration Number");
        }
        if(mySingeltonData.engine.equalsIgnoreCase("")){
            showToast("Please Enter Engine Information");
        }
        if(mySingeltonData.regDate.equalsIgnoreCase("Reg. Date")){
            showToast("Please Select Registration Date");
            return;
        }
        if(mySingeltonData.expectedValue.equalsIgnoreCase("")){
            showToast("Please Enter Expected Value");
            return;
        }
        my_ButtonForSource.submitDataToServer();
    }

    public interface ButtonForSource{
        public void cameraSelected();
        public void GallerySelected();
        public void SomeImageSelected(ImageView someImageView,String indexString);
        public void submitDataToServer();
        public void navigateToPage1();
    }


}
