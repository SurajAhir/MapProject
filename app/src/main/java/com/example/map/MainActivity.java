package com.example.map;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.map.FirebaseWork.LoginUsingFirebase;
import com.example.map.FirebaseWork.ShowFirebaseUserProfile;
import com.example.map.FirebaseWork.firebaseUserData;
import com.example.map.ServicesData.ServicesDataClass;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 101;
    ImageButton current_location;
    GoogleMap googleMap;
    AlertDialog.Builder dialog;
    SupportMapFragment supportMapFragment;
    TextView description, name;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FragmentContainerView containerView;
    public static List<ServicesDataClass> list_service;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<LatLng> list1 = new ArrayList<>();
    String list[];
    ProgressDialog progressDialog;
    public static firebaseUserData userData;
    private LocationRequest locationRequest;
    private static final int REQUEST_CHECK_SETTINGS = 10001;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFirebaseNotification();
        runThreadForShowFirebaseUserProfile();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initialize();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();
        putBooleanForFirebase();
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String jwt = preferences.getString("jwt", "");
        Log.d("wait", Thread.currentThread().getName());
        callApiAndShowName(jwt);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.show_list_menu:
                        Intent intent = new Intent(MainActivity.this, ShowListsOfService.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.video_menu:
                        Intent intent1 = new Intent(MainActivity.this, PlayVideo.class);
                        startActivity(intent1);
                        break;
                    case R.id.show_user_details_menu:
                        Intent intent2=new Intent(MainActivity.this,ShowFirebaseUserProfile.class);
                        intent2.putExtra("valueOfObject",userData);
                        startActivity(intent2);

                        break;
                    case R.id.logout_menu:
                        SharedPreferences preferences1 = getSharedPreferences("FirebaseLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.clear();
                        editor.commit();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent3 = new Intent(MainActivity.this, LoginUsingFirebase.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
                return true;
            }
        });
        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "METHOD ME AA");
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "return ");
                    return;
                }
                Task<Location> task = fusedLocationProviderClient.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("TAG", location + " h ye meri");

                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                MainActivity.this.googleMap = googleMap;
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions options = new MarkerOptions().position(latLng).title("Current Position");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                                googleMap.addMarker(options);

                            }
                        });

                    }
                });

            }
        });
    }

   public  void runThreadForShowFirebaseUserProfile() {
       FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
       firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               MainActivity.userData = snapshot.getValue(firebaseUserData.class);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void putBooleanForFirebase() {
        SharedPreferences preferences = getSharedPreferences("FirebaseLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogin", true);
        editor.commit();
    }

    private void showFirebaseNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                    }
                });
    }

    private void callApiAndShowName(String jwt) {
        Retrofit retrofit = RetrofitManager.getRetrofit();
        RetrofitManager.getResponseInterface api = retrofit.create(RetrofitManager.getResponseInterface.class);
        api.getServicesResponse("Bearer " + jwt).enqueue(new Callback<List<ServicesDataClass>>() {
            @Override
            public void onResponse(Call<List<ServicesDataClass>> call, Response<List<ServicesDataClass>> response) {
                list_service = response.body();
//               Log.d("Tag",list_service.get(0).getImages().length+"");
            }


            @Override
            public void onFailure(Call<List<ServicesDataClass>> call, Throwable t) {
                Log.d("Tag", t.getMessage());
            }
        });
    }

    private void initialize() {
        name = findViewById(R.id.name);
        description = findViewById(R.id.descripton);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer);
        current_location = findViewById(R.id.current_location);
        containerView = findViewById(R.id.container_view);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        dialog = new AlertDialog.Builder(this);
        list = new String[]{"Normal", "Hybrid", "Satelite", "Terrain"};
        list1.add(new LatLng(29.970912805506323, 76.87572125258492));
        list1.add(new LatLng(29.979804660863547, 76.889033688676));
        list1.add(new LatLng(29.78172414509632, 76.40433244479465));
        list1.add(new LatLng(30.376403045843414, 76.78293536345275));
    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkDeviceLocationTurnOn();

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
    }

    private void checkDeviceLocationTurnOn() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    getCurrentLocation();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    public void getCurrentLocation() {
        Log.d("TAG", "METHOD ME AA");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "return ");
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d("TAG", location + " h ye meri");

                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        MainActivity.this.googleMap = googleMap;
//                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
//                            MarkerOptions options=new MarkerOptions().position(latLng).title("Current Position");
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                            googleMap.addMarker(options);
                        for (int i = 0; i < list1.size(); i++) {
                            MarkerOptions options = new MarkerOptions().position(list1.get(i)).title("Current Position");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list1.get(i), 10));
                            googleMap.addMarker(options);
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            checkDeviceLocationTurnOn();
        } else {
            getLocationPermission();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_type_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.normal:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.hybrid:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.satelite:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "GPS is tured on", Toast.LENGTH_SHORT).show();
                    getCurrentLocation();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS required to be tured on", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}