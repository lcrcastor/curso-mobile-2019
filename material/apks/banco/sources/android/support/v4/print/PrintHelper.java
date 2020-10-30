package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.FileNotFoundException;

public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    private final PrintHelperVersionImpl a;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    @RequiresApi(19)
    static class PrintHelperApi19 implements PrintHelperVersionImpl {
        final Context a;
        Options b = null;
        protected boolean c = true;
        protected boolean d = true;
        int e = 2;
        int f = 2;
        int g;
        /* access modifiers changed from: private */
        public final Object h = new Object();

        PrintHelperApi19(Context context) {
            this.a = context;
        }

        public void a(int i) {
            this.e = i;
        }

        public int a() {
            return this.e;
        }

        public void b(int i) {
            this.f = i;
        }

        public void c(int i) {
            this.g = i;
        }

        public int b() {
            if (this.g == 0) {
                return 1;
            }
            return this.g;
        }

        public int c() {
            return this.f;
        }

        /* access modifiers changed from: private */
        public static boolean b(Bitmap bitmap) {
            return bitmap.getWidth() <= bitmap.getHeight();
        }

        /* access modifiers changed from: protected */
        public Builder a(PrintAttributes printAttributes) {
            Builder minMargins = new Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins());
            if (printAttributes.getColorMode() != 0) {
                minMargins.setColorMode(printAttributes.getColorMode());
            }
            return minMargins;
        }

        public void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            MediaSize mediaSize;
            if (bitmap != null) {
                final int i = this.e;
                PrintManager printManager = (PrintManager) this.a.getSystemService("print");
                if (b(bitmap)) {
                    mediaSize = MediaSize.UNKNOWN_PORTRAIT;
                } else {
                    mediaSize = MediaSize.UNKNOWN_LANDSCAPE;
                }
                PrintAttributes build = new Builder().setMediaSize(mediaSize).setColorMode(this.f).build();
                final String str2 = str;
                final Bitmap bitmap2 = bitmap;
                final OnPrintFinishCallback onPrintFinishCallback2 = onPrintFinishCallback;
                AnonymousClass1 r0 = new PrintDocumentAdapter() {
                    private PrintAttributes f;

                    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                        this.f = printAttributes2;
                        layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(str2).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
                    }

                    public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
                        PrintHelperApi19.this.a(this.f, i, bitmap2, parcelFileDescriptor, cancellationSignal, writeResultCallback);
                    }

                    public void onFinish() {
                        if (onPrintFinishCallback2 != null) {
                            onPrintFinishCallback2.onFinish();
                        }
                    }
                };
                printManager.print(str, r0, build);
            }
        }

        /* access modifiers changed from: private */
        public Matrix a(int i, int i2, RectF rectF, int i3) {
            float f2;
            Matrix matrix = new Matrix();
            float f3 = (float) i;
            float width = rectF.width() / f3;
            if (i3 == 2) {
                f2 = Math.max(width, rectF.height() / ((float) i2));
            } else {
                f2 = Math.min(width, rectF.height() / ((float) i2));
            }
            matrix.postScale(f2, f2);
            matrix.postTranslate((rectF.width() - (f3 * f2)) / 2.0f, (rectF.height() - (((float) i2) * f2)) / 2.0f);
            return matrix;
        }

        /* access modifiers changed from: private */
        public void a(PrintAttributes printAttributes, int i, Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            final PrintAttributes printAttributes2;
            if (this.d) {
                printAttributes2 = printAttributes;
            } else {
                printAttributes2 = a(printAttributes).setMinMargins(new Margins(0, 0, 0, 0)).build();
            }
            final CancellationSignal cancellationSignal2 = cancellationSignal;
            final Bitmap bitmap2 = bitmap;
            final PrintAttributes printAttributes3 = printAttributes;
            final int i2 = i;
            final ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor;
            final WriteResultCallback writeResultCallback2 = writeResultCallback;
            AnonymousClass2 r0 = new AsyncTask<Void, Void, Throwable>() {
                /* access modifiers changed from: protected */
                /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
                /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x00ab */
                /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x00cd */
                /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x00e2 */
                /* JADX WARNING: Removed duplicated region for block: B:27:0x00af A[Catch:{ all -> 0x00d5, Throwable -> 0x00ea }] */
                /* JADX WARNING: Removed duplicated region for block: B:39:0x00d1 A[Catch:{ all -> 0x00d5, Throwable -> 0x00ea }] */
                /* JADX WARNING: Removed duplicated region for block: B:49:0x00e6 A[Catch:{ all -> 0x00d5, Throwable -> 0x00ea }] */
                /* renamed from: a */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public java.lang.Throwable doInBackground(java.lang.Void... r9) {
                    /*
                        r8 = this;
                        android.os.CancellationSignal r9 = r2     // Catch:{ Throwable -> 0x00ea }
                        boolean r9 = r9.isCanceled()     // Catch:{ Throwable -> 0x00ea }
                        r0 = 0
                        if (r9 == 0) goto L_0x000a
                        return r0
                    L_0x000a:
                        android.print.pdf.PrintedPdfDocument r9 = new android.print.pdf.PrintedPdfDocument     // Catch:{ Throwable -> 0x00ea }
                        android.support.v4.print.PrintHelper$PrintHelperApi19 r1 = android.support.v4.print.PrintHelper.PrintHelperApi19.this     // Catch:{ Throwable -> 0x00ea }
                        android.content.Context r1 = r1.a     // Catch:{ Throwable -> 0x00ea }
                        android.print.PrintAttributes r2 = r3     // Catch:{ Throwable -> 0x00ea }
                        r9.<init>(r1, r2)     // Catch:{ Throwable -> 0x00ea }
                        android.support.v4.print.PrintHelper$PrintHelperApi19 r1 = android.support.v4.print.PrintHelper.PrintHelperApi19.this     // Catch:{ Throwable -> 0x00ea }
                        android.graphics.Bitmap r2 = r4     // Catch:{ Throwable -> 0x00ea }
                        android.print.PrintAttributes r3 = r3     // Catch:{ Throwable -> 0x00ea }
                        int r3 = r3.getColorMode()     // Catch:{ Throwable -> 0x00ea }
                        android.graphics.Bitmap r1 = r1.a(r2, r3)     // Catch:{ Throwable -> 0x00ea }
                        android.os.CancellationSignal r2 = r2     // Catch:{ Throwable -> 0x00ea }
                        boolean r2 = r2.isCanceled()     // Catch:{ Throwable -> 0x00ea }
                        if (r2 == 0) goto L_0x002c
                        return r0
                    L_0x002c:
                        r2 = 1
                        android.graphics.pdf.PdfDocument$Page r3 = r9.startPage(r2)     // Catch:{ all -> 0x00d5 }
                        android.support.v4.print.PrintHelper$PrintHelperApi19 r4 = android.support.v4.print.PrintHelper.PrintHelperApi19.this     // Catch:{ all -> 0x00d5 }
                        boolean r4 = r4.d     // Catch:{ all -> 0x00d5 }
                        if (r4 == 0) goto L_0x0045
                        android.graphics.RectF r2 = new android.graphics.RectF     // Catch:{ all -> 0x00d5 }
                        android.graphics.pdf.PdfDocument$PageInfo r4 = r3.getInfo()     // Catch:{ all -> 0x00d5 }
                        android.graphics.Rect r4 = r4.getContentRect()     // Catch:{ all -> 0x00d5 }
                        r2.<init>(r4)     // Catch:{ all -> 0x00d5 }
                        goto L_0x0068
                    L_0x0045:
                        android.print.pdf.PrintedPdfDocument r4 = new android.print.pdf.PrintedPdfDocument     // Catch:{ all -> 0x00d5 }
                        android.support.v4.print.PrintHelper$PrintHelperApi19 r5 = android.support.v4.print.PrintHelper.PrintHelperApi19.this     // Catch:{ all -> 0x00d5 }
                        android.content.Context r5 = r5.a     // Catch:{ all -> 0x00d5 }
                        android.print.PrintAttributes r6 = r5     // Catch:{ all -> 0x00d5 }
                        r4.<init>(r5, r6)     // Catch:{ all -> 0x00d5 }
                        android.graphics.pdf.PdfDocument$Page r2 = r4.startPage(r2)     // Catch:{ all -> 0x00d5 }
                        android.graphics.RectF r5 = new android.graphics.RectF     // Catch:{ all -> 0x00d5 }
                        android.graphics.pdf.PdfDocument$PageInfo r6 = r2.getInfo()     // Catch:{ all -> 0x00d5 }
                        android.graphics.Rect r6 = r6.getContentRect()     // Catch:{ all -> 0x00d5 }
                        r5.<init>(r6)     // Catch:{ all -> 0x00d5 }
                        r4.finishPage(r2)     // Catch:{ all -> 0x00d5 }
                        r4.close()     // Catch:{ all -> 0x00d5 }
                        r2 = r5
                    L_0x0068:
                        android.support.v4.print.PrintHelper$PrintHelperApi19 r4 = android.support.v4.print.PrintHelper.PrintHelperApi19.this     // Catch:{ all -> 0x00d5 }
                        int r5 = r1.getWidth()     // Catch:{ all -> 0x00d5 }
                        int r6 = r1.getHeight()     // Catch:{ all -> 0x00d5 }
                        int r7 = r6     // Catch:{ all -> 0x00d5 }
                        android.graphics.Matrix r4 = r4.a(r5, r6, r2, r7)     // Catch:{ all -> 0x00d5 }
                        android.support.v4.print.PrintHelper$PrintHelperApi19 r5 = android.support.v4.print.PrintHelper.PrintHelperApi19.this     // Catch:{ all -> 0x00d5 }
                        boolean r5 = r5.d     // Catch:{ all -> 0x00d5 }
                        if (r5 == 0) goto L_0x007f
                        goto L_0x008d
                    L_0x007f:
                        float r5 = r2.left     // Catch:{ all -> 0x00d5 }
                        float r6 = r2.top     // Catch:{ all -> 0x00d5 }
                        r4.postTranslate(r5, r6)     // Catch:{ all -> 0x00d5 }
                        android.graphics.Canvas r5 = r3.getCanvas()     // Catch:{ all -> 0x00d5 }
                        r5.clipRect(r2)     // Catch:{ all -> 0x00d5 }
                    L_0x008d:
                        android.graphics.Canvas r2 = r3.getCanvas()     // Catch:{ all -> 0x00d5 }
                        r2.drawBitmap(r1, r4, r0)     // Catch:{ all -> 0x00d5 }
                        r9.finishPage(r3)     // Catch:{ all -> 0x00d5 }
                        android.os.CancellationSignal r2 = r2     // Catch:{ all -> 0x00d5 }
                        boolean r2 = r2.isCanceled()     // Catch:{ all -> 0x00d5 }
                        if (r2 == 0) goto L_0x00b3
                        r9.close()     // Catch:{ Throwable -> 0x00ea }
                        android.os.ParcelFileDescriptor r9 = r7     // Catch:{ Throwable -> 0x00ea }
                        if (r9 == 0) goto L_0x00ab
                        android.os.ParcelFileDescriptor r9 = r7     // Catch:{ IOException -> 0x00ab }
                        r9.close()     // Catch:{ IOException -> 0x00ab }
                    L_0x00ab:
                        android.graphics.Bitmap r9 = r4     // Catch:{ Throwable -> 0x00ea }
                        if (r1 == r9) goto L_0x00b2
                        r1.recycle()     // Catch:{ Throwable -> 0x00ea }
                    L_0x00b2:
                        return r0
                    L_0x00b3:
                        java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x00d5 }
                        android.os.ParcelFileDescriptor r3 = r7     // Catch:{ all -> 0x00d5 }
                        java.io.FileDescriptor r3 = r3.getFileDescriptor()     // Catch:{ all -> 0x00d5 }
                        r2.<init>(r3)     // Catch:{ all -> 0x00d5 }
                        r9.writeTo(r2)     // Catch:{ all -> 0x00d5 }
                        r9.close()     // Catch:{ Throwable -> 0x00ea }
                        android.os.ParcelFileDescriptor r9 = r7     // Catch:{ Throwable -> 0x00ea }
                        if (r9 == 0) goto L_0x00cd
                        android.os.ParcelFileDescriptor r9 = r7     // Catch:{ IOException -> 0x00cd }
                        r9.close()     // Catch:{ IOException -> 0x00cd }
                    L_0x00cd:
                        android.graphics.Bitmap r9 = r4     // Catch:{ Throwable -> 0x00ea }
                        if (r1 == r9) goto L_0x00d4
                        r1.recycle()     // Catch:{ Throwable -> 0x00ea }
                    L_0x00d4:
                        return r0
                    L_0x00d5:
                        r0 = move-exception
                        r9.close()     // Catch:{ Throwable -> 0x00ea }
                        android.os.ParcelFileDescriptor r9 = r7     // Catch:{ Throwable -> 0x00ea }
                        if (r9 == 0) goto L_0x00e2
                        android.os.ParcelFileDescriptor r9 = r7     // Catch:{ IOException -> 0x00e2 }
                        r9.close()     // Catch:{ IOException -> 0x00e2 }
                    L_0x00e2:
                        android.graphics.Bitmap r9 = r4     // Catch:{ Throwable -> 0x00ea }
                        if (r1 == r9) goto L_0x00e9
                        r1.recycle()     // Catch:{ Throwable -> 0x00ea }
                    L_0x00e9:
                        throw r0     // Catch:{ Throwable -> 0x00ea }
                    L_0x00ea:
                        r9 = move-exception
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.support.v4.print.PrintHelper.PrintHelperApi19.AnonymousClass2.doInBackground(java.lang.Void[]):java.lang.Throwable");
                }

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public void onPostExecute(Throwable th) {
                    if (cancellationSignal2.isCanceled()) {
                        writeResultCallback2.onWriteCancelled();
                    } else if (th == null) {
                        writeResultCallback2.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                    } else {
                        Log.e("PrintHelperApi19", "Error writing printed content", th);
                        writeResultCallback2.onWriteFailed(null);
                    }
                }
            };
            r0.execute(new Void[0]);
        }

        public void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
            final int i = this.e;
            final String str2 = str;
            final Uri uri2 = uri;
            final OnPrintFinishCallback onPrintFinishCallback2 = onPrintFinishCallback;
            AnonymousClass3 r0 = new PrintDocumentAdapter() {
                AsyncTask<Uri, Boolean, Bitmap> a;
                Bitmap b = null;
                /* access modifiers changed from: private */
                public PrintAttributes h;

                public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                    synchronized (this) {
                        this.h = printAttributes2;
                    }
                    if (cancellationSignal.isCanceled()) {
                        layoutResultCallback.onLayoutCancelled();
                    } else if (this.b != null) {
                        layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(str2).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
                    } else {
                        final CancellationSignal cancellationSignal2 = cancellationSignal;
                        final PrintAttributes printAttributes3 = printAttributes2;
                        final PrintAttributes printAttributes4 = printAttributes;
                        final LayoutResultCallback layoutResultCallback2 = layoutResultCallback;
                        AnonymousClass1 r0 = new AsyncTask<Uri, Boolean, Bitmap>() {
                            /* access modifiers changed from: protected */
                            public void onPreExecute() {
                                cancellationSignal2.setOnCancelListener(new OnCancelListener() {
                                    public void onCancel() {
                                        AnonymousClass3.this.a();
                                        AnonymousClass1.this.cancel(false);
                                    }
                                });
                            }

                            /* access modifiers changed from: protected */
                            /* renamed from: a */
                            public Bitmap doInBackground(Uri... uriArr) {
                                try {
                                    return PrintHelperApi19.this.a(uri2);
                                } catch (FileNotFoundException unused) {
                                    return null;
                                }
                            }

                            /* access modifiers changed from: protected */
                            /* renamed from: a */
                            public void onPostExecute(Bitmap bitmap) {
                                MediaSize mediaSize;
                                super.onPostExecute(bitmap);
                                if (bitmap != null && (!PrintHelperApi19.this.c || PrintHelperApi19.this.g == 0)) {
                                    synchronized (this) {
                                        mediaSize = AnonymousClass3.this.h.getMediaSize();
                                    }
                                    if (!(mediaSize == null || mediaSize.isPortrait() == PrintHelperApi19.b(bitmap))) {
                                        Matrix matrix = new Matrix();
                                        matrix.postRotate(90.0f);
                                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                                    }
                                }
                                AnonymousClass3.this.b = bitmap;
                                if (bitmap != null) {
                                    layoutResultCallback2.onLayoutFinished(new PrintDocumentInfo.Builder(str2).setContentType(1).setPageCount(1).build(), true ^ printAttributes3.equals(printAttributes4));
                                } else {
                                    layoutResultCallback2.onLayoutFailed(null);
                                }
                                AnonymousClass3.this.a = null;
                            }

                            /* access modifiers changed from: protected */
                            /* renamed from: b */
                            public void onCancelled(Bitmap bitmap) {
                                layoutResultCallback2.onLayoutCancelled();
                                AnonymousClass3.this.a = null;
                            }
                        };
                        this.a = r0.execute(new Uri[0]);
                    }
                }

                /* access modifiers changed from: private */
                public void a() {
                    synchronized (PrintHelperApi19.this.h) {
                        if (PrintHelperApi19.this.b != null) {
                            PrintHelperApi19.this.b.requestCancelDecode();
                            PrintHelperApi19.this.b = null;
                        }
                    }
                }

                public void onFinish() {
                    super.onFinish();
                    a();
                    if (this.a != null) {
                        this.a.cancel(true);
                    }
                    if (onPrintFinishCallback2 != null) {
                        onPrintFinishCallback2.onFinish();
                    }
                    if (this.b != null) {
                        this.b.recycle();
                        this.b = null;
                    }
                }

                public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
                    PrintHelperApi19.this.a(this.h, i, this.b, parcelFileDescriptor, cancellationSignal, writeResultCallback);
                }
            };
            PrintManager printManager = (PrintManager) this.a.getSystemService("print");
            Builder builder = new Builder();
            builder.setColorMode(this.f);
            if (this.g == 1 || this.g == 0) {
                builder.setMediaSize(MediaSize.UNKNOWN_LANDSCAPE);
            } else if (this.g == 2) {
                builder.setMediaSize(MediaSize.UNKNOWN_PORTRAIT);
            }
            printManager.print(str, r0, builder.build());
        }

        /* access modifiers changed from: private */
        public Bitmap a(Uri uri) {
            Options options;
            if (uri == null || this.a == null) {
                throw new IllegalArgumentException("bad argument to getScaledBitmap");
            }
            Options options2 = new Options();
            options2.inJustDecodeBounds = true;
            a(uri, options2);
            int i = options2.outWidth;
            int i2 = options2.outHeight;
            if (i <= 0 || i2 <= 0) {
                return null;
            }
            int max = Math.max(i, i2);
            int i3 = 1;
            while (max > 3500) {
                max >>>= 1;
                i3 <<= 1;
            }
            if (i3 <= 0 || Math.min(i, i2) / i3 <= 0) {
                return null;
            }
            synchronized (this.h) {
                this.b = new Options();
                this.b.inMutable = true;
                this.b.inSampleSize = i3;
                options = this.b;
            }
            try {
                Bitmap a2 = a(uri, options);
                synchronized (this.h) {
                    this.b = null;
                }
                return a2;
            } catch (Throwable th) {
                synchronized (this.h) {
                    this.b = null;
                    throw th;
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x002b A[SYNTHETIC, Splitter:B:18:0x002b] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.graphics.Bitmap a(android.net.Uri r3, android.graphics.BitmapFactory.Options r4) {
            /*
                r2 = this;
                if (r3 == 0) goto L_0x0038
                android.content.Context r0 = r2.a
                if (r0 != 0) goto L_0x0007
                goto L_0x0038
            L_0x0007:
                r0 = 0
                android.content.Context r1 = r2.a     // Catch:{ all -> 0x0028 }
                android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ all -> 0x0028 }
                java.io.InputStream r3 = r1.openInputStream(r3)     // Catch:{ all -> 0x0028 }
                android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r3, r0, r4)     // Catch:{ all -> 0x0025 }
                if (r3 == 0) goto L_0x0024
                r3.close()     // Catch:{ IOException -> 0x001c }
                goto L_0x0024
            L_0x001c:
                r3 = move-exception
                java.lang.String r0 = "PrintHelperApi19"
                java.lang.String r1 = "close fail "
                android.util.Log.w(r0, r1, r3)
            L_0x0024:
                return r4
            L_0x0025:
                r4 = move-exception
                r0 = r3
                goto L_0x0029
            L_0x0028:
                r4 = move-exception
            L_0x0029:
                if (r0 == 0) goto L_0x0037
                r0.close()     // Catch:{ IOException -> 0x002f }
                goto L_0x0037
            L_0x002f:
                r3 = move-exception
                java.lang.String r0 = "PrintHelperApi19"
                java.lang.String r1 = "close fail "
                android.util.Log.w(r0, r1, r3)
            L_0x0037:
                throw r4
            L_0x0038:
                java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
                java.lang.String r4 = "bad argument to loadBitmap"
                r3.<init>(r4)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.print.PrintHelper.PrintHelperApi19.a(android.net.Uri, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
        }

        /* access modifiers changed from: private */
        public Bitmap a(Bitmap bitmap, int i) {
            if (i != 1) {
                return bitmap;
            }
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(BitmapDescriptorFactory.HUE_RED);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(bitmap, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, paint);
            canvas.setBitmap(null);
            return createBitmap;
        }
    }

    @RequiresApi(20)
    static class PrintHelperApi20 extends PrintHelperApi19 {
        PrintHelperApi20(Context context) {
            super(context);
            this.c = false;
        }
    }

    @RequiresApi(23)
    static class PrintHelperApi23 extends PrintHelperApi20 {
        /* access modifiers changed from: protected */
        public Builder a(PrintAttributes printAttributes) {
            Builder a = super.a(printAttributes);
            if (printAttributes.getDuplexMode() != 0) {
                a.setDuplexMode(printAttributes.getDuplexMode());
            }
            return a;
        }

        PrintHelperApi23(Context context) {
            super(context);
            this.d = false;
        }
    }

    @RequiresApi(24)
    static class PrintHelperApi24 extends PrintHelperApi23 {
        PrintHelperApi24(Context context) {
            super(context);
            this.d = true;
            this.c = true;
        }
    }

    static final class PrintHelperStub implements PrintHelperVersionImpl {
        int a;
        int b;
        int c;

        public void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        }

        public void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
        }

        private PrintHelperStub() {
            this.a = 2;
            this.b = 2;
            this.c = 1;
        }

        public void a(int i) {
            this.a = i;
        }

        public int a() {
            return this.a;
        }

        public int c() {
            return this.b;
        }

        public void b(int i) {
            this.b = i;
        }

        public void c(int i) {
            this.c = i;
        }

        public int b() {
            return this.c;
        }
    }

    interface PrintHelperVersionImpl {
        int a();

        void a(int i);

        void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback);

        void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback);

        int b();

        void b(int i);

        int c();

        void c(int i);
    }

    public static boolean systemSupportsPrint() {
        return VERSION.SDK_INT >= 19;
    }

    public PrintHelper(Context context) {
        if (VERSION.SDK_INT >= 24) {
            this.a = new PrintHelperApi24(context);
        } else if (VERSION.SDK_INT >= 23) {
            this.a = new PrintHelperApi23(context);
        } else if (VERSION.SDK_INT >= 20) {
            this.a = new PrintHelperApi20(context);
        } else if (VERSION.SDK_INT >= 19) {
            this.a = new PrintHelperApi19(context);
        } else {
            this.a = new PrintHelperStub();
        }
    }

    public void setScaleMode(int i) {
        this.a.a(i);
    }

    public int getScaleMode() {
        return this.a.a();
    }

    public void setColorMode(int i) {
        this.a.b(i);
    }

    public int getColorMode() {
        return this.a.c();
    }

    public void setOrientation(int i) {
        this.a.c(i);
    }

    public int getOrientation() {
        return this.a.b();
    }

    public void printBitmap(String str, Bitmap bitmap) {
        this.a.a(str, bitmap, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        this.a.a(str, bitmap, onPrintFinishCallback);
    }

    public void printBitmap(String str, Uri uri) {
        this.a.a(str, uri, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
        this.a.a(str, uri, onPrintFinishCallback);
    }
}
