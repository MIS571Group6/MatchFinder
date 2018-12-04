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




        //copy database file
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }




        welcomeListView = (ListView) findViewById(R.id.welcomeListView);


        updateListView();


        //Event for clicking a certain item of the welcomeListView: The corresponding DetailView is being shown
        welcomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Get and pass the matchesID of the clicked item
                Intent showDetail = new Intent(getApplicationContext(), DetailView.class);

                Methods matchInfo = new Methods();
                Cursor  cursor3 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERMATCHES + "'" + g.getUserID() + "'");
                String clickedMatch = matchInfo.getArray(cursor3, "MatchesID")[i];

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



        //Event when clicking Button welcomeToProfile
        Button welcomeToProfile = (Button) findViewById(R.id.welcomeToProfile);
        welcomeToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //Linking to profile page
                Intent showProfile = new Intent(getApplicationContext(), Profile.class);

                String userID = g.getUserID().toString();
                //passing over the current user's UserID

                showProfile.putExtra("com.matchfinder.mis571.matchfinder.USER_ID",userID);
                startActivity(showProfile);
            }
        });



        //Event when clicking Button welcomeToMatchHistory
        Button welcomeToMatchHistory = (Button) findViewById(R.id.welcomeToMatchHistory);
        welcomeToMatchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Linking to MatchHistory page
                Intent showHistory = new Intent(getApplicationContext(), MatchHistory.class);
                startActivity(showHistory);

            }
        });


    }





    private void updateListView(){
        welcomeListView = (ListView) findViewById(R.id.welcomeListView);
        //create objects from Methods class
        Methods ListViewFiller = new Methods();

        Globals g = Globals.getInstance();

        //fills a cursor with MatchesID, Sportname and Time of Match
        Cursor  cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERMATCHES + "'" + g.getUserID() + "'");

        //Give the welcomeAdapter (that fills information in the ListView) the information of the query (by using the method getArray from CursorToArrayClass)
        WelcomeAdapter welcomeAdapter = new WelcomeAdapter(this, ListViewFiller.getArray(cursor2,"SportName"), ListViewFiller.getArray(cursor2, "Time"), ListViewFiller.getArray(cursor2,"MatchesID" ));
        welcomeListView.setAdapter(welcomeAdapter);

    }




    @Override
    public void onResume(){
        super.onResume();

        updateListView();

    }

}
