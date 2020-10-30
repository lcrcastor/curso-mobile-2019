package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

class zzs implements Logger {
    private int a = 2;
    private boolean b;

    zzs() {
    }

    public void error(Exception exc) {
    }

    public void error(String str) {
    }

    public int getLogLevel() {
        return this.a;
    }

    public void info(String str) {
    }

    public void setLogLevel(int i) {
        this.a = i;
        if (!this.b) {
            String str = (String) zzy.cg.get();
            String str2 = (String) zzy.cg.get();
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 91);
            sb.append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            sb.append(str2);
            sb.append(" DEBUG");
            Log.i(str, sb.toString());
            this.b = true;
        }
    }

    public void verbose(String str) {
    }

    public void warn(String str) {
    }
}
