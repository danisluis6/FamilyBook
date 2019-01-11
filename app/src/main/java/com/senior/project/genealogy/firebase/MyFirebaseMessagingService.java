package com.senior.project.genealogy.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.util.NotificationUtils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationManager notificationManager;

    public static String ADMIN_CHANNEL_ID = "Hello";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage.getNotification(), remoteMessage.getData());
        }
    }

    private void handleNotification(RemoteMessage.Notification remoteMessage, Map<String, String> dataPayload) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = new Random().nextInt(60000);

            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("title", remoteMessage.getTitle());

            String imageUrl = "https://firebasestorage.googleapis.com/v0/b/genealogy-c2e67.appspot.com/o/config%2FMemberJoin.png?alt=media&token=3c8c7383-5692-4049-b917-663836210d6f";

            if (dataPayload.size() > 0) {
                try {
                    JSONObject json = new JSONObject(dataPayload);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }

            final PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromURL(imageUrl);

                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.setBigContentTitle(remoteMessage.getTitle());
                try {
                    String content = new String(remoteMessage.getBody().getBytes("UTF-8"));
                    bigTextStyle.bigText(content);
                } catch (Exception e){

                }
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                bigPictureStyle.setBigContentTitle(remoteMessage.getTitle());
                bigPictureStyle.setSummaryText(Html.fromHtml(remoteMessage.getBody()).toString());
                bigPictureStyle.bigPicture(bitmap);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                        .setContentTitle(remoteMessage.getTitle())
                        .setContentText(remoteMessage.getBody())
                        .setAutoCancel(true)
                        .setSound(alarmSound)
                        .setStyle(bigTextStyle)
                        .setContentIntent(resultPendingIntent)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_notification))
                        .setSound(defaultSoundUri);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(notificationId, notificationBuilder.build());
            }
        }
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}