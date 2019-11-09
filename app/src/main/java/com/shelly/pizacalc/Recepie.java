package com.shelly.pizacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Recepie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie);

        EditText flour = findViewById(R.id.flourEdText);

        Integer TotalWeight = null;
        TotalWeight = getIntent().getIntExtra("Total Weight",0);

        flour.setText(TotalWeight.toString());
    }
}
