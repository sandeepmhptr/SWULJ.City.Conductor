package com.example.swuljcityconductor;

import java.util.ArrayList;
import java.util.List;

public class PostData {
    String url;
    String busNumber;
    int countStops1, countStops2;
    int countPairs;
    String [] Stops1,Stops2;
    String[][] fares;
    List<StopFareData> list = new ArrayList<>();
    PostData(String urlArg, String  busNumberArg, int countStopsArg1, int countStopsArg2, String [] stopsArg1, String [] stopsArg2, int countPairsArg,  List<StopFareData> listArg)
    {
        url = urlArg;
        busNumber = busNumberArg;
        countStops1 = countStopsArg1;
        countStops2 = countStopsArg2;
        countPairs = countPairsArg;
        Stops1 = stopsArg1;
        Stops2 = stopsArg2;
        list = listArg;
    }
}
