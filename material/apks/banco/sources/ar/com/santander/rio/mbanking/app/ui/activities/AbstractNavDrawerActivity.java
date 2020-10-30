package ar.com.santander.rio.mbanking.app.ui.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;

public abstract class AbstractNavDrawerActivity extends BaseActivity implements INavDrawerActivityConfiguration {
    private DrawerLayout p;
    private ActionBarDrawerToggle q;
    private ListView r;
    private BaseAdapter s;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getMainLayout());
        this.p = (DrawerLayout) findViewById(getDrawerLayoutId());
        this.r = (ListView) findViewById(getLeftDrawerId());
        this.s = getBaseAdapter();
        this.r.setAdapter(this.s);
        AnonymousClass1 r1 = new ActionBarDrawerToggle(this, this.p, R.string.ACCESSIBILITY_MENU_OPEN, R.string.ACCESSIBILITY_MENU_CLOSE) {
            public void onDrawerClosed(View view) {
                AbstractNavDrawerActivity.this.hideKeyboard();
            }

            public void onDrawerOpened(View view) {
                AbstractNavDrawerActivity.this.hideKeyboard();
            }
        };
        this.q = r1;
        this.p.setDrawerListener(this.q);
    }

    /* access modifiers changed from: protected */
    public DrawerLayout getDrawerLayout() {
        return this.p;
    }

    /* access modifiers changed from: protected */
    public ActionBarDrawerToggle getDrawerToggle() {
        return this.q;
    }

    /* access modifiers changed from: protected */
    public ListView getDrawerList() {
        return this.r;
    }

    public void openDrawer() {
        if (!this.p.isDrawerOpen((int) GravityCompat.START)) {
            this.p.openDrawer((int) GravityCompat.START);
        }
    }

    public void switchDrawer() {
        if (this.p.isDrawerOpen((int) GravityCompat.START)) {
            this.p.closeDrawer((int) GravityCompat.START);
        } else {
            this.p.openDrawer((int) GravityCompat.START);
        }
    }

    public void closeDrawer() {
        if (this.p.isDrawerOpen((int) GravityCompat.START)) {
            this.p.closeDrawer((int) GravityCompat.START);
        }
    }

    public void notifyDataSetChanged() {
        this.s.notifyDataSetChanged();
    }
}
