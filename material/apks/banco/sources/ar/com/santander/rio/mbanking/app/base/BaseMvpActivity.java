package ar.com.santander.rio.mbanking.app.base;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class BaseMvpActivity extends BaseActivity implements IBaseView {
    protected View mActionBar;
    @Inject
    protected Bus mBus;
    @Inject
    public IDataManager mDataManager;

    public void onResume() {
        super.onResume();
        attachView();
    }

    public void onPause() {
        super.onPause();
        detachView();
    }

    public void showProgressIndicator(String str) {
        showProgress(str);
    }

    public void dismissProgressIndicator() {
        dismissProgress();
    }

    public void showOnBoarding(int i, int i2, int i3, String str) {
        if (!onBoardingAlreadyViewed(str).booleanValue()) {
            super.showOnBoarding(i, i2, i3, str);
        }
    }

    public Boolean onBoardingAlreadyViewed(String str) {
        return Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(str, false));
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }
}
