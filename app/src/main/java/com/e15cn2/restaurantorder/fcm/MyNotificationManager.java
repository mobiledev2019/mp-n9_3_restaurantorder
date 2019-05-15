package com.e15cn2.restaurantorder.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class MyNotificationManager {
    private final String ID_BIG_NOTIFICATION_CHANNEL = "ID_BIG_NOTIFICATION_CHANNEL";
    private final String ID_SMALL_NOTIFICATION_CHANNEL = "ID_SMALL_NOTIFICATION_CHANNEL";
    private int mId;
    private Context mContext;

    public MyNotificationManager() {
        mId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        mContext = AppContext.getInstance();
    }

    public void showBigNotification(String title, String message, String url, Intent intent) {
      NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, ID_BIG_NOTIFICATION_CHANNEL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ID_BIG_NOTIFICATION_CHANNEL,
                    ID_BIG_NOTIFICATION_CHANNEL,
                    android.app.NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                mContext,
                mId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromUrl(url));

        Notification notification = builder
                .setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.drawable.ic_restaurant_app)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_restaurant_app))
                .setContentText(message)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mId, notification);

    }

    public void showSmallNotification(String title, String message, Intent intent) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, ID_SMALL_NOTIFICATION_CHANNEL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ID_SMALL_NOTIFICATION_CHANNEL,
                    ID_SMALL_NOTIFICATION_CHANNEL,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                mContext,
                mId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Notification notification = builder
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_restaurant_app)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_restaurant_app))
                .setContentText(message)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(mId, notification);
    }

    private Bitmap getBitmapFromUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap mBitmap = BitmapFactory.decodeStream(input);
            return mBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
