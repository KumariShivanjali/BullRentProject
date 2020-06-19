package com.github.bullrentproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bullrentproject.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ImageView logoJeep;
    TextView  welcome,signUp;
    Button newSignUp,Login;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logoJeep = findViewById(R.id.iv_jeepImage);
        welcome = findViewById(R.id.tv_Welcome);
        signUp = findViewById(R.id.tv_sign);
        Login = findViewById(R.id.btn_login);
        newSignUp = findViewById(R.id.btn_newSignUp);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        newSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                Pair[] pairs  = new Pair[7];
                pairs[0] = new Pair<View, String>(logoJeep,"logo_image");
                pairs[1] = new Pair<View, String>(welcome, "logo_text");
                pairs[2] = new Pair<View, String>(signUp, "logo_desc");
                pairs[3] = new Pair<View, String>(Login, "login_trans");
                pairs[4] = new Pair<View, String>(newSignUp, "new_user_trans");
                pairs[5] = new Pair<View, String>(username, "username_trans");
                pairs[6] = new Pair<View, String>(password, "password_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                    startActivity(intent, options.toBundle());  //option.toBundle will carry over transition
                }
//              startActivity(intent);
              }
        });
    }

    private Boolean validateUserName(){
        String val = username.getEditText().getText().toString();
        if(val.isEmpty()){
            username.setError("User Name cannot be empty");
            username.setAnimation(AnimationError());
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            username.setAnimation(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()){
            password.setError("Password cannot be empty");
            password.setAnimation(AnimationError());
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            password.setAnimation(null);
            return true;
        }
    }

    public TranslateAnimation AnimationError() {
        TranslateAnimation vibrate = new TranslateAnimation(10, 20, 0, 0);
        vibrate.setDuration(400);
        vibrate.setInterpolator(new CycleInterpolator(6));
        return vibrate;
    }

    public void loginUser(View view) {
        if(!validateUserName() | !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String mobileFromDB = dataSnapshot.child(userEnteredUsername).child("mobile").getValue(String.class);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("mobile", mobileFromDB);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("username", usernameFromDB);
                        startActivity(intent);
                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    username.setError("No such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}