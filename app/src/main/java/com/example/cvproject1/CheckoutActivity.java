package com.example.cvproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.Volley;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerAnchor;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.map.gestures.GesturesConfiguration;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CheckoutActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private TomtomMap tomtomMap;
    GPSTracker tracker;
    RequestQueue requestQueue;
    Location shopLocation;
    TextView locationAddress;
    TextView subtotalMoney;
    TextView phone;
    TextView clientName;
    TextView estTime;
    TextView barCodeTextView;
    String barcode;
    String[] days = new String[] { "SAT", "SUN", "MON", "TUE", "WED", "THU", "FRI" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        locationAddress = findViewById(R.id.locationAddress);
        requestQueue = Volley.newRequestQueue(this);
        barcode = getAlphaNumericString(10);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        subtotalMoney = findViewById(R.id.subtotalMoney);
        subtotalMoney.setText(getIntent().getDoubleExtra("totalMoney", 0) + "");
        clientName = findViewById(R.id.clientName);
        barCodeTextView = findViewById(R.id.barCode);
        barCodeTextView.setText(barcode);
        clientName.setText(UserHandler.getLoggedInUser().getUserName());
        phone = findViewById(R.id.phone);
        phone.setText(UserHandler.getLoggedInUser().getUserPhone());
        estTime = findViewById(R.id.estTime);
        shopLocation = new Location("");
        shopLocation.setLatitude(29.970072);
        shopLocation.setLongitude(31.206372);
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

            @Override
            public void afterPermissionsGranted() {
                updateMarker();
                tracker.stopUsingGPS();
            }
        };
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
        Location currentLocation = null;
        currentLocation = tracker.getLocation();
        if(currentLocation != null){
            updateMarker();
            updateTime(currentLocation, shopLocation);
            tracker.stopUsingGPS();
        }
        tomtomMap.getMarkerSettings().addOnMarkerDragListener(new TomtomMapCallback.OnMarkerDragListener() {
            @Override
            public void onStartDragging(@NonNull Marker marker) {

            }

            @Override
            public void onStopDragging(@NonNull Marker marker) {
                Location currentLocation = new Location("");
                currentLocation.setLongitude(marker.getPosition().getLongitude());
                currentLocation.setLatitude(marker.getPosition().getLatitude());
                updateAddress(marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
                updateTime(currentLocation, shopLocation);
            }

            @Override
            public void onDragging(@NonNull Marker marker) {

            }
        });
    }

    private void updateMarker() {
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
    }

    private void updateAddress(double latitude, double longitude){
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

    private void updateTime(Location location1, Location location2){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        System.out.println(location1.distanceTo(location2));
        int hours   = Integer.parseInt(formatter.format(date).substring(0, 2));
        int minutes = Integer.parseInt(formatter.format(date).substring(3, 5));
        int numberOfMinutesToTake = Integer.parseInt(((location1.distanceTo(location2) / 60) + "").split("\\.")[0]);
        minutes += numberOfMinutesToTake;
        String day = days[calendar.get(Calendar.DAY_OF_WEEK)];
        String AMorPM = "AM";
        if(minutes > 59){
            hours++;
            minutes %= 60;
        }if(hours > 23){
            hours %= 24;
            day = days[(calendar.get(Calendar.DAY_OF_WEEK) + 1) % 7];
        }
        if(hours > 12){
            hours %= 13;
            hours++;
            AMorPM = "PM";
        }
        estTime.setText(day + " " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + " " + AMorPM);
    }

    public void confirmBtn(View view) {
        FirebaseListener.addReset(new Reset(locationAddress.getText() + "", subtotalMoney.getText() + "", UserHandler.getLoggedInUser().getUserPhone(), barcode), UserHandler.getLoggedInUser().getUserPhone());
        Cart.clearCart();
        Cart.writeTOSharedPreference(getApplicationContext());
        finish();
    }

    private String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

}