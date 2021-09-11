package com.example.swuljcityconductor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton imgButtonGenerateQrCode;
    ImageButton Register;
    ImageButton imgButton_bus_changeDirection;
    Context cntxt = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        imgButton_bus_changeDirection =(ImageButton)findViewById(R.id.imgButton_bus_changeDirection);
        imgButtonGenerateQrCode =(ImageButton)findViewById(R.id.imageButtonGenerateQrCode);
        Register = (ImageButton) findViewById(R.id.Register);

        imgButton_bus_changeDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final CharSequence[] items = {"On-Direction", "Return-Direction"};
                AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);
                builder.setTitle("Alert Dialog with ListView and Radio button");
                //builder.setIcon(R.drawable.icon);
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(cntxt, "Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(cntxt, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainActivity.this,
                        DirectionActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        });


        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,
                        RegisterActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.
                finish();
            }
        });

        imgButtonGenerateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,
                        GenerateQrCodeActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.
                finish();
            }
        });
    }
}