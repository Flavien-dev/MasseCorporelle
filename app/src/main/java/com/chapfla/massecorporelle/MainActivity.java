package com.chapfla.massecorporelle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    // initialiser les différents éléments de la page
    private EditText ET_saisiePoids;
    private EditText ET_saisie_Taille;
    private Button BT_cancel;
    private Button BT_valider;
    private LinearLayout LL_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        // cherche les éléments par leur ID
        ET_saisiePoids = findViewById(R.id.main_poids_editText);
        ET_saisie_Taille = findViewById(R.id.main_taille_editText);
        BT_cancel = findViewById(R.id.main_cancel_button);
        BT_valider = findViewById(R.id.main_validate_button);
        LL_info = findViewById(R.id.linearlayout);

        // bouton désactivé
        BT_valider.setEnabled(false);

        // linearlayout invisible
        LL_info.setVisibility(LinearLayout.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /**
         * réinitilise la page en entier
         */
        BT_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vide tous les champs
                resetFields();
            }
        });

        /**
         * saisie la masse de l'utilisateur
         */
        ET_saisiePoids.addTextChangedListener(new TextWatcher() {

            /**
             * rend les infos sur l'application invisible
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // linearlayout invisible
                LL_info.setVisibility(LinearLayout.GONE);
            }

            /**
             * vérifie la saisie de l'utilisateur
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!ET_saisiePoids.getText().toString().equals("") && !ET_saisie_Taille.getText().toString().equals("")) {
                    if (Integer.parseInt(ET_saisiePoids.getText().toString()) >= 0) {
                        BT_valider.setEnabled(true);
                    }
                } else {
                    BT_valider.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * saisie la taille de l'utilisateur
         */
        ET_saisie_Taille.addTextChangedListener(new TextWatcher() {

            /**
             * rend les infos sur l'application invisible
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // linearlayout invisible
                LL_info.setVisibility(LinearLayout.GONE);
            }

            /**
             * vérifie la saisie de l'utilisateur
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!ET_saisiePoids.getText().toString().equals("") && !ET_saisie_Taille.getText().toString().equals("")) {
                    if (Integer.parseInt(ET_saisiePoids.getText().toString()) >= 0) {
                        BT_valider.setEnabled(true);
                    }
                } else {
                    BT_valider.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        BT_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // initialise la valeur de chaque champs
                String masse = ET_saisiePoids.getText().toString();
                String taille = ET_saisie_Taille.getText().toString();

                // initialise l'Intent
                Intent resultActivity = new Intent(MainActivity.this,ResultActivity.class);

                if (resultActivity != null) {

                    // calculer l'IMC
                    float result = Float.parseFloat(masse) / ((Float.parseFloat(taille) / 100) * (Float.parseFloat(taille) / 100)) / 10;

                    String result_alpha_num = "";

                    // modifier la valeur alpha numérique
                    if (result <= 18.5) {
                        result_alpha_num = "Maigreur";
                    } else if (result > 18.5 && result <= 25) {
                        result_alpha_num = "Poids normal";
                    } else if (result > 25 && result <= 30) {
                        result_alpha_num = "Poids pondéral";
                    } else if (result > 30 && result <= 35){
                        result_alpha_num = "Obésité";
                    } else {
                        result_alpha_num = "Obésité sévère";
                    }

                    // initialiser les paramêtres
                    resultActivity.putExtra("texte",result_alpha_num);
                    resultActivity.putExtra("imc",Float.toString(result));
                }

                // changer de page
                startActivity(resultActivity);
            }
        });
    }

    /**
     * crée un popup menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_favorite:
                //Do action
                break;
            case R.id.action_delete:
                // vide les champs
                resetFields();
                break;
            case R.id.action_about:
                Log.wtf("about", "ça fonctionne");

                // linearlayout visible
                LL_info.setVisibility(LinearLayout.VISIBLE);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * vide tous les champs de la page
     */
    private void resetFields(){
        ET_saisiePoids.setText("");
        ET_saisie_Taille.setText("");

        // remet le focus sur le champ poids
        ET_saisiePoids.requestFocus();
    }
}