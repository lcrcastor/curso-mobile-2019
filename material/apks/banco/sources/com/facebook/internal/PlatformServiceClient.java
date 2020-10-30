package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public abstract class PlatformServiceClient implements ServiceConnection {
    private final Context a;
    private final Handler b;
    private CompletedListener c;
    private boolean d;
    private Messenger e;
    private int f;
    private int g;
    private final String h;
    private final int i;

    public interface CompletedListener {
        void completed(Bundle bundle);
    }

    /* access modifiers changed from: protected */
    public abstract void populateRequestBundle(Bundle bundle);

    public PlatformServiceClient(Context context, int i2, int i3, int i4, String str) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        this.a = context;
        this.f = i2;
        this.g = i3;
        this.h = str;
        this.i = i4;
        this.b = new Handler() {
            public void handleMessage(Message message) {
                PlatformServiceClient.this.handleMessage(message);
            }
        };
    }

    public void setCompletedListener(CompletedListener completedListener) {
        this.c = completedListener;
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.a;
    }

    public boolean start() {
        if (this.d || NativeProtocol.getLatestAvailableProtocolVersionForService(this.a, this.i) == -1) {
            return false;
        }
        Intent createPlatformServiceIntent = NativeProtocol.createPlatformServiceIntent(this.a);
        if (createPlatformServiceIntent == null) {
            return false;
        }
        this.d = true;
        this.a.bindService(createPlatformServiceIntent, this, 1);
        return true;
    }

    public void cancel() {
        this.d = false;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.e = new Messenger(iBinder);
        a();
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.e = null;
        try {
            this.a.unbindService(this);
        } catch (IllegalArgumentException unused) {
        }
        a(null);
    }

    private void a() {
        Bundle bundle = new Bundle();
        bundle.putString(NativeProtocol.EXTRA_APPLICATION_ID, this.h);
        populateRequestBundle(bundle);
        Message obtain = Message.obtain(null, this.f);
        obtain.arg1 = this.i;
        obtain.setData(bundle);
        obtain.replyTo = new Messenger(this.b);
        try {
            this.e.send(obtain);
        } catch (RemoteException unused) {
            a(null);
        }
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        if (message.what == this.g) {
            Bundle data = message.getData();
            if (data.getString(NativeProtocol.STATUS_ERROR_TYPE) != null) {
                a(null);
            } else {
                a(data);
            }
            this.a.unbindService(this);
        }
    }

    private void a(Bundle bundle) {
        if (this.d) {
            this.d = false;
            CompletedListener completedListener = this.c;
            if (completedListener != null) {
                completedListener.completed(bundle);
            }
        }
    }
}
