package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ActivityEnvioDineroConfirmacionEnvio$$ViewInjector {
    public static void inject(Finder finder, final ActivityEnvioDineroConfirmacionEnvio activityEnvioDineroConfirmacionEnvio, Object obj) {
        activityEnvioDineroConfirmacionEnvio.lbl_confirmacionEnvio = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_confirmacionEnvio, "field 'lbl_confirmacionEnvio'");
        activityEnvioDineroConfirmacionEnvio.lbl_verificaDatos = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_verificaDatos, "field 'lbl_verificaDatos'");
        activityEnvioDineroConfirmacionEnvio.lbl_detalleCuenta = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_detalleCuenta, "field 'lbl_detalleCuenta'");
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleCuenta = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_data_detalleCuenta, "field 'lbl_data_detalleCuenta'");
        activityEnvioDineroConfirmacionEnvio.img_checkboxTyc = (ImageView) finder.findRequiredView(obj, R.id.F12_03_img_checkbox_tyc, "field 'img_checkboxTyc'");
        activityEnvioDineroConfirmacionEnvio.lbl_conectorVerTerminos = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_conectorVerTerminos, "field 'lbl_conectorVerTerminos'");
        activityEnvioDineroConfirmacionEnvio.lbl_detalleImporte = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_detalleImporte, "field 'lbl_detalleImporte'");
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleImporte = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_data_detalleImporte, "field 'lbl_data_detalleImporte'");
        activityEnvioDineroConfirmacionEnvio.lbl_detalleDestinatario = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_detalleDestinatario, "field 'lbl_detalleDestinatario'");
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleDestinatario = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_data_detalleDestinatario, "field 'lbl_data_detalleDestinatario'");
        activityEnvioDineroConfirmacionEnvio.lbl_detalleDocumento = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_detalleDocumento, "field 'lbl_detalleDocumento'");
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleDocumento = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_data_detalleDocumento, "field 'lbl_data_detalleDocumento'");
        activityEnvioDineroConfirmacionEnvio.lbl_detalleEmail = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_detalleEmail, "field 'lbl_detalleEmail'");
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleEmail = (TextView) finder.findRequiredView(obj, R.id.F12_03_lbl_data_detalleEmail, "field 'lbl_data_detalleEmail'");
        activityEnvioDineroConfirmacionEnvio.btn_confirmarEnvio = (Button) finder.findRequiredView(obj, R.id.F12_03_btn_confirmarEnvio, "field 'btn_confirmarEnvio'");
        View findRequiredView = finder.findRequiredView(obj, R.id.F12_03_lbl_verTerminos, "field 'lbl_terminosycondiciones' and method 'verTerminos'");
        activityEnvioDineroConfirmacionEnvio.lbl_terminosycondiciones = (TextView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                activityEnvioDineroConfirmacionEnvio.verTerminos(view);
            }
        });
    }

    public static void reset(ActivityEnvioDineroConfirmacionEnvio activityEnvioDineroConfirmacionEnvio) {
        activityEnvioDineroConfirmacionEnvio.lbl_confirmacionEnvio = null;
        activityEnvioDineroConfirmacionEnvio.lbl_verificaDatos = null;
        activityEnvioDineroConfirmacionEnvio.lbl_detalleCuenta = null;
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleCuenta = null;
        activityEnvioDineroConfirmacionEnvio.img_checkboxTyc = null;
        activityEnvioDineroConfirmacionEnvio.lbl_conectorVerTerminos = null;
        activityEnvioDineroConfirmacionEnvio.lbl_detalleImporte = null;
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleImporte = null;
        activityEnvioDineroConfirmacionEnvio.lbl_detalleDestinatario = null;
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleDestinatario = null;
        activityEnvioDineroConfirmacionEnvio.lbl_detalleDocumento = null;
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleDocumento = null;
        activityEnvioDineroConfirmacionEnvio.lbl_detalleEmail = null;
        activityEnvioDineroConfirmacionEnvio.lbl_data_detalleEmail = null;
        activityEnvioDineroConfirmacionEnvio.btn_confirmarEnvio = null;
        activityEnvioDineroConfirmacionEnvio.lbl_terminosycondiciones = null;
    }
}
