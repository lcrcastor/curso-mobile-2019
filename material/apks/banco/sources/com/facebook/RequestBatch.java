package com.facebook;

import android.os.Handler;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestBatch extends AbstractList<Request> {
    private static AtomicInteger a = new AtomicInteger();
    private Handler b;
    private List<Request> c;
    private int d;
    private final String e;
    private List<Callback> f;
    private String g;

    public interface Callback {
        void onBatchCompleted(RequestBatch requestBatch);
    }

    public interface OnProgressCallback extends Callback {
        void onBatchProgress(RequestBatch requestBatch, long j, long j2);
    }

    public RequestBatch() {
        this.c = new ArrayList();
        this.d = 0;
        this.e = Integer.valueOf(a.incrementAndGet()).toString();
        this.f = new ArrayList();
        this.c = new ArrayList();
    }

    public RequestBatch(Collection<Request> collection) {
        this.c = new ArrayList();
        this.d = 0;
        this.e = Integer.valueOf(a.incrementAndGet()).toString();
        this.f = new ArrayList();
        this.c = new ArrayList(collection);
    }

    public RequestBatch(Request... requestArr) {
        this.c = new ArrayList();
        this.d = 0;
        this.e = Integer.valueOf(a.incrementAndGet()).toString();
        this.f = new ArrayList();
        this.c = Arrays.asList(requestArr);
    }

    public RequestBatch(RequestBatch requestBatch) {
        this.c = new ArrayList();
        this.d = 0;
        this.e = Integer.valueOf(a.incrementAndGet()).toString();
        this.f = new ArrayList();
        this.c = new ArrayList(requestBatch);
        this.b = requestBatch.b;
        this.d = requestBatch.d;
        this.f = new ArrayList(requestBatch.f);
    }

    public int getTimeout() {
        return this.d;
    }

    public void setTimeout(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Argument timeoutInMilliseconds must be >= 0.");
        }
        this.d = i;
    }

    public void addCallback(Callback callback) {
        if (!this.f.contains(callback)) {
            this.f.add(callback);
        }
    }

    public void removeCallback(Callback callback) {
        this.f.remove(callback);
    }

    public final boolean add(Request request) {
        return this.c.add(request);
    }

    public final void add(int i, Request request) {
        this.c.add(i, request);
    }

    public final void clear() {
        this.c.clear();
    }

    public final Request get(int i) {
        return (Request) this.c.get(i);
    }

    public final Request remove(int i) {
        return (Request) this.c.remove(i);
    }

    public final Request set(int i, Request request) {
        return (Request) this.c.set(i, request);
    }

    public final int size() {
        return this.c.size();
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public final Handler b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Handler handler) {
        this.b = handler;
    }

    /* access modifiers changed from: 0000 */
    public final List<Request> c() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public final List<Callback> d() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public final String e() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        this.g = str;
    }

    public final List<Response> executeAndWait() {
        return f();
    }

    public final RequestAsyncTask executeAsync() {
        return g();
    }

    /* access modifiers changed from: 0000 */
    public List<Response> f() {
        return Request.executeBatchAndWait(this);
    }

    /* access modifiers changed from: 0000 */
    public RequestAsyncTask g() {
        return Request.executeBatchAsync(this);
    }
}
