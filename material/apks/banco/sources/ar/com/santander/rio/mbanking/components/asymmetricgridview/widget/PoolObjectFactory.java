package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.view.View;

public interface PoolObjectFactory<T extends View> {
    T createObject();
}
