package com.example.new_bus_application;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.new_bus_application.domain_model.Person;
import com.example.new_bus_application.domain_model.Station;


import androidx.annotation.RequiresApi;
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
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_stations);

        requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
        requestPermissions(CONTACTS_PERMS, CONTACTS_REQUEST);
        requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        ListView listView = findViewById(R.id.stations);
        Button back = findViewById(R.id.back);
        double latitude, longitude;
        Location location = getLastKnownLocation();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        nearest_stations = Person.getNearestStations(latitude, longitude);
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