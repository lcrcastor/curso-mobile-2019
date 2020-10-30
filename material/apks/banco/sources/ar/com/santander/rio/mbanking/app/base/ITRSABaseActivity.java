package ar.com.santander.rio.mbanking.app.base;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter.MenuActionsListener;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.crashlytics.android.Crashlytics;

public class ITRSABaseActivity extends BaseActivity implements DrawerListener, OnItemClickListener, MenuActionsListener {
    private ActionBarDrawerToggle p;
    private boolean q = false;
    private DrawerLayout r;

    public void onClickItem(int i) {
    }

    public void onDrawerClosed(View view) {
    }

    public void onDrawerOpened(View view) {
    }

    public void onDrawerSlide(View view, float f) {
    }

    public void onDrawerStateChanged(int i) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        getEventBus().unregister(this);
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
        getEventBus().register(this);
    }

    /* access modifiers changed from: protected */
    public void initializePrivateMenuDrawer(DrawerLayout drawerLayout) {
        this.r = drawerLayout;
        this.q = true;
        ListView listView = (ListView) this.r.findViewById(R.id.ID16_PRIVATEMENU_BTN_CREDITS);
        this.p = new ActionBarDrawerToggle(this, this.r, R.string.ACCESSIBILITY_MENU_OPEN, R.string.ACCESSIBILITY_MENU_CLOSE);
        DatosPersonales datosPersonales = this.o.getLoginUnico().getDatosPersonales();
        int parseInt = (this.o.getLoginUnico().getCrm() == null || !UtilString.isNumber(this.o.getLoginUnico().getCrm().cantidad)) ? 0 : Integer.parseInt(this.o.getLoginUnico().getCrm().cantidad);
        String str = "";
        if (!datosPersonales.getSegmento().isEmpty() && !datosPersonales.getUrlSegmento().isEmpty()) {
            str = datosPersonales.getSegmento();
            Crashlytics.setString("segmento", str);
        }
        String str2 = str;
        StringBuilder sb = new StringBuilder();
        sb.append(datosPersonales.getNombre());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(datosPersonales.getApellido());
        listView.setAdapter(createMenuPrivadoAdapter(parseInt, sb.toString(), datosPersonales.getUltimoAcceso(), str2, this));
        listView.setOnItemClickListener(this);
        this.r.addDrawerListener(this);
    }

    public void lockMenu(boolean z) {
        if (this.r == null) {
            return;
        }
        if (z) {
            this.r.setDrawerLockMode(1);
        } else {
            this.r.setDrawerLockMode(0);
        }
    }

    public void closeDrawer() {
        if (this.r.isDrawerOpen((int) GravityCompat.START)) {
            this.r.closeDrawer((int) GravityCompat.START);
        }
    }

    public void switchDrawer() {
        if (this.r.isDrawerOpen((int) GravityCompat.START)) {
            this.r.closeDrawer((int) GravityCompat.START);
        } else {
            this.r.openDrawer((int) GravityCompat.START);
        }
    }
}
