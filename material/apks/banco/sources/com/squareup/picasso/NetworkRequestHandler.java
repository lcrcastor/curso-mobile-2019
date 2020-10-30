package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.NetworkInfo;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;
import cz.msebera.android.httpclient.HttpHost;
import java.io.IOException;
import java.io.InputStream;

class NetworkRequestHandler extends RequestHandler {
    private final Downloader a;
    private final Stats b;

    static class ContentLengthException extends IOException {
        public ContentLengthException(String str) {
            super(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return 2;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return true;
    }

    public NetworkRequestHandler(Downloader downloader, Stats stats) {
        this.a = downloader;
        this.b = stats;
    }

    public boolean canHandleRequest(Request request) {
        String scheme = request.uri.getScheme();
        return HttpHost.DEFAULT_SCHEME_NAME.equals(scheme) || "https".equals(scheme);
    }

    public Result load(Request request, int i) {
        Response load = this.a.load(request.uri, request.c);
        if (load == null) {
            return null;
        }
        LoadedFrom loadedFrom = load.c ? LoadedFrom.DISK : LoadedFrom.NETWORK;
        Bitmap bitmap = load.getBitmap();
        if (bitmap != null) {
            return new Result(bitmap, loadedFrom);
        }
        InputStream inputStream = load.getInputStream();
        if (inputStream == null) {
            return null;
        }
        if (loadedFrom == LoadedFrom.DISK && load.getContentLength() == 0) {
            Utils.a(inputStream);
            throw new ContentLengthException("Received response with 0 content-length header.");
        }
        if (loadedFrom == LoadedFrom.NETWORK && load.getContentLength() > 0) {
            this.b.a(load.getContentLength());
        }
        return new Result(inputStream, loadedFrom);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(boolean z, NetworkInfo networkInfo) {
        return networkInfo == null || networkInfo.isConnected();
    }
}
