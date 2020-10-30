package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Model.ISBANPopupMenuItem;
import java.util.ArrayList;

public class ISBANPopupMenuAdapter extends BaseAdapter {
    private Context a;
    private ArrayList<ISBANPopupMenuItem> b = new ArrayList<>();
    private LayoutInflater c;

    class ViewHolder {
        TextView a;

        private ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public ISBANPopupMenuAdapter(Context context, ArrayList<ISBANPopupMenuItem> arrayList) {
        this.a = context;
        this.b = arrayList;
        this.c = (LayoutInflater) this.a.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.b.size();
    }

    public ISBANPopupMenuItem getItem(int i) {
        return (ISBANPopupMenuItem) this.b.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int i2;
        Typeface typeface;
        if (view == null) {
            view = this.c.inflate(R.layout.layout_isban_menu, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.a = (TextView) view.findViewById(R.id.isban_menu_opcion);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ISBANPopupMenuItem iSBANPopupMenuItem = (ISBANPopupMenuItem) this.b.get(i);
        switch (iSBANPopupMenuItem.getMenuItemType()) {
            case 1:
                i2 = this.a.getResources().getColor(R.color.grey_medium_dark);
                typeface = Typeface.createFromAsset(this.a.getAssets(), "fonts/OpenSans-Bold.otf");
                break;
            case 2:
                typeface = Typeface.createFromAsset(this.a.getAssets(), "fonts/OpenSans-Regular.otf");
                i2 = this.a.getResources().getColor(R.color.grey_medium_dark);
                break;
            default:
                typeface = Typeface.createFromAsset(this.a.getAssets(), "fonts/OpenSans-Regular.otf");
                i2 = this.a.getResources().getColor(R.color.grey_medium_dark);
                break;
        }
        viewHolder.a.setTypeface(typeface);
        viewHolder.a.setTextColor(i2);
        viewHolder.a.setText(iSBANPopupMenuItem.getmMenuItemText());
        return view;
    }
}
