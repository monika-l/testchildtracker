package childtracker.roti.com.childtracker.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.activities.ChildTrackerApplication;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;

/**
 * OneSingal notification extender to stop onesingal defualt notifications and create our own notification on certains conditions
 */

public class OneSignalNotificationExtender extends NotificationExtenderService {

    private final static String TAG = OneSignalNotificationExtender.class.getSimpleName();

    /**
     * Customize notification as per need when on signal notification is received
     *
     * @param notification
     * @return
     */
    @Override
    protected boolean onNotificationProcessing(final OSNotificationReceivedResult notification) {
        CustomSharedPreferance mSharedPref = new CustomSharedPreferance(this);
        Log.d("noti = >>>>>>>", ChildTrackerUtils.convertObjectToJson(notification));
        Log.d("noti = >>>>>>>", notification.payload.body);
        CustomSharedPreferance customSharedPreferance = new CustomSharedPreferance(this);
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> allNotifications = (ArrayList<String>) ChildTrackerUtils.convertJsonToObject(customSharedPreferance.getString(Constants.SHARED_PREF_ALL_NOTFICATION), listType);
        allNotifications.add(notification.payload.body);
        customSharedPreferance.addString(Constants.SHARED_PREF_ALL_NOTFICATION, ChildTrackerUtils.convertObjectToJson(allNotifications));
        showNotification(ChildTrackerApplication.getContext(),"Child Tracker" , notification.payload.body,null );
        return true;
    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);



        notificationManager.notify(notificationId, mBuilder.build());
    }


}
