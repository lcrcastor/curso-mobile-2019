package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.TextUtils;

public final class MediaDescriptionCompat implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final Creator<MediaDescriptionCompat> CREATOR = new Creator<MediaDescriptionCompat>() {
        /* renamed from: a */
        public MediaDescriptionCompat createFromParcel(Parcel parcel) {
            if (VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat(parcel);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.a(parcel));
        }

        /* renamed from: a */
        public MediaDescriptionCompat[] newArray(int i) {
            return new MediaDescriptionCompat[i];
        }
    };
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
    public static final long STATUS_DOWNLOADED = 2;
    public static final long STATUS_DOWNLOADING = 1;
    public static final long STATUS_NOT_DOWNLOADED = 0;
    private final String a;
    private final CharSequence b;
    private final CharSequence c;
    private final CharSequence d;
    private final Bitmap e;
    private final Uri f;
    private final Bundle g;
    private final Uri h;
    private Object i;

    public static final class Builder {
        private String a;
        private CharSequence b;
        private CharSequence c;
        private CharSequence d;
        private Bitmap e;
        private Uri f;
        private Bundle g;
        private Uri h;

        public Builder setMediaId(@Nullable String str) {
            this.a = str;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.b = charSequence;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.c = charSequence;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.e = bitmap;
            return this;
        }

        public Builder setIconUri(@Nullable Uri uri) {
            this.f = uri;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            this.g = bundle;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            this.h = uri;
            return this;
        }

        public MediaDescriptionCompat build() {
            MediaDescriptionCompat mediaDescriptionCompat = new MediaDescriptionCompat(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
            return mediaDescriptionCompat;
        }
    }

    public int describeContents() {
        return 0;
    }

    MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.a = str;
        this.b = charSequence;
        this.c = charSequence2;
        this.d = charSequence3;
        this.e = bitmap;
        this.f = uri;
        this.g = bundle;
        this.h = uri2;
    }

    MediaDescriptionCompat(Parcel parcel) {
        this.a = parcel.readString();
        this.b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.c = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.d = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.e = (Bitmap) parcel.readParcelable(null);
        this.f = (Uri) parcel.readParcelable(null);
        this.g = parcel.readBundle();
        this.h = (Uri) parcel.readParcelable(null);
    }

    @Nullable
    public String getMediaId() {
        return this.a;
    }

    @Nullable
    public CharSequence getTitle() {
        return this.b;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return this.c;
    }

    @Nullable
    public CharSequence getDescription() {
        return this.d;
    }

    @Nullable
    public Bitmap getIconBitmap() {
        return this.e;
    }

    @Nullable
    public Uri getIconUri() {
        return this.f;
    }

    @Nullable
    public Bundle getExtras() {
        return this.g;
    }

    @Nullable
    public Uri getMediaUri() {
        return this.h;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        if (VERSION.SDK_INT < 21) {
            parcel.writeString(this.a);
            TextUtils.writeToParcel(this.b, parcel, i2);
            TextUtils.writeToParcel(this.c, parcel, i2);
            TextUtils.writeToParcel(this.d, parcel, i2);
            parcel.writeParcelable(this.e, i2);
            parcel.writeParcelable(this.f, i2);
            parcel.writeBundle(this.g);
            parcel.writeParcelable(this.h, i2);
            return;
        }
        MediaDescriptionCompatApi21.a(getMediaDescription(), parcel, i2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(", ");
        sb.append(this.c);
        sb.append(", ");
        sb.append(this.d);
        return sb.toString();
    }

    public Object getMediaDescription() {
        if (this.i != null || VERSION.SDK_INT < 21) {
            return this.i;
        }
        Object a2 = Builder.a();
        Builder.a(a2, this.a);
        Builder.a(a2, this.b);
        Builder.b(a2, this.c);
        Builder.c(a2, this.d);
        Builder.a(a2, this.e);
        Builder.a(a2, this.f);
        Bundle bundle = this.g;
        if (VERSION.SDK_INT < 23 && this.h != null) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
            }
            bundle.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.h);
        }
        Builder.a(a2, bundle);
        if (VERSION.SDK_INT >= 23) {
            Builder.b(a2, this.h);
        }
        this.i = Builder.a(a2);
        return this.i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.v4.media.MediaDescriptionCompat fromMediaDescription(java.lang.Object r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x0082
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r1 < r2) goto L_0x0082
            android.support.v4.media.MediaDescriptionCompat$Builder r1 = new android.support.v4.media.MediaDescriptionCompat$Builder
            r1.<init>()
            java.lang.String r2 = android.support.v4.media.MediaDescriptionCompatApi21.a(r6)
            r1.setMediaId(r2)
            java.lang.CharSequence r2 = android.support.v4.media.MediaDescriptionCompatApi21.b(r6)
            r1.setTitle(r2)
            java.lang.CharSequence r2 = android.support.v4.media.MediaDescriptionCompatApi21.c(r6)
            r1.setSubtitle(r2)
            java.lang.CharSequence r2 = android.support.v4.media.MediaDescriptionCompatApi21.d(r6)
            r1.setDescription(r2)
            android.graphics.Bitmap r2 = android.support.v4.media.MediaDescriptionCompatApi21.e(r6)
            r1.setIconBitmap(r2)
            android.net.Uri r2 = android.support.v4.media.MediaDescriptionCompatApi21.f(r6)
            r1.setIconUri(r2)
            android.os.Bundle r2 = android.support.v4.media.MediaDescriptionCompatApi21.g(r6)
            if (r2 != 0) goto L_0x0040
            r3 = r0
            goto L_0x0048
        L_0x0040:
            java.lang.String r3 = "android.support.v4.media.description.MEDIA_URI"
            android.os.Parcelable r3 = r2.getParcelable(r3)
            android.net.Uri r3 = (android.net.Uri) r3
        L_0x0048:
            if (r3 == 0) goto L_0x0064
            java.lang.String r4 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            boolean r4 = r2.containsKey(r4)
            if (r4 == 0) goto L_0x005a
            int r4 = r2.size()
            r5 = 2
            if (r4 != r5) goto L_0x005a
            goto L_0x0065
        L_0x005a:
            java.lang.String r0 = "android.support.v4.media.description.MEDIA_URI"
            r2.remove(r0)
            java.lang.String r0 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            r2.remove(r0)
        L_0x0064:
            r0 = r2
        L_0x0065:
            r1.setExtras(r0)
            if (r3 == 0) goto L_0x006e
            r1.setMediaUri(r3)
            goto L_0x007b
        L_0x006e:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            if (r0 < r2) goto L_0x007b
            android.net.Uri r0 = android.support.v4.media.MediaDescriptionCompatApi23.h(r6)
            r1.setMediaUri(r0)
        L_0x007b:
            android.support.v4.media.MediaDescriptionCompat r0 = r1.build()
            r0.i = r6
            return r0
        L_0x0082:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaDescriptionCompat.fromMediaDescription(java.lang.Object):android.support.v4.media.MediaDescriptionCompat");
    }
}
