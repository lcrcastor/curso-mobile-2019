package android.support.v7.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil<T> implements ThreadUtil<T> {

    static class MessageQueue {
        private SyncQueueItem a;

        MessageQueue() {
        }

        /* access modifiers changed from: 0000 */
        public synchronized SyncQueueItem a() {
            if (this.a == null) {
                return null;
            }
            SyncQueueItem syncQueueItem = this.a;
            this.a = this.a.j;
            return syncQueueItem;
        }

        /* access modifiers changed from: 0000 */
        public synchronized void a(SyncQueueItem syncQueueItem) {
            syncQueueItem.j = this.a;
            this.a = syncQueueItem;
        }

        /* access modifiers changed from: 0000 */
        public synchronized void b(SyncQueueItem syncQueueItem) {
            if (this.a == null) {
                this.a = syncQueueItem;
                return;
            }
            SyncQueueItem syncQueueItem2 = this.a;
            while (syncQueueItem2.j != null) {
                syncQueueItem2 = syncQueueItem2.j;
            }
            syncQueueItem2.j = syncQueueItem;
        }

        /* access modifiers changed from: 0000 */
        public synchronized void a(int i) {
            while (this.a != null && this.a.a == i) {
                SyncQueueItem syncQueueItem = this.a;
                this.a = this.a.j;
                syncQueueItem.a();
            }
            if (this.a != null) {
                SyncQueueItem syncQueueItem2 = this.a;
                SyncQueueItem a2 = syncQueueItem2.j;
                while (a2 != null) {
                    SyncQueueItem a3 = a2.j;
                    if (a2.a == i) {
                        syncQueueItem2.j = a3;
                        a2.a();
                    } else {
                        syncQueueItem2 = a2;
                    }
                    a2 = a3;
                }
            }
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem h;
        private static final Object i = new Object();
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public Object g;
        /* access modifiers changed from: private */
        public SyncQueueItem j;

        SyncQueueItem() {
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.j = null;
            this.f = 0;
            this.e = 0;
            this.d = 0;
            this.c = 0;
            this.b = 0;
            this.a = 0;
            this.g = null;
            synchronized (i) {
                if (h != null) {
                    this.j = h;
                }
                h = this;
            }
        }

        static SyncQueueItem a(int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
            SyncQueueItem syncQueueItem;
            synchronized (i) {
                if (h == null) {
                    syncQueueItem = new SyncQueueItem();
                } else {
                    syncQueueItem = h;
                    h = h.j;
                    syncQueueItem.j = null;
                }
                syncQueueItem.a = i2;
                syncQueueItem.b = i3;
                syncQueueItem.c = i4;
                syncQueueItem.d = i5;
                syncQueueItem.e = i6;
                syncQueueItem.f = i7;
                syncQueueItem.g = obj;
            }
            return syncQueueItem;
        }

        static SyncQueueItem a(int i2, int i3, int i4) {
            return a(i2, i3, i4, 0, 0, 0, null);
        }

        static SyncQueueItem a(int i2, int i3, Object obj) {
            return a(i2, i3, 0, 0, 0, 0, obj);
        }
    }

    MessageThreadUtil() {
    }

    public MainThreadCallback<T> a(final MainThreadCallback<T> mainThreadCallback) {
        return new MainThreadCallback<T>() {
            final MessageQueue a = new MessageQueue();
            private final Handler d = new Handler(Looper.getMainLooper());
            private Runnable e = new Runnable() {
                public void run() {
                    SyncQueueItem a2 = AnonymousClass1.this.a.a();
                    while (a2 != null) {
                        switch (a2.a) {
                            case 1:
                                mainThreadCallback.updateItemCount(a2.b, a2.c);
                                break;
                            case 2:
                                mainThreadCallback.addTile(a2.b, (Tile) a2.g);
                                break;
                            case 3:
                                mainThreadCallback.removeTile(a2.b, a2.c);
                                break;
                            default:
                                StringBuilder sb = new StringBuilder();
                                sb.append("Unsupported message, what=");
                                sb.append(a2.a);
                                Log.e("ThreadUtil", sb.toString());
                                break;
                        }
                        a2 = AnonymousClass1.this.a.a();
                    }
                }
            };

            public void updateItemCount(int i, int i2) {
                a(SyncQueueItem.a(1, i, i2));
            }

            public void addTile(int i, Tile<T> tile) {
                a(SyncQueueItem.a(2, i, (Object) tile));
            }

            public void removeTile(int i, int i2) {
                a(SyncQueueItem.a(3, i, i2));
            }

            private void a(SyncQueueItem syncQueueItem) {
                this.a.b(syncQueueItem);
                this.d.post(this.e);
            }
        };
    }

    public BackgroundCallback<T> a(final BackgroundCallback<T> backgroundCallback) {
        return new BackgroundCallback<T>() {
            final MessageQueue a = new MessageQueue();
            AtomicBoolean b = new AtomicBoolean(false);
            private final Executor e = AsyncTask.THREAD_POOL_EXECUTOR;
            private Runnable f = new Runnable() {
                public void run() {
                    while (true) {
                        SyncQueueItem a2 = AnonymousClass2.this.a.a();
                        if (a2 != null) {
                            switch (a2.a) {
                                case 1:
                                    AnonymousClass2.this.a.a(1);
                                    backgroundCallback.refresh(a2.b);
                                    break;
                                case 2:
                                    AnonymousClass2.this.a.a(2);
                                    AnonymousClass2.this.a.a(3);
                                    backgroundCallback.updateRange(a2.b, a2.c, a2.d, a2.e, a2.f);
                                    break;
                                case 3:
                                    backgroundCallback.loadTile(a2.b, a2.c);
                                    break;
                                case 4:
                                    backgroundCallback.recycleTile((Tile) a2.g);
                                    break;
                                default:
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Unsupported message, what=");
                                    sb.append(a2.a);
                                    Log.e("ThreadUtil", sb.toString());
                                    break;
                            }
                        } else {
                            AnonymousClass2.this.b.set(false);
                            return;
                        }
                    }
                }
            };

            public void refresh(int i) {
                b(SyncQueueItem.a(1, i, (Object) null));
            }

            public void updateRange(int i, int i2, int i3, int i4, int i5) {
                b(SyncQueueItem.a(2, i, i2, i3, i4, i5, null));
            }

            public void loadTile(int i, int i2) {
                a(SyncQueueItem.a(3, i, i2));
            }

            public void recycleTile(Tile<T> tile) {
                a(SyncQueueItem.a(4, 0, (Object) tile));
            }

            private void a(SyncQueueItem syncQueueItem) {
                this.a.b(syncQueueItem);
                a();
            }

            private void b(SyncQueueItem syncQueueItem) {
                this.a.a(syncQueueItem);
                a();
            }

            private void a() {
                if (this.b.compareAndSet(false, true)) {
                    this.e.execute(this.f);
                }
            }
        };
    }
}
