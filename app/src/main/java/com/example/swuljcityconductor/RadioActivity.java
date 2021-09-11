package com.example.swuljcityconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class RadioActivity extends AppCompatActivity {
    Button btn;
    QRResultData datum = null;
    String BUS_NUMBER = "EXTRA_BUS_NUMBER";
    String busNumber;
    String stackServer = "https://swulj.000webhostapp.com/direction.php";
    EditText HTTPResult;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        Intent i = getIntent();
        busNumber = i.getStringExtra(BUS_NUMBER);

        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        // add 20 radio buttons to the group

        RadioButton newRadioButton = new RadioButton(this);
        String label = "On-Direction";
        newRadioButton.setText(label);
        newRadioButton.setId(0);
        radiogroup.addView(newRadioButton, layoutParams);

        newRadioButton = new RadioButton(this);
        label = "Return-Direction";
        newRadioButton.setText(label);
        newRadioButton.setId(1);
        radiogroup.addView(newRadioButton, layoutParams);

        btn = (Button) findViewById(R.id.btn_up);
        HTTPResult = (EditText)findViewById(R.id.HTTPResult) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
                int a = radiogroup.getCheckedRadioButtonId();

                datum = new QRResultData();
                datum.busNumber = busNumber;
                datum.flag = convertSelectionToChar(a);
                datum.url = stackServer;
                HTTPConnection1 conn = new HTTPConnection1();
                conn.execute(datum);
            }
        });

    }

    public String convertSelectionToChar(int a) {
        return ((a == 0 )? "Y" : "N");
    }
    class HTTPConnection1  extends AsyncTask<QRResultData, Void, String> {
        String result;
        String url;
        String busNumber;
        String flag;
        @Override
        protected String doInBackground(QRResultData... params) {
            QRResultData data= params[0];
            url = data.url;
            busNumber = data.busNumber;
            flag = data.flag;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("busNumber", busNumber));
                nameValuePairs.add(new BasicNameValuePair("flag", flag));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpclient.execute(httppost);
                InputStream inputStream = httpResponse.getEntity().getContent();
                //HTTPResult.setText("result3");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                //HTTPResult.setText("result4");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //HTTPResult.setText("result5");
                StringBuilder stringBuilder = new StringBuilder();
                //HTTPResult.setText("result6");
                String bufferedStrChunk = null;

                while((bufferedStrChunk = bufferedReader.readLine()) != null){
                    stringBuilder.append(bufferedStrChunk);
                }


                result = stringBuilder.toString();
                //result= "Sandeep";

            } catch (ClientProtocolException e) {
                result = "ClientProtocolException";
                // TODO Auto-generated catch block
            } catch (IOException e) {
                result = "IOException";
                // TODO Auto-generated catch block
            }
            return null;
        }
        @Override
        protected void onPostExecute(String bitmap) {
            super.onPostExecute(bitmap);
            HTTPResult.setText(result);
        }

    }
}