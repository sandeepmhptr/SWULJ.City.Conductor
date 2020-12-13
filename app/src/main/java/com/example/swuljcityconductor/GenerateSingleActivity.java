package com.example.swuljcityconductor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GenerateSingleActivity extends AppCompatActivity {

    EditText edit_seat_serial_number;
    EditText bus_number;
    Button btn_Generate;
    String busNumber = null;
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";

    String str_seat_serial_number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_single);
        Intent intent=getIntent();
        busNumber = intent.getStringExtra(BUS_NUMBER);


        btn_Generate = (Button) findViewById(R.id.button_Generate2);
        edit_seat_serial_number = (EditText) findViewById(R.id.seat_serial);
        bus_number = (EditText) findViewById(R.id.bus_number);
        bus_number.setText(busNumber);



        btn_Generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // it was the 1st button
                str_seat_serial_number = edit_seat_serial_number.getText().toString();
                edit_seat_serial_number.setText(busNumber);
                if((str_seat_serial_number.length()>6))
                {
                    Intent i = new Intent(GenerateSingleActivity.this,
                            QrCodeResultActivity.class);
                    //Intent is used to switch from one activity to another.
                    i.putExtra("EXTRA_BUS_NUMBER", busNumber);
                    i.putExtra("EXTRA_PARAMETER_NUMBER", String.valueOf(1));
                    i.putExtra("EXTRA_SEAT_PREFIX", str_seat_serial_number);

                    startActivity(i);
                    //invoke the SecondActivity.
                }

            }
        });
        //btn_Generate.setOnClickListener(btn_Generatehandler);
    }
}
