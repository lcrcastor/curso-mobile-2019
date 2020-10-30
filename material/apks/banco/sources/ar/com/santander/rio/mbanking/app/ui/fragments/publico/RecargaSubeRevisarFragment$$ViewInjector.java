package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.support.constraint.ConstraintLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class RecargaSubeRevisarFragment$$ViewInjector {
    public static void inject(Finder finder, RecargaSubeRevisarFragment recargaSubeRevisarFragment, Object obj) {
        recargaSubeRevisarFragment.tvTarjeta = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta, "field 'tvTarjeta'");
        recargaSubeRevisarFragment.tvTarjetaNro = (TextView) finder.findRequiredView(obj, R.id.tv_tarjeta_nro, "field 'tvTarjetaNro'");
        recargaSubeRevisarFragment.tvImporteNro = (TextView) finder.findRequiredView(obj, R.id.tv_importe_nro, "field 'tvImporteNro'");
        recargaSubeRevisarFragment.tvCuentaNro = (TextView) finder.findRequiredView(obj, R.id.tv_cuenta_nro, "field 'tvCuentaNro'");
        recargaSubeRevisarFragment.progressBar = (ProgressBar) finder.findRequiredView(obj, R.id.progress_bar, "field 'progressBar'");
        recargaSubeRevisarFragment.btn = (TextView) finder.findRequiredView(obj, R.id.sube_next_button2, "field 'btn'");
        recargaSubeRevisarFragment.flBtnConfirmar = (FrameLayout) finder.findRequiredView(obj, R.id.fl_confirmar, "field 'flBtnConfirmar'");
        recargaSubeRevisarFragment.iconOk = (LinearLayout) finder.findRequiredView(obj, R.id.checkmark_imagen, "field 'iconOk'");
        recargaSubeRevisarFragment.ivItemButton = (ImageView) finder.findRequiredView(obj, R.id.iv_item_button, "field 'ivItemButton'");
        recargaSubeRevisarFragment.iconOkRounded = (ImageView) finder.findRequiredView(obj, R.id.checkmark_imagen_rounded, "field 'iconOkRounded'");
        recargaSubeRevisarFragment.clMain2 = (ConstraintLayout) finder.findRequiredView(obj, R.id.clMain2, "field 'clMain2'");
        recargaSubeRevisarFragment.clMain = (ConstraintLayout) finder.findRequiredView(obj, R.id.clMain, "field 'clMain'");
        recargaSubeRevisarFragment.actionBarLayout = (RelativeLayout) finder.findRequiredView(obj, R.id.recarga_sube_revisar_actionbar, "field 'actionBarLayout'");
    }

    public static void reset(RecargaSubeRevisarFragment recargaSubeRevisarFragment) {
        recargaSubeRevisarFragment.tvTarjeta = null;
        recargaSubeRevisarFragment.tvTarjetaNro = null;
        recargaSubeRevisarFragment.tvImporteNro = null;
        recargaSubeRevisarFragment.tvCuentaNro = null;
        recargaSubeRevisarFragment.progressBar = null;
        recargaSubeRevisarFragment.btn = null;
        recargaSubeRevisarFragment.flBtnConfirmar = null;
        recargaSubeRevisarFragment.iconOk = null;
        recargaSubeRevisarFragment.ivItemButton = null;
        recargaSubeRevisarFragment.iconOkRounded = null;
        recargaSubeRevisarFragment.clMain2 = null;
        recargaSubeRevisarFragment.clMain = null;
        recargaSubeRevisarFragment.actionBarLayout = null;
    }
}
