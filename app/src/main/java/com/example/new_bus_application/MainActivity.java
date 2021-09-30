package com.example.new_bus_application;

import android.content.Intent;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Person;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static boolean data=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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