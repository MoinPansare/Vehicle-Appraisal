package vehicleappraisal.com.vehicleappraisal;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.drakeet.materialdialog.MaterialDialog;
import vehicleappraisal.com.vehicleappraisal.Tabs.CustomPager;
import vehicleappraisal.com.vehicleappraisal.Tabs.DrawingFragment;
import vehicleappraisal.com.vehicleappraisal.Tabs.Fragment1;
import vehicleappraisal.com.vehicleappraisal.Tabs.Fragment2;
import vehicleappraisal.com.vehicleappraisal.Tabs.PhotosFragment;
import vehicleappraisal.com.vehicleappraisal.Tabs.Sketch;
import vehicleappraisal.com.vehicleappraisal.Tabs.SlidingTabLayout;
import vehicleappraisal.com.vehicleappraisal.external.SingeltonData;
import vehicleappraisal.com.vehicleappraisal.network.MyApplication;
import vehicleappraisal.com.vehicleappraisal.network.VolleySingelton;


public class Home extends AppCompatActivity implements DrawingFragment.ButtonForSource,Fragment1.frg1Interface,Fragment2.frg2Interface,PhotosFragment.photosInterface {

    private NewtonCradleLoading newtonCradleLoading;
    private View loadingView;
    private TextView loadingTextView;


    String userName,Token;

    MaterialDialog mMaterialDialog;

    private Toolbar myToolar;

    private CustomPager mPager;
    private SlidingTabLayout myTabLayout;
    private Fragment1 frg1;
    private Fragment2 frg2;
    private DrawingFragment frg3;
    private PhotosFragment frg4;

    private static final int CAMERA_REQUEST = 1888;
    private int PICK_IMAGE_REQUEST = 1;

    private VolleySingelton mVolleySingleton;
    private RequestQueue myRequestQueue;

    private final String URLFORGET = "http://testwip.northside.co.uk/Broker/Broker.svc/GetTableData";

    private SingeltonData mySingeltonData = SingeltonData.getMy_SingeltonData_Reference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        loadingView = (View)findViewById(R.id.loadingView);
        loadingTextView = (TextView)findViewById(R.id.loadingTextView);

        startLoading();

        myToolar = (Toolbar)findViewById(R.id.app_bar_id);
        setSupportActionBar(myToolar);
        getSupportActionBar().setTitle("");

//        myTabLayout.setBackgroundColor(Color.WHITE);

        mPager = (CustomPager) findViewById(R.id.viewPagerHome);
        myTabLayout = (SlidingTabLayout) findViewById(R.id.slidingtabLayout);
//        myTabLayout.setBackgroundColor(getColor(R.color.primaryColor));

        mPager.setPagingEnabled(false);
        mPager.setScrollDurationFactor(3);

        mVolleySingleton = VolleySingelton.getMy_Volley_Singelton_Reference();
        myRequestQueue = mVolleySingleton.getRequestQueue();

//        Toast.makeText(this,getIntent().getStringExtra("UserName").toString(),Toast.LENGTH_LONG).show();
        userName = this.getIntent().getStringExtra(getString(R.string.accessToken));
        Token = this.getIntent().getStringExtra(getString(R.string.accessToken));
//        Toast.makeText(this,userName,Toast.LENGTH_SHORT).show();

        JSONObject postObj = new JSONObject();
        try {
            postObj.put("TokenValue",Token);
        } catch (JSONException e) {
            stopLoading();
            e.printStackTrace();
        }

        JsonObjectRequest baseRequest = new JsonObjectRequest(Request.Method.POST, URLFORGET,postObj,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());

