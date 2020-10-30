package com.appsflyer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

final class m {

    static final class a {
        private final String a;
        private final boolean b;

        a(String str, boolean z) {
            this.a = str;
            this.b = z;
        }

        public final String a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public final boolean b() {
            return this.b;
        }
    }

    static final class d implements IInterface {
        private IBinder a;

        d(IBinder iBinder) {
            this.a = iBinder;
        }

        public final IBinder asBinder() {
            return this.a;
        }

        public final String a() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.a.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readString();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        /* access modifiers changed from: 0000 */
        public final boolean b() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                boolean z = true;
                obtain.writeInt(1);
                this.a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z = false;
                }
                return z;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    static final class e implements ServiceConnection {
        private boolean a;
        private final LinkedBlockingQueue<IBinder> b;

        public final void onServiceDisconnected(ComponentName componentName) {
        }

        private e() {
            this.a = false;
            this.b = new LinkedBlockingQueue<>(1);
        }

        /* synthetic */ e(byte b2) {
            this();
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        public final IBinder a() {
            if (this.a) {
                throw new IllegalStateException();
            }
            this.a = true;
            return (IBinder) this.b.take();
        }
    }

    m() {
    }

    static a a(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            e eVar = new e(0);
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (context.bindService(intent, eVar, 1)) {
                    d dVar = new d(eVar.a());
                    a aVar = new a(dVar.a(), dVar.b());
                    if (context != null) {
                        context.unbindService(eVar);
                    }
                    return aVar;
                }
                if (context != null) {
                    context.unbindService(eVar);
                }
                throw new IOException("Google Play connection failed");
            } catch (Exception e2) {
                throw e2;
            } catch (Throwable th) {
                if (context != null) {
                    context.unbindService(eVar);
                }
                throw th;
            }
        } catch (Exception e3) {
            throw e3;
        }
    }
}
