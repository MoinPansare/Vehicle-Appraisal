package vehicleappraisal.com.vehicleappraisal.external;

import android.graphics.Bitmap;

/**
 * Created by macpro on 9/10/15.
 */
public class SingeltonData {

    public String regNo, engine, regDate, model, variant, motDate, extra, make, rflDate, mileage, owner, expectedValue, color;
    public String serviceHistory, servicePackage, howDoYouMantain;
    public Boolean vanImported, v5cCode, keySets, warrenty, accidents;


    private static Bitmap vector1 = null;
    private static Bitmap vector2 = null;
    private static Bitmap vector3 = null;
    private static Bitmap vector4 = null;

    public static SingeltonData my_SingeltonData;

    private SingeltonData() {
        regNo = engine = regDate = model = variant = motDate = extra = make = rflDate = mileage = owner = expectedValue = color = "";
        serviceHistory = servicePackage = howDoYouMantain = "";
        vanImported = v5cCode = keySets = warrenty = accidents = false;
    }

    public static SingeltonData getMy_SingeltonData_Reference() {
        if (my_SingeltonData == null) {
            my_SingeltonData = new SingeltonData();
        }
        return my_SingeltonData;
    }

    public void setVector1Bitmap(Bitmap bitmap) {
        vector1 = bitmap;
    }

    public void setVector2Bitmap(Bitmap bitmap) {
        vector2 = bitmap;
    }

    public void setVector3Bitmap(Bitmap bitmap) {
        vector3 = bitmap;
    }

    public void setVector4Bitmap(Bitmap bitmap) {
        vector4 = bitmap;
    }

    public Bitmap getVector1() {
        return vector1;
    }

    public Bitmap getVector2() {
        return vector2;
    }

    public Bitmap getVector3() {
        return vector3;
    }

    public Bitmap getVector4() {
        return vector4;
    }


}
