package com.example.new_bus_application;

import android.content.Intent;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Bus;
import com.example.new_bus_application.domain_model.Person;
import com.example.new_bus_application.domain_model.Station;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowBusesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buses);

        Button back=findViewById(R.id.back);
        TextView textView=findViewById(R.id.textView3);
        ListView listView=findViewById(R.id.buses);
        textView.setText("The buses of station"+ Person.getChosen_station()+" are");
        ArrayList<Bus> buses=Person.getBuses(Person.getChosen_station().getName());
        ArrayAdapter<Bus> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item
                ,buses);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l)->{
            Toast toast=Toast.makeText(getApplicationContext(),
                    buses.get(i).Info(),Toast.LENGTH_LONG);
            toast.show();
        });

        back.setOnClickListener(view -> {
            Intent activityChangeIntent=new Intent(ShowBusesActivity.this,StationBusesActivity.class);
            startActivity(activityChangeIntent);
        });

    }
}