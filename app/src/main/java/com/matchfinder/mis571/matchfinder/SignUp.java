package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        final EditText signUpFName = (EditText) findViewById(R.id.signUpFName);
        final EditText signUpLName = (EditText) findViewById(R.id.signUpLName);
        final EditText signUpNName = (EditText) findViewById(R.id.signUpNName);

        final EditText signUpMajor = (EditText) findViewById(R.id.signUpMajor);
        final EditText signUpBDate = (EditText) findViewById(R.id.signUpBDate);
        final EditText signUpPhone = (EditText) findViewById(R.id.signUpPhone);

        final EditText signUpPwA = (EditText) findViewById(R.id.signUpPwA);
        final EditText signUpPwB = (EditText) findViewById(R.id.signUpPwB);
        final EditText signUpSecQuest = (EditText) findViewById(R.id.signUpSecQuest);







        //Creating event for clicking Button signUpBtn
        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Checks if all fields are filled out and if passwords are identical
                //NEED TO ADD: NICKNAME = UNIQUE?
                //NEED TO ADD: INSERT DATA IN DB

                if (!(signUpFName.getText().toString().equals("")) && !(signUpLName.getText().toString().equals("")) && !(signUpNName.getText().toString().equals("")) && !(signUpMajor.getText().toString().equals("")) && !(signUpBDate.getText().toString().equals("")) && !(signUpFName.getText().toString().equals("")) && !(signUpPhone.getText().toString().equals("")) && !(signUpPwA.getText().toString().equals("")) && !(signUpPwB.getText().toString().equals("")) && !(signUpSecQuest.getText().toString().equals("")) && signUpPwA.getText().toString().equals(signUpPwB.getText().toString())){

                    //Link to Welcome
                    Intent startIntent = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(startIntent);


                }else if(!(signUpPwA.getText().toString().equals(signUpPwB.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"Passwords not identical", Toast.LENGTH_LONG).show();
                    signUpPwA.setText("");
                    signUpPwB.setText("");

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill out all fields", Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}
