package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasAdapter.CustodiasViewHolder;
import butterknife.ButterKnife.Finder;

public class CustodiasAdapter$CustodiasViewHolder$$ViewInjector {
    public static void inject(Finder finder, CustodiasViewHolder custodiasViewHolder, Object obj) {
        custodiasViewHolder.tvCustodiaDesc = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_desc, "field 'tvCustodiaDesc'");
        custodiasViewHolder.tvCustodiaTenenciaHoy = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_hoy, "field 'tvCustodiaTenenciaHoy'");
    }

    public static void reset(CustodiasViewHolder custodiasViewHolder) {
        custodiasViewHolder.tvCustodiaDesc = null;
        custodiasViewHolder.tvCustodiaTenenciaHoy = null;
    }
}
