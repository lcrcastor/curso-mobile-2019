package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;

public final class AdapterListUpdateCallback implements ListUpdateCallback {
    @NonNull
    private final Adapter a;

    public AdapterListUpdateCallback(@NonNull Adapter adapter) {
        this.a = adapter;
    }

    public void onInserted(int i, int i2) {
        this.a.notifyItemRangeInserted(i, i2);
    }

    public void onRemoved(int i, int i2) {
        this.a.notifyItemRangeRemoved(i, i2);
    }

    public void onMoved(int i, int i2) {
        this.a.notifyItemMoved(i, i2);
    }

    public void onChanged(int i, int i2, Object obj) {
        this.a.notifyItemRangeChanged(i, i2, obj);
    }
}
