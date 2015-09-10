package vehicleappraisal.com.vehicleappraisal.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;

import vehicleappraisal.com.vehicleappraisal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private Spinner serviceHistorySpinner,ServicePackageSpinner,MantainSpinner;
    private ArrayList<String> serviecHistorySpinnerData,ServicePackageSpinnerData,MantainSpinnerData;

    private SwitchButton vanImportedSwitch,V5CSwitch,KeySetSwitch,WarrantySwitch,AccidentSwitch;

    private String spinnerValue1,spinnerValue2,spinnerValue3;
    private boolean swit1,swit2,swit3,swit4,swit5,swit6;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 getInstance(){
        return (new Fragment2());
    }


    public void setserviecHistorySpinnerData(ArrayList<String> list){
        this.serviecHistorySpinnerData = list;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, serviecHistorySpinnerData);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        serviceHistorySpinner.setAdapter(adapter);
    }

    public void setMantainSpinnerData(ArrayList<String> list){
        this.MantainSpinnerData = list;
        ArrayAdapter<String> MantainSpinner_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, MantainSpinnerData);

        MantainSpinner_adapter.setDropDownViewResource(R.layout.spinner_item);

        MantainSpinner.setAdapter(MantainSpinner_adapter);
    }

    public void setServicePackageSpinnerData(ArrayList<String> list){
        this.ServicePackageSpinnerData = list;
        ArrayAdapter<String> ServicePackageSpinner_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, ServicePackageSpinnerData);

        ServicePackageSpinner_adapter.setDropDownViewResource(R.layout.spinner_item);

        ServicePackageSpinner.setAdapter(ServicePackageSpinner_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        swit1 = swit2 = swit3 = swit4 = swit5 = swit6 = false;

        spinnerValue1 = spinnerValue2 = spinnerValue1 = "";

        serviceHistorySpinner = (Spinner)view.findViewById(R.id.ServiceHistory_spinner_info);

        serviecHistorySpinnerData = new ArrayList<String>();
        serviecHistorySpinnerData.add("Red");
        serviecHistorySpinnerData.add("Green");
        serviecHistorySpinnerData.add("Blue");
        serviecHistorySpinnerData.add("Black");
        serviecHistorySpinnerData.add("Yellow");
        serviecHistorySpinnerData.add("White");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, serviecHistorySpinnerData);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        serviceHistorySpinner.setAdapter(adapter);

        serviceHistorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ServicePackageSpinner = (Spinner)view.findViewById(R.id.ServicePackage_spinner_info);

        ServicePackageSpinnerData = new ArrayList<String>();
        ServicePackageSpinnerData.add("Red");
        ServicePackageSpinnerData.add("Green");
        ServicePackageSpinnerData.add("Blue");
        ServicePackageSpinnerData.add("Black");
        ServicePackageSpinnerData.add("Yellow");
        ServicePackageSpinnerData.add("White");

        ArrayAdapter<String> ServicePackageSpinner_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, ServicePackageSpinnerData);

        ServicePackageSpinner_adapter.setDropDownViewResource(R.layout.spinner_item);

        ServicePackageSpinner.setAdapter(ServicePackageSpinner_adapter);

        ServicePackageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        MantainSpinner = (Spinner)view.findViewById(R.id.Mantain_spinner_info);

        MantainSpinnerData = new ArrayList<String>();
        MantainSpinnerData.add("Red");
        MantainSpinnerData.add("Green");
        MantainSpinnerData.add("Blue");
        MantainSpinnerData.add("Black");
        MantainSpinnerData.add("Yellow");
        MantainSpinnerData.add("White");

        ArrayAdapter<String> MantainSpinner_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, MantainSpinnerData);

        MantainSpinner_adapter.setDropDownViewResource(R.layout.spinner_item);

        MantainSpinner.setAdapter(MantainSpinner_adapter);

        MantainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue3 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        vanImportedSwitch = (SwitchButton)view.findViewById(R.id.vanImportedSwitch);
        vanImportedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swit1 = isChecked;
            }
        });


        V5CSwitch = (SwitchButton)view.findViewById(R.id.V5CSwitch);
        V5CSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swit2 = isChecked;
            }
        });

        KeySetSwitch = (SwitchButton)view.findViewById(R.id.KeySetSwitch);
        KeySetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swit3 = isChecked;
            }
        });

        WarrantySwitch = (SwitchButton)view.findViewById(R.id.WarrantySwitch);
        WarrantySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swit4 = isChecked;
            }
        });
        AccidentSwitch = (SwitchButton)view.findViewById(R.id.AccidentSwitch);
        AccidentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swit5 = isChecked;
            }
        });
        return view;

    }


}
