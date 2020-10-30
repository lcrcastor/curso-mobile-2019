package org.joda.time.tz;

public class ZoneInfoLogger {
    static ThreadLocal<Boolean> a = new ThreadLocal<Boolean>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public static boolean verbose() {
        return ((Boolean) a.get()).booleanValue();
    }

    public static void set(boolean z) {
        a.set(Boolean.valueOf(z));
    }
}
