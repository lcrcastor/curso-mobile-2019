package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ErrorActivity$$ViewInjector {
    public static void inject(Finder finder, ErrorActivity errorActivity, Object obj) {
        errorActivity.imgError = (ImageView) finder.findRequiredView(obj, R.id.imageView3, "field 'imgError'");
        errorActivity.txtMessage = (TextView) finder.findRequiredView(obj, R.id.text_ko_result, "field 'txtMessage'");
        errorActivity.tvErrorTitle = (TextView) finder.findRequiredView(obj, R.id.textViewTituloDelError, "field 'tvErrorTitle'");
        errorActivity.gralLayout = (RelativeLayout) finder.findRequiredView(obj, R.id.layout_ko_result, "field 'gralLayout'");
    }

    public static void reset(ErrorActivity errorActivity) {
        errorActivity.imgError = null;
        errorActivity.txtMessage = null;
        errorActivity.tvErrorTitle = null;
        errorActivity.gralLayout = null;
    }
}
