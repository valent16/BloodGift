package com.bloodgift.bloodgift.Vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bloodgift.bloodgift.R;

public class PersonnalInformationActivity extends ActivityWithDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnal_information);
        initializeView();
    }

    protected void initializeView(){
        super.initializeToolBar();
    }
}
