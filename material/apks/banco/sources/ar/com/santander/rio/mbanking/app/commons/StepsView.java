package ar.com.santander.rio.mbanking.app.commons;

import android.view.View;

public interface StepsView {
    int getIndexViewPage(View view);

    void gotoPage(int i);

    void nextPage();

    void previousPage();

    void setModalInPageAnimation();

    void setModalOutPageAnimation();

    void setNextPageAnimation();

    void setPreviusPageAnimation();
}
