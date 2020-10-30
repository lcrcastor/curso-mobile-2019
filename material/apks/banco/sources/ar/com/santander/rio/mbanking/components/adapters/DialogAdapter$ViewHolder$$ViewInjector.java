package ar.com.santander.rio.mbanking.components.adapters;

import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import butterknife.ButterKnife.Finder;

public class DialogAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.textOption = (HtmlTextView) finder.findRequiredView(obj, R.id.idTextOption, "field 'textOption'");
        viewHolder.radioButton = (ImageView) finder.findRequiredView(obj, R.id.radioButton, "field 'radioButton'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.textOption = null;
        viewHolder.radioButton = null;
    }
}
