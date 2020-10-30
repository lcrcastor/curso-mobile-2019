package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.Session.StatusCallback;
import com.facebook.internal.LikeActionController;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PendingCallStore;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Callback;
import com.facebook.widget.FacebookDialog.PendingCall;
import java.util.UUID;

public class UiLifecycleHelper {
    private final Activity a;
    /* access modifiers changed from: private */
    public final StatusCallback b;
    private final BroadcastReceiver c;
    private final LocalBroadcastManager d;
    private UUID e;
    private PendingCallStore f;
    private AppEventsLogger g;

    class ActiveSessionBroadcastReceiver extends BroadcastReceiver {
        private ActiveSessionBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Session.ACTION_ACTIVE_SESSION_SET.equals(intent.getAction())) {
                Session activeSession = Session.getActiveSession();
                if (activeSession != null && UiLifecycleHelper.this.b != null) {
                    activeSession.addCallback(UiLifecycleHelper.this.b);
                }
            } else if (Session.ACTION_ACTIVE_SESSION_UNSET.equals(intent.getAction())) {
                Session activeSession2 = Session.getActiveSession();
                if (activeSession2 != null && UiLifecycleHelper.this.b != null) {
                    activeSession2.removeCallback(UiLifecycleHelper.this.b);
                }
            }
        }
    }

    public void onDestroy() {
    }

    public UiLifecycleHelper(Activity activity, StatusCallback statusCallback) {
        if (activity == null) {
            throw new IllegalArgumentException("activity cannot be null");
        }
        this.a = activity;
        this.b = statusCallback;
        this.c = new ActiveSessionBroadcastReceiver();
        this.d = LocalBroadcastManager.getInstance(activity);
        this.f = PendingCallStore.getInstance();
        Settings.sdkInitialize(activity);
    }

    public void onCreate(Bundle bundle) {
        Session activeSession = Session.getActiveSession();
        if (activeSession == null) {
            if (bundle != null) {
                activeSession = Session.restoreSession(this.a, null, this.b, bundle);
            }
            if (activeSession == null) {
                activeSession = new Session(this.a);
            }
            Session.setActiveSession(activeSession);
        }
        if (bundle != null) {
            String string = bundle.getString("com.facebook.UiLifecycleHelper.pendingFacebookDialogCallKey");
            if (string != null) {
                this.e = UUID.fromString(string);
            }
            this.f.restoreFromSavedInstanceState(bundle);
        }
    }

    public void onResume() {
        Session activeSession = Session.getActiveSession();
        if (activeSession != null) {
            if (this.b != null) {
                activeSession.addCallback(this.b);
            }
            if (SessionState.CREATED_TOKEN_LOADED.equals(activeSession.getState())) {
                activeSession.openForRead(null);
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_SET);
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_UNSET);
        this.d.registerReceiver(this.c, intentFilter);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        onActivityResult(i, i2, intent, null);
    }

    public void onActivityResult(int i, int i2, Intent intent, Callback callback) {
        Session activeSession = Session.getActiveSession();
        if (activeSession != null) {
            activeSession.onActivityResult(this.a, i, i2, intent);
        }
        if (!LikeActionController.handleOnActivityResult(this.a, i, i2, intent)) {
            a(i, i2, intent, callback);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        Session.saveSession(Session.getActiveSession(), bundle);
        if (this.e != null) {
            bundle.putString("com.facebook.UiLifecycleHelper.pendingFacebookDialogCallKey", this.e.toString());
        }
        this.f.saveInstanceState(bundle);
    }

    public void onPause() {
        this.d.unregisterReceiver(this.c);
        if (this.b != null) {
            Session activeSession = Session.getActiveSession();
            if (activeSession != null) {
                activeSession.removeCallback(this.b);
            }
        }
    }

    public void onStop() {
        AppEventsLogger.onContextStop();
    }

    public void trackPendingDialogCall(PendingCall pendingCall) {
        if (this.e != null) {
            Log.i("Facebook", "Tracking new app call while one is still pending; canceling pending call.");
            a((Callback) null);
        }
        if (pendingCall != null) {
            this.e = pendingCall.getCallId();
            this.f.trackPendingCall(pendingCall);
        }
    }

    public AppEventsLogger getAppEventsLogger() {
        Session activeSession = Session.getActiveSession();
        if (activeSession == null) {
            return null;
        }
        if (this.g == null || !this.g.a(activeSession)) {
            if (this.g != null) {
                AppEventsLogger.onContextStop();
            }
            this.g = AppEventsLogger.newLogger((Context) this.a, activeSession);
        }
        return this.g;
    }

    private boolean a(int i, int i2, Intent intent, Callback callback) {
        if (this.e == null) {
            return false;
        }
        PendingCall pendingCallById = this.f.getPendingCallById(this.e);
        if (pendingCallById == null || pendingCallById.getRequestCode() != i) {
            return false;
        }
        if (intent == null) {
            a(callback);
            return true;
        }
        UUID callIdFromIntent = NativeProtocol.getCallIdFromIntent(intent);
        if (callIdFromIntent == null || !this.e.equals(callIdFromIntent)) {
            a(callback);
        } else {
            FacebookDialog.handleActivityResult(this.a, pendingCallById, i, intent, callback);
        }
        a();
        return true;
    }

    private void a(Callback callback) {
        if (this.e != null) {
            PendingCall pendingCallById = this.f.getPendingCallById(this.e);
            if (pendingCallById != null) {
                if (callback != null) {
                    Intent requestIntent = pendingCallById.getRequestIntent();
                    Intent intent = new Intent();
                    intent.putExtra(NativeProtocol.EXTRA_PROTOCOL_CALL_ID, requestIntent.getStringExtra(NativeProtocol.EXTRA_PROTOCOL_CALL_ID));
                    intent.putExtra(NativeProtocol.EXTRA_PROTOCOL_ACTION, requestIntent.getStringExtra(NativeProtocol.EXTRA_PROTOCOL_ACTION));
                    intent.putExtra(NativeProtocol.EXTRA_PROTOCOL_VERSION, requestIntent.getIntExtra(NativeProtocol.EXTRA_PROTOCOL_VERSION, 0));
                    intent.putExtra(NativeProtocol.STATUS_ERROR_TYPE, NativeProtocol.ERROR_UNKNOWN_ERROR);
                    FacebookDialog.handleActivityResult(this.a, pendingCallById, pendingCallById.getRequestCode(), intent, callback);
                }
                a();
            }
        }
    }

    private void a() {
        this.f.stopTrackingPendingCall(this.e);
        this.e = null;
    }
}
