package com.bloodgift.bloodgift.Vue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

public class TestEligibleActivity extends ActivityWithDrawer {

    //propriétés datepicker Sang
    public static TextView tvSang;
    public static Calendar dateSang;

    //propriétés
    private ProfileController controller;
    private EditText txtAge;
    private EditText txtPoids;
    private RadioButton rdHomme;
    private RadioButton rdFemme;


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

        public void onDateSet(DatePicker view, int year, int month, int day) {
            //tvSang.setText("Selected Date: " + (month + 1) + "-" + day + "-" + year);
            c.set(Calendar.DAY_OF_MONTH,day);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.YEAR,year);
            tvSang.setText("Selected Date: " + c.get(Calendar.DAY_OF_MONTH) + " / " + (c.get(Calendar.MONTH)+1) + " / " + c.get(Calendar.YEAR));
            dateSang = c;
        }

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
        tvSang = findViewById(R.id.selected_date);
        this.controller = new ProfileController(this);
        recupProfil();
        ecouteBouton();


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }


    /**
     * Ecoute evenment sur le bouton "suis-je éligible ?"
     */
    private void ecouteBouton(){
        ((Button) findViewById(R.id.btnEligible)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                //dateSang = Calendar.getInstance();
                //dateSang = ConvertDate.ConvertStringToCalendar("20-10-1997");
                //Toast.makeText(TestEligibleActivity.this, "Date : " + dateSang.get(Calendar.DAY_OF_MONTH) + " / " + dateSang.get(Calendar.MONTH),Toast.LENGTH_SHORT).show();


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
        controller.createProfile(dateSang, age,poids,sexe);

        //affichage
        Toast.makeText(TestEligibleActivity.this, controller.getMessage(),Toast.LENGTH_SHORT).show();

    }

    /**
     * Récupération du profil au lencement du test d'éligibilité
     */
    private void recupProfil(){
        if (controller.getAge() != null){
            dateSang = controller.getDateSang();
            tvSang.setText("Selected Date: " + dateSang.get(Calendar.DAY_OF_MONTH) + " / " + (dateSang.get(Calendar.MONTH)+1) + " / " + dateSang.get(Calendar.YEAR));
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
