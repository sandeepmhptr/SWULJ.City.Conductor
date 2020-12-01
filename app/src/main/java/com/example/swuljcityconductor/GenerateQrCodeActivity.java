package com.example.swuljcityconductor;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;

public class GenerateQrCodeActivity extends AppCompatActivity {
    ImageButton imgButtonGenerateBulk;
    ImageButton imgButtonGenerateSingle;
    SQLiteDatabase db;
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);

        imgButtonGenerateBulk =(ImageButton)findViewById(R.id.imageButtonGenerateBulk);
        imgButtonGenerateSingle =(ImageButton)findViewById(R.id.imageButtonGenerateSingle);

        imgButtonGenerateBulk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GenerateQrCodeActivity.this,
                        GenerateBulkActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.
            }
        });

        imgButtonGenerateSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GenerateQrCodeActivity.this,
                        GenerateSingleActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.
            }
        });
    }
}