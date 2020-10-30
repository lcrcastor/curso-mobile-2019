package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class TokenOBPActivity$$ViewInjector {
    public static void inject(Finder finder, TokenOBPActivity tokenOBPActivity, Object obj) {
        tokenOBPActivity.btn_Aceptar = (Button) finder.findRequiredView(obj, R.id.tokenOBPAceptar, "field 'btn_Aceptar'");
        tokenOBPActivity.btn_Cancelar = (Button) finder.findRequiredView(obj, R.id.tokenOBPCancelar, "field 'btn_Cancelar'");
        tokenOBPActivity.tv_Mensaje = (TextView) finder.findRequiredView(obj, R.id.tokenOBPMensaje, "field 'tv_Mensaje'");
        tokenOBPActivity.row_TokenOK = (LinearLayout) finder.findRequiredView(obj, R.id.row_tokenOBPOk, "field 'row_TokenOK'");
        tokenOBPActivity.row_SinToken = (LinearLayout) finder.findRequiredView(obj, R.id.row_tokenOBPSinToken, "field 'row_SinToken'");
        tokenOBPActivity.btn_CancelarSinToken = (Button) finder.findRequiredView(obj, R.id.tokenOBPCancelarSinToken, "field 'btn_CancelarSinToken'");
    }

    public static void reset(TokenOBPActivity tokenOBPActivity) {
        tokenOBPActivity.btn_Aceptar = null;
        tokenOBPActivity.btn_Cancelar = null;
        tokenOBPActivity.tv_Mensaje = null;
        tokenOBPActivity.row_TokenOK = null;
        tokenOBPActivity.row_SinToken = null;
        tokenOBPActivity.btn_CancelarSinToken = null;
    }
}
