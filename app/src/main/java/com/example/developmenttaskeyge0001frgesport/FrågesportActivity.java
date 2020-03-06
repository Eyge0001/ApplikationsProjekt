package com.example.developmenttaskeyge0001frgesport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developmenttaskeyge0001frgesport.Modell.Fraga;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class FrågesportActivity extends AppCompatActivity {

    Button knapp1, knapp2, knapp3, knapp4;
    TextView  fragatext,tidtagning;
    int totalafragor = 0;
    int rätt = 0;
    int fel = 0;

    private DatabaseReference minDatabas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Här ger vi knapparna de olika valalternativen som finns i layout, activity_main, de olika knapparna och deras id för valenalternativen
        knapp1 = (Button) findViewById(R.id.Valett);
        knapp2 = (Button) findViewById(R.id.Valtvå);
        knapp3 = (Button) findViewById(R.id.Valtre);
        knapp4 = (Button) findViewById(R.id.Valfyra);

        //Här har vi textviewen som visar frågan, anropar id för frågan som finns i layout, activity_main
        fragatext = (TextView)findViewById(R.id.Fraga);

        //Här har vi textviewen som visar timern, anropar id för tidtagningen som finns i layout, activity_main
        tidtagning = (TextView)findViewById(R.id.tidtagning);

        //Anropar metoden under
        UppdateraFraga();

        //Anropar metoden för timern som ligger längre ner på sidan, timern ligger på 5min
        tidtagningsTimer(300,tidtagning);
    }

    private void UppdateraFraga()
    {

        totalafragor++;
        //Om all frågor överstiger 10 så kommer det som sker i denna metod utföras, förklaras mer detaljerat under
        if(totalafragor > 10 )
        {
            // Hjälper så att den totala mängden av frågor i resultatetsidan är korrekt
            totalafragor--;

            // Då öppnas resultat aktiviteten som visar ens resultat på frågesporten, det visas även liten toasttext som får användaren att förstå att frågorna är slut
            Toast.makeText(FrågesportActivity.this, "Frågesporten är slut, se resultat", Toast.LENGTH_LONG).show();

            // Det är här som själva övergången sker, här går man från den nuvarande frågesport aktiviteten till resultat sidan, skickar även med hur många rätt som användaren fick av de totala frågorna
            Intent intent = new Intent(FrågesportActivity.this,ResultatsidaActivity.class);

            //tar med de antal rätt som användaren fick
            intent.putExtra("rättsvar",String.valueOf(rätt));

            //tar med hur många frågor det var, så användaren får reda på hur många rätt man var ifrån alla rätt
            intent.putExtra("allafrågor",String.valueOf(totalafragor));

            // Om det är så att användaren får alla rätt, ska man få en toast meddelande som ger en uppmuntran men även massa applåder
            if (rätt == 10)
            {
                Toast.makeText(FrågesportActivity.this, "Mycket bra jobbat! du fick alla rätt", Toast.LENGTH_LONG).show();
                //Denna knappljud har jag laddat ner från nätet och lagt till i mappen raw, finns flera mp3 ljudspår
                MediaPlayer knappljud = MediaPlayer.create(getApplicationContext(),R.raw.appladerallaratt);
                knappljud.start();
            }

            // Om det är så att användaren 7 eller mer rätt, ska man få en toast meddelande som ger en uppmuntran men även mindre applåder tillskillnaden från den ovanför
            else if (rätt >= 7)
            {
                Toast.makeText(FrågesportActivity.this, "Bra jobbat! ", Toast.LENGTH_LONG).show();
                MediaPlayer knappljud = MediaPlayer.create(getApplicationContext(),R.raw.brajobbatapplader);
                knappljud.start();
            }

            // Om det är så att användaren får mindre än 5 frågor rätt , ska man få en toast meddelande som ber än att försöka igen men även en röst som ber än att testa på nytt
            else if (rätt < 5)
            {
                Toast.makeText(FrågesportActivity.this, "Du får läsa på, försök igen!", Toast.LENGTH_LONG).show();
                MediaPlayer knappljud = MediaPlayer.create(getApplicationContext(),R.raw.forsokigenrost);
                knappljud.start();
            }

            startActivity(intent);
            finish();
        }

        else
        {
            //Här har vi kopplingen till firebase realtime database, och jag hänvisar till "Fragor" där alla mina frågor är lagrade, tar med antalet av frågor som finns i databasen, då får man även med antalet totala frågor, koden är tagen från youtube tutorial, se reeferenser i dokumetationen
            minDatabas = FirebaseDatabase.getInstance().getReference().child("Fragor").child(String.valueOf(totalafragor));
            //läser data från databasen , hämtar även frågorna och de olika alternativen att välja på samt vilket av alternativen som är korrekta svar
            minDatabas.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //kopplingen till modellen Fraga, där jag har mina get och set för frågorna,valen och mina svar från firebase databsen, koden är tagen från youtube tutorial, se reeferenser i dokumetationen
                  final Fraga fraga = dataSnapshot.getValue(Fraga.class);

                  // Textviewsen sätts här för varje fråga och de 4 olika valen
                  fragatext.setText(fraga.getFraga());
                  knapp1.setText(fraga.getVal1());
                  knapp2.setText(fraga.getVal2());
                  knapp3.setText(fraga.getVal3());
                  knapp4.setText(fraga.getVal4());

                  //Detta är knapptrycksljuden för de olika knapparna när man väljer så man får ljudrespons
                  final MediaPlayer knappljud = MediaPlayer.create( getApplicationContext(),R.raw.knappljud);

                  //Detta är för första alternativknappen
                  knapp1.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          knappljud.start(); // när användaren trycker på knappen hör man knappljud

                          // om det är så att knapp 1 är lika med det svar som finns med i databasen så händer det som är i if-satsen
                          if(knapp1.getText().toString().equals(fraga.getSvar()))
                          {
                              // Om användaren klickar på första alternativet/val1 så kommer knappen att bli grön, och detta är förutsatt att första valet är även det korrekta svaret
                              knapp1.setBackgroundColor(Color.GREEN);
                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      rätt++;
                                      // efter att den visat knappen i grön färg återgår samma knapp till den ursprungliga färgen (guld färg) då nästa fråga kommer
                                      knapp1.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();

                                  }
                              },1500);
                          }

                          else
                              {
                                  // Om användaren klickat i fel svar, kommer det rätta alternativet att visas med grön färg och det alternativ man klickade på kommer att visas i röd färg, i detta fall är det första alternativet(val 1)
                                  fel++;
                                  knapp1.setBackgroundColor(Color.RED);

                                     // Om det andra alternativet är lika med svaret som finns hårkordat i databasen så blir den knappen grön
                                  if(knapp2.getText().toString().equals(fraga.getSvar()))
                                  {
                                      knapp2.setBackgroundColor(Color.GREEN);
                                  }
                                  // Om det tredje alternativet är lika med svaret som finns hårkordat i databasen så blir den knappen grön
                                  else if (knapp3.getText().toString().equals(fraga.getSvar()))
                                  {
                                      knapp3.setBackgroundColor(Color.GREEN);
                                  }
                                  // Om det fjärde alternativet är lika med svaret som finns hårkordat i databasen så blir den knappen grön
                                  else if (knapp4.getText().toString().equals(fraga.getSvar()))
                                  {
                                      knapp4.setBackgroundColor(Color.GREEN);
                                  }

                                  Handler handler = new Handler();
                                  handler.postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          // efter att den visat knappen i grön färg återgår samma knapp till den ursprungliga färgen (guld färg) då nästa fråga kommer
                                          knapp1.setBackgroundResource(R.drawable.ovalknapp);
                                          knapp2.setBackgroundResource(R.drawable.ovalknapp);
                                          knapp3.setBackgroundResource(R.drawable.ovalknapp);
                                          knapp4.setBackgroundResource(R.drawable.ovalknapp);
                                          UppdateraFraga();

                                      }
                                  },1500);

                              }
                      }
                  });

                    //Detta är för andra alternativknappen (Koden liknar den första alternativknappen, lite ändringar i else delen eftersom det nu är knapp1,knapp3 och knapp4 som ska bli gröna i det fallet då knapp 2 inte är det rätta svaret )
                  knapp2.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          knappljud.start();

                          if(knapp2.getText().toString().equals(fraga.getSvar()))
                          {
                              knapp2.setBackgroundColor(Color.GREEN);
                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      rätt++;
                                      knapp2.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();

                                  }
                              },1500);
                          }

                          else
                          {
                              fel++;
                              knapp2.setBackgroundColor(Color.RED);


                              if(knapp1.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp1.setBackgroundColor(Color.GREEN);
                              }

                              else if (knapp3.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp3.setBackgroundColor(Color.GREEN);
                              }

                              else if (knapp4.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp4.setBackgroundColor(Color.GREEN);
                              }

                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      knapp1.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp2.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp3.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp4.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();


                                  }
                              },1500);

                          }
                      }
                  });
                    //Detta är för tredje alternativknappen (Koden liknar den andra alternativknappen, men det är lite ändringar i else delen eftersom det nu är knapp1,knapp2 och knapp4 som ska bli gröna i det fallet då knapp 3 inte är det rätta svaret )
                  knapp3.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          knappljud.start();

                          if(knapp3.getText().toString().equals(fraga.getSvar()))
                          {
                              knapp3.setBackgroundColor(Color.GREEN);
                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      rätt++;
                                      knapp3.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();

                                  }
                              },1500);
                          }

                          else
                          {
                              fel++;
                              knapp3.setBackgroundColor(Color.RED);


                              if(knapp1.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp1.setBackgroundColor(Color.GREEN);
                              }

                              else if (knapp2.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp2.setBackgroundColor(Color.GREEN);
                              }

                              else if (knapp4.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp4.setBackgroundColor(Color.GREEN);
                              }

                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {


                                      knapp1.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp2.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp3.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp4.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();

                                  }
                              },1500);

                          }
                      }
                  });
                    //Detta är för fjärde alternativknappen (Koden liknar den andra alternativknappen, men det är lite ändringar i else delen eftersom det nu är knapp1,knapp2 och knapp3 som ska bli gröna i det fallet då knapp 4 inte är det rätta svaret )
                  knapp4.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          knappljud.start();

                          if(knapp4.getText().toString().equals(fraga.getSvar()))
                          {
                              knapp4.setBackgroundColor(Color.GREEN);
                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      rätt++;
                                      knapp4.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();

                                  }
                              },1500);
                          }

                          else
                          {
                              fel++;
                              knapp4.setBackgroundColor(Color.RED);


                              if(knapp1.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp1.setBackgroundColor(Color.GREEN);
                              }

                              else if (knapp2.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp2.setBackgroundColor(Color.GREEN);
                              }

                              else if (knapp3.getText().toString().equals(fraga.getSvar()))
                              {
                                  knapp3.setBackgroundColor(Color.GREEN);
                              }

                              Handler handler = new Handler();
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      knapp1.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp2.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp3.setBackgroundResource(R.drawable.ovalknapp);
                                      knapp4.setBackgroundResource(R.drawable.ovalknapp);
                                      UppdateraFraga();

                                  }
                              },1500);

                          }
                      }
                  });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    // Detta är metoden för tidtagnings timern som räknar ner tiden från att användaren klickar på "starta ww2 frågesporten" startas, koden är tagen från youtube, se reeferenser i dokumetationen
    public void tidtagningsTimer (int sekunder , final TextView tidtagning)
    {
        new CountDownTimer (sekunder * 1000 +1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int sekunder = (int) (millisUntilFinished / 1000);
                int minuter = sekunder / 60;
                sekunder = sekunder % 60;
                tidtagning.setText(String.format("%02d",minuter)
                        + ":" + String.format("%02d", sekunder));
            }

            @Override
            public void onFinish() {
                //Denna text under kommer upp som en toast meddellande efter att timerns tid har gått ut
                Toast.makeText(FrågesportActivity.this, "Tiden är slut, se resultat", Toast.LENGTH_LONG).show();

                // Här går användaren vidare till resultatsidan då timern är slut
                Intent intent = new Intent(FrågesportActivity.this,ResultatsidaActivity.class);

                // I resultatsidan så visas det hur många frågor totalt som ställts och hur många av de som användaren svarat rätt på
                intent.putExtra("allafrågor",String.valueOf(totalafragor));
                intent.putExtra("rättsvar",String.valueOf(rätt));

                // Dessa toast meddelanden och ljudspåren spelas upp eller dyker upp precis som vid början av koden, (då alla frågor är besvarade)

                if (rätt == 10)
                {
                    Toast.makeText(FrågesportActivity.this, "Mycket bra jobbat! du fick alla rätt", Toast.LENGTH_LONG).show();
                    MediaPlayer knappljud = MediaPlayer.create(getApplicationContext(),R.raw.appladerallaratt);
                    knappljud.start();
                }

                else if (rätt >= 7)
                {
                    Toast.makeText(FrågesportActivity.this, "Bra jobbat! ", Toast.LENGTH_LONG).show();
                    MediaPlayer knappljud = MediaPlayer.create(getApplicationContext(),R.raw.brajobbatapplader);
                    knappljud.start();
                }

                else if (rätt < 5)
                {
                    Toast.makeText(FrågesportActivity.this, "Du får läsa på, försök igen!", Toast.LENGTH_LONG).show();
                    MediaPlayer knappljud = MediaPlayer.create(getApplicationContext(),R.raw.forsokigenrost);
                    knappljud.start();
                }
                startActivity(intent);
            }
        }.start();
    }
}
