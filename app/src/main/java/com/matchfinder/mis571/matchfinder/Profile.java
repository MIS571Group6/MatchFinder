package com.matchfinder.mis571.matchfinder;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.adapters.WelcomeAdapter;
import com.matchfinder.mis571.matchfinder.adapters.ProfileSkillsAdapter;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;
import com.matchfinder.mis571.matchfinder.util.Methods;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        //Getting UserID from clicked Item in original ListView
        String clickedUser;

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
        //MAYBE ADD GENDER

        Methods userInfo = new Methods();

        userNameTextView.setText(userInfo.getArray(cursor, "NickName")[0]);
        userMajorTextView.setText("Major: " + userInfo.getArray(cursor, "Major")[0]);
        userAgeTextView.setText("Age: " + userInfo.getArray(cursor, "Age")[0]);
        userPhoneTextView.setText("Phonenumber: "+ userInfo.getArray(cursor, "Phone")[0]);




        //Fill ListView with Information on skills of the user

        ListView SportsListView = (ListView) findViewById(R.id.SportsListView);


        Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERSKILLS + "'" + clickedUser + "'");

        Methods skill= new Methods();

        final ProfileSkillsAdapter profileSkillsAdapter = new ProfileSkillsAdapter(this, skill.getArray(cursor2, "SportName"), skill.getSkillStringArray(cursor2, "SkillGroup"));

        SportsListView.setAdapter(profileSkillsAdapter);


    }
}
