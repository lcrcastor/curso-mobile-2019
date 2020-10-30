package ar.com.santander.rio.mbanking.app.ui.utils;

import android.content.Context;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;
import java.util.concurrent.TimeUnit;

public class PicassoCache {
    private static Picasso a;

    private PicassoCache(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(500, TimeUnit.MILLISECONDS);
        a = new Builder(context).downloader(new OkHttpDownloader(okHttpClient)).build();
    }

    public static Picasso getPicassoInstance(Context context, SessionManager sessionManager) {
        if (a != null || sessionManager.getSession().isEmpty()) {
            return a;
        }
        new PicassoCache(context);
        return a;
    }
}
