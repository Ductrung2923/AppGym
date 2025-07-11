package com.example.gym.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gym.R;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class TrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private Button startButton;
    private Button backButton;

    private FusedLocationProviderClient fusedLocationClient;

    private TextView distanceTextView;
    private Button stopButton;

    private LocationCallback locationCallback;
    private Location lastLocation;
    private float totalDistance = 0f;

    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_tracking);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        BindingView();
        BindingAction();
    }

    private void BindingView() {
        startButton = findViewById(R.id.startButton);
        backButton = findViewById(R.id.BackButton);
        distanceTextView = findViewById(R.id.distanceTextView);
        stopButton = findViewById(R.id.stopButton);

    }

    private void BindingAction() {
        startButton.setOnClickListener(view -> {
            startButton.setVisibility(View.GONE);
            backButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);

            isTracking = true;
            startLocationUpdates();
        });

        backButton.setOnClickListener(this::backPage);

        stopButton.setOnClickListener(v -> {
            isTracking = false;
            stopLocationUpdates();
            stopButton.setVisibility(View.GONE);
            Toast.makeText(this, "Đã dừng theo dõi", Toast.LENGTH_SHORT).show();
        });

    }

    private void backPage(View view) {
        stopLocationUpdates();
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true);
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(3000); // update mỗi 3 giây
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    updateLocationOnMap(location);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void updateLocationOnMap(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Đang di chuyển"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }

        // Tính khoảng cách giữa các điểm GPS
        if (lastLocation != null && isTracking) {
            float distance = lastLocation.distanceTo(location);
            totalDistance += distance;

            // Hiển thị khoảng cách đã đi (nếu muốn)
            String formattedDistance = String.format("Quãng đường: %.2f km", totalDistance / 1000);
            distanceTextView.setText(formattedDistance);

        }

        lastLocation = location;
    }

    // Handle permission result
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap);
            } else {
                Toast.makeText(this, "Không có quyền truy cập vị trí", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
