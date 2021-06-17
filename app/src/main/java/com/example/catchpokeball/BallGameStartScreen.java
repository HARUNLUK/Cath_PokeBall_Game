package com.example.catchpokeball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BallGameStartScreen extends AppCompatActivity {
    Intent starGameIntent;
    ToggleButton toggle;
    Button startButton,quitButton;
    TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_game_start_screen);
        starGameIntent = new Intent(this,MainActivity.class);
        toggle = (ToggleButton)findViewById(R.id.languageToggle);
        startButton = (Button)findViewById(R.id.startButton);
        quitButton = (Button)findViewById(R.id.quitButton);
        appName = (TextView) findViewById(R.id.appText);
        MainActivity.language = "";
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.language = "tr";
                    appName.setText("Poke Topu Yakala");
                    appName.setTextSize(45f);
                    startButton.setText("Oyna");
                    quitButton.setText("Çıkış");
                }else {
                    MainActivity.language = "en";
                    appName.setText("Catch Poke Ball");
                    appName.setTextSize(50f);
                    startButton.setText("Play");
                    quitButton.setText("Quit");
                }
            }
        });
    }
    public void StartGame(View view){
        startActivity(starGameIntent);
    }
    public void QuitGame(View view){
        finish();
    }
}
