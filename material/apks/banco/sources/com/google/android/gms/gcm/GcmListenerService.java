package com.google.android.gms.gcm;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.util.Iterator;

public abstract class GcmListenerService extends Service {
    private final Object a = new Object();
    private int b;
    private int c = 0;

    private void a() {
        synchronized (this.a) {
            this.c--;
            if (this.c == 0) {
                a(this.b);
            }
        }
    }

    @TargetApi(11)
    private void a(final Intent intent) {
        if (VERSION.SDK_INT >= 11) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                public void run() {
                    GcmListenerService.this.b(intent);
                }
            });
        } else {
            new AsyncTask<Void, Void, Void>() {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Void doInBackground(Void... voidArr) {
                    GcmListenerService.this.b(intent);
                    return null;
                }
            }.execute(new Void[0]);
        }
    }

    static void a(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(Intent intent) {
        try {
            String action = intent.getAction();
            char c2 = 65535;
            if (action.hashCode() == 366519424) {
                if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
                    c2 = 0;
                }
            }
            if (c2 != 0) {
                String str = "GcmListenerService";
                String str2 = "Unknown intent action: ";
                String valueOf = String.valueOf(intent.getAction());
                Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            } else {
                c(intent);
            }
            a();
        } finally {
            GcmReceiver.completeWakefulIntent(intent);
        }
    }

    private void c(Intent intent) {
        String stringExtra = intent.getStringExtra("message_type");
        if (stringExtra == null) {
            stringExtra = GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE;
        }
        char c2 = 65535;
        int hashCode = stringExtra.hashCode();
        if (hashCode != -2062414158) {
            if (hashCode != 102161) {
                if (hashCode != 814694033) {
                    if (hashCode == 814800675 && stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_EVENT)) {
                        c2 = 2;
                    }
                } else if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)) {
                    c2 = 3;
                }
            } else if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
                c2 = 0;
            }
        } else if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)) {
            c2 = 1;
        }
        switch (c2) {
            case 0:
                d(intent);
                return;
            case 1:
                onDeletedMessages();
                return;
            case 2:
                onMessageSent(intent.getStringExtra("google.message_id"));
                return;
            case 3:
                onSendError(e(intent), intent.getStringExtra("error"));
                return;
            default:
                String str = "GcmListenerService";
                String str2 = "Received message with unknown type: ";
                String valueOf = String.valueOf(stringExtra);
                Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                return;
        }
    }

    private void d(Intent intent) {
        Bundle extras = intent.getExtras();
        extras.remove("message_type");
        extras.remove("android.support.content.wakelockid");
        if (zza.a(extras)) {
            if (!zza.b((Context) this)) {
                zza.a((Context) this).c(extras);
                return;
            }
            zza.b(extras);
        }
        String string = extras.getString("from");
        extras.remove("from");
        a(extras);
        onMessageReceived(string, extras);
    }

    private String e(Intent intent) {
        String stringExtra = intent.getStringExtra("google.message_id");
        return stringExtra == null ? intent.getStringExtra("message_id") : stringExtra;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i) {
        return stopSelfResult(i);
    }

    public final IBinder onBind(Intent intent) {
        return null;
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(String str, Bundle bundle) {
    }

    public void onMessageSent(String str) {
    }

    public void onSendError(String str, String str2) {
    }

    public final int onStartCommand(Intent intent, int i, int i2) {
        synchronized (this.a) {
            this.b = i2;
            this.c++;
        }
        if (intent == null) {
            a();
            return 2;
        }
        a(intent);
        return 3;
    }
}
