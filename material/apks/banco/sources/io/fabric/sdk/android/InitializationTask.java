package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;

class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> {
    final Kit<Result> a;

    public InitializationTask(Kit<Result> kit) {
        this.a = kit;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        TimingMetric a2 = a("onPreExecute");
        try {
            boolean onPreExecute = this.a.onPreExecute();
            a2.stopMeasuring();
            if (onPreExecute) {
                return;
            }
        } catch (UnmetDependencyException e) {
            throw e;
        } catch (Exception e2) {
            Fabric.getLogger().e(Fabric.TAG, "Failure onPreExecute()", e2);
            a2.stopMeasuring();
        } catch (Throwable th) {
            a2.stopMeasuring();
            cancel(true);
            throw th;
        }
        cancel(true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Result doInBackground(Void... voidArr) {
        TimingMetric a2 = a("doInBackground");
        Result doInBackground = !isCancelled() ? this.a.doInBackground() : null;
        a2.stopMeasuring();
        return doInBackground;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Result result) {
        this.a.onPostExecute(result);
        this.a.e.success(result);
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Result result) {
        this.a.onCancelled(result);
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getIdentifier());
        sb.append(" Initialization was cancelled");
        this.a.e.failure(new InitializationException(sb.toString()));
    }

    public Priority getPriority() {
        return Priority.HIGH;
    }

    private TimingMetric a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getIdentifier());
        sb.append(".");
        sb.append(str);
        TimingMetric timingMetric = new TimingMetric(sb.toString(), "KitInitialization");
        timingMetric.startMeasuring();
        return timingMetric;
    }
}
