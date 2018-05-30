package childtracker.roti.com.childtracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.adapter.NotificationAdapter;
import childtracker.roti.com.childtracker.dto.NotificationsDto;

public class NotificationsActivity extends AppCompatActivity {


    private ArrayList<NotificationsDto.NotificationsMetaData> memberDtos = new ArrayList<NotificationsDto.NotificationsMetaData>();

    @BindView(R.id.rvNotification)
    RecyclerView rvMembersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        ButterKnife.bind(NotificationsActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.notify_activity_title)));
        prepareData();

        NotificationAdapter adapter = new NotificationAdapter(memberDtos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMembersList.setLayoutManager(mLayoutManager);
        rvMembersList.setItemAnimator(new DefaultItemAnimator());
        rvMembersList.setAdapter(adapter);
    }

    private void prepareData()

    {
        NotificationsDto.NotificationsMetaData memberMetadata = new NotificationsDto().new NotificationsMetaData();
        memberMetadata.setMemberName("Sachin Shah");
        memberMetadata.setMemberPhoneNo("874554120");
        memberMetadata.setMemberMessage("Please find this member near you");
        memberDtos.add(memberMetadata);

        memberMetadata = new NotificationsDto().new NotificationsMetaData();
        memberMetadata.setMemberName("Meena Shah");
        memberMetadata.setMemberPhoneNo("874998545");
        memberMetadata.setMemberMessage("Please find this member near you");
        memberDtos.add(memberMetadata);
    }
}
