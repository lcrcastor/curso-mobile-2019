package ar.com.santander.rio.mbanking.app.commons.imageloader;

import android.content.Context;

public class ImageLoaderFactory {
    public static ImageLoader getImageLoader(Context context) {
        return new PicassoImageLoader(context);
    }
}
