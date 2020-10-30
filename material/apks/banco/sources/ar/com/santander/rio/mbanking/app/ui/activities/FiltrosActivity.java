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

public class FiltrosActivity extends BaseActivity {
    public static final String FRAGMENT_KEY = "arrayidCV";
    public static Filtro todosCajeros;
    @InjectView(2131365278)
    ImageView okMenu;
    @Inject
    SessionManager p;
    @Inject
    AnalyticsManager q;
    AccordionMultilevel r;

    @OnClick({2131366059})
    public void onBackPressed() {
        setResult(0);
        finish();
        c();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.filtros_fragment);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        this.q.trackScreen(getString(R.string.analytics_screen_name_cajeros_filtro));
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
    }

    private void b() {
        List arrayList = new ArrayList();
        if (this.p.getFiltrosTipoCajero() == null) {
            ArrayList<Filtro> arrayList2 = new ArrayList<>();
            ArrayList<Filtro> arrayList3 = new ArrayList<>();
            for (ClasificatorValueBean clasificatorValueBean : this.p.getGetClasificadores().clasificadorValores.clasificatorValueBean) {
                if (Integer.valueOf(clasificatorValueBean.idClasificator).intValue() == 5) {
                    if (clasificatorValueBean.idParent != null) {
                        Filtro filtro = new Filtro(clasificatorValueBean);
                        filtro.setSelected(true);
                        arrayList3.add(filtro);
                    } else {
                        Filtro filtro2 = new Filtro(clasificatorValueBean);
                        filtro2.setSelected(true);
                        arrayList2.add(filtro2);
                    }
                }
            }
            for (Filtro filtro3 : arrayList3) {
                for (Filtro filtro4 : arrayList2) {
                    if (filtro3.clasificatorBean.idParent.equals(filtro4.clasificatorBean.f256id)) {
                        filtro4.addChild(filtro3);
                    }
                }
            }
            Filtro filtro5 = new Filtro();
            filtro5.clasificatorBean.name = getString(R.string.TODOS_CAJEROS);
            filtro5.setSelected(true);
            filtro5.setStyleItem(new HeadItemStyle());
            arrayList.add(0, filtro5);
            for (Filtro addChild : arrayList2) {
                filtro5.addChild(addChild);
            }
        } else {
            arrayList = this.p.getFiltrosTipoCajero();
        }
        this.r = (AccordionMultilevel) findViewById(R.id.idAccordionMultilevel);
        this.r.setBuilderListener(new BuildAccordionMultilevelListenerImpl(getSupportFragmentManager()));
        this.r.build(arrayList);
    }

    @OnClick({2131365278})
    public void onAceptar(View view) {
        this.p.setFiltrosTipoCajero(this.r.getStateItems());
        List itemsSelected = this.r.getItemsSelected();
        Intent intent = new Intent();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < itemsSelected.size(); i++) {
            if (((ItemState) itemsSelected.get(i)).getChildren() == null || ((ItemState) itemsSelected.get(i)).getChildren().size() <= 0) {
                arrayList.add(((Filtro) itemsSelected.get(i)).clasificatorBean.f256id);
                arrayList2.add(((Filtro) itemsSelected.get(i)).clasificatorBean.name);
            } else {
                for (int i2 = 0; i2 < ((ItemState) itemsSelected.get(i)).getChildren().size(); i2++) {
                    if (((ItemState) ((ItemState) itemsSelected.get(i)).getChildren().get(i2)).isSelected()) {
                        arrayList.add(((Filtro) ((ItemState) itemsSelected.get(i)).getChildren().get(i2)).clasificatorBean.f256id);
                    }
                    arrayList2.add(((Filtro) ((ItemState) itemsSelected.get(i)).getChildren().get(i2)).clasificatorBean.name);
                }
            }
        }
        this.q.trackEvent(getString(R.string.analytics_category_cajeros), getString(R.string.analytics_action_cajeros_filtros), this.q.formatIDFilters(arrayList2));
        intent.putStringArrayListExtra("arrayidCV", arrayList);
        setResult(-1, intent);
        finish();
        c();
    }

    private void c() {
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
