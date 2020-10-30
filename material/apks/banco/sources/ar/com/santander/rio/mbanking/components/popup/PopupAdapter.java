package ar.com.santander.rio.mbanking.components.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

public class PopupAdapter extends BaseAdapter {
    private List<ActionItem> a;
    private LayoutInflater b;

    static class ViewHolder {
        @InjectView(2131364872)
        ImageView img;
        @InjectView(2131366421)
        TextView text;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PopupAdapter(Context context, List<ActionItem> list) {
        this.a = list;
        this.b = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int i) {
        return this.a.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = this.b.inflate(R.layout.action_item_vertical, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        String title = ((ActionItem) this.a.get(i)).getTitle();
        Drawable icon = ((ActionItem) this.a.get(i)).getIcon();
        if (icon != null) {
            viewHolder.img.setImageDrawable(icon);
        } else {
            viewHolder.img.setVisibility(8);
        }
        if (title != null) {
            viewHolder.text.setText(title);
        } else {
            viewHolder.text.setVisibility(8);
        }
        return view;
    }
}
