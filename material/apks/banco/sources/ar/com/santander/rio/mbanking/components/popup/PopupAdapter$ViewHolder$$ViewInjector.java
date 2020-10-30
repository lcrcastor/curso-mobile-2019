package ar.com.santander.rio.mbanking.components.popup;

import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class PopupAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.text = (TextView) finder.findRequiredView(obj, R.id.view_title, "field 'text'");
        viewHolder.img = (ImageView) finder.findRequiredView(obj, R.id.iv_icon, "field 'img'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.text = null;
        viewHolder.img = null;
    }
}
