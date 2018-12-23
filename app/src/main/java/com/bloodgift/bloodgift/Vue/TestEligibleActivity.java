package com.bloodgift.bloodgift.Vue;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.ControleProfil;
import com.bloodgift.bloodgift.R;

public class TestEligibleActivity extends ActivityWithDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnal_information);
        initializeView();
        init();

    }

    protected void initializeView(){
        super.initializeToolBar();
    }

    //propriétés
    private ControleProfil controle;
    private EditText txtAge;
    private EditText txtPoids;
    private RadioButton rdHomme;
    private RadioButton rdFemme;

    /**
     * Initialisation des liens avec les objets graphiques
     */
    private void init(){
        txtAge = findViewById(R.id.txtAge);
        txtPoids = findViewById(R.id.txtPoids);
        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdFemme);
        this.controle = ControleProfil.getInstance(this);
        ecouteBouton();
        recupProfil();
    }

    /**
     * Ecoute evenment sur le bouton "suis-je éligible ?"
     */
    private void ecouteBouton(){
        ((Button) findViewById(R.id.btnEligible)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Toast.makeText(TestEligibleActivity.this, "test",Toast.LENGTH_SHORT).show();

                Integer age = 0;
                Integer poids = 0;
                Integer sexe = 0;

                //récupération des données saisies
                try {
                    age = Integer.parseInt(txtAge.getText().toString());
                    poids = Integer.parseInt(txtPoids.getText().toString());
                } catch (Exception e) {}
                if (rdHomme.isChecked()){
                    sexe = 1;
                }

                //controle des données saisies
                if (age ==0 || poids ==0){
                    Toast.makeText(TestEligibleActivity.this, "Saisie incorrecte",Toast.LENGTH_SHORT).show();
                } else {
                    afficheResult(age,poids,sexe);
                }
            }
        });
    }

    /**
     * Affichage du message résultant du test d'éligibilité
     * @param age
     * @param poids
     * @param sexe
     */
    private void afficheResult(Integer age, Integer poids, Integer sexe){
        //création du profil et récupération du message résultat
        this.controle.créerProfil(age,poids,sexe,this);
        String message = this.controle.getMessage();

        //affichage
        Toast.makeText(TestEligibleActivity.this, this.controle.getMessage(),Toast.LENGTH_SHORT).show();

    }

    /**
     * Récupération du profil au lencement du test d'éligibilité
     */
    private void recupProfil(){
        if (controle.getAge() != null){
            txtAge.setText(controle.getAge().toString());
            txtPoids.setText(controle.getPoids().toString());
            rdFemme.setChecked(true);
            if(controle.getSexe()==1){
                rdHomme.setChecked(true);
            }
            //((Button)findViewById(R.id.btnEligible)).performClick();
        }
    }
}
