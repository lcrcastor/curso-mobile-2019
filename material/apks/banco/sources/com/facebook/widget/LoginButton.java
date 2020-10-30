package com.facebook.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.android.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.SessionAuthorizationType;
import com.facebook.internal.SessionTracker;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.model.GraphUser;
import com.facebook.widget.ToolTipPopup.Style;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LoginButton extends Button {
    /* access modifiers changed from: private */
    public static final String a = "com.facebook.widget.LoginButton";
    /* access modifiers changed from: private */
    public String b = null;
    /* access modifiers changed from: private */
    public SessionTracker c;
    /* access modifiers changed from: private */
    public GraphUser d = null;
    private Session e = null;
    /* access modifiers changed from: private */
    public boolean f;
    private boolean g;
    private String h;
    private String i;
    /* access modifiers changed from: private */
    public UserInfoChangedCallback j;
    /* access modifiers changed from: private */
    public Fragment k;
    /* access modifiers changed from: private */
    public LoginButtonProperties l = new LoginButtonProperties();
    /* access modifiers changed from: private */
    public String m = AnalyticsEvents.EVENT_LOGIN_VIEW_USAGE;
    /* access modifiers changed from: private */
    public OnClickListener n;
    private boolean o;
    private Style p = Style.BLUE;
    private ToolTipMode q = ToolTipMode.DEFAULT;
    private long r = ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME;
    private ToolTipPopup s;

    class LoginButtonCallback implements StatusCallback {
        private LoginButtonCallback() {
        }

        public void call(Session session, SessionState sessionState, Exception exc) {
            LoginButton.this.e();
            LoginButton.this.d();
            if (LoginButton.this.l.f != null) {
                LoginButton.this.l.f.call(session, sessionState, exc);
            } else if (exc != null) {
                LoginButton.this.a(exc);
            }
        }
    }

    static class LoginButtonProperties {
        /* access modifiers changed from: private */
        public SessionDefaultAudience a = SessionDefaultAudience.FRIENDS;
        /* access modifiers changed from: private */
        public List<String> b = Collections.emptyList();
        /* access modifiers changed from: private */
        public SessionAuthorizationType c = null;
        /* access modifiers changed from: private */
        public OnErrorListener d;
        /* access modifiers changed from: private */
        public SessionLoginBehavior e = SessionLoginBehavior.SSO_WITH_FALLBACK;
        /* access modifiers changed from: private */
        public StatusCallback f;

        LoginButtonProperties() {
        }

        public void a(OnErrorListener onErrorListener) {
            this.d = onErrorListener;
        }

        public OnErrorListener a() {
            return this.d;
        }

        public void a(SessionDefaultAudience sessionDefaultAudience) {
            this.a = sessionDefaultAudience;
        }

        public SessionDefaultAudience b() {
            return this.a;
        }

        public void a(List<String> list, Session session) {
            if (SessionAuthorizationType.PUBLISH.equals(this.c)) {
                throw new UnsupportedOperationException("Cannot call setReadPermissions after setPublishPermissions has been called.");
            } else if (a(list, SessionAuthorizationType.READ, session)) {
                this.b = list;
                this.c = SessionAuthorizationType.READ;
            }
        }

        public void b(List<String> list, Session session) {
            if (SessionAuthorizationType.READ.equals(this.c)) {
                throw new UnsupportedOperationException("Cannot call setPublishPermissions after setReadPermissions has been called.");
            } else if (a(list, SessionAuthorizationType.PUBLISH, session)) {
                this.b = list;
                this.c = SessionAuthorizationType.PUBLISH;
            }
        }

        private boolean a(List<String> list, SessionAuthorizationType sessionAuthorizationType, Session session) {
            if (SessionAuthorizationType.PUBLISH.equals(sessionAuthorizationType) && Utility.isNullOrEmpty((Collection<T>) list)) {
                throw new IllegalArgumentException("Permissions for publish actions cannot be null or empty.");
            } else if (session == null || !session.isOpened() || Utility.isSubset(list, session.getPermissions())) {
                return true;
            } else {
                Log.e(LoginButton.a, "Cannot set additional permissions when session is already open.");
                return false;
            }
        }

        /* access modifiers changed from: 0000 */
        public List<String> c() {
            return this.b;
        }

        public void d() {
            this.b = null;
            this.c = null;
        }

        public void a(SessionLoginBehavior sessionLoginBehavior) {
            this.e = sessionLoginBehavior;
        }

        public SessionLoginBehavior e() {
            return this.e;
        }

        public void a(StatusCallback statusCallback) {
            this.f = statusCallback;
        }

        public StatusCallback f() {
            return this.f;
        }
    }

    class LoginClickListener implements OnClickListener {
        private LoginClickListener() {
        }

        public void onClick(View view) {
            OpenRequest openRequest;
            String str;
            Context context = LoginButton.this.getContext();
            final Session openSession = LoginButton.this.c.getOpenSession();
            int i = 0;
            if (openSession == null) {
                Session session = LoginButton.this.c.getSession();
                if (session == null || session.getState().isClosed()) {
                    LoginButton.this.c.setSession(null);
                    session = new Builder(context).setApplicationId(LoginButton.this.b).build();
                    Session.setActiveSession(session);
                }
                if (!session.isOpened()) {
                    if (LoginButton.this.k != null) {
                        openRequest = new OpenRequest(LoginButton.this.k);
                    } else if (context instanceof Activity) {
                        openRequest = new OpenRequest((Activity) context);
                    } else {
                        if (context instanceof ContextWrapper) {
                            Context baseContext = ((ContextWrapper) context).getBaseContext();
                            if (baseContext instanceof Activity) {
                                openRequest = new OpenRequest((Activity) baseContext);
                            }
                        }
                        openRequest = null;
                    }
                    if (openRequest != null) {
                        openRequest.setDefaultAudience(LoginButton.this.l.a);
                        openRequest.setPermissions(LoginButton.this.l.b);
                        openRequest.setLoginBehavior(LoginButton.this.l.e);
                        if (SessionAuthorizationType.PUBLISH.equals(LoginButton.this.l.c)) {
                            session.openForPublish(openRequest);
                        } else {
                            session.openForRead(openRequest);
                        }
                    }
                }
            } else if (LoginButton.this.f) {
                String string = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_log_out_action);
                String string2 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_cancel_action);
                if (LoginButton.this.d == null || LoginButton.this.d.getName() == null) {
                    str = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_using_facebook);
                } else {
                    str = String.format(LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_as), new Object[]{LoginButton.this.d.getName()});
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(str).setCancelable(true).setPositiveButton(string, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openSession.closeAndClearTokenInformation();
                    }
                }).setNegativeButton(string2, null);
                builder.create().show();
            } else {
                openSession.closeAndClearTokenInformation();
            }
            AppEventsLogger newLogger = AppEventsLogger.newLogger(LoginButton.this.getContext());
            Bundle bundle = new Bundle();
            String str2 = "logging_in";
            if (openSession == null) {
                i = 1;
            }
            bundle.putInt(str2, i);
            newLogger.logSdkEvent(LoginButton.this.m, null, bundle);
            if (LoginButton.this.n != null) {
                LoginButton.this.n.onClick(view);
            }
        }
    }

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    public enum ToolTipMode {
        DEFAULT,
        DISPLAY_ALWAYS,
        NEVER_DISPLAY
    }

    public interface UserInfoChangedCallback {
        void onUserInfoFetched(GraphUser graphUser);
    }

    public LoginButton(Context context) {
        super(context);
        a(context);
        b();
    }

    public LoginButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (attributeSet.getStyleAttribute() == 0) {
            setGravity(17);
            setTextColor(getResources().getColor(R.color.com_facebook_loginview_text_color));
            setTextSize(0, getResources().getDimension(R.dimen.com_facebook_loginview_text_size));
            setTypeface(Typeface.DEFAULT_BOLD);
            if (isInEditMode()) {
                setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
                this.h = "Log in with Facebook";
            } else {
                setBackgroundResource(R.drawable.com_facebook_button_blue);
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_inverse_icon, 0, 0, 0);
                setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.com_facebook_loginview_compound_drawable_padding));
                setPadding(getResources().getDimensionPixelSize(R.dimen.com_facebook_loginview_padding_left), getResources().getDimensionPixelSize(R.dimen.com_facebook_loginview_padding_top), getResources().getDimensionPixelSize(R.dimen.com_facebook_loginview_padding_right), getResources().getDimensionPixelSize(R.dimen.com_facebook_loginview_padding_bottom));
            }
        }
        a(attributeSet);
        if (!isInEditMode()) {
            a(context);
        }
    }

    public LoginButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(attributeSet);
        a(context);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.l.a(onErrorListener);
    }

    public OnErrorListener getOnErrorListener() {
        return this.l.a();
    }

    public void setDefaultAudience(SessionDefaultAudience sessionDefaultAudience) {
        this.l.a(sessionDefaultAudience);
    }

    public SessionDefaultAudience getDefaultAudience() {
        return this.l.b();
    }

    public void setReadPermissions(List<String> list) {
        this.l.a(list, this.c.getSession());
    }

    public void setReadPermissions(String... strArr) {
        this.l.a(Arrays.asList(strArr), this.c.getSession());
    }

    public void setPublishPermissions(List<String> list) {
        this.l.b(list, this.c.getSession());
    }

    public void setPublishPermissions(String... strArr) {
        this.l.b(Arrays.asList(strArr), this.c.getSession());
    }

    public void clearPermissions() {
        this.l.d();
    }

    public void setLoginBehavior(SessionLoginBehavior sessionLoginBehavior) {
        this.l.a(sessionLoginBehavior);
    }

    public SessionLoginBehavior getLoginBehavior() {
        return this.l.e();
    }

    public void setApplicationId(String str) {
        this.b = str;
    }

    public UserInfoChangedCallback getUserInfoChangedCallback() {
        return this.j;
    }

    public void setUserInfoChangedCallback(UserInfoChangedCallback userInfoChangedCallback) {
        this.j = userInfoChangedCallback;
    }

    public void setSessionStatusCallback(StatusCallback statusCallback) {
        this.l.a(statusCallback);
    }

    public StatusCallback getSessionStatusCallback() {
        return this.l.f();
    }

    public void setToolTipStyle(Style style) {
        this.p = style;
    }

    public void setToolTipMode(ToolTipMode toolTipMode) {
        this.q = toolTipMode;
    }

    public ToolTipMode getToolTipMode() {
        return this.q;
    }

    public void setToolTipDisplayTime(long j2) {
        this.r = j2;
    }

    public long getToolTipDisplayTime() {
        return this.r;
    }

    public void dismissToolTip() {
        if (this.s != null) {
            this.s.dismiss();
            this.s = null;
        }
    }

    public boolean onActivityResult(int i2, int i3, Intent intent) {
        Session session = this.c.getSession();
        if (session != null) {
            return session.onActivityResult((Activity) getContext(), i2, i3, intent);
        }
        return false;
    }

    public void setSession(Session session) {
        this.c.setSession(session);
        e();
        d();
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        b();
    }

    private void b() {
        super.setOnClickListener(new LoginClickListener());
        d();
        if (!isInEditMode()) {
            this.c = new SessionTracker(getContext(), new LoginButtonCallback(), null, false);
            e();
        }
    }

    public void setFragment(Fragment fragment) {
        this.k = fragment;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.c != null && !this.c.isTracking()) {
            this.c.startTracking();
            e();
            d();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.o && this.q != ToolTipMode.NEVER_DISPLAY && !isInEditMode()) {
            this.o = true;
            c();
        }
    }

    /* access modifiers changed from: private */
    public void a(FetchedAppSettings fetchedAppSettings) {
        if (fetchedAppSettings != null && fetchedAppSettings.getNuxEnabled() && getVisibility() == 0) {
            a(fetchedAppSettings.getNuxContent());
        }
    }

    private void a(String str) {
        this.s = new ToolTipPopup(str, this);
        this.s.setStyle(this.p);
        this.s.setNuxDisplayTime(this.r);
        this.s.show();
    }

    private void c() {
        if (this.q == ToolTipMode.DISPLAY_ALWAYS) {
            a(getResources().getString(R.string.com_facebook_tooltip_default));
            return;
        }
        final String metadataApplicationId = Utility.getMetadataApplicationId(getContext());
        new AsyncTask<Void, Void, FetchedAppSettings>() {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public FetchedAppSettings doInBackground(Void... voidArr) {
                return Utility.queryAppSettings(metadataApplicationId, false);
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void onPostExecute(FetchedAppSettings fetchedAppSettings) {
                LoginButton.this.a(fetchedAppSettings);
            }
        }.execute(null);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.c != null) {
            this.c.stopTracking();
        }
        dismissToolTip();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i2) {
        super.onVisibilityChanged(view, i2);
        if (i2 != 0) {
            dismissToolTip();
        }
    }

    /* access modifiers changed from: 0000 */
    public List<String> getPermissions() {
        return this.l.c();
    }

    /* access modifiers changed from: 0000 */
    public void setProperties(LoginButtonProperties loginButtonProperties) {
        this.l = loginButtonProperties;
    }

    /* access modifiers changed from: 0000 */
    public void setLoginLogoutEventName(String str) {
        this.m = str;
    }

    private void a(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_login_view);
        this.f = obtainStyledAttributes.getBoolean(0, true);
        this.g = obtainStyledAttributes.getBoolean(1, true);
        this.h = obtainStyledAttributes.getString(2);
        this.i = obtainStyledAttributes.getString(3);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.c == null || this.c.getOpenSession() == null) {
            setText(this.h != null ? this.h : getResources().getString(R.string.com_facebook_loginview_log_in_button));
        } else {
            setText(this.i != null ? this.i : getResources().getString(R.string.com_facebook_loginview_log_out_button));
        }
    }

    private boolean a(Context context) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        Session activeSession = Session.getActiveSession();
        if (activeSession != null) {
            return activeSession.isOpened();
        }
        if (Utility.getMetadataApplicationId(context) == null) {
            return false;
        }
        if (Session.openActiveSessionFromCache(context) != null) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.g) {
            final Session openSession = this.c.getOpenSession();
            if (openSession == null) {
                this.d = null;
                if (this.j != null) {
                    this.j.onUserInfoFetched(this.d);
                }
            } else if (openSession != this.e) {
                Request.executeBatchAsync(Request.newMeRequest(openSession, new GraphUserCallback() {
                    public void onCompleted(GraphUser graphUser, Response response) {
                        if (openSession == LoginButton.this.c.getOpenSession()) {
                            LoginButton.this.d = graphUser;
                            if (LoginButton.this.j != null) {
                                LoginButton.this.j.onUserInfoFetched(LoginButton.this.d);
                            }
                        }
                        if (response.getError() != null) {
                            LoginButton.this.a((Exception) response.getError().getException());
                        }
                    }
                }));
                this.e = openSession;
            }
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.n = onClickListener;
    }

    /* access modifiers changed from: 0000 */
    public void a(Exception exc) {
        if (this.l.d == null) {
            return;
        }
        if (exc instanceof FacebookException) {
            this.l.d.onError((FacebookException) exc);
        } else {
            this.l.d.onError(new FacebookException((Throwable) exc));
        }
    }
}
