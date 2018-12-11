package com.matchfinder.mis571.matchfinder;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.Calendar;

public class CreateOffer extends AppCompatActivity {


    private static final String Tag = "CreateOffer";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);


        Spinner SportSpinner = (Spinner) findViewById(R.id.newOfferSportsSpinner);
        Button newOfferCreateButton = (Button) findViewById(R.id.newOfferCreateButton);



        //copy database file
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }


        // Fill Spinner with all Sports that are in the DB
        Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_SPORTS);
        Methods SportList = new Methods();
        String sports[] = SportList.getArray(cursor1, "SportName");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sports);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SportSpinner.setAdapter(spinnerArrayAdapter);



        //Event when clicking Create Button
        newOfferCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Insert new Match into DB
                DBOperator.getInstance().execSQL(SQLCommand.INSERT_OFFER, getArgs1());

                //Get the MatchesID from new match
                Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_NoMatches);
                Methods matchesIDHelper = new Methods();

                String matchID = matchesIDHelper.getArray(cursor1,"count")[0];
                matchID = String.valueOf(Integer.parseInt(matchID) + 1);

                // Insert current user as first user of the match
                DBOperator.getInstance().execSQL(SQLCommand.NEW_USERMATCH,getArgs2(matchID));



                //Link to welcome
                Toast.makeText(getApplicationContext(),"Offer Created", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(startIntent);

            }
        });



        //Dialog with Datepicker when clicking TextView
        final TextView newOfferDateTextView = (TextView) findViewById(R.id.newOfferDateTextView);
        newOfferDateTextView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateOffer.this, android.R.style.Theme_DeviceDefault_Dialog, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;
            String monthString;
            String dayString;

            //Make sure that formatting is correct (e.g. 01 instead of 1)
            if (month < 10){
                 monthString = "0"+month;
            }else{
                 monthString = String.valueOf(month);
            }
            if (dayOfMonth <10){
                dayString = "0" + dayOfMonth;
            }else{
                dayString = String.valueOf(dayOfMonth);
            }
            //Set the picked date as text in textview
            String date = year + "-" + monthString + "-" + dayString;
            newOfferDateTextView.setText(date);
            }
        };




        //Dialog with TimePicker when clicking TextView
        final TextView newOfferTimeTextView = (TextView) findViewById(R.id.newOfferTimeTextView);
        newOfferTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(CreateOffer.this, android.R.style.Theme_DeviceDefault_Dialog, mTimeListener, hour, minute,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourOfDayString;
                String minuteString;

                //Check that formatting is correct
                if (hourOfDay<10){
                    hourOfDayString = "0"+hourOfDay;
                }else{
                    hourOfDayString = String.valueOf(hourOfDay);
                }

                if (minute<10){
                    minuteString = "0"+minute;
                }else{
                    minuteString = String.valueOf(minute);
                }
                //Set text of TextView
                newOfferTimeTextView.setText(hourOfDayString+":"+minuteString+":"+"00");
            }
        };



    }





    //Method for filling a string array with the user input
    private String[] getArgs1(){

        Spinner SportSpinner = (Spinner) findViewById(R.id.newOfferSportsSpinner);
        TextView newOfferNoOfPeopleTextView = (TextView) findViewById(R.id.newOfferNoOfPeopleTextView);
        TextView newOfferDateTextView = (TextView) findViewById(R.id.newOfferDateTextView);
        TextView newOfferTimeTextView = (TextView) findViewById(R.id.newOfferTimeTextView);
        TextView newOfferLocationTextView = (TextView) findViewById(R.id.newOfferLocationTextView);

        //get sportID for provided SportName
        Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_SPORTID + "'" + SportSpinner.getSelectedItem().toString() + "'");
        Methods sportIDHandler = new Methods();

        //fill array
        String args[]= new String[6];
        args[0]= sportIDHandler.getArray(cursor2, "SportID")[0];
        args[1]=newOfferNoOfPeopleTextView.getText().toString();
        args[2]= "datetime('now')";
        args[3]= newOfferDateTextView.getText().toString() + " " +  newOfferTimeTextView.getText().toString();
        args[4] = newOfferLocationTextView.getText().toString();
        args[5]="N";

        return args;
    }



    //Method for filling a string array for inserting info in UserMatchTable
    private String[] getArgs2(String matchID){

        String args[]= new String[3];

        Globals g = Globals.getInstance();

        args[0]=matchID;
        args[1]=g.getUserID().toString();
        args[2]="'" + matchID + "-" + g.getUserID().toString() + "'";

        Log.d(g.getUserID().toString(),"UserID");

        return args;
    }




}
