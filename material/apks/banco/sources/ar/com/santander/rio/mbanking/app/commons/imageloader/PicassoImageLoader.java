package ar.com.santander.rio.mbanking.app.commons.imageloader;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;

public class PicassoImageLoader implements ImageLoader {
    private Picasso a;
    private Context b;

    public PicassoImageLoader(Context context) {
        this.b = context;
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(this.b, 2147483647L);
        Builder builder = new Builder(this.b);
        builder.downloader(okHttpDownloader);
        this.a = builder.build();
    }

    public void loadImage(String str, ImageView imageView) {
        Picasso picasso = this.a;
        Picasso.with(this.b).load(str).into(imageView);
    }

    public void loadImage(String str, ImageView imageView, final ImageLoaderCallBack imageLoaderCallBack) {
        Picasso picasso = this.a;
        Picasso.with(this.b).load(str).into(imageView, new Callback() {
            public void onSuccess() {
                imageLoaderCallBack.onSuccess();
            }

            public void onError() {
                imageLoaderCallBack.onError();
            }
        });
    }
}
