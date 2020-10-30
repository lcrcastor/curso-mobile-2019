package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.EditTextValidator;
import butterknife.ButterKnife.Finder;

public class EditarCelularActivity$$ViewInjector {
    public static void inject(Finder finder, final EditarCelularActivity editarCelularActivity, Object obj) {
        editarCelularActivity.aceptarButton = (Button) finder.findRequiredView(obj, R.id.aceptarButton, "field 'aceptarButton'");
        editarCelularActivity.empresaCelular = (TextView) finder.findRequiredView(obj, R.id.idEmpresaCelular, "field 'empresaCelular'");
        editarCelularActivity.vTitle = (TextView) finder.findRequiredView(obj, R.id.vTitle, "field 'vTitle'");
        View findRequiredView = finder.findRequiredView(obj, R.id.numero, "field 'numero' and method 'onFocusChanged'");
        editarCelularActivity.numero = (EditTextValidator) findRequiredView;
        findRequiredView.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                editarCelularActivity.a((EditTextValidator) view, z);
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.codArea, "field 'codigoArea' and method 'onFocusChanged'");
        editarCelularActivity.codigoArea = (EditTextValidator) findRequiredView2;
        findRequiredView2.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                editarCelularActivity.a((EditTextValidator) view, z);
            }
        });
    }

    public static void reset(EditarCelularActivity editarCelularActivity) {
        editarCelularActivity.aceptarButton = null;
        editarCelularActivity.empresaCelular = null;
        editarCelularActivity.vTitle = null;
        editarCelularActivity.numero = null;
        editarCelularActivity.codigoArea = null;
    }
}
