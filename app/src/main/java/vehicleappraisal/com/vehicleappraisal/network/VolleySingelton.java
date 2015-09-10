package vehicleappraisal.com.vehicleappraisal.network;


import android.graphics.Bitmap;

import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class VolleySingelton {


    public static VolleySingelton my_Volley_Singelton_Reference;
    private RequestQueue my_requestQueue;
    private ImageLoader my_imageLoader;

    private VolleySingelton(){
        my_requestQueue=Volley.newRequestQueue(MyApplication.getAppContext());
        my_imageLoader=new ImageLoader(my_requestQueue,new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingelton getMy_Volley_Singelton_Reference(){
        if(my_Volley_Singelton_Reference == null){
            my_Volley_Singelton_Reference = new VolleySingelton();
        }
        return my_Volley_Singelton_Reference;
    }

    public RequestQueue getRequestQueue(){
        return my_requestQueue;
    }

    public ImageLoader getImageLoader(){
        return my_imageLoader;
    }



}
