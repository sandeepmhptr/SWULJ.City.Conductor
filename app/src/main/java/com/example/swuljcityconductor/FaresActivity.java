package com.example.swuljcityconductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class FaresActivity extends AppCompatActivity {
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String Param1 = "PARAM_ON_DIRECTION";
    String Stop1 = "STOP_ON_DIRECTION";
    String Param2 = "PARAM_RETURN_DIRECTION";
    String Stop2 = "STOP_RETURN_DIRECTION";

    int count_stops1 = 0, count_stops2 = 0;
    int NumPairStops1 = 0, NumPairStops2 = 0;
    int StopPair =0;
    String busNumber;
    String temp3 = null, temp4 = null;
    String [] Stops1, Stops2;
    String[][] fares;
    int FirstStop =0, SecondStop=1;
    TextView txt, source_stop, destination_stop;
    EditText fare, result1, result2, HTTPResult;
    Button next_fare, More_Bus, Skip_To_Main;
    boolean flag = true;
    String stackServer = "https://swulj.000webhostapp.com/new.php";
    static final int REQUEST = 112;
    Context mContext = this;
    FaresFragment myf;
    Context cntxt = this;
    FaresActivity obj = this;
    List<StopFareData> list = new ArrayList<>();
    StopFareData datum = null;
    boolean stopFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fares);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        txt = (TextView) findViewById(R.id.txt);
        source_stop = (TextView) findViewById(R.id.source_stop);
        destination_stop = (TextView) findViewById(R.id.destination_stop);
        fare = (EditText) findViewById(R.id.fares_input);
        next_fare = (Button) findViewById(R.id.btn_next_fare);
        More_Bus = (Button) findViewById(R.id.btn_more_bus);
        Skip_To_Main = findViewById(R.id.btn_skip_Main_Page);
        result1 = (EditText) findViewById(R.id.result1);
        result2 = (EditText) findViewById(R.id.result2);
        HTTPResult = (EditText) findViewById(R.id.HTTP_response);
        Skip_To_Main.setVisibility(View.GONE);
        More_Bus.setVisibility(View.GONE);


        Intent i = getIntent();

        busNumber = i.getStringExtra(BUS_NUMBER);
        String temp = i.getStringExtra(Param1);
        count_stops1 = Integer.valueOf(temp);
        temp = i.getStringExtra(Param2);
        count_stops2 = Integer.valueOf(temp);

        Stops1 = new String[count_stops1];
        Stops2 = new String[count_stops2];

        for(int x = 1; x <= count_stops1; x++){
            String temp2 = Stop1 + String.valueOf(x);
            Stops1[x-1] = i.getStringExtra(temp2);

            temp3 += ";";
            temp3 += Stops1[x-1];
        }
        result1.setText(temp3);

        for(int x = 1; x <= count_stops2; x++){
            String temp2 = Stop2 + String.valueOf(x);
            Stops2[x-1] = i.getStringExtra(temp2);
            temp4 += ";";
            temp4 += Stops2[x-1];

        }
        //result2.setText(String.valueOf(count_stops2));
        result2.setText(temp4);

        source_stop.setText("Source Stop is:" + Stops1[FirstStop]);
        destination_stop.setText("Destination Stop is:" + Stops1[SecondStop]);

        for(int x = count_stops1 - 1; x > 0; x--){
            NumPairStops1 += x;
        }
        //NumPairStops1 *= 2;

        for(int x = count_stops2 - 1; x > 0; x--){
            NumPairStops2 += x;
        }
        //NumPairStops2 *= 2;

        fares = new String[NumPairStops1 + NumPairStops2][3];


        next_fare.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!stopFlag) {
                    datum = new StopFareData(Stops1[FirstStop], Stops1[SecondStop], (String) fare.getText().toString());
                    list.add(datum);
                    SecondStop++;
                    if ((FirstStop != count_stops1 - 1) && (SecondStop == count_stops1)) {
                        FirstStop++;
                        SecondStop = FirstStop + 1;
                    }
                    if (FirstStop == count_stops1 - 1) {
                        FirstStop = 0;
                        SecondStop = 0;
                        stopFlag = true;
                        fare.setText("");
                        source_stop.setText("Source Stop is:" + Stops2[FirstStop]);
                        destination_stop.setText("Destination Stop is:" + Stops2[SecondStop + 1]);
                    } else {
                        fare.setText("");
                        //fare.setText("FirstStop =" + String.valueOf(FirstStop)+"; count_stops ="+ String.valueOf(count_stops));
                        source_stop.setText("Source Stop is:" + Stops1[FirstStop]);
                        destination_stop.setText("Destination Stop is:" + Stops1[SecondStop]);
                    }
                }
                else{
                    SecondStop++;
                    datum = new StopFareData(Stops2[FirstStop], Stops2[SecondStop], (String) fare.getText().toString());
                    list.add(datum);

                    if ((FirstStop != count_stops1 - 1) && (SecondStop == count_stops1 - 1)) {
                        FirstStop++;
                        SecondStop = FirstStop ;
                    }
                    if (FirstStop == count_stops1 - 1) {

                        //hide button
                        next_fare.setVisibility(View.GONE);
                        More_Bus.setVisibility(View.VISIBLE);
                        Skip_To_Main.setVisibility(View.VISIBLE);

                        myf = new FaresFragment(cntxt, NumPairStops1 + NumPairStops2 , list, obj);
                        frameLayout.setVisibility(View.VISIBLE);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.frameLayout, myf);
                        transaction.commit();

                    } else {
                        fare.setText("");
                        //fare.setText("FirstStop =" + String.valueOf(FirstStop)+"; count_stops ="+ String.valueOf(count_stops));
                        source_stop.setText("Source Stop is:" + Stops2[FirstStop]);
                        destination_stop.setText("Destination Stop is:" + Stops2[SecondStop + 1]);
                    }
                }
            }
        });

        More_Bus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*String res= "";
                for(int i = 0; i < NumPairStops; i++){
                    for(int j = 0; j < 3; j++){
                        res += ";";
                        res += fares[i][j];
                    }
                }

                result.setText(res);*/
            }
        });
    }
    class HTTPConnection1  extends AsyncTask<PostData, Void, String> {
        PostData data;
        String result;
        String url;
        String busNumber;
        int countStops1, countStops2;
        int countPairs;
        String [] Stops1, Stops2;
        List<StopFareData> list = new ArrayList<>();
        @Override
        protected String doInBackground(PostData... params) {
            String Pairs = "Pairs";
            String stops1 = "Stops1";
            String stops2 = "StopsReturn";
            data = params[0];
            url = data.url;
            busNumber = data.busNumber;
            countStops1 = data.countStops1;
            countStops2 = data.countStops2;
            countPairs = data.countPairs;
            Stops1 = data.Stops1;
            Stops2 = data.Stops2;
            int c=0;
            list = data.list;
            //url = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3 + countStops1+ count_stops2 + countPairs);
                nameValuePairs.add(new BasicNameValuePair("busNumber", busNumber));
                nameValuePairs.add(new BasicNameValuePair("countStops1", String.valueOf(countStops1)));
                nameValuePairs.add(new BasicNameValuePair("countStops2", String.valueOf(countStops2)));
                nameValuePairs.add(new BasicNameValuePair("countPairs", String.valueOf(countPairs)));
                for(int i = 0; i < countStops1; i++)
                {
                    nameValuePairs.add(new BasicNameValuePair(stops1 + String.valueOf(i), Stops1[i]));
                }
                for(int i = 0; i < countStops2; i++)
                {
                    nameValuePairs.add(new BasicNameValuePair(stops2 + String.valueOf(i), Stops1[i]));
                }

                for(int i = 0; i < list.size(); i++)
                {
                    nameValuePairs.add(new BasicNameValuePair(Pairs + String.valueOf(i) + "source", list.get(i).Source));
                    nameValuePairs.add(new BasicNameValuePair(Pairs + String.valueOf(i) + "destination", list.get(i).Destination));
                    nameValuePairs.add(new BasicNameValuePair(Pairs + String.valueOf(i) + "fare", list.get(i).fare));
                }

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
            return result;
        }
        @Override
        protected void onPostExecute(String bitmap) {
            super.onPostExecute(bitmap);
            HTTPResult.setText(result);
        }

    }
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                    Toast.makeText(mContext, "The app was  allowed to read your store.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    void changeIntent()
    {

         if (Build.VERSION.SDK_INT >= 23) {
             String[] PERMISSIONS = {android.Manifest.permission.INTERNET};
             if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
                HTTPConnection1 conn = new HTTPConnection1();
                PostData data = new PostData(stackServer, busNumber, count_stops1, count_stops2, Stops1, Stops2, NumPairStops1 + NumPairStops2, list);
                conn.execute(data);
             } else {
                 HTTPConnection1 conn = new HTTPConnection1();
                 PostData data = new PostData(stackServer, busNumber, count_stops1, count_stops2, Stops1, Stops2, NumPairStops1 + NumPairStops2, list);
                 conn.execute(data);
             }
         } else {
             HTTPConnection1 conn = new HTTPConnection1();
             PostData data = new PostData(stackServer, busNumber, count_stops1, count_stops2, Stops1, Stops2, NumPairStops1 + NumPairStops2, list);
             conn.execute(data);
         }
    }
}

