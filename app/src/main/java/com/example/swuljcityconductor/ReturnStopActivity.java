package com.example.swuljcityconductor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReturnStopActivity extends AppCompatActivity {
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String Param1 = "PARAM_ON_DIRECTION";
    String Stop1 = "STOP_ON_DIRECTION";
    String Param2 = "PARAM_RETURN_DIRECTION";
    String Stop2 = "STOP_RETURN_DIRECTION";
    String res = null, res2 = null;
    String busNumber, Next_stop;
    TextView txt;
    EditText next_stop;
    EditText result1, result2, result;
    Button btn_next_stop;
    Button btn_done;
    int count_stops1 = 0, count_stops2 = 0;
    List<String> list = new ArrayList<>();
    String [] Stops;
    String temp3 = null;
    ReturnStopFragment myf;
    Context cntxt = this;
    ReturnStopActivity obj =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_stop);

        final FrameLayout frameLayout =(FrameLayout) findViewById(R.id.frameLayout);;
        result1 = (EditText) findViewById(R.id.result1);
        result2 = (EditText) findViewById(R.id.result2);
        Intent i = getIntent();
        busNumber = i.getStringExtra(BUS_NUMBER);
        String temp = i.getStringExtra(Param1);
        count_stops1 = Integer.valueOf(temp);

        Stops = new String[count_stops1];

        for(int x = 1; x <= count_stops1; x++){
            String temp2 = Stop1 + String.valueOf(x);
            Stops[x-1] = i.getStringExtra(temp2);
            temp3 += ";";
            temp3 += Stops[x-1];
        }


        next_stop = (EditText) findViewById(R.id.next_stop);
        txt = (TextView) findViewById(R.id.txt);
        btn_next_stop = (Button) findViewById(R.id.btn_next_stop);
        btn_done = (Button) findViewById(R.id.btn_done);
        result = (EditText) findViewById(R.id.result__);

        btn_next_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next_stop = next_stop.getText().toString();
                if(Next_stop.length() > 2)
                {
                    count_stops2++;
                    list.add(Next_stop);
                    next_stop.setText("");
                    res += Next_stop;
                    result.setText(res);
                }
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                res = null;
                myf = new ReturnStopFragment(cntxt, count_stops2, list, obj);
                frameLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, myf);
                transaction.commit();
                result1.setText(String.valueOf(count_stops2));

            }
        });
    }
    void changeIntent(int Args)
    {
        //count_stops2 = Args;
        if(list.size() > 2){
            Intent i = new Intent(ReturnStopActivity.this, FaresActivity.class);
            i.putExtra(BUS_NUMBER, busNumber);
            i.putExtra(Param1,String.valueOf(count_stops1));
            i.putExtra(Param2,String.valueOf(count_stops2));


            for(int j = 1; j <= count_stops1; j++){
                String temp = Stop1 + String.valueOf(j);
                i.putExtra(temp, Stops[j-1]);
                res2 +=";";
                res2 += Stops[j-1];
            }
            res = "";
            for(int j = 1; j <= count_stops2; j++){
                String temp = Stop2 + String.valueOf(j);
                i.putExtra(temp, list.get(j-1));
                res +=";";
                res += list.get(j-1);
            }
            result2.setText(res);
            result1.setText(String.valueOf(count_stops2+1));
            startActivity(i);
            finish();
        }

    }
}