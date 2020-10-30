package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;

class ResourceRequestHandler extends RequestHandler {
    private final Context a;

    ResourceRequestHandler(Context context) {
        this.a = context;
    }

    public boolean canHandleRequest(Request request) {
        if (request.resourceId != 0) {
            return true;
        }
        return "android.resource".equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) {
        Resources a2 = Utils.a(this.a, request);
        return new Result(a(a2, Utils.a(a2, request), request), LoadedFrom.DISK);
    }

    private static Bitmap a(Resources resources, int i, Request request) {
        Options b = b(request);
        if (a(b)) {
            BitmapFactory.decodeResource(resources, i, b);
            a(request.targetWidth, request.targetHeight, b, request);
        }
        return BitmapFactory.decodeResource(resources, i, b);
    }
}
