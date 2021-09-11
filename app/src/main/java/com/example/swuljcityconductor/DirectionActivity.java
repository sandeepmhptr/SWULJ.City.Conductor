package com.example.swuljcityconductor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DirectionActivity extends AppCompatActivity implements View.OnClickListener {
    Button scanBtn;
    TextView messageText, messageFormat, messageText2, messageFormat2, HTTPResult;
    String Result = null, busNumber = null, seatNumber = null;
    //QRResultData datum;
    String BUS_NUMBER = "EXTRA_BUS_NUMBER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        scanBtn = findViewById(R.id.scanBtn);
        messageText = findViewById(R.id.textContent);
        messageFormat = findViewById(R.id.textFormat);
        messageText2 = findViewById(R.id.textContent2);
        messageFormat2 = findViewById(R.id.textFormat2);
        HTTPResult = findViewById(R.id.HTTPResult);
        // adding listener to the button
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
        //showDialog(DirectionActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                Result = intentResult.getContents();

                busNumber = parseResult(Result);


                //messageText.setText(Result);
                //messageFormat.setText(intentResult.getFormatName());
                messageText.setText(seatNumber);
                messageFormat.setText(busNumber);
                //messageText2.setText(datum.original);
                messageFormat2.setText(Result);

                //Toast.makeText(getApplicationContext(),"You download is resumed2",Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Intent inte = new Intent(DirectionActivity.this,
                RadioActivity.class);
        HTTPResult.setText("setting httpresult, busNumber = " +busNumber);
        //Intent is used to switch from one activity to another.
        inte.putExtra(BUS_NUMBER, busNumber);
        startActivity(inte);
        //invoke the SecondActivity.

        finish();
    }

    public String parseResult(String Result) {
        String[] split = Result.split(";");
        busNumber = split[0];
        if (split.length > 1) {
            seatNumber = split[1];
        }
        return busNumber;
    }
}