package com.example.developmenttaskeyge0001frgesport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartsidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startsida);

        final Button startaspelknapp = (Button) findViewById(R.id.startaspelknapp);
        startaspelknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //koden är tagen från youtubeklipp, se reeferenser i dokumetationen
                final MediaPlayer Knappljud = MediaPlayer.create(getApplicationContext(), R.raw.knappljud);
                startaspelknapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Knappljud.start();

                        // frågesports aktiviteten startas när man klickar på knappen
                        openFrågessportspelsSida();
                    }
                });

            }
        });
    }
    public void openFrågessportspelsSida() {
        Intent intent = new Intent(this,FrågesportActivity.class);
        startActivity(intent);
    }
}
