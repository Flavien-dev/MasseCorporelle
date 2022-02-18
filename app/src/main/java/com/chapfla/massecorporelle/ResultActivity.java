package com.chapfla.massecorporelle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    // initialiser les objets
    private TextView TV_alpha_value;
    private TextView TV_num_value;
    private Button BT_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialiser l'intent
        Intent MainActivity = getIntent();

        // modifier la valeur des objets
        TV_alpha_value = findViewById(R.id.result_alpha_value);
        TV_num_value = findViewById(R.id.result_num_value);

        // changer la valeur des textview
        TV_alpha_value.setText(MainActivity.getStringExtra("texte"));
        TV_num_value.setText(MainActivity.getStringExtra("imc"));
    }
}