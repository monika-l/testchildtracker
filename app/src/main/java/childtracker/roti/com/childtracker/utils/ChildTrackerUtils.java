package childtracker.roti.com.childtracker.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ChildTrackerUtils {
    public static AlphaAnimation clickEffect = new AlphaAnimation(1.0f, 0.5f);

    private static ProgressBar mProgressBar;
    public static int isWindowProcessingEnabled = 0;
    private static String TAG = ChildTrackerUtils.class.getSimpleName();

    /**
     * Navigates to activities
     *
     * @param from
     * @param to
     * @param isFinish
     */
    public static void navigateToScreen(Activity from, Class to, boolean isFinish) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        if (true == isFinish) {
            from.finish();
        }
    }

    /**
     * Checks the network availiblity
     *
     * @param context
     * @return
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // test for connection
        if (networkInfo != null && networkInfo.isAvailable()
                && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate email id
     *
     * @param target
     * @return
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }


    /**
     * Shows the progressbar
     *
     * @param activity
     */
    public final static void showProgressBar(Activity activity) {
//        DotsZoomProgress progressBar = new DotsZoomProgress(activity, null);
//        activity.getWindow().getDecorView().findViewById(android.R.id.content).setAlpha((float) 0.5);
//        ViewGroup rootView = activity.findViewById(R.id.llRootView);
//        RelativeLayout.LayoutParams layoutParams =
//                (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        progressBar.setLayoutParams(layoutParams);
//        rootView.addView(progressBar);

        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(activity);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            mProgressBar.setLayoutParams(params);
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) mProgressBar.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mProgressBar.setLayoutParams(layoutParams);
            getLayoutRootView(activity).addView(mProgressBar);
        }

    }

    /**
     * Hides the progress bar
     *
     * @param activity
     */
    public final static void hideProgressBar(Activity activity) {
        if (mProgressBar != null) {
            getLayoutRootView(activity).removeView(mProgressBar);
        }
        mProgressBar = null;
    }


    /**
     * Gets the rootview of Activity
     *
     * @param activity
     * @return
     */
    public static ViewGroup getLayoutRootView(Activity activity) {
        ViewGroup viewGroup = null;
        try {
            viewGroup = (ViewGroup) ((ViewGroup) activity
                    .findViewById(android.R.id.content)).getChildAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            // for some elements like frgaments the rootview may not exists
        }
        return viewGroup;
    }


    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    /**
     * Allows to show alpha window on Activity
     *
     * @param context
     */
    public static void enableWindowProcessing(Activity context) {
        isWindowProcessingEnabled = 1;
        showProgressBar(context);
        context.getWindow().getDecorView().findViewById(android.R.id.content).setAlpha((float) 0.5);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Disbales to show alpha window on Activity
     *
     * @param context
     */
    public static void disableWindowProcessing(Activity context) {
        hideProgressBar(context);
        context.getWindow().getDecorView().findViewById(android.R.id.content).setAlpha((float) 1);
        context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        isWindowProcessingEnabled = 0;
    }

    /**
     * Generate random 4 digit code for varification
     *
     * @return
     */
    public static String generateVarificationCode() {
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        return id;
    }

    /**
     * Short hand code for showing toast messages
     *
     * @param activity
     * @param message
     */
    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }





    public static String convertDateToDatePickerFormat(int year, int month, int day) {
        String result = null;
        try {
            String datestr = year + "-" + (month + 1) + "-" + day;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = dateFormat.parse(datestr);
            dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.US);
            result = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }




    /**
     * Addes minutes in existing timeline
     *
     * @param time
     * @param min
     * @return
     */
    public static String addMinutesInTime(String time, int min) {
        String updatedTime = time;
        try {
            SimpleDateFormat df = new SimpleDateFormat("h:mm a");
            Date d = df.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, min);
            updatedTime = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedTime;
    }




    /**
     * Generate Time format provided hr and min
     *
     * @param hr
     * @param min
     * @return
     */
    public static String getTimeInAmPmFormat(int hr, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hr);
        cal.set(Calendar.MINUTE, min);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(cal.getTime());
    }

    /**
     * Provides unique id for device
     *
     * @param activity
     * @return
     */
    public static String getUniqueDeviceId(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }




    /**
     * Used to send object as string
     *
     * @param object
     * @return
     */
    public static String convertObjectToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Converts Json represention into Object
     *
     * @param json
     * @param classType
     * @return
     */
    public static Object convertJsonToObject(String json, Class classType) {
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, classType);
        return obj;
    }


    /**
     * Generic Back event handling
     *
     * @param activity
     * @return
     */
    public static View.OnClickListener navigationBackLisenter(final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        };
    }


    /**
     * Generic callback for all the generic no action on result
     */
    public static Callback mGenericCallBack = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
        }

        @Override
        public void onFailure(Call call, Throwable t) {
        }
    };



    /**
     * Returns Calendar Base URI, supports both new and old OS.
     */
    private static String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }



    public static void disableSpaceFromEditext(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (editText.getText().toString().contains(" ")) {
                    editText.setText(editText.getText().toString().replaceAll(" ", ""));
                    editText.setSelection(editText.getText().length());
                }
            }
        });
    }



    public static void navigateToLocationOnGoogleMap(Context context, Double lat, Double lng, String title) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=" + lat + "," + lng + " (" + title + ")"));
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + lat + "," + lng + " (" + title + ")"));
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context, "Google Map Application not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String convertTimeToAmPm(String time) throws Exception {
        String result = time;
        try {
            SimpleDateFormat t24 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat t12 = new SimpleDateFormat("hh:mm a");
            Date date = t24.parse(time);
            result = (t12.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getDateWithBuffer(int extraDays) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        c.add(Calendar.DATE, extraDays);  // number of days to add
        String result = df.format(c.getTime());
        return result;
    }


}
