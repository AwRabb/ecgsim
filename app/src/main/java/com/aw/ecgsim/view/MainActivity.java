package com.aw.ecgsim.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aw.ecgsim.R;
import com.aw.ecgsim.business.bloodPressure;
import com.aw.ecgsim.business.Line;

import java.io.Console;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<>(this, R.layout.listview, results);
        listView = (ListView) findViewById(R.id.resultslist);

        test();
    }
    ArrayList<Integer> results;
    ArrayAdapter adapter;
    ListView listView;



    Line bloodPressure = new bloodPressure();
    private void test(){
        results = new ArrayList<>();
        for (int i = 0; i < 120; i++){
            int x = bloodPressure.getAmplitude(i);
            results.add(x);
            //Log.d("testing bloodPressure", String.valueOf(x));

        }
        listView.setAdapter(adapter);




    }



}
