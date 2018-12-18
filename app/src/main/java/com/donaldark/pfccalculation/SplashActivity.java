package com.donaldark.pfccalculation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashLogo;
    private TextView splashTitle;
    private TextView splashUnderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        splashLogo = (ImageView) findViewById(R.id.splash_logo);
        splashTitle = (TextView) findViewById(R.id.splash_title);
        splashUnderTitle = (TextView) findViewById(R.id.splash_under_title);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.my_transition);
        splashLogo.startAnimation(animation);
        splashTitle.startAnimation(animation);
        splashUnderTitle.startAnimation(animation);

        final Intent intent = new Intent(this, LoginActivity.class);

        Thread timer = new Thread() {
            public  void run () {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
