package vehicleappraisal.com.vehicleappraisal.Tabs;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vehicleappraisal.com.vehicleappraisal.R;
import vehicleappraisal.com.vehicleappraisal.external.SingeltonData;
import vehicleappraisal.com.vehicleappraisal.network.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {



    public photosInterface my_photosInterface;
    private TextView indicatorTextView;

    private Button CameraButton,GalleryButton;

    private RecyclerView CameraRecyclerView;

    private CameraAdapter myAdapter;

    private Button submitButton;

    private List<Bitmap> data = new ArrayList<Bitmap>();

    private SingeltonData mySingeltonData = SingeltonData.getMy_SingeltonData_Reference();

    public void setMy_photosInterface(photosInterface my_photosInterface) {
        this.my_photosInterface = my_photosInterface;
    }

    public PhotosFragment() {
        // Required empty public constructor
    }

    public static PhotosFragment getInstance(){
        return (new PhotosFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        indicatorTextView = (TextView)view.findViewById(R.id.hintTextViewPhotos);

        CameraRecyclerView = (RecyclerView)view.findViewById(R.id.CameraRecyclerView);
        myAdapter = new CameraAdapter(MyApplication.getAppContext(),data);
        CameraRecyclerView.setLayoutManager(new GridLayoutManager(MyApplication.getAppContext(), 2));
//        myAdapter.setsomeListData(this);

        CameraRecyclerView.setAdapter(myAdapter);


        CameraButton = (Button)view.findViewById(R.id.cameraButton);

        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_photosInterface.cameraSelected();
            }
        });

        GalleryButton = (Button)view.findViewById(R.id.galleryButton);

        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_photosInterface.GallerySelected();
            }
        });

        submitButton = (Button)view.findViewById(R.id.submitButton1);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareToSendData();
            }
        });


        return view;
    }

    public void AddData(Bitmap someBitmap){
        data.add(someBitmap);
        myAdapter.notifyDataSetChanged();
        if(data.size()>0){
            indicatorTextView.setAlpha((float)0.0);
        }
        else{
            indicatorTextView.setAlpha((float)1.0);
        }
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
        my_photosInterface.submitDataToServer();
    }

    private void showToast(String str){
        Toast.makeText(MyApplication.getAppContext(), str, Toast.LENGTH_LONG).show();
        my_photosInterface.NavigateTpPage1();
    }

    private void deleteItemAtposition(int position){
        if(data.size()==0){
            return;
        }
        data.remove(position);
        myAdapter.notifyDataSetChanged();
        if(data.size()>0){
            indicatorTextView.setAlpha((float)0.0);
        }
        else{
            indicatorTextView.setAlpha((float)1.0);
        }
    }





    //--------------------

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
//            if(data.size() == 0){
//                return 1;
//            }else{
                return data.size();
//            }

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

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MyApplication.getAppContext(),getAdapterPosition()+"",Toast.LENGTH_LONG).show();
                    deleteItemAtposition(getAdapterPosition());
                }
            });
        }
    }



    public interface photosInterface{
        public void cameraSelected();
        public void GallerySelected();
        public void NavigateTpPage1();
        public void submitDataToServer();
    }


}
