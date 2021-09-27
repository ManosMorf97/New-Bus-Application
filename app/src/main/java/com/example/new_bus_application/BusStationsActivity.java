package com.example.new_bus_application;


import android.content.Intent;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Bus;
import com.example.new_bus_application.domain_model.DAO.BusDAOAndroid;
import com.example.new_bus_application.domain_model.HelpComparator;
import com.example.new_bus_application.domain_model.Person;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class BusStationsActivity extends KeyboardActivity {

    EditText bus_name=findViewById(R.id.bus_name);
    ListView listView=findViewById(R.id.buses);
    ArrayList<Bus> busesList= BusDAOAndroid.ListBuses();
    Button show_stations=findViewById(R.id.button);
    Button back=findViewById(R.id.back);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stations);
        Collections.sort(busesList,new HelpComparator());
        ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item
                ,busesList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> bus_name.setText(busesList.get(i).getName()));
        show_stations.setOnClickListener(view -> {
            Bus chosen_bus=BusDAOAndroid.getBuses().get(bus_name.getText().toString());
            if(chosen_bus==null){
                Toast toast=Toast.makeText(getApplicationContext(),
                        "The bus "+bus_name.getText().toString()+" is not on the list,See the list",Toast.LENGTH_LONG);
                toast.show();
                bus_name.getText().clear();
            }else{
                Person.setChosen_bus(chosen_bus);
                Intent activityChangeIntent=new Intent(BusStationsActivity.this,ShowStationsActivity.class);
                startActivity(activityChangeIntent);
            }
        });
        back.setOnClickListener(view -> {
            Intent activityChangeIntent=new Intent(BusStationsActivity.this,UserActivity.class);
            startActivity(activityChangeIntent);
        });
    }
}