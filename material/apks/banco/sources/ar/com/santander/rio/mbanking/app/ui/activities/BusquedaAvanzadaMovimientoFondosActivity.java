package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.BusquedaAvanzadaMovimientoFondosPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.BusquedaAvanzadaMovimientoFondosView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

public class BusquedaAvanzadaMovimientoFondosActivity extends BaseMvpActivity implements OnClickListener, BusquedaAvanzadaMovimientoFondosView {
    @InjectView(2131362996)
    Button btnBuscar;
    @InjectView(2131362997)
    NumericEditText inpImporteDesde;
    @InjectView(2131362998)
    NumericEditText inpImporteHasta;
    @InjectView(2131362999)
    TextView lblDataFechaDesde;
    @InjectView(2131363000)
    TextView lblDataFechaHasta;
    @InjectView(2131363003)
    TextView lblImporte;
    Date p = null;
    Date q = null;
    private BusquedaAvanzadaMovimientoFondosPresenter r;
    private FondoBean s;
    private CuentaFondosBean t;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public void onPointerCaptureChanged(boolean z) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_busqueda_avanzanda_movimiento_fondo_activity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
    }

    public void initialize() {
        this.t = (CuentaFondosBean) getIntent().getParcelableExtra("CUENTA");
        this.s = (FondoBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_FONDO);
        this.r = new BusquedaAvanzadaMovimientoFondosPresenter(this.mBus, this.mDataManager, this);
        this.r.attachView(this);
        this.r.onCreate();
    }

    public void setBusquedaAvanzadaView() {
        String simbolCurrencyFromDescription = UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(this.s.getMoneda()).toString());
        TextView textView = this.lblImporte;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.F24_08_LBL_IMPORTE));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(simbolCurrencyFromDescription);
        textView.setText(sb.toString());
        try {
            this.lblImporte.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(this.lblImporte.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.inpImporteDesde.setHint(getString(R.string.F24_08_LBL_IMPORTE_DESDE));
        try {
            this.inpImporteDesde.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(simbolCurrencyFromDescription));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.inpImporteHasta.setHint(getString(R.string.F24_08_LBL_IMPORTE_HASTA));
        try {
            this.inpImporteHasta.setContentDescription(new CAccessibility(getBaseContext()).applyFilterGeneral(simbolCurrencyFromDescription));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.btnBuscar.setOnClickListener(this);
        this.lblDataFechaDesde.setOnClickListener(this);
        this.lblDataFechaHasta.setOnClickListener(this);
        Calendar instance = Calendar.getInstance();
        instance.add(5, -1);
        setFechaDesde(instance.getTime());
        setFechaHasta(instance.getTime());
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.CANCEL_WITH_CONFIRMAR);
        b();
        c();
    }

    private void b() {
        ImageView imageView = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.cancel_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BusquedaAvanzadaMovimientoFondosActivity.this.onBackPressed();
                }
            });
        }
    }

    private void c() {
        ImageView imageView = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.confirm_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BusquedaAvanzadaMovimientoFondosActivity.this.btnBuscar.callOnClick();
                }
            });
        }
    }

    public void onBackPressed() {
        setResult(0);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void attachView() {
        if (!this.r.isViewAttached()) {
            this.r.attachView(this);
        }
    }

    public void detachView() {
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 != R.id.F24_08_btn_buscar) {
            switch (id2) {
                case R.id.F24_08_lbl_data_fecha_desde /*2131362999*/:
                    BusquedaAvanzadaMovimientoFondosPresenter busquedaAvanzadaMovimientoFondosPresenter = this.r;
                    this.r.getClass();
                    busquedaAvanzadaMovimientoFondosPresenter.showSelectDateDialog("FECHA_DESDE", this.lblDataFechaDesde.getText().toString());
                    return;
                case R.id.F24_08_lbl_data_fecha_hasta /*2131363000*/:
                    BusquedaAvanzadaMovimientoFondosPresenter busquedaAvanzadaMovimientoFondosPresenter2 = this.r;
                    this.r.getClass();
                    busquedaAvanzadaMovimientoFondosPresenter2.showSelectDateDialog("FECHA_HASTA", this.lblDataFechaHasta.getText().toString());
                    return;
                default:
                    return;
            }
        } else if (a(Boolean.valueOf(true)).booleanValue()) {
            this.r.callGetMovimientos(this.s, this.t, a(this.lblDataFechaDesde), a(this.lblDataFechaHasta), UtilAmount.getAmount(this.inpImporteDesde.getFormatedText().toString()) == 0.0d ? "" : this.inpImporteDesde.getFormatedText().toString(), UtilAmount.getAmount(this.inpImporteHasta.getFormatedText().toString()) == 0.0d ? "" : this.inpImporteHasta.getFormatedText().toString());
        }
    }

    private Boolean a(Boolean bool) {
        if (b(Boolean.valueOf(false)).booleanValue() && c(Boolean.valueOf(false)).booleanValue()) {
            return Boolean.valueOf(true);
        }
        if (b(bool).booleanValue()) {
            c(bool);
        }
        return Boolean.valueOf(false);
    }

    private Boolean b(Boolean bool) {
        if (this.p == null || this.q == null || this.p.compareTo(this.q) <= 0) {
            return Boolean.valueOf(true);
        }
        if (bool.booleanValue()) {
            a(getString(R.string.USER200011));
        }
        return Boolean.valueOf(false);
    }

    private Boolean c(Boolean bool) {
        if (TextUtils.isEmpty(this.inpImporteDesde.getText().toString()) || TextUtils.isEmpty(this.inpImporteHasta.getText().toString()) || UtilAmount.getAmount(this.inpImporteDesde.getFormatedText().toString()) <= UtilAmount.getAmount(this.inpImporteHasta.getFormatedText().toString())) {
            return Boolean.valueOf(true);
        }
        if (bool.booleanValue()) {
            a(getString(R.string.USER200010));
        }
        return Boolean.valueOf(false);
    }

    private void a(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), str, null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public void setFechaDesde(Date date) {
        this.p = date;
        a(date, this.lblDataFechaDesde);
    }

    public void setFechaHasta(Date date) {
        this.q = date;
        a(date, this.lblDataFechaHasta);
    }

    private void a(Date date, TextView textView) {
        textView.setText(new DateTime((Object) date).toString(getString(R.string.FORMAT_FULL_DATE)));
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.CONTENT_FECHA));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(textView.getText().toString());
        textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
    }

    private String a(TextView textView) {
        return UtilDate.getDateFormat(textView.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP_2);
    }

    public void gotoMovimiento(List<MovimientoFondosBean> list) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_LST_MOVIMIENTOS, (ArrayList) list);
        setResult(-1, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
