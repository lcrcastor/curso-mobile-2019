package ar.com.santander.rio.mbanking.components.asymmetricgridview;

import android.os.Parcelable;

public interface AsymmetricGridViewAdapterContract {
    void recalculateItemsPerRow();

    void restoreState(Parcelable parcelable);

    Parcelable saveState();
}
