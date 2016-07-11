package com.aw.ecgsim.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aw.ecgsim.R;

/**
 * Created by Andrew Rabb on 2016-07-10.
 */
public class HeartBeatFrag extends Fragment {
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.heartbeat_frag_layout, container, false);
        //listView = (ListView) view.findViewById(R.id.resultslist);
        //test();
        //setListeners();
        return view;
    }
}
