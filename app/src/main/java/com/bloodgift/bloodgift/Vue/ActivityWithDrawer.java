package com.bloodgift.bloodgift.Vue;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bloodgift.bloodgift.Controller.MenuController;
import com.bloodgift.bloodgift.R;

public class ActivityWithDrawer extends AppCompatActivity {
    //Drawer Layout of the application
    private DrawerLayout myDrawerLayout;

    private MenuController menuController;

    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_page);
    }

    protected void initializeToolBar(){
        menuController = new MenuController((NavigationView)findViewById(R.id.nav_view), this);

        //set the application toolbar as the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        actionbar.setDisplayShowTitleEnabled(false);

        myDrawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                myDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
