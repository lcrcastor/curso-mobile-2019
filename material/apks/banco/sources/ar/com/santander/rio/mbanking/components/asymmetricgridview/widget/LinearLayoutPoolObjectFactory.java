package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.content.Context;

public class LinearLayoutPoolObjectFactory implements PoolObjectFactory<IcsLinearLayout> {
    private final Context a;

    public LinearLayoutPoolObjectFactory(Context context) {
        this.a = context;
    }

    public IcsLinearLayout createObject() {
        return new IcsLinearLayout(this.a, null);
    }
}
