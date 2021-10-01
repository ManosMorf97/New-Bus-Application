package com.example.new_bus_application;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Person;
import com.example.new_bus_application.domain_model.Station;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NearestStationsActivity extends AppCompatActivity {
    LocationManager mLocationManager;

    ArrayList<Station> nearest_stations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_stations);



        ListView listView = findViewById(R.id.stations);
        Button back = findViewById(R.id.back);
        double latitude, longitude;
        Location location = getLastKnownLocation();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        nearest_stations = Person.getNearestStations(latitude,longitude);
        ArrayAdapter<Station> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item
                , nearest_stations);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast toast = Toast.makeText(getApplicationContext(),
                    nearest_stations.get(i).Info(), Toast.LENGTH_LONG);
            toast.show();

        });


        back.setOnClickListener(view -> {
            Intent activityChangeIntent = new Intent(NearestStationsActivity.this, UserActivity.class);
            startActivity(activityChangeIntent);
        });
    }
    //Stack Overflow https://stackoverflow.com/questions/20438627/getlastknownlocation-returns-null
    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return bestLocation;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
    //end

}