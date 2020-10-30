package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import java.util.List;

@RequiresApi(21)
class MediaBrowserCompatApi21 {

    interface ConnectionCallback {
        void a();

        void b();

        void c();
    }

    static class ConnectionCallbackProxy<T extends ConnectionCallback> extends android.media.browse.MediaBrowser.ConnectionCallback {
        protected final T a;

        public ConnectionCallbackProxy(T t) {
            this.a = t;
        }

        public void onConnected() {
            this.a.a();
        }

        public void onConnectionSuspended() {
            this.a.b();
        }

        public void onConnectionFailed() {
            this.a.c();
        }
    }

    static class MediaItem {
        MediaItem() {
        }

        public static int a(Object obj) {
            return ((android.media.browse.MediaBrowser.MediaItem) obj).getFlags();
        }

        public static Object b(Object obj) {
            return ((android.media.browse.MediaBrowser.MediaItem) obj).getDescription();
        }
    }

    interface SubscriptionCallback {
        void a(@NonNull String str);

        void a(@NonNull String str, List<?> list);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends android.media.browse.MediaBrowser.SubscriptionCallback {
        protected final T a;

        public SubscriptionCallbackProxy(T t) {
            this.a = t;
        }

        public void onChildrenLoaded(@NonNull String str, List<android.media.browse.MediaBrowser.MediaItem> list) {
            this.a.a(str, list);
        }

        public void onError(@NonNull String str) {
            this.a.a(str);
        }
    }

    MediaBrowserCompatApi21() {
    }

    public static Object a(ConnectionCallback connectionCallback) {
        return new ConnectionCallbackProxy(connectionCallback);
    }

    public static Object a(Context context, ComponentName componentName, Object obj, Bundle bundle) {
        return new MediaBrowser(context, componentName, (android.media.browse.MediaBrowser.ConnectionCallback) obj, bundle);
    }

    public static void a(Object obj) {
        ((MediaBrowser) obj).connect();
    }

    public static void b(Object obj) {
        ((MediaBrowser) obj).disconnect();
    }

    public static boolean c(Object obj) {
        return ((MediaBrowser) obj).isConnected();
    }

    public static ComponentName d(Object obj) {
        return ((MediaBrowser) obj).getServiceComponent();
    }

    public static String e(Object obj) {
        return ((MediaBrowser) obj).getRoot();
    }

    public static Bundle f(Object obj) {
        return ((MediaBrowser) obj).getExtras();
    }

    public static Object g(Object obj) {
        return ((MediaBrowser) obj).getSessionToken();
    }

    public static Object a(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy(subscriptionCallback);
    }

    public static void a(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).subscribe(str, (android.media.browse.MediaBrowser.SubscriptionCallback) obj2);
    }

    public static void a(Object obj, String str) {
        ((MediaBrowser) obj).unsubscribe(str);
    }
}
