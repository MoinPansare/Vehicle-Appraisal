package vehicleappraisal.com.vehicleappraisal.network;



import android.app.Application;
import android.content.Context;


/**
 * Created by macpro on 8/20/15.
 */
public class MyApplication extends Application {

    public static MyApplication my_application;

    public static MyApplication getApplication(){
        return my_application;
    }

    public static Context getAppContext(){
        return my_application.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        my_application = this;
    }


}