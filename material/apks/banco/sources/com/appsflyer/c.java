package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

final class c {
    private IntentFilter a = new IntentFilter("android.intent.action.BATTERY_CHANGED");

    /* renamed from: com.appsflyer.c$c reason: collision with other inner class name */
    static final class C0001c {
        static final c a = new c();
    }

    static final class e {
        private final float a;
        private final String b;

        e(float f, String str) {
            this.a = f;
            this.b = str;
        }

        /* access modifiers changed from: 0000 */
        public final float a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public final String b() {
            return this.b;
        }

        e() {
        }
    }

    c() {
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final e a(Context context) {
        String str;
        String str2 = null;
        float f = BitmapDescriptorFactory.HUE_RED;
        try {
            Intent registerReceiver = context.registerReceiver(null, this.a);
            if (registerReceiver != null) {
                if (2 == registerReceiver.getIntExtra("status", -1)) {
                    int intExtra = registerReceiver.getIntExtra("plugged", -1);
                    if (intExtra != 4) {
                        switch (intExtra) {
                            case 1:
                                str = "ac";
                                break;
                            case 2:
                                str = "usb";
                                break;
                            default:
                                str = "other";
                                break;
                        }
                    } else {
                        str = "wireless";
                    }
                } else {
                    str = "no";
                }
                str2 = str;
                int intExtra2 = registerReceiver.getIntExtra("level", -1);
                int intExtra3 = registerReceiver.getIntExtra("scale", -1);
                if (!(-1 == intExtra2 || -1 == intExtra3)) {
                    f = (((float) intExtra2) * 100.0f) / ((float) intExtra3);
                }
            }
        } catch (Throwable unused) {
        }
        return new e(f, str2);
    }
}
