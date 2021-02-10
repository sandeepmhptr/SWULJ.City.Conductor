package com.example.swuljcityconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FaresActivity extends AppCompatActivity {
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String Param = "PARAM";
    String Stop = "STOP";
    int count_stops = 0;
    int TwoDArrayCounter = 0;
    int TwoDCounter =0;
    String busNumber;
    String [] Stops;
    String[][] fares;
    int l =0, j=1;
    TextView txt, source_stop, destination_stop;
    EditText fare, result;
    Button next_fare, Register, Skip_registration;
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fares);

        txt = (TextView) findViewById(R.id.txt);
        source_stop = (TextView) findViewById(R.id.source_stop);
        destination_stop = (TextView) findViewById(R.id.destination_stop);
        fare = (EditText) findViewById(R.id.fares_input);
        next_fare = (Button) findViewById(R.id.btn_next_fare);
        Register = (Button) findViewById(R.id.btn_register);
        Skip_registration = findViewById(R.id.btn_skip_registration);
        result = (EditText) findViewById(R.id.result);

        Skip_registration.setVisibility(View.GONE);
        Register.setVisibility(View.GONE);

        source_stop.setText("Source Stop is:" + Stops[l]);
        destination_stop.setText("Destination Stop is:" + Stops[j]);

        Stops = new String[count_stops];

        Intent i = getIntent();

        busNumber = i.getStringExtra(BUS_NUMBER);
        count_stops = Integer.valueOf(i.getStringExtra(Param));
        for(int x = 0; x < count_stops; x++){
            Stops[x-1] = i.getStringExtra(Stop+ String.valueOf(x));
        }

        for(int x = count_stops - 1; x > 0; x--){
            TwoDArrayCounter += x;
        }
        TwoDArrayCounter *= 2;
        fares = new String[TwoDArrayCounter][3];

        next_fare.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(flag){
                    flag = false;
                    source_stop.setText("Source Stop is:" + Stops[j]);
                    destination_stop.setText("Destination Stop is:" + Stops[l]);
                    fares[TwoDCounter] = new String[3];
                    fares[TwoDCounter][0] = Stops[l];
                    fares[TwoDCounter][0] = Stops[j];
                    fares[TwoDCounter][0] = (String)fare.getText().toString();
                    TwoDCounter++;
                    fares[TwoDCounter] = new String[3];
                    fares[TwoDCounter][0] = Stops[j-1];
                    fares[TwoDCounter][0] = Stops[l-1];
                    fares[TwoDCounter][0] = (String)fare.getText().toString();
                    TwoDCounter++;
                }
                else{
                    flag = false;
                    j++;
                    if(j == count_stops - 1){
                        l++;
                        j = l + 1;
                    }
                    if(l == count_stops - 1){
                        //hide button
                        next_fare.setVisibility(View.GONE);
                        Register.setVisibility(View.VISIBLE);
                        Skip_registration.setVisibility(View.VISIBLE);
                    }
                    source_stop.setText("Source Stop is:" + Stops[l]);
                    destination_stop.setText("Destination Stop is:" + Stops[j]);

                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String res= "";
                for(int i = 0; i < TwoDArrayCounter; i++){
                    for(int j = 0; j < 3; j++){
                        res += fares[i][j];
                    }
                }

                result.setText(res);
            }
        });
    }
}