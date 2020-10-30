package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;
import ar.com.santander.rio.mbanking.components.asymmetricgridview.AsymmetricGridViewAdapterContract;
import ar.com.santander.rio.mbanking.utils.Utils;

public class AsymmetricGridView extends ListView {
    protected boolean allowReordering;
    protected AsymmetricGridViewAdapterContract gridAdapter;
    protected int numColumns = 2;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    protected int requestedColumnCount;
    protected int requestedColumnWidth;
    protected int requestedHorizontalSpacing;

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        boolean g;
        Parcelable h;
        ClassLoader i;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
            this.f = parcel.readInt();
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.g = z;
            this.h = parcel.readParcelable(this.i);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f);
            parcel.writeByte(this.g ? (byte) 1 : 0);
            parcel.writeParcelable(this.h, i2);
        }
    }

    public AsymmetricGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.requestedHorizontalSpacing = Utils.dpToPx(context, 5.0f);
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    AsymmetricGridView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    AsymmetricGridView.this.determineColumns();
                    if (AsymmetricGridView.this.gridAdapter != null) {
                        AsymmetricGridView.this.gridAdapter.recalculateItemsPerRow();
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    /* access modifiers changed from: protected */
    public void fireOnItemClick(int i, View view) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(this, view, i, (long) view.getId());
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener2) {
        this.onItemLongClickListener = onItemLongClickListener2;
    }

    /* access modifiers changed from: protected */
    public boolean fireOnItemLongClick(int i, View view) {
        if (this.onItemLongClickListener != null) {
            if (this.onItemLongClickListener.onItemLongClick(this, view, i, (long) view.getId())) {
                return true;
            }
        }
        return false;
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (listAdapter instanceof WrapperListAdapter) {
            listAdapter = ((WrapperListAdapter) listAdapter).getWrappedAdapter();
            if (!(listAdapter instanceof AsymmetricGridViewAdapterContract)) {
                throw new UnsupportedOperationException("Wrapped adapter must implement AsymmetricGridViewAdapterContract");
            }
        } else if (!(listAdapter instanceof AsymmetricGridViewAdapterContract)) {
            throw new UnsupportedOperationException("Adapter must implement AsymmetricGridViewAdapterContract");
        }
        this.gridAdapter = (AsymmetricGridViewAdapterContract) listAdapter;
        super.setAdapter(listAdapter);
        this.gridAdapter.recalculateItemsPerRow();
    }

    public void setRequestedColumnWidth(int i) {
        this.requestedColumnWidth = i;
    }

    public void setRequestedColumnCount(int i) {
        this.requestedColumnCount = i;
    }

    public int getRequestedHorizontalSpacing() {
        return this.requestedHorizontalSpacing;
    }

    public void setRequestedHorizontalSpacing(int i) {
        this.requestedHorizontalSpacing = i;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        determineColumns();
    }

    public int determineColumns() {
        int i = this.requestedColumnWidth > 0 ? (getAvailableSpace() + this.requestedHorizontalSpacing) / (this.requestedColumnWidth + this.requestedHorizontalSpacing) : this.requestedColumnCount > 0 ? this.requestedColumnCount : 2;
        if (i <= 0) {
            i = 1;
        }
        this.numColumns = i;
        return i;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.g = this.allowReordering;
        savedState.a = this.numColumns;
        savedState.c = this.requestedColumnCount;
        savedState.b = this.requestedColumnWidth;
        savedState.e = this.requestedHorizontalSpacing;
        if (this.gridAdapter != null) {
            savedState.h = this.gridAdapter.saveState();
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.allowReordering = savedState.g;
        this.numColumns = savedState.a;
        this.requestedColumnCount = savedState.c;
        this.requestedColumnWidth = savedState.b;
        this.requestedHorizontalSpacing = savedState.e;
        if (this.gridAdapter != null) {
            this.gridAdapter.restoreState(savedState.h);
        }
        setSelectionFromTop(20, 0);
    }

    public int getNumColumns() {
        return this.numColumns;
    }

    public int getColumnWidth() {
        return (getAvailableSpace() - ((this.numColumns - 1) * this.requestedHorizontalSpacing)) / this.numColumns;
    }

    public int getAvailableSpace() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public boolean isAllowReordering() {
        return this.allowReordering;
    }

    public void setAllowReordering(boolean z) {
        this.allowReordering = z;
        if (this.gridAdapter != null) {
            this.gridAdapter.recalculateItemsPerRow();
        }
    }
}
