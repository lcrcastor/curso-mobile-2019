package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.creditos.model.TasasProd;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

public class CreditosAdapterProd extends ArrayAdapter<TasasProd> {
    private List<TasasProd> a;
    private LayoutInflater b;
    private Context c;

    static class ViewHolder {
        @InjectView(2131364568)
        TextView cuotas;
        @InjectView(2131365830)
        TextView tasa;
        @InjectView(2131366035)
        TextView tipoTasa;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
        }
    }

    public CreditosAdapterProd(Context context, int i, List<TasasProd> list) {
        super(context, i, list);
        this.a = list;
        this.b = LayoutInflater.from(context);
        this.c = context;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        TasasProd tasasProd = (TasasProd) this.a.get(i);
        if (view != null) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            return view;
        } else if (tasasProd.getLeyenda() != null) {
            View inflate = this.b.inflate(R.layout.creditos_adapter_leyenda_prod, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.idLeyenda)).setText(Html.fromHtml(tasasProd.getLeyenda()));
            ((TextView) inflate.findViewById(R.id.idLeyenda)).setContentDescription(Html.fromHtml(tasasProd.getLeyenda()));
            return inflate;
        } else {
            View inflate2 = this.b.inflate(R.layout.creditos_adapter_prod, viewGroup, false);
            ViewHolder viewHolder2 = new ViewHolder(inflate2);
            inflate2.setTag(viewHolder2);
            viewHolder2.tipoTasa.setText(tasasProd.getTipo());
            TextView textView = viewHolder2.tipoTasa;
            String string = this.c.getString(R.string.ID125_CREDITS_MAIN_LBL_RATETYPE);
            StringBuilder sb = new StringBuilder();
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(tasasProd.getTipo());
            textView.setContentDescription(string.concat(sb.toString()));
            viewHolder2.tasa.setText(tasasProd.getTasaNominalAnual());
            try {
                CAccessibility.getInstance(this.c).applyFilterGeneral(viewHolder2.tasa.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolder2.cuotas.setText(tasasProd.getCuotas());
            TextView textView2 = viewHolder2.cuotas;
            String string2 = this.c.getString(R.string.ACCESSIBILITY_CREDITOS_CUOTAS_MAX_MIN);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(tasasProd.getCuotas());
            textView2.setContentDescription(string2.concat(sb2.toString()));
            return inflate2;
        }
    }
}
