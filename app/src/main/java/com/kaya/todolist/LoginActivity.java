package com.kaya.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText loginEmail, loginPwd;
    private Button loginBtn;
    private TextView loginQn;
    private FirebaseAuth zAuth;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("login");
        zAuth=FirebaseAuth.getInstance();
        loader=new ProgressDialog(this);

        loginEmail = findViewById(R.id.loginEmail);
        loginPwd = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);
        loginQn = findViewById(R.id.loginPageQuestion);

        loginQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

         loginBtn.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPwd.getText().toString().trim();

             if (TextUtils.isEmpty(email)) {
                 loginEmail.setError("Email is required");
                 return;
             }
             if (TextUtils.isEmpty(password)) {
                 loginPwd.setError("Password is required");
                 return;
             } else {

               loader.setMessage("Login in progress");
               loader.setCanceledOnTouchOutside(false);
               loader.show();

               zAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                         startActivity(intent);
                         finish();
                         loader.dismiss();
                     }
                     else{
                         String error=task.getException().toString();
                         Toast.makeText(LoginActivity.this,"login failed "+error,Toast.LENGTH_SHORT).show();
                         loader.dismiss();
                     }
                   }
               });
             }
         });
        }
    }