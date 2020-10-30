package ar.com.santander.rio.mbanking.app.base;

import android.support.v4.app.Fragment;

public interface IFragmentBase {
    void backToPrincipalFragment();

    void changeFragment(Fragment fragment, String str);

    void configureBackActionBar();

    void dismissProgressBar();

    void showProgressBar(String str);
}
