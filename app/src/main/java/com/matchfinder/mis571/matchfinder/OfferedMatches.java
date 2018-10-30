package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OfferedMatches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_matches);



        //Create Event for clicking button offeredMatchesCreateNewOffer
        Button offeredMatchesCreateNewOffer = (Button) findViewById(R.id.offeredMatchesCreateNewOffer);
        offeredMatchesCreateNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Link to CreateOffer
                Intent startIntent = new Intent(getApplicationContext(), CreateOffer.class);
                startActivity(startIntent);

            }
        });






    }
}
