package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import ar.com.santander.rio.mbanking.utils.ImageSurfaceView;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;

class MediaStoreRequestHandler extends ContentStreamRequestHandler {
    private static final String[] b = {ImageSurfaceView.ORIENTATION_PARAMETER};

    enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);
        
        final int d;
        final int e;
        final int f;

        private PicassoKind(int i, int i2, int i3) {
            this.d = i;
            this.e = i2;
            this.f = i3;
        }
    }

    MediaStoreRequestHandler(Context context) {
        super(context);
    }

    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "content".equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    public Result load(Request request, int i) {
        Bitmap bitmap;
        Request request2 = request;
        ContentResolver contentResolver = this.a.getContentResolver();
        int a = a(contentResolver, request2.uri);
        String type = contentResolver.getType(request2.uri);
        boolean z = type != null && type.startsWith("video/");
        if (request.hasSize()) {
            PicassoKind a2 = a(request2.targetWidth, request2.targetHeight);
            if (!z && a2 == PicassoKind.FULL) {
                return new Result(null, a(request), LoadedFrom.DISK, a);
            }
            long parseId = ContentUris.parseId(request2.uri);
            Options b2 = b(request);
            b2.inJustDecodeBounds = true;
            Options options = b2;
            a(request2.targetWidth, request2.targetHeight, a2.e, a2.f, b2, request2);
            if (z) {
                bitmap = Thumbnails.getThumbnail(contentResolver, parseId, a2 == PicassoKind.FULL ? 1 : a2.d, options);
            } else {
                bitmap = Images.Thumbnails.getThumbnail(contentResolver, parseId, a2.d, options);
            }
            if (bitmap != null) {
                return new Result(bitmap, null, LoadedFrom.DISK, a);
            }
        }
        return new Result(null, a(request), LoadedFrom.DISK, a);
    }

    static PicassoKind a(int i, int i2) {
        if (i <= PicassoKind.MICRO.e && i2 <= PicassoKind.MICRO.f) {
            return PicassoKind.MICRO;
        }
        if (i > PicassoKind.MINI.e || i2 > PicassoKind.MINI.f) {
            return PicassoKind.FULL;
        }
        return PicassoKind.MINI;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int a(android.content.ContentResolver r8, android.net.Uri r9) {
        /*
            r0 = 0
            r1 = 0
            java.lang.String[] r4 = b     // Catch:{ RuntimeException -> 0x0030, all -> 0x0028 }
            r5 = 0
            r6 = 0
            r7 = 0
            r2 = r8
            r3 = r9
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ RuntimeException -> 0x0030, all -> 0x0028 }
            if (r8 == 0) goto L_0x0022
            boolean r9 = r8.moveToFirst()     // Catch:{ RuntimeException -> 0x0031, all -> 0x0020 }
            if (r9 != 0) goto L_0x0016
            goto L_0x0022
        L_0x0016:
            int r9 = r8.getInt(r0)     // Catch:{ RuntimeException -> 0x0031, all -> 0x0020 }
            if (r8 == 0) goto L_0x001f
            r8.close()
        L_0x001f:
            return r9
        L_0x0020:
            r9 = move-exception
            goto L_0x002a
        L_0x0022:
            if (r8 == 0) goto L_0x0027
            r8.close()
        L_0x0027:
            return r0
        L_0x0028:
            r9 = move-exception
            r8 = r1
        L_0x002a:
            if (r8 == 0) goto L_0x002f
            r8.close()
        L_0x002f:
            throw r9
        L_0x0030:
            r8 = r1
        L_0x0031:
            if (r8 == 0) goto L_0x0036
            r8.close()
        L_0x0036:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.MediaStoreRequestHandler.a(android.content.ContentResolver, android.net.Uri):int");
    }
}
