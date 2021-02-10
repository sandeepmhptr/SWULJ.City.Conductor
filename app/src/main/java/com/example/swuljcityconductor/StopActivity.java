package com.example.swuljcityconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StopActivity extends AppCompatActivity {
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String Param = "PARAM";
    String Stop = "STOP";
    String busNumber, Next_stop;
    TextView txt;
    EditText next_stop;
    Button btn_next_stop;
    Button btn_done;
    int count_stops = 0;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        Intent i = getIntent();
        busNumber = i.getStringExtra(BUS_NUMBER);
        next_stop = (EditText) findViewById(R.id.next_stop);
        txt = (TextView) findViewById(R.id.txt);
        btn_next_stop = (Button) findViewById(R.id.btn_next_stop);
        btn_done = (Button) findViewById(R.id.btn_done);

        btn_next_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next_stop = next_stop.getText().toString();
                if(Next_stop.length() > 2)
                {
                    count_stops++;
                    list.add(Next_stop);
                    next_stop.setText("");
                }
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(list.size() > 2){
                    Intent i = new Intent(StopActivity.this, FaresActivity.class);
                    i.putExtra(BUS_NUMBER, busNumber);
                    i.putExtra(Param,count_stops);
                    for(int j = 1; j <= count_stops; j++){
                        i.putExtra(Stop+String.valueOf(j), list.get(j-1));
                    }
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}