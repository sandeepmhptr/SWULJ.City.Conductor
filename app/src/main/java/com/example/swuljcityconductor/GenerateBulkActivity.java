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

public class GenerateBulkActivity extends AppCompatActivity {
    //private static final Object BUS_NUMBER = ;
    EditText edit_prefix;
    EditText edit_test1;
    EditText edit_seat_serial_start_number;
    EditText edit_seat_serial_end_number;
    Button btn_Generate;
    String busNumber;
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String str_seat_prefix = null;
    String str_seat_serial_start_number = null;
    String str_seat_serial_end_number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_bulk);
        Intent intent=getIntent();
        busNumber = intent.getStringExtra(BUS_NUMBER);
        edit_prefix = (EditText) findViewById(R.id.seat_serial_prefix);

        edit_seat_serial_start_number = (EditText) findViewById(R.id.seat_serial_start_number);
        edit_test1 = (EditText) findViewById(R.id.test1);

        edit_seat_serial_end_number = (EditText) findViewById(R.id.seat_serial_end_number);

        btn_Generate = (Button) findViewById(R.id.button_Generate1);

        btn_Generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // it was the 1st button
                edit_test1.setText(busNumber+"+"+"working");
                str_seat_prefix = edit_prefix.getText().toString();
                str_seat_serial_start_number = edit_seat_serial_start_number.getText().toString();
                str_seat_serial_end_number = edit_seat_serial_end_number.getText().toString();
                if((str_seat_prefix.length()>6)
                        &&(str_seat_serial_start_number.length()>1)
                &&(str_seat_serial_end_number.length()>1))
                {
                    edit_test1.setText("inside working");
                    Intent i = new Intent(GenerateBulkActivity.this,
                            QrCodeResultActivity.class);
                    //Intent is used to switch from one activity to another.
                    i.putExtra("EXTRA_BUS_NUMBER", busNumber);
                    i.putExtra("EXTRA_PARAMETER_NUMBER", String.valueOf(3));
                    i.putExtra("EXTRA_SEAT_PREFIX", str_seat_prefix);
                    i.putExtra("EXTRA_SEAT_SERIAL_START_NUMBER", str_seat_serial_start_number);
                    i.putExtra("EXTRA_SEAT_SERIAL_END_NUMBER", str_seat_serial_end_number);
                    startActivity(i);
                    //invoke the SecondActivity.
                }

            }
        });
        //btn_Generate.setOnClickListener(btn_Generatehandler);
    }


    /*View.OnClickListener btn_Generatehandler = new View.OnClickListener() {
        public void onClick(View v) {
            // it was the 1st button

        }
    };*/
}