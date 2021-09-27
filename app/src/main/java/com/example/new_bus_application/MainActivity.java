package com.example.new_bus_application;

import android.content.Intent;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Person;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static boolean data=false;
    Button user=findViewById(R.id.User);
    Button administrator=findViewById(R.id.Administrator);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!data){
            Person.initialize();
            data=true;
        }
        user.setOnClickListener(view -> {
            Intent activityChangeIntent=new Intent(MainActivity.this,UserActivity.class);
            startActivity(activityChangeIntent);
        });



    }


}