package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class InfoActivityWithHyperlink$$ViewInjector {
    public static void inject(Finder finder, InfoActivityWithHyperlink infoActivityWithHyperlink, Object obj) {
        infoActivityWithHyperlink.vTitle = (TextView) finder.findRequiredView(obj, R.id.info_activity_with_hyperlink_title, "field 'vTitle'");
        infoActivityWithHyperlink.vHyperlinks = (ListView) finder.findRequiredView(obj, R.id.info_activity_with_hyperlink_hyperlinks, "field 'vHyperlinks'");
    }

    public static void reset(InfoActivityWithHyperlink infoActivityWithHyperlink) {
        infoActivityWithHyperlink.vTitle = null;
        infoActivityWithHyperlink.vHyperlinks = null;
    }
}
