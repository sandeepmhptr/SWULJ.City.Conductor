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
    String Param = "PARAM";
    String Stop = "STOP";
    int count_stops = 0;
    int NumPairStops = 0;
    int StopPair =0;
    String busNumber;
    String temp3 = null;
    String [] Stops;
    String[][] fares;
    int FirstStop =0, SecondStop=1;
    TextView txt, source_stop, destination_stop;
    EditText fare, result, HTTPResult;
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
        result = (EditText) findViewById(R.id.result);
        HTTPResult = (EditText) findViewById(R.id.HTTP_response);
        Skip_To_Main.setVisibility(View.GONE);
        More_Bus.setVisibility(View.GONE);


        Intent i = getIntent();

        busNumber = i.getStringExtra(BUS_NUMBER);
        String temp = i.getStringExtra(Param);
        count_stops = Integer.valueOf(temp);

        Stops = new String[count_stops];

        for(int x = 1; x <= count_stops; x++){
            String temp2 = Stop + String.valueOf(x);
            Stops[x-1] = i.getStringExtra(temp2);
            temp3 += ";";
            temp3 += Stops[x-1];
        }
        result.setText(temp3);
        source_stop.setText("Source Stop is:" + Stops[FirstStop]);
        destination_stop.setText("Destination Stop is:" + Stops[SecondStop]);

        for(int x = count_stops - 1; x > 0; x--){
            NumPairStops += x;
        }
        NumPairStops *= 2;
        fares = new String[NumPairStops][3];


        next_fare.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //if(flag){
                    //flag = false;
                    datum = new StopFareData(Stops[FirstStop], Stops[SecondStop], (String)fare.getText().toString());
                    list.add(datum);
                    /*fares[StopPair] = new String[3];
                    fares[StopPair][0] = Stops[FirstStop];
                    fares[StopPair][1] = Stops[SecondStop];
                    fares[StopPair][2] = (String)fare.getText().toString();
                    StopPair++;*/
                    source_stop.setText("Source Stop is:" + Stops[SecondStop]);
                    destination_stop.setText("Destination Stop is:" + Stops[FirstStop]);
                    datum = new StopFareData(Stops[SecondStop], Stops[FirstStop], (String)fare.getText().toString());
                    list.add(datum);
                    /*fares[StopPair] = new String[3];
                    fares[StopPair][0] = Stops[SecondStop];
                    fares[StopPair][1] = Stops[FirstStop];
                    fares[StopPair][2] = (String)fare.getText().toString();
                    StopPair++;*/
                    SecondStop++;
                    if((FirstStop != count_stops - 1 ) && (SecondStop == count_stops )){
                        FirstStop++;
                        SecondStop = FirstStop + 1;
                    }
                    if(FirstStop == count_stops - 1 ){
                        //hide button
                        next_fare.setVisibility(View.GONE);
                        More_Bus.setVisibility(View.VISIBLE);
                        Skip_To_Main.setVisibility(View.VISIBLE);

                        myf = new FaresFragment(cntxt, NumPairStops, list, obj);
                        frameLayout.setVisibility(View.VISIBLE);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.frameLayout, myf);
                        transaction.commit();



                    }else{
                        fare.setText("");
                        //fare.setText("FirstStop =" + String.valueOf(FirstStop)+"; count_stops ="+ String.valueOf(count_stops));
                        source_stop.setText("Source Stop is:" + Stops[FirstStop]);
                        destination_stop.setText("Destination Stop is:" + Stops[SecondStop]);
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
        int countStops;
        int countPairs;
        String [] Stops;
        List<StopFareData> list = new ArrayList<>();
        @Override
        protected String doInBackground(PostData... params) {
            String Pairs = "Pairs";
            String stops = "Stops";
            data = params[0];
            url = data.url;
            busNumber = data.busNumber;
            countStops = data.countStops;
            countPairs = data.countPairs;
            Stops = data.Stops;
            int c=0;
            list = data.list;
            //url = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3 + countStops + countPairs);
                nameValuePairs.add(new BasicNameValuePair("busNumber", busNumber));
                nameValuePairs.add(new BasicNameValuePair("countStops", String.valueOf(countStops)));
                nameValuePairs.add(new BasicNameValuePair("countPairs", String.valueOf(countPairs)));
                for(int i = 0; i < countStops; i++)
                {
                    nameValuePairs.add(new BasicNameValuePair(stops + String.valueOf(i), Stops[i]));
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
                                PostData data = new PostData(stackServer, busNumber, count_stops, NumPairStops, Stops, list);
                                conn.execute(data);
                            } else {
                                HTTPConnection1 conn = new HTTPConnection1();
                                PostData data = new PostData(stackServer, busNumber, count_stops, NumPairStops, Stops, list);
                                conn.execute(data);
                            }
                        } else {
                            HTTPConnection1 conn = new HTTPConnection1();
                            PostData data = new PostData(stackServer, busNumber, count_stops, NumPairStops, Stops, list);
                            conn.execute(data);
                        }
    }
}

