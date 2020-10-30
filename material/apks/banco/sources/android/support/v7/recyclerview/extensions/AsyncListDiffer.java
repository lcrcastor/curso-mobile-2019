package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig.Builder;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.Callback;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.DiffUtil.ItemCallback;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView.Adapter;
import java.util.Collections;
import java.util.List;

public class AsyncListDiffer<T> {
    private final ListUpdateCallback a;
    /* access modifiers changed from: private */
    public final AsyncDifferConfig<T> b;
    @Nullable
    private List<T> c;
    @NonNull
    private List<T> d = Collections.emptyList();
    /* access modifiers changed from: private */
    public int e;

    public AsyncListDiffer(@NonNull Adapter adapter, @NonNull ItemCallback<T> itemCallback) {
        this.a = new AdapterListUpdateCallback(adapter);
        this.b = new Builder(itemCallback).build();
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.a = listUpdateCallback;
        this.b = asyncDifferConfig;
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.d;
    }

    public void submitList(final List<T> list) {
        if (list != this.c) {
            final int i = this.e + 1;
            this.e = i;
            if (list == null) {
                this.a.onRemoved(0, this.c.size());
                this.c = null;
                this.d = Collections.emptyList();
            } else if (this.c == null) {
                this.a.onInserted(0, list.size());
                this.c = list;
                this.d = Collections.unmodifiableList(list);
            } else {
                final List<T> list2 = this.c;
                this.b.getBackgroundThreadExecutor().execute(new Runnable() {
                    public void run() {
                        final DiffResult calculateDiff = DiffUtil.calculateDiff(new Callback() {
                            public int getOldListSize() {
                                return list2.size();
                            }

                            public int getNewListSize() {
                                return list.size();
                            }

                            public boolean areItemsTheSame(int i, int i2) {
                                return AsyncListDiffer.this.b.getDiffCallback().areItemsTheSame(list2.get(i), list.get(i2));
                            }

                            public boolean areContentsTheSame(int i, int i2) {
                                return AsyncListDiffer.this.b.getDiffCallback().areContentsTheSame(list2.get(i), list.get(i2));
                            }
                        });
                        AsyncListDiffer.this.b.getMainThreadExecutor().execute(new Runnable() {
                            public void run() {
                                if (AsyncListDiffer.this.e == i) {
                                    AsyncListDiffer.this.a(list, calculateDiff);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(@NonNull List<T> list, @NonNull DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(this.a);
        this.c = list;
        this.d = Collections.unmodifiableList(list);
    }
}
