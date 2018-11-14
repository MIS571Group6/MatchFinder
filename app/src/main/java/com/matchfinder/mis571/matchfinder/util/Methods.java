package com.matchfinder.mis571.matchfinder.util;

import android.database.Cursor;

import java.util.ArrayList;


public class Methods {


    //Converts cursors into Arrays
    public String[] getArray(Cursor cursor, String column) {

        ArrayList<String> array = new ArrayList<String>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            array.add(cursor.getString(cursor.getColumnIndex(column)));
            cursor.moveToNext();
        }
        return array.toArray(new String[array.size()]);
    }


    //Converts numbers that are representing skills into clear Text
    public String getSkillString(String i) {

        String skill = "null";

        if (i.equals("1")) {
            skill = "Beginner";
        } else if (i.equals("2")) {
            skill = "Advanced";
        } else if (i.equals("3")) {
            skill = "Semi-Professional";
        } else if (i.equals("4")) {
            skill = "Professional";
        }
        return skill;
    }




}




