package com.ziedharzallah.locationretriever;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 102;
    private TextView locationTextView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTextView = findViewById(R.id.locationTextView);
        Button locationButton = findViewById(R.id.locationButton);
        Button startUpdatesButton = findViewById(R.id.startUpdatesButton);
        Button stopUpdatesButton = findViewById(R.id.stopUpdatesButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationButton.setOnClickListener(view -> checkLocationPermission());
        startUpdatesButton.setOnClickListener(view -> startLocationUpdates());
        stopUpdatesButton.setOnClickListener(view -> stopLocationUpdates());

    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            getDeviceLocation();
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(5000); // Update every 5 seconds

            Intent intent = new Intent(this, LocationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            fusedLocationClient.requestLocationUpdates(locationRequest, pendingIntent);

            Toast.makeText(this, "Mises à jour de position démarrées", Toast.LENGTH_SHORT).show();

            // Show the "Stop Updates" button
            Button stopUpdatesButton = findViewById(R.id.stopUpdatesButton);
            stopUpdatesButton.setVisibility(View.VISIBLE);
            stopUpdatesButton.setOnClickListener(view -> stopLocationUpdates());
        }
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null) {
            Intent intent = new Intent(this, LocationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

            fusedLocationClient.removeLocationUpdates(pendingIntent);

            Toast.makeText(this, "Mises à jour de position arrêtées", Toast.LENGTH_SHORT).show();

            // Hide the "Stop Updates" button
            Button stopUpdatesButton = findViewById(R.id.stopUpdatesButton);
            stopUpdatesButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation();
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        locationTextView.setText("Latitude : " + location.getLatitude() + "\n" + "Longitude : " + location.getLongitude() + "\n" + "Altitude : " + location.getAltitude() + "\n" + "Provider : " + location.getProvider() + "\n" + "Accuracy : " + location.getAccuracy());
                    } else {
                        locationTextView.setText("Location non trouvée !");
                    }
                }
            });
        } else {
            Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show();
        }
    }
}
