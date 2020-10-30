package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.TelephoneRow;
import butterknife.ButterKnife.Finder;

public class SucursalDetalleActivity$$ViewInjector {
    public static void inject(Finder finder, SucursalDetalleActivity sucursalDetalleActivity, Object obj) {
        sucursalDetalleActivity.txtDirec = (TextView) finder.findRequiredView(obj, R.id.txtDirec, "field 'txtDirec'");
        sucursalDetalleActivity.txtDist = (TextView) finder.findRequiredView(obj, R.id.txtDist, "field 'txtDist'");
        sucursalDetalleActivity.imgCajero = (ImageView) finder.findRequiredView(obj, R.id.imgCajero, "field 'imgCajero'");
        sucursalDetalleActivity.txtNombre = (TextView) finder.findRequiredView(obj, R.id.txtNombre, "field 'txtNombre'");
        sucursalDetalleActivity.telephoneRow = (TelephoneRow) finder.findRequiredView(obj, R.id.telephoneRow, "field 'telephoneRow'");
        sucursalDetalleActivity.txtDesc = (TextView) finder.findRequiredView(obj, R.id.txtDesc, "field 'txtDesc'");
    }

    public static void reset(SucursalDetalleActivity sucursalDetalleActivity) {
        sucursalDetalleActivity.txtDirec = null;
        sucursalDetalleActivity.txtDist = null;
        sucursalDetalleActivity.imgCajero = null;
        sucursalDetalleActivity.txtNombre = null;
        sucursalDetalleActivity.telephoneRow = null;
        sucursalDetalleActivity.txtDesc = null;
    }
}
