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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import vehicleappraisal.com.vehicleappraisal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

//    private MFCalendarView myCalender1;
    FragmentManager my_FragmentManager;
    private Button date1,date2,date3;
    private TextView date1FromPicker,date2FromPicker,date3FromPicker;

    private Spinner myColorSpinner;

    private ArrayList<String> colorSpinnerData;

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
//        myCalender1 = (MFCalendarView)view.findViewById(R.id.mFCalendarView);
        date1 = (Button)view.findViewById(R.id.selectDate1);
        date1FromPicker = (TextView)view.findViewById(R.id.date1FromPicker);

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date1FromPicker);
                newFragment.show(my_FragmentManager, "datePicker");
            }
        });

        date2 = (Button)view.findViewById(R.id.selectDate2);
        date2FromPicker = (TextView)view.findViewById(R.id.MotDueTextFeild);

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date2FromPicker);
                newFragment.show(my_FragmentManager, "datePicker");
            }
        });

        date3 = (Button)view.findViewById(R.id.selectDate3);
        date3FromPicker = (TextView)view.findViewById(R.id.RefDateTextFeild);

        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date3FromPicker);
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

        return view;


    }

    public void setColors(ArrayList<String> list){
        this.colorSpinnerData = list;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, colorSpinnerData);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        myColorSpinner.setAdapter(adapter);

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private TextView passedTextView;

        @SuppressLint("ValidFragment")
        public DatePickerFragment(TextView someTextView){
            this.passedTextView = someTextView;
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
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            passedTextView.setText(passedTextView.getText().toString()+"  :  "+day+"/"+month+"/"+year);
        }
    }


}
