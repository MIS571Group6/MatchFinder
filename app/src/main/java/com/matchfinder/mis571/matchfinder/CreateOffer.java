package com.matchfinder.mis571.matchfinder;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

import org.w3c.dom.Text;

import java.lang.reflect.Method;

public class CreateOffer extends AppCompatActivity {

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

        return args;
    }




}
