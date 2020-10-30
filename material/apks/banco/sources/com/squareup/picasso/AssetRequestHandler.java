package com.squareup.picasso;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;

class AssetRequestHandler extends RequestHandler {
    private static final int a = "file:///android_asset/".length();
    private final AssetManager b;

    public AssetRequestHandler(Context context) {
        this.b = context.getAssets();
    }

    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        if (!"file".equals(uri.getScheme()) || uri.getPathSegments().isEmpty() || !"android_asset".equals(uri.getPathSegments().get(0))) {
            return false;
        }
        return true;
    }

    public Result load(Request request, int i) {
        return new Result(this.b.open(a(request)), LoadedFrom.DISK);
    }

    static String a(Request request) {
        return request.uri.toString().substring(a);
    }
}
