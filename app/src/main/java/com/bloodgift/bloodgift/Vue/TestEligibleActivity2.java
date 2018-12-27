package com.bloodgift.bloodgift.Vue;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.R;

public class TestEligibleActivity2 extends ActivityWithDrawer {

    final String EXTRA_RESULTAT = "resultat_test";
    final String EXTRA_SEXE = "sexe";

    private String resultatTest;
    private Integer sexe;

    private TextView questionAccouchement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eligible2);
        initializeView();
        ecouteBouton();

        questionAccouchement = findViewById(R.id.questionAccouchement);
        resultatTest = "Erreur lors de la réalisation du test";
        questionAccouchement.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        if (intent != null){
            resultatTest = intent.getStringExtra(EXTRA_RESULTAT);
            sexe = intent.getIntExtra(EXTRA_SEXE,1);
            if (sexe==1){
                questionAccouchement.setVisibility(View.GONE);
            }
        }
    }

    protected void initializeView(){
        super.initializeToolBar();
    }

    private void ecouteBouton(){
        ((Button) findViewById(R.id.btnEligible2)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TestEligibleActivity2.this, TestEligibleActivityResultat.class);
                intent = intent.putExtra(EXTRA_RESULTAT, resultatTest);
                startActivity(intent);

            }
        });
    }
}
