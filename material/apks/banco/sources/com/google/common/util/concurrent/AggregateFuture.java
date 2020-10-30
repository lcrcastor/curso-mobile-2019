package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AggregateFuture<InputT, OutputT> extends TrustedFuture<OutputT> {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(AggregateFuture.class.getName());
    /* access modifiers changed from: private */
    public RunningState b;

    abstract class RunningState extends AggregateFutureState implements Runnable {
        /* access modifiers changed from: private */
        public ImmutableCollection<? extends ListenableFuture<? extends InputT>> b;
        private final boolean c;
        private final boolean d;

        /* access modifiers changed from: 0000 */
        public abstract void a(boolean z, int i, @Nullable InputT inputt);

        /* access modifiers changed from: 0000 */
        public abstract void b();

        /* access modifiers changed from: 0000 */
        public void c() {
        }

        RunningState(ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean z, boolean z2) {
            super(immutableCollection.size());
            this.b = (ImmutableCollection) Preconditions.checkNotNull(immutableCollection);
            this.c = z;
            this.d = z2;
        }

        public final void run() {
            g();
        }

        /* access modifiers changed from: private */
        public void f() {
            if (this.b.isEmpty()) {
                b();
                return;
            }
            if (this.c) {
                final int i = 0;
                Iterator it = this.b.iterator();
                while (it.hasNext()) {
                    final ListenableFuture listenableFuture = (ListenableFuture) it.next();
                    int i2 = i + 1;
                    listenableFuture.addListener(new Runnable() {
                        public void run() {
                            try {
                                RunningState.this.a(i, listenableFuture);
                            } finally {
                                RunningState.this.g();
                            }
                        }
                    }, MoreExecutors.directExecutor());
                    i = i2;
                }
            } else {
                Iterator it2 = this.b.iterator();
                while (it2.hasNext()) {
                    ((ListenableFuture) it2.next()).addListener(this, MoreExecutors.directExecutor());
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0027  */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x002e  */
        /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void a(java.lang.Throwable r7) {
            /*
                r6 = this;
                com.google.common.base.Preconditions.checkNotNull(r7)
                boolean r0 = r6.c
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x001e
                com.google.common.util.concurrent.AggregateFuture r0 = com.google.common.util.concurrent.AggregateFuture.this
                boolean r0 = r0.setException(r7)
                if (r0 == 0) goto L_0x0015
                r6.a()
                goto L_0x001f
            L_0x0015:
                java.util.Set r3 = r6.d()
                boolean r3 = com.google.common.util.concurrent.AggregateFuture.b(r3, r7)
                goto L_0x0020
            L_0x001e:
                r0 = 0
            L_0x001f:
                r3 = 1
            L_0x0020:
                boolean r4 = r7 instanceof java.lang.Error
                boolean r5 = r6.c
                if (r0 != 0) goto L_0x0027
                goto L_0x0028
            L_0x0027:
                r1 = 0
            L_0x0028:
                r0 = r5 & r1
                r0 = r0 & r3
                r0 = r0 | r4
                if (r0 == 0) goto L_0x003e
                if (r4 == 0) goto L_0x0033
                java.lang.String r0 = "Input Future failed with Error"
                goto L_0x0035
            L_0x0033:
                java.lang.String r0 = "Got more than one input Future failure. Logging failures after the first"
            L_0x0035:
                java.util.logging.Logger r1 = com.google.common.util.concurrent.AggregateFuture.a
                java.util.logging.Level r2 = java.util.logging.Level.SEVERE
                r1.log(r2, r0, r7)
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AggregateFuture.RunningState.a(java.lang.Throwable):void");
        }

        /* access modifiers changed from: 0000 */
        public final void a(Set<Throwable> set) {
            if (!AggregateFuture.this.isCancelled()) {
                AggregateFuture.b(set, AggregateFuture.this.a());
            }
        }

        /* access modifiers changed from: private */
        public void a(int i, Future<? extends InputT> future) {
            Preconditions.checkState(this.c || !AggregateFuture.this.isDone() || AggregateFuture.this.isCancelled(), "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.c) {
                    if (future.isCancelled()) {
                        AggregateFuture.this.b = null;
                        AggregateFuture.this.cancel(false);
                        return;
                    }
                    Object done = Futures.getDone(future);
                    if (this.d) {
                        a(this.c, i, (InputT) done);
                    }
                } else if (this.d && !future.isCancelled()) {
                    a(this.c, i, (InputT) Futures.getDone(future));
                }
            } catch (ExecutionException e) {
                a(e.getCause());
            } catch (Throwable th) {
                a(th);
            }
        }

        /* access modifiers changed from: private */
        public void g() {
            int e = e();
            Preconditions.checkState(e >= 0, "Less than 0 remaining futures");
            if (e == 0) {
                h();
            }
        }

        private void h() {
            if (this.d && (!this.c)) {
                int i = 0;
                Iterator it = this.b.iterator();
                while (it.hasNext()) {
                    int i2 = i + 1;
                    a(i, (ListenableFuture) it.next());
                    i = i2;
                }
            }
            b();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b = null;
        }
    }

    AggregateFuture() {
    }

    /* access modifiers changed from: protected */
    public final void afterDone() {
        super.afterDone();
        RunningState runningState = this.b;
        if (runningState != null) {
            this.b = null;
            ImmutableCollection a2 = runningState.b;
            boolean wasInterrupted = wasInterrupted();
            if (wasInterrupted()) {
                runningState.c();
            }
            if (isCancelled() && (a2 != null)) {
                Iterator it = a2.iterator();
                while (it.hasNext()) {
                    ((ListenableFuture) it.next()).cancel(wasInterrupted);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(RunningState runningState) {
        this.b = runningState;
        runningState.f();
    }

    /* access modifiers changed from: private */
    public static boolean b(Set<Throwable> set, Throwable th) {
        while (th != null) {
            if (!set.add(th)) {
                return false;
            }
            th = th.getCause();
        }
        return true;
    }
}
