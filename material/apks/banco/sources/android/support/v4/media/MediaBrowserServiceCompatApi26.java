package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.service.media.MediaBrowserService.Result;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(26)
class MediaBrowserServiceCompatApi26 {
    /* access modifiers changed from: private */
    public static Field a;

    static class MediaBrowserServiceAdaptor extends MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            super(context, serviceCompatProxy);
        }

        public void onLoadChildren(String str, Result<List<MediaItem>> result, Bundle bundle) {
            ((ServiceCompatProxy) this.a).onLoadChildren(str, new ResultWrapper(result), bundle);
        }
    }

    static class ResultWrapper {
        Result a;

        ResultWrapper(Result result) {
            this.a = result;
        }

        public void a(List<Parcel> list, int i) {
            try {
                MediaBrowserServiceCompatApi26.a.setInt(this.a, i);
            } catch (IllegalAccessException e) {
                Log.w("MBSCompatApi26", e);
            }
            this.a.sendResult(a(list));
        }

        public void a() {
            this.a.detach();
        }

        /* access modifiers changed from: 0000 */
        public List<MediaItem> a(List<Parcel> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcel parcel : list) {
                parcel.setDataPosition(0);
                arrayList.add(MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return arrayList;
        }
    }

    public interface ServiceCompatProxy extends android.support.v4.media.MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        void onLoadChildren(String str, ResultWrapper resultWrapper, Bundle bundle);
    }

    MediaBrowserServiceCompatApi26() {
    }

    static {
        try {
            a = Result.class.getDeclaredField("mFlags");
            a.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.w("MBSCompatApi26", e);
        }
    }

    public static Object a(Context context, ServiceCompatProxy serviceCompatProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceCompatProxy);
    }

    public static void a(Object obj, String str, Bundle bundle) {
        ((MediaBrowserService) obj).notifyChildrenChanged(str, bundle);
    }

    public static Bundle a(Object obj) {
        return ((MediaBrowserService) obj).getBrowserRootHints();
    }
}
