package com.matchfinder.mis571.matchfinder;

import android.app.Application;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

import java.lang.reflect.Method;

public class CreateOffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);


        Spinner spinner = (Spinner) findViewById(R.id.newOfferSportsSpinner);



        // Fill Spinner with all Sports that are in the DB
        Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_SPORTS);
        Methods SportList = new Methods();
        String colors[] = SportList.getArray(cursor1, "SportName");



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

















    }
}
