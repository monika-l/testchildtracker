package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.CircleTransform;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;

public class DashboardActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CustomSharedPreferance mCustomSharedPref;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mSlidingLayout;

    @BindView(R.id.llUserProfile)
    LinearLayout mLlUserProfile;

    @BindView(R.id.tvBell)
    TextView tvBell;

    @BindView(R.id.btAddMember)
    Button btAddMember;

    @OnClick(R.id.tvBell)
    public void onClickBell() {
        Intent siginActivity = new Intent(DashboardActivity.this, NotificationsActivity.class);
        startActivity(siginActivity);
    }

    @OnClick(R.id.btAddMember)
    public void onNotify() {
        Intent siginActivity = new Intent(DashboardActivity.this, AddMemberActivity.class);
        startActivity(siginActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        ButterKnife.bind(DashboardActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.dashboard_activity_title)));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(DashboardActivity.this);
        mCustomSharedPref = new CustomSharedPreferance(DashboardActivity.this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> allNotifications = (ArrayList<String>) ChildTrackerUtils.convertJsonToObject(mCustomSharedPref.getString(Constants.SHARED_PREF_ALL_NOTFICATION), listType);
        tvBell.setText(String.valueOf(allNotifications.size()));
        loadMembers();
    }

    private void loadMembers() {
        Type listType = new TypeToken<List<LoginResponseDto.UserMembers>>() {
        }.getType();
        final ArrayList<LoginResponseDto.UserMembers> members = (ArrayList<LoginResponseDto.UserMembers>) ChildTrackerUtils.convertJsonToObject(mCustomSharedPref.getString(Constants.SHARED_PREF_ALL_MEMBERS), listType);
        mLlUserProfile.removeAllViews();
        if (members != null && members.size() > 0) {


            for (int position = 0; position < members.size(); position++) {

                LayoutInflater inflater = (LayoutInflater) this
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View childLayout = inflater.inflate(
                        R.layout.user_member_selection_component, null);

                TextView userName = (TextView) childLayout
                        .findViewById(R.id.tvUserName);
                final ImageView userImage = (ImageView) childLayout
                        .findViewById(R.id.ivUserImage);

                userImage.setTag(R.string.app_name, position);
                userImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent siginActivity = new Intent(DashboardActivity.this, SendMessageToCommunityActivity.class);
                        siginActivity.putExtra(Constants.EXTRA_MEMBER_DETAILS, ChildTrackerUtils.convertObjectToJson(members.get(  ( (int)userImage.getTag(R.string.app_name)))));
                        startActivity(siginActivity);
                    }
                });
                if (members.get(position).getPhoto() != null && TextUtils.isEmpty(members.get(position).getPhoto()) == false) {
                    Picasso.with(DashboardActivity.this).load("http://52.91.166.193/Webservices/ChildTracker/" + members.get(position).getPhoto()).transform(new CircleTransform()).into(userImage);
                } else {
                    Picasso.with(DashboardActivity.this).load(R.mipmap.ic_user).transform(new CircleTransform()).into(userImage);
                }

                userName.setText(members.get(position).getChildName());

                mLlUserProfile.addView(childLayout);

            }
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
