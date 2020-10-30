package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.commons.BuildAccordionMultilevelListenerImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Filtro;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ClasificatorValueBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.jmperezra.accordion_multilevel.items.ItemState;
import com.jmperezra.accordion_multilevel.items.styles.HeadItemStyle;
import com.jmperezra.accordion_multilevel.widget.AccordionMultilevel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ZonaActivity extends BaseActivity {
    public static final String INDEX_ZONA_ID = "index_zona_id";
    public static final String ZONA_ARRAY = "arrayIdCVZona";
    @InjectView(2131366059)
    ImageView okMenu;
    @Inject
    SessionManager p;
    @Inject
    AnalyticsManager q;
    AccordionMultilevel r;
    List<ItemState> s;

    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
        d();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        setContentView((int) R.layout.filtros_fragment);
        this.r = (AccordionMultilevel) findViewById(R.id.idAccordionMultilevel);
        b();
        this.s = c();
        h();
        this.q.trackScreen(getString(R.string.analytics_screen_name_ubicacion_filtros));
    }

    private void b() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayShowCustomEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayUseLogoEnabled(false);
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back, null);
            ButterKnife.inject((Object) this, inflate);
            supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
            this.okMenu.setVisibility(4);
        }
    }

    private List<ItemState> c() {
        List<ItemState> f = f();
        if (f != null || this.p.getGetClasificadores() == null) {
            return f;
        }
        List<ItemState> g = g();
        b(g);
        return g;
    }

    @OnClick({2131365278})
    public void onAceptar(View view) {
        b(this.s);
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ZONA_ARRAY, a(this.r.getItemsSelected()));
        setResult(-1, intent);
        d();
    }

    @OnClick({2131366059})
    public void onClose(View view) {
        setResult(0);
        d();
    }

    private void d() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    private ArrayList<String> a(List<ItemState> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        while (i < list.size()) {
            try {
                if (((ItemState) list.get(i)).isSelected()) {
                    arrayList.add(((Filtro) list.get(i)).clasificatorBean.f256id);
                }
                i++;
            } catch (Exception unused) {
                return arrayList;
            }
        }
        return arrayList;
    }

    private int e() {
        if (getIntent() == null || !getIntent().hasExtra(INDEX_ZONA_ID)) {
            return 0;
        }
        return getIntent().getIntExtra(INDEX_ZONA_ID, 0);
    }

    private void b(List<ItemState> list) {
        int e = e();
        if (e != 1) {
            switch (e) {
                case 7:
                    this.p.setFiltrosSucursales(list);
                    return;
                case 8:
                    this.p.setFiltrosCajero(list);
                    return;
                default:
                    return;
            }
        } else {
            this.p.setFiltrosPromociones(list);
        }
    }

    private List<ItemState> f() {
        int e = e();
        if (e == 1) {
            return this.p.getFiltrosPromociones();
        }
        switch (e) {
            case 7:
                return this.p.getFiltrosSucursales();
            case 8:
                return this.p.getFiltrosCajero();
            default:
                return null;
        }
    }

    private List<ItemState> g() {
        ArrayList arrayList = new ArrayList();
        try {
            int e = e();
            ArrayList<Filtro> arrayList2 = new ArrayList<>();
            ArrayList<Filtro> arrayList3 = new ArrayList<>();
            for (ClasificatorValueBean clasificatorValueBean : this.p.getGetClasificadores().clasificadorValores.clasificatorValueBean) {
                if (Integer.valueOf(clasificatorValueBean.idClasificator).intValue() == e) {
                    if (clasificatorValueBean.idParent != null) {
                        arrayList3.add(new Filtro(clasificatorValueBean));
                    } else {
                        arrayList2.add(new Filtro(clasificatorValueBean));
                    }
                }
            }
            for (Filtro filtro : arrayList3) {
                for (Filtro filtro2 : arrayList2) {
                    if (filtro.clasificatorBean.idParent.equals(filtro2.clasificatorBean.f256id)) {
                        filtro2.addChild(filtro);
                    }
                }
            }
            Filtro filtro3 = new Filtro();
            filtro3.clasificatorBean.name = getString(R.string.TODAS_ZONAS);
            filtro3.clasificatorBean.f256id = "8";
            filtro3.setStyleItem(new HeadItemStyle());
            for (Filtro addChild : arrayList2) {
                filtro3.addChild(addChild);
            }
            arrayList.add(0, filtro3);
        } catch (Exception unused) {
        }
        return arrayList;
    }

    private void h() {
        try {
            this.r.setBuilderListener(new BuildAccordionMultilevelListenerImpl(getSupportFragmentManager()));
            this.r.build(this.s);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error:");
            sb.append(th.getMessage());
            Log.e("@dev", sb.toString());
        }
    }
}
