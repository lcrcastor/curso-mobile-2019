package ar.com.santander.rio.mbanking.components.asymmetricgridview.model;

import android.os.Parcelable;

public interface AsymmetricItem extends Parcelable {
    int getColumnSpan();

    int getRowSpan();
}
