package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity {

    ListView welcomeListView;
    Globals g = Globals.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //Adjusting the displayed name
        TextView textViewWelcomeName = (TextView) findViewById(R.id.textViewWelcomeName);

        String UserNickName = g.getUserNickName();
        textViewWelcomeName.setText("Welcome, " + UserNickName);


        welcomeListView = (ListView) findViewById(R.id.welcomeListView);




        //Give the welcomeAdapter (that fills information in the ListView) the information of the query (by using the method getArrays)
        WelcomeAdapter welcomeAdapter = new WelcomeAdapter(this, getArrays("sportNames"),   getArrays("times"), getArrays("matchesIDs"));
        welcomeListView.setAdapter(welcomeAdapter);



        //Event for clicking a certain item of the welcomeListView: The corresponding DetailView is being shown
        welcomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                Intent showDetail = new Intent(getApplicationContext(), DetailView.class);
                showDetail.putExtra("com.matchfinder.mis571.matchfinder.ITEM_INDEX",i);
                startActivity(showDetail);
            }
        });




        //Event when clicking Button welcomeShowOffers
        Button welcomeShowOffers = (Button) findViewById(R.id.welcomeShowOffers);
        welcomeShowOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Linking to OfferedMatches
                Intent startIntent = new Intent(getApplicationContext(), OfferedMatches.class);
                startActivity(startIntent);

            }
        });

        //Event when clicking Button welcomeCreateOffer

        Button welcomeCreateOffer = (Button) findViewById(R.id.welcomeCreateOffer);
        welcomeCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Linking to CreateOffer
                Intent startIntent = new Intent(getApplicationContext(), CreateOffer.class);
                startActivity(startIntent);
            }
        });

    }








    //method to fill an string arrays with the results of the query
    private String[] getArrays(String kind){


        //fills a cursor with MatchesID, Sportname and Time of Match
       Cursor  cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERMATCHES + "'" + 10 + "'");


       if (kind == "sportNames") {
           //cursor info is filled in matches array
           cursor.moveToFirst();
           ArrayList<String> sportNames = new ArrayList<String>();

           while (!cursor.isAfterLast()) {
               sportNames.add(cursor.getString(cursor.getColumnIndex("SportName")));
               cursor.moveToNext();
           }
           return sportNames.toArray(new String[sportNames.size()]);


       }else if(kind == "times"){
           //cursor info is filled in matches array
           cursor.moveToFirst();
           ArrayList<String> times = new ArrayList<String>();

           while (!cursor.isAfterLast()) {
               times.add(cursor.getString(cursor.getColumnIndex("Time")));
               cursor.moveToNext();
           }
           return times.toArray(new String[times.size()]);


       }else if(kind=="matchesIDs"){
           //cursor info is filled in matches array
           cursor.moveToFirst();
           ArrayList<String> matchesIDs = new ArrayList<String>();

           while (!cursor.isAfterLast()) {
               matchesIDs.add(cursor.getString(cursor.getColumnIndex("MatchesID")));
               cursor.moveToNext();
           }
           return matchesIDs.toArray(new String[matchesIDs.size()]);

       }else{

           return null;
       }


    }

}
