package com.example.new_bus_application.domain_model;

import com.example.new_bus_application.domain_model.DAO.BusDAOAndroid;
import com.example.new_bus_application.domain_model.DAO.StationDAOAndroid;


import java.util.ArrayList;
import java.util.HashMap;

public class Person {
    private static String[] message = new String[1];
    private static Station chosen_station;
    private static Route chosen_route;
    private static Bus chosen_bus;

    private static String ending_station_name;

    public static String getMessage() {
        return message[0];
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
        Person.message[0] = message;
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

    public static void connect(Bus bus, Route route) {
        bus.addRoute(route);
        route.setBus(bus);
    }

    public static void connect(Route route, Station station) {
        route.addStation(station);
        station.addRoute(route);
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

    public static ArrayList<Station> getNearestStations(double latitude, double longitude) {
        ArrayList<Station> nearestStations = new ArrayList<>(3);
        ArrayList<Station> stations = StationDAOAndroid.ListStations();
        if(stations.size()<=3) return stations;
        for (int i = 0; i < 3; i++) {
            int nearestStation_index=-1;
            long distance=-1;
            for(int j=0; j<stations.size(); j++){
                if(!nearestStations.contains(stations.get(j))){
                    nearestStation_index = j;
                    //distance=Math.sqrt(Math.pow(stations.get(j).getLatitude()-latitude,2)+Math.pow(stations.get(j).getLongitude()-longitude,2));
                    distance=distanceV2(longDistance(stations.get(j).getLongitude(),longitude),longDistance(stations.get(j).getLatitude(),latitude));
                    break;
                }

            }

            for (int j = 0; j < stations.size(); j++) {
                //double next_distance=Math.sqrt(Math.pow(stations.get(j).getLatitude()-latitude,2)+Math.pow(stations.get(j).getLongitude()-longitude,2));
                long next_distance =distanceV2(longDistance(stations.get(j).getLongitude(),longitude),longDistance(stations.get(j).getLatitude(),latitude));
                if (!nearestStations.contains(stations.get(j)) && distance > next_distance) {
                    distance = next_distance;
                    nearestStation_index = j;
                }
            }
            nearestStations.add(stations.get(nearestStation_index));
        }
        return nearestStations;
    }

    private static boolean findRoute(String Start, String End, ArrayList<Route> t_routes, ArrayList<Station> t_stations,
                                    String[] message_, int ammount) {
        ArrayList<Route> Start_routes = new ArrayList<>();
        Station Start_station = StationDAOAndroid.getStations().get(Start);
        ArrayList<Route> End_routes = new ArrayList<>();
        Station End_station = StationDAOAndroid.getStations().get(End);
        Start_routes.addAll(Start_station.getRoutes());
        End_routes.addAll(End_station.getRoutes());
        for (Route route : subRoutes(Start_routes, t_routes)) {
            if (End_routes.contains(route)&&
                    route.getStations().indexOf(Start_station)<route.getStations().indexOf(End_station)) {
                message_[0] += route.getBus().getName() + "\n" + route.getName()
                        + "\n" + Start + "\n" + End + "\n \n";
                return true;
            }
        }
        if (ammount <= 1) return false;
        t_stations.add(Start_station);
        ArrayList<Route> subbed_routes= subRoutes(Start_routes, t_routes);
        for (Route route :subbed_routes) {
            ArrayList<Station> subbed_stations=subStation(route.getStations(), t_stations,Start_station);
            for (Station station : subbed_stations) {
                ArrayList<Station> parameter_t_stations = new ArrayList<>();
                parameter_t_stations.addAll(t_stations);
                String[] message_parameter = {message_[0] +  route.getBus().getName() + "\n" + route.getName()
                        + "\n" + Start + "\n" + station.getName() + "\n \n"};
                if (findRoute(station.getName(), End, addRoute(t_routes, route), parameter_t_stations,
                        message_parameter, ammount - 1)) {
                    message_[0] = message_parameter[0];
                    return true;
                }

            }
        }

        return false;
    }

    public static void checkRoutes(String Start, String End) {
        for (int ammount = 1; ammount <= 2*BusDAOAndroid.getBuses().size(); ammount++) {
            message[0] = "BUS\nROUTE\nEMBARKING STATION\nDISEMBARKING STATION\n \n";
            if (findRoute(Start, End, new ArrayList<Route>(), new ArrayList<Station>(), message, ammount))
                return ;
        }
        message[0]="There are no buses";

    }

    private static ArrayList<Station> subStation(ArrayList<Station> stations, ArrayList<Station> t_stations,Station begin) {
        ArrayList<Station> returned = new ArrayList<Station>();
        boolean add=false;
        for(Station station:stations) {
            if (station == begin) {
                add = true;
                continue;
            }
            if(add)
                returned.add(station);
        }
        returned.removeAll(t_stations);

        return returned;
    }

    private static ArrayList<Route> subRoutes(ArrayList<Route> routes, ArrayList<Route> t_routes) {
        ArrayList<Route> returned = new ArrayList<>();
        returned.addAll(routes);
        returned.removeAll(t_routes);
        return returned;
    }

    private static ArrayList<Route> addRoute(ArrayList<Route> routes, Route new_route) {
        ArrayList<Route> returned = new ArrayList<>();
        returned.addAll(routes);
        returned.add(new_route);
        return returned;
    }
    public static void initialize(){
        String stations_names[]={"KIFISSIA","KAT","MAROUSSI","NERATZIOTISSA","IRINI","IRAKLIO", "NEA IONIA","PEFKAKIA"
                ,"PERISSOS", "ANO PATISSIA","AGHIOS ELEFTHERIOS","KATO PATISSIA","AGHIOS NIKOLAOS","ATTIKI","VICTORIA"
                ,"OMONIA", "MONASTIRAKI","THISSIO","PETRALONA","TAVROS","KALITHEA","MOSCHATO","FALIRO","PIRAEUS"
                ,"ANTHOUPOLI", "PERISTERI","ΑGHΙΟΣ ΑΝΤOΝΙΟS","SEPOLIA","LARISSA STATION","METAXOURGHIO","PANEPISTIMIO"
                ,"SYNTAGMA", "ΑCROPOLI","SYNGROU - FIX","NEOS KOSMOS","AGHIOS IOANNIS","DAFNI","AGHIOS DIMITRIOS",
                "ILIOUPOLI","ALIMOS","ARGYROUPOLI","ELLINIKO","NIKAIA","KORYDALLOS","AGHIA VARVARA","AGHIA MARINA"
                ,"EGALEO","ELEONAS","KERAMEIKOS","EVANGELISMOS","MEGARO MOUSSIKIS","AMBELOKIPI","PANORMOU","KATEHAKI",
                "ETHNIKI AMYNA","HOLARGOS","NOMISMATOKOPIO","AGHIA PARASKEVI","HALANDRI","DOUK. PLAKENTIAS"};

        double [] latitudes={38.0736817,38.065922,38.056182,38.0450788,38.0437324,38.0462627,38.0398349,38.0371452,
                38.032728,38.0235953,38.0201134,38.011031,38.006919,37.9992951,37.9930663,37.984183,37.9760854,
                37.9767093,37.9686198,37.9626042,37.9604041,37.9550458,37.9449872,37.9481116,
                38.0171154,38.0131616,38.0066611,38.0026465,37.9919866,37.9862737,37.9803782,37.9745068,37.9687332,
                37.9645189,37.9569,37.9566294,37.9492026,37.940544,37.929805,37.9183,37.9030515,37.8925685,//3
                37.96571,37.978924,37.9899328,37.9972998,37.991956,37.9878938,37.97863,37.9760182,
                37.979287,37.987131,37.9931249,37.9931672,38.000054,38.004519,38.009274,38.0166993,38.021915,38.0245781};
        double [] longitudes={23.8061214,23.8018393,23.8027913,23.7909649,23.7805333,23.7617785,23.7510863,23.7479803,
                23.7424813,23.7335176,23.7296698,23.7269931,23.7255223,23.7199094,23.7282005,23.7265033,23.7234369,
                23.7185174,23.7070795,23.701346,23.6951434,23.6774801,23.6630364,23.6410834,
                23.6889085,23.693313,23.6972923,23.7113494,23.7186199,23.7189533,23.7308699,23.7330764,23.727412,
                23.7244829,23.72707,23.7324954,23.7350346,23.7385273,23.7422375,23.744,23.7371504,23.7383798,//3
                23.6385973,23.6382792,23.6551848,23.6652232,23.6796408,23.6919882,23.7093069,23.7444382,
                23.7507236,23.754881,23.7615041,23.7739632,23.7835623,23.7925343,23.8034782,23.8104653,23.8189825,23.832187};
        for(int i=0; i<stations_names.length; i++){
            StationDAOAndroid.AddStation(stations_names[i],new Station(stations_names[i],latitudes[i],longitudes[i]));
        }
        String [] route_names={"KIFISSIA-PIRAEUS","PIRAEUS-KIFISSIA","ANTHOUPOLI-ELLINIKO","ELLINIKO-ANTHOUPOLI",
                "NIKAIA-DOUK. PLAKENTIAS","DOUK. PLAKENTIAS-NIKAIA"};
        Route [] routes=new Route[route_names.length];
        for(int i=0; i<route_names.length; i++){
            routes[i]=new Route(route_names[i]);
        }
        String [][] lines_names={{"KIFISSIA","KAT","MAROUSSI","NERATZIOTISSA","IRINI","IRAKLIO", "NEA IONIA","PEFKAKIA"
                ,"PERISSOS", "ANO PATISSIA","AGHIOS ELEFTHERIOS","KATO PATISSIA","AGHIOS NIKOLAOS","ATTIKI","VICTORIA"
                ,"OMONIA", "MONASTIRAKI","THISSIO","PETRALONA","TAVROS","KALITHEA","MOSCHATO","FALIRO","PIRAEUS"},
                {"ANTHOUPOLI", "PERISTERI","ΑGHΙΟΣ ΑΝΤOΝΙΟS","SEPOLIA","ATTIKI","LARISSA STATION","METAXOURGHIO","OMONIA","PANEPISTIMIO"
                        ,"SYNTAGMA", "ΑCROPOLI","SYNGROU - FIX","NEOS KOSMOS","AGHIOS IOANNIS","DAFNI","AGHIOS DIMITRIOS",
                        "ILIOUPOLI","ALIMOS","ARGYROUPOLI","ELLINIKO"},
                {"NIKAIA","KORYDALLOS","AGHIA VARVARA","AGHIA MARINA"
                        ,"EGALEO","ELEONAS","KERAMEIKOS","MONASTIRAKI","SYNTAGMA","EVANGELISMOS","MEGARO MOUSSIKIS","AMBELOKIPI","PANORMOU","KATEHAKI",
                        "ETHNIKI AMYNA","HOLARGOS","NOMISMATOKOPIO","AGHIA PARASKEVI","HALANDRI","DOUK. PLAKENTIAS"}
        };
        int k=0;
        for(int i=0; i<6; i+=2){
            for(int j=0; j<lines_names[k].length; j++){
                Person.connect(routes[i],StationDAOAndroid.getStations().get(lines_names[k][j]));
            }
            for(int j=lines_names[k].length-1; j>-1; j--){
                Person.connect(routes[i+1],StationDAOAndroid.getStations().get(lines_names[k][j]));
            }
            k++;
        }
        k=0;
        String[] bus_names={"Line1","Line2","Line3"};
        String[] bus_codes={"000","111","222"};
        for(int i=0; i<3; i++){
            BusDAOAndroid.AddBus(bus_names[i],new Bus(bus_codes[i],bus_names[i]));
            for(int j=0; j<2; j++){
                Person.connect(BusDAOAndroid.getBuses().get(bus_names[i]),routes[k]);
                k++;
            }
        }

    }
    private static long longDistance(double x1,double x2){
        String x1_S=""+x1;
        String x2_S=""+x2;
        int x1Size=(x1_S.substring(x1_S.indexOf(".")+1)).length();
        int x2Size=(x2_S.substring(x2_S.indexOf(".")+1)).length();
        int max=Math.max(x1Size,x2Size);
        long x1_L=(long)(x1*(long)Math.pow(10,max));
        long x2_L=(long)(x2*(long)Math.pow(10,max));
        return x1_L-x2_L;

    }
    private static long distanceV2(long x,long y){
        return (long)(x*x)+(long)(y*y);

    }
}