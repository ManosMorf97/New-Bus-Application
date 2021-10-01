package com.example.new_bus_application;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Person;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static boolean data=false;
    private static final int INITIAL_REQUEST = 1337;
    private static final int CONTACTS_REQUEST = INITIAL_REQUEST + 2;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
        requestPermissions(CONTACTS_PERMS, CONTACTS_REQUEST);
        requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        if(!data){
            Person.initialize();
            data=true;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button user=findViewById(R.id.User);
        Button administrator=findViewById(R.id.Administrator);
        user.setOnClickListener(view -> {
            Intent activityChangeIntent=new Intent(MainActivity.this,UserActivity.class);
            startActivity(activityChangeIntent);
        });



    }


}