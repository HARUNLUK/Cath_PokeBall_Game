package com.example.catchpokeball;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText, scoreText;
    int score;
    public static String language;
    ImageView[] imageArray;
    public static Handler handler;
    public static Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        imageArray = new ImageView[20];
        for(int i=0;i<20;i++){
            imageArray[i] = (ImageView) gridLayout.getChildAt(i);
        }
        hideImages();
        randomImages();
        timeText = (TextView)findViewById(R.id.timeText);
        scoreText = (TextView)findViewById(R.id.scoreText);
        score=0;
        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                if(language=="tr"){
                    timeText.setText("Zaman : "+millisUntilFinished/1000);
                }else {
                    timeText.setText("Time : "+millisUntilFinished/1000);
                }
            }

            @Override
            public void onFinish() {
                handler.removeCallbacks(runnable);
                hideImages();
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                if(language=="tr"){
                    alert.setTitle("Tekrar Oyna");
                    alert.setMessage("Tekrar oynamak ister misiniz?");
                    alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Oynadığınız için teşekkürler", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                }else{
                    alert.setTitle("Restart");
                    alert.setMessage("Do you want to play Again?");
                    alert.setMessage("Score : "+score);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Thank you for play", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

                alert.show();
            }
        }.start();
    }
    public void increaseScore(View view){
        score++;
        if(language=="tr"){
            scoreText.setText("Puan : "+score);
        }else {
            scoreText.setText("Score : " + score);
        }
    }
    void randomImages(){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                hideImages();
                Random random = new Random();
                showImageAt(random.nextInt(20));
                handler.postDelayed(this,600);
            }
        };
        handler.post(runnable);
    }
    void hideImages(){
        for(ImageView image : imageArray){
            image.setVisibility(View.INVISIBLE);
        }
    }
    void showImageAt(int index){
        imageArray[index].setVisibility(View.VISIBLE);
    }
    void setLocale(String selectedLocale){
        Locale locale = new Locale(selectedLocale);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        writeLocaleSharedPreferances(selectedLocale);
        finish();
        startActivity(getIntent());
    }
    void writeLocaleSharedPreferances(String selectedLocale){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Selected Locale", selectedLocale);
        editor.commit();
    }
    String getLocaleSharedPreferances(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        return sharedPref.getString("Selected Locale","");
    }
}
