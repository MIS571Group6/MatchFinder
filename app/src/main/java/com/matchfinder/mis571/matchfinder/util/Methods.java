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


    //Converts a cursor that contains numeric skill information to clear text skill information
    public String[] getSkillStringArray(Cursor cursor, String column){
        ArrayList<String> array = new ArrayList<String>();

        String numericSkill;
        String clearTextSkill = "null";

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            numericSkill = cursor.getString(cursor.getColumnIndex(column));

            if (numericSkill.equals("1")) {
                clearTextSkill = "Beginner";
            } else if (numericSkill.equals("2")) {
                clearTextSkill = "Advanced";
            } else if (numericSkill.equals("3")) {
                clearTextSkill = "Semi-Professional";
            } else if (numericSkill.equals("4")) {
                clearTextSkill = "Professional";
            }

            array.add(clearTextSkill);
            cursor.moveToNext();
        }
        return array.toArray(new String[array.size()]);
    }



}




