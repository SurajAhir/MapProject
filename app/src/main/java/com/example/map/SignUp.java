package com.example.map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.map.DataClasses.ResponseClass;
import com.example.map.DataClasses.ResponseErrorClass;
import com.example.map.DataClasses.UserData;
import com.example.map.DataClasses.UserParameter;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {
TextInputEditText userId,userPassword;
TextView login;
    Retrofit retrofit;
    RetrofitManager.getResponseInterface api;
    SharedPreferences sharedPreferences;
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
userId=findViewById(R.id.user_id);
userPassword=findViewById(R.id.user_password);
login=findViewById(R.id.btn_login);
sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
String jwt_token=sharedPreferences.getString("jwt",null);
boolean val=sharedPreferences.getBoolean("valid",false);
if(val){
    Intent intent=new Intent(this,MainActivity.class);
    startActivity(intent);
    finish();
}
         retrofit=RetrofitManager.getRetrofit();
        api=retrofit.create(RetrofitManager.getResponseInterface.class);
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(userId.getText().toString().isEmpty()){
                    userId.setError("Please Enter your emailId ");
                    userPassword.setFocusable(View.FOCUSABLE);
                }else if(userPassword.getText().toString().isEmpty()){
                    userPassword.setError("Please Enter your Password");
                    userPassword.setFocusable(View.FOCUSABLE);
                }
                else{
                    progressDialog.show();
                    UserParameter obj=new UserParameter(userId.getText().toString().trim(),userPassword.getText().toString().trim(),"vendor");
                    funcHitApi(obj);
                }
            }
        });
    }

    private void funcHitApi(UserParameter obj) {
     api.getResponseError(obj).enqueue(new Callback<ResponseErrorClass>() {
         @Override
         public void onResponse(Call<ResponseErrorClass> call, Response<ResponseErrorClass> response) {
             Toast.makeText(SignUp.this, response.body().getError(), Toast.LENGTH_SHORT).show();
             progressDialog.dismiss();
         }

         @Override
         public void onFailure(Call<ResponseErrorClass> call, Throwable t) {
funcWrongDetails(obj);
         }
     });
    }
    private void funcWrongDetails(UserParameter obj){
      api.getResponse(obj).enqueue(new Callback<ResponseClass>() {
          @Override
          public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
              UserData data = response.body().getData();
              SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
              SharedPreferences.Editor editor=sharedPreferences.edit();
              editor.putBoolean("valid",true);
              editor.putString("jwt",data.getJWT_TOKEN());
              editor.commit();
              Log.d("Tag",response.body().getStatus());
              Toast.makeText(SignUp.this,response.body().getStatus(),Toast.LENGTH_LONG).show();
              Intent intent=new Intent(SignUp.this,MainActivity.class);
              startActivity(intent);
              finish();
          }

          @Override
          public void onFailure(Call<ResponseClass> call, Throwable t) {
//              funcHitApi(obj);
              Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_LONG).show();
              progressDialog.dismiss();
          }
      });
    }
}