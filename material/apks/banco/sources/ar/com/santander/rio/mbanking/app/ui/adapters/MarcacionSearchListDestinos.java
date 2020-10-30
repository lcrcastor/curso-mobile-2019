package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisBean;
import java.util.ArrayList;
import java.util.List;

public class MarcacionSearchListDestinos extends ArrayAdapter<PaisBean> {
    Context a;
    int b;
    List<PaisBean> c;
    PaisBean d;
    String e;
    ArrayList<Integer> f = new ArrayList<>();
    /* access modifiers changed from: private */
    public OnItemClickListener g;
    public List<String> mData;

    public interface OnItemClickListener {
        void OnItemClick(View view);
    }

    public MarcacionSearchListDestinos(Context context, int i, List<PaisBean> list) {
        super(context, i, list);
        this.a = context;
        this.b = i;
        this.c = list;
    }

    public void setFiltro(String str) {
        this.e = str;
        notifyDataSetChanged();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((Activity) this.a).getLayoutInflater().inflate(this.b, viewGroup, false);
            view.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (MarcacionSearchListDestinos.this.g != null) {
                        MarcacionSearchListDestinos.this.g.OnItemClick(view);
                    }
                }
            });
        }
        this.d = (PaisBean) this.c.get(i);
        this.d.getDescripcion().toLowerCase();
        String str = this.e;
        this.d = (PaisBean) this.c.get(i);
        TextView textView = (TextView) view.findViewById(R.id.F26_MARCACION_ROW_DESTINOS_DATA);
        view.setVisibility(0);
        textView.setText(Html.fromHtml(this.d.getDescripcion()).toString());
        return view;
    }

    public int getCount() {
        return this.c.size() - this.f.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.g = onItemClickListener;
    }
}
