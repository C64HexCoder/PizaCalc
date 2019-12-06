package com.shelly.pizzacalc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        TextView aboutTxt = (TextView) findViewById(R.id.about_aboutTxt);

        try {
            byte[] b = new byte[1024];
            //FileInputStream fileInputStream = new FileInputStream("assets\takanon.txt");
            //fileInputStream.
            InputStream fileInputStream = getAssets().open("about_heb.txt");
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            String string = new String(buffer);
            aboutTxt.setText(string);
        } catch (FileNotFoundException exc) {
            Toast.makeText(getApplicationContext(),"הקובץ לא נמצא",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
