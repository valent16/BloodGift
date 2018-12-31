package com.bloodgift.bloodgift.Vue;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.SettingsController;
import com.bloodgift.bloodgift.R;

import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends ActivityWithDrawer {

    private Button modifierSettings;
    private Switch swNotifSang;
    private TextView tvSettings;
    private SettingsController controller;

    private String strSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeView();
        this.controller = new SettingsController(SettingsActivity.this);
        tvSettings = findViewById(R.id.tvSettings);
        modifierSettings = (Button) findViewById(R.id.notifSang);
        swNotifSang = (Switch) findViewById(R.id.switchNotifSang);

        strSettings = "<h1><font color=\"#DF0101\">Vos Préférences :</font></h1>";
        tvSettings.setText(Html.fromHtml(strSettings));
        recupSettings();
        ecouteBouton();
    }

    protected void initializeView() {
        super.initializeToolBar();
    }

    private void ecouteBouton(){
        modifierSettings.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Integer notifSang = 0;
                if(swNotifSang.isChecked()){
                    notifSang = 1;
                }

                controller.createSettings(notifSang);
                Toast.makeText(SettingsActivity.this, "Vos préférences sont enregistrées !",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recupSettings(){

        if (controller.getNotif() != null){
            if(controller.getNotif()==1){
                swNotifSang.setChecked(true);
            }
        }
    }
}