package com.github.bullrentproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bullrentproject.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logoImage;
    TextView logoText,tagLineText;
    Animation topAnim,botAnim;
    private static int SPLASH_SCREEN = 5000;  //1000s mean 1s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.top_anim);
        botAnim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_anim);

        logoImage = findViewById(R.id.iv_jeepLogo);
        logoText = findViewById(R.id.tv_logoText);
        tagLineText = findViewById(R.id.tv_logoTagLine);

        logoImage.setAnimation(topAnim);
        logoText.setAnimation(botAnim);
        tagLineText.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                //Shared Anim
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logoImage, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                    startActivity(intent, options.toBundle());
                }
//                startActivity(intent);
//                finish();
            }
        }, SPLASH_SCREEN);
    }
}
