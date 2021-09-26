package com.example.new_bus_application.domain_model;

import java.util.ArrayList;
import java.util.Comparator;

public class HelpComparator implements Comparator<HelpComparator> {
    private final String name;
    protected ArrayList<Route> routes;

    public HelpComparator(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void addRoute(Route route){
        routes.add(route);
    }
    public ArrayList<Route> getRoutes(){
        return routes;
    }


    @Override
    public int compare(HelpComparator helpComparator, HelpComparator helpComparator2) {
        return helpComparator.getName().compareTo(helpComparator2.getName());
    }
}
