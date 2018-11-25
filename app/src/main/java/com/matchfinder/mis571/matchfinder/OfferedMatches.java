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

import com.matchfinder.mis571.matchfinder.adapters.OfferedMatchesAdapter;
import com.matchfinder.mis571.matchfinder.adapters.WelcomeAdapter;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

public class OfferedMatches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_matches);



        ListView offeredMatchesListView = (ListView) findViewById(R.id.offeredMatchesListView);



        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_MATCHES);


        Methods sportName = new Methods();
        Methods date = new Methods();
        Methods playerCount = new Methods();
        Methods matchesID = new Methods();


        //Filling the ListView with information
        final OfferedMatchesAdapter offeredMatchesAdapter = new OfferedMatchesAdapter(this, sportName.getArray(cursor,"SportName"), date.getArray(cursor, "PlannedDate"), matchesID.getArray(cursor,"MatchesID"), playerCount.getArray(cursor, "PlayerCount"));
        offeredMatchesListView.setAdapter(offeredMatchesAdapter);






        //Create Event for clicking certain items of the ListView and opening the corresponding DetailView
        offeredMatchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Get and pass the matchesID of the clicked item
                Intent showDetail = new Intent(getApplicationContext(), DetailView.class);
                //Calling a method in offeredMatchesAdapter class that returns the MatchesID
                String clickedMatch = offeredMatchesAdapter.getMatchID(i);

                //Passing the info to the detailView page
                showDetail.putExtra("com.matchfinder.mis571.matchfinder.MATCH_ID",clickedMatch);
                startActivity(showDetail);
            }
        });




        //Create Event for clicking button offeredMatchesCreateNewOffer
        Button offeredMatchesCreateNewOffer = (Button) findViewById(R.id.offeredMatchesCreateNewOffer);
        offeredMatchesCreateNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Link to CreateOffer
                Intent startIntent = new Intent(getApplicationContext(), CreateOffer.class);
                startActivity(startIntent);

            }
        });




    }
}
