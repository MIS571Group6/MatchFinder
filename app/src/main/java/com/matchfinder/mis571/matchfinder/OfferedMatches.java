package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.matchfinder.mis571.matchfinder.adapters.OfferedMatchesAdapter;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

public class OfferedMatches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_matches);




        //copy database file
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }



        ListView offeredMatchesListView = (ListView) findViewById(R.id.matchHistoryListView);


        updateListView();



        //Create Event for clicking certain items of the ListView and opening the corresponding DetailView
        offeredMatchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Get and pass the matchesID of the clicked item
                Intent showDetail = new Intent(getApplicationContext(), DetailView.class);
                //Calling a method in offeredMatchesAdapter class that returns the MatchesID

                Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_MATCHES);

                Methods clickHandler = new Methods();

                String clickedMatch = clickHandler.getArray(cursor2,"MatchesID")[i];

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





    //Method for updating the ListView, depending on query chosen
    private void updateListView(){
        Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_MATCHES);
        ListView offeredMatchesListView = (ListView) findViewById(R.id.matchHistoryListView);

        Methods ListViewHandler = new Methods();

        //Filling the ListView with information
        OfferedMatchesAdapter offeredMatchesAdapter = new OfferedMatchesAdapter(this, ListViewHandler.getArray(cursor1,"SportName"), ListViewHandler.getArray(cursor1, "PlannedDate"), ListViewHandler.getArray(cursor1,"MatchesID"), ListViewHandler.getArray(cursor1, "PlayerCount"));
        offeredMatchesListView.setAdapter(offeredMatchesAdapter);

    }
}
