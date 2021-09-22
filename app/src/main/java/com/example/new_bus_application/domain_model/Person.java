package com.example.new_bus_application.domain_model;

import com.example.new_bus_application.domain_model.DAO.BusDAOAndroid;
import com.example.new_bus_application.domain_model.DAO.StationDAOAndroid;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Person {
    private static String message;
    private static Station chosen_station;
    private static Route chosen_route;
    private static Bus chosen_bus;

    public static String getMessage() {
        return message;
    }

    public static Station getChosen_station() {
        return chosen_station;
    }

    public static Route getChosen_route() {
        return chosen_route;
    }

    public static Bus getChosen_bus() {
        return chosen_bus;
    }

    public static void setMessage(String message) {
        Person.message = message;
    }

    public static void setChosen_station(Station chosen_station) {
        Person.chosen_station = chosen_station;
    }

    public static void setChosen_route(Route chosen_route) {
        Person.chosen_route = chosen_route;
    }

    public static void setChosen_bus(Bus chosen_bus) {
        Person.chosen_bus = chosen_bus;
    }

    public static ArrayList<Station> getStations(String bus_name) {
        HashMap<String, Bus> buses = BusDAOAndroid.getBuses();
        Bus bus = buses.get(bus_name);
        ArrayList<Station> stations = new ArrayList<>();
        for (Route route : bus.getRoutes())
            for (Station station : route.getStations())
                if (!stations.contains(station))
                    stations.add(station);
        return stations;
    }

    public static ArrayList<Bus> getBuses(String station_name) {
        HashMap<String, Station> stations = StationDAOAndroid.getStations();
        Station station = stations.get(station_name);
        ArrayList<Bus> buses = new ArrayList<>();
        for (Route route : station.getRoutes())
            if (!buses.contains(route.getBus()))
                buses.add(route.getBus());
        return buses;
    }

    public static ArrayList<Station> getNearestStations(double longitude,double latitude){
        ArrayList<Station> nearestStations=new ArrayList<>(3);
        ArrayList<Station> stations=StationDAOAndroid.ListStations();
        for(int i=0; i<3; i++){
            int nearestStation_index=0;
            double distance=Math.sqrt(Math.pow(stations.get(0).getLongitude()-longitude,2)+
                    Math.pow(stations.get(0).getLatitude()-latitude,2));
            for(int j=1; j<stations.size(); j++){
                double next_distance=Math.sqrt(Math.pow(stations.get(j).getLongitude()-longitude,2)+
                        Math.pow(stations.get(j).getLatitude()-latitude,2));
                if(!nearestStations.contains(stations.get(j))&&distance>next_distance){
                    distance=next_distance;
                    nearestStation_index=j;
                }
            }
            nearestStations.add(stations.get(nearestStation_index));
        }
        return nearestStations;
    }

    public static boolean findRoute(String Start,String End,ArrayList<Route> t_routes,ArrayList<Station> t_stations,
                                    String message_,int ammount){
        ArrayList<Route> Start_routes=new ArrayList<>();
        Station Start_station=StationDAOAndroid.getStations().get(Start);
        ArrayList<Route> End_routes=new ArrayList<>();
        Station End_station=StationDAOAndroid.getStations().get(End);
        Start_routes.addAll(Start_station.getRoutes());
        End_routes.addAll(End_station.getRoutes());
        for(Route route:subRoutes(Start_routes,End_routes)){
            if(End_routes.contains(route)){
                message_+="Go to The bus: "+route.getBus().getName()+" with route: "+route.getName()
                        +" embark at station:"+Start+" and disembark at station: "+End+"\n";
                return true;
            }
        }
        if(ammount<=1) return false;
        t_stations.add(Start_station);
            for(Route route: subRoutes(Start_routes,t_routes)){
                for(Station station: subStation(route.getStations(),t_stations)){
                    ArrayList<Station> parameter_t_stations=new ArrayList<>();
                    parameter_t_stations.addAll(t_stations);
                    if(findRoute(station.getName(),End,addRoute(t_routes,route),parameter_t_stations,
                            message_+"Go to The bus: "+route.getBus().getName()+" with route: "+route.getName() +
                                    " embark at station:"+Start+" and disembark at station: "
                                    +station.getName()+"\n",ammount-1)){
                            return true;
                    }

                }
            }

        return false;
    }
    public static boolean checkRoutes(String Start,String End){
        message="";
        for(int ammount=0; ammount<BusDAOAndroid.getBuses().size(); ammount++)
            if(findRoute(Start,End,new ArrayList<Route>(),new ArrayList<Station>(),message,ammount))
                return true;
        return false;
    }

    public static ArrayList<Station> subStation(ArrayList<Station> stations,ArrayList<Station> t_stations){
        ArrayList<Station> returned=new ArrayList<Station>();
        returned.addAll(stations);
        returned.removeAll(t_stations);
        return returned;
    }
    public static ArrayList<Route> subRoutes(ArrayList<Route> routes,ArrayList<Route> t_routes){
        ArrayList<Route> returned=new ArrayList<>();
        returned.addAll(routes);
        returned.removeAll(t_routes);
        return returned;
    }
    public static ArrayList<Route> addRoute(ArrayList<Route> routes, Route new_route){
        ArrayList<Route> returned=new ArrayList<>();
        returned.addAll(routes);
        returned.add(new_route);
        return returned;
    }
}
