package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class InfoAdmFondosActivity$$ViewInjector {
    public static void inject(Finder finder, InfoAdmFondosActivity infoAdmFondosActivity, Object obj) {
        infoAdmFondosActivity.txtTitulo = (TextView) finder.findRequiredView(obj, R.id.F24_14_LBL_TITLE, "field 'txtTitulo'");
        infoAdmFondosActivity.lnlHeaderHonorarios = (LinearLayout) finder.findRequiredView(obj, R.id.F24_14_LNL_HEADER_HONORARIOS, "field 'lnlHeaderHonorarios'");
        infoAdmFondosActivity.lnlHeaderHorarios = (LinearLayout) finder.findRequiredView(obj, R.id.F24_15_LNL_HEADER_HORARIOS, "field 'lnlHeaderHorarios'");
        infoAdmFondosActivity.lstDatos = (TableLayout) finder.findRequiredView(obj, R.id.F24_14_LST_DATOS, "field 'lstDatos'");
        infoAdmFondosActivity.lblLegales = (TextView) finder.findRequiredView(obj, R.id.F24_14_LBL_LEGALES, "field 'lblLegales'");
        infoAdmFondosActivity.lblNombre = (TextView) finder.findRequiredView(obj, R.id.F24_14_LBL_NOMBRE, "field 'lblNombre'");
    }

    public static void reset(InfoAdmFondosActivity infoAdmFondosActivity) {
        infoAdmFondosActivity.txtTitulo = null;
        infoAdmFondosActivity.lnlHeaderHonorarios = null;
        infoAdmFondosActivity.lnlHeaderHorarios = null;
        infoAdmFondosActivity.lstDatos = null;
        infoAdmFondosActivity.lblLegales = null;
        infoAdmFondosActivity.lblNombre = null;
    }
}
