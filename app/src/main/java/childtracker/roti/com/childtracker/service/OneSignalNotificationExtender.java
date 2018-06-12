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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.activities.ChildTrackerApplication;
import childtracker.roti.com.childtracker.dto.NotificationDisplayDto;
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
        try {
            CustomSharedPreferance mSharedPref = new CustomSharedPreferance(this);
            Log.d("noti = >>>>>>>", ChildTrackerUtils.convertObjectToJson(notification));
            Log.d("noti = >>>>>>>", notification.payload.body);
            CustomSharedPreferance customSharedPreferance = new CustomSharedPreferance(this);
            Type listType = new TypeToken<ArrayList<NotificationDisplayDto>>() {
            }.getType();
            ArrayList<NotificationDisplayDto> allNotifications = (ArrayList<NotificationDisplayDto>) ChildTrackerUtils.convertJsonToObject(customSharedPreferance.getString(Constants.SHARED_PREF_ALL_NOTFICATION), listType);
            NotificationDisplayDto notificationDisplayDto = new NotificationDisplayDto();
            Log.d(TAG, notification.payload.additionalData.toString());
            if (notification.payload.additionalData.has("extra_data")) {
                String memberData = notification.payload.additionalData.getString("extra_data");
                JSONObject jsonObj = new JSONObject(memberData);
                JSONArray jsonArray = jsonObj.getJSONArray("memberInfo");
                JSONObject memberDetails = (JSONObject) (jsonArray.get(0));
                Log.d("noti = >>>>>>>", memberData);
                Log.d("noti = >>>>>>>", memberDetails.getString("photo"));
                notificationDisplayDto.setMessage(notification.payload.body);
                notificationDisplayDto.setMemberId(jsonObj.getString("memberId"));
                notificationDisplayDto.setImages(memberDetails.getString("photo"));
                allNotifications.add(notificationDisplayDto);
            } else if (notification.payload.additionalData.has("replyInfo")) {
                notificationDisplayDto.setMessage(notification.payload.body);
                String memberData = notification.payload.additionalData.getString("replyInfo");
                JSONObject jsonObj = new JSONObject(memberData);
                String memberId = jsonObj.getString("memberId");
                String userName = jsonObj.getString("userName");
                String childName = jsonObj.getString("childName");
                String childPhoto = jsonObj.getString("childPhoto");
                 String userMobile = jsonObj.getString("userMobile");
                String userPhoto = jsonObj.getString("userPhoto");
                notificationDisplayDto.setMessage(notification.payload.body);
                notificationDisplayDto.setMemberId(memberId);
                notificationDisplayDto.setUserName(userName);
                notificationDisplayDto.setChildName(childName);
                notificationDisplayDto.setChildPhoto(childPhoto);
                 notificationDisplayDto.setUserMobile(userMobile);
                notificationDisplayDto.setUserPhoto(userPhoto);
                allNotifications.add(notificationDisplayDto);
            }

            customSharedPreferance.addString(Constants.SHARED_PREF_ALL_NOTFICATION, ChildTrackerUtils.convertObjectToJson(allNotifications));
            showNotification(ChildTrackerApplication.getContext(), "Child Tracker", notification.payload.body, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
