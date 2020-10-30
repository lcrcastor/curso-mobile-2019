package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class TelefonoSucursalSelectorActivity$$ViewInjector {
    public static void inject(Finder finder, final TelefonoSucursalSelectorActivity telefonoSucursalSelectorActivity, Object obj) {
        telefonoSucursalSelectorActivity.textViewNumUtilesNumber = (TextView) finder.findRequiredView(obj, R.id.textViewNumUtilesNumber, "field 'textViewNumUtilesNumber'");
        telefonoSucursalSelectorActivity.textViewNumUtilesCall = (TextView) finder.findRequiredView(obj, R.id.textViewNumUtilesCall, "field 'textViewNumUtilesCall'");
        telefonoSucursalSelectorActivity.textViewNumUtilesCopy = (TextView) finder.findRequiredView(obj, R.id.textViewNumUtilesCopy, "field 'textViewNumUtilesCopy'");
        telefonoSucursalSelectorActivity.textViewNumUtilesSave = (TextView) finder.findRequiredView(obj, R.id.textViewNumUtilesSave, "field 'textViewNumUtilesSave'");
        finder.findRequiredView(obj, R.id.textViewNumUtilesCancel, "method 'cerrarActivity'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                telefonoSucursalSelectorActivity.b();
            }
        });
    }

    public static void reset(TelefonoSucursalSelectorActivity telefonoSucursalSelectorActivity) {
        telefonoSucursalSelectorActivity.textViewNumUtilesNumber = null;
        telefonoSucursalSelectorActivity.textViewNumUtilesCall = null;
        telefonoSucursalSelectorActivity.textViewNumUtilesCopy = null;
        telefonoSucursalSelectorActivity.textViewNumUtilesSave = null;
    }
}
