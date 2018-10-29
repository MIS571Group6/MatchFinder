package com.matchfinder.mis571.matchfinder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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
                EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
                EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

                String UserNameCarrier = emailEditText.getText().toString();

                //Saving the User name in global variable UserName
                Globals g = Globals.getInstance();
                g.setUserName(UserNameCarrier);




                //TESTING
                TextView textView1 = (TextView) findViewById(R.id.textView1);
                textView1.setText(g.getUserName());


            }
        });


    }
}
