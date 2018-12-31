package com.bloodgift.bloodgift.Vue;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.MenuController;
import com.bloodgift.bloodgift.R;


public class HomePageActivity extends ActivityWithDrawer {

    private TextView tvHomePage;
    private String strHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initializeView();
        tvHomePage = findViewById(R.id.tvHomePage);
        strHomePage = "<h2>Bienvenue sur notre application <h2><h1><font color=\"#DF0101\">BloodGift !!</font></h1>";
        strHomePage += "<br><p>Testez votre éligibilité aux différents dons sanguins, découvrez les centres de collecte près de chez vous, soyez notifié lorsque vous pouvez donner de nouveau !</p>";
        tvHomePage.setText(Html.fromHtml(strHomePage));
    }

    protected void initializeView(){
        super.initializeToolBar();
    }

}
