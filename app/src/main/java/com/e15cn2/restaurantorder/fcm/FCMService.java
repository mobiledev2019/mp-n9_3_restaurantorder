package com.e15cn2.restaurantorder.fcm;

import android.content.Intent;

import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.utils.Constants;
import com.e15cn2.restaurantorder.utils.SharedPreferenceUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FCMService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SharedPreferenceUtils.getInstance(AppContext.getInstance()).saveDeviceToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        try {
            JSONObject data = json.getJSONObject(Constants.JsonNotificationKey.DATA);
            String title = data.getString(Constants.JsonNotificationKey.TITLE);
            String message = data.getString(Constants.JsonNotificationKey.MESSAGE);
            String imgUrl = data.getString(Constants.JsonNotificationKey.IMAGE);
            MyNotificationManager notificationManager = new MyNotificationManager();
            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (imgUrl.equals(Constants.JsonNotificationKey.RES_NULL)) {
                notificationManager.showSmallNotification(title, message, intent);
            } else {
                notificationManager.showBigNotification(title, message, imgUrl, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
