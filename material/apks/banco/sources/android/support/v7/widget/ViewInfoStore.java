package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import android.support.v7.widget.RecyclerView.ViewHolder;

class ViewInfoStore {
    @VisibleForTesting
    final ArrayMap<ViewHolder, InfoRecord> a = new ArrayMap<>();
    @VisibleForTesting
    final LongSparseArray<ViewHolder> b = new LongSparseArray<>();

    static class InfoRecord {
        static Pool<InfoRecord> d = new SimplePool(20);
        int a;
        @Nullable
        ItemHolderInfo b;
        @Nullable
        ItemHolderInfo c;

        private InfoRecord() {
        }

        static InfoRecord a() {
            InfoRecord infoRecord = (InfoRecord) d.acquire();
            return infoRecord == null ? new InfoRecord() : infoRecord;
        }

        static void a(InfoRecord infoRecord) {
            infoRecord.a = 0;
            infoRecord.b = null;
            infoRecord.c = null;
            d.release(infoRecord);
        }

        static void b() {
            do {
            } while (d.acquire() != null);
        }
    }

    interface ProcessCallback {
        void a(ViewHolder viewHolder);

        void a(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        void b(ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        void c(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);
    }

    ViewInfoStore() {
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.a.clear();
        this.b.clear();
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.a();
            this.a.put(viewHolder, infoRecord);
        }
        infoRecord.b = itemHolderInfo;
        infoRecord.a |= 4;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        if (infoRecord == null || (infoRecord.a & 1) == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ItemHolderInfo b(ViewHolder viewHolder) {
        return a(viewHolder, 4);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ItemHolderInfo c(ViewHolder viewHolder) {
        return a(viewHolder, 8);
    }

    private ItemHolderInfo a(ViewHolder viewHolder, int i) {
        ItemHolderInfo itemHolderInfo;
        int indexOfKey = this.a.indexOfKey(viewHolder);
        if (indexOfKey < 0) {
            return null;
        }
        InfoRecord infoRecord = (InfoRecord) this.a.valueAt(indexOfKey);
        if (infoRecord == null || (infoRecord.a & i) == 0) {
            return null;
        }
        infoRecord.a &= i ^ -1;
        if (i == 4) {
            itemHolderInfo = infoRecord.b;
        } else if (i == 8) {
            itemHolderInfo = infoRecord.c;
        } else {
            throw new IllegalArgumentException("Must provide flag PRE or POST");
        }
        if ((infoRecord.a & 12) == 0) {
            this.a.removeAt(indexOfKey);
            InfoRecord.a(infoRecord);
        }
        return itemHolderInfo;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j, ViewHolder viewHolder) {
        this.b.put(j, viewHolder);
    }

    /* access modifiers changed from: 0000 */
    public void b(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.a();
            this.a.put(viewHolder, infoRecord);
        }
        infoRecord.a |= 2;
        infoRecord.b = itemHolderInfo;
    }

    /* access modifiers changed from: 0000 */
    public boolean d(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        return (infoRecord == null || (infoRecord.a & 4) == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public ViewHolder a(long j) {
        return (ViewHolder) this.b.get(j);
    }

    /* access modifiers changed from: 0000 */
    public void c(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.a();
            this.a.put(viewHolder, infoRecord);
        }
        infoRecord.c = itemHolderInfo;
        infoRecord.a |= 8;
    }

    /* access modifiers changed from: 0000 */
    public void e(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = InfoRecord.a();
            this.a.put(viewHolder, infoRecord);
        }
        infoRecord.a |= 1;
    }

    /* access modifiers changed from: 0000 */
    public void f(ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.a.get(viewHolder);
        if (infoRecord != null) {
            infoRecord.a &= -2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ProcessCallback processCallback) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = (ViewHolder) this.a.keyAt(size);
            InfoRecord infoRecord = (InfoRecord) this.a.removeAt(size);
            if ((infoRecord.a & 3) == 3) {
                processCallback.a(viewHolder);
            } else if ((infoRecord.a & 1) != 0) {
                if (infoRecord.b == null) {
                    processCallback.a(viewHolder);
                } else {
                    processCallback.a(viewHolder, infoRecord.b, infoRecord.c);
                }
            } else if ((infoRecord.a & 14) == 14) {
                processCallback.b(viewHolder, infoRecord.b, infoRecord.c);
            } else if ((infoRecord.a & 12) == 12) {
                processCallback.c(viewHolder, infoRecord.b, infoRecord.c);
            } else if ((infoRecord.a & 4) != 0) {
                processCallback.a(viewHolder, infoRecord.b, null);
            } else if ((infoRecord.a & 8) != 0) {
                processCallback.b(viewHolder, infoRecord.b, infoRecord.c);
            } else {
                int i = infoRecord.a;
            }
            InfoRecord.a(infoRecord);
        }
    }

    /* access modifiers changed from: 0000 */
    public void g(ViewHolder viewHolder) {
        int size = this.b.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            } else if (viewHolder == this.b.valueAt(size)) {
                this.b.removeAt(size);
                break;
            } else {
                size--;
            }
        }
        InfoRecord infoRecord = (InfoRecord) this.a.remove(viewHolder);
        if (infoRecord != null) {
            InfoRecord.a(infoRecord);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        InfoRecord.b();
    }

    public void h(ViewHolder viewHolder) {
        f(viewHolder);
    }
}
