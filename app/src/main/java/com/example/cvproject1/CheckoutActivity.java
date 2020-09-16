package com.example.cvproject1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.location.LocationUpdateListener;
import com.tomtom.online.sdk.location.Locations;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MapProperties;
import com.tomtom.online.sdk.map.MapView;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerAnchor;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.map.gestures.GesturesConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.tomtom.online.sdk.map.MapConstants.DEFAULT_ZOOM_LEVEL;

public class CheckoutActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private TomtomMap tomtomMap;
    GPSTracker tracker;
    TextView locationAddress;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        locationAddress = findViewById(R.id.locationAddress);
        requestQueue = Volley.newRequestQueue(this);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        tracker = new GPSTracker(this) {
            @Override
            public void permissionsDenied() {
                finish();
            }

            @Override
            public void noInternetConnection() {
                new AlertDialog.Builder(this)
                        .setTitle("Alert Dialog")
                        .setMessage("Open Internet")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
            }

            @Override
            public void GPSIsOff() {
                new AlertDialog.Builder(this)
                        .setTitle("Alert Dialog")
                        .setMessage("Open GPS")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                showSettingsAlert();
                                finish();
                            }
                        });
            }
        };
        tracker.stopUsingGPS();
        mapFragment.getAsyncMap(this);
    }

    @Override
    public void onMapReady(@NonNull TomtomMap map) {
        tomtomMap = map;
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .rotationEnabled(true)
                        .panningEnabled(true)
                        .zoomEnabled(true)
                        .tiltEnabled(true)
                        .build()
        );
        LatLng currentLocation = new LatLng(tracker.getLatitude(), tracker.getLongitude());
        updateAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
        tomtomMap.centerOn(new CameraPosition(currentLocation, 17, 0, MapConstants.ORIENTATION_NORTH, 1500));
        MarkerBuilder markerBuilder = new MarkerBuilder(currentLocation)
                .icon(Icon.Factory.fromResources(getApplicationContext(), R.drawable.location_icon))
                .markerBalloon(new SimpleMarkerBalloon("Drag to your Location"))
                .tag("more information in tag").iconAnchor(MarkerAnchor.Bottom)
                .decal(true)
                .draggable(true); //By default is false
        tomtomMap.addMarker(markerBuilder);
        tomtomMap.getMarkerSettings().addOnMarkerDragListener(new TomtomMapCallback.OnMarkerDragListener() {
            @Override
            public void onStartDragging(@NonNull Marker marker) {

            }

            @Override
            public void onStopDragging(@NonNull Marker marker) {
                updateAddress(marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
            }

            @Override
            public void onDragging(@NonNull Marker marker) {

            }
        });
    }
    private void updateAddress(double latitude, double longitude){
        requestQueue.cancelAll("All Requests Canceled");
        String url ="https://api.tomtom.com/search/2/reverseGeocode/" +
                latitude + "%2C" + longitude +
                ".json?returnSpeedLimit=true&language=ar&key=OAOdbDtrSPjAiwG0JSFyzoBRWNFjXLiB";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    locationAddress.setText(response.getJSONArray("addresses").getJSONObject(0).getJSONObject("address").getString("freeformAddress"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}