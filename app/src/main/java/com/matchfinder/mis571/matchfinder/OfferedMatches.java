package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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



        final OfferedMatchesAdapter offeredMatchesAdapter = new OfferedMatchesAdapter(this, sportName.getArray(cursor,"SportName"), date.getArray(cursor, "PlannedDate"), matchesID.getArray(cursor,"MatchesID"), playerCount.getArray(cursor, "PlayerCount"));
        offeredMatchesListView.setAdapter(offeredMatchesAdapter);








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
