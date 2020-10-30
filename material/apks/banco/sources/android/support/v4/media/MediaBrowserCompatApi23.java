package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@RequiresApi(23)
class MediaBrowserCompatApi23 {

    interface ItemCallback {
        void a(Parcel parcel);

        void a(@NonNull String str);
    }

    static class ItemCallbackProxy<T extends ItemCallback> extends android.media.browse.MediaBrowser.ItemCallback {
        protected final T a;

        public ItemCallbackProxy(T t) {
            this.a = t;
        }

        public void onItemLoaded(MediaItem mediaItem) {
            if (mediaItem == null) {
                this.a.a((Parcel) null);
                return;
            }
            Parcel obtain = Parcel.obtain();
            mediaItem.writeToParcel(obtain, 0);
            this.a.a(obtain);
        }

        public void onError(@NonNull String str) {
            this.a.a(str);
        }
    }

    MediaBrowserCompatApi23() {
    }

    public static Object a(ItemCallback itemCallback) {
        return new ItemCallbackProxy(itemCallback);
    }

    public static void a(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).getItem(str, (android.media.browse.MediaBrowser.ItemCallback) obj2);
    }
}
