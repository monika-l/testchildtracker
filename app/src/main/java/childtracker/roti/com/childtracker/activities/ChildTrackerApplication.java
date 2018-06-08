package childtracker.roti.com.childtracker.activities;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

import java.util.ArrayList;

import childtracker.roti.com.childtracker.dto.NotificationDisplayDto;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;
import io.fabric.sdk.android.Fabric;


public class ChildTrackerApplication extends Application {
    private static ChildTrackerApplication sInstance;
    private CustomSharedPreferance customSharedPreferance;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        ArrayList<NotificationDisplayDto> allNotification = new ArrayList<NotificationDisplayDto>();
        customSharedPreferance = new CustomSharedPreferance(this);
        if (customSharedPreferance.getString(Constants.SHARED_PREF_ALL_NOTFICATION) == null) {
            customSharedPreferance.addString(Constants.SHARED_PREF_ALL_NOTFICATION, ChildTrackerUtils.convertObjectToJson(allNotification));
        }
        Fabric.with(this, new Crashlytics());

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);

                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);
                customSharedPreferance.addString(Constants.SHAREDPREF_PLAYER_ID, userId);
            }
        });

    }

    public static Context getContext() {
        return sInstance;
    }

    public ChildTrackerApplication() {
        sInstance = this;
    }

}