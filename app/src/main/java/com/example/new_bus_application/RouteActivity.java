package com.example.new_bus_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.new_bus_application.domain_model.DAO.StationDAOAndroid;
import com.example.new_bus_application.domain_model.HelpComparator;
import com.example.new_bus_application.domain_model.Person;
import com.example.new_bus_application.domain_model.Station;

import java.util.ArrayList;
import java.util.Collections;

public class RouteActivity extends KeyboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        EditText [] editTexts={findViewById(R.id.begining_station),findViewById(R.id.ending_station)};
        ListView listView=findViewById(R.id.stations);
        ArrayList<Station> stationsList= StationDAOAndroid.ListStations();
        Button makeRoute=findViewById(R.id.button);
        Button back=findViewById(R.id.back);
        Collections.sort(stationsList,new HelpComparator());
        ArrayAdapter<Station> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item
                ,stationsList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) ->
        {
            for(EditText editText:editTexts){
                if(editText.getText().toString().equals("")){
                    editText.setText(stationsList.get(i).getName());
                    break;
                }
            }
        });
        makeRoute.setOnClickListener(view -> {
            for(EditText editText:editTexts){
                if(StationDAOAndroid.getStations().get(editText.getText().toString())==null){
                    Toast toast=Toast.makeText(getApplicationContext(),
                            "The station "+editText.getText().toString()+" is not on the list,See the list",Toast.LENGTH_LONG);
                    toast.show();
                    editText.getText().clear();
                    return;
                }
            }
            Person.checkRoutes(editTexts[0].getText().toString(),editTexts[1].getText().toString());
            Intent intent=new Intent(RouteActivity.this,UserRouteActivity.class);
            startActivity(intent);
        });
        back.setOnClickListener(view -> {
            Intent activityChangeIntent=new Intent(RouteActivity.this,UserActivity.class);
            startActivity(activityChangeIntent);
        });


    }
}