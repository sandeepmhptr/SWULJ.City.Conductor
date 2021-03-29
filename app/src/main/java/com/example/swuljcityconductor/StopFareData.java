package com.example.swuljcityconductor;

public class StopFareData {
    String Source;
    String Destination;
    String fare;
    StopFareData(String SourceArg, String DestinationArg, String fareArg)
    {
        Source = SourceArg;
        Destination = DestinationArg;
        fare = fareArg;
    }
}
