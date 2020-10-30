package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;

public class IntervinientesAdapter extends Adapter<ViewHolder> {
    List<String> a = new ArrayList();

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @InjectView(2131366107)
        TextView tvText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject((Object) this, this.itemView);
        }

        public void bind(String str) {
            this.tvText.setText(str);
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_simple_text, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind((String) this.a.get(i));
    }

    public int getItemCount() {
        return this.a.size();
    }

    public void addItems(List<String> list) {
        this.a.addAll(list);
        notifyDataSetChanged();
    }
}
