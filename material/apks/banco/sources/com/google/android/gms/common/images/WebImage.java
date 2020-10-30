package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage extends AbstractSafeParcelable {
    public static final Creator<WebImage> CREATOR = new zzb();
    private final int a;
    private final Uri b;
    private final int c;
    private final int d;

    WebImage(int i, Uri uri, int i2, int i3) {
        this.a = i;
        this.b = uri;
        this.c = i2;
        this.d = i3;
    }

    public WebImage(Uri uri) {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int i, int i2) {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject jSONObject) {
        this(a(jSONObject), jSONObject.optInt(SettingsJsonConstants.ICON_WIDTH_KEY, 0), jSONObject.optInt(SettingsJsonConstants.ICON_HEIGHT_KEY, 0));
    }

    private static Uri a(JSONObject jSONObject) {
        if (jSONObject.has("url")) {
            try {
                return Uri.parse(jSONObject.getString("url"));
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) obj;
        return zzab.equal(this.b, webImage.b) && this.c == webImage.c && this.d == webImage.d;
    }

    public int getHeight() {
        return this.d;
    }

    public Uri getUrl() {
        return this.b;
    }

    public int getWidth() {
        return this.c;
    }

    public int hashCode() {
        return zzab.hashCode(this.b, Integer.valueOf(this.c), Integer.valueOf(this.d));
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.b.toString());
            jSONObject.put(SettingsJsonConstants.ICON_WIDTH_KEY, this.c);
            jSONObject.put(SettingsJsonConstants.ICON_HEIGHT_KEY, this.d);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public String toString() {
        return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), this.b.toString()});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.a(this, parcel, i);
    }
}
