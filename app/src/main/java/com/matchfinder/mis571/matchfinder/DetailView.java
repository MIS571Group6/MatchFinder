package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matchfinder.mis571.matchfinder.adapters.DetailViewAdapter;
import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.Methods;
import com.matchfinder.mis571.matchfinder.util.DBOperator;

import java.util.Arrays;


public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_offer);


        //Getting MatchID from clicked Item in original ListView
        final String matchID;

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


        Methods matchinfo = new Methods();


        TextView detailViewSportName = (TextView) findViewById(R.id.detailViewSportName);
        TextView detailViewPlannedDate = (TextView) findViewById(R.id.detailViewPlannedDate);
        TextView detailViewPlannedTime = (TextView) findViewById(R.id.detailViewPlannedTime);
        TextView detailViewLocation = (TextView) findViewById(R.id.detailViewLocation);
        TextView detailViewCreationDate = (TextView) findViewById(R.id.detailViewCreationDate);
        TextView detailViewAvgSkill = (TextView) findViewById(R.id.detailViewAvgSkill);

        //setting first (and only) element of created string arrays as text of te textviews
        detailViewSportName.setText(matchinfo.getArray(cursor,"sportname")[0]);
        detailViewPlannedDate.setText("Planned Date: " + matchinfo.getArray(cursor,"PlannedDate")[0]);
        detailViewPlannedTime.setText("Planned Time: " + matchinfo.getArray(cursor, "PlannedTime")[0]);
        detailViewLocation.setText("Location: " + matchinfo.getArray(cursor,"Location")[0]);
        detailViewCreationDate.setText("Offer created on: " + matchinfo.getArray(cursor, "CreationDate")[0]);


        //Finding out the average skill of all participating users and set the result as text of the textview
        cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_AVGSKILL_1 + "'" + matchID + "'" + SQLCommand.QUERY_AVGSKILL_2);






        //Because there is no mechanism that makes sure that every user has the corresponding skills, yet
        if (matchinfo.getArray(cursor, "AverageSkill").length > 0) {

            //Finding out the clear text of the average skill
            String averageSkillDescription = matchinfo.getSkillString(matchinfo.getArray(cursor, "AverageSkill")[0]);

            detailViewAvgSkill.setText("Average Skill: " + averageSkillDescription);
        } else{
            detailViewAvgSkill.setText("Average Skill: " + "n/a");
        }







        //Finding out all Users and their skill for the inspected match
        Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERS1 + "'"+ matchID +"'"+ SQLCommand.QUERY_USERS2 + "'" + matchID + "'");


        //Filling ListView with information on all other participating users
        ListView detailListView = (ListView) findViewById(R.id.detailListView);

        Methods userinfo = new Methods();


        //Setting up the adapter that fills the ListView
        final DetailViewAdapter DetailViewAdapter = new DetailViewAdapter(this, userinfo.getArray(cursor2, "UserNickName"), userinfo.getSkillStringArray(cursor2, "SkillGroup"), userinfo.getArray(cursor2, "UserID"));
        detailListView.setAdapter(DetailViewAdapter);







        //Event when clicking a certain item of the listview. The corresponding Profile will be shown
        detailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Get and pass the matchesID of the clicked item
                Intent showDetail = new Intent(getApplicationContext(), Profile.class);
                //Calling a method in detailAdapter class that returns the UsersID
                String clickedUser = DetailViewAdapter.getUserID(i);


                //Passing the info to the detailView page
                showDetail.putExtra("com.matchfinder.mis571.matchfinder.USER_ID",clickedUser);
                startActivity(showDetail);
            }
        });









        //Signing up for or getting out of matches

        //Cursor containing information on all users that have signed up for the specific match
        Cursor cursor3 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERS1 + "'"+ matchID +"'"+ SQLCommand.QUERY_USERS2 + "'" + matchID + "'");

        Methods cursorCheck = new Methods();
        Globals g = Globals.getInstance();
        final Button detailViewParticipate = (Button) findViewById(R.id.detailViewParticipate);

        //Check if current user is in the list of all users that have signed up for the specific match and change button text correspondingly
        if (Arrays.asList(cursorCheck.getArray(cursor3, "UserID")).contains(g.getUserID().toString())){
            detailViewParticipate.setText("I no longer want to participate");
        }else {
            detailViewParticipate.setText("I want to participate");
        }




        //Toast.makeText(getApplicationContext(),cursorCheck.getArray(cursor2,"UserID")[0] , Toast.LENGTH_SHORT).show();





        //Event when clicking the button
        detailViewParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                //Method for updating the listView
                updateListVIew(matchID);

            }
        });

    }






    //Method for updating the ListView
    private void updateListVIew(String matchID){




    }


}
