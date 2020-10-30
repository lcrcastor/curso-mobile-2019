package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View.OnClickListener;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail.TitulosValoresDetailContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail.TitulosValoresDetailContract.View;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Titulo;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;
import javax.inject.Inject;

public class TitulosValoresDetailActivity extends BaseMvpActivity implements View {
    public static final String TITULO_DESC_TAG = "desc_leyenda";
    public static final String TITULO_LEYENDA_TAG = "titulo_leyenda";
    public static final String TITULO_TAG = "titulo";
    @Inject
    public AnalyticsManager analyticsManager;
    @InjectView(2131366233)
    TextView getTvLabelTitulosCodEspecie;
    TitulosValoresDetailAdapter p;
    Presenter q;
    Titulo r;
    @InjectView(2131365506)
    RecyclerView rvTitulosValores;
    private String s;
    @InjectView(2131365637)
    NestedScrollView scrollView;
    private String t;
    @InjectView(2131366225)
    TextView tvDescriptionLeyenda;
    @InjectView(2131366234)
    TextView tvLabelTitulosValoresDesc;
    @InjectView(2131366235)
    TextView tvLabelTitulosValoresTenenciaValuada;
    @InjectView(2131366421)
    TextView tvTitle;
    @InjectView(2131366257)
    TextView tvTitleLeyenda;
    private int u;
    private InversionesAnalyticsImpl v;

    public void attachView() {
    }

    public void configureLayout() {
    }

    public void detachView() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_titulos_valores_detail);
        ButterKnife.inject((Activity) this);
        initialize();
        this.v = new InversionesAnalyticsImpl(this, this.analyticsManager);
        b();
    }

    private void b() {
        try {
            this.u = ((Integer) getIntent().getExtras().get("TIPO_CUENTA")).intValue();
            String obj = Html.fromHtml(this.r.getTipoEspecie()).toString();
            if (obj.contains("Acciones") && c()) {
                this.v.tenenciaTitulosValoresAccionDetallebp();
            } else if (obj.contains("Acciones") && !c()) {
                this.v.tenenciaTitulosValoresAccionDetallertl();
            } else if (obj.contains("Títulos") && c()) {
                this.v.tenenciaTitulosValoresBonoDetallebp();
            } else if (obj.contains("Títulos") && !c()) {
                this.v.tenenciaTitulosValoresBonoDetallertl();
            }
        } catch (Exception unused) {
        }
    }

    private boolean c() {
        return Integer.valueOf(this.u).intValue() >= 250 && Integer.valueOf(this.u).intValue() <= 259;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.q.detachView();
        super.onDestroy();
    }

    public void initialize() {
        this.q = new TitulosValoresDetailPresenter(this.mBus, this.mDataManager);
        this.q.attachView(this);
        this.tvTitle.setText(R.string.ID_4319_INVERSIONES_LBL_TITULOS_VALORES);
        d();
        getExtras();
        configureActionBar();
        this.scrollView.post(new Runnable() {
            public void run() {
                TitulosValoresDetailActivity.this.scrollView.scrollTo(0, 0);
            }
        });
    }

    public void getExtras() {
        this.r = (Titulo) getIntent().getParcelableExtra("titulo");
        this.s = getIntent().getStringExtra("titulo_leyenda");
        this.t = getIntent().getStringExtra("desc_leyenda");
        setLeyenda(this.s, this.t);
        this.q.getTitulo(this.r);
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        enableBackButton();
    }

    public void enableBackButton() {
        android.view.View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(android.view.View view) {
                    TitulosValoresDetailActivity.this.finish();
                    TitulosValoresDetailActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
    }

    public void setLeyenda(String str, String str2) {
        this.tvTitleLeyenda.setText(Html.fromHtml(str));
        this.tvTitleLeyenda.setVisibility(8);
        this.tvDescriptionLeyenda.setText(Html.fromHtml(str2));
    }

    private void d() {
        this.p = new TitulosValoresDetailAdapter(null);
        this.rvTitulosValores.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.rvTitulosValores.setAdapter(this.p);
    }

    public void setHeaderData(String str, String str2, String str3) {
        this.tvLabelTitulosValoresDesc.setText(str2);
        this.getTvLabelTitulosCodEspecie.setText(str);
        this.tvLabelTitulosValoresTenenciaValuada.setText(str3);
    }

    public void setRecyclerViewData(List<String> list, List<TitulosValoresViewElements> list2) {
        this.p.setValues(list, list2);
    }
}
