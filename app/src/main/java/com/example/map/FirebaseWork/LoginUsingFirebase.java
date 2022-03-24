package com.example.map.FirebaseWork;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.map.MainActivity;
import com.example.map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUsingFirebase extends AppCompatActivity {
EditText userEmail,userPassword;
Button signUp;
TextView login;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_using_firebase);
        SharedPreferences preferences=getSharedPreferences("FirebaseLogin",MODE_PRIVATE);
        boolean isVal=preferences.getBoolean("isLogin",false);
        if(isVal){
            Intent intent=new Intent(LoginUsingFirebase.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        initialize();
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(userEmail.getText().toString().isEmpty()){
                    userEmail.setError("Field Required");
                    userEmail.setFocusable(View.FOCUSABLE);
                }else if(userPassword.getText().toString().isEmpty()){
                    userPassword.setError("Field Required");
                    userPassword.setFocusable(View.FOCUSABLE);
                }else {
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString().trim(),userPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginUsingFirebase.this, "Login Successfull", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginUsingFirebase.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(LoginUsingFirebase.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginUsingFirebase.this,SignUpUsingFirebase.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initialize() {
//        userName=findViewById(R.id.firebaseLogin_user_name);
        userEmail=findViewById(R.id.firebaseLogin_user_id);
        userPassword=findViewById(R.id.firebaseLogin_user_password);
        login=findViewById(R.id.firebaseLogin_btn_login);
        signUp=findViewById(R.id.firebaseLogin_jumpToSignUp);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(LoginUsingFirebase.this);
        progressDialog.setTitle("Please Wait...");
    }
}