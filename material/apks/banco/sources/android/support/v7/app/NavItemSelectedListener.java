package android.support.v7.app;

import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

class NavItemSelectedListener implements OnItemSelectedListener {
    private final OnNavigationListener a;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public NavItemSelectedListener(OnNavigationListener onNavigationListener) {
        this.a = onNavigationListener;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.a != null) {
            this.a.onNavigationItemSelected(i, j);
        }
    }
}
