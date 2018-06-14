package childtracker.roti.com.childtracker.activities;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.retrofit.PathJSONParser;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.MapHttpConnection;


public class ShowRouteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private LatLng sourceLocation;
    private LatLng destincationLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(ShowRouteActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.showroute_activity_title)));
        setContentView(R.layout.show_route_activity);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    private String getMapsApiDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        sourceLocation = new LatLng(Double.parseDouble(getIntent().getStringExtra(Constants.EXTRA_LAT)), Double.parseDouble(getIntent().getStringExtra(Constants.EXTRA_LNG)));
        destincationLocation = new LatLng(Double.parseDouble(getIntent().getStringExtra(Constants.EXTRA_LAT2)), Double.parseDouble(getIntent().getStringExtra(Constants.EXTRA_LNG2)));
        new PlotLocation().execute(getMapsApiDirectionsUrl(sourceLocation, destincationLocation));
    }

    private class PlotLocation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                MapHttpConnection http = new MapHttpConnection();
                data = http.readUr(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParseLocationDirections().execute(result);
        }

    }

    private class ParseLocationDirections extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;
            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(15);

                polyLineOptions.color(Color.BLACK);
            }
            if (polyLineOptions != null) {
                mMap.addPolyline(polyLineOptions);
            }

            getWindow().getDecorView().findViewById(android.R.id.content).setAlpha((float) 1.0);
            createMarker(sourceLocation, "Current location", BitmapDescriptorFactory.HUE_RED).showInfoWindow();
            createMarker(destincationLocation, "Destination", BitmapDescriptorFactory.HUE_GREEN).showInfoWindow();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(sourceLocation).zoom(8f).tilt(70).build();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.moveCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }

    }

    private Marker createMarker(LatLng locationPoint, String title, float color) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(locationPoint)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(color))
                .title(title));
        return marker;
    }


}
