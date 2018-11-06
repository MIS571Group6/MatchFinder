package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.matchfinder.mis571.matchfinder.constant.SQLCommand;
import com.matchfinder.mis571.matchfinder.util.DBOperator;




public class SignUp extends AppCompatActivity {

    //Declaring string variables for user information
    String signUpFName ="default";
    String signUpLName="default";
    String signUpNName="default";
    String signUpMajor="default";
    String signUpGender="default";
    String signUpBDate="1900-01-01";
    String signUpPhone="default";
    String signUpPwA="default";
    String signUpPwB="default";
    String signUpSecQuest="default";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //Creating event for clicking Button signUpBtn
        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Checks if all fields are filled out and if passwords are identical
                //NEED TO ADD: NICKNAME = UNIQUE?


                //Running method for updating string variables
                getValues();



                if (!(signUpFName.equals("")) && !(signUpLName.equals("")) && !(signUpNName.equals("")) && !(signUpMajor.equals("")) && !(signUpBDate.equals("")) && !(signUpFName.equals("")) && !(signUpPhone.equals("")) && !(signUpPwA.equals("")) && !(signUpPwB.equals("")) && !(signUpSecQuest.equals("")) && signUpPwA.equals(signUpPwB)){

                    // Log for testing purposes
                    Log.d("Info","test");


                    //Checking, if UserNName unique


                    //copy database file
                    try{
                        DBOperator.copyDB(getBaseContext());
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                    //counting the number of nicknames that are identical to the user input (proceed, if chosen nickname is unique)
                    Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_NICKNAME + "'" + signUpNName + "'");

                    Integer uniqueNickNameChecker;

                    if (cursor.moveToFirst()) {
                        uniqueNickNameChecker = cursor.getInt(cursor.getColumnIndex("Count"));
                    }else{
                        uniqueNickNameChecker=100;
                    }



                    if (uniqueNickNameChecker==0) {
                        //Inserting the user input to the database
                        DBOperator.getInstance().getInstance().execSQL(SQLCommand.NEW_USER, getArgs());
                        Toast.makeText(getBaseContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();

                        //Saving the User name in global variable UserNickName
                        Globals g = Globals.getInstance();
                        g.setUserNickName(signUpNName);

                        //Link to Welcome
                        Intent startIntent = new Intent(getApplicationContext(), Welcome.class);
                        startActivity(startIntent);
                    }else{
                        Toast.makeText(getBaseContext(), "Nick Name already taken.", Toast.LENGTH_SHORT).show();
                        EditText signUpNName2 = (EditText) findViewById(R.id.signUpNName);
                        signUpNName2.setText("");
                    }


                }else if(!(signUpFName.equals("")) && !(signUpLName.equals("")) && !(signUpNName.equals("")) && !(signUpMajor.equals("")) && !(signUpBDate.equals("")) && !(signUpFName.equals("")) && !(signUpPhone.equals("")) && !(signUpPwA.equals("")) && !(signUpPwB.equals("")) && !(signUpSecQuest.equals(""))&& !(signUpPwA.equals(signUpPwB))){
                    Toast.makeText(getApplicationContext(),"Passwords not identical", Toast.LENGTH_LONG).show();

                    EditText signUpPwA2 = (EditText) findViewById(R.id.signUpPwA);
                    EditText signUpPwB2= (EditText) findViewById(R.id.signUpPwB);
                    signUpPwA2.setText("");
                    signUpPwB2.setText("");

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill out all fields", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

//Method for updating string variables based on user input
private void getValues(){

    EditText txtField_signUpFName = (EditText)findViewById(R.id.signUpFName);
    EditText txtField_signUpLName = (EditText)findViewById(R.id.signUpLName);
    EditText txtField_signUpNName = (EditText)findViewById(R.id.signUpNName);
    EditText txtField_signUpMajor = (EditText) findViewById(R.id.signUpMajor);
    EditText txtField_signUpGender= (EditText) findViewById(R.id.signUpGender);
    EditText txtField_signUpBDate = (EditText) findViewById(R.id.signUpBDate);
    EditText txtField_signUpPhone = (EditText) findViewById(R.id.signUpPhone);
    EditText txtField_signUpPwA = (EditText) findViewById(R.id.signUpPwA);
    EditText txtField_signUpPwB = (EditText) findViewById(R.id.signUpPwB);
    EditText txtField_signUpSecQuest = (EditText) findViewById(R.id.signUpSecQuest);

    signUpFName = txtField_signUpFName.getText().toString();
    signUpLName = txtField_signUpLName.getText().toString();
    signUpNName = txtField_signUpNName.getText().toString();
    signUpMajor = txtField_signUpMajor.getText().toString();
    signUpGender = txtField_signUpGender.getText().toString();
    signUpBDate=txtField_signUpBDate.getText().toString();
    signUpPhone = txtField_signUpPhone.getText().toString();
    signUpPwA = txtField_signUpPwA.getText().toString();
    signUpPwB = txtField_signUpPwB.getText().toString();
    signUpPwB = txtField_signUpPwB.getText().toString();
    signUpSecQuest = txtField_signUpSecQuest.getText().toString();
    }



//string array for inserting info in UserInfo table
    private String[] getArgs(){
        String args[]= new String[9];

        getValues();
        args[0]=signUpFName;
        args[1]=signUpLName;
        args[2]=signUpNName;
        args[3]=signUpGender;
        args[4]=signUpBDate;
        args[5]=signUpMajor;
        args[6]=signUpPhone;
        args[7]=signUpPwA;
        args[8]=signUpSecQuest;

        return args;
    }


}


