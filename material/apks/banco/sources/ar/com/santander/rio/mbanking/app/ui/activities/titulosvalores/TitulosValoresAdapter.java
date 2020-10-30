package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseViewHolder;
import ar.com.santander.rio.mbanking.services.model.general.TitulosValoresEnum;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Titulo;
import butterknife.InjectView;
import java.util.LinkedHashMap;
import java.util.List;

public class TitulosValoresAdapter extends Adapter<TitulosValoresViewHolder> {
    /* access modifiers changed from: private */
    public Context a;
    private TitulosValoresEnum[] b = TitulosValoresEnum.values();
    private LinkedHashMap<String, List<Titulo>> c = new LinkedHashMap<>();
    private ChildClickListener d;

    public interface ChildClickListener {
        void onClick(Titulo titulo);
    }

    public class TitulosValoresViewHolder extends BaseViewHolder {
        @InjectView(2131364904)
        LinearLayout layoutChild;
        @InjectView(2131364958)
        LinearLayout layoutParent;
        /* access modifiers changed from: private */
        public ChildClickListener n;
        @InjectView(2131366421)
        TextView tvTitle;

        public void onClick(View view) {
        }

        public TitulosValoresViewHolder(View view, ChildClickListener childClickListener) {
            super(view);
            this.n = childClickListener;
        }

        /* access modifiers changed from: private */
        public void a(String str, List<Titulo> list) {
            if (list != null) {
                this.layoutParent.setVisibility(0);
                this.tvTitle.setText(str);
                this.layoutChild.setVisibility(0);
                for (final Titulo titulo : list) {
                    View inflate = LayoutInflater.from(TitulosValoresAdapter.this.a).inflate(R.layout.item_child_titulos_valores, null);
                    if (!TextUtils.isEmpty(titulo.getCodEspecie())) {
                        TextView textView = (TextView) inflate.findViewById(R.id.tv_titulos_valores_desc);
                        StringBuilder sb = new StringBuilder();
                        sb.append(titulo.getDescripcion());
                        sb.append("\n");
                        sb.append(titulo.getCodEspecie());
                        textView.setText(sb.toString());
                    } else {
                        ((TextView) inflate.findViewById(R.id.tv_titulos_valores_desc)).setText(titulo.getDescripcion());
                    }
                    ((TextView) inflate.findViewById(R.id.tv_titulos_valores_tenencia_hoy)).setText(titulo.getTenValHoy());
                    inflate.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Position: ");
                            sb.append(TitulosValoresViewHolder.this.getAdapterPosition());
                            sb.append(" CodProducto: ");
                            sb.append(titulo.getDescripcion());
                            sb.append(titulo.getCodEspecie());
                            Log.d("Child:", sb.toString());
                            TitulosValoresViewHolder.this.n.onClick(titulo);
                        }
                    });
                    this.layoutChild.addView(inflate);
                }
                return;
            }
            this.layoutParent.setVisibility(8);
            this.layoutChild.setVisibility(8);
        }
    }

    public TitulosValoresAdapter(Context context, ChildClickListener childClickListener) {
        this.a = context;
        this.d = childClickListener;
    }

    public void setTitulosHashMap(LinkedHashMap<String, List<Titulo>> linkedHashMap) {
        this.c = linkedHashMap;
        notifyDataSetChanged();
    }

    public TitulosValoresViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TitulosValoresViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_parent_titulos_valores, viewGroup, false), this.d);
    }

    public void onBindViewHolder(TitulosValoresViewHolder titulosValoresViewHolder, int i) {
        titulosValoresViewHolder.a(this.b[i].getTitulo(this.a), (List) this.c.get(this.b[i].getCategoriaTitulo()));
    }

    public int getItemCount() {
        return TitulosValoresEnum.values().length;
    }
}
