package com.facebook.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;

public class SessionTracker {
    /* access modifiers changed from: private */
    public Session a;
    /* access modifiers changed from: private */
    public final StatusCallback b;
    private final BroadcastReceiver c;
    private final LocalBroadcastManager d;
    private boolean e;

    class ActiveSessionBroadcastReceiver extends BroadcastReceiver {
        private ActiveSessionBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Session.ACTION_ACTIVE_SESSION_SET.equals(intent.getAction())) {
                Session activeSession = Session.getActiveSession();
                if (activeSession != null) {
                    activeSession.addCallback(SessionTracker.this.b);
                }
            }
        }
    }

    class CallbackWrapper implements StatusCallback {
        private final StatusCallback b;

        public CallbackWrapper(StatusCallback statusCallback) {
            this.b = statusCallback;
        }

        public void call(Session session, SessionState sessionState, Exception exc) {
            if (this.b != null && SessionTracker.this.isTracking()) {
                this.b.call(session, sessionState, exc);
            }
            if (session == SessionTracker.this.a && sessionState.isClosed()) {
                SessionTracker.this.setSession(null);
            }
        }
    }

    public SessionTracker(Context context, StatusCallback statusCallback) {
        this(context, statusCallback, null);
    }

    SessionTracker(Context context, StatusCallback statusCallback, Session session) {
        this(context, statusCallback, session, true);
    }

    public SessionTracker(Context context, StatusCallback statusCallback, Session session, boolean z) {
        this.e = false;
        this.b = new CallbackWrapper(statusCallback);
        this.a = session;
        this.c = new ActiveSessionBroadcastReceiver();
        this.d = LocalBroadcastManager.getInstance(context);
        if (z) {
            startTracking();
        }
    }

    public Session getSession() {
        return this.a == null ? Session.getActiveSession() : this.a;
    }

    public Session getOpenSession() {
        Session session = getSession();
        if (session == null || !session.isOpened()) {
            return null;
        }
        return session;
    }

    public void setSession(Session session) {
        if (session != null) {
            if (this.a == null) {
                Session activeSession = Session.getActiveSession();
                if (activeSession != null) {
                    activeSession.removeCallback(this.b);
                }
                this.d.unregisterReceiver(this.c);
            } else {
                this.a.removeCallback(this.b);
            }
            this.a = session;
            this.a.addCallback(this.b);
        } else if (this.a != null) {
            this.a.removeCallback(this.b);
            this.a = null;
            a();
            if (getSession() != null) {
                getSession().addCallback(this.b);
            }
        }
    }

    public void startTracking() {
        if (!this.e) {
            if (this.a == null) {
                a();
            }
            if (getSession() != null) {
                getSession().addCallback(this.b);
            }
            this.e = true;
        }
    }

    public void stopTracking() {
        if (this.e) {
            Session session = getSession();
            if (session != null) {
                session.removeCallback(this.b);
            }
            this.d.unregisterReceiver(this.c);
            this.e = false;
        }
    }

    public boolean isTracking() {
        return this.e;
    }

    public boolean isTrackingActiveSession() {
        return this.a == null;
    }

    private void a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_SET);
        intentFilter.addAction(Session.ACTION_ACTIVE_SESSION_UNSET);
        this.d.registerReceiver(this.c, intentFilter);
    }
}
