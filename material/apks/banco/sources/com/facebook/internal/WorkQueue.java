package com.facebook.internal;

import com.facebook.Settings;
import java.util.concurrent.Executor;

class WorkQueue {
    static final /* synthetic */ boolean a = true;
    /* access modifiers changed from: private */
    public final Object b;
    /* access modifiers changed from: private */
    public WorkNode c;
    private final int d;
    private final Executor e;
    private WorkNode f;
    private int g;

    interface WorkItem {
        boolean a();

        void b();
    }

    class WorkNode implements WorkItem {
        static final /* synthetic */ boolean a = true;
        private final Runnable c;
        private WorkNode d;
        private WorkNode e;
        private boolean f;

        static {
            Class<WorkQueue> cls = WorkQueue.class;
        }

        WorkNode(Runnable runnable) {
            this.c = runnable;
        }

        public boolean a() {
            synchronized (WorkQueue.this.b) {
                if (c()) {
                    return false;
                }
                WorkQueue.this.c = a(WorkQueue.this.c);
                return true;
            }
        }

        public void b() {
            synchronized (WorkQueue.this.b) {
                if (!c()) {
                    WorkQueue.this.c = a(WorkQueue.this.c);
                    WorkQueue.this.c = a(WorkQueue.this.c, true);
                }
            }
        }

        public boolean c() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public Runnable d() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            this.f = z;
        }

        /* access modifiers changed from: 0000 */
        public WorkNode a(WorkNode workNode, boolean z) {
            if (!a && this.d != null) {
                throw new AssertionError();
            } else if (a || this.e == null) {
                if (workNode == null) {
                    this.e = this;
                    this.d = this;
                    workNode = this;
                } else {
                    this.d = workNode;
                    this.e = workNode.e;
                    WorkNode workNode2 = this.d;
                    this.e.d = this;
                    workNode2.e = this;
                }
                return z ? this : workNode;
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public WorkNode a(WorkNode workNode) {
            if (!a && this.d == null) {
                throw new AssertionError();
            } else if (a || this.e != null) {
                if (workNode == this) {
                    if (this.d == this) {
                        workNode = null;
                    } else {
                        workNode = this.d;
                    }
                }
                this.d.e = this.e;
                this.e.d = this.d;
                this.e = null;
                this.d = null;
                return workNode;
            } else {
                throw new AssertionError();
            }
        }
    }

    WorkQueue() {
        this(8);
    }

    WorkQueue(int i) {
        this(i, Settings.getExecutor());
    }

    WorkQueue(int i, Executor executor) {
        this.b = new Object();
        this.f = null;
        this.g = 0;
        this.d = i;
        this.e = executor;
    }

    /* access modifiers changed from: 0000 */
    public WorkItem a(Runnable runnable) {
        return a(runnable, true);
    }

    /* access modifiers changed from: 0000 */
    public WorkItem a(Runnable runnable, boolean z) {
        WorkNode workNode = new WorkNode(runnable);
        synchronized (this.b) {
            this.c = workNode.a(this.c, z);
        }
        a();
        return workNode;
    }

    private void a() {
        a((WorkNode) null);
    }

    /* access modifiers changed from: private */
    public void a(WorkNode workNode) {
        WorkNode workNode2;
        synchronized (this.b) {
            if (workNode != null) {
                try {
                    this.f = workNode.a(this.f);
                    this.g--;
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
            if (this.g < this.d) {
                workNode2 = this.c;
                if (workNode2 != null) {
                    this.c = workNode2.a(this.c);
                    this.f = workNode2.a(this.f, false);
                    this.g++;
                    workNode2.a(true);
                }
            } else {
                workNode2 = null;
            }
        }
        if (workNode2 != null) {
            b(workNode2);
        }
    }

    private void b(final WorkNode workNode) {
        this.e.execute(new Runnable() {
            public void run() {
                try {
                    workNode.d().run();
                } finally {
                    WorkQueue.this.a(workNode);
                }
            }
        });
    }
}
