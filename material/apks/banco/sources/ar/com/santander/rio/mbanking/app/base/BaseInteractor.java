package ar.com.santander.rio.mbanking.app.base;

import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import javax.inject.Inject;

public abstract class BaseInteractor {
    /* access modifiers changed from: private */
    public static final String b = "BaseInteractor";
    @Inject
    RequestQueue a;
    private ErrorListener c = new ErrorListener() {
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(BaseInteractor.b, "Error en petición");
        }
    };

    public ErrorListener getmErrorListener() {
        return this.c;
    }
}
