package com.example.map.FirebaseWork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import retrofit2.http.Url;

public class SignUpUsingFirebase extends AppCompatActivity  {
EditText fname,lname,address,country,state,pincode,district,emailId,password;
TextView tv_image;
Button uploadImage,signUp,login;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;
public static Uri uri;
ProgressDialog progressDialog;
FirebaseStorage firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_using_firebase);
initialize();
progressDialog.setTitle("Please Wait...");
signUp.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (fname.getText().toString().isEmpty()) {
            fname.setError("Field Required");
            fname.setFocusable(View.FOCUSABLE);
        } else if (lname.getText().toString().isEmpty()) {
            lname.setError("Field Required");
            lname.setFocusable(View.FOCUSABLE);
        } else if (address.getText().toString().isEmpty()) {
            address.setError("Field Required");
            address.setFocusable(View.FOCUSABLE);
        } else if (state.getText().toString().isEmpty()) {
            state.setError("Field Required");
            state.setFocusable(View.FOCUSABLE);
        } else if (pincode.getText().toString().isEmpty()) {
            pincode.setError("Field Required");
            pincode.setFocusable(View.FOCUSABLE);
        } else if (district.getText().toString().isEmpty()) {
            district.setError("Field Required");
            district.setFocusable(View.FOCUSABLE);
        } else if (tv_image.getText().toString().isEmpty()) {
            tv_image.setText("Please Select image");
        } else if (emailId.getText().toString().isEmpty()) {
            emailId.setError("Field Required");
            emailId.setFocusable(View.FOCUSABLE);
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Field Required");
            password.setFocusable(View.FOCUSABLE);
        } else {
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(emailId.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("Tag", "enter in method");
                    if (task.isSuccessful()) {
                        firebaseUserData data = new firebaseUserData(fname.getText().toString(), lname.getText().toString(), address.getText().toString(),
                                country.getText().toString(), state.getText().toString(), pincode.getText().toString(), district.getText().toString(),
                                emailId.getText().toString(), password.getText().toString(), uri.toString());
                        firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).setValue(data);
                        Toast.makeText(SignUpUsingFirebase.this, "SignUp Successfull", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(SignUpUsingFirebase.this,LoginUsingFirebase.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(SignUpUsingFirebase.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
});
uploadImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1001);
    }
});
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(SignUpUsingFirebase.this,LoginUsingFirebase.class);
        startActivity(intent);
        finish();
    }
});
    }

    private void initialize() {
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        address=findViewById(R.id.address);
        country=findViewById(R.id.country);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        district=findViewById(R.id.district);
        emailId=findViewById(R.id.signUpEmailId);
        password=findViewById(R.id.signUpPassword);
        tv_image=findViewById(R.id.tv_image);
        uploadImage=findViewById(R.id.upload_image);
        signUp=findViewById(R.id.signUpButton);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        login=findViewById(R.id.firebaseSignUp_jumpToLogin);
        progressDialog=new ProgressDialog(SignUpUsingFirebase.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001){
            StorageReference reference=firebaseStorage.getReference().child("Images");
            Log.d("Tag",reference+"");
            reference.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onSuccess(Uri uri) {
                            SignUpUsingFirebase.uri=uri;
                            Log.d("Tag",uri.toString());
                            tv_image.setText(uri.toString());
                            tv_image.setTextColor(R.color.purple_700);
                        }
                    });
                }
            });

        }
    }
}