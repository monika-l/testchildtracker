package childtracker.roti.com.childtracker.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
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
import childtracker.roti.com.childtracker.dto.NotificationDisplayDto;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.CircleTransform;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;

public class DashboardActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private Location mylocation;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private String TAG = DashboardActivity.class.getSimpleName();

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
        if(mylocation!=null){
            Intent siginActivity = new Intent(DashboardActivity.this, NotificationsActivity.class);
            siginActivity.putExtra(Constants.EXTRA_LAT,String.valueOf(mylocation.getLatitude()));
            siginActivity.putExtra(Constants.EXTRA_LNG,String.valueOf(mylocation.getLongitude()));
            startActivity(siginActivity);
        }else{
            Toast.makeText(DashboardActivity.this, R.string.please_turn_on_network, Toast.LENGTH_LONG).show();

        }

    }

    @OnClick(R.id.btAddMember)
    public void onNotify() {
        Intent siginActivity = new Intent(DashboardActivity.this, AddMemberActivity.class);
        startActivity(siginActivity);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Type listType = new TypeToken<ArrayList<NotificationDisplayDto>>() {
        }.getType();
        ArrayList<NotificationDisplayDto> allNotifications = (ArrayList<NotificationDisplayDto>) ChildTrackerUtils.convertJsonToObject(mCustomSharedPref.getString(Constants.SHARED_PREF_ALL_NOTFICATION), listType);
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
                        if (mylocation != null) {
                            Intent siginActivity = new Intent(DashboardActivity.this, SendMessageToCommunityActivity.class);
                            siginActivity.putExtra(Constants.EXTRA_LAT, String.valueOf(mylocation.getLatitude()));
                            siginActivity.putExtra(Constants.EXTRA_LNG, String.valueOf(mylocation.getLongitude()));
                            siginActivity.putExtra(Constants.EXTRA_MEMBER_DETAILS, ChildTrackerUtils.convertObjectToJson(members.get(((int) userImage.getTag(R.string.app_name)))));
                            startActivity(siginActivity);
                        } else {
                            Toast.makeText(DashboardActivity.this, R.string.please_turn_on_network, Toast.LENGTH_LONG).show();
                        }

                    }
                });
                if (members.get(position).getPhoto() != null && TextUtils.isEmpty(members.get(position).getPhoto()) == false) {
                    String[] allPhotos = members.get(position).getPhoto().split(",");
                    Log.d(TAG, members.get(position).getPhoto());
                    Picasso.with(DashboardActivity.this).load("http://52.91.166.193/Webservices/ChildTracker/" + allPhotos[0]).placeholder(R.mipmap.ic_loading).transform(new CircleTransform()).into(userImage);
                } else {
                    Picasso.with(DashboardActivity.this).load(R.mipmap.ic_user).placeholder(R.mipmap.ic_loading).transform(new CircleTransform()).into(userImage);
                }

                userName.setText(members.get(position).getChildName());

                mLlUserProfile.addView(childLayout);

            }
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        ButterKnife.bind(DashboardActivity.this);
        mCustomSharedPref = new CustomSharedPreferance(DashboardActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.dashboard_activity_title)));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()

                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(DashboardActivity.this);
        setUpGClient();
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {

            //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
            MarkerOptions markerOptions = new MarkerOptions().position(new
                    LatLng(location.getLatitude(), location.getLongitude())).title("My Location");

            //Adding the created the marker on the map
            mMap.addMarker(markerOptions);
            LatLng mLocationDestination = new LatLng((location.getLatitude()), (location.getLongitude()));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mLocationDestination).zoom(11f).tilt(70).build();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.moveCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            //Or Do whatever you want with your location
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(DashboardActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(DashboardActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(DashboardActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(DashboardActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(DashboardActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }
}

