package com.zurich.lockview.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.TestBatteryActivity;
import com.facebook.internal.AnalyticsEvents;
import com.google.gson.Gson;
import com.zurich.battery.BatteryModel;

public class BroadcastReceiverManager {
    /* access modifiers changed from: private */
    public BatteryListener a;
    private Context b;
    private BroadcastReceiver c;

    /* access modifiers changed from: private */
    public String a(int i) {
        String str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        switch (i) {
            case 2:
                return TestBatteryActivity.GOOD_CONDITION_VALUE;
            case 3:
                return "Over Heat";
            case 4:
                return "Dead";
            case 5:
                return "Over Voltage";
            case 6:
                return "Failure";
            default:
                return str;
        }
    }

    /* access modifiers changed from: private */
    public String b(int i) {
        if (i == 4) {
            return "Plugged Wireless";
        }
        switch (i) {
            case 1:
                return "Plugged AC";
            case 2:
                return "Plugged Usb";
            default:
                return "None";
        }
    }

    /* access modifiers changed from: private */
    public String c(int i) {
        String str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        switch (i) {
            case 2:
                return "Charging";
            case 3:
                return "Discharging";
            case 4:
                return "Not Charging";
            case 5:
                return "Full";
            default:
                return str;
        }
    }

    public BroadcastReceiverManager(BatteryListener batteryListener, Context context) {
        this.a = batteryListener;
        this.b = context;
        a();
    }

    private void a() {
        this.c = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int intExtra = intent.getIntExtra("level", 0);
                int intExtra2 = intent.getIntExtra("status", 0);
                int intExtra3 = intent.getIntExtra(TestBatteryActivity.HEALTH_KEY, 0);
                int intExtra4 = intent.getIntExtra("plugged", 0);
                int intExtra5 = intent.getIntExtra("voltage", 0);
                int intExtra6 = intent.getIntExtra("temperature", 0);
                boolean booleanExtra = intent.getBooleanExtra("present", false);
                String a2 = BroadcastReceiverManager.this.a(intExtra3);
                String b = BroadcastReceiverManager.this.c(intExtra2);
                String c = BroadcastReceiverManager.this.b(intExtra4);
                Gson gson = new Gson();
                BatteryListener a3 = BroadcastReceiverManager.this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(intExtra);
                sb.append(Constants.SYMBOL_PERCENTAGE);
                BatteryModel batteryModel = new BatteryModel(sb.toString(), c, b, a2, intExtra6, intExtra5, booleanExtra);
                a3.onBatteryStatus(gson.toJson((Object) batteryModel));
            }
        };
        registerReceiver();
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        this.b.registerReceiver(this.c, intentFilter);
    }

    public void unregisterReceiver() {
        this.b.unregisterReceiver(this.c);
    }
}
