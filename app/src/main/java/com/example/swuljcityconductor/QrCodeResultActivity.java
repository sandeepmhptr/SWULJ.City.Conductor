package com.example.swuljcityconductor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QrCodeResultActivity extends AppCompatActivity {
    String busNumber;
    String str_seat_prefix = null;
    String str_seat_serial_start_number = null;
    String str_seat_serial_end_number = null;
    String str_seat_serial_number = null;
    int parameterNumber;
    String BUS_NUMBER = "EXTRA_BUS_NUMBER";
    String PARAMETER_NUMBER = "EXTRA_PARAMETER_NUMBER";
    String SEAT_PREFIX = "EXTRA_SEAT_PREFIX";
    String SEAT_SERIAL_START_NUMBER = "EXTRA_SEAT_SERIAL_START_NUMBER";
    String SEAT_SERIAL_END_NUMBER = "EXTRA_SEAT_SERIAL_END_NUMBER";
    EditText edit_Total_text;
    EditText imagePath;
    int start,end;
    String total_text;
    static final int REQUEST = 112;
    Context mContext=QrCodeResultActivity.this;
    String imageName;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_result);
        edit_Total_text = (EditText) findViewById(R.id.total_text);
        imagePath = (EditText) findViewById(R.id.image_path);
        Intent intent = getIntent();
        busNumber = intent.getStringExtra(BUS_NUMBER);
        parameterNumber = Integer.valueOf(intent.getStringExtra(PARAMETER_NUMBER));
        switch (parameterNumber) {
            case 1:
                str_seat_serial_number = intent.getStringExtra(SEAT_PREFIX);
                total_text = busNumber + ";" ;

                {
                    imageName= str_seat_serial_number;
                    total_text = total_text + imageName;
                    Bitmap myBitmap = TextToImageEncode(total_text, imageName);
                    saveImage(myBitmap,busNumber, imageName, imagePath);
                }
                break;
            case 3:
                str_seat_prefix = intent.getStringExtra(SEAT_PREFIX);
                str_seat_serial_start_number = intent.getStringExtra(SEAT_SERIAL_START_NUMBER);
                str_seat_serial_end_number = intent.getStringExtra(SEAT_SERIAL_END_NUMBER);
                start = Integer.valueOf(str_seat_serial_start_number);
                end = Integer.valueOf(str_seat_serial_end_number);
                total_text = busNumber + ";" ;
                for(int i =start; i<=end; i++)
                {
                    imageName= str_seat_prefix + String.valueOf(i);
                    total_text = total_text + imageName;
                    Bitmap myBitmap = TextToImageEncode(total_text, imageName);
                    saveImage(myBitmap,busNumber, imageName, imagePath);
                }

                break;
        }
        edit_Total_text.setText(total_text);

        // String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";


    }

    Bitmap TextToImageEncode(String QRText, String imageName) {
        MultiFormatWriter multiFormatWriter;
        BitMatrix bitMatrix;
        Bitmap bitmap = null;
        int textWidth = 10;

        multiFormatWriter = new MultiFormatWriter();

        try {
            bitMatrix = multiFormatWriter.encode(QRText, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);

            Canvas canvas = new Canvas(bitmap);
            // new antialiased Paint
            TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(0, 0, 0));
            // text size in pixels
            paint.setTextSize(textWidth);
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            // get position of text's top left corner
            float x = 3 * textWidth;
            float y = (bitmap.getHeight() - textWidth);
            // draw text to the Canvas center
            canvas.drawText(imageName,x,y,paint);
            canvas.save();
            canvas.restore();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveImage(Bitmap myBitmap, String busNumber, String imageName, EditText imagePath) {
        String IMAGE_DIRECTORY = "QRCode";

        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {

            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY+"/"+busNumber );
            // have the object build the directory structure, if needed.

            if (!wallpaperDirectory.exists()) {
                Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
                wallpaperDirectory.mkdirs();
            }

            try {
                imagePath.setText("Chintu");
                File f = new File(wallpaperDirectory,imageName + ".jpeg");
                imagePath.setText("Sandeep");





                if (Build.VERSION.SDK_INT >= 23) {
                    String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (!hasPermissions(mContext, PERMISSIONS)) {
                        ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
                        imagePath.setText("SDK>23,has no permission");
                        f.createNewFile();

                    } else {
                        //do here
                        imagePath.setText("SDK>23,has no permission");
                        f.createNewFile();

                    }
                } else {
                    //do here
                    imagePath.setText("SDK<23");
                    f.createNewFile();

                }
                   //give read write permission
                imagePath.setText("Chintu1");
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                imagePath.setText("f.getAbsolutePath()");
                fo.close();
                Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

                Toast.makeText(getBaseContext(), f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                //return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
                //imagePath.setText("Pintu");
            }
        } catch (IOException e) {
            e.printStackTrace();
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

}
