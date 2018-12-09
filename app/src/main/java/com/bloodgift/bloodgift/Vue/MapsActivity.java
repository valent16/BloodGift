package com.bloodgift.bloodgift.Vue;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bloodgift.bloodgift.Controller.MenuController;
import com.bloodgift.bloodgift.R;

public class MapsActivity extends ActivityWithDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initializeView();
    }

    protected void initializeView(){
        super.initializeToolBar();
    }
}
