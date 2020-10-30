package ar.com.santander.rio.mbanking.app.base;

import android.content.Intent;

public interface IBaseView {
    void attachView();

    void clearScreenData();

    void configureActionBar();

    void configureLayout();

    void detachView();

    void dismissProgressIndicator();

    void handleWSError(Intent intent);

    void initialize();

    void onBackPressed();

    void showProgressIndicator(String str);
}
