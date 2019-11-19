package com.shelly.pizacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SettingsActivity extends AppCompatActivity {
    final PizzaRecipe pizzaReciepe = PizzaRecipe.getInstance();

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




        if (pizzaReciepe.unitOfMesure == PizzaRecipe.UnitOfMeasure.Grams)
            UnitgramRB.setChecked(true);
        else
            UnitOunceRB.setChecked(true);


        // Checks the appropiate RadioBOxes according to PizzaRecipe
        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstent)
            dryInstantYeast.setChecked(true);
        else if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
            dryActiveYeast.setChecked(true);
        else
            freshYeast.setChecked(true);

        if (pizzaReciepe.liquidMeasureUnit == PizzaRecipe.LiquidMeasureUnit.Milliliter)
            milRB.setChecked(true);
        else
            gramRB.setChecked(true);

        milRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeLiquidMeasureUnit(PizzaRecipe.LiquidMeasureUnit.Milliliter);
            }
        });

        gramRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeLiquidMeasureUnit(PizzaRecipe.LiquidMeasureUnit.Grams);
            }
        });

        UnitgramRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeWeightMeasureUnit(PizzaRecipe.UnitOfMeasure.Grams);
                SavePizzaRecipeToFile();
            }
        });

        UnitOunceRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeWeightMeasureUnit(PizzaRecipe.UnitOfMeasure.Ounce);
                SavePizzaRecipeToFile();
            }
        });




       // the user clicked the dry instant yeast
        dryInstantYeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // and the selected yeastYype is not dry Instant yeast
               if (pizzaReciepe.yeastType != PizzaRecipe.YeastType.DryInstent) {
                   // then if the yeastType is dry active yeast
                   if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
                       // converty from dry active yeast to dry instant yeast
                       pizzaReciepe.yeastInPercentage = (pizzaReciepe.yeastInPercentage *2)/3;
                   else
                       pizzaReciepe.yeastInPercentage /= 3;

                   pizzaReciepe.yeastType = PizzaRecipe.YeastType.DryInstent;
               }
           }
       });

       dryActiveYeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (pizzaReciepe.yeastType != PizzaRecipe.YeastType.DryActive) {
                   if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstent) {
                       pizzaReciepe.yeastInPercentage = (pizzaReciepe.yeastInPercentage * 3) / 2;
                   }
                   else
                       pizzaReciepe.yeastInPercentage /=2;

                   pizzaReciepe.yeastType = PizzaRecipe.YeastType.DryActive;
               }
           }
       });

       freshYeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (pizzaReciepe.yeastType != PizzaRecipe.YeastType.Fresh) {
                   if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstent)
                        pizzaReciepe.yeastInPercentage *= 3;
                   else
                        pizzaReciepe.yeastInPercentage *=2;

                   pizzaReciepe.yeastType = PizzaRecipe.YeastType.Fresh;
               }
           }
       });
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
            new AlertDialog.Builder (SettingsActivity.this).setTitle("File Error").setMessage(ext.getMessage()).create().show();
        }
    }
}
