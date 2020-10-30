package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import java.util.List;

@RequiresApi(26)
class MediaBrowserCompatApi26 {

    interface SubscriptionCallback extends SubscriptionCallback {
        void a(@NonNull String str, @NonNull Bundle bundle);

        void a(@NonNull String str, List<?> list, @NonNull Bundle bundle);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends SubscriptionCallbackProxy<T> {
        SubscriptionCallbackProxy(T t) {
            super(t);
        }

        public void onChildrenLoaded(@NonNull String str, List<MediaItem> list, @NonNull Bundle bundle) {
            ((SubscriptionCallback) this.a).a(str, list, bundle);
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
            ((SubscriptionCallback) this.a).a(str, bundle);
        }
    }

    MediaBrowserCompatApi26() {
    }

    static Object a(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy(subscriptionCallback);
    }

    public static void a(Object obj, String str, Bundle bundle, Object obj2) {
        ((MediaBrowser) obj).subscribe(str, bundle, (android.media.browse.MediaBrowser.SubscriptionCallback) obj2);
    }

    public static void a(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).unsubscribe(str, (android.media.browse.MediaBrowser.SubscriptionCallback) obj2);
    }
}
