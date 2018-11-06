package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matchfinder.mis571.matchfinder.constant.Globals;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //Adjusting the displayed name
        TextView textViewWelcomeName = (TextView) findViewById(R.id.textViewWelcomeName);
        Globals g = Globals.getInstance();
        String UserNickName = g.getUserNickName();
        textViewWelcomeName.setText("Welcome, " + UserNickName);


        //Event when clicking Button welcomeShowOffers
        Button welcomeShowOffers = (Button) findViewById(R.id.welcomeShowOffers);
        welcomeShowOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Linking to OfferedMatches
                Intent startIntent = new Intent(getApplicationContext(), OfferedMatches.class);
                startActivity(startIntent);

            }
        });

        //Event when clicking Button welcomeCreateOffer

        Button welcomeCreateOffer = (Button) findViewById(R.id.welcomeCreateOffer);
        welcomeCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Linking to CreateOffer
                Intent startIntent = new Intent(getApplicationContext(), CreateOffer.class);
                startActivity(startIntent);
            }
        });



    }
}
