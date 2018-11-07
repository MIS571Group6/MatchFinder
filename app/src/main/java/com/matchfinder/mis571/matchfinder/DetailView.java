package com.matchfinder.mis571.matchfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;





public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_offer);


        TextView detailViewTitle = (TextView) findViewById(R.id.detailViewTitle);


        Intent in = getIntent();
        int index = in.getIntExtra("com.matchfinder.mis571.matchfinder.ITEM_INDEX", -1);




        if (index >-1){






        }
    }


    private int setUpPage(int index) {
        switch(index){
            case 0: return 0;
            case 1: return 1;
            case 2: return 2;
            case 3: return 3;

            default: return 0;
        }
    }


}
