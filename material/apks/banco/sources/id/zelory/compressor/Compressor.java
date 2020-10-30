package id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import io.reactivex.Flowable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public class Compressor {
    private int a = 612;
    private int b = 816;
    private CompressFormat c = CompressFormat.JPEG;
    private int d = 80;
    private String e;

    public Compressor(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getCacheDir().getPath());
        sb.append(File.separator);
        sb.append("images");
        this.e = sb.toString();
    }

    public Compressor setMaxWidth(int i) {
        this.a = i;
        return this;
    }

    public Compressor setMaxHeight(int i) {
        this.b = i;
        return this;
    }

    public Compressor setCompressFormat(CompressFormat compressFormat) {
        this.c = compressFormat;
        return this;
    }

    public Compressor setQuality(int i) {
        this.d = i;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String str) {
        this.e = str;
        return this;
    }

    public File compressToFile(File file) {
        return compressToFile(file, file.getName());
    }

    public File compressToFile(File file, String str) {
        int i = this.a;
        int i2 = this.b;
        CompressFormat compressFormat = this.c;
        int i3 = this.d;
        StringBuilder sb = new StringBuilder();
        sb.append(this.e);
        sb.append(File.separator);
        sb.append(str);
        return ImageUtil.a(file, i, i2, compressFormat, i3, sb.toString());
    }

    public Bitmap compressToBitmap(File file) {
        return ImageUtil.a(file, this.a, this.b);
    }

    public Flowable<File> compressToFileAsFlowable(File file) {
        return compressToFileAsFlowable(file, file.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File file, final String str) {
        return Flowable.defer(new Callable<Flowable<File>>() {
            /* renamed from: a */
            public Flowable<File> call() {
                try {
                    return Flowable.just(Compressor.this.compressToFile(file, str));
                } catch (IOException e) {
                    return Flowable.error((Throwable) e);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File file) {
        return Flowable.defer(new Callable<Flowable<Bitmap>>() {
            /* renamed from: a */
            public Flowable<Bitmap> call() {
                try {
                    return Flowable.just(Compressor.this.compressToBitmap(file));
                } catch (IOException e) {
                    return Flowable.error((Throwable) e);
                }
            }
        });
    }
}
