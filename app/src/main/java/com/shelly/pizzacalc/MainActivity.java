package com.shelly.pizzacalc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    PizzaRecipe pizzaReciepe = null;

    EditText numOfBalls;
    EditText ballWeight;
    TextView weightTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        numOfBalls = findViewById(R.id.numOfBalls);
        ballWeight = findViewById(R.id.ballWeight);

        setSupportActionBar(toolbar);

        FloatingActionButton recipe = findViewById(R.id.calculateRecipe);
        ImageButton receiptBan = findViewById(R.id.ReceiptBtw);


        try {
            FileInputStream FileStream = new FileInputStream(getApplicationInfo().dataDir + "/Receipt.txt");

            ObjectInputStream objectInputStream = new ObjectInputStream(FileStream);
            try {
                String Test = getApplicationInfo().dataDir;

                pizzaReciepe = PizzaRecipe.getInstance((PizzaRecipe) objectInputStream.readObject());

            }catch (ClassNotFoundException ext)
            {
                //new AlertDialog.Builder(this).setMessage(ext.getMessage()).create().show();
                //Toast.makeText(getApplicationContext(),"pizzaRecipe not found",Toast.LENGTH_LONG).show();
            }

        } catch (FileNotFoundException ext) {
            pizzaReciepe = PizzaRecipe.getInstance();
            SavePizzaRecipeToFile();

        }catch (IOException ex) {
            pizzaReciepe = PizzaRecipe.getInstance();
            SavePizzaRecipeToFile();
            //new AlertDialog.Builder (this).setTitle("שגיאת קובץ").setMessage(ex.getMessage()).create().show();
        }

        pizzaReciepe.setApplicationContext(getApplicationContext());
        weightTV = findViewById(R.id.main_weightTV);

        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                double result = Double.valueOf (numOfBalls.getText().toString()) * Double.valueOf(ballWeight.getText().toString());
                Intent intent = new Intent(getBaseContext(), CalculateRecepie.class).putExtra("Total Weight",result);
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
                //finish();
                break;

            case R.id.action_about:
                Intent intent1 = new Intent(this,About.class);
                startActivity(intent1);
                break;

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

 //       numOfBalls.setText(String.valueOf(pizzaReciepe.NumOfBalls));
 //       ballWeight.setText(String.valueOf(pizzaReciepe.BallWeight));
    }

    @Override
    protected void onResume() {
        super.onResume();
        numOfBalls.setText(String.valueOf(pizzaReciepe.NumOfBalls));
        ballWeight.setText(String.valueOf(pizzaReciepe.BallWeight));

        if (pizzaReciepe.unitOfMesure == PizzaRecipe.UnitOfMeasure.Grams)
            weightTV.setText(R.string.gram);
        else
            weightTV.setText(R.string.ounce);

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        SavePizzaRecipeToFile();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        pizzaReciepe.NumOfBalls = Double.valueOf(numOfBalls.getText().toString());
        pizzaReciepe.BallWeight = Double.valueOf(ballWeight.getText().toString());
        SavePizzaRecipeToFile();
    }

    @Override
    protected void onStop() {
        super.onStop();
    //    pizzaReciepe.NumOfBalls = Double.valueOf(numOfBalls.getText().toString());
     //   pizzaReciepe.BallWeight = Double.valueOf(ballWeight.getText().toString());
    //    SavePizzaRecipeToFile();
    }


    private void SavePizzaRecipeToFile()
    {

        try
        {
            FileOutputStream fileOutput = new FileOutputStream(getApplicationInfo().dataDir + "/Receipt.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutput);

            objectOutputStream.writeObject(pizzaReciepe);


        } catch (IOException ext)
        {
            new AlertDialog.Builder (MainActivity.this).setTitle("File Error").setMessage(ext.getMessage()).create().show();
        }
    }
}