                        try {
                            JSONArray colorArray = response.getJSONArray("Colours");
                            ArrayList<String> colorSpinnerData = new ArrayList<String>();
                            ArrayList<String> colorSpinnerDataIndex = new ArrayList<String>();

                            for(int i=0;i<colorArray.length();i++){
                                JSONObject obj = colorArray.getJSONObject(i);
                                colorSpinnerData.add(obj.get("Colour").toString());
                                colorSpinnerDataIndex.add(obj.get("id").toString());
                            }


                            JSONArray VehicleMaintenanceArr = response.getJSONArray("VehicleMaintenance");
                            ArrayList<String> MantainSpinnerData = new ArrayList<String>();
                            ArrayList<String> MantainSpinnerDataIndex = new ArrayList<String>();

                            for(int i=0;i<VehicleMaintenanceArr.length();i++){
                                JSONObject obj = VehicleMaintenanceArr.getJSONObject(i);
                                MantainSpinnerData.add(obj.get("VehicleMaintenanceDescription").toString());
                                MantainSpinnerDataIndex.add(obj.get("Maintenanceid").toString());
                            }

                            JSONArray VehicleServiceHistoryArr = response.getJSONArray("VehicleServiceHistory");
                            ArrayList<String> serviecHistorySpinnerData = new ArrayList<String>();
                            ArrayList<String> serviecHistorySpinnerDataIndex = new ArrayList<String>();

                            for(int i=0;i<VehicleServiceHistoryArr.length();i++){
                                JSONObject obj = VehicleServiceHistoryArr.getJSONObject(i);
                                serviecHistorySpinnerData.add(obj.get("ServiceHistoryDescription").toString());
                                serviecHistorySpinnerDataIndex.add(obj.get("ServiceHistoryID").toString());
                            }

                            frg1.setColors(colorSpinnerData, colorSpinnerDataIndex);

                            frg2.setMantainSpinnerData(MantainSpinnerData,MantainSpinnerDataIndex);

                            frg2.setserviecHistorySpinnerData(serviecHistorySpinnerData,serviecHistorySpinnerDataIndex);

//                            frg2.setServicePackageSpinnerData(ServicePackageSpinnerData);
                            stopLoading();


                        } catch (JSONException e) {
                            stopLoading();
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response",error.toString());
                        stopLoading();
                    }
                }
        );

//        JsonObjectRequest getDataReq = new JsonObjectRequest(Request.Method.POST,G)

        myRequestQueue.add(baseRequest);

//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Response: ", response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//
//        VolleySingelton.getRequestQueue().add(jsObjRequest);


        frg1 = Fragment1.getInstance();
        frg1.setfragmentManager(getSupportFragmentManager());
        frg1.setMy_frg1Interface(this);
        frg2 = Fragment2.getInstance();
        frg2.setMy_frg2Interface(this);
        frg3 = DrawingFragment.getInstance();
        frg3.setMyContext(this);
        frg3.setMy_ButtonForSource(this);
        frg4 = PhotosFragment.getInstance();
        frg4.setMy_photosInterface(this);


        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        myTabLayout.setDistributeEvenly(false);
        myTabLayout.setTabWeights(new int[]{3,3,2,2});
        myTabLayout.setViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void cameraSelected() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//    }

