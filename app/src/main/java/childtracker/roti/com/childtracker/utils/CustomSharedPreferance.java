package childtracker.roti.com.childtracker.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class CustomSharedPreferance {

    private static final String TAG = CustomSharedPreferance.class.getSimpleName();
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPref;
    private Context mContext;

    /**
     * Default Constructor
     *
     * @param context
     */
    public CustomSharedPreferance(Context context) {
        mContext = context;
        mSharedPref = context.getSharedPreferences("shared_pref", context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();

    }

    /**
     * Addes the string to shared pref
     *
     * @param key
     * @param value
     */
    public void addString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * Gets the string from shared pref
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        String result = mSharedPref.getString(key, null);
        return result;
    }

    /**
     * Addes the string to shared pref
     *
     * @param key
     * @param value
     */
    public void addInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * Gets the string from shared pref
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        int result = mSharedPref.getInt(key, 0);
        return result;
    }

    public void clearSSharedPref() {
        mEditor.clear();
        mEditor.commit();
    }
}
