package com.example.developmenttaskeyge0001frgesport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultatsidaActivity extends AppCompatActivity {

    TextView Rtext1,Rtext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultatsida);

        // Textview för att visa mängden av frågor som finns (i databasen), det totala antalet av frågor
        Rtext1= (TextView) findViewById(R.id.totalafrågortext);

        //Textview för att visa mängden av frågor som användaren svarade rätt på
        Rtext2= (TextView) findViewById(R.id.antalfrättsvartext);

        // här hämtas båda textviewen till den aktivitet
        Intent i=getIntent();

        String allafrågor = i.getStringExtra("allafrågor");
        String rätt = i.getStringExtra("rättsvar");


        Rtext1.setText(allafrågor);
        Rtext2.setText(rätt);

        // Första knappen som användaren kan klicka på i denna aktivitet som ger chansen till användaren att köra om frågesporten, metoden finns längre ner
        final Button försökigenknapp = (Button) findViewById(R.id.spelaIgenknapp);
        försökigenknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer Knappljud = MediaPlayer.create(getApplicationContext(), R.raw.knappljud);
                försökigenknapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Knappljud.start();
                        öppnafrågesportspelssida();
                    }
                });
            }
        });

        // Denna knapp tar användaren tillbaka till startsidan, metoden finns längre ner
        final Button tillbakatillstartsidaknapp = (Button) findViewById(R.id.tillbakastartsidaknapp);
        tillbakatillstartsidaknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer Knappljud = MediaPlayer.create(getApplicationContext(), R.raw.knappljud);
                tillbakatillstartsidaknapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Knappljud.start();
                        öppnastartsida();
                    }
                });
            }
        });
    }

    private void öppnafrågesportspelssida() {
        Intent intent = new Intent(this,FrågesportActivity.class);
        startActivity(intent);
    }

    private void öppnastartsida() {
        Intent intent = new Intent(this,StartsidaActivity.class);
        startActivity(intent);
    }

}

