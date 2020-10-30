package com.appsflyer;

final class t implements a {
    private a a = this;

    interface a {
        Class<?> b(String str);
    }

    enum c {
        UNITY("android_unity", "com.unity3d.player.UnityPlayer"),
        REACT_NATIVE("android_reactNative", "com.facebook.react.ReactApplication"),
        CORDOVA("android_cordova", "org.apache.cordova.CordovaActivity"),
        SEGMENT("android_segment", "com.segment.analytics.integrations.Integration"),
        COCOS2DX("android_cocos2dx", "org.cocos2dx.lib.Cocos2dxActivity"),
        DEFAULT("android_native", "android_native");
        
        /* access modifiers changed from: private */
        public String g;
        /* access modifiers changed from: private */
        public String h;

        private c(String str, String str2) {
            this.g = str;
            this.h = str2;
        }
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        c[] values;
        for (c cVar : c.values()) {
            if (a(cVar.h)) {
                return cVar.g;
            }
        }
        return c.DEFAULT.g;
    }

    t() {
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(String str) {
        try {
            this.a.b(str);
            StringBuilder sb = new StringBuilder("Class: ");
            sb.append(str);
            sb.append(" is found.");
            AFLogger.afRDLog(sb.toString());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog(th.getMessage(), th);
            return false;
        }
    }

    public final Class<?> b(String str) {
        return Class.forName(str);
    }
}
