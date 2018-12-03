package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.matchfinder.mis571.matchfinder.constant.Globals;
import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;

public class LogIn extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        //Finding the LogIn button
        Button logInBtn = (Button) findViewById(R.id.logInBtn);

        //Creating event for clicking the Login button
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userNameEditText = (EditText) findViewById(R.id.UserNameEditText);
                EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

                //String UserNameCarrier holds the Nickname from user input
                String UserNameCarrier = userNameEditText.getText().toString();


                //copy database file
                try{
                    DBOperator.copyDB(getBaseContext());
                }catch(Exception e){
                    e.printStackTrace();
                }


                //find the corresponding password for the provided user name
                Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_NAME + "'" + UserNameCarrier + "'");

                //passwordChecker holds the password for the provided user name
                String passwordChecker;

                //The value of the found password is assigned to passwordChecker
                if (cursor.moveToFirst()) {
                    passwordChecker = cursor.getString(cursor.getColumnIndex("UserPassword"));
                }else{
                    passwordChecker="EMPTY";
                }

                //variable holds the UserID for the given Nick name
                Integer userIDCarrier;

                //find the corresponding UserID for the provided nick name
                cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_USERID + "'" + UserNameCarrier + "'");
                if (cursor.moveToFirst()) {
                     userIDCarrier = cursor.getInt(cursor.getColumnIndex("UserID"));
                }else{
                    userIDCarrier=0;
                }

                //Check if pw is correct or not. If correct, save nickname and userID as global variables and link to welcome page
                if(passwordChecker.equals(passwordEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Password correct", Toast.LENGTH_SHORT).show();

                    //Saving the User name in global variable
                    Globals g = Globals.getInstance();
                    g.setUserNickName(UserNameCarrier);

                    //Saving the UserID in global variable
                    g.setUserID(userIDCarrier);

                    //Opening new page
                    Intent startIntent = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(startIntent);

                }else {
                    Toast.makeText(getApplicationContext(),"Password or Nick Name not correct", Toast.LENGTH_SHORT).show();
                    passwordEditText.setText("");
                }
            }
        });

        //Event for clicking button for creating new account
        Button createAccBtn = (Button) findViewById(R.id.createAccBtn);
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(startIntent);
            }
        });

        //Event for clicking button for restoring password
        Button forgotPwBtn = (Button) findViewById(R.id.forgotPwBtn);
        forgotPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), ForgotPW.class);
                startActivity(startIntent);
            }
        });
        }
    }


