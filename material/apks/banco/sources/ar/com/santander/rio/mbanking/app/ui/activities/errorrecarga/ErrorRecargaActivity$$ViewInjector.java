package ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ErrorRecargaActivity$$ViewInjector {
    public static void inject(Finder finder, final ErrorRecargaActivity errorRecargaActivity, Object obj) {
        errorRecargaActivity.algoSalioMalText = (TextView) finder.findRequiredView(obj, R.id.algo_salio_mal_text, "field 'algoSalioMalText'");
        errorRecargaActivity.intentaNuevamenteText = (TextView) finder.findRequiredView(obj, R.id.intenta_nuevamente_text, "field 'intentaNuevamenteText'");
        errorRecargaActivity.btnVolverHome = (TextView) finder.findRequiredView(obj, R.id.btn_volver_home, "field 'btnVolverHome'");
        View findRequiredView = finder.findRequiredView(obj, R.id.btn_intenta_otra_vez, "field 'btnIntentaOtraVez' and method 'onViewClicked'");
        errorRecargaActivity.btnIntentaOtraVez = (Button) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                errorRecargaActivity.onViewClicked();
            }
        });
        errorRecargaActivity.closeAnimationView = finder.findRequiredView(obj, R.id.close_animation_view, "field 'closeAnimationView'");
    }

    public static void reset(ErrorRecargaActivity errorRecargaActivity) {
        errorRecargaActivity.algoSalioMalText = null;
        errorRecargaActivity.intentaNuevamenteText = null;
        errorRecargaActivity.btnVolverHome = null;
        errorRecargaActivity.btnIntentaOtraVez = null;
        errorRecargaActivity.closeAnimationView = null;
    }
}
