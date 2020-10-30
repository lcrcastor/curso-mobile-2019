package com.facebook.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.SessionAuthorizationType;
import com.facebook.internal.SessionTracker;
import java.util.Date;
import java.util.List;

class FacebookFragment extends Fragment {
    private SessionTracker a;

    class DefaultSessionStatusCallback implements StatusCallback {
        private DefaultSessionStatusCallback() {
        }

        public void call(Session session, SessionState sessionState, Exception exc) {
            FacebookFragment.this.onSessionStateChange(sessionState, exc);
        }
    }

    /* access modifiers changed from: protected */
    public void onSessionStateChange(SessionState sessionState, Exception exc) {
    }

    FacebookFragment() {
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.a = new SessionTracker(getActivity(), new DefaultSessionStatusCallback());
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.a.getSession().onActivityResult(getActivity(), i, i2, intent);
    }

    public void onDestroy() {
        super.onDestroy();
        this.a.stopTracking();
    }

    public void setSession(Session session) {
        if (this.a != null) {
            this.a.setSession(session);
        }
    }

    /* access modifiers changed from: protected */
    public final Session getSession() {
        if (this.a != null) {
            return this.a.getSession();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final boolean isSessionOpen() {
        boolean z = false;
        if (this.a == null) {
            return false;
        }
        if (this.a.getOpenSession() != null) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public final SessionState getSessionState() {
        SessionState sessionState = null;
        if (this.a == null) {
            return null;
        }
        Session session = this.a.getSession();
        if (session != null) {
            sessionState = session.getState();
        }
        return sessionState;
    }

    /* access modifiers changed from: protected */
    public final String getAccessToken() {
        String str = null;
        if (this.a == null) {
            return null;
        }
        Session openSession = this.a.getOpenSession();
        if (openSession != null) {
            str = openSession.getAccessToken();
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public final Date getExpirationDate() {
        Date date = null;
        if (this.a == null) {
            return null;
        }
        Session openSession = this.a.getOpenSession();
        if (openSession != null) {
            date = openSession.getExpirationDate();
        }
        return date;
    }

    /* access modifiers changed from: protected */
    public final void closeSession() {
        if (this.a != null) {
            Session openSession = this.a.getOpenSession();
            if (openSession != null) {
                openSession.close();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void closeSessionAndClearTokenInformation() {
        if (this.a != null) {
            Session openSession = this.a.getOpenSession();
            if (openSession != null) {
                openSession.closeAndClearTokenInformation();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final List<String> getSessionPermissions() {
        List<String> list = null;
        if (this.a == null) {
            return null;
        }
        Session session = this.a.getSession();
        if (session != null) {
            list = session.getPermissions();
        }
        return list;
    }

    /* access modifiers changed from: protected */
    public final void openSession() {
        openSessionForRead(null, null);
    }

    /* access modifiers changed from: protected */
    public final void openSessionForRead(String str, List<String> list) {
        openSessionForRead(str, list, SessionLoginBehavior.SSO_WITH_FALLBACK, Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
    }

    /* access modifiers changed from: protected */
    public final void openSessionForRead(String str, List<String> list, SessionLoginBehavior sessionLoginBehavior, int i) {
        a(str, list, sessionLoginBehavior, i, SessionAuthorizationType.READ);
    }

    /* access modifiers changed from: protected */
    public final void openSessionForPublish(String str, List<String> list) {
        openSessionForPublish(str, list, SessionLoginBehavior.SSO_WITH_FALLBACK, Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
    }

    /* access modifiers changed from: protected */
    public final void openSessionForPublish(String str, List<String> list, SessionLoginBehavior sessionLoginBehavior, int i) {
        a(str, list, sessionLoginBehavior, i, SessionAuthorizationType.PUBLISH);
    }

    private void a(String str, List<String> list, SessionLoginBehavior sessionLoginBehavior, int i, SessionAuthorizationType sessionAuthorizationType) {
        if (this.a != null) {
            Session session = this.a.getSession();
            if (session == null || session.getState().isClosed()) {
                session = new Builder(getActivity()).setApplicationId(str).build();
                Session.setActiveSession(session);
            }
            if (!session.isOpened()) {
                OpenRequest requestCode = new OpenRequest((Fragment) this).setPermissions((List) list).setLoginBehavior(sessionLoginBehavior).setRequestCode(i);
                if (SessionAuthorizationType.PUBLISH.equals(sessionAuthorizationType)) {
                    session.openForPublish(requestCode);
                } else {
                    session.openForRead(requestCode);
                }
            }
        }
    }
}
