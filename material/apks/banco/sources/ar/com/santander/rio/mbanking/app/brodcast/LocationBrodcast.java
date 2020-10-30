package ar.com.santander.rio.mbanking.app.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import java.util.List;

public class LocationBrodcast extends BroadcastReceiver {
    public LocationBrodcastListener mLocationBrodcastListener;

    public interface LocationBrodcastListener {
        void getLocationStatus(boolean z);
    }

    public void onReceive(Context context, Intent intent) {
        List<String> providers = ((LocationManager) context.getSystemService("location")).getProviders(true);
        boolean z = false;
        if (providers != null && providers.size() > 0) {
            for (String str : providers) {
                if ("gps".equals(str) || "network".equals(str)) {
                    z = true;
                }
            }
        }
        if (this.mLocationBrodcastListener != null) {
            this.mLocationBrodcastListener.getLocationStatus(z);
        }
    }

    public void setLocationBrodcastListener(LocationBrodcastListener locationBrodcastListener) {
        this.mLocationBrodcastListener = locationBrodcastListener;
    }
}
