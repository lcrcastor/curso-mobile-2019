package ar.com.santander.rio.mbanking.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Base64;
import id.zelory.compressor.Compressor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressionUtils {
    private static Bitmap a(Bitmap bitmap, float f, float f2) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        float height = ((float) bitmap.getHeight()) * f2;
        return Bitmap.createBitmap(bitmap, 0, 0, (int) (((float) (bitmap.getWidth() / 100)) * (100.0f / ((float) bitmap.getHeight())) * height), (int) height, matrix, true);
    }

    private static byte[] a(@NonNull Context context, byte[] bArr) {
        try {
            File createTempFile = File.createTempFile("cache", "jpg", context.getCacheDir());
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile.getPath());
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            Bitmap compressToBitmap = new Compressor(context).setMaxWidth(900).setQuality(75).setCompressFormat(CompressFormat.JPEG).setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()).compressToBitmap(createTempFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap a = a(compressToBitmap, 90.0f, 1.0f);
            compressToBitmap.recycle();
            a.compress(CompressFormat.JPEG, 75, byteArrayOutputStream);
            a.recycle();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                createTempFile.delete();
                return byteArray;
            } catch (IOException unused) {
                return byteArray;
            }
        } catch (IOException unused2) {
            return new byte[0];
        }
    }

    private static String a(byte[] bArr) {
        return new String(Base64.encode(bArr, 0));
    }

    public static String compressAndEncodingImageStringBase64(Context context, byte[] bArr) {
        return a(a(context, bArr));
    }
}
