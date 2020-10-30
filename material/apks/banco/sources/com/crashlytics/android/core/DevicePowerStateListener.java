package com.crashlytics.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.atomic.AtomicBoolean;

class DevicePowerStateListener {
    private static final IntentFilter a = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static final IntentFilter b = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    private static final IntentFilter c = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    private final AtomicBoolean d;
    private final Context e;
    private final BroadcastReceiver f;
    private final BroadcastReceiver g;
    /* access modifiers changed from: private */
    public boolean h;

    public DevicePowerStateListener(Context context) {
        this.e = context;
        Intent registerReceiver = context.registerReceiver(null, a);
        int i = -1;
        if (registerReceiver != null) {
            i = registerReceiver.getIntExtra("status", -1);
        }
        this.h = i == 2 || i == 5;
        this.g = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                DevicePowerStateListener.this.h = true;
            }
        };
        this.f = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                DevicePowerStateListener.this.h = false;
            }
        };
        context.registerReceiver(this.g, b);
        context.registerReceiver(this.f, c);
        this.d = new AtomicBoolean(true);
    }

    public boolean a() {
        return this.h;
    }

    public void b() {
        if (this.d.getAndSet(false)) {
            this.e.unregisterReceiver(this.g);
            this.e.unregisterReceiver(this.f);
        }
    }
}
