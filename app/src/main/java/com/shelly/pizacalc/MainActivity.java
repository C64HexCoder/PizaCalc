package com.shelly.pizacalc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Message;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        final EditText numOfBalls = findViewById(R.id.numOfBalls);
        final EditText ballWeight = findViewById(R.id.ballWeight);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.Calculate);
        Button receiptBan = findViewById(R.id.ReceiptBtw);
        PizzaReciepe pizzaReciepe = null;

        try {
            FileInputStream FileStream = new FileInputStream(getApplicationInfo().dataDir + "/Receipt.txt");

            ObjectInputStream objectInputStream = new ObjectInputStream(FileStream);
            try {
                pizzaReciepe = PizzaReciepe.getInstance((PizzaReciepe) objectInputStream.readObject());

            }catch (ClassNotFoundException ext)
            {
                new AlertDialog.Builder(this).setMessage(ext.getMessage()).create().show();
            }

        } catch (FileNotFoundException ext) {
                pizzaReciepe = PizzaReciepe.getInstance();

        }catch (IOException ex) {
            new AlertDialog.Builder (this).setTitle("שגיאת קובץ").setMessage(ex.getMessage()).create().show();
        }

        numOfBalls.setText(String.valueOf(pizzaReciepe.NumOfBalls));
        ballWeight.setText(String.valueOf(pizzaReciepe.BallWeight));



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                double result = Double.valueOf (numOfBalls.getText().toString()) * Double.valueOf(ballWeight.getText().toString());
                Intent intent = new Intent(getBaseContext(),Recepie.class).putExtra("Total Weight",result);
                startActivity(intent);
                //finish();
            }
        });

        receiptBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),BakersRecipe.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_about:
                Intent intent1 = new Intent(this,AboutActivity.class);
                startActivity(intent1);
                break;

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
