package com.aw.ecgsim.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.aw.ecgsim.R;
import com.aw.ecgsim.business.bloodPressure;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> results;
    ArrayAdapter adapter;
    ListView listView;
    final String TAG = "View.Main Activity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.resultslist);

        test();
        setListeners();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    bloodPressure bloodPressure = new bloodPressure();

    private void test() {
        results = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            int x = bloodPressure.getAmplitude(i);
            results.add(String.valueOf(x));
            //Log.d("testing bloodPressure", String.valueOf(x));

        }
        adapter = new ArrayAdapter<>(this, R.layout.listview, results);
        if (!adapter.isEmpty()) {
            listView.setAdapter(adapter);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.respiration:
                //// TODO: 2016-06-24
                return true;
            case R.id.bloodpressure:
                //// TODO: 2016-06-24
                return true;
            case R.id.heartbeat:
                // TODO: 2016-06-24
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setListeners() {


        final EditText systolicEdit = (EditText) findViewById(R.id.systolicVal);
        final EditText diastolicEdit = (EditText) findViewById(R.id.DiastolVal);
        final EditText heartRate = (EditText) findViewById(R.id.heartRateVal);

        systolicEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

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
                test();
            }
        });
        diastolicEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!bloodPressure.setDiastolic(Double.parseDouble(diastolicEdit.getText().toString()))) {
                        diastolicEdit.setError("field must be >0 and < systolic");
                    }

                } catch (NumberFormatException e) {
                    bloodPressure.setDiastolic(80);
                }
                test();
            }
        });

        heartRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    bloodPressure.setHeartRate(Double.parseDouble(heartRate.getText().toString()));

                } catch (NumberFormatException e) {
                    bloodPressure.setHeartRate(80);
                }
                test();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.aw.ecgsim.view/http/host/path")
        );
        //AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.aw.ecgsim.view/http/host/path")
        );
        //AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
