package com.bloodgift.bloodgift.Vue;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bloodgift.bloodgift.R;

public class SettingsActivity extends ActivityWithDrawer {

    private Button addNotificationBtn;
    private Button deleteNotificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeView();
        addNotificationBtn = (Button) findViewById(R.id.notifSang);


        addNotificationBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getBaseContext(), "Ajout d'une notification", Toast.LENGTH_SHORT).show();
                createNotification();
            }
        });
    }

    protected void initializeView() {
        super.initializeToolBar();
    }

    private final void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, SettingsActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);


        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker("Je suis un Ticker")
                .setSmallIcon(R.mipmap.logo_bloodgift)
                .setContentTitle("JE suis un titre")
                .setContentText("et moi le text..")
                .setContentIntent(pendingIntent);

        mNotification.notify(1, builder.build());
    }
}