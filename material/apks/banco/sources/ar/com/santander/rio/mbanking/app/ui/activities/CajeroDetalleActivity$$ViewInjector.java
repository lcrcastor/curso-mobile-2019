package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class CajeroDetalleActivity$$ViewInjector {
    public static void inject(Finder finder, CajeroDetalleActivity cajeroDetalleActivity, Object obj) {
        cajeroDetalleActivity.txtDirec = (TextView) finder.findRequiredView(obj, R.id.txtDirec, "field 'txtDirec'");
        cajeroDetalleActivity.txtDist = (TextView) finder.findRequiredView(obj, R.id.txtDist, "field 'txtDist'");
        cajeroDetalleActivity.imgCajero = (ImageView) finder.findRequiredView(obj, R.id.imgCajero, "field 'imgCajero'");
        cajeroDetalleActivity.txtNombre = (TextView) finder.findRequiredView(obj, R.id.txtNombre, "field 'txtNombre'");
        cajeroDetalleActivity.txtEstado = (TextView) finder.findRequiredView(obj, R.id.txtEstado, "field 'txtEstado'");
        cajeroDetalleActivity.llDesc = (LinearLayout) finder.findRequiredView(obj, R.id.llDesc, "field 'llDesc'");
        cajeroDetalleActivity.llEstado = (LinearLayout) finder.findRequiredView(obj, R.id.llEstado, "field 'llEstado'");
        cajeroDetalleActivity.llExpende = (LinearLayout) finder.findRequiredView(obj, R.id.llExpende, "field 'llExpende'");
        cajeroDetalleActivity.txtDesc = (TextView) finder.findRequiredView(obj, R.id.txtDesc, "field 'txtDesc'");
        cajeroDetalleActivity.txtExpende = (TextView) finder.findRequiredView(obj, R.id.txtExpende, "field 'txtExpende'");
    }

    public static void reset(CajeroDetalleActivity cajeroDetalleActivity) {
        cajeroDetalleActivity.txtDirec = null;
        cajeroDetalleActivity.txtDist = null;
        cajeroDetalleActivity.imgCajero = null;
        cajeroDetalleActivity.txtNombre = null;
        cajeroDetalleActivity.txtEstado = null;
        cajeroDetalleActivity.llDesc = null;
        cajeroDetalleActivity.llEstado = null;
        cajeroDetalleActivity.llExpende = null;
        cajeroDetalleActivity.txtDesc = null;
        cajeroDetalleActivity.txtExpende = null;
    }
}
