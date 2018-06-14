package childtracker.roti.com.childtracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.adapter.NotificationAdapter;
import childtracker.roti.com.childtracker.dto.NotificationDisplayDto;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;

public class NotificationsActivity extends AppCompatActivity {


    // private ArrayList<NotificationsDto.NotificationsMetaData> memberDtos = new ArrayList<NotificationsDto.NotificationsMetaData>();

    ArrayList<NotificationDisplayDto> allNotifications = new ArrayList<NotificationDisplayDto>();
    @BindView(R.id.rvNotification)
    RecyclerView rvMembersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        ButterKnife.bind(NotificationsActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.notify_activity_title)));
        prepareData();
        String lat = getIntent().getStringExtra(Constants.EXTRA_LAT);
        String lng = getIntent().getStringExtra(Constants.EXTRA_LNG);
        NotificationAdapter adapter = new NotificationAdapter(NotificationsActivity.this, allNotifications, lat, lng);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMembersList.setLayoutManager(mLayoutManager);
        rvMembersList.setItemAnimator(new DefaultItemAnimator());
        rvMembersList.setAdapter(adapter);
    }

    private void prepareData()

    {
        CustomSharedPreferance customSharedPreferance = new CustomSharedPreferance(this);
        Type listType = new TypeToken<ArrayList<NotificationDisplayDto>>() {
        }.getType();
        allNotifications = (ArrayList<NotificationDisplayDto>) ChildTrackerUtils.convertJsonToObject(customSharedPreferance.getString(Constants.SHARED_PREF_ALL_NOTFICATION), listType);
    }
}
