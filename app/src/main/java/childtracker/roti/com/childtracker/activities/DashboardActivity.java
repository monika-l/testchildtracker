package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class DashboardActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mSlidingLayout;


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

    @OnClick(R.id.llScrollProfile)
    public void onProfileClick(){
        Intent activity = new Intent(DashboardActivity.this, SendMessageToCommunityActivity.class);
        startActivity(activity);
    }
    @OnClick(R.id.llUserProfile)
    public void onLLProfileClick(){
        Intent activity = new Intent(DashboardActivity.this, SendMessageToCommunityActivity.class);
        startActivity(activity);
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
