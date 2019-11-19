package com.shelly.pizacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final RadioButton dryInstantYeast = findViewById(R.id.dryInstenYeasttRB);
        RadioButton dryActiveYeast = findViewById(R.id.dryActiveYeastRB);
        RadioButton freshYeast = findViewById(R.id.freshYeastRB);
        RadioButton milRB = findViewById(R.id.milRB);
        RadioButton gramRB = findViewById(R.id.gTB);
        RadioButton UnitgramRB = findViewById(R.id.gramRB);
        RadioButton UnitOunceRB = findViewById(R.id.ounceRB);

        final PizzaReciepe pizzaReciepe = PizzaReciepe.getInstance();


        if (pizzaReciepe.unitOfMesure == PizzaReciepe.UnitOfMeasure.Grams)
            UnitgramRB.setChecked(true);
        else
            UnitOunceRB.setChecked(true);


        // Checks the appropiate RadioBOxes according to PizzaRecipe
        if (pizzaReciepe.yeastType == PizzaReciepe.YeastType.DryInstent)
            dryInstantYeast.setChecked(true);
        else if (pizzaReciepe.yeastType == PizzaReciepe.YeastType.DryActive)
            dryActiveYeast.setChecked(true);
        else
            freshYeast.setChecked(true);

        if (pizzaReciepe.liquidMeasureUnit == PizzaReciepe.LiquidMeasureUnit.Milliliter)
            milRB.setChecked(true);
        else
            gramRB.setChecked(true);

        milRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeLiquidMeasureUnit(PizzaReciepe.LiquidMeasureUnit.Milliliter);
            }
        });

        gramRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeLiquidMeasureUnit(PizzaReciepe.LiquidMeasureUnit.Grams);
            }
        });

        UnitgramRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeWeightMeasureUnit(PizzaReciepe.UnitOfMeasure.Grams);
            }
        });

        UnitOunceRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeWeightMeasureUnit(PizzaReciepe.UnitOfMeasure.Ounce);
            }
        });

       // the user clicked the dry instant yeast
        dryInstantYeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // and the selected yeastYype is not dry Instant yeast
               if (pizzaReciepe.yeastType != PizzaReciepe.YeastType.DryInstent) {
                   // then if the yeastType is dry active yeast
                   if (pizzaReciepe.yeastType == PizzaReciepe.YeastType.DryActive)
                       // converty from dry active yeast to dry instant yeast
                       pizzaReciepe.yeastInPercentage = (pizzaReciepe.yeastInPercentage *2)/3;
                   else
                       pizzaReciepe.yeastInPercentage /= 3;

                   pizzaReciepe.yeastType = PizzaReciepe.YeastType.DryInstent;
               }
           }
       });

       dryActiveYeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (pizzaReciepe.yeastType != PizzaReciepe.YeastType.DryActive) {
                   if (pizzaReciepe.yeastType == PizzaReciepe.YeastType.DryInstent) {
                       pizzaReciepe.yeastInPercentage = (pizzaReciepe.yeastInPercentage * 3) / 2;
                   }
                   else
                       pizzaReciepe.yeastInPercentage /=2;

                   pizzaReciepe.yeastType = PizzaReciepe.YeastType.DryActive;
               }
           }
       });

       freshYeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (pizzaReciepe.yeastType != PizzaReciepe.YeastType.Fresh) {
                   if (pizzaReciepe.yeastType == PizzaReciepe.YeastType.DryInstent)
                        pizzaReciepe.yeastInPercentage *= 3;
                   else
                        pizzaReciepe.yeastInPercentage *=2;

                   pizzaReciepe.yeastType = PizzaReciepe.YeastType.Fresh;
               }
           }
       });
    }
}
