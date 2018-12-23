package com.bloodgift.bloodgift.Controller;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.bloodgift.bloodgift.R;
import com.bloodgift.bloodgift.Vue.HomePageActivity;
import com.bloodgift.bloodgift.Vue.MapsActivity;
import com.bloodgift.bloodgift.Vue.TestEligibleActivity;
import com.bloodgift.bloodgift.Vue.SettingsActivity;

public class MenuController {

    //Activity that contains the controller
    AppCompatActivity activity;

    //Navigation view containing the different menu controls
    NavigationView navigationView;

    /**
     * Creates the controller that manages the menu actions
     * @param navigationView
     */
    public MenuController(NavigationView navigationView, AppCompatActivity activity){
        this.navigationView = navigationView;
        this.activity = activity;

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_notification:
                            display_Settings_Page();
                            return true;
                        case R.id.nav_eligibility:
                            display_Eligibility_Page();
                            return true;
                        case R.id.nav_map:
                            display_Maps_Page();
                            return true;
                        case R.id.nav_home:
                            display_Home_Page();
                            return true;
                        default:
                            return false;
                    }
                }
            }
        );
    }

    private void display_Eligibility_Page(){
        Toast.makeText(activity, "Test d'éligibilité", Toast.LENGTH_LONG).show();
        Intent intent = new Intent().setClass(activity,TestEligibleActivity.class);
        activity.startActivity(intent);
    }

    private void display_Maps_Page(){
        Toast.makeText(activity, "Carte des centres", Toast.LENGTH_LONG).show();
        Intent intent = new Intent().setClass(activity,MapsActivity.class);
        activity.startActivity(intent);
    }

    private void display_Settings_Page(){
        Toast.makeText(activity, "Paramètres de notifications", Toast.LENGTH_LONG).show();
        Intent intent = new Intent().setClass(activity,SettingsActivity.class);
        activity.startActivity(intent);
    }

    private void display_Home_Page(){
        Toast.makeText(activity, "Page d'accueil", Toast.LENGTH_LONG).show();
        Intent intent = new Intent().setClass(activity,HomePageActivity.class);
        activity.startActivity(intent);
    }
}
