package fr.quentinklein.slt;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.Log;

public abstract class LocationTracker implements LocationListener {
    private static final String TAG = "LocationTracker";
    private static Location sLocation;
    private Context mContext;
    private boolean mIsListening;
    private boolean mIsLocationFound;
    private LocationManager mLocationManagerService;
    private TrackerSettings mTrackerSettings;

    class C14341 implements Runnable {
        C14341() {
        }

        public void run() {
            if (!LocationTracker.this.mIsLocationFound && LocationTracker.this.mIsListening) {
                Log.i(LocationTracker.TAG, "No location found in the meantime");
                LocationTracker.this.stopListening();
                LocationTracker.this.onTimeout();
            }
        }
    }

    public abstract void onLocationFound(@NonNull Location location);

    public abstract void onTimeout();

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public LocationTracker(@NonNull Context context) {
        this(context, TrackerSettings.DEFAULT);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public LocationTracker(@NonNull Context context, @NonNull TrackerSettings trackerSettings) {
        this.mIsListening = false;
        this.mIsLocationFound = false;
        this.mContext = context;
        this.mTrackerSettings = trackerSettings;
        this.mLocationManagerService = (LocationManager) context.getSystemService("location");
        if (sLocation == null && trackerSettings.shouldUseGPS()) {
            sLocation = this.mLocationManagerService.getLastKnownLocation("gps");
        }
        if (sLocation == null && trackerSettings.shouldUseNetwork()) {
            sLocation = this.mLocationManagerService.getLastKnownLocation("network");
        }
        if (sLocation == null && trackerSettings.shouldUsePassive()) {
            sLocation = this.mLocationManagerService.getLastKnownLocation("passive");
        }
    }

    @Deprecated
    public final void startListen() {
        startListening();
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final void startListening() {
        if (this.mIsListening) {
            Log.i(TAG, "Relax, LocationTracked is already listening for location updates");
            return;
        }
        Log.i(TAG, "LocationTracked is now listening for location updates");
        if (this.mTrackerSettings.shouldUseGPS()) {
            if (LocationUtils.isGpsProviderEnabled(this.mContext)) {
                this.mLocationManagerService.requestLocationUpdates("gps", this.mTrackerSettings.getTimeBetweenUpdates(), this.mTrackerSettings.getMetersBetweenUpdates(), this);
            } else {
                onProviderError(new ProviderError("gps", "Provider is not enabled"));
            }
        }
        if (this.mTrackerSettings.shouldUseNetwork()) {
            if (LocationUtils.isNetworkProviderEnabled(this.mContext)) {
                this.mLocationManagerService.requestLocationUpdates("network", this.mTrackerSettings.getTimeBetweenUpdates(), this.mTrackerSettings.getMetersBetweenUpdates(), this);
            } else {
                onProviderError(new ProviderError("network", "Provider is not enabled"));
            }
        }
        if (this.mTrackerSettings.shouldUsePassive()) {
            if (LocationUtils.isPassiveProviderEnabled(this.mContext)) {
                this.mLocationManagerService.requestLocationUpdates("passive", this.mTrackerSettings.getTimeBetweenUpdates(), this.mTrackerSettings.getMetersBetweenUpdates(), this);
            } else {
                onProviderError(new ProviderError("passive", "Provider is not enabled"));
            }
        }
        this.mIsListening = true;
        if (this.mTrackerSettings.getTimeout() != -1) {
            new Handler().postDelayed(new C14341(), (long) this.mTrackerSettings.getTimeout());
        }
    }

    @Deprecated
    public final void stopListen() {
        stopListening();
    }

    public final void stopListening() {
        if (this.mIsListening) {
            Log.i(TAG, "LocationTracked has stopped listening for location updates");
            this.mLocationManagerService.removeUpdates(this);
            this.mIsListening = false;
            return;
        }
        Log.i(TAG, "LocationTracked wasn't listening for location updates anyway");
    }

    public final void quickFix() {
        if (sLocation != null) {
            onLocationChanged(sLocation);
        }
    }

    public final boolean isListening() {
        return this.mIsListening;
    }

    public final void onLocationChanged(@NonNull Location location) {
        Log.i(TAG, "Location has changed, new location is " + location);
        sLocation = new Location(location);
        this.mIsLocationFound = true;
        onLocationFound(location);
    }

    public void onProviderDisabled(@NonNull String provider) {
        Log.i(TAG, "Provider (" + provider + ") has been disabled");
    }

    public void onProviderEnabled(@NonNull String provider) {
        Log.i(TAG, "Provider (" + provider + ") has been enabled");
    }

    public void onStatusChanged(@NonNull String provider, int status, Bundle extras) {
        Log.i(TAG, "Provider (" + provider + ") status has changed, new status is " + status);
    }

    public void onProviderError(@NonNull ProviderError providerError) {
        Log.w(TAG, "Provider (" + providerError.getProvider() + ")", providerError);
    }
}
