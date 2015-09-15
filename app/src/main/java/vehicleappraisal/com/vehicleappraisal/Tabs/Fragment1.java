package vehicleappraisal.com.vehicleappraisal.Tabs;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import vehicleappraisal.com.vehicleappraisal.R;
import vehicleappraisal.com.vehicleappraisal.external.SingeltonData;
import vehicleappraisal.com.vehicleappraisal.network.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

//    private MFCalendarView myCalender1;

    private String ownerSpinnerValue = "";
    private String colorSpinnerVlaue = "";

    private frg1Interface my_frg1Interface;

    FragmentManager my_FragmentManager;
    private Button date1,date2,date3;
    private Button nextButton;
    private TextView date1FromPicker,date2FromPicker,date3FromPicker;

    private Spinner myColorSpinner,ownerSpinner;

    private ArrayList<String> colorSpinnerData;
    private ArrayList<String> colorSpinnerDataIndex;

    private ArrayList<String> ownerSpinnerData;

    private SingeltonData mySingelton = SingeltonData.getMy_SingeltonData_Reference();

    private EditText regNo_EditText,engine_EditText,model_EditText,variant_EditText,extra_EditText,make_EditText,mileage_EditText,owner_EditText,expectedValue_EditText;

    public void setMy_frg1Interface(frg1Interface my_frg1Interface) {
        this.my_frg1Interface = my_frg1Interface;
    }

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 getInstance(){
        return (new Fragment1());
    }

    public void setfragmentManager(FragmentManager some_manager){
        this.my_FragmentManager = some_manager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        regNo_EditText = (EditText)view.findViewById(R.id.regNoEditText);
        engine_EditText = (EditText)view.findViewById(R.id.engineEditText);
        model_EditText = (EditText)view.findViewById(R.id.modelEditText);
        variant_EditText = (EditText)view.findViewById(R.id.variantEditText);
        extra_EditText = (EditText)view.findViewById(R.id.extraEditText);
        make_EditText = (EditText)view.findViewById(R.id.makeEditText);
        mileage_EditText = (EditText)view.findViewById(R.id.mileageEditText);
//        owner_EditText = (EditText)view.findViewById(R.id.ownerEditText);
        expectedValue_EditText = (EditText)view.findViewById(R.id.expectedValueEditText);


        date1 = (Button)view.findViewById(R.id.selectDate1);
        date1FromPicker = (TextView)view.findViewById(R.id.date1FromPicker);

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date1FromPicker,1);
                newFragment.show(my_FragmentManager, "datePicker");
            }
        });

        date2 = (Button)view.findViewById(R.id.selectDate2);
        date2FromPicker = (TextView)view.findViewById(R.id.MotDueTextFeild);

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date2FromPicker,2);
                newFragment.show(my_FragmentManager, "datePicker");
            }
        });

        date3 = (Button)view.findViewById(R.id.selectDate3);
        date3FromPicker = (TextView)view.findViewById(R.id.RefDateTextFeild);

        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date3FromPicker,3);
                newFragment.show(my_FragmentManager, "datePicker");
            }
        });

        myColorSpinner = (Spinner)view.findViewById(R.id.color_spinner_info);

        colorSpinnerData = new ArrayList<String>();
        colorSpinnerData.add("Red");
        colorSpinnerData.add("Green");
        colorSpinnerData.add("Blue");
        colorSpinnerData.add("Black");
        colorSpinnerData.add("Yellow");
        colorSpinnerData.add("White");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, colorSpinnerData);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        myColorSpinner.setAdapter(adapter);

        myColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorSpinnerVlaue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //----

        ownerSpinner = (Spinner)view.findViewById(R.id.ownersSpinner);

        ownerSpinnerData = new ArrayList<String>();
        ownerSpinnerData.add("1");
        ownerSpinnerData.add("2");
        ownerSpinnerData.add("3");
        ownerSpinnerData.add("4");
        ownerSpinnerData.add("5");
        ownerSpinnerData.add("6");
        ownerSpinnerData.add("7");
        ownerSpinnerData.add("8");
        ownerSpinnerData.add("9");
        ownerSpinnerData.add("10");

        ArrayAdapter<String> OwnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, ownerSpinnerData);

        OwnerAdapter.setDropDownViewResource(R.layout.spinner_item);

        ownerSpinner.setAdapter(OwnerAdapter);

        ownerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ownerSpinnerValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---

        nextButton = (Button)view.findViewById(R.id.nextButton1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToSingelton();
            }
        });

        return view;


    }

    public void setColors(ArrayList<String> list,ArrayList<String> listIndex){
        this.colorSpinnerData = list;
        this.colorSpinnerDataIndex = listIndex;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, colorSpinnerData);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        myColorSpinner.setAdapter(adapter);

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private TextView passedTextView;
        private int index;

        @SuppressLint("ValidFragment")
        public DatePickerFragment(TextView someTextView,int someIndex){
            this.passedTextView = someTextView;
            this.index = someIndex;
        }

        public DatePickerFragment(){

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month+1, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            switch (index){
                case 1 : passedTextView.setText("Reg. Date  :  "+day+"/"+month+"/"+year);break;
                case 2 : passedTextView.setText("MOT Due  :  "+day+"/"+month+"/"+year);break;
                case 3 : passedTextView.setText("RFL Date  :  "+day+"/"+month+"/"+year);break;
            }
//            passedTextView.setText(passedTextView.getText().toString()+"  :  "+day+"/"+month+"/"+year);
        }
    }

    private void showToast(String str){
        Toast.makeText(MyApplication.getAppContext(),str,Toast.LENGTH_LONG).show();
    }

    public void saveDataToSingelton(){


//        regNo_EditText = (EditText)view.findViewById(R.id.regNoEditText);
//        engine_EditText = (EditText)view.findViewById(R.id.engineEditText);
//        model_EditText = (EditText)view.findViewById(R.id.modelEditText);
//        variant_EditText = (EditText)view.findViewById(R.id.variantEditText);
//        extra_EditText = (EditText)view.findViewById(R.id.extraEditText);
//        make_EditText = (EditText)view.findViewById(R.id.makeEditText);
//        mileage_EditText = (EditText)view.findViewById(R.id.mileageEditText);
//        owner_EditText = (EditText)view.findViewById(R.id.ownerEditText);
//        expectedValue_EditText = (EditText)view.findViewById(R.id.expectedValueEditText);

        if(regNo_EditText.getText().toString().equalsIgnoreCase("")){
            showToast("Please Enter Registration Number");
            return;
        }
        if(engine_EditText.getText().toString().equalsIgnoreCase("")){
            showToast("Please Enter Engine Information");
            return;
        }
        if(date1FromPicker.getText().toString().equalsIgnoreCase("Reg. Date")){
            showToast("Please Select Registration Date");
            return;
        }
        if(expectedValue_EditText.getText().toString().equalsIgnoreCase("")){
            showToast("Please Enter Expected Value");
            return;
        }

//        regNo,engine,regDate,model,variant,motDate,extra,make,rflDate,mileage,owner,expectedValue,color

        mySingelton.regNo = regNo_EditText.getText().toString();
        mySingelton.engine = engine_EditText.getText().toString();
        mySingelton.model = model_EditText.getText().toString();
        mySingelton.variant = variant_EditText.getText().toString();
        mySingelton.extra = extra_EditText.getText().toString();
        mySingelton.make = make_EditText.getText().toString();
        mySingelton.mileage = mileage_EditText.getText().toString();
        mySingelton.owner = ownerSpinnerValue;
        mySingelton.expectedValue = expectedValue_EditText.getText().toString();

        mySingelton.color = colorSpinnerDataIndex.get(colorSpinnerData.indexOf(colorSpinnerVlaue));

        String date1Selected = date1FromPicker.getText().toString();
        if(date1FromPicker.getText().toString().equalsIgnoreCase("Reg. Date")){
//            showToast("Please Select Registration Date");
        }else{
            date1Selected = date1Selected.substring(14);
            mySingelton.regDate = date1Selected;
        }


        date1Selected = date2FromPicker.getText().toString();
        if(date1Selected.length() >= 8){
            mySingelton.motDate = date1Selected.substring(12);
        }


        date1Selected = date3FromPicker.getText().toString();
        if(date1Selected.length()>=9){
            mySingelton.rflDate = date1Selected.substring(13);
        }




        my_frg1Interface.data1Entered();

    }

    public interface frg1Interface{
        public void data1Entered();
    }


}
