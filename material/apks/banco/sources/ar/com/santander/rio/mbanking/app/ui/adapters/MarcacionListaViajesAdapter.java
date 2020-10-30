package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import java.util.List;

public class MarcacionListaViajesAdapter extends ArrayAdapter<ViajeBean> {
    Context a;
    private int b;
    private List<ViajeBean> c;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public MarcacionListaViajesAdapter(Context context, int i, List<ViajeBean> list) {
        super(context, i, list);
        this.a = context;
        this.b = i;
        this.c = list;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((Activity) this.a).getLayoutInflater().inflate(this.b, viewGroup, false);
        }
        ViajeBean viajeBean = (ViajeBean) this.c.get(i);
        TextView textView = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_DATA_FECHA_DIA);
        TextView textView2 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_DATA_FECHA_MES);
        TextView textView3 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_DATA_CANTIDAD_DIAS);
        TextView textView4 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_LBL_DIAS);
        TextView textView5 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_DATA_CANTIDAD_DESTINOS);
        TextView textView6 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_LBL_DESTINOS);
        TextView textView7 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_DATA_CANTIDAD_TARJETAS);
        TextView textView8 = (TextView) view.findViewById(R.id.F26_LISTA_VIAJE_FRAGMENT_LBL_TARJETAS);
        textView.setText(viajeBean.getFechaInicio().substring(0, 2));
        textView2.setText(UtilDate.getMonthFromNumber(viajeBean.getFechaInicio().substring(3, 5)));
        textView3.setText(String.valueOf(viajeBean.getTotalDias()));
        if (Integer.valueOf(viajeBean.getTotalDias()).intValue() == 1) {
            textView4.setText(this.a.getString(R.string.F26_00_ITEM_DIA));
        } else {
            textView4.setText(this.a.getString(R.string.F26_00_ITEM_DIAS));
        }
        textView5.setText(String.valueOf(viajeBean.getTotalDestinos()));
        if (Integer.valueOf(viajeBean.getTotalDestinos()).intValue() == 1) {
            textView6.setText(this.a.getString(R.string.F26_00_ITEM_DESTINO));
        } else {
            textView6.setText(this.a.getString(R.string.F26_00_ITEM_DESTINOS));
        }
        textView7.setText(String.valueOf(viajeBean.getTotalTarjetas()));
        if (Integer.valueOf(viajeBean.getTotalTarjetas()).intValue() == 1) {
            textView8.setText(this.a.getString(R.string.F26_00_ITEM_TARJETA));
        } else {
            textView8.setText(this.a.getString(R.string.F26_00_ITEM_TARJETAS));
        }
        try {
            textView.setContentDescription(CAccessibility.getInstance(this.a).applyFilterDate(viajeBean.getFechaInicio()));
            textView3.setContentDescription(CAccessibility.getInstance(this.a).applyFilterNumberOne(textView3.getText().toString()));
            textView5.setContentDescription(CAccessibility.getInstance(this.a).applyFilterNumberOne(textView5.getText().toString()));
            textView7.setContentDescription(CAccessibility.getInstance(this.a).applyFilterNumberOne(textView7.getText().toString()));
        } catch (Exception unused) {
        }
        return view;
    }
}
