package com.example.swuljcityconductor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class GenerateQrCodeActivity extends AppCompatActivity {
    ImageButton imgButtonGenerateBulk;
    ImageButton imgButtonGenerateSingle;
    DatabaseHelper myDb;
    EditText edit;
    EditText edit_error;
    EditText edit_total;
    boolean flag = false;
    String data_bus_number=null;
    String total_data=null;
    String ID_bus_number = null;
    int status = -1;



    void updateLastDataAsInvalid(String id,String number)
    {
        myDb.updateData(id,number,"route","city",0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);
        edit =  (EditText) findViewById(R.id.bus_number);
        edit_error =  (EditText) findViewById(R.id.error);
        edit_total = (EditText) findViewById(R.id.total);
        myDb = new DatabaseHelper(this,edit);
        //myDb.deleteDatabase();
        imgButtonGenerateBulk =(ImageButton)findViewById(R.id.imageButtonGenerateBulk);
        imgButtonGenerateSingle =(ImageButton)findViewById(R.id.imageButtonGenerateSingle);



        Cursor cursor = myDb.getLastData();
        edit_error.setText(String.valueOf(cursor.getCount()));
        flag = false;
        if(cursor!=null)
        {
            if (cursor.moveToFirst()) {
                do {
                    flag = true;

                    data_bus_number = cursor.getString(cursor.getColumnIndex("NUMBER"));
                    ID_bus_number = cursor.getString(cursor.getColumnIndex("ID"));
                    status = cursor.getInt(cursor.getColumnIndex("STATUS"));
                    total_data += ID_bus_number + "+" + data_bus_number + "+" + String.valueOf(status) + "+";
                    Toast.makeText(getBaseContext(), ID_bus_number, Toast.LENGTH_SHORT).show();
                    edit.setText(data_bus_number);
                    edit_total.setText(total_data);
                    // do what ever you want here
                } while (cursor.moveToNext());
            }
            cursor.close();
        }


        imgButtonGenerateBulk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = edit.getText().toString();

                if (data.length() > 6) {

                    if((data_bus_number==null) || (data.compareTo(data_bus_number)!=0))
                    {
                        if(myDb.insertData(data, "route", "city",1))
                            edit_error.setText("done"+data+"data_bus_number-different text"+ID_bus_number);
                        else
                        edit_error.setText("none");
                        if(data_bus_number!=null)
                            updateLastDataAsInvalid(ID_bus_number,data_bus_number);


                    }
                    else edit_error.setText(data_bus_number+"Same Text");


                    Intent i = new Intent(GenerateQrCodeActivity.this,
                            GenerateBulkActivity.class);
                    //Intent is used to switch from one activity to another.
                    i.putExtra("EXTRA_BUS_NUMBER", data);
                    startActivity(i);
                    //invoke the SecondActivity.
                }
            }
            });

        imgButtonGenerateSingle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data = edit.getText().toString();

                if (data.length() > 6) {

                    if((data_bus_number==null) || (data.compareTo(data_bus_number)!=0))
                    {
                        if(myDb.insertData(data, "route", "city",1))
                            edit_error.setText("done"+data+"data_bus_number-different text"+ID_bus_number);
                        else
                            edit_error.setText("none");
                        if(data_bus_number!=null)
                            updateLastDataAsInvalid(ID_bus_number,data_bus_number);


                    }
                    else edit_error.setText(data_bus_number+"Same Text");

                    Intent i = new Intent(GenerateQrCodeActivity.this,
                            GenerateSingleActivity.class);
                    //Intent is used to switch from one activity to another.
                    i.putExtra("EXTRA_BUS_NUMBER", data);
                    startActivity(i);
                    //invoke the SecondActivity.
                    }
                }
        });
    }
}