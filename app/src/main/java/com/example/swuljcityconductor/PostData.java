package com.example.swuljcityconductor;

import java.util.ArrayList;
import java.util.List;

public class PostData {
    String url;
    String busNumber;
    int countStops;
    int countPairs;
    String [] Stops;
    String[][] fares;
    List<StopFareData> list = new ArrayList<>();
    PostData(String urlArg, String  busNumberArg, int countStopsArg, int countPairsArg, String [] stopsArg, List<StopFareData> listArg)
    {
        url = urlArg;
        busNumber = busNumberArg;
        countStops = countStopsArg;
        countPairs = countPairsArg;
        Stops = stopsArg;
        int c=0;
        list = listArg;

    }
}
