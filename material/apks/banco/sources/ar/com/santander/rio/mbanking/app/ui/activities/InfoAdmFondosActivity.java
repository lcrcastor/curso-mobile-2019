package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.InfoAdmFondosPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.InfoAdmFondosView;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants.InfoAdmViewMode;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesAdmFondos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

public class InfoAdmFondosActivity extends BaseMvpActivity implements InfoAdmFondosView {
    @InjectView(2131363081)
    TextView lblLegales;
    @InjectView(2131363082)
    TextView lblNombre;
    @InjectView(2131363085)
    LinearLayout lnlHeaderHonorarios;
    @InjectView(2131363093)
    LinearLayout lnlHeaderHorarios;
    @InjectView(2131363086)
    TableLayout lstDatos;
    private InfoAdmFondosPresenter p;
    private String q;
    @InjectView(2131363084)
    TextView txtTitulo;

    public void clearScreenData() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.info_adm_fondos_activity);
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
    }

    public void initialize() {
        this.p = new InfoAdmFondosPresenter(this.mBus, this.mDataManager, this);
        this.p.attachView(this);
        this.q = getIntent().getStringExtra(FondosConstants.INTENT_EXTRA_INFOADM_VIEWMODE);
        LegalesAdmFondos legalesAdmFondos = (LegalesAdmFondos) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        this.p.onCreate(((ListaFondosBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDOS)).getFondosBean(), legalesAdmFondos, this.q);
    }

    public void setHonorariosFondoView(List<FondoBean> list, String str) {
        this.lstDatos.addView(a(list));
        this.lblLegales.setText(Html.fromHtml(str));
    }

    public void setHorariosFondoView(List<FondoBean> list, String str) {
        this.lstDatos.addView(b(list));
        this.lblLegales.setText(Html.fromHtml(str));
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE);
        b();
    }

    private void b() {
        ImageView imageView = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.ok);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    InfoAdmFondosActivity.this.finish();
                    InfoAdmFondosActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
                }
            });
        }
    }

    public void configureLayout() {
        if (this.q.equals(InfoAdmViewMode.VIEWMODE_HONORARIOS)) {
            this.txtTitulo.setText(getString(R.string.F24_14_LBL_TITLE));
            this.lnlHeaderHonorarios.setVisibility(0);
            this.lnlHeaderHorarios.setVisibility(8);
            this.lblLegales.setVisibility(0);
        } else if (this.q.equals(InfoAdmViewMode.VIEWMODE_HORARIOS)) {
            this.txtTitulo.setText(getString(R.string.ID_3911_FONDOS_LBL_HONORARIOS));
            this.lnlHeaderHonorarios.setVisibility(8);
            this.lnlHeaderHorarios.setVisibility(0);
            this.lblLegales.setVisibility(0);
        }
        try {
            this.lblNombre.setContentDescription(new CAccessibility(getApplicationContext()).applyFilterGeneral(this.lblNombre.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View a(List<FondoBean> list) {
        TableLayout tableLayout = new TableLayout(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService("layout_inflater");
        CAccessibility instance = CAccessibility.getInstance(this);
        for (FondoBean fondoBean : list) {
            LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_honorario_fondos, null);
            TextView textView = (TextView) linearLayout.findViewById(R.id.F24_14_LBL_DATA_NOMBRE);
            TextView textView2 = (TextView) linearLayout.findViewById(R.id.F24_14_LBL_DATA_ADMIN);
            TextView textView3 = (TextView) linearLayout.findViewById(R.id.F24_14_LBL_DATA_ENTRADA);
            TextView textView4 = (TextView) linearLayout.findViewById(R.id.F24_14_LBL_DATA_SALIDA);
            textView.setText(Html.fromHtml(fondoBean.getNombre()));
            textView2.setText(fondoBean.getHonorarios().getAdmin());
            textView3.setText(fondoBean.getHonorarios().getEntrada());
            textView4.setText(fondoBean.getHonorarios().getSalida());
            try {
                textView.setContentDescription(FondosConstants.applyAccesibilityFilterName(getBaseContext(), textView.getText().toString()));
                textView2.setContentDescription(instance.applyFilterTasaValue(textView2.getText().toString()));
                textView3.setContentDescription(instance.applyFilterTasaValue(textView3.getText().toString()));
                textView4.setContentDescription(instance.applyFilterTasaValue(textView4.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tableLayout.addView(linearLayout);
        }
        return tableLayout;
    }

    private View b(List<FondoBean> list) {
        TableLayout tableLayout = new TableLayout(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService("layout_inflater");
        CAccessibility instance = CAccessibility.getInstance(this);
        for (FondoBean fondoBean : list) {
            if (!fondoBean.getHorarios().getApertura().isEmpty()) {
                LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_horario_fondos, null);
                TextView textView = (TextView) linearLayout.findViewById(R.id.F24_15_LBL_DATA_NOMBRE);
                TextView textView2 = (TextView) linearLayout.findViewById(R.id.F24_15_LBL_DATA_APERTURA);
                TextView textView3 = (TextView) linearLayout.findViewById(R.id.F24_15_LBL_DATA_CIERRE);
                textView.setText(Html.fromHtml(fondoBean.getNombre()));
                textView2.setText(fondoBean.getHorarios().getApertura());
                textView3.setText(fondoBean.getHorarios().getCierre());
                try {
                    textView.setContentDescription(FondosConstants.applyAccesibilityFilterName(getBaseContext(), textView.getText().toString()));
                    textView2.setContentDescription(instance.applyFilterTime(textView2.getText().toString()));
                    textView3.setContentDescription(instance.applyFilterTime(textView3.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tableLayout.addView(linearLayout);
            }
        }
        return tableLayout;
    }

    public void attachView() {
        if (!this.p.isViewAttached()) {
            this.p.attachView(this);
        }
    }

    public void detachView() {
        if (this.p.isViewAttached()) {
            this.p.detachView();
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }
}
