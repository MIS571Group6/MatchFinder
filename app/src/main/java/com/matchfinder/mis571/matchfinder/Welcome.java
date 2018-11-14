package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.adapters.WelcomeAdapter;
import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.Methods;
import com.matchfinder.mis571.matchfinder.util.DBOperator;

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

        //create objects from CursorToArrayClass
        Methods sportName = new Methods();
        Methods time = new Methods();
        Methods matchesID = new Methods();

        //fills a cursor with MatchesID, Sportname and Time of Match
        Cursor  cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERMATCHES + "'" + g.getUserID() + "'");

        //Give the welcomeAdapter (that fills information in the ListView) the information of the query (by using the method getArray from CursorToArrayClass)
        final WelcomeAdapter welcomeAdapter = new WelcomeAdapter(this, sportName.getArray(cursor,"SportName"), matchesID.getArray(cursor, "Time"), time.getArray(cursor,"MatchesID" ));
        welcomeListView.setAdapter(welcomeAdapter);


        //Event for clicking a certain item of the welcomeListView: The corresponding DetailView is being shown
        welcomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Get and pass the matchesID of the clicked item
                Intent showDetail = new Intent(getApplicationContext(), DetailView.class);
                //Calling a method in welcomeAdapter class that returns the MatchesID
                String clickedMatch = welcomeAdapter.getMatchID(i);

                //Passing the info to the detailView page
                showDetail.putExtra("com.matchfinder.mis571.matchfinder.MATCH_ID",clickedMatch);
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



}
