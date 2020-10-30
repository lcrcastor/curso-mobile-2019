package com.zurich.battery;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.zurich.lcd_test.R;
import com.zurich.lockview.utils.BatteryListener;
import com.zurich.lockview.utils.BroadcastReceiverManager;

public class BatteryActivity extends AppCompatActivity implements BatteryListener {
    private BroadcastReceiverManager n;

    @Deprecated
    public String batteryOperations() {
        return "";
    }

    public void onBatteryStatus(String str) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.menu_main);
        this.n = new BroadcastReceiverManager(this, this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        new IntentFilter().addAction("android.intent.action.BATTERY_CHANGED");
        this.n.registerReceiver();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.n.unregisterReceiver();
    }
}
