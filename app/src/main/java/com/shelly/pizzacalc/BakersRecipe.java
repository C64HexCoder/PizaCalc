package com.shelly.pizzacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BakersRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakers_recipe);

        final PizzaRecipe pizzaReciepe = PizzaRecipe.getInstance();

        FloatingActionButton button = findViewById(R.id.updateFloatingButton);
        final CheckBox sugarCB = findViewById(R.id.sugarCB), oliveOilCB = findViewById(R.id.oliveOilCB);
        final EditText sugarET = findViewById(R.id.sugarEd), olivOildET = findViewById(R.id.oliveOilEd), flourET = findViewById(R.id.flourEd),
        watterET = findViewById(R.id.watterEd), yeastET = findViewById(R.id.yeastEd), saltET = findViewById(R.id.saltEd);
        TextView yeastTV = findViewById(R.id.yestTV);

        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstent)
            yeastTV.setText(R.string.dryInstant);
        else if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
            yeastTV.setText(R.string.dryActive);
        else
            yeastTV.setText(R.string.freshYeast);

        watterET.setText(String.valueOf(pizzaReciepe.watterInPercentage));
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
}
