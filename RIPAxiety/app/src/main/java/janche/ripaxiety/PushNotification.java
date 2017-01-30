package janche.ripaxiety;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

/**
 * NOTE: Not currently functional, but does not break anything else
 *
 * Purpose: Generate push notifications with motivational expressions
 *
 * Created by Liliana on 12/4/2016.
 */

public class PushNotification extends AppCompatActivity
{
    //Specify UI information and specifications

    //Create Builder object
    //and set notification properties

    //Attach actions

    //Create intent to start notification
    Intent notificationIntent = new Intent(this, MainActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

    public void setContentIntent(PendingIntent contentIntent) {
        this.contentIntent = contentIntent;
    }
    // myBuilder.setContentIntent(contentIntent)

    //NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    //Notification notify = new Notification(getApplicationContext());
            //(getApplicationContext()).setContentTitle("Test").setContentText("Testing").
             //setContentTitle("Check").setSmallIcon(R.mipmap.RIPAnxietySmall);//.build();
    //notify.flags |= Notification.FLAG_AUTO_CANCEL;
    //notif.notify(0, notify);
}
