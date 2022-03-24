package com.example.map.FirebaseWork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.map.MainActivity;
import com.example.map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ShowFirebaseUserProfile extends AppCompatActivity {
ImageView background,profile;
TextView userName,userAddress,userEmail,userCountry;
FloatingActionButton floatingActionButton;
FirebaseDatabase firebaseDatabase;
FirebaseAuth firebaseAuth;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_firebase_user_profile);
        initialize();
        setUpProfileDetails();
        String uid=firebaseAuth.getCurrentUser().getUid();
        Log.d("Tag", uid);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });
//        firebaseDatabase.getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                firebaseUserData data=snapshot.getValue(firebaseUserData.class);
//                Log.d("Tag",data.getImage()+"");
//                Picasso.get().load(data.getImage()).placeholder(R.drawable.default_image).into(profile);
//                userName.append(data.getFname());
//                userAddress.append(data.getAddress());
//                userEmail.append(data.getEmailId());
//                userCountry.append(data.getCountry());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
////                Log.d("Tag",error.toString());
//                Toast.makeText(ShowFirebaseUserProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setUpProfileDetails() {
        firebaseUserData userData= (firebaseUserData) getIntent().getSerializableExtra("valueOfObject");
       if(userData!=null) {
           Picasso.get().load(userData.getImage()).placeholder(R.drawable.default_image).into(profile);
           userName.append(userData.getFname());
           userAddress.append(userData.getAddress());
           userEmail.append(userData.getEmailId());
           userCountry.append(userData.getCountry());
           if (userData.getBackgroundImage() != null) {
               Picasso.get().load(userData.getBackgroundImage()).placeholder(R.drawable.default_image).into(background);
           }
       }else{
           Intent intent=new Intent(ShowFirebaseUserProfile.this,MainActivity.class);
           startActivity(intent);
           finish();
       }
    }

    private void initialize() {
        background=findViewById(R.id.user_background_image);
        profile=findViewById(R.id.userProfile);
        userName=findViewById(R.id.userName);
        userAddress=findViewById(R.id.userAddress);
        userEmail=findViewById(R.id.userEmail);
        userCountry=findViewById(R.id.userCountry);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            background.setImageURI(data.getData());
            StorageReference reference= FirebaseStorage.getInstance().getReference().child("BackgroundImage");
          reference.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                  reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {
                          FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance()
                                  .getCurrentUser().getUid()).child("backgroundImage").setValue(uri.toString());
                          Toast.makeText(ShowFirebaseUserProfile.this, "Successs", Toast.LENGTH_SHORT).show();
                      }
                  });
              }
          });
        }
    }
}