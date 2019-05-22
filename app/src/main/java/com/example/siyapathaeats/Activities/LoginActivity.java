package com.example.siyapathaeats.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyapathaeats.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this, com.example.siyapathaeats.Activities.HomeActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setVisibility(View.INVISIBLE);
                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();


                if(mail.isEmpty()|| password.isEmpty()){
                    showMessage("please verify all feilds");
                }
                else{
                    signIn(mail,password);
                }

            }
        });

    }

    private void signIn(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    btnLogin.setVisibility(View.INVISIBLE);
                    updateUI();
                }
                else{
                    showMessage(task.getException().getMessage());
                }
            }
        });
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
