package com.example.developmenttaskeyge0001frgesport;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //koden är tagen från youtube tutorial, se reeferenser i dokumetationen

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(StartsidaActivity.class)// efter splashscreen så går den till startsidan
                .withSplashTimeOut(1500) // hur länge som denna splashscreen ska hålla på
                .withBackgroundColor(Color.parseColor("#FFFFFF")) // bakgrundsfärgen är vit
                .withAfterLogoText("WW2Q") // efter loggan kommer denna text
                .withLogo(R.drawable.icons8_tank_96); // detta är loggan som visas i splashscreen

        config.getAfterLogoTextView().setTextColor(Color.BLACK);

        View easysplashScreen = config.create();
        setContentView(easysplashScreen);
    }
}
