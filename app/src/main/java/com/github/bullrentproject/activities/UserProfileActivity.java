package com.github.bullrentproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bullrentproject.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {

    ImageView userImg;
    TextView  fullName,userName;
    TextInputLayout labelName,labelEmail,labelMobile,labelPassword;
    Button btnUpdate;
    DatabaseReference reference;

    //Global Variable to hold user data inside this activity
    String _userName, _Name, _userEmail, _userMobile, _userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImg  = findViewById(R.id.iv_profileImg);
        fullName = findViewById(R.id.tv_fullName);
        userName = findViewById(R.id.tv_userName);

        labelName = findViewById(R.id.profile_fullName);
        labelEmail = findViewById(R.id.profile_userEmail);
        labelMobile = findViewById(R.id.profile_userMobile);
        labelPassword = findViewById(R.id.profile_userPassword);

        //For Database Update
        reference = FirebaseDatabase.getInstance().getReference("users");

        showAllData();
    }

    private void showAllData(){
        _Name = getIntent().getStringExtra("name");
        _userName = getIntent().getStringExtra("username");
        _userEmail = getIntent().getStringExtra("email");
        _userMobile = getIntent().getStringExtra("mobile");
        _userPassword = getIntent().getStringExtra("password");

        fullName.setText(_Name);
        userName.setText(_userName);

        labelName.getEditText().setText(_Name);
        labelEmail.getEditText().setText(_userEmail);
        labelMobile.getEditText().setText(_userMobile);
        labelPassword.getEditText().setText(_userPassword);
    }

    public void dataUpdate(View view){
        if(isNameChanged() || isPassChanged()){
            Toast.makeText(this, "Data has been updated!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data is same & cann't be updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNameChanged() {
       if(!_Name.equals(labelName.getEditText().getText().toString())){
          reference.child(_userName).child("name").setValue(labelName.getEditText().getText().toString());
          _Name = labelName.getEditText().getText().toString();
          return true;
       }else {
           return false;
       }
    }

    private boolean isPassChanged() {
        if(!_userPassword.equals(labelPassword.getEditText().getText().toString())){
            reference.child(_userName).child("password").setValue(labelPassword.getEditText().getText().toString());
            _userPassword = labelPassword.getEditText().getText().toString();
            return true;
        }else {
            return false;
        }
    }
}
