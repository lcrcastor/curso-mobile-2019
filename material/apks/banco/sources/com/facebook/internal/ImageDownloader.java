package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest.Callback;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static Handler a;
    private static WorkQueue b = new WorkQueue(8);
    private static WorkQueue c = new WorkQueue(2);
    private static final Map<RequestKey, DownloaderContext> d = new HashMap();

    static class CacheReadWorkItem implements Runnable {
        private Context a;
        private RequestKey b;
        private boolean c;

        CacheReadWorkItem(Context context, RequestKey requestKey, boolean z) {
            this.a = context;
            this.b = requestKey;
            this.c = z;
        }

        public void run() {
            ImageDownloader.b(this.b, this.a, this.c);
        }
    }

    static class DownloadImageWorkItem implements Runnable {
        private Context a;
        private RequestKey b;

        DownloadImageWorkItem(Context context, RequestKey requestKey) {
            this.a = context;
            this.b = requestKey;
        }

        public void run() {
            ImageDownloader.b(this.b, this.a);
        }
    }

    static class DownloaderContext {
        WorkItem a;
        ImageRequest b;
        boolean c;

        private DownloaderContext() {
        }
    }

    static class RequestKey {
        URI a;
        Object b;

        RequestKey(URI uri, Object obj) {
            this.a = uri;
            this.b = obj;
        }

        public int hashCode() {
            return ((1073 + this.a.hashCode()) * 37) + this.b.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof RequestKey)) {
                return false;
            }
            RequestKey requestKey = (RequestKey) obj;
            if (requestKey.a == this.a && requestKey.b == this.b) {
                return true;
            }
            return false;
        }
    }

    public static void downloadAsync(ImageRequest imageRequest) {
        if (imageRequest != null) {
            RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
            synchronized (d) {
                DownloaderContext downloaderContext = (DownloaderContext) d.get(requestKey);
                if (downloaderContext != null) {
                    downloaderContext.b = imageRequest;
                    downloaderContext.c = false;
                    downloaderContext.a.b();
                } else {
                    a(imageRequest, requestKey, imageRequest.isCachedRedirectAllowed());
                }
            }
        }
    }

    public static boolean cancelRequest(ImageRequest imageRequest) {
        boolean z;
        RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
        synchronized (d) {
            DownloaderContext downloaderContext = (DownloaderContext) d.get(requestKey);
            z = true;
            if (downloaderContext == null) {
                z = false;
            } else if (downloaderContext.a.a()) {
                d.remove(requestKey);
            } else {
                downloaderContext.c = true;
            }
        }
        return z;
    }

    public static void prioritizeRequest(ImageRequest imageRequest) {
        RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
        synchronized (d) {
            DownloaderContext downloaderContext = (DownloaderContext) d.get(requestKey);
            if (downloaderContext != null) {
                downloaderContext.a.b();
            }
        }
    }

    public static void clearCache(Context context) {
        ImageResponseCache.b(context);
        UrlRedirectCache.b(context);
    }

    private static void a(ImageRequest imageRequest, RequestKey requestKey, boolean z) {
        a(imageRequest, requestKey, c, (Runnable) new CacheReadWorkItem(imageRequest.getContext(), requestKey, z));
    }

    private static void a(ImageRequest imageRequest, RequestKey requestKey) {
        a(imageRequest, requestKey, b, (Runnable) new DownloadImageWorkItem(imageRequest.getContext(), requestKey));
    }

    private static void a(ImageRequest imageRequest, RequestKey requestKey, WorkQueue workQueue, Runnable runnable) {
        synchronized (d) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.b = imageRequest;
            d.put(requestKey, downloaderContext);
            downloaderContext.a = workQueue.a(runnable);
        }
    }

    private static void a(RequestKey requestKey, Exception exc, Bitmap bitmap, boolean z) {
        DownloaderContext a2 = a(requestKey);
        if (a2 != null && !a2.c) {
            final ImageRequest imageRequest = a2.b;
            final Callback callback = imageRequest.getCallback();
            if (callback != null) {
                Handler a3 = a();
                final Exception exc2 = exc;
                final boolean z2 = z;
                final Bitmap bitmap2 = bitmap;
                AnonymousClass1 r1 = new Runnable() {
                    public void run() {
                        callback.onCompleted(new ImageResponse(imageRequest, exc2, z2, bitmap2));
                    }
                };
                a3.post(r1);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(com.facebook.internal.ImageDownloader.RequestKey r2, android.content.Context r3, boolean r4) {
        /*
            r0 = 0
            r1 = 0
            if (r4 == 0) goto L_0x0014
            java.net.URI r4 = r2.a
            java.net.URI r4 = com.facebook.internal.UrlRedirectCache.a(r3, r4)
            if (r4 == 0) goto L_0x0014
            java.io.InputStream r4 = com.facebook.internal.ImageResponseCache.a(r4, r3)
            if (r4 == 0) goto L_0x0015
            r0 = 1
            goto L_0x0015
        L_0x0014:
            r4 = r1
        L_0x0015:
            if (r0 != 0) goto L_0x001d
            java.net.URI r4 = r2.a
            java.io.InputStream r4 = com.facebook.internal.ImageResponseCache.a(r4, r3)
        L_0x001d:
            if (r4 == 0) goto L_0x002a
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r4)
            com.facebook.internal.Utility.closeQuietly(r4)
            a(r2, r1, r3, r0)
            goto L_0x0039
        L_0x002a:
            com.facebook.internal.ImageDownloader$DownloaderContext r3 = a(r2)
            if (r3 == 0) goto L_0x0039
            boolean r4 = r3.c
            if (r4 != 0) goto L_0x0039
            com.facebook.internal.ImageRequest r3 = r3.b
            a(r3, r2)
        L_0x0039:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.b(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context, boolean):void");
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.lang.Exception] */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.graphics.Bitmap] */
    /* JADX WARNING: type inference failed for: r10v2 */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r10v3, types: [java.io.IOException] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r10v4, types: [java.net.URISyntaxException] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r10v5, types: [java.io.IOException] */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r10v6, types: [java.net.URISyntaxException] */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r10v7, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r10v8 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r10v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r4v9, types: [java.io.IOException] */
    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r4v11, types: [java.net.URISyntaxException] */
    /* JADX WARNING: type inference failed for: r8v1 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r4v13, types: [android.graphics.Bitmap] */
    /* JADX WARNING: type inference failed for: r10v12, types: [java.io.IOException] */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r10v13, types: [java.net.URISyntaxException] */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r4v17 */
    /* JADX WARNING: type inference failed for: r4v19, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r10v17, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r10v18, types: [com.facebook.FacebookException] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r10v19 */
    /* JADX WARNING: type inference failed for: r4v20 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r10v22 */
    /* JADX WARNING: type inference failed for: r10v23 */
    /* JADX WARNING: type inference failed for: r10v24 */
    /* JADX WARNING: type inference failed for: r10v25 */
    /* JADX WARNING: type inference failed for: r10v26 */
    /* JADX WARNING: type inference failed for: r10v27 */
    /* JADX WARNING: type inference failed for: r4v21 */
    /* JADX WARNING: type inference failed for: r10v28 */
    /* JADX WARNING: type inference failed for: r10v29 */
    /* JADX WARNING: type inference failed for: r4v22 */
    /* JADX WARNING: type inference failed for: r10v30 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bc, code lost:
        r9 = th;
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00be, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00bf, code lost:
        r4 = 0;
        r10 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c1, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c2, code lost:
        r4 = 0;
        r10 = r10;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v2
      assigns: []
      uses: []
      mth insns count: 115
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00bc A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0014] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 24 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(com.facebook.internal.ImageDownloader.RequestKey r9, android.content.Context r10) {
        /*
            r0 = 0
            r1 = 0
            r2 = 1
            java.net.URL r3 = new java.net.URL     // Catch:{ IOException -> 0x00d1, URISyntaxException -> 0x00cd, all -> 0x00c4 }
            java.net.URI r4 = r9.a     // Catch:{ IOException -> 0x00d1, URISyntaxException -> 0x00cd, all -> 0x00c4 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x00d1, URISyntaxException -> 0x00cd, all -> 0x00c4 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00d1, URISyntaxException -> 0x00cd, all -> 0x00c4 }
            java.net.URLConnection r3 = r3.openConnection()     // Catch:{ IOException -> 0x00d1, URISyntaxException -> 0x00cd, all -> 0x00c4 }
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3     // Catch:{ IOException -> 0x00d1, URISyntaxException -> 0x00cd, all -> 0x00c4 }
            r3.setInstanceFollowRedirects(r1)     // Catch:{ IOException -> 0x00c1, URISyntaxException -> 0x00be, all -> 0x00bc }
            int r4 = r3.getResponseCode()     // Catch:{ IOException -> 0x00c1, URISyntaxException -> 0x00be, all -> 0x00bc }
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 == r5) goto L_0x009e
            switch(r4) {
                case 301: goto L_0x0066;
                case 302: goto L_0x0066;
                default: goto L_0x0022;
            }     // Catch:{ IOException -> 0x00c1, URISyntaxException -> 0x00be, all -> 0x00bc }
        L_0x0022:
            java.io.InputStream r4 = r3.getErrorStream()     // Catch:{ IOException -> 0x00c1, URISyntaxException -> 0x00be, all -> 0x00bc }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            r5.<init>()     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            if (r4 == 0) goto L_0x0045
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            r10.<init>(r4)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            r6 = 128(0x80, float:1.794E-43)
            char[] r6 = new char[r6]     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
        L_0x0036:
            int r7 = r6.length     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            int r7 = r10.read(r6, r1, r7)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            if (r7 <= 0) goto L_0x0041
            r5.append(r6, r1, r7)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            goto L_0x0036
        L_0x0041:
            com.facebook.internal.Utility.closeQuietly(r10)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            goto L_0x004e
        L_0x0045:
            int r6 = com.facebook.android.R.string.com_facebook_image_download_unknown_error     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            java.lang.String r10 = r10.getString(r6)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            r5.append(r10)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
        L_0x004e:
            com.facebook.FacebookException r10 = new com.facebook.FacebookException     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            r10.<init>(r5)     // Catch:{ IOException -> 0x0063, URISyntaxException -> 0x0060, all -> 0x005c }
            r8 = r0
            r0 = r10
            r10 = r4
            r4 = r8
            goto L_0x00a6
        L_0x005c:
            r9 = move-exception
            r0 = r4
            goto L_0x00c6
        L_0x0060:
            r10 = move-exception
            goto L_0x00d4
        L_0x0063:
            r10 = move-exception
            goto L_0x00d4
        L_0x0066:
            java.lang.String r2 = "location"
            java.lang.String r2 = r3.getHeaderField(r2)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            boolean r4 = com.facebook.internal.Utility.isNullOrEmpty(r2)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            if (r4 != 0) goto L_0x0092
            java.net.URI r4 = new java.net.URI     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            r4.<init>(r2)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            java.net.URI r2 = r9.a     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            com.facebook.internal.UrlRedirectCache.a(r10, r2, r4)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            com.facebook.internal.ImageDownloader$DownloaderContext r10 = a(r9)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            if (r10 == 0) goto L_0x0092
            boolean r2 = r10.c     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            if (r2 != 0) goto L_0x0092
            com.facebook.internal.ImageRequest r10 = r10.b     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            com.facebook.internal.ImageDownloader$RequestKey r2 = new com.facebook.internal.ImageDownloader$RequestKey     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            java.lang.Object r5 = r9.b     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            r2.<init>(r4, r5)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
            a(r10, r2, r1)     // Catch:{ IOException -> 0x009a, URISyntaxException -> 0x0096, all -> 0x00bc }
        L_0x0092:
            r10 = r0
            r4 = r10
            r2 = 0
            goto L_0x00a6
        L_0x0096:
            r10 = move-exception
            r4 = r0
            r2 = 0
            goto L_0x00d4
        L_0x009a:
            r10 = move-exception
            r4 = r0
            r2 = 0
            goto L_0x00d4
        L_0x009e:
            java.io.InputStream r10 = com.facebook.internal.ImageResponseCache.a(r10, r3)     // Catch:{ IOException -> 0x00c1, URISyntaxException -> 0x00be, all -> 0x00bc }
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r10)     // Catch:{ IOException -> 0x00b7, URISyntaxException -> 0x00b2, all -> 0x00af }
        L_0x00a6:
            com.facebook.internal.Utility.closeQuietly(r10)
            com.facebook.internal.Utility.disconnectQuietly(r3)
            r10 = r0
            r0 = r4
            goto L_0x00da
        L_0x00af:
            r9 = move-exception
            r0 = r10
            goto L_0x00c6
        L_0x00b2:
            r4 = move-exception
            r8 = r4
            r4 = r10
            r10 = r8
            goto L_0x00d4
        L_0x00b7:
            r4 = move-exception
            r8 = r4
            r4 = r10
            r10 = r8
            goto L_0x00d4
        L_0x00bc:
            r9 = move-exception
            goto L_0x00c6
        L_0x00be:
            r10 = move-exception
            r4 = r0
            goto L_0x00d4
        L_0x00c1:
            r10 = move-exception
            r4 = r0
            goto L_0x00d4
        L_0x00c4:
            r9 = move-exception
            r3 = r0
        L_0x00c6:
            com.facebook.internal.Utility.closeQuietly(r0)
            com.facebook.internal.Utility.disconnectQuietly(r3)
            throw r9
        L_0x00cd:
            r10 = move-exception
            r3 = r0
            r4 = r3
            goto L_0x00d4
        L_0x00d1:
            r10 = move-exception
            r3 = r0
            r4 = r3
        L_0x00d4:
            com.facebook.internal.Utility.closeQuietly(r4)
            com.facebook.internal.Utility.disconnectQuietly(r3)
        L_0x00da:
            if (r2 == 0) goto L_0x00df
            a(r9, r10, r0, r1)
        L_0x00df:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.b(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context):void");
    }

    private static synchronized Handler a() {
        Handler handler;
        synchronized (ImageDownloader.class) {
            if (a == null) {
                a = new Handler(Looper.getMainLooper());
            }
            handler = a;
        }
        return handler;
    }

    private static DownloaderContext a(RequestKey requestKey) {
        DownloaderContext downloaderContext;
        synchronized (d) {
            downloaderContext = (DownloaderContext) d.remove(requestKey);
        }
        return downloaderContext;
    }
}
