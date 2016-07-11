package com.aw.ecgsim.view;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.aw.ecgsim.business.bloodPressure;

import java.util.ArrayList;

/**
 * Created by Andrew Rabb on 2016-07-10.
 */
public class BloodPressureFrag extends Fragment {
    ArrayList<String> results;
    ArrayAdapter adapter;
    ListView listView;
    final String TAG = "View.Main Activity";
    bloodPressure bloodPressure = new bloodPressure();
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bp_fragment_layout, container, false);
        //listView = (ListView) view.findViewById(R.id.resultslist);
        //test();
        setListeners();
        Drawable();
        return view;
    }

    /**
     * Displays the amplitude as a series of numbers in an array adapter, for easier debugging
     */
    private void test() {
        results = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            int x = bloodPressure.getAmplitude(i);
            results.add(String.valueOf(x));
            //Log.d("testing bloodPressure", String.valueOf(x));
        }
        adapter = new ArrayAdapter<>(this.getActivity(), R.layout.listview, results);
        if (!adapter.isEmpty()) {
            listView.setAdapter(adapter);
        }
    }

    private Paint paint = new Paint();

    private void init(){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1f);
    }


    private void Drawable(){
        init();
//        int width = view.findViewById(R.id.draw_box).getWidth();
//        int height = view.findViewById(R.id.draw_box).getHeight();
        int width = 100;
        int height = 100;

        width = view.getMeasuredWidth();





        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        DrawView drawView = new DrawView();




        Log.d(TAG, "width is :" + width + " - height is :" + height);
        canvas.drawLine(width/3, height/3, width /2, height /2, paint);

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
                }
                if (testing) test();
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
                }
                if (testing) test();
            }
        });
    }
}