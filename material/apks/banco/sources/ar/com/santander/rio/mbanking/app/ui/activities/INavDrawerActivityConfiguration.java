package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.BaseAdapter;

public interface INavDrawerActivityConfiguration {
    BaseAdapter getBaseAdapter();

    int getDrawerLayoutId();

    int getLeftDrawerId();

    int getMainLayout();

    String getSelectedOption();
}
