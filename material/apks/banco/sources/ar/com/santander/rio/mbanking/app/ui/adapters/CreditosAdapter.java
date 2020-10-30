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
import ar.com.santander.rio.mbanking.app.module.creditos.model.Tasas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

public class CreditosAdapter extends ArrayAdapter<Tasas> {
    private Context a;
    private List<Tasas> b;
    private LayoutInflater c;

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

    public CreditosAdapter(Context context, int i, List<Tasas> list) {
        super(context, i, list);
        this.b = list;
        this.c = LayoutInflater.from(context);
        this.a = context;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Tasas tasas = (Tasas) this.b.get(i);
        if (view != null) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            return view;
        } else if (tasas.getLeyenda() != null) {
            View inflate = this.c.inflate(R.layout.creditos_adapter_leyenda, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.idLeyenda)).setText(Html.fromHtml(tasas.getLeyenda()));
            ((TextView) inflate.findViewById(R.id.idLeyenda)).setContentDescription(Html.fromHtml(tasas.getLeyenda()));
            return inflate;
        } else {
            View inflate2 = this.c.inflate(R.layout.creditos_adapter, viewGroup, false);
            ViewHolder viewHolder2 = new ViewHolder(inflate2);
            inflate2.setTag(viewHolder2);
            viewHolder2.tipoTasa.setText(tasas.getTipo());
            viewHolder2.tipoTasa.setContentDescription("Tipo prestamo, ".concat(tasas.getTipo().concat(".")));
            String tasaNominalAnual = tasas.getTasaNominalAnual();
            viewHolder2.tasa.setText(tasaNominalAnual);
            try {
                viewHolder2.tasa.setContentDescription(CAccessibility.getInstance(this.a).applyFilterTasaValue(this.a.getString(R.string.ID126_CREDITS_MAIN_LBL_YEARLYRATE).concat(tasaNominalAnual)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String cuotas = tasas.getCuotas();
            viewHolder2.cuotas.setText(cuotas);
            try {
                viewHolder2.cuotas.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterCantidadDeCuotas(". Cantidad de cuotas mínima y máxima, de ".concat(cuotas)));
                return inflate2;
            } catch (Exception e2) {
                e2.printStackTrace();
                return inflate2;
            }
        }
    }
}
