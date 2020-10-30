package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ErrorFragment$$ViewInjector {
    public static void inject(Finder finder, ErrorFragment errorFragment, Object obj) {
        errorFragment.messageLayout = (RelativeLayout) finder.findRequiredView(obj, R.id.layout_ko_result, "field 'messageLayout'");
        errorFragment.messageTV = (TextView) finder.findRequiredView(obj, R.id.text_ko_result, "field 'messageTV'");
        errorFragment.messageIC = (ImageView) finder.findRequiredView(obj, R.id.imageView3, "field 'messageIC'");
        errorFragment.tittleTV = (TextView) finder.findRequiredView(obj, R.id.textViewTituloDelError, "field 'tittleTV'");
    }

    public static void reset(ErrorFragment errorFragment) {
        errorFragment.messageLayout = null;
        errorFragment.messageTV = null;
        errorFragment.messageIC = null;
        errorFragment.tittleTV = null;
    }
}
