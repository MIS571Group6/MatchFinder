package com.matchfinder.mis571.matchfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.R;

import org.w3c.dom.Text;

public class OfferedMatchesAdapter extends BaseAdapter {

    LayoutInflater mInflater3;

    String[] sportNames;
    String[] dates;
    String[] matchesIDs;
    String[] playerCounts;


    public OfferedMatchesAdapter(Context c, String[] i, String[] p, String[] d, String[] e) {
        sportNames = i;
        dates = p;
        matchesIDs=d;
        playerCounts=e;
        mInflater3 = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        View v = mInflater3.inflate(R.layout.offered_matches_details, null);
        TextView sportNameTextView = (TextView) v.findViewById(R.id.sportNameTextView);
        TextView dateTextView = (TextView) v.findViewById(R.id.plannedDateTextView);
        TextView matchIDTextView = (TextView) v.findViewById(R.id.matchIDTextView);
        TextView playerCountTextView = (TextView) v.findViewById(R.id.playerCountTextView);


        String sportName = sportNames[i];
        String date = dates[i];
        String matchesID = matchesIDs[i];
        String playerCount = playerCounts[i];

        sportNameTextView.setText(sportName);
        dateTextView.setText(date);
        matchIDTextView.setText(matchesID);
        playerCountTextView.setText("Users needed: "+ playerCount);     //THS RATHER BE PLACES LEFT

        return v;
    }



    public String getMatchID(int i){

        String MatchID = matchesIDs[i];

        return MatchID;
    }
}
