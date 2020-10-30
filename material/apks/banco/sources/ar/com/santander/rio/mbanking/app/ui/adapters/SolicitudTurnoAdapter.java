package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import java.util.List;

public class SolicitudTurnoAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    private List<Data> a;

    public static class Data {
        protected String description;
        protected int image;
        protected String subDescription;

        public Data() {
        }

        public Data(int i, String str) {
            this.image = i;
            this.description = str;
        }

        public Data(int i, String str, String str2) {
            this.image = i;
            this.description = str;
            this.subDescription = str2;
        }

        public int getImage() {
            return this.image;
        }

        public void setImage(int i) {
            this.image = i;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public String getSubDescription() {
            return this.subDescription;
        }

        public void setSubDescription(String str) {
            this.subDescription = str;
        }
    }

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public HtmlTextView m;
        /* access modifiers changed from: private */
        public HtmlTextView n;
        /* access modifiers changed from: private */
        public ImageView o;
        /* access modifiers changed from: private */
        public LinearLayout p;

        private ViewHolder(View view) {
            super(view);
            this.o = (ImageView) view.findViewById(R.id.ivItem);
            this.m = (HtmlTextView) view.findViewById(R.id.tvDescription);
            this.n = (HtmlTextView) view.findViewById(R.id.tvSubDescription);
            this.p = (LinearLayout) view.findViewById(R.id.layoutItemView);
        }
    }

    public SolicitudTurnoAdapter(List<Data> list) {
        this.a = list;
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_view_image_with_description, viewGroup, false));
    }

    @RequiresApi(api = 24)
    public void onBindViewHolder(@NonNull android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (!TextUtils.isEmpty(((Data) this.a.get(i)).getDescription())) {
            viewHolder2.m.setText(((Data) this.a.get(i)).getDescription());
            viewHolder2.o.setBackgroundResource(((Data) this.a.get(i)).getImage());
            String description = ((Data) this.a.get(i)).getDescription();
            if (!TextUtils.isEmpty(((Data) this.a.get(i)).getSubDescription())) {
                viewHolder2.n.setText(((Data) this.a.get(i)).getSubDescription());
                StringBuilder sb = new StringBuilder();
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(Html.fromHtml(((Data) this.a.get(i)).getSubDescription()));
                description = description.concat(sb.toString());
            } else {
                viewHolder2.n.setVisibility(8);
            }
            viewHolder2.p.setContentDescription(description);
            return;
        }
        viewHolder2.o.setVisibility(8);
    }

    public int getItemCount() {
        return this.a.size();
    }
}
