package com.example.swuljcityconductor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class QrCodeResultActivity extends AppCompatActivity {
    String busNumber;
    String str_seat_prefix = null;
    String str_seat_serial_start_number = null;
    String str_seat_serial_end_number = null;
    String str_seat_serial_number = null;
    int parameterNumber;
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String PARAMETER_NUMBER = "EXTRA_PARAMETER_NUMBER";
    String SEAT_PREFIX = "EXTRA_SEAT_PREFIX";
    String SEAT_SERIAL_START_NUMBER = "EXTRA_SEAT_SERIAL_START_NUMBER";
    String SEAT_SERIAL_END_NUMBER = "EXTRA_SEAT_SERIAL_END_NUMBER";
    EditText edit_Total_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_result);
        edit_Total_text = (EditText) findViewById(R.id.total_text);
        Intent intent=getIntent();
        busNumber = intent.getStringExtra(BUS_NUMBER);
        parameterNumber = Integer.valueOf(intent.getStringExtra(PARAMETER_NUMBER));
        switch(parameterNumber)
        {
            case 1:
                str_seat_serial_number = intent.getStringExtra(SEAT_PREFIX);
                edit_Total_text.setText(busNumber+ ";" +str_seat_serial_number);
                break;
            case 3:
                str_seat_prefix = intent.getStringExtra(SEAT_PREFIX);
                str_seat_serial_start_number = intent.getStringExtra(SEAT_SERIAL_START_NUMBER);
                str_seat_serial_end_number = intent.getStringExtra(SEAT_SERIAL_END_NUMBER);
                edit_Total_text.setText(busNumber + ";" + str_seat_prefix + ";" + str_seat_serial_start_number + ";" + str_seat_serial_end_number);
                break;
        }
    }
}