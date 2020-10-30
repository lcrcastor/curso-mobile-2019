package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ProgressBar;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SplashScreenActivity$$ViewInjector {
    public static void inject(Finder finder, SplashScreenActivity splashScreenActivity, Object obj) {
        splashScreenActivity.mSplashLegalText = (TextView) finder.findRequiredView(obj, R.id.splashTextoLegal, "field 'mSplashLegalText'");
        splashScreenActivity.mLogoCompany = finder.findRequiredView(obj, R.id.logo, "field 'mLogoCompany'");
        splashScreenActivity.mockVersion = (TextView) finder.findRequiredView(obj, R.id.f5mockVersin, "field 'mockVersion'");
        splashScreenActivity.progressBar = (ProgressBar) finder.findRequiredView(obj, R.id.progressBar, "field 'progressBar'");
    }

    public static void reset(SplashScreenActivity splashScreenActivity) {
        splashScreenActivity.mSplashLegalText = null;
        splashScreenActivity.mLogoCompany = null;
        splashScreenActivity.mockVersion = null;
        splashScreenActivity.progressBar = null;
    }
}
