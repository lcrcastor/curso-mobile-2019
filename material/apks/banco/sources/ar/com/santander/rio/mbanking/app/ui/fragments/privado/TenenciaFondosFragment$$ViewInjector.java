package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife.Finder;

public class TenenciaFondosFragment$$ViewInjector {
    public static void inject(Finder finder, TenenciaFondosFragment tenenciaFondosFragment, Object obj) {
        tenenciaFondosFragment.mainView = (RelativeLayout) finder.findRequiredView(obj, R.id.F24_00_RLL_MAIN, "field 'mainView'");
        tenenciaFondosFragment.lytLista = (LinearLayout) finder.findRequiredView(obj, R.id.F24_00_LNL_LISTA, "field 'lytLista'");
        tenenciaFondosFragment.lblDataLegalesCuenta = (TextView) finder.findRequiredView(obj, R.id.F24_00_LBL_DATA_LEGALES_CUENTA, "field 'lblDataLegalesCuenta'");
        tenenciaFondosFragment.lblTxtErrorRes4 = (TextView) finder.findRequiredView(obj, R.id.F24_00_cara_triste_txt, "field 'lblTxtErrorRes4'");
        tenenciaFondosFragment.btnSuscribirFondo = (Button) finder.findRequiredView(obj, R.id.F24_00_BTN_SUSCRIBIR_FONDO, "field 'btnSuscribirFondo'");
        tenenciaFondosFragment.btnConoceMas = (Button) finder.findRequiredView(obj, R.id.F24_00_BTN_CONOCE_MAS, "field 'btnConoceMas'");
        tenenciaFondosFragment.lblDataTotalPesos = (TextView) finder.findRequiredView(obj, R.id.F24_00_LBL_DATA_TOTAL_EN_PESOS, "field 'lblDataTotalPesos'");
        tenenciaFondosFragment.lblDataTotalDolares = (TextView) finder.findRequiredView(obj, R.id.F24_00_LBL_DATA_TOTAL_EN_DOLARES, "field 'lblDataTotalDolares'");
        tenenciaFondosFragment.lblCuentaTitulo = (TextView) finder.findRequiredView(obj, R.id.F24_00_LBL_CUENTA_TITULO, "field 'lblCuentaTitulo'");
        tenenciaFondosFragment.scrollTenencias = (ScrollView) finder.findRequiredView(obj, R.id.scrollTenencias, "field 'scrollTenencias'");
        tenenciaFondosFragment.vgSelectorAccountTenencias = (CustomSpinner) finder.findRequiredView(obj, R.id.F24_00_vgSelectorAccountTenencias, "field 'vgSelectorAccountTenencias'");
        tenenciaFondosFragment.rwLnImporte = (LinearLayout) finder.findRequiredView(obj, R.id.F24_00_RW_IMPORTES, "field 'rwLnImporte'");
        tenenciaFondosFragment.rwCaraTriste = (RelativeLayout) finder.findRequiredView(obj, R.id.F24_00_RLL_CARA_TRISTE, "field 'rwCaraTriste'");
        tenenciaFondosFragment.rwSignoMas = (RelativeLayout) finder.findRequiredView(obj, R.id.F24_00_RLL_SIGNO_MAS, "field 'rwSignoMas'");
        tenenciaFondosFragment.noTenenciasTextMessage = (HtmlTextView) finder.findRequiredView(obj, R.id.F24_00_signo_mas_txt, "field 'noTenenciasTextMessage'");
    }

    public static void reset(TenenciaFondosFragment tenenciaFondosFragment) {
        tenenciaFondosFragment.mainView = null;
        tenenciaFondosFragment.lytLista = null;
        tenenciaFondosFragment.lblDataLegalesCuenta = null;
        tenenciaFondosFragment.lblTxtErrorRes4 = null;
        tenenciaFondosFragment.btnSuscribirFondo = null;
        tenenciaFondosFragment.btnConoceMas = null;
        tenenciaFondosFragment.lblDataTotalPesos = null;
        tenenciaFondosFragment.lblDataTotalDolares = null;
        tenenciaFondosFragment.lblCuentaTitulo = null;
        tenenciaFondosFragment.scrollTenencias = null;
        tenenciaFondosFragment.vgSelectorAccountTenencias = null;
        tenenciaFondosFragment.rwLnImporte = null;
        tenenciaFondosFragment.rwCaraTriste = null;
        tenenciaFondosFragment.rwSignoMas = null;
        tenenciaFondosFragment.noTenenciasTextMessage = null;
    }
}
