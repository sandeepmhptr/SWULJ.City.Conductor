package com.example.swuljcityconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    Button btn;
    EditText bus_number;
    TextView txt;
    String num = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = (Button) findViewById(R.id.btn);
        bus_number = (EditText)findViewById(R.id.bus_number);
        txt = (TextView) findViewById(R.id.text_view_id);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = (String) bus_number.getText().toString();
                if(num.length()>2){
                    Intent i = new Intent(RegisterActivity.this,StopActivity.class);
                    i.putExtra("EXTRA_BUS_NUMBER", num);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}