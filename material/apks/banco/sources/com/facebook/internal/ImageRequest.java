package com.facebook.internal;

import android.content.Context;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.net.URI;

public class ImageRequest {
    public static final int UNSPECIFIED_DIMENSION = 0;
    private Context a;
    private URI b;
    private Callback c;
    private boolean d;
    private Object e;

    public static class Builder {
        /* access modifiers changed from: private */
        public Context a;
        /* access modifiers changed from: private */
        public URI b;
        /* access modifiers changed from: private */
        public Callback c;
        /* access modifiers changed from: private */
        public boolean d;
        /* access modifiers changed from: private */
        public Object e;

        public Builder(Context context, URI uri) {
            Validate.notNull(uri, "imageUrl");
            this.a = context;
            this.b = uri;
        }

        public Builder setCallback(Callback callback) {
            this.c = callback;
            return this;
        }

        public Builder setCallerTag(Object obj) {
            this.e = obj;
            return this;
        }

        public Builder setAllowCachedRedirects(boolean z) {
            this.d = z;
            return this;
        }

        public ImageRequest build() {
            return new ImageRequest(this);
        }
    }

    public interface Callback {
        void onCompleted(ImageResponse imageResponse);
    }

    public static URI getProfilePictureUrl(String str, int i, int i2) {
        Validate.notNullOrEmpty(str, "userId");
        int max = Math.max(i, 0);
        int max2 = Math.max(i2, 0);
        if (max == 0 && max2 == 0) {
            throw new IllegalArgumentException("Either width or height must be greater than 0");
        }
        android.net.Uri.Builder encodedPath = new android.net.Uri.Builder().encodedPath(String.format("https://graph.facebook.com/%s/picture", new Object[]{str}));
        if (max2 != 0) {
            encodedPath.appendQueryParameter(SettingsJsonConstants.ICON_HEIGHT_KEY, String.valueOf(max2));
        }
        if (max != 0) {
            encodedPath.appendQueryParameter(SettingsJsonConstants.ICON_WIDTH_KEY, String.valueOf(max));
        }
        encodedPath.appendQueryParameter("migration_overrides", "{october_2012:true}");
        return new URI(encodedPath.toString());
    }

    private ImageRequest(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e == null ? new Object() : builder.e;
    }

    public Context getContext() {
        return this.a;
    }

    public URI getImageUri() {
        return this.b;
    }

    public Callback getCallback() {
        return this.c;
    }

    public boolean isCachedRedirectAllowed() {
        return this.d;
    }

    public Object getCallerTag() {
        return this.e;
    }
}
