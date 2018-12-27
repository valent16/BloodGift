package com.bloodgift.bloodgift.Vue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.ProfileController;
import com.bloodgift.bloodgift.Outils.ConvertDate;

import com.bloodgift.bloodgift.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestEligibleActivity extends ActivityWithDrawer {

    final String EXTRA_RESULTAT = "resultat_test";
    final String EXTRA_SEXE = "sexe";

    //propriétés datepickers
    public static TextView tvSang;
    public static Calendar dateSang;

    public static TextView tvPlaque;
    public static Calendar datePlaque;

    public static TextView tvPlasma;
    public static Calendar datePlasma;

    //propriétés
    private ProfileController controller;
    private EditText txtAge;
    private EditText txtPoids;
    private RadioButton rdHomme;
    private RadioButton rdFemme;


    /**
     * Class des datePickers
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private Calendar c;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * Modification d'un date picker
         * @param view
         * @param year
         * @param month
         * @param day
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //tvSang.setText("Selected Date: " + (month + 1) + "-" + day + "-" + year);
            c.set(Calendar.DAY_OF_MONTH,day);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.YEAR,year);
            /**
             * Identification du datepicker modifié, récupération des données et modification du TextView correspondant
             */
            if (modifDate == "Sang"){
                tvSang.setText("Dernier don de sang :\n" + c.get(Calendar.DAY_OF_MONTH) + " / " + (c.get(Calendar.MONTH)+1) + " / " + c.get(Calendar.YEAR));
                dateSang = c;
            } else if (modifDate == "Plaque"){
                tvPlaque.setText("Dernier don de plaquettes :\n" + c.get(Calendar.DAY_OF_MONTH) + " / " + (c.get(Calendar.MONTH)+1) + " / " + c.get(Calendar.YEAR));
                datePlaque = c;
            } else {
                tvPlasma.setText("Dernier don de plasma :\n" + c.get(Calendar.DAY_OF_MONTH) + " / " + (c.get(Calendar.MONTH)+1) + " / " + c.get(Calendar.YEAR));
                datePlasma = c;
            }
        }
    }

    public static String modifDate;

    /**
     * Listener des bouton "modifier date", et lancement du DatePiker correspondant
     * @param v
     */
    public void showDatePickerSang(View v) {
        modifDate = "Sang";
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "dateSang");
    }
    public void showDatePickerPlaque(View v) {
        modifDate = "Plaque";
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePlaque");
    }
    public void showDatePickerPlasma(View v) {
        modifDate = "Plasma";
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePlasma");
    }


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

    /**
     * Initialisation des liens avec les objets graphiques
     */
    private void init(){
        txtAge = findViewById(R.id.txtAge);
        txtPoids = findViewById(R.id.txtPoids);
        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdFemme);
        tvSang = findViewById(R.id.dateSang);
        tvPlaque = findViewById(R.id.datePlaque);
        tvPlasma = findViewById(R.id.datePlasma);
        this.controller = new ProfileController(this);
        recupProfil();
        ecouteBouton();
    }



    /**
     * Ecoute evenment sur le bouton "suis-je éligible ?"
     */
    private void ecouteBouton(){
        ((Button) findViewById(R.id.btnEligible)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

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
        controller.createProfile(dateSang, datePlaque, datePlasma, age,poids,sexe);

        //Lancement de la seconde activity du test (avec en extra le resultat de la première page)
        Intent intent = new Intent(TestEligibleActivity.this, TestEligibleActivity2.class);
        intent = intent.putExtra(EXTRA_SEXE, sexe);
        intent = intent.putExtra(EXTRA_RESULTAT, controller.getMessage());
        startActivity(intent);

        //affichage
        //Toast.makeText(TestEligibleActivity.this, controller.getMessage(),Toast.LENGTH_SHORT).show();

    }

    /**
     * Récupération du profil au lencement du test d'éligibilité
     */
    private void recupProfil(){

        //récupération de la date du dernier don de sang; Si c'est la première fois (date fixé au 1/1/2016), le TextView affiche "Jamais"
        dateSang = controller.getDateSang();
        if (dateSang.get(Calendar.YEAR)==2016 && dateSang.get(Calendar.MONTH)==0 && dateSang.get(Calendar.DAY_OF_MONTH)==1){
            tvSang.setText("Dernier don de sang :\nJamais");
        } else {
            tvSang.setText("Dernier don de sang :\n" + dateSang.get(Calendar.DAY_OF_MONTH) + " / " + (dateSang.get(Calendar.MONTH) + 1) + " / " + dateSang.get(Calendar.YEAR));
        }
        //récupération de la date du dernier don de plaquette
        datePlaque = controller.getDatePlaque();
        if (datePlaque.get(Calendar.YEAR)==2016 && datePlaque.get(Calendar.MONTH)==0 && datePlaque.get(Calendar.DAY_OF_MONTH)==1){
            tvPlaque.setText("Dernier don de plaquettes :\nJamais");
        } else {
            tvPlaque.setText("Dernier don de plaquettes :\n" + datePlaque.get(Calendar.DAY_OF_MONTH) + " / " + (datePlaque.get(Calendar.MONTH) + 1) + " / " + datePlaque.get(Calendar.YEAR));
        }
        //récupération de la date du dernier don de plasma
        datePlasma = controller.getDatePlasma();
        if (datePlasma.get(Calendar.YEAR)==2016 && datePlasma.get(Calendar.MONTH)==0 && datePlasma.get(Calendar.DAY_OF_MONTH)==1){
             tvPlasma.setText("Dernier don de plasma :\nJamais");
        } else {
            tvPlasma.setText("Dernier don de plasma :\n" + datePlasma.get(Calendar.DAY_OF_MONTH) + " / " + (datePlasma.get(Calendar.MONTH) + 1) + " / " + datePlasma.get(Calendar.YEAR));
        }

        if (controller.getAge() != null){

            txtAge.setText(controller.getAge().toString());
            txtPoids.setText(controller.getPoids().toString());
            rdFemme.setChecked(true);
            if(controller.getSexe()==1){
                rdHomme.setChecked(true);
            }
            //((Button)findViewById(R.id.btnEligible)).performClick();
        }
    }
}
