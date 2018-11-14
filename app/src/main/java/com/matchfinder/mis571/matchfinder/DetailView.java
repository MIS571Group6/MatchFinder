package com.matchfinder.mis571.matchfinder;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.adapters.DetailViewAdapter;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.Methods;
import com.matchfinder.mis571.matchfinder.util.DBOperator;


public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_offer);


        //Getting MatchID from clicked Item in original ListView
        String matchID;

        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                matchID = null;

            }else{
                matchID = extras.getString("com.matchfinder.mis571.matchfinder.MATCH_ID");
            }
        }else {
            matchID = (String) savedInstanceState.getSerializable("com.matchfinder.mis571.matchfinder.MATCH_ID");
        }



        //query for finding out detail information for the matchID
        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_OFFERDETAILS  + "'" + matchID + "'");

        //Create Arrays from cursor to save lines of codes
        Methods sportName = new Methods();
        Methods plannedDate = new Methods();
        Methods plannedTime = new Methods();
        Methods location = new Methods();
        Methods creationDate = new Methods();
        Methods avgSkill = new Methods();

        TextView detailViewSportName = (TextView) findViewById(R.id.detailViewSportName);
        TextView detailViewPlannedDate = (TextView) findViewById(R.id.detailViewPlannedDate);
        TextView detailViewPlannedTime = (TextView) findViewById(R.id.detailViewPlannedTime);
        TextView detailViewLocation = (TextView) findViewById(R.id.detailViewLocation);
        TextView detailViewCreationDate = (TextView) findViewById(R.id.detailViewCreationDate);
        TextView detailViewAvgSkill = (TextView) findViewById(R.id.detailViewAvgSkill);

        //setting first (and only) element of created string arrays as text of te textviews
        detailViewSportName.setText(sportName.getArray(cursor,"sportname")[0]);
        detailViewPlannedDate.setText("Planned Date: " + plannedDate.getArray(cursor,"PlannedDate")[0]);
        detailViewPlannedTime.setText("Planned Time: " + plannedTime.getArray(cursor, "PlannedTime")[0]);
        detailViewLocation.setText("Location: " + location.getArray(cursor,"Location")[0]);
        detailViewCreationDate.setText("Offer created on: " + creationDate.getArray(cursor, "CreationDate")[0]);


        //Finding out the average skill of all participating users and set the result as text of the textview
        cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_AVGSKILL_1 + "'" + matchID + "'" + SQLCommand.QUERY_AVGSKILL_2);



        //Because there is no mechanism that makes sure that every user has the corresponding skills, yet
        if (avgSkill.getArray(cursor, "AverageSkill").length > 0) {

            //Finding out the clear text of the average skill
            String averageSkillDescription = avgSkill.getSkillString(avgSkill.getArray(cursor, "AverageSkill")[0]);

            detailViewAvgSkill.setText("Average Skill: " + averageSkillDescription);
        } else{
            detailViewAvgSkill.setText("Average Skill: " + "n/a");
        }



        //Finding out all Users and their skill for the inspected match
        cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERS + "'" + matchID + "'");  //QUERY NOT WORKING


        //Filling ListView with information on all other participating users
        ListView detailListView = (ListView) findViewById(R.id.detailListView);

        Methods UserName = new Methods();
        Methods UserSkill = new Methods();
        Methods UserID = new Methods();

        //Setting up the adapter that fills the ListView
        final DetailViewAdapter DetailViewAdapter = new DetailViewAdapter(this, UserName.getArray(cursor, "NickName"), UserSkill.getArray(cursor, "Skill"), UserID.getArray(cursor, "UserID"));
        detailListView.setAdapter(DetailViewAdapter);



        //Event when clicking a certain item of the listview










        //CHANGING BUTTON "I WANT TO PARTICIPATE" TO "I NO LONGER WANT TO PARTICIPATE" WHEN ALREADY IN




    }
}
