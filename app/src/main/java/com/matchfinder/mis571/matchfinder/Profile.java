package com.matchfinder.mis571.matchfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matchfinder.mis571.matchfinder.adapters.WelcomeAdapter;
import com.matchfinder.mis571.matchfinder.adapters.ProfileSkillsAdapter;
import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {


    AlertDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        //copy database file
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }




        //Getting UserID from clicked Item in original ListView
        final String clickedUser;

        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                clickedUser = null;

            }else{
                clickedUser = extras.getString("com.matchfinder.mis571.matchfinder.USER_ID");
            }
        }else {
            clickedUser = (String) savedInstanceState.getSerializable("com.matchfinder.mis571.matchfinder.USER_ID");
        }






        //find the NickName, Age, Major and Phonenumber for UserID
        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_NICKNAME + clickedUser);


        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        TextView userMajorTextView = (TextView) findViewById(R.id.userMajorTextView);
        TextView userAgeTextView = (TextView) findViewById(R.id.userAgeTextView);
        TextView userPhoneTextView =(TextView) findViewById(R.id.userPhoneTextView);


        Methods userInfo = new Methods();

        userNameTextView.setText(userInfo.getArray(cursor, "NickName")[0]);
        userMajorTextView.setText("Major: " + userInfo.getArray(cursor, "Major")[0]);
        userAgeTextView.setText("Age: " + userInfo.getArray(cursor, "Age")[0]);
        userPhoneTextView.setText("Phonenumber: "+ userInfo.getArray(cursor, "Phone")[0]);




        //Fill ListView with Information on skills of the user
        updateListView(clickedUser);
        ListView SportsListView = (ListView) findViewById(R.id.SportsListView);




        //adapt layout if user is inspecting own profile or some other user's profile
        TextView profileUpdateskillsTextView = (TextView) findViewById(R.id.profileUpdateskillsTextView);
        final Globals g = Globals.getInstance();

        if (clickedUser.equals(g.getUserID().toString())){

            profileUpdateskillsTextView.setText("click on skills to update them.");

        } else{
            profileUpdateskillsTextView.setText("");
        }




        //Event when listview is being clicked (for updating skills)
        SportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {


                if (clickedUser.equals(g.getUserID().toString())) {
                    Methods skillInfo = new Methods();
                    Cursor  cursor3 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERSKILLS + "'" + clickedUser + "'");
                    String clickedSkill = skillInfo.getArray(cursor3, "SportID")[i];

                    //Show Dialog and update database correspondingly
                    showDialog(clickedSkill, clickedUser);

                }

            }
        });



    }





    //Method to update the listview
    private void updateListView(String clickedUser){

        ListView SportsListView = (ListView) findViewById(R.id.SportsListView);
        //Query to gather information needed
        Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERSKILLS + "'" + clickedUser + "'");
        Methods skill= new Methods();
        //Setting up adapter for filling ListView
        ProfileSkillsAdapter profileSkillsAdapter = new ProfileSkillsAdapter(this, skill.getArray(cursor2, "SportName"), skill.getSkillStringArray(cursor2, "SkillGroup"));
        SportsListView.setAdapter(profileSkillsAdapter);


    }


    //method for creating a dialog to update skills, also the database is being updated if necessary
    private void showDialog(final String clickedSkill, final String clickedUser){

        //dialog is being opened
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        //Choices of dialog
        final CharSequence[] skills = {"Beginner", "Advanced", "Semi-Professional", "Professional"};


        myBuilder.setTitle ("Update Skill").setItems(skills, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selectedSkill = null;
                Integer skillGroup;
                selectedSkill = skills[which].toString();



                //UPDATE DATABASE
                Globals g = Globals.getInstance();

                //Convert String to numeric SkillGroup
                if(selectedSkill.equals("Beginner")){
                    skillGroup = 1;
                }else if(selectedSkill.equals("Advanced")){
                    skillGroup = 2;
                } else if (selectedSkill.equals("Semi-Professional")){
                    skillGroup = 3;
                }else if (selectedSkill.equals("Professional")){
                    skillGroup = 4;
                }else{
                    skillGroup = 1;}

                String args[]= new String[3];
                args[0]= skillGroup.toString();
                args[1]=g.getUserID().toString();
                args[2] = clickedSkill;

                //SQL Command for updating the db
                DBOperator.getInstance().execSQL(SQLCommand.UPDATE_SKILL, args);

                Toast.makeText(Profile.this, "Skill updated", Toast.LENGTH_SHORT).show();
                updateListView(clickedUser);

            }
        });

        myDialog = myBuilder.create();
        myDialog.show();

    }


}
