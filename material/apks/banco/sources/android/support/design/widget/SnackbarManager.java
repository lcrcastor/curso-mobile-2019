package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import java.lang.ref.WeakReference;

class SnackbarManager {
    private static SnackbarManager a;
    private final Object b = new Object();
    private final Handler c = new Handler(Looper.getMainLooper(), new android.os.Handler.Callback() {
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            SnackbarManager.this.a((SnackbarRecord) message.obj);
            return true;
        }
    });
    private SnackbarRecord d;
    private SnackbarRecord e;

    interface Callback {
        void a();

        void a(int i);
    }

    static class SnackbarRecord {
        final WeakReference<Callback> a;
        int b;
        boolean c;

        SnackbarRecord(int i, Callback callback) {
            this.a = new WeakReference<>(callback);
            this.b = i;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Callback callback) {
            return callback != null && this.a.get() == callback;
        }
    }

    static SnackbarManager a() {
        if (a == null) {
            a = new SnackbarManager();
        }
        return a;
    }

    private SnackbarManager() {
    }

    public void a(int i, Callback callback) {
        synchronized (this.b) {
            if (g(callback)) {
                this.d.b = i;
                this.c.removeCallbacksAndMessages(this.d);
                b(this.d);
                return;
            }
            if (h(callback)) {
                this.e.b = i;
            } else {
                this.e = new SnackbarRecord(i, callback);
            }
            if (this.d == null || !a(this.d, 4)) {
                this.d = null;
                b();
            }
        }
    }

    public void a(Callback callback, int i) {
        synchronized (this.b) {
            if (g(callback)) {
                a(this.d, i);
            } else if (h(callback)) {
                a(this.e, i);
            }
        }
    }

    public void a(Callback callback) {
        synchronized (this.b) {
            if (g(callback)) {
                this.d = null;
                if (this.e != null) {
                    b();
                }
            }
        }
    }

    public void b(Callback callback) {
        synchronized (this.b) {
            if (g(callback)) {
                b(this.d);
            }
        }
    }

    public void c(Callback callback) {
        synchronized (this.b) {
            if (g(callback) && !this.d.c) {
                this.d.c = true;
                this.c.removeCallbacksAndMessages(this.d);
            }
        }
    }

    public void d(Callback callback) {
        synchronized (this.b) {
            if (g(callback) && this.d.c) {
                this.d.c = false;
                b(this.d);
            }
        }
    }

    public boolean e(Callback callback) {
        boolean g;
        synchronized (this.b) {
            g = g(callback);
        }
        return g;
    }

    public boolean f(Callback callback) {
        boolean z;
        synchronized (this.b) {
            if (!g(callback)) {
                if (!h(callback)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private void b() {
        if (this.e != null) {
            this.d = this.e;
            this.e = null;
            Callback callback = (Callback) this.d.a.get();
            if (callback != null) {
                callback.a();
            } else {
                this.d = null;
            }
        }
    }

    private boolean a(SnackbarRecord snackbarRecord, int i) {
        Callback callback = (Callback) snackbarRecord.a.get();
        if (callback == null) {
            return false;
        }
        this.c.removeCallbacksAndMessages(snackbarRecord);
        callback.a(i);
        return true;
    }

    private boolean g(Callback callback) {
        return this.d != null && this.d.a(callback);
    }

    private boolean h(Callback callback) {
        return this.e != null && this.e.a(callback);
    }

    private void b(SnackbarRecord snackbarRecord) {
        if (snackbarRecord.b != -2) {
            int i = 2750;
            if (snackbarRecord.b > 0) {
                i = snackbarRecord.b;
            } else if (snackbarRecord.b == -1) {
                i = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
            }
            this.c.removeCallbacksAndMessages(snackbarRecord);
            this.c.sendMessageDelayed(Message.obtain(this.c, 0, snackbarRecord), (long) i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(SnackbarRecord snackbarRecord) {
        synchronized (this.b) {
            if (this.d == snackbarRecord || this.e == snackbarRecord) {
                a(snackbarRecord, 2);
            }
        }
    }
}
