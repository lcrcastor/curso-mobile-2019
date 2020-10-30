package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Model.Favorito;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoritosAdapter extends BaseAdapter {
    private List<Favorito> a = new ArrayList();
    private List<Favorito> b;
    private Context c;

    class ViewHolder {
        ImageView a;
        TextView b;

        public ViewHolder(View view) {
            this.a = (ImageView) view.findViewById(R.id.iv_icon);
            this.b = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public FavoritosAdapter(Context context, List<Favorito> list) {
        this.c = context;
        this.b = list;
        this.a.addAll(list);
    }

    public int getCount() {
        return this.b.size();
    }

    public Favorito getItem(int i) {
        return (Favorito) this.b.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(this.c, R.layout.item_list_favorito, null);
            new ViewHolder(view);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Favorito item = getItem(i);
        viewHolder.a.setImageResource(R.drawable.ic_favoritos);
        viewHolder.b.setTypeface(Typeface.createFromAsset(this.c.getAssets(), "fonts/OpenSans-Regular.otf"));
        viewHolder.b.setText(item.nombre);
        return view;
    }

    public void filter(String str) {
        String lowerCase = str.toLowerCase(Locale.getDefault());
        this.b.clear();
        if (lowerCase.length() == 0) {
            this.b.addAll(this.a);
        } else {
            for (Favorito favorito : this.a) {
                if (favorito.nombre.toLowerCase().contains(lowerCase.toLowerCase())) {
                    this.b.add(favorito);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateList(List<Favorito> list) {
        a(list);
        notifyDataSetChanged();
    }

    private void a(List<Favorito> list) {
        this.a = new ArrayList(list.size());
        for (Favorito add : list) {
            this.a.add(add);
        }
    }
}
