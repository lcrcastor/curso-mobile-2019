package com.squareup.okhttp.internal.spdy;

import java.util.List;
import okio.BufferedSource;

public interface PushObserver {
    public static final PushObserver CANCEL = new PushObserver() {
        public boolean onHeaders(int i, List<Header> list, boolean z) {
            return true;
        }

        public boolean onRequest(int i, List<Header> list) {
            return true;
        }

        public void onReset(int i, ErrorCode errorCode) {
        }

        public boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z) {
            bufferedSource.skip((long) i2);
            return true;
        }
    };

    boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z);

    boolean onHeaders(int i, List<Header> list, boolean z);

    boolean onRequest(int i, List<Header> list);

    void onReset(int i, ErrorCode errorCode);
}
