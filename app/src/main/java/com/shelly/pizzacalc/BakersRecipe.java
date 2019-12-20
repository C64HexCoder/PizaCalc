package com.shelly.pizzacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BakersRecipe extends AppCompatActivity {
    SeekBar watterSeekBar;
    EditText watterET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakers_recipe);

        final PizzaRecipe pizzaReciepe = PizzaRecipe.getInstance();

        // Initiolize the Views
        FloatingActionButton updateButton = findViewById(R.id.updateFloatingButton);
        final CheckBox sugarCB = findViewById(R.id.sugarCB), oliveOilCB = findViewById(R.id.oliveOilCB);
        final EditText sugarET = findViewById(R.id.sugarEd), olivOildET = findViewById(R.id.oliveOilEd), /*flourET = findViewById(R.id.flourEd),*/
                yeastET = findViewById(R.id.yeastEd), saltET = findViewById(R.id.saltEd);

        watterET = findViewById(R.id.watterEd);
        TextView yeastTV = findViewById(R.id.yestTV);
        watterSeekBar = findViewById(R.id.watterSeekBar);
        final SeekBar saltSeekBar = findViewById(R.id.saltSeekBar);
        final SeekBar yeastSeekBar = findViewById(R.id.yeastSeekBar), sugarSeekBar = findViewById(R.id.sugerSeekBar), oliveOilSeekBar = findViewById(R.id.oliveOildSeekBar);


        if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryInstant)
            yeastTV.setText(R.string.dryInstant);
        else if (pizzaReciepe.yeastType == PizzaRecipe.YeastType.DryActive)
            yeastTV.setText(R.string.dryActive);
        else
            yeastTV.setText(R.string.freshYeast);

        watterET.setText(String.valueOf(pizzaReciepe.watterInPercentage));
        watterSeekBar.setProgress((int) pizzaReciepe.watterInPercentage - 55);
        yeastET.setText(String.valueOf(pizzaReciepe.yeastInPercentage));
        yeastSeekBar.setProgress(yeastSeekBarPos(pizzaReciepe.yeastInPercentage));
        saltET.setText(String.valueOf(pizzaReciepe.saltInPercentage));
        saltSeekBar.setProgress(saltSeekBarPos(pizzaReciepe.saltInPercentage));
        sugarET.setText(String.valueOf(pizzaReciepe.sugarInPercentage));
        sugarSeekBar.setProgress(sugarSeekBarPos(pizzaReciepe.sugarInPercentage));
        olivOildET.setText(String.valueOf(pizzaReciepe.oliveOilInPercentage));
        oliveOilSeekBar.setProgress(oliveOildSeekBarPos(pizzaReciepe.oliveOilInPercentage));

        sugarCB.setChecked(pizzaReciepe.UseSuger());
        oliveOilCB.setChecked(pizzaReciepe.UseOliveOil());

        if (pizzaReciepe.UseOliveOil()) {
            oliveOilCB.setChecked(true);
            olivOildET.setEnabled(true);
            oliveOilSeekBar.setEnabled(true);
        }
        else {
            oliveOilCB.setChecked(false);
            olivOildET.setEnabled(false);
            oliveOilSeekBar.setEnabled(false);
        }

        if (pizzaReciepe.UseSuger()) {
            sugarCB.setChecked(true);
            sugarET.setEnabled(true);
            sugarSeekBar.setEnabled(true);
        }
        else {
            sugarCB.setChecked(false);
            sugarET.setEnabled(false);
            sugarSeekBar.setEnabled(false);
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
                        new AlertDialog.Builder(BakersRecipe.this).setMessage(R.string.water_range_error).create().show();
                    }
                    watterSeekBar.setProgress(Integer.valueOf(watterET.getText().toString()) - 55);
                }
            }
        });

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            int newPosition;
            @Override
            public void onFocusChange(View view, boolean b) {
                switch (view.getId()) {
                    case R.id.yeastEd:
                        if (b == false) {
                            newPosition = yeastSeekBarPos(Double.valueOf(yeastET.getText().toString()));
                            yeastSeekBar.setProgress(newPosition);
                        }
                        break;

                    case R.id.saltEd:
                        if (b == false) {
                            newPosition = saltSeekBarPos(Double.valueOf(saltET.getText().toString()));
                            saltSeekBar.setProgress(newPosition);
                        }
                        break;

                    case R.id.sugarEd:
                        if (b == false) {
                            newPosition = sugarSeekBarPos(Double.valueOf(sugarET.getText().toString()));
                            sugarSeekBar.setProgress(newPosition);
                        }
                        break;

                    case R.id.oliveOilEd:
                        if (b == false) {
                            newPosition = oliveOildSeekBarPos(Double.valueOf(olivOildET.getText().toString()));
                            oliveOilSeekBar.setProgress(newPosition);
                        }

                }
            }
        };

        yeastET.setOnFocusChangeListener(onFocusChangeListener);
        saltET.setOnFocusChangeListener(onFocusChangeListener);
        sugarET.setOnFocusChangeListener(onFocusChangeListener);
        olivOildET.setOnFocusChangeListener(onFocusChangeListener);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()) {
                    case R.id.watterSeekBar:
                        watterET.setText(String.valueOf(progress+55));
                        break;

                    case R.id.saltSeekBar:
                        saltET.setText(String.valueOf(new BigDecimal(progress*0.05+2).setScale(2,RoundingMode.HALF_UP)));
                        break;

                    case R.id.yeastSeekBar:
                        yeastET.setText(String.valueOf(new BigDecimal(progress*0.01+0.01).setScale(2, RoundingMode.HALF_UP)));
                        break;

                    case R.id.sugerSeekBar:
                        sugarET.setText(String.valueOf(new BigDecimal(progress*0.05+1).setScale(2,RoundingMode.HALF_UP)));
                        break;

                    case R.id.oliveOildSeekBar:
                        olivOildET.setText(String.valueOf(progress*0.5+1));
                        break;

                }

            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        watterSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        saltSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        yeastSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        sugarSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        oliveOilSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);



        updateButton.setOnClickListener(new View.OnClickListener() {
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

                new AlertDialog.Builder(BakersRecipe.this).setMessage(R.string.recipe_update_dialog_message).setTitle(R.string.recipe_update_dialog_title).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setIcon(R.mipmap.save).setCancelable(false).create().show();

            }
        });


        sugarCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    sugarET.setEnabled(true);
                    sugarSeekBar.setEnabled(true);
                }
                else {
                    sugarET.setEnabled(false);
                    sugarSeekBar.setEnabled(false);
                }
            }
        });

        oliveOilCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    olivOildET.setEnabled(true);
                    oliveOilSeekBar.setEnabled(true);
                }
                else {
                    olivOildET.setEnabled(false);
                    oliveOilSeekBar.setEnabled(false);
                }
            }
        });



    }

    private int yeastSeekBarPos (double yeastPercentage)
    {
        return new BigDecimal((yeastPercentage - 0.01) * 100).round(MathContext.DECIMAL64).intValue();
    }

    private int saltSeekBarPos (double saltPercentage)
    {
        return new BigDecimal((saltPercentage- 2)/0.05).round(MathContext.DECIMAL64).intValue();
    }

    private int sugarSeekBarPos (double sugarPercentage)
    {
        return new BigDecimal((sugarPercentage-1)/0.05).round(MathContext.DECIMAL64).intValue();
    }

    private int oliveOildSeekBarPos (double oliveOilPercentage)
    {
        return new BigDecimal((oliveOilPercentage-1)/0.5).round(MathContext.DECIMAL64).intValue();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
