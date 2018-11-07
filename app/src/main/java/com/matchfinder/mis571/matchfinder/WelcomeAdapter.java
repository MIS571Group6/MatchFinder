package com.matchfinder.mis571.matchfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.R;

public class WelcomeAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    String[] sportNames;
    String[] times;
    String[] matchesIDs;


    public WelcomeAdapter(Context c, String[] i, String[] p, String[] d) {
        sportNames = i;
        times = p;
        matchesIDs=d;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return sportNames.length;
    }

    @Override
    public Object getItem(int i) {
        return sportNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.welcomelistview_detail, null);
        TextView matchTextView = (TextView) v.findViewById(R.id.matchTextView);
        TextView timeTextView = (TextView) v.findViewById(R.id.timeTextView);
        TextView matchIDTextView = (TextView) v.findViewById(R.id.matchIDTextView);

        String sportName = sportNames[i];
        String time = times[i];
        String matchesID = matchesIDs[i];

        matchTextView.setText(sportName);
        timeTextView.setText(time);
        matchIDTextView.setText(matchesID);

        return v;
    }


}
