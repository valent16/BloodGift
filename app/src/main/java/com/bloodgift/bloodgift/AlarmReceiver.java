package com.bloodgift.bloodgift;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.bloodgift.bloodgift.Vue.HomePageActivity;

public class AlarmReceiver extends Service {

    public static Context context = MyApplication.getAppContext();

    public AlarmReceiver() {
    }

    public static final int NOTIFICATION_ID = 5111;

    /**     * Class for clients to access     */
    public class ServiceBinder extends Binder {
        AlarmReceiver getService() {
            return AlarmReceiver.this;
        }
    }

    @Override    public void onCreate() {
        Log.i("AlarmReceiver", "onCreate()");
    }

    @Override    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // If this service was started by out AlarmTask intent then we want to show our notification
        if (intent.getBooleanExtra("Alarm", false)) {
        showNotification();
        }

    // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**     * Creates a notification and shows it in the OS drag-down status bar     */
    private void showNotification() {


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder mBuilder = new Notification.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.logo_bloodgift)
                .setContentTitle("Donnez votre sang !")
                .setAutoCancel(true)
                .setContentText("Vous pouvez dès aujourd'hui donner votre sang de nouveau")
                .setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
}