package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Parcel;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
class MediaMetadataCompatApi21 {

    public static class Builder {
        public static Object newInstance() {
            return new android.media.MediaMetadata.Builder();
        }

        public static void putBitmap(Object obj, String str, Bitmap bitmap) {
            ((android.media.MediaMetadata.Builder) obj).putBitmap(str, bitmap);
        }

        public static void putLong(Object obj, String str, long j) {
            ((android.media.MediaMetadata.Builder) obj).putLong(str, j);
        }

        public static void putRating(Object obj, String str, Object obj2) {
            ((android.media.MediaMetadata.Builder) obj).putRating(str, (Rating) obj2);
        }

        public static void putText(Object obj, String str, CharSequence charSequence) {
            ((android.media.MediaMetadata.Builder) obj).putText(str, charSequence);
        }

        public static void putString(Object obj, String str, String str2) {
            ((android.media.MediaMetadata.Builder) obj).putString(str, str2);
        }

        public static Object build(Object obj) {
            return ((android.media.MediaMetadata.Builder) obj).build();
        }
    }

    MediaMetadataCompatApi21() {
    }

    public static void a(Object obj, Parcel parcel, int i) {
        ((MediaMetadata) obj).writeToParcel(parcel, i);
    }

    public static Object a(Parcel parcel) {
        return MediaMetadata.CREATOR.createFromParcel(parcel);
    }
}
