package com.shelly.pizzacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SettingsActivity extends AppCompatActivity {
    final PizzaRecipe pizzaReciepe = PizzaRecipe.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //final RadioButton dryInstantYeast = findViewById(R.id.dryInstenYeasttRB);
        //RadioButton dryActiveYeast = findViewById(R.id.dryActiveYeastRB);
        //RadioButton freshYeast = findViewById(R.id.freshYeastRB);
        RadioButton milRB = findViewById(R.id.milRB);
        final RadioButton gramRB = findViewById(R.id.gTB);
        RadioButton UnitgramRB = findViewById(R.id.gramRB);
        RadioButton UnitOunceRB = findViewById(R.id.ounceRB);
        RadioGroup roundingRGroup = findViewById(R.id.roundingGRBtn);
        RadioGroup yeastRGroup = findViewById(R.id.yeastRG);
        FloatingActionButton backFloatingButton = findViewById(R.id.settingsBackFloatingButton);


        if (pizzaReciepe.weightRounding == PizzaRecipe.WeightRounding.Round)
            roundingRGroup.check(R.id.roundRBtn);
        else
            roundingRGroup.check(R.id.notRoundRBtn);

        if (pizzaReciepe.unitOfMesure == PizzaRecipe.UnitOfMeasure.Grams)
            UnitgramRB.setChecked(true);
        else
            UnitOunceRB.setChecked(true);


        // Checks the appropiate RadioBOxes according to PizzaRecipe
        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstant)
            yeastRGroup.check(R.id.dryInstenYeasttRB);
        else if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
            yeastRGroup.check(R.id.dryActiveYeastRB);
        else
            yeastRGroup.check(R.id.freshYeastRB);

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
                pizzaReciepe.ex = 0;
                SavePizzaRecipeToFile();
                //gramRB.setText(R.string.gram);
                //recreate();
            }
        });

        UnitOunceRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaReciepe.changeWeightMeasureUnit(PizzaRecipe.UnitOfMeasure.Ounce);
                pizzaReciepe.ex = 2;
                SavePizzaRecipeToFile();
                //gramRB.setText(R.string.ounce);
                //recreate();
            }
        });


        yeastRGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.dryInstenYeasttRB:
                        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
                            // converty from dry active yeast to dry instant yeast
                            pizzaReciepe.yeastInPercentage = (pizzaReciepe.yeastInPercentage *2)/3;
                        else
                            pizzaReciepe.yeastInPercentage /= 3;

                        pizzaReciepe.yeastType = PizzaRecipe.YeastType.DryInstant;
                        break;

                    case R.id.dryActiveYeastRB:
                        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstant) {
                            pizzaReciepe.yeastInPercentage = (pizzaReciepe.yeastInPercentage * 3) / 2;
                        }
                        else
                            pizzaReciepe.yeastInPercentage /=2;

                        pizzaReciepe.yeastType = PizzaRecipe.YeastType.DryActive;
                        break;

                    case R.id.freshYeastRB:
                        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstant)
                            pizzaReciepe.yeastInPercentage *= 3;
                        else
                            pizzaReciepe.yeastInPercentage *=2;

                        pizzaReciepe.yeastType = PizzaRecipe.YeastType.Fresh;
                        break;
                }
            }
        });




       roundingRGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
               if (checkedId == R.id.roundRBtn) {
                   pizzaReciepe.weightRounding = PizzaRecipe.WeightRounding.Round;
               }
               else
                   pizzaReciepe.weightRounding = PizzaRecipe.WeightRounding.NotRound;
           }
       });

       backFloatingButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
               startActivity(mainIntent);
               finish();
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
