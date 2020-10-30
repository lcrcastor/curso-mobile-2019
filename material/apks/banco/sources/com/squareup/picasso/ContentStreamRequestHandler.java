package com.squareup.picasso;

import android.content.Context;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;
import java.io.InputStream;

class ContentStreamRequestHandler extends RequestHandler {
    final Context a;

    ContentStreamRequestHandler(Context context) {
        this.a = context;
    }

    public boolean canHandleRequest(Request request) {
        return "content".equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) {
        return new Result(a(request), LoadedFrom.DISK);
    }

    /* access modifiers changed from: 0000 */
    public InputStream a(Request request) {
        return this.a.getContentResolver().openInputStream(request.uri);
    }
}
