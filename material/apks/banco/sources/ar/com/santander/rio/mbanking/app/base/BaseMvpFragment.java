package ar.com.santander.rio.mbanking.app.base;

import android.preference.PreferenceManager;
import android.view.View;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class BaseMvpFragment extends BaseFragment implements IBaseView {
    protected View mActionBar;
    @Inject
    protected Bus mBus;
    @Inject
    protected IDataManager mDataManager;
    protected View mRootView;

    public void onStart() {
        super.onStart();
        attachView();
    }

    public void onStop() {
        super.onStop();
        detachView();
    }

    public void showOnBoarding(int i, int i2, int i3, String str) {
        if (!onBoardingAlreadyViewed(str).booleanValue()) {
            super.showOnBoarding(i, i2, i3, str);
        }
    }

    public Boolean onBoardingAlreadyViewed(String str) {
        return Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(str, false));
    }

    public void showProgressIndicator(String str) {
        showProgress(str);
    }

    public void dismissProgressIndicator() {
        dismissProgress();
    }
}
