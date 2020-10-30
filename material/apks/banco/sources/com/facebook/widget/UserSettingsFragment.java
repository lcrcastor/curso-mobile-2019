package com.facebook.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.android.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageRequest.Builder;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.ImageResponse;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class UserSettingsFragment extends FacebookFragment {
    private static final String a = TextUtils.join(",", new String[]{"id", "name", "picture"});
    /* access modifiers changed from: private */
    public LoginButton b;
    private LoginButtonProperties c = new LoginButtonProperties();
    private TextView d;
    /* access modifiers changed from: private */
    public GraphUser e;
    private Session f;
    private Drawable g;
    private String h;
    private StatusCallback i;

    public /* bridge */ /* synthetic */ void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public /* bridge */ /* synthetic */ void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
    }

    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.com_facebook_usersettingsfragment, viewGroup, false);
        this.b = (LoginButton) inflate.findViewById(R.id.com_facebook_usersettingsfragment_login_button);
        this.b.setProperties(this.c);
        this.b.setFragment(this);
        this.b.setLoginLogoutEventName(AnalyticsEvents.EVENT_USER_SETTINGS_USAGE);
        Session session = getSession();
        if (session != null && !session.equals(Session.getActiveSession())) {
            this.b.setSession(session);
        }
        this.d = (TextView) inflate.findViewById(R.id.com_facebook_usersettingsfragment_profile_name);
        if (inflate.getBackground() == null) {
            inflate.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
        } else {
            inflate.getBackground().setDither(true);
        }
        return inflate;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void onResume() {
        super.onResume();
        y();
        z();
    }

    public void setSession(Session session) {
        super.setSession(session);
        if (this.b != null) {
            this.b.setSession(session);
        }
        y();
        z();
    }

    public void setDefaultAudience(SessionDefaultAudience sessionDefaultAudience) {
        this.c.a(sessionDefaultAudience);
    }

    public SessionDefaultAudience getDefaultAudience() {
        return this.c.b();
    }

    public void setReadPermissions(List<String> list) {
        this.c.a(list, getSession());
    }

    public void setReadPermissions(String... strArr) {
        this.c.a(Arrays.asList(strArr), getSession());
    }

    public void setPublishPermissions(List<String> list) {
        this.c.b(list, getSession());
    }

    public void setPublishPermissions(String... strArr) {
        this.c.b(Arrays.asList(strArr), getSession());
    }

    public void clearPermissions() {
        this.c.d();
    }

    public void setLoginBehavior(SessionLoginBehavior sessionLoginBehavior) {
        this.c.a(sessionLoginBehavior);
    }

    public SessionLoginBehavior getLoginBehavior() {
        return this.c.e();
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.c.a(onErrorListener);
    }

    public OnErrorListener getOnErrorListener() {
        return this.c.a();
    }

    public void setSessionStatusCallback(StatusCallback statusCallback) {
        this.i = statusCallback;
    }

    public StatusCallback getSessionStatusCallback() {
        return this.i;
    }

    /* access modifiers changed from: protected */
    public void onSessionStateChange(SessionState sessionState, Exception exc) {
        y();
        z();
        if (this.i != null) {
            this.i.call(getSession(), sessionState, exc);
        }
    }

    private void y() {
        final Session session = getSession();
        if (session == null || !session.isOpened()) {
            this.e = null;
        } else if (session != this.f) {
            Request newMeRequest = Request.newMeRequest(session, new GraphUserCallback() {
                public void onCompleted(GraphUser graphUser, Response response) {
                    if (session == UserSettingsFragment.this.getSession()) {
                        UserSettingsFragment.this.e = graphUser;
                        UserSettingsFragment.this.z();
                    }
                    if (response.getError() != null) {
                        UserSettingsFragment.this.b.a((Exception) response.getError().getException());
                    }
                }
            });
            Bundle bundle = new Bundle();
            bundle.putString("fields", a);
            newMeRequest.setParameters(bundle);
            Request.executeBatchAsync(newMeRequest);
            this.f = session;
        }
    }

    /* access modifiers changed from: private */
    public void z() {
        if (isAdded()) {
            if (isSessionOpen()) {
                this.d.setTextColor(getResources().getColor(R.color.com_facebook_usersettingsfragment_connected_text_color));
                this.d.setShadowLayer(1.0f, BitmapDescriptorFactory.HUE_RED, -1.0f, getResources().getColor(R.color.com_facebook_usersettingsfragment_connected_shadow_color));
                if (this.e != null) {
                    ImageRequest A = A();
                    if (A != null) {
                        URI imageUri = A.getImageUri();
                        if (!imageUri.equals(this.d.getTag())) {
                            if (this.e.getId().equals(this.h)) {
                                this.d.setCompoundDrawables(null, this.g, null, null);
                                this.d.setTag(imageUri);
                            } else {
                                ImageDownloader.downloadAsync(A);
                            }
                        }
                    }
                    this.d.setText(this.e.getName());
                } else {
                    this.d.setText(getResources().getString(R.string.com_facebook_usersettingsfragment_logged_in));
                    Drawable drawable = getResources().getDrawable(R.drawable.com_facebook_profile_default_icon);
                    drawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height));
                    this.d.setCompoundDrawables(null, drawable, null, null);
                }
            } else {
                int color = getResources().getColor(R.color.com_facebook_usersettingsfragment_not_connected_text_color);
                this.d.setTextColor(color);
                this.d.setShadowLayer(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, color);
                this.d.setText(getResources().getString(R.string.com_facebook_usersettingsfragment_not_logged_in));
                this.d.setCompoundDrawables(null, null, null, null);
                this.d.setTag(null);
            }
        }
    }

    private ImageRequest A() {
        try {
            return new Builder(getActivity(), ImageRequest.getProfilePictureUrl(this.e.getId(), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height))).setCallerTag(this).setCallback(new Callback() {
                public void onCompleted(ImageResponse imageResponse) {
                    UserSettingsFragment.this.a(UserSettingsFragment.this.e.getId(), imageResponse);
                }
            }).build();
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, ImageResponse imageResponse) {
        if (imageResponse != null) {
            Bitmap bitmap = imageResponse.getBitmap();
            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                bitmapDrawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height));
                this.g = bitmapDrawable;
                this.h = str;
                this.d.setCompoundDrawables(null, bitmapDrawable, null, null);
                this.d.setTag(imageResponse.getRequest().getImageUri());
            }
        }
    }
}
