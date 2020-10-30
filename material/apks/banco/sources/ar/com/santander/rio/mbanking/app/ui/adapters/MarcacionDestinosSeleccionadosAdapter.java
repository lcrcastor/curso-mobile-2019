package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisBean;
import java.util.List;

public class MarcacionDestinosSeleccionadosAdapter extends ArrayAdapter<PaisBean> {
    int a;
    int b;
    private Context c;
    private List<PaisBean> d;
    private PaisBean e;
    /* access modifiers changed from: private */
    public OnItemClickListener f;
    /* access modifiers changed from: private */
    public Boolean g = Boolean.valueOf(false);

    public interface OnItemClickListener {
        void OnItemClick(View view);
    }

    public MarcacionDestinosSeleccionadosAdapter(Context context, int i, List<PaisBean> list, int i2) {
        super(context, i, list);
        this.c = context;
        this.a = i;
        this.d = list;
        this.b = i2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((Activity) this.c).getLayoutInflater().inflate(this.a, viewGroup, false);
        }
        this.e = (PaisBean) this.d.get(i);
        TextView textView = (TextView) view.findViewById(R.id.F26_MARCACION_ROW_DESTINOS_SELECCIONADOS_DATA);
        Button button = (Button) view.findViewById(R.id.F26_MARCACION_LISTADESTINOS_CLEAR_BUTTON);
        if (this.b == 0 || this.b == 2) {
            button.setVisibility(8);
        }
        button.setText(Html.fromHtml(this.e.getDescripcion()).toString());
        button.setContentDescription(getContext().getString(R.string.IDXX_MARCACION_VIAJE_EDITAR_DESTINOS_BTN_ELIMINAR));
        textView.setText(Html.fromHtml(this.e.getDescripcion()).toString());
        button.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                if (MarcacionDestinosSeleccionadosAdapter.this.f != null) {
                    MarcacionDestinosSeleccionadosAdapter.this.g = Boolean.valueOf(true);
                    MarcacionDestinosSeleccionadosAdapter.this.f.OnItemClick(view);
                }
            }
        });
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.f = onItemClickListener;
    }
}
