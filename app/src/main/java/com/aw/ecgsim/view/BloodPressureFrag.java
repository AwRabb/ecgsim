package com.aw.ecgsim.view;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.aw.ecgsim.R;
import com.aw.ecgsim.business.BloodPressure;

import java.util.ArrayList;

/**
 * Created by Andrew Rabb on 2016-07-10.
 */
public class BloodPressureFrag extends Fragment {
    ArrayAdapter adapter;
    ListView listView;
    final String TAG = "View.Main Activity";
    BloodPressure bloodPressure = new BloodPressure();
    View view;
    BPPanel bpPanel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bp_fragment_layout, container, false);
        //listView = (ListView) view.findViewById(R.id.resultslist);
        //test();
        setListeners();

        bpPanel = (BPPanel) view.findViewById(R.id.BloodPressureDrawBox);
        bpPanel.setBloodPressure(bloodPressure);
        return view;
    }


    /**
     * Displays the amplitude as a series of numbers in an array adapter, for easier debugging
     */
    private void test() {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            int x = bloodPressure.getAmplitude(i);
            results.add(String.valueOf(x));
            //Log.d("testing BloodPressure", String.valueOf(bitmap));
        }
        adapter = new ArrayAdapter<>(this.getActivity(), R.layout.listview, results);
        if (!adapter.isEmpty()) {
            listView.setAdapter(adapter);
        }
    }

    private void setListeners() {
        final boolean testing = false;
        final EditText systolicEdit = (EditText) view.findViewById(R.id.systolicVal);
        final EditText diastolicEdit = (EditText) view.findViewById(R.id.DiastolVal);
        final EditText heartRate = (EditText) view.findViewById(R.id.heartRateVal);
        systolicEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!bloodPressure.setSystolic(Double.parseDouble(systolicEdit.getText().toString()))) {
                        systolicEdit.setError("field must be >0 and >diastolic");
                    }

                } catch (NumberFormatException e) {
                    Log.d(TAG, " error occurred, setting default systolic (80)");
                    bloodPressure.setSystolic(80);
                }
                if (testing) test();
                bpPanel.setBloodPressure(bloodPressure);
            }
        });
        diastolicEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!bloodPressure.setDiastolic(Double.parseDouble(diastolicEdit.getText().toString()))) {
                        diastolicEdit.setError("field must be >0 and < systolic");
                    }

                } catch (NumberFormatException e) {
                    bloodPressure.setDiastolic(80);
                    bpPanel.setBloodPressure(bloodPressure);
                }
                if (testing) test();
                bpPanel.setBloodPressure(bloodPressure);
            }
        });
        heartRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    bloodPressure.setHeartRate(Double.parseDouble(heartRate.getText().toString()));

                } catch (NumberFormatException e) {
                    bloodPressure.setHeartRate(80);
                    bpPanel.setBloodPressure(bloodPressure);
                }
                if (testing) test();
                bpPanel.setBloodPressure(bloodPressure);
            }
        });
    }
}
