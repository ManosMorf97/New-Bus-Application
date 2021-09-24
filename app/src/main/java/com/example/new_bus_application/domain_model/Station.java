package com.example.new_bus_application.domain_model;

import java.util.ArrayList;

public class Station{
    private ArrayList<Route> routes=new ArrayList<>();
    private final double longitude;
    private final double latitude;
    private final String name;
    public Station(String name,double latitude,double longitude){
        this.name=name;
        this.longitude=longitude;
        this.latitude=latitude;
    }
    public double getLongitude(){
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public String getName(){
        return name;
    }
    public void addRoute(Route route){
        routes.add(route);
    }
    public ArrayList<Route> getRoutes(){
        return routes;
    }
}