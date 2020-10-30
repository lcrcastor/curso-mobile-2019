package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class RecargaCelularAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.vCompDesc = (TextView) finder.findRequiredView(obj, R.id.vCompDesc, "field 'vCompDesc'");
        viewHolder.vPhoneNumber = (TextView) finder.findRequiredView(obj, R.id.vPhoneNumber, "field 'vPhoneNumber'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.vCompDesc = null;
        viewHolder.vPhoneNumber = null;
    }
}
