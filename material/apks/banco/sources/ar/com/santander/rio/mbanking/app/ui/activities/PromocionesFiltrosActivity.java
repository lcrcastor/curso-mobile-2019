package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
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
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.jmperezra.accordion_multilevel.items.styles.HeadItemStyle;
import com.jmperezra.accordion_multilevel.widget.AccordionMultilevel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.inject.Inject;

public class PromocionesFiltrosActivity extends BaseActivity {
    public static final String FRAGMENT_KEY = "arrayidCV";
    @InjectView(2131365278)
    ImageView okMenu;
    HorizontalScrollList p;
    @Inject
    SessionManager q;
    @Inject
    AnalyticsManager r;
    private AccordionMultilevel s;
    private AccordionMultilevel t;
    /* access modifiers changed from: private */
    public LinkedHashMap<String, Filtro> u;

    public class CustomComparator implements Comparator<Filtro> {
        public CustomComparator() {
        }

        public int compare(Filtro filtro, Filtro filtro2) {
            return Integer.valueOf(filtro.clasificatorBean.order).compareTo(Integer.valueOf(filtro2.clasificatorBean.order));
        }
    }

    @OnClick({2131366059})
    public void onBackPressed() {
        setResult(0);
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        setContentView((int) R.layout.filtros_promociones_fragment);
        this.r.trackScreen(getString(R.string.analytics_screen_name_promociones_filtros));
        this.p = (HorizontalScrollList) findViewById(R.id.tabSelector);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayUseLogoEnabled(false);
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.actionbar_back, null);
        ButterKnife.inject((Object) this, inflate);
        supportActionBar.setCustomView(inflate, new LayoutParams(-1, -1));
        this.okMenu.setVisibility(0);
        b();
        c();
        d();
    }

    private void b() {
        if (this.q.getFiltrosDias() == null) {
            this.u = new LinkedHashMap<>();
            ArrayList<Filtro> arrayList = new ArrayList<>();
            for (ClasificatorValueBean clasificatorValueBean : this.q.getGetClasificadores().clasificadorValores.clasificatorValueBean) {
                if (Integer.valueOf(clasificatorValueBean.idClasificator).intValue() == 4) {
                    Filtro filtro = new Filtro(clasificatorValueBean);
                    filtro.setSelected(false);
                    arrayList.add(filtro);
                }
            }
            Collections.sort(arrayList, new CustomComparator());
            for (Filtro filtro2 : arrayList) {
                this.u.put(filtro2.getLabel(), filtro2);
            }
        } else {
            this.u = this.q.getFiltrosDias();
        }
        for (Filtro filtro3 : this.u.values()) {
            this.p.addItem(filtro3.getLabel(), filtro3.isSelected(), (int) R.color.grey_light);
        }
        this.p.setHorizontalScrollListener(new IHorizontalScrollListListener() {
            public void OnNewItemSelected(ToggleItem toggleItem) {
            }

            public void OnCheckedChangeListener(List<ToggleItem> list) {
                for (ToggleItem toggleItem : list) {
                    ((Filtro) PromocionesFiltrosActivity.this.u.get(toggleItem.getLabel())).setSelected(toggleItem.isStatus());
                }
            }
        });
        this.p.setMultipleSelection(true);
        this.p.show();
    }

    private void c() {
        List arrayList = new ArrayList();
        if (this.q.getFiltrosPromocionesMPago() == null) {
            ArrayList<Filtro> arrayList2 = new ArrayList<>();
            ArrayList<Filtro> arrayList3 = new ArrayList<>();
            for (ClasificatorValueBean clasificatorValueBean : this.q.getGetClasificadores().clasificadorValores.clasificatorValueBean) {
                if (Integer.valueOf(clasificatorValueBean.idClasificator).intValue() == 2) {
                    if (clasificatorValueBean.idParent != null) {
                        Filtro filtro = new Filtro(clasificatorValueBean);
                        filtro.setSelected(false);
                        arrayList3.add(filtro);
                    } else {
                        Filtro filtro2 = new Filtro(clasificatorValueBean);
                        filtro2.setSelected(false);
                        arrayList2.add(filtro2);
                    }
                }
            }
            Collections.sort(arrayList2, new CustomComparator());
            Collections.sort(arrayList3, new CustomComparator());
            for (Filtro filtro3 : arrayList3) {
                for (Filtro filtro4 : arrayList2) {
                    if (filtro3.clasificatorBean.idParent.equals(filtro4.clasificatorBean.f256id)) {
                        filtro4.addChild(filtro3);
                    }
                }
            }
            Filtro filtro5 = new Filtro();
            filtro5.clasificatorBean.name = getString(R.string.TODOS_MEDIOS_PAGO);
            filtro5.setSelected(false);
            filtro5.setStyleItem(new HeadItemStyle());
            arrayList.add(0, filtro5);
            for (Filtro addChild : arrayList2) {
                filtro5.addChild(addChild);
            }
        } else {
            arrayList = this.q.getFiltrosPromocionesMPago();
        }
        this.s = (AccordionMultilevel) findViewById(R.id.idAccordionMPago);
        this.s.setBuilderListener(new BuildAccordionMultilevelListenerImpl(getSupportFragmentManager()));
        this.s.build(arrayList);
    }

    private void d() {
        List arrayList = new ArrayList();
        if (this.q.getFiltrosPromocionesRublos() == null) {
            ArrayList<Filtro> arrayList2 = new ArrayList<>();
            ArrayList<Filtro> arrayList3 = new ArrayList<>();
            for (ClasificatorValueBean clasificatorValueBean : this.q.getGetClasificadores().clasificadorValores.clasificatorValueBean) {
                if (Integer.valueOf(clasificatorValueBean.idClasificator).intValue() == 3) {
                    if (clasificatorValueBean.idParent != null) {
                        Filtro filtro = new Filtro(clasificatorValueBean);
                        filtro.setSelected(false);
                        arrayList3.add(filtro);
                    } else {
                        Filtro filtro2 = new Filtro(clasificatorValueBean);
                        filtro2.setSelected(false);
                        arrayList2.add(filtro2);
                    }
                }
            }
            Collections.sort(arrayList2, new CustomComparator());
            Collections.sort(arrayList3, new CustomComparator());
            for (Filtro filtro3 : arrayList3) {
                for (Filtro filtro4 : arrayList2) {
                    if (filtro3.clasificatorBean.idParent.equals(filtro4.clasificatorBean.f256id)) {
                        filtro4.addChild(filtro3);
                    }
                }
            }
            Filtro filtro5 = new Filtro();
            filtro5.clasificatorBean.name = getString(R.string.TODOS_RUBROS);
            filtro5.setSelected(false);
            filtro5.setStyleItem(new HeadItemStyle());
            arrayList.add(0, filtro5);
            for (Filtro addChild : arrayList2) {
                filtro5.addChild(addChild);
            }
        } else {
            arrayList = this.q.getFiltrosPromocionesRublos();
        }
        this.t = (AccordionMultilevel) findViewById(R.id.idAccordionRublos);
        this.t.setBuilderListener(new BuildAccordionMultilevelListenerImpl(getSupportFragmentManager()));
        this.t.build(arrayList);
    }

    @OnClick({2131365278})
    public void onAceptar(View view) {
        this.q.setFiltrosPromocionesMPago(this.s.getStateItems());
        this.q.setFiltrosPromocionesRublos(this.t.getStateItems());
        this.q.setFiltrosDias(this.u);
        List itemsSelected = this.s.getItemsSelected();
        Intent intent = new Intent();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < itemsSelected.size(); i++) {
            String str = ((Filtro) itemsSelected.get(i)).clasificatorBean.f256id;
            String str2 = ((Filtro) itemsSelected.get(i)).clasificatorBean.name;
            if (str != null) {
                arrayList.add(str);
                arrayList2.add(str2);
            }
        }
        List itemsSelected2 = this.t.getItemsSelected();
        for (int i2 = 0; i2 < itemsSelected2.size(); i2++) {
            String str3 = ((Filtro) itemsSelected2.get(i2)).clasificatorBean.f256id;
            String str4 = ((Filtro) itemsSelected2.get(i2)).clasificatorBean.name;
            if (str3 != null) {
                arrayList.add(str3);
                arrayList2.add(str4);
            }
        }
        for (Filtro filtro : this.u.values()) {
            if (filtro.isSelected()) {
                arrayList.add(filtro.clasificatorBean.f256id);
                arrayList2.add(filtro.clasificatorBean.name);
            }
        }
        this.r.trackEvent(getString(R.string.analytics_category_promociones), getString(R.string.analytics_action_promociones_filtros), this.r.formatIDFilters(arrayList2));
        intent.putStringArrayListExtra("arrayidCV", arrayList);
        setResult(-1, intent);
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
