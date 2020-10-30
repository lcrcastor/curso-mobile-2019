package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseViewHolder;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;

public class TitulosValoresDetailAdapter extends Adapter<TitulosValoresDetailViewHolder> {
    private List<String> a = new ArrayList();
    private ChildClickListener b;
    private List<TitulosValoresViewElements> c = new ArrayList();

    public interface ChildClickListener {
        void onClick(View view, int i);
    }

    public class TitulosValoresDetailViewHolder extends BaseViewHolder {
        @InjectView(2131364962)
        LinearLayout layoutProducto;
        private ChildClickListener n;
        @InjectView(2131366259)
        TextView tvTitulosValoresDesc;
        @InjectView(2131366261)
        TextView tvTitulosValoresValue;

        public void onClick(View view) {
        }

        public TitulosValoresDetailViewHolder(View view, ChildClickListener childClickListener) {
            super(view);
            this.n = childClickListener;
        }

        /* access modifiers changed from: private */
        public void a(String str, String str2) {
            if (!TextUtils.isEmpty(str2)) {
                this.layoutProducto.setVisibility(0);
                this.tvTitulosValoresDesc.setText(str);
                this.tvTitulosValoresValue.setText(Html.fromHtml(str2));
                return;
            }
            this.layoutProducto.setVisibility(8);
        }
    }

    public TitulosValoresDetailAdapter(ChildClickListener childClickListener) {
        this.b = childClickListener;
    }

    public void setValues(List<String> list, List<TitulosValoresViewElements> list2) {
        this.a.clear();
        this.a.addAll(list);
        this.c.clear();
        this.c.addAll(list2);
        notifyDataSetChanged();
    }

    public TitulosValoresDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TitulosValoresDetailViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_titulos_valores_detail, viewGroup, false), this.b);
    }

    public void onBindViewHolder(TitulosValoresDetailViewHolder titulosValoresDetailViewHolder, int i) {
        titulosValoresDetailViewHolder.a(((TitulosValoresViewElements) this.c.get(i)).getTitle(), (String) this.a.get(i));
    }

    public int getItemCount() {
        return this.a.size();
    }
}
