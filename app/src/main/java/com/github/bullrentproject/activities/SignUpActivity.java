package com.github.bullrentproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import com.github.bullrentproject.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    TextInputLayout regName,regUserName,regUserEmail,regUserMobile,regUserPass;
    Button signUp,alreadySignUp;
    FirebaseDatabase rootNode;     //main database
    DatabaseReference reference;  //subNodes of root database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize FireBase Call
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.sign_fullName);
        regUserName = findViewById(R.id.sign_userName);
        regUserEmail = findViewById(R.id.sign_Email);
        regUserMobile = findViewById(R.id.sign_Mobile);
        regUserPass = findViewById(R.id.sign_Password);
        signUp = findViewById(R.id.btn_signUp);
        alreadySignUp = findViewById(R.id.btn_already);

//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rootNode = FirebaseDatabase.getInstance();   //getInstance will call main database from firebase
//                reference = rootNode.getReference("users");  //sub root nodes
//
//                //get values from req. field
//                String name = userFullName.getEditText().getText().toString();
//                String username = userName.getEditText().getText().toString();
//                String email = userEmail.getEditText().getText().toString();
//                String mobile = userMobile.getEditText().getText().toString();
//                String password = userPass.getEditText().getText().toString();
//                UserHelperClass userHelperClass = new UserHelperClass(name,username,email,mobile,password);
//                reference.child(mobile).setValue(userHelperClass);
//            }
//        });
    }

    private Boolean validateFullName(){
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Full Name cannot be empty");
            regName.setAnimation(AnimationError());
            return false;
        } else {
            regName.setError(null); //hide error
            regName.setErrorEnabled(false); //remove whitespace error
            regName.setAnimation(null);
            return true;
        }
    }

    private Boolean validateUserName(){
        String val = regUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty()){
            regUserName.setError("User Name cannot be empty");
            regUserName.setAnimation(AnimationError());
            return false;
        } else if(val.length()>=14){
            regUserName.setError("User Name too long");
            regUserName.setAnimation(AnimationError());
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regUserName.setError("No Whitespace are allowed");
            regUserName.setAnimation(AnimationError());
            return false;
        }else {
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            regUserName.setAnimation(null);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regUserEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            regUserEmail.setError("Email cannot be empty");
            regUserEmail.setAnimation(AnimationError());
            return false;
        }else if(!val.matches(emailPattern)){
            regUserEmail.setError("Invaild Email Address");
            regUserEmail.setAnimation(AnimationError());
            return false;
        }else {
            regUserEmail.setError(null);
            regUserEmail.setErrorEnabled(false);
            regUserEmail.setAnimation(null);
            return true;
        }
    }

    private Boolean validateMobile(){
        String val = regUserMobile.getEditText().getText().toString();
        if(val.isEmpty()){
            regUserMobile.setError("Mobile cannot be empty");
            regUserMobile.setAnimation(AnimationError());
            return false;
        }else {
            regUserMobile.setError(null);
            regUserMobile.setErrorEnabled(false);
            regUserMobile.setAnimation(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = regUserPass.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if(val.isEmpty()){
            regUserPass.setError("Password cannot be empty");
            regUserPass.setAnimation(AnimationError());
            return false;
        }else if(!val.matches(passwordVal)){
            regUserPass.setError("Password is too weak");
            regUserPass.setAnimation(AnimationError());
            return false;
        }else {
            regUserPass.setError(null);
            regUserPass.setErrorEnabled(false);
            regUserPass.setAnimation(null);
            return true;
        }
    }

    public TranslateAnimation AnimationError() {
        TranslateAnimation vibrate = new TranslateAnimation(10, 20, 0, 0);
        vibrate.setDuration(400);
        vibrate.setInterpolator(new CycleInterpolator(6));
        return vibrate;
    }

    public void registerUser(View view) {

        if(!validateFullName() | !validateUserName() | !validateEmail() | !validateMobile() | !validatePassword()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();   //getInstance will call main database from firebase
        reference = rootNode.getReference("users");  //sub root nodes

        //get values from req. field
        String name = regName.getEditText().getText().toString();
        String username = regUserName.getEditText().getText().toString();
        String email = regUserEmail.getEditText().getText().toString();
        String mobile = regUserMobile.getEditText().getText().toString();
        String password = regUserPass.getEditText().getText().toString();

        Intent intent = new Intent(SignUpActivity.this,VerifyMobileActivity.class);
        intent.putExtra("mobile",mobile);
        startActivity(intent);

        //To store data in firebase
       //UserHelperClass userHelperClass = new UserHelperClass(name,username,email,mobile,password);
      //reference.child(username).setValue(userHelperClass);
    }
}
