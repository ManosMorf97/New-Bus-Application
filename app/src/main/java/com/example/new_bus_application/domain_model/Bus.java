package com.example.new_bus_application.domain_model;

import java.util.ArrayList;

public class Bus {
    private final String code;
    private  final String name;
    private ArrayList<Route> routes=new ArrayList<>(2);
    public Bus(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getCode(){
        return  code;
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
