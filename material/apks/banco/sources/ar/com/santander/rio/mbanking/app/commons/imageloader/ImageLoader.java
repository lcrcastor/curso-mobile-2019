package ar.com.santander.rio.mbanking.app.commons.imageloader;

import android.widget.ImageView;

public interface ImageLoader {
    void loadImage(String str, ImageView imageView);

    void loadImage(String str, ImageView imageView, ImageLoaderCallBack imageLoaderCallBack);
}
