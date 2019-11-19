package com.shelly.pizacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class Recepie extends AppCompatActivity {
    PizzaReciepe pizzaReciepe = PizzaReciepe.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie);

        EditText flour = findViewById(R.id.flourEd);
        EditText watter = findViewById(R.id.watterEd);
        EditText yeast = findViewById(R.id.yeastED);
        EditText salt = findViewById(R.id.saltED);
        EditText sugar = findViewById(R.id.sugarED);
        EditText oliveOil = findViewById(R.id.oliveOilED);
        TextView sugarTV = findViewById(R.id.sugarTV);
        TextView oliveOilTV = findViewById(R.id.oliveOilTV);

        double TotalWeight;
        TotalWeight = getIntent().getDoubleExtra("Total Weight",250);

        pizzaReciepe.Calculate(TotalWeight);


        flour.setText(pizzaReciepe.getFlour());
        watter.setText(pizzaReciepe.getWatter());
        yeast.setText(pizzaReciepe.getYeast());
        salt.setText(pizzaReciepe.getSalt());
        sugar.setText(pizzaReciepe.getSugar());
        oliveOil.setText(pizzaReciepe.getOliveOil());

        if (pizzaReciepe.UseSuger()) {
            sugar.setVisibility(View.VISIBLE);
            sugarTV.setVisibility(View.VISIBLE);
        }
        else {
            sugar.setVisibility(View.GONE);
            sugarTV.setVisibility(View.GONE);
        }

        if (pizzaReciepe.UseOliveOil()) {
            oliveOil.setVisibility(View.VISIBLE);
            oliveOilTV.setVisibility(View.VISIBLE);
        }
        else {
            oliveOil.setVisibility(View.INVISIBLE);
            oliveOilTV.setVisibility(View.INVISIBLE);
        }
    }
}
