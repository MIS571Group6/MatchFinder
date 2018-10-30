package com.matchfinder.mis571.matchfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //Adjusting the displayed name
        TextView textViewWelcomeName = (TextView) findViewById(R.id.textViewWelcomeName);
        Globals g = Globals.getInstance();
        String UserName = g.getUserName();
        textViewWelcomeName.setText("Welcome, " + UserName);





    }
}
