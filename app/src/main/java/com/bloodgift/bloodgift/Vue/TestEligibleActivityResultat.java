package com.bloodgift.bloodgift.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bloodgift.bloodgift.R;

public class TestEligibleActivityResultat extends ActivityWithDrawer {

    final String EXTRA_RESULTAT = "resultat_test";
    private String resultatTest;

    private TextView tvResult1;
    private TextView tvResult2;
    private String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eligible_resultat);
        initializeView();

        tvResult1 = findViewById(R.id.txtResult1);
        tvResult2 = findViewById(R.id.txtResult2);
        str = "Ce test n'est pas officiel, vous devrez de toute façon passer par votre medecin pour connaître votre eligibilité aux dons.";

        Intent intent = getIntent();
        if (intent != null){
            resultatTest = intent.getStringExtra(EXTRA_RESULTAT);
        }

        annoncerResultat();
    }

    protected void initializeView(){
        super.initializeToolBar();
    }

    public void annoncerResultat(){
        tvResult1.setText(Html.fromHtml(resultatTest));
        tvResult2.setText(Html.fromHtml(str));
    }
}