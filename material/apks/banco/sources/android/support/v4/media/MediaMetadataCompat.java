package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public final class MediaMetadataCompat implements Parcelable {
    public static final Creator<MediaMetadataCompat> CREATOR = new Creator<MediaMetadataCompat>() {
        /* renamed from: a */
        public MediaMetadataCompat createFromParcel(Parcel parcel) {
            return new MediaMetadataCompat(parcel);
        }

        /* renamed from: a */
        public MediaMetadataCompat[] newArray(int i) {
            return new MediaMetadataCompat[i];
        }
    };
    public static final String METADATA_KEY_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT";
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DOWNLOAD_STATUS = "android.media.metadata.DOWNLOAD_STATUS";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    static final ArrayMap<String, Integer> a = new ArrayMap<>();
    private static final String[] c = {METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
    private static final String[] d = {METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
    private static final String[] e = {METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
    final Bundle b;
    private Object f;
    private MediaDescriptionCompat g;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BitmapKey {
    }

    public static final class Builder {
        private final Bundle a;

        public Builder() {
            this.a = new Bundle();
        }

        public Builder(MediaMetadataCompat mediaMetadataCompat) {
            this.a = new Bundle(mediaMetadataCompat.b);
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder(MediaMetadataCompat mediaMetadataCompat, int i) {
            this(mediaMetadataCompat);
            for (String str : this.a.keySet()) {
                Object obj = this.a.get(str);
                if (obj instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) obj;
                    if (bitmap.getHeight() > i || bitmap.getWidth() > i) {
                        putBitmap(str, a(bitmap, i));
                    }
                }
            }
        }

        public Builder putText(String str, CharSequence charSequence) {
            if (!MediaMetadataCompat.a.containsKey(str) || ((Integer) MediaMetadataCompat.a.get(str)).intValue() == 1) {
                this.a.putCharSequence(str, charSequence);
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The ");
            sb.append(str);
            sb.append(" key cannot be used to put a CharSequence");
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder putString(String str, String str2) {
            if (!MediaMetadataCompat.a.containsKey(str) || ((Integer) MediaMetadataCompat.a.get(str)).intValue() == 1) {
                this.a.putCharSequence(str, str2);
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The ");
            sb.append(str);
            sb.append(" key cannot be used to put a String");
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder putLong(String str, long j) {
            if (!MediaMetadataCompat.a.containsKey(str) || ((Integer) MediaMetadataCompat.a.get(str)).intValue() == 0) {
                this.a.putLong(str, j);
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The ");
            sb.append(str);
            sb.append(" key cannot be used to put a long");
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder putRating(String str, RatingCompat ratingCompat) {
            if (!MediaMetadataCompat.a.containsKey(str) || ((Integer) MediaMetadataCompat.a.get(str)).intValue() == 3) {
                if (VERSION.SDK_INT >= 19) {
                    this.a.putParcelable(str, (Parcelable) ratingCompat.getRating());
                } else {
                    this.a.putParcelable(str, ratingCompat);
                }
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The ");
            sb.append(str);
            sb.append(" key cannot be used to put a Rating");
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder putBitmap(String str, Bitmap bitmap) {
            if (!MediaMetadataCompat.a.containsKey(str) || ((Integer) MediaMetadataCompat.a.get(str)).intValue() == 2) {
                this.a.putParcelable(str, bitmap);
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The ");
            sb.append(str);
            sb.append(" key cannot be used to put a Bitmap");
            throw new IllegalArgumentException(sb.toString());
        }

        public MediaMetadataCompat build() {
            return new MediaMetadataCompat(this.a);
        }

        private Bitmap a(Bitmap bitmap, int i) {
            float f = (float) i;
            float min = Math.min(f / ((float) bitmap.getWidth()), f / ((float) bitmap.getHeight()));
            return Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) * min), (int) (((float) bitmap.getHeight()) * min), true);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LongKey {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RatingKey {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextKey {
    }

    public int describeContents() {
        return 0;
    }

    static {
        a.put(METADATA_KEY_TITLE, Integer.valueOf(1));
        a.put(METADATA_KEY_ARTIST, Integer.valueOf(1));
        a.put(METADATA_KEY_DURATION, Integer.valueOf(0));
        a.put(METADATA_KEY_ALBUM, Integer.valueOf(1));
        a.put(METADATA_KEY_AUTHOR, Integer.valueOf(1));
        a.put(METADATA_KEY_WRITER, Integer.valueOf(1));
        a.put(METADATA_KEY_COMPOSER, Integer.valueOf(1));
        a.put(METADATA_KEY_COMPILATION, Integer.valueOf(1));
        a.put(METADATA_KEY_DATE, Integer.valueOf(1));
        a.put(METADATA_KEY_YEAR, Integer.valueOf(0));
        a.put(METADATA_KEY_GENRE, Integer.valueOf(1));
        a.put(METADATA_KEY_TRACK_NUMBER, Integer.valueOf(0));
        a.put(METADATA_KEY_NUM_TRACKS, Integer.valueOf(0));
        a.put(METADATA_KEY_DISC_NUMBER, Integer.valueOf(0));
        a.put(METADATA_KEY_ALBUM_ARTIST, Integer.valueOf(1));
        a.put(METADATA_KEY_ART, Integer.valueOf(2));
        a.put(METADATA_KEY_ART_URI, Integer.valueOf(1));
        a.put(METADATA_KEY_ALBUM_ART, Integer.valueOf(2));
        a.put(METADATA_KEY_ALBUM_ART_URI, Integer.valueOf(1));
        a.put(METADATA_KEY_USER_RATING, Integer.valueOf(3));
        a.put(METADATA_KEY_RATING, Integer.valueOf(3));
        a.put(METADATA_KEY_DISPLAY_TITLE, Integer.valueOf(1));
        a.put(METADATA_KEY_DISPLAY_SUBTITLE, Integer.valueOf(1));
        a.put(METADATA_KEY_DISPLAY_DESCRIPTION, Integer.valueOf(1));
        a.put(METADATA_KEY_DISPLAY_ICON, Integer.valueOf(2));
        a.put(METADATA_KEY_DISPLAY_ICON_URI, Integer.valueOf(1));
        a.put(METADATA_KEY_MEDIA_ID, Integer.valueOf(1));
        a.put(METADATA_KEY_BT_FOLDER_TYPE, Integer.valueOf(0));
        a.put(METADATA_KEY_MEDIA_URI, Integer.valueOf(1));
        a.put(METADATA_KEY_ADVERTISEMENT, Integer.valueOf(0));
        a.put(METADATA_KEY_DOWNLOAD_STATUS, Integer.valueOf(0));
    }

    MediaMetadataCompat(Bundle bundle) {
        this.b = new Bundle(bundle);
        this.b.setClassLoader(MediaMetadataCompat.class.getClassLoader());
    }

    MediaMetadataCompat(Parcel parcel) {
        this.b = parcel.readBundle();
        this.b.setClassLoader(MediaMetadataCompat.class.getClassLoader());
    }

    public boolean containsKey(String str) {
        return this.b.containsKey(str);
    }

    public CharSequence getText(String str) {
        return this.b.getCharSequence(str);
    }

    public String getString(String str) {
        CharSequence charSequence = this.b.getCharSequence(str);
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    public long getLong(String str) {
        return this.b.getLong(str, 0);
    }

    public RatingCompat getRating(String str) {
        try {
            if (VERSION.SDK_INT >= 19) {
                return RatingCompat.fromRating(this.b.getParcelable(str));
            }
            return (RatingCompat) this.b.getParcelable(str);
        } catch (Exception e2) {
            Log.w("MediaMetadata", "Failed to retrieve a key as Rating.", e2);
            return null;
        }
    }

    public Bitmap getBitmap(String str) {
        try {
            return (Bitmap) this.b.getParcelable(str);
        } catch (Exception e2) {
            Log.w("MediaMetadata", "Failed to retrieve a key as Bitmap.", e2);
            return null;
        }
    }

    public MediaDescriptionCompat getDescription() {
        Uri uri;
        Bitmap bitmap;
        Uri uri2;
        if (this.g != null) {
            return this.g;
        }
        String string = getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] charSequenceArr = new CharSequence[3];
        CharSequence text = getText(METADATA_KEY_DISPLAY_TITLE);
        if (!TextUtils.isEmpty(text)) {
            charSequenceArr[0] = text;
            charSequenceArr[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            charSequenceArr[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        } else {
            int i = 0;
            int i2 = 0;
            while (i < charSequenceArr.length && i2 < c.length) {
                int i3 = i2 + 1;
                CharSequence text2 = getText(c[i2]);
                if (!TextUtils.isEmpty(text2)) {
                    int i4 = i + 1;
                    charSequenceArr[i] = text2;
                    i = i4;
                }
                i2 = i3;
            }
        }
        int i5 = 0;
        while (true) {
            uri = null;
            if (i5 >= d.length) {
                bitmap = null;
                break;
            }
            bitmap = getBitmap(d[i5]);
            if (bitmap != null) {
                break;
            }
            i5++;
        }
        int i6 = 0;
        while (true) {
            if (i6 >= e.length) {
                uri2 = null;
                break;
            }
            String string2 = getString(e[i6]);
            if (!TextUtils.isEmpty(string2)) {
                uri2 = Uri.parse(string2);
                break;
            }
            i6++;
        }
        String string3 = getString(METADATA_KEY_MEDIA_URI);
        if (!TextUtils.isEmpty(string3)) {
            uri = Uri.parse(string3);
        }
        android.support.v4.media.MediaDescriptionCompat.Builder builder = new android.support.v4.media.MediaDescriptionCompat.Builder();
        builder.setMediaId(string);
        builder.setTitle(charSequenceArr[0]);
        builder.setSubtitle(charSequenceArr[1]);
        builder.setDescription(charSequenceArr[2]);
        builder.setIconBitmap(bitmap);
        builder.setIconUri(uri2);
        builder.setMediaUri(uri);
        Bundle bundle = new Bundle();
        if (this.b.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE));
        }
        if (this.b.containsKey(METADATA_KEY_DOWNLOAD_STATUS)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_DOWNLOAD_STATUS, getLong(METADATA_KEY_DOWNLOAD_STATUS));
        }
        if (!bundle.isEmpty()) {
            builder.setExtras(bundle);
        }
        this.g = builder.build();
        return this.g;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.b);
    }

    public int size() {
        return this.b.size();
    }

    public Set<String> keySet() {
        return this.b.keySet();
    }

    public Bundle getBundle() {
        return this.b;
    }

    public static MediaMetadataCompat fromMediaMetadata(Object obj) {
        if (obj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        MediaMetadataCompatApi21.a(obj, obtain, 0);
        obtain.setDataPosition(0);
        MediaMetadataCompat mediaMetadataCompat = (MediaMetadataCompat) CREATOR.createFromParcel(obtain);
        obtain.recycle();
        mediaMetadataCompat.f = obj;
        return mediaMetadataCompat;
    }

    public Object getMediaMetadata() {
        if (this.f == null && VERSION.SDK_INT >= 21) {
            Parcel obtain = Parcel.obtain();
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            this.f = MediaMetadataCompatApi21.a(obtain);
            obtain.recycle();
        }
        return this.f;
    }
}
