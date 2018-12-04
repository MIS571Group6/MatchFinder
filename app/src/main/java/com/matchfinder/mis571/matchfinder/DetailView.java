package com.matchfinder.mis571.matchfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_offer);



        //copy database file
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }




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
        Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_OFFERDETAILS  + "'" + matchID + "'");


        Methods matchinfo = new Methods();


        TextView detailViewSportName = (TextView) findViewById(R.id.detailViewSportName);
        TextView detailViewPlannedDate = (TextView) findViewById(R.id.detailViewPlannedDate);
        TextView detailViewPlannedTime = (TextView) findViewById(R.id.detailViewPlannedTime);
        TextView detailViewLocation = (TextView) findViewById(R.id.detailViewLocation);
        TextView detailViewCreationDate = (TextView) findViewById(R.id.detailViewCreationDate);
        TextView detailViewAvgSkill = (TextView) findViewById(R.id.detailViewAvgSkill);

        //setting first (and only) element of created string arrays as text of te textviews
        detailViewSportName.setText(matchinfo.getArray(cursor1,"sportname")[0]);
        detailViewPlannedDate.setText("Planned Date: " + matchinfo.getArray(cursor1,"PlannedDate")[0]);
        detailViewPlannedTime.setText("Planned Time: " + matchinfo.getArray(cursor1, "PlannedTime")[0]);
        detailViewLocation.setText("Location: " + matchinfo.getArray(cursor1,"Location")[0]);
        detailViewCreationDate.setText("Offer created on: " + matchinfo.getArray(cursor1, "CreationDate")[0]);


        //Finding out the average skill of all participating users and set the result as text of the textview
        Cursor cursor3 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_AVGSKILL_1 + "'" + matchID + "'" + SQLCommand.QUERY_AVGSKILL_2);



        //Because there is no mechanism that makes sure that every user has the corresponding skills, yet
        if (matchinfo.getArray(cursor3, "AverageSkill").length > 0) {

            //Finding out the clear text of the average skill
            String averageSkillDescription = matchinfo.getSkillString(matchinfo.getArray(cursor3, "AverageSkill")[0]);

            detailViewAvgSkill.setText("Average Skill: " + averageSkillDescription);
        } else{
            detailViewAvgSkill.setText("Average Skill: " + "n/a");
        }




        updateListView(matchID);
        updateBtnText(matchID);



        ListView detailListView = (ListView) findViewById(R.id.detailListView);


        //Event when clicking a certain item of the listview. The corresponding Profile will be shown
        detailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Get and pass the UserID of the clicked item
                Intent showDetail = new Intent(getApplicationContext(), Profile.class);

                Methods userinfo2 = new Methods();
                Cursor cursor5 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERS1 + "'" + matchID + "'" + SQLCommand.QUERY_USERS2 + "'" + matchID + "'");
                String clickedUser = userinfo2.getArray(cursor5, "UserID")[i];

                //Passing the info to the detailView page
                showDetail.putExtra("com.matchfinder.mis571.matchfinder.USER_ID",clickedUser);
                startActivity(showDetail);
            }
        });





        //Event when clicking the participating button
        Button detailViewParticipate = (Button) findViewById(R.id.detailViewParticipate);
        detailViewParticipate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            String inOrOut = updateBtnText(matchID);

            if (inOrOut.equals("In")){

                DBOperator.getInstance().execSQL(SQLCommand.DELETE_USERMATCH,getArgsdel(matchID));

                Toast.makeText(getApplicationContext(),"You no longer are participating", Toast.LENGTH_SHORT).show();


                } else if (inOrOut.equals("Out")){
                DBOperator.getInstance().execSQL(SQLCommand.NEW_USERMATCH, getArgs(matchID));
                Toast.makeText(getApplicationContext(),"You are participating", Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_SHORT).show();
            }

            updateListView(matchID);
            updateBtnText(matchID);

            }

        });





        //Event when clicking detailViewMatchDone button: Match will be updated as "Done"
        Button detailViewMatchDone = (Button) findViewById(R.id.detailViewMatchDone);

        detailViewMatchDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialog that warns user
                AlertDialog alertDialog = new AlertDialog.Builder(DetailView.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Watch out!");
                //Setting Dialog Message
                alertDialog.setMessage("You are about to set this match to be done!");

                //When pressing the ok button, the DB will be updated
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Update DB
                        DBOperator.getInstance().execSQL(SQLCommand.MATCH_DONE + matchID);
                        Toast.makeText(getApplicationContext(),"Match done", Toast.LENGTH_SHORT).show();

                        //Link to Welcome Page
                        Intent startIntent = new Intent(getApplicationContext(), Welcome.class);
                        startActivity(startIntent);
                    }
                });
                alertDialog.show();


            }
        });

    }






    //Method for filling a string array for inserting info in UserMatchTable
    private String[] getArgs(String matchID){
        String args[]= new String[3];

        Globals g = Globals.getInstance();

        args[0]=matchID;
        args[1]=g.getUserID().toString();
        args[2]="'" + matchID + "-" + g.getUserID().toString() + "'";

        return args;
    }


    //Method for filling a string array for deleting info in UserMatchTable
    private String[] getArgsdel (String matchID){
        String args[]= new String[1];

        Globals g = Globals.getInstance();
        args[0]="'" + matchID + "-" + g.getUserID().toString() + "'";

        return args;
    }



    //Method for updating button text
    String updateBtnText(String matchID){

        Button detailViewParticipate = (Button) findViewById(R.id.detailViewParticipate);

        Cursor cursor4 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERS1 + matchID + SQLCommand.QUERY_USERS2 + matchID);

        Globals g = Globals.getInstance();
        Methods userCheck = new Methods();
        String inOrOut;

        if (Arrays.asList(userCheck.getArray(cursor4, "UserID")).contains(g.getUserID().toString())){
            detailViewParticipate.setText("I no longer want to participate");
            inOrOut = "In";
        } else if (!Arrays.asList(userCheck.getArray(cursor4, "UserID")).contains(g.getUserID().toString())){
            detailViewParticipate.setText("I want to participate");
            inOrOut ="Out";
        } else{
            detailViewParticipate.setText("ERROR");
            inOrOut="Error";
        }

        return inOrOut;
    }




    //Method for updating the ListView
    public void updateListView(String matchID) {
    //Finding out all Users and their skill for the inspected match
    Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERS1 + "'" + matchID + "'" + SQLCommand.QUERY_USERS2 + "'" + matchID + "'");

    //Filling ListView with information on all other participating users
    ListView detailListView = (ListView) findViewById(R.id.detailListView);

    Methods userinfo = new Methods();


    //Setting up the adapter that fills the ListView
    DetailViewAdapter DetailViewAdapter = new DetailViewAdapter(this, userinfo.getArray(cursor2, "UserNickName"), userinfo.getSkillStringArray(cursor2, "SkillGroup"), userinfo.getArray(cursor2, "UserID"));
    detailListView.setAdapter(DetailViewAdapter);



    }

}