//    @Override
//    public void GallerySelected() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void SomeImageSelected(ImageView someImageView, final String indexString) {


        Intent intent = new Intent(Home.this, Sketch.class);
        intent.putExtra("IndexOfImage", indexString);

        if(android.os.Build.VERSION.SDK_INT >= 21){
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, someImageView, "imageTransition");
//        startActivity(intent, options.toBundle());
//            startActivity(intent, options.toBundle());
            startActivityForResult(intent,99,options.toBundle());
        }
        else{
//            startActivity(intent);
            startActivityForResult(intent,99);
        }

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
            frg4.AddData(photo);
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                frg2.setUserImage(bitmap);
                frg4.AddData(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 99 && resultCode == 99 ) {
            String index = data.getStringExtra("StringIndex");
            switch (index){
                case "1" :
                    Bitmap someBitmap = mySingeltonData.getVector1();
                    frg3.setMyDrawView1(someBitmap);
                    break;
                case "2" :
                    Bitmap someBitmap2 = mySingeltonData.getVector2();
                    frg3.setMyDrawView2(someBitmap2);
                    break;
                case "3" :
                    Bitmap someBitmap3 = mySingeltonData.getVector3();
                    frg3.setMyDrawView3(someBitmap3);
                    break;
                case "4" :
                    Bitmap someBitmap4 = mySingeltonData.getVector4();
                    frg3.setMyDrawView4(someBitmap4);
                    break;
            }
        }
    }

    @Override
    public void data1Entered() {
        mPager.setPagingEnabled(true);
        mPager.setCurrentItem(1);
        mPager.setPagingEnabled(false);
    }

    @Override
    public void data2Entered() {
        mPager.setPagingEnabled(true);
        mPager.setCurrentItem(2);
        mPager.setPagingEnabled(false);
    }

    @Override
    public void navigateTpLastPage() {
        mPager.setPagingEnabled(true);
        mPager.setCurrentItem(3);
        mPager.setPagingEnabled(false);
    }

    @Override
    public void NavigateTpPage1() {
        mPager.setPagingEnabled(true);
        mPager.setCurrentItem(0);
        mPager.setPagingEnabled(false);
        frg1.highlighthNecessary();
    }

    @Override
    public void submitDataToServer() {
        startLoading();
        JSONObject params = new JSONObject();
        try {
            params.put("RegNo", mySingeltonData.regNo);
            params.put("Model", mySingeltonData.model);
//
            params.put("Variant", mySingeltonData.variant);
            params.put("Extras", mySingeltonData.extra);
//
            params.put("Mileage", mySingeltonData.mileage);
            params.put("MOTDueDate", mySingeltonData.motDate);
//
            params.put("RegDate", mySingeltonData.regDate);
            params.put("NoOfOwners", getIntFromString(mySingeltonData.owner.toString()));
//
            params.put("RFLExpiryDate", mySingeltonData.rflDate);
            params.put("VanImported", mySingeltonData.vanImported);
//
            params.put("V5CDoc", mySingeltonData.v5cCode);
            params.put("SetOfKeys", mySingeltonData.keySets);
//
            params.put("Warranty", mySingeltonData.warrenty);
            params.put("Accidents", mySingeltonData.accidents);
//
            params.put("VehicleMaintainID", getIntFromString(mySingeltonData.howDoYouMantain.toString()));
            params.put("ServicePackage", getIntFromString(mySingeltonData.servicePackage.toString()));
//
            params.put("ColourID", getIntFromString(mySingeltonData.color.toString()));
            params.put("ServiceHistoryID", getIntFromString(mySingeltonData.serviceHistory.toString()));
//
            params.put("AppraisalStatusID", 1);
            params.put("AppraisalDate", DateFormat.getDateTimeInstance().format(new Date()).toString());
//
            params.put("Make", mySingeltonData.make);
            params.put("ExpectedValue", mySingeltonData.expectedValue);
//
            params.put("UserNotes", " ");
            params.put("TokenValue", this.Token);

            params.put("Engine", mySingeltonData.engine);

        } catch (JSONException e) {
            e.printStackTrace();
            stopLoading();
        }

        String url = "http://testwip.northside.co.uk/Broker/Broker.svc/CreateAppraisal";


        JsonObjectRequest finalDataPOSTRequest = new JsonObjectRequest(Request.Method.POST, url, params,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mPager.setPagingEnabled(true);
                        mPager.setCurrentItem(0);
                        mPager.setPagingEnabled(false);
//                        Toast.makeText(Home.this,"All Done",Toast.LENGTH_LONG).show();

                        sendVectorImages();
//                        showResultDialogBox();
//                        SimpleDialogFragment.createBuilder(MyApplication.getAppContext(), getSupportFragmentManager()).setMessage(messageStr).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stopLoading();
                        Toast.makeText(Home.this,"There Was Some Error Please Try Again In Some Time",Toast.LENGTH_LONG).show();
                    }
                }

        );

        Log.d("Sending Data", params.toString());

        VolleySingelton.getMy_Volley_Singelton_Reference().getRequestQueue().add(finalDataPOSTRequest);

    }

    private void sendVectorImages(){
        JSONObject paramsVector = new JSONObject();
        try {
            paramsVector.put("TokenValue", this.Token);
            paramsVector.put("RegNo",mySingeltonData.regNo);

            if(mySingeltonData.getVector1()!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mySingeltonData.getVector1().compress(Bitmap.CompressFormat.JPEG, 0, stream);
                byte[] byteArray1 = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray1, Base64.DEFAULT);
                paramsVector.put("Image1",encoded);

            }else{
                paramsVector.put("Image1","null");
            }

            if(mySingeltonData.getVector2()!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mySingeltonData.getVector2().compress(Bitmap.CompressFormat.JPEG, 0, stream);

                byte[] byteArray2 = stream.toByteArray();
                String encoded2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);
                paramsVector.put("Image2",encoded2);

            }else{
                paramsVector.put("Image2","null");
            }

            if(mySingeltonData.getVector3()!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mySingeltonData.getVector3().compress(Bitmap.CompressFormat.JPEG, 0, stream);
                byte[] byteArray3 = stream.toByteArray();
                String encoded3 = Base64.encodeToString(byteArray3, Base64.DEFAULT);
                paramsVector.put("Image3",encoded3);
            }else{
                paramsVector.put("Image3","null");
            }

            if(mySingeltonData.getVector4()!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mySingeltonData.getVector4().compress(Bitmap.CompressFormat.JPEG, 0, stream);
                byte[] byteArray4 = stream.toByteArray();
                String encoded4 = Base64.encodeToString(byteArray4, Base64.DEFAULT);
                paramsVector.put("Image4",encoded4);
            }else{
                paramsVector.put("Image4","null");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            stopLoading();
        }


        JsonObjectRequest vectorImageRequest = new JsonObjectRequest(Request.Method.POST, "http://testwip.northside.co.uk/Broker/Broker.svc/UploadVectorImages", paramsVector,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mPager.setPagingEnabled(true);
                        mPager.setCurrentItem(0);
                        mPager.setPagingEnabled(false);
                        stopLoading();
                        showResultDialogBox();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stopLoading();
                        Toast.makeText(Home.this,"There Was Some Error Please Try Again In Some Time",Toast.LENGTH_LONG).show();
                    }
                }
        );

        Log.d("params",paramsVector.toString());

        VolleySingelton.getMy_Volley_Singelton_Reference().getRequestQueue().add(vectorImageRequest);
        mySingeltonData.setVector1Bitmap(null);
        mySingeltonData.setVector2Bitmap(null);
        mySingeltonData.setVector3Bitmap(null);
        mySingeltonData.setVector4Bitmap(null);
    }

    private int getIntFromString(String str){
        if(str.equalsIgnoreCase("")){
            return 1;
        }
        return Integer.parseInt(str);
    }

    @Override
    public void cameraSelected() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void showResultDialogBox(){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("MaterialDialog")
                .setMessage("Hello world!")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });

        mMaterialDialog.show();

        mMaterialDialog.setTitle("Success");
        mMaterialDialog.show();
// You can change the message anytime. after show
        String messageStr = "\n\n" + getResources().getString(R.string.finalMessage) + "\n\n";
        mMaterialDialog.setMessage(messageStr);
    }



    @Override
    public void GallerySelected() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return frg1;
                case 1:
                    return frg2;
                case 2:
                    return frg3;
                case 3:return frg4;
                default:
                    return frg1;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public void startLoading(){
        newtonCradleLoading.setAlpha((float) 1.0);
        newtonCradleLoading.start();
        loadingView.setAlpha((float) 0.7);
        loadingTextView.setAlpha((float) 1.0);
    }

    public void stopLoading(){
        newtonCradleLoading.setAlpha((float) 0.0);
        newtonCradleLoading.stop();
        loadingView.setAlpha((float) 0.0);
        loadingTextView.setAlpha((float) 0.0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
