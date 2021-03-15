package com.example.swuljcityconductor;

public class PostData {
    String url;
    String busNumber;
    int countStops;
    int countPairs;
    String [] Stops;
    String[][] fares;
    PostData(String urlArg, String  busNumberArg, int countStopsArg, int countPairsArg, String [] stopsArg, String[][] faresArg)
    {
        url = urlArg;
        busNumber = busNumberArg;
        countStops = countStopsArg;
        countPairs = countPairsArg;
        Stops = stopsArg;
        int c=0;
        fares = new String [faresArg.length][];
        for(String[] array:faresArg){
            fares[c++]=array;
        }
    }
}
