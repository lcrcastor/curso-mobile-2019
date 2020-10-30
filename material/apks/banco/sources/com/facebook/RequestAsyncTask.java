package com.facebook;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

public class RequestAsyncTask extends AsyncTask<Void, Void, List<Response>> {
    private static final String a = RequestAsyncTask.class.getCanonicalName();
    private static Method b;
    private final HttpURLConnection c;
    private final RequestBatch d;
    private Exception e;

    static {
        Method[] methods;
        for (Method method : AsyncTask.class.getMethods()) {
            if ("executeOnExecutor".equals(method.getName())) {
                Class<Executor>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 2 && parameterTypes[0] == Executor.class && parameterTypes[1].isArray()) {
                    b = method;
                    return;
                }
            }
        }
    }

    public RequestAsyncTask(Request... requestArr) {
        this((HttpURLConnection) null, new RequestBatch(requestArr));
    }

    public RequestAsyncTask(Collection<Request> collection) {
        this((HttpURLConnection) null, new RequestBatch(collection));
    }

    public RequestAsyncTask(RequestBatch requestBatch) {
        this((HttpURLConnection) null, requestBatch);
    }

    public RequestAsyncTask(HttpURLConnection httpURLConnection, Request... requestArr) {
        this(httpURLConnection, new RequestBatch(requestArr));
    }

    public RequestAsyncTask(HttpURLConnection httpURLConnection, Collection<Request> collection) {
        this(httpURLConnection, new RequestBatch(collection));
    }

    public RequestAsyncTask(HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        this.d = requestBatch;
        this.c = httpURLConnection;
    }

    /* access modifiers changed from: protected */
    public final Exception getException() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final RequestBatch getRequests() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{RequestAsyncTask: ");
        sb.append(" connection: ");
        sb.append(this.c);
        sb.append(", requests: ");
        sb.append(this.d);
        sb.append("}");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        if (this.d.b() == null) {
            this.d.a(new Handler());
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(List<Response> list) {
        super.onPostExecute(list);
        if (this.e != null) {
            Log.d(a, String.format("onPostExecute: exception encountered during request: %s", new Object[]{this.e.getMessage()}));
        }
    }

    /* access modifiers changed from: protected */
    public List<Response> doInBackground(Void... voidArr) {
        try {
            if (this.c == null) {
                return this.d.executeAndWait();
            }
            return Request.executeConnectionAndWait(this.c, this.d);
        } catch (Exception e2) {
            this.e = e2;
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public RequestAsyncTask a() {
        if (b != null) {
            try {
                b.invoke(this, new Object[]{Settings.getExecutor(), null});
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        } else {
            execute(new Void[0]);
        }
        return this;
    }
}
