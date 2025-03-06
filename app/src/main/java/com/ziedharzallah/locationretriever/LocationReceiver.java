package com.ziedharzallah.locationretriever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.room.RoomDatabase;

import com.google.android.gms.location.LocationResult;

public class LocationReceiver extends BroadcastReceiver {
    private static final String TAG = "LocationReceiver"; // Tag for debugging logs

    @Override
    public void onReceive(Context context, Intent intent) {
        if (LocationResult.hasResult(intent)) { // Check if the intent contains location results
            LocationResult locationResult = LocationResult.extractResult(intent); // Extract location results

            if (locationResult != null) { // Ensure locationResult is not null
                for (Location location : locationResult.getLocations()) { // Iterate through location list
                    double lat = location.getLatitude(); // Get latitude
                    double lng = location.getLongitude(); // Get longitude

                    // Display location in a Toast message
                    Toast.makeText(context, "Location: Lat " + lat + ", Lng " + lng, Toast.LENGTH_SHORT).show();
                    AppDatabase db = AppDatabase.getDatabase(context);
                    new Thread(() -> {
                        Position p = new Position(location.getLatitude(), location.getLongitude(), location.getAltitude());
                        db.userDao().insert(p);
                    }).start();
                    // Log location for debugging
                    Log.d(TAG, "Location update: Lat " + lat + ", Lng " + lng);
                }
            }
        }
    }
}
