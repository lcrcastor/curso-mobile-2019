package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SelectorEnvironmentFragment$$ViewInjector {
    public static void inject(Finder finder, SelectorEnvironmentFragment selectorEnvironmentFragment, Object obj) {
        selectorEnvironmentFragment.mSelectorEnvironment = (Spinner) finder.findRequiredView(obj, R.id.spSelectorEnvironment, "field 'mSelectorEnvironment'");
        selectorEnvironmentFragment.mEnvironmentType = (Spinner) finder.findRequiredView(obj, R.id.spEnvironment, "field 'mEnvironmentType'");
        selectorEnvironmentFragment.mEnvironmentToken = (Spinner) finder.findRequiredView(obj, R.id.spSelectorToken, "field 'mEnvironmentToken'");
        selectorEnvironmentFragment.rowToken = (LinearLayout) finder.findRequiredView(obj, R.id.rowLabelToken, "field 'rowToken'");
        selectorEnvironmentFragment.mInputUrl = (EditText) finder.findRequiredView(obj, R.id.etInputUrl, "field 'mInputUrl'");
        selectorEnvironmentFragment.mInputToken = (EditText) finder.findRequiredView(obj, R.id.etInputToken, "field 'mInputToken'");
    }

    public static void reset(SelectorEnvironmentFragment selectorEnvironmentFragment) {
        selectorEnvironmentFragment.mSelectorEnvironment = null;
        selectorEnvironmentFragment.mEnvironmentType = null;
        selectorEnvironmentFragment.mEnvironmentToken = null;
        selectorEnvironmentFragment.rowToken = null;
        selectorEnvironmentFragment.mInputUrl = null;
        selectorEnvironmentFragment.mInputToken = null;
    }
}
