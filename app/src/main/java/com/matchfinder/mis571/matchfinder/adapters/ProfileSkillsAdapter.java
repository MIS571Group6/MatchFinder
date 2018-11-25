package com.matchfinder.mis571.matchfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.R;

public class ProfileSkillsAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    String[] sportNames;
    String[] skillGroups;



    public ProfileSkillsAdapter(Context c, String[] i, String[] d) {
        sportNames = i;
        skillGroups=d;
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

        View v = mInflater.inflate(R.layout.profile_details, null);
        TextView profileSport = (TextView) v.findViewById(R.id.profileSport);
        TextView profileSkillGroup = (TextView) v.findViewById(R.id.profileSkillGroup);

        String sportName = sportNames[i];
        String skillGroup = skillGroups[i];

        profileSport.setText(sportName);
        profileSkillGroup.setText(skillGroup);

        return v;
    }




}
