package com.matchfinder.mis571.matchfinder;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

                String UserNameCarrier = userNameEditText.getText().toString();


                //Saving the User name in global variable UserName
                Globals g = Globals.getInstance();
                g.setUserName(UserNameCarrier);




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
                if (cursor.moveToFirst()) {
                    passwordChecker = cursor.getString(cursor.getColumnIndex("UserPassword"));
                }else{
                    passwordChecker="EMPTY";
                }

                //Toast indicates if pw is correct or not
                if(passwordChecker.equals(passwordEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Password correct", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Password or Nick Name not correct", Toast.LENGTH_LONG).show();
                }



            }


        });


    }
}
