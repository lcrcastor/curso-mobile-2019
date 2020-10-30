package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.EditTextValidator;
import butterknife.ButterKnife.Finder;

public class SuscripcionActivity$$ViewInjector {
    public static void inject(Finder finder, SuscripcionActivity suscripcionActivity, Object obj) {
        suscripcionActivity.aceptarButton = (Button) finder.findRequiredView(obj, R.id.aceptarButton, "field 'aceptarButton'");
        suscripcionActivity.linearCelular = (LinearLayout) finder.findRequiredView(obj, R.id.linearCelular, "field 'linearCelular'");
        suscripcionActivity.linearCelular1 = (LinearLayout) finder.findRequiredView(obj, R.id.linearCelular1, "field 'linearCelular1'");
        suscripcionActivity.linearMail = (LinearLayout) finder.findRequiredView(obj, R.id.linearMail, "field 'linearMail'");
        suscripcionActivity.empresaCelular = (LinearLayout) finder.findRequiredView(obj, R.id.empresaCelular, "field 'empresaCelular'");
        suscripcionActivity.idLine = finder.findRequiredView(obj, R.id.idLine2, "field 'idLine'");
        suscripcionActivity.textViewCelular = (TextView) finder.findRequiredView(obj, R.id.idCelular, "field 'textViewCelular'");
        suscripcionActivity.textViewEmpresaCelular = (TextView) finder.findRequiredView(obj, R.id.idEmpresaCelular, "field 'textViewEmpresaCelular'");
        suscripcionActivity.editTextMail = (EditTextValidator) finder.findRequiredView(obj, R.id.emailSusc, "field 'editTextMail'");
    }

    public static void reset(SuscripcionActivity suscripcionActivity) {
        suscripcionActivity.aceptarButton = null;
        suscripcionActivity.linearCelular = null;
        suscripcionActivity.linearCelular1 = null;
        suscripcionActivity.linearMail = null;
        suscripcionActivity.empresaCelular = null;
        suscripcionActivity.idLine = null;
        suscripcionActivity.textViewCelular = null;
        suscripcionActivity.textViewEmpresaCelular = null;
        suscripcionActivity.editTextMail = null;
    }
}
