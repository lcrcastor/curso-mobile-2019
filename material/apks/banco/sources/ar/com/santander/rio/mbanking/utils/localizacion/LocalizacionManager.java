package ar.com.santander.rio.mbanking.utils.localizacion;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.text.DateFormat;
import java.util.Date;

public abstract class LocalizacionManager implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    protected static final String TAG = "location-updates-sample";
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private OnLocationChangeListener a;
    private Activity b;
    protected Location mCurrentLocation;
    protected GoogleApiClient mGoogleApiClient;
    protected String mLastUpdateTime = "";
    protected LocationRequest mLocationRequest;
    protected Boolean mRequestingLocationUpdates = Boolean.valueOf(false);

    public interface OnLocationChangeListener {
        void onLocationListener(Location location);
    }

    public LocalizacionManager(Activity activity, OnLocationChangeListener onLocationChangeListener) {
        this.b = activity;
        this.a = onLocationChangeListener;
        buildGoogleApiClient();
    }

    public LocalizacionManager(Activity activity) {
        this.b = activity;
        buildGoogleApiClient();
    }

    /* access modifiers changed from: protected */
    public synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        this.mGoogleApiClient = new Builder(this.b).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        createLocationRequest();
    }

    /* access modifiers changed from: protected */
    public void createLocationRequest() {
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        this.mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        this.mLocationRequest.setPriority(100);
    }

    /* access modifiers changed from: protected */
    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, (LocationListener) this);
    }

    /* access modifiers changed from: protected */
    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, (LocationListener) this);
    }

    public void onStart() {
        if (!this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.connect();
        }
    }

    public void onResume() {
        if (this.mGoogleApiClient.isConnected() && this.mRequestingLocationUpdates.booleanValue()) {
            startLocationUpdates();
        }
    }

    public void onPause() {
        if (this.mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    public void onStop() {
        if (this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }

    public Location getCurrentLocation() {
        if (this.mCurrentLocation == null) {
            this.mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
        }
        return this.mCurrentLocation;
    }

    public void onLocationChanged(Location location) {
        this.mCurrentLocation = location;
        this.mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (this.a != null) {
            this.a.onLocationListener(this.mCurrentLocation);
        }
    }

    public void onConnectionSuspended(int i) {
        this.mGoogleApiClient.connect();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Connection failed: ConnectionResult.getErrorCode() = ");
        sb.append(connectionResult.getErrorCode());
        Log.i(str, sb.toString());
    }
}
