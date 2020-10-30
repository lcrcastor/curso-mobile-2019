package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import java.util.List;

public class ComprobanteTurnoAdapter extends Adapter<ViewHolder> {
    private List<Data> a;
    private Context b;
    private OnItemClickListener c;

    public static class ComprobanteItemHolder extends ViewHolder {
        /* access modifiers changed from: private */
        public HtmlTextView m;
        /* access modifiers changed from: private */
        public HtmlTextView n;
        /* access modifiers changed from: private */
        public EditText o;

        private ComprobanteItemHolder(View view) {
            super(view);
            this.m = (HtmlTextView) view.findViewById(R.id.label);
            this.n = (HtmlTextView) view.findViewById(R.id.tvValue);
            this.o = (EditText) view.findViewById(R.id.value);
        }
    }

    public static class Data {
        protected String leyendaBean;
        protected RowData rowData;

        public Data(RowData rowData2, String str) {
            this.rowData = rowData2;
            this.leyendaBean = str;
        }

        public Data() {
        }

        public RowData getRowData() {
            return this.rowData;
        }

        public void setRowData(RowData rowData2) {
            this.rowData = rowData2;
        }

        public String getLeyendaBean() {
            return this.leyendaBean;
        }

        public void setLeyendaBean(String str) {
            this.leyendaBean = str;
        }

        public int getType() {
            if (this.rowData != null) {
                return 0;
            }
            return this.leyendaBean != null ? 1 : 99;
        }
    }

    public static class EmptyViewHolder extends ViewHolder {
        EmptyViewHolder(View view) {
            super(view);
        }
    }

    public static class LeyendViewHolder extends ViewHolder {
        public TextView mDescription;

        LeyendViewHolder(View view) {
            super(view);
            this.mDescription = (TextView) view.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RowData rowData);
    }

    public static class RowData {
        protected String label;
        protected String value;

        public RowData(String str, String str2) {
            this.label = str;
            this.value = str2;
        }

        public RowData() {
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String str) {
            this.label = str;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String str) {
            this.value = str;
        }
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.c = onItemClickListener;
    }

    public ComprobanteTurnoAdapter(Context context, List<Data> list) {
        this.a = list;
        this.b = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        EmptyViewHolder emptyViewHolder = new EmptyViewHolder(new View(this.b));
        if (i == 0) {
            return new ComprobanteItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_row, viewGroup, false));
        }
        return i == 1 ? new LeyendViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_form_leyend_row, viewGroup, false)) : emptyViewHolder;
    }

    public int getItemViewType(int i) {
        return ((Data) this.a.get(i)).getType();
    }

    @RequiresApi(api = 24)
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ComprobanteItemHolder) {
            ComprobanteItemHolder comprobanteItemHolder = (ComprobanteItemHolder) viewHolder;
            comprobanteItemHolder.n.setVisibility(0);
            comprobanteItemHolder.o.setVisibility(8);
            comprobanteItemHolder.m.setText(((Data) this.a.get(i)).rowData.label);
            comprobanteItemHolder.n.setText(((Data) this.a.get(i)).rowData.value);
        } else if (viewHolder instanceof LeyendViewHolder) {
            ((LeyendViewHolder) viewHolder).mDescription.setText(Html.fromHtml(((Data) this.a.get(i)).leyendaBean).toString());
        }
    }

    public int getItemCount() {
        return this.a.size();
    }
}
