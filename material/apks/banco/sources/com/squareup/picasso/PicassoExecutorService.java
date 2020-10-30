package com.squareup.picasso;

import android.net.NetworkInfo;
import com.squareup.picasso.Picasso.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService extends ThreadPoolExecutor {

    static final class PicassoFutureTask extends FutureTask<BitmapHunter> implements Comparable<PicassoFutureTask> {
        private final BitmapHunter a;

        public PicassoFutureTask(BitmapHunter bitmapHunter) {
            super(bitmapHunter, null);
            this.a = bitmapHunter;
        }

        /* renamed from: a */
        public int compareTo(PicassoFutureTask picassoFutureTask) {
            Priority n = this.a.n();
            Priority n2 = picassoFutureTask.a.n();
            return n == n2 ? this.a.a - picassoFutureTask.a.a : n2.ordinal() - n.ordinal();
        }
    }

    PicassoExecutorService() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    /* access modifiers changed from: 0000 */
    public void a(NetworkInfo networkInfo) {
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            a(3);
            return;
        }
        int type = networkInfo.getType();
        if (!(type == 6 || type == 9)) {
            switch (type) {
                case 0:
                    int subtype = networkInfo.getSubtype();
                    switch (subtype) {
                        case 1:
                        case 2:
                            a(1);
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            a(2);
                            break;
                        default:
                            switch (subtype) {
                                case 12:
                                    break;
                                case 13:
                                case 14:
                                case 15:
                                    a(3);
                                    break;
                                default:
                                    a(3);
                                    break;
                            }
                            a(2);
                            break;
                    }
                case 1:
                    break;
                default:
                    a(3);
                    break;
            }
        }
        a(4);
    }

    private void a(int i) {
        setCorePoolSize(i);
        setMaximumPoolSize(i);
    }

    public Future<?> submit(Runnable runnable) {
        PicassoFutureTask picassoFutureTask = new PicassoFutureTask((BitmapHunter) runnable);
        execute(picassoFutureTask);
        return picassoFutureTask;
    }
}
