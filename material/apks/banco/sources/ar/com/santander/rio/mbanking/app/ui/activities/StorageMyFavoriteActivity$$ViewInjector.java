package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class StorageMyFavoriteActivity$$ViewInjector {
    public static void inject(Finder finder, final StorageMyFavoriteActivity storageMyFavoriteActivity, Object obj) {
        storageMyFavoriteActivity.eTxtFavorito = (EditText) finder.findRequiredView(obj, R.id.eTxtFavorito, "field 'eTxtFavorito'");
        storageMyFavoriteActivity.llBuscar = (TextView) finder.findRequiredView(obj, R.id.llBuscar, "field 'llBuscar'");
        finder.findRequiredView(obj, R.id.btnAgregar, "method 'agregarFavorito'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                storageMyFavoriteActivity.b();
            }
        });
    }

    public static void reset(StorageMyFavoriteActivity storageMyFavoriteActivity) {
        storageMyFavoriteActivity.eTxtFavorito = null;
        storageMyFavoriteActivity.llBuscar = null;
    }
}
