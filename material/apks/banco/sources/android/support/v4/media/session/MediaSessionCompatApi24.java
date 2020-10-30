package android.support.v4.media.session;

import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

@RequiresApi(24)
class MediaSessionCompatApi24 {

    public interface Callback extends android.support.v4.media.session.MediaSessionCompatApi23.Callback {
        void onPrepare();

        void onPrepareFromMediaId(String str, Bundle bundle);

        void onPrepareFromSearch(String str, Bundle bundle);

        void onPrepareFromUri(Uri uri, Bundle bundle);
    }

    static class CallbackProxy<T extends Callback> extends CallbackProxy<T> {
        public CallbackProxy(T t) {
            super(t);
        }

        public void onPrepare() {
            ((Callback) this.a).onPrepare();
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
            ((Callback) this.a).onPrepareFromMediaId(str, bundle);
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
            ((Callback) this.a).onPrepareFromSearch(str, bundle);
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
            ((Callback) this.a).onPrepareFromUri(uri, bundle);
        }
    }

    MediaSessionCompatApi24() {
    }

    public static Object a(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static String a(Object obj) {
        MediaSession mediaSession = (MediaSession) obj;
        try {
            return (String) mediaSession.getClass().getMethod("getCallingPackage", new Class[0]).invoke(mediaSession, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("MediaSessionCompatApi24", "Cannot execute MediaSession.getCallingPackage()", e);
            return null;
        }
    }
}
