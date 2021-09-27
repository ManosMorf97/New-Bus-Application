package com.example.new_bus_application;


import android.content.Intent;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

public class UserActivity extends AppCompatActivity {
    Button back=findViewById(R.id.back);
    Button[] choices={findViewById(R.id.see_bus_stations),findViewById(R.id.see_station_buses),findViewById(R.id.see_nearest_stations),findViewById(R.id.make_route)};
    Class [] classes={BusStationsActivity.class,StationBusesActivity.class,NearestStationsActivity.class,RouteActivity.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        for(int i=0; i<choices.length; i++){
            int finalI = i;
            choices[i].setOnClickListener(view -> {
                Intent activityChangeIntent=new Intent(UserActivity.this,classes[finalI]);
                startActivity(activityChangeIntent);
            });
        }
        back.setOnClickListener(view -> {
            Intent activityChangeIntent=new Intent(UserActivity.this,MainActivity.class);
            startActivity(activityChangeIntent);
        });
    }
}