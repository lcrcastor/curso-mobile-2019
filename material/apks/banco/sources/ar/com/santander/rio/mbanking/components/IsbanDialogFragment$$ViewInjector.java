package ar.com.santander.rio.mbanking.components;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class IsbanDialogFragment$$ViewInjector {
    public static void inject(Finder finder, IsbanDialogFragment isbanDialogFragment, Object obj) {
        isbanDialogFragment.optionButton = (Button) finder.findRequiredView(obj, R.id.oneOptionButton, "field 'optionButton'");
        isbanDialogFragment.positiveButton = (Button) finder.findRequiredView(obj, R.id.twoOptionsPositiveButton, "field 'positiveButton'");
        isbanDialogFragment.negativeButton = (Button) finder.findRequiredView(obj, R.id.twoOptionsNegativeButton, "field 'negativeButton'");
        isbanDialogFragment.threeOptionsButton1 = (Button) finder.findRequiredView(obj, R.id.threeOptionsButton1, "field 'threeOptionsButton1'");
        isbanDialogFragment.threeOptionsButton2 = (Button) finder.findRequiredView(obj, R.id.threeOptionsButton2, "field 'threeOptionsButton2'");
        isbanDialogFragment.threeOptionsButton3 = (Button) finder.findRequiredView(obj, R.id.threeOptionsButton3, "field 'threeOptionsButton3'");
        isbanDialogFragment.layoutTwoOption = (LinearLayout) finder.findRequiredView(obj, R.id.layout_twoOptions, "field 'layoutTwoOption'");
        isbanDialogFragment.layoutThreeOption = (LinearLayout) finder.findRequiredView(obj, R.id.layout_threeOptions, "field 'layoutThreeOption'");
        isbanDialogFragment.listOptions = (ListView) finder.findRequiredView(obj, R.id.listOptions, "field 'listOptions'");
        isbanDialogFragment.titleView = (TextView) finder.findRequiredView(obj, R.id.dialogTitle, "field 'titleView'");
        isbanDialogFragment.messageView = (TextView) finder.findRequiredView(obj, R.id.messageAlert, "field 'messageView'");
        isbanDialogFragment.vTitle = (LinearLayout) finder.findRequiredView(obj, R.id.idTitle, "field 'vTitle'");
        isbanDialogFragment.vTitleSeparator = finder.findRequiredView(obj, R.id.layout_list_title_separator, "field 'vTitleSeparator'");
    }

    public static void reset(IsbanDialogFragment isbanDialogFragment) {
        isbanDialogFragment.optionButton = null;
        isbanDialogFragment.positiveButton = null;
        isbanDialogFragment.negativeButton = null;
        isbanDialogFragment.threeOptionsButton1 = null;
        isbanDialogFragment.threeOptionsButton2 = null;
        isbanDialogFragment.threeOptionsButton3 = null;
        isbanDialogFragment.layoutTwoOption = null;
        isbanDialogFragment.layoutThreeOption = null;
        isbanDialogFragment.listOptions = null;
        isbanDialogFragment.titleView = null;
        isbanDialogFragment.messageView = null;
        isbanDialogFragment.vTitle = null;
        isbanDialogFragment.vTitleSeparator = null;
    }
}
