package cz.msebera.android.httpclient.impl.client;

class SystemClock implements Clock {
    SystemClock() {
    }

    public long a() {
        return System.currentTimeMillis();
    }
}
