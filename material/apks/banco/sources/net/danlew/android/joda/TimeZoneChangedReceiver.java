package net.danlew.android.joda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.TimeZone;
import org.joda.time.DateTimeZone;

public class TimeZoneChangedReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("time-zone");
        try {
            DateTimeZone.setDefault(DateTimeZone.forTimeZone(TimeZone.getDefault()));
            StringBuilder sb = new StringBuilder();
            sb.append("TIMEZONE_CHANGED received, changed default timezone to \"");
            sb.append(stringExtra);
            sb.append("\"");
            Log.d("joda-time-android", sb.toString());
        } catch (IllegalArgumentException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Could not recognize timezone id \"");
            sb2.append(stringExtra);
            sb2.append("\"");
            Log.e("joda-time-android", sb2.toString(), e);
        }
    }
}
