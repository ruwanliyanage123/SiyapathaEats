package com.example.siyapathaeats.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.siyapathaeats.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView ImgUserPhoto;
    static int PReqCode =1;
    static int REQUESCODE =1;
    Uri pickedImgUri;

    private EditText userEmail, userPassword, userPassword2, userName;
    private Button regBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // views

        userEmail = findViewById(R.id.regMail);
        userPassword = findViewById(R.id.regPassword);
        userPassword2 = findViewById(R.id.regPassword2);
        userName = findViewById(R.id.regName);
        regBtn = findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regBtn.setVisibility(View.INVISIBLE);
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2= userPassword2.getText().toString();
                final String name  = userName.getText().toString();

                if(email.isEmpty() || name.isEmpty()|| password.isEmpty()||!password.equals(password2)){
                    // something is wrong
                    showMessage("please verify all fields");
                    regBtn.setVisibility(View.VISIBLE);

                }
                else{
                    // everything is ok
                    //create account for the user 

                    CreateUserAccount(email,name,password);
                }

            }
        });


        ImgUserPhoto = findViewById(R.id.regUserPhoto);

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=22){

                    checkAndRequestForPermission();
                }
                else{
                    openGallery();
                }
            }
        });
    }

    private void CreateUserAccount(String email, String name, String password) {
    }

    //simple mothod to show messages
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();

    }

    private void openGallery() {
        //TODO: open gallery intent and wait

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    private void checkAndRequestForPermission(){

        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this, "please accepr for required permission", Toast.LENGTH_LONG).show();
            }
            else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PReqCode);
            }
        }
        else{
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            //the user has successfull picked as image
            //we need to save

            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);
        }
    }
}
