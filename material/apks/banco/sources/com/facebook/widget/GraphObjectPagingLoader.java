package com.facebook.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.Loader;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Response.PagingDirection;
import com.facebook.internal.CacheableRequestBatch;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;

class GraphObjectPagingLoader<T extends GraphObject> extends Loader<SimpleGraphObjectCursor<T>> {
    private final Class<T> a;
    private boolean b;
    private Request c;
    private Request d;
    private Request e;
    private OnErrorListener f;
    private SimpleGraphObjectCursor<T> g;
    private boolean h = false;
    private boolean i = false;

    public interface OnErrorListener {
        void onError(FacebookException facebookException, GraphObjectPagingLoader<?> graphObjectPagingLoader);
    }

    interface PagedResults extends GraphObject {
        GraphObjectList<GraphObject> a();
    }

    public GraphObjectPagingLoader(Context context, Class<T> cls) {
        super(context);
        this.a = cls;
    }

    public void a(OnErrorListener onErrorListener) {
        this.f = onErrorListener;
    }

    public SimpleGraphObjectCursor<T> a() {
        return this.g;
    }

    public void b() {
        this.e = null;
        this.c = null;
        this.d = null;
        deliverResult(null);
    }

    public boolean c() {
        return this.i;
    }

    public void a(Request request, boolean z) {
        this.c = request;
        a(request, z, 0);
    }

    public void a(long j) {
        if (this.c == null) {
            throw new FacebookException("refreshOriginalRequest may not be called until after startLoading has been called.");
        }
        a(this.c, false, j);
    }

    public void d() {
        if (this.e != null) {
            this.h = true;
            this.d = this.e;
            this.d.setCallback(new Callback() {
                public void onCompleted(Response response) {
                    GraphObjectPagingLoader.this.a(response);
                }
            });
            this.i = true;
            Request.executeBatchAsync((RequestBatch) b(this.d, this.b));
        }
    }

    /* renamed from: a */
    public void deliverResult(SimpleGraphObjectCursor<T> simpleGraphObjectCursor) {
        SimpleGraphObjectCursor<T> simpleGraphObjectCursor2 = this.g;
        this.g = simpleGraphObjectCursor;
        if (isStarted()) {
            super.deliverResult(simpleGraphObjectCursor);
            if (simpleGraphObjectCursor2 != null && simpleGraphObjectCursor2 != simpleGraphObjectCursor && !simpleGraphObjectCursor2.h()) {
                simpleGraphObjectCursor2.f();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStartLoading() {
        super.onStartLoading();
        if (this.g != null) {
            deliverResult(this.g);
        }
    }

    private void a(Request request, boolean z, long j) {
        this.b = z;
        this.h = false;
        this.e = null;
        this.d = request;
        this.d.setCallback(new Callback() {
            public void onCompleted(Response response) {
                GraphObjectPagingLoader.this.a(response);
            }
        });
        this.i = true;
        final CacheableRequestBatch b2 = b(request, z);
        AnonymousClass3 r4 = new Runnable() {
            public void run() {
                Request.executeBatchAsync(b2);
            }
        };
        if (j == 0) {
            r4.run();
        } else {
            new Handler().postDelayed(r4, j);
        }
    }

    private CacheableRequestBatch b(Request request, boolean z) {
        CacheableRequestBatch cacheableRequestBatch = new CacheableRequestBatch(request);
        cacheableRequestBatch.setForceRoundTrip(!z);
        return cacheableRequestBatch;
    }

    /* access modifiers changed from: private */
    public void a(Response response) {
        FacebookException facebookException;
        if (response.getRequest() == this.d) {
            this.i = false;
            this.d = null;
            FacebookRequestError error = response.getError();
            if (error == null) {
                facebookException = null;
            } else {
                facebookException = error.getException();
            }
            if (response.getGraphObject() == null && facebookException == null) {
                facebookException = new FacebookException("GraphObjectPagingLoader received neither a result nor an error.");
            }
            if (facebookException != null) {
                this.e = null;
                if (this.f != null) {
                    this.f.onError(facebookException, this);
                }
            } else {
                b(response);
            }
        }
    }

    private void b(Response response) {
        SimpleGraphObjectCursor simpleGraphObjectCursor = (this.g == null || !this.h) ? new SimpleGraphObjectCursor() : new SimpleGraphObjectCursor(this.g);
        PagedResults pagedResults = (PagedResults) response.getGraphObjectAs(PagedResults.class);
        boolean isFromCache = response.getIsFromCache();
        GraphObjectList castToListOf = pagedResults.a().castToListOf(this.a);
        boolean z = castToListOf.size() > 0;
        if (z) {
            this.e = response.getRequestForPagedResults(PagingDirection.NEXT);
            simpleGraphObjectCursor.a(castToListOf, isFromCache);
            if (this.e != null) {
                simpleGraphObjectCursor.b(true);
            } else {
                simpleGraphObjectCursor.b(false);
            }
        }
        if (!z) {
            simpleGraphObjectCursor.b(false);
            simpleGraphObjectCursor.a(isFromCache);
            this.e = null;
        }
        if (!isFromCache) {
            this.b = false;
        }
        deliverResult(simpleGraphObjectCursor);
    }
}
