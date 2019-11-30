package com.shelly.pizzacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BakersRecipe extends AppCompatActivity {
    SeekBar watterSeekBar;
    EditText watterET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakers_recipe);

        final PizzaRecipe pizzaReciepe = PizzaRecipe.getInstance();

        // Initiolize the Views
        FloatingActionButton button = findViewById(R.id.updateFloatingButton);
        final CheckBox sugarCB = findViewById(R.id.sugarCB), oliveOilCB = findViewById(R.id.oliveOilCB);
        final EditText sugarET = findViewById(R.id.sugarEd), olivOildET = findViewById(R.id.oliveOilEd), flourET = findViewById(R.id.flourEd),
                yeastET = findViewById(R.id.yeastEd), saltET = findViewById(R.id.saltEd);

        watterET = findViewById(R.id.watterEd);
        TextView yeastTV = findViewById(R.id.yestTV);
        watterSeekBar = findViewById(R.id.watterSeekBar);

        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstant)
            yeastTV.setText(R.string.dryInstant);
        else if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
            yeastTV.setText(R.string.dryActive);
        else
            yeastTV.setText(R.string.freshYeast);

        watterET.setText(String.valueOf(pizzaReciepe.watterInPercentage));
        watterSeekBar.setProgress((int) pizzaReciepe.watterInPercentage - 55);
        yeastET.setText(String.valueOf(pizzaReciepe.yeastInPercentage));
        saltET.setText(String.valueOf(pizzaReciepe.saltInPercentage));
        sugarET.setText(String.valueOf(pizzaReciepe.sugarInPercentage));
        olivOildET.setText(String.valueOf(pizzaReciepe.oliveOilInPercentage));

        sugarCB.setChecked(pizzaReciepe.UseSuger());
        oliveOilCB.setChecked(pizzaReciepe.UseOliveOil());

        if (pizzaReciepe.UseOliveOil()) {
            oliveOilCB.setChecked(true);
            olivOildET.setEnabled(true);
        }
        else {
            oliveOilCB.setChecked(false);
            olivOildET.setEnabled(false);
        }

        if (pizzaReciepe.UseSuger()) {
            sugarCB.setChecked(true);
            sugarET.setEnabled(true);
        }
        else {
            sugarCB.setChecked(false);
            sugarET.setEnabled(false);
        }

        

        watterET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    //Toast.makeText(getApplicationContext(),"Out of focus "+watterET.getText().toString(),Toast.LENGTH_LONG).show();
                    if (Integer.valueOf(watterET.getText().toString()) > 70 || Integer.valueOf(watterET.getText().toString()) < 55) {
                        new AlertDialog.Builder(BakersRecipe.this).setMessage("Watter percentage must be between 55% to 70%").create().show();
                    }
                    watterSeekBar.setProgress(Integer.valueOf(watterET.getText().toString()) - 55);
                }
            }
        });

        watterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                watterET.setText(String.valueOf(progress+55));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.watterInPercentage = Double.valueOf(watterET.getText().toString());
                pizzaReciepe.yeastInPercentage = Double.valueOf(yeastET.getText().toString());
                pizzaReciepe.saltInPercentage = Double.valueOf(saltET.getText().toString());
                pizzaReciepe.sugarInPercentage = Double.valueOf(sugarET.getText().toString());
                pizzaReciepe.oliveOilInPercentage = Double.valueOf(olivOildET.getText().toString());

                pizzaReciepe.UseOliveOil(oliveOilCB.isChecked());
                pizzaReciepe.UseSuger(sugarCB.isChecked());

                try
                {
                    FileOutputStream fileOutput = new FileOutputStream(getApplicationInfo().dataDir + "/Receipt.txt");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutput);

                    objectOutputStream.writeObject(pizzaReciepe);


                } catch (IOException ext)
                {
                    new AlertDialog.Builder (BakersRecipe.this).setTitle("File Error").setMessage(ext.getMessage()).create().show();
                }

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        sugarCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    sugarET.setEnabled(true);
                }
                else
                    sugarET.setEnabled(false);
            }
        });

        oliveOilCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    olivOildET.setEnabled(true);
                else
                    olivOildET.setEnabled(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
