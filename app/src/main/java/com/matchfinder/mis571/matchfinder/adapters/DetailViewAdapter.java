package com.matchfinder.mis571.matchfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.R;

public class DetailViewAdapter extends BaseAdapter {



    LayoutInflater mInflater2;

    String[] UserNames;
    String[] Skills;
    String[] UserIDs;


    public DetailViewAdapter(Context c, String[] i, String[] p, String[] d) {
        UserNames = i;
        Skills = p;
        UserIDs=d;
        mInflater2 = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return UserNames.length;
    }

    @Override
    public Object getItem(int i) {
        return UserNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = mInflater2.inflate(R.layout.detail_view_details, null);
        TextView matchTextView = (TextView) v.findViewById(R.id.userNameTextView);
        TextView timeTextView = (TextView) v.findViewById(R.id.skillTextView);
        TextView matchIDTextView = (TextView) v.findViewById(R.id.userIDTextView);

        String userName = UserNames[i];
        String skill = Skills[i];
        String userID = UserIDs[i];

        matchTextView.setText(userName);
        timeTextView.setText(skill);
        matchIDTextView.setText(userID);

        return v;
    }



    public String getUserID(int i){

        String userID = UserIDs[i];

        return userID;
    }


}