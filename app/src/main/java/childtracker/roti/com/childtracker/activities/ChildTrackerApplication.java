package childtracker.roti.com.childtracker.activities;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

import java.util.ArrayList;

import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;


public class ChildTrackerApplication extends Application {
    private static ChildTrackerApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        ArrayList<String> allNotification = new ArrayList<String>();
        CustomSharedPreferance customSharedPreferance = new CustomSharedPreferance(this);
        if (customSharedPreferance.getString(Constants.SHARED_PREF_ALL_NOTFICATION) == null) {
            customSharedPreferance.addString(Constants.SHARED_PREF_ALL_NOTFICATION, ChildTrackerUtils.convertObjectToJson(allNotification));
        }

    }

    public static Context getContext() {
        return sInstance;
    }

    public ChildTrackerApplication() {
        sInstance = this;
    }

}