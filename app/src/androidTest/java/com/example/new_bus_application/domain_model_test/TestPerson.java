package com.example.new_bus_application.domain_model_test;
import com.example.new_bus_application.domain_model.Bus;
import com.example.new_bus_application.domain_model.DAO.BusDAOAndroid;
import com.example.new_bus_application.domain_model.DAO.StationDAOAndroid;
import com.example.new_bus_application.domain_model.Person;
import com.example.new_bus_application.domain_model.Route;
import com.example.new_bus_application.domain_model.Station;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.example.new_bus_application.domain_model.Person.*;

public class TestPerson {
    @Before
    public void init(){
        String [] stationnames={"A","B","C","D","A2","B2","A3","B3"};
        int [] longitudes={0,1,2,3,4,5,6,7,8};
        int [] latitudes={0,1,2,3,4,5,6,7,8};
        for(int i=0; i<8; i++)
            StationDAOAndroid.AddStation(stationnames[i],new Station(stationnames[i],longitudes[i],latitudes[i]));
        Route[] routes=new Route[6];
        String [] name_routes={"A-D","D-A","A2-B2","B2-A2","D-B3","B3-D"};
        for(int i=0; i<6; i++)
            routes[i]=new Route(name_routes[i]);
        String [][] routeStations={{"A","B","C","D"},{"D","C","B","A"},{"A2","C","B2"},{"B2","C","A2"},
                {"D","A3","B3"},{"B3","A3","D"}};
        for(int i=0; i<6; i++)
            for(int j=0; j<routeStations[i].length; j++)
                Person.connect(routes[i],StationDAOAndroid.getStations().get(routeStations[i][j]));
        String [] busnames={"Line1","Line2","Line3"};
        String [] buscodes={"0","1","2"};
        int j=0;
        for(int i=0; i<3; i++){
            BusDAOAndroid.AddBus(busnames[i],new Bus(buscodes[i],busnames[i]));
            for(int k=0; k<2; k++) {
                Person.connect(BusDAOAndroid.getBuses().get(busnames[i]), routes[j]);
                j++;
            }
        }
    }
    @Test
    public void checkConnection(){
        Station station=StationDAOAndroid.getStations().get("A");
        Route route=station.getRoutes().get(0);
        Station station2=route.getStations().get(0);
        Assert.assertTrue(station==station2);
        Bus bus=BusDAOAndroid.getBuses().get("Line1");
        route=bus.getRoutes().get(0);
        Bus bus2=route.getBus();
        Assert.assertTrue(bus==bus2);
    }
    @Test
    public void testGetStations(){
        ArrayList<Station> stations=Person.getStations("Line1");
        Assert.assertEquals(stations.size(),4);
        String [] station_names={"A","B","C","D"};
        for(int i=0; i<4; i++)
            Assert.assertTrue(StationDAOAndroid.getStations().get(station_names[i])==stations.get(i));
    }
    @Test
    public void testGetBuses(){
        ArrayList<Bus> buses=Person.getBuses("C");
        Assert.assertEquals(buses.size(),2);
        String [] bus_names={"Line1","Line2"};
        for(int i=0; i<2; i++)
            Assert.assertTrue(BusDAOAndroid.getBuses().get(bus_names[i])==buses.get(i));
    }
    @Test
    public void testNearestStations(){
        ArrayList<Station> stations=getNearestStations(1,1);
        Assert.assertEquals(stations.size(),3);
        String [] station_names={"B","A","C"};
        for(int i=0; i<3; i++)
            Assert.assertTrue(StationDAOAndroid.getStations().get(station_names[i])==stations.get(i));

    }

}
