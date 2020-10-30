package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.debin.BuscadorDebinPresenter;
import ar.com.santander.rio.mbanking.app.module.debin.BuscadorDebinView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConsultaTenenciaPreAutorizacionesRecibidasFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class BuscadorDebinActivity extends BaseMvpActivity implements OnClickListener, BuscadorDebinView {
    static final Boolean p = Boolean.valueOf(true);
    static final Boolean q = Boolean.valueOf(false);
    private String A;
    private List<ListTableBean> B = new ArrayList();
    /* access modifiers changed from: private */
    public List<ListGroupBean> C = new ArrayList();
    @InjectView(2131363715)
    Button btnBuscarDebin;
    @InjectView(2131363727)
    TextView estadoDebin;
    @InjectView(2131363728)
    TextView fechaDesde;
    @InjectView(2131363729)
    TextView fechaHasta;
    @InjectView(2131363718)
    TextView lblFechaDesde;
    @InjectView(2131363719)
    TextView lblFechaHasta;
    @InjectView(2131363723)
    TextView lblTitulo;
    String r = "DialogDate";
    @Inject
    SessionManager s;
    @InjectView(2131363724)
    RelativeLayout seleccionarEstado;
    @InjectView(2131363725)
    RelativeLayout seleccionarFechaDesde;
    @InjectView(2131363726)
    RelativeLayout seleccionarFechaHasta;
    @Inject
    AnalyticsManager t;
    private BuscadorDebinPresenter u;
    /* access modifiers changed from: private */
    public String v;
    /* access modifiers changed from: private */
    public String w;
    private List<ListDebinesBean> x = new ArrayList();
    private String y = "";
    /* access modifiers changed from: private */
    public String z;

    public void clearScreenData() {
    }

    public void configureActionBar() {
    }

    public void configureLayout() {
    }

    public void onClick(View view) {
    }

    public void setBackgroundVisibleInit() {
    }

    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_buscador_debin);
        ButterKnife.inject((Activity) this);
        initialize();
    }

    public void initialize() {
        this.u = new BuscadorDebinPresenter(this.mBus, this.mDataManager, this, this.s);
        this.u.attachView(this);
        this.B = this.s.getConsDescripciones().getListTableBeans();
        this.A = getIntent().getStringExtra("TipoConsulta");
        this.v = getIntent().getStringExtra("fechaDesde");
        this.w = getIntent().getStringExtra("fechaHasta");
        this.z = getIntent().getStringExtra("estadoSeleccionado");
        setListaDeEstadoConDescripciones();
        this.u.onCreatePage();
    }

    public void setBuscadorView() {
        b();
        if (this.A.equalsIgnoreCase("C")) {
            this.lblTitulo.setText(getString(R.string.ID_4413_DEBIN_BUSCARSOLICITUDESRECIBIDAS));
        } else if (this.A.equalsIgnoreCase("V")) {
            this.lblTitulo.setText(getString(R.string.ID_4418_DEBIN_BUSCARSOLICITUDESENVIADAS));
        }
        this.w = UtilDate.getDateFormat(this.w, Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
        this.v = UtilDate.getDateFormat(this.v, Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
        this.fechaDesde.setText(UtilDate.getDateFormat(this.v, Constants.FORMAT_DATE_WS_1, getString(R.string.FORMAT_FULL_DATE)));
        this.fechaHasta.setText(UtilDate.getDateFormat(this.w, Constants.FORMAT_DATE_WS_1, getString(R.string.FORMAT_FULL_DATE)));
        this.estadoDebin.setText(this.z);
        this.seleccionarFechaDesde.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BuscadorDebinActivity.this.a(BuscadorDebinActivity.this.fechaDesde, BuscadorDebinActivity.this.lblFechaDesde.getText().toString(), BuscadorDebinActivity.p);
            }
        });
        this.seleccionarFechaHasta.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BuscadorDebinActivity.this.a(BuscadorDebinActivity.this.fechaHasta, BuscadorDebinActivity.this.lblFechaHasta.getText().toString(), BuscadorDebinActivity.q);
            }
        });
        this.seleccionarEstado.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BuscadorDebinActivity.this.mostrarSelectorEstado(BuscadorDebinActivity.this.C);
            }
        });
        this.btnBuscarDebin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BuscadorDebinActivity.this.c();
            }
        });
    }

    private void b() {
        setActionBarType(ActionBarType.CANCELAR_CONFIRMAR);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.confirm_imgButton);
        ImageView imageView2 = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.cancel_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BuscadorDebinActivity.this.c();
                }
            });
        }
        if (imageView2 != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BuscadorDebinActivity.this.onBackPressed();
                }
            });
        }
    }

    public void setListaDeEstadoConDescripciones() {
        for (ListTableBean listTableBean : this.B) {
            if (listTableBean.getIdTable().equalsIgnoreCase("ESTDEBIN")) {
                this.C = listTableBean.getListGroupBeans();
            }
        }
    }

    public void mostrarSelectorEstado(List<ListGroupBean> list) {
        ArrayList arrayList = new ArrayList();
        for (ListGroupBean label : list) {
            arrayList.add(label.getLabel());
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selectorEstadoPopUp", "Estado", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, this.z, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                BuscadorDebinActivity.this.estadoDebin.setText(str);
                BuscadorDebinActivity.this.z = str;
            }

            public void onNegativeButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getSupportFragmentManager(), "selectorEstadoPopUp");
    }

    /* access modifiers changed from: private */
    public void a(TextView textView, String str, Boolean bool) {
        IsbanDatePickerDialogFragment crearSelectorFecha = crearSelectorFecha(textView, str, bool);
        getFragmentManager();
        crearSelectorFecha.show(getSupportFragmentManager(), this.r);
    }

    public IsbanDatePickerDialogFragment crearSelectorFecha(final TextView textView, final String str, final Boolean bool) {
        String str2;
        if (bool == p) {
            str2 = textView.getText().toString();
        } else {
            str2 = textView.getText().toString();
        }
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(str2, Constants.FORMAT_DATE_DASH, Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                textView.setText(UtilDate.getDateFormat(UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2), Constants.FORMAT_DATE_APP_2, BuscadorDebinActivity.this.getString(R.string.FORMAT_FULL_DATE)));
                TextView textView = textView;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(textView.getText().toString());
                textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
                String dateFormat = UtilDate.getDateFormat(date, Constants.FORMAT_DATE_APP_2);
                if (bool == BuscadorDebinActivity.p) {
                    BuscadorDebinActivity.this.v = UtilDate.getDateFormat(dateFormat, Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
                } else {
                    BuscadorDebinActivity.this.w = UtilDate.getDateFormat(dateFormat, Constants.FORMAT_DATE_APP_2, Constants.FORMAT_DATE_WS_1);
                }
            }
        });
        return newInstance;
    }

    /* access modifiers changed from: private */
    public void c() {
        UtilDate.getDateFormat(this.v, getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_WS_1);
        UtilDate.getDateFormat(this.w, getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_WS_1);
        if (a(this.w, this.v).isEmpty()) {
            this.u.filtrarDebines(this.A, UtilDate.getDateFormat(this.v, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), UtilDate.getDateFormat(this.w, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2), b(this.z, "ESTDEBIN"));
        } else {
            mostrarPopUpFechaInvalida(a(this.w, this.v));
        }
    }

    public void setResultados(ArrayList<ListDebinesBean> arrayList, String str, String str2) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("lista", arrayList);
        intent.putExtra("mensaje", str2);
        intent.putExtra(ConsultaTenenciaPreAutorizacionesRecibidasFragment.SIGUIENTE_PAGINA, str);
        intent.putExtra("fechaDesde", this.v);
        intent.putExtra("fechaHasta", this.w);
        intent.putExtra("estadoSeleccionado", this.z);
        setResult(-1, intent);
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void mostrarPopUpFechaInvalida(String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpInvalidData", getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, getString(R.string.ID1_ALERT_BTN_ACCEPT));
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
        newInstance.show(getSupportFragmentManager(), "invalidDataPopup");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1
            r0.<init>(r1)
            r1 = 0
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH     // Catch:{ ParseException -> 0x0023 }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ ParseException -> 0x0023 }
            java.lang.String r6 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r6, r2, r3)     // Catch:{ ParseException -> 0x0023 }
            java.util.Date r6 = r0.parse(r6)     // Catch:{ ParseException -> 0x0023 }
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_DASH     // Catch:{ ParseException -> 0x0021 }
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_WS_1     // Catch:{ ParseException -> 0x0021 }
            java.lang.String r5 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r5, r2, r3)     // Catch:{ ParseException -> 0x0021 }
            java.util.Date r5 = r0.parse(r5)     // Catch:{ ParseException -> 0x0021 }
            goto L_0x0029
        L_0x0021:
            r5 = move-exception
            goto L_0x0025
        L_0x0023:
            r5 = move-exception
            r6 = r1
        L_0x0025:
            r5.printStackTrace()
            r5 = r1
        L_0x0029:
            if (r5 != 0) goto L_0x002d
            if (r6 == 0) goto L_0x0052
        L_0x002d:
            java.util.Date r0 = new java.util.Date
            r0.<init>()
            java.util.Date r0 = ar.com.santander.rio.mbanking.utils.UtilDate.resetHours(r0)
            boolean r0 = ar.com.santander.rio.mbanking.utils.UtilDate.isDateAfter(r6, r0)
            if (r0 == 0) goto L_0x0044
            r5 = 2131757185(0x7f100881, float:1.9145299E38)
            java.lang.String r5 = r4.getString(r5)
            return r5
        L_0x0044:
            boolean r5 = ar.com.santander.rio.mbanking.utils.UtilDate.isDateAfter(r6, r5)
            if (r5 == 0) goto L_0x0052
            r5 = 2131757186(0x7f100882, float:1.91453E38)
            java.lang.String r5 = r4.getString(r5)
            return r5
        L_0x0052:
            java.lang.String r5 = ""
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.BuscadorDebinActivity.a(java.lang.String, java.lang.String):java.lang.String");
    }

    public void attachView() {
        if (!this.u.isViewAttached()) {
            this.u.attachView(this);
        }
    }

    public void detachView() {
        if (this.u.isViewAttached()) {
            this.u.detachView();
        }
    }

    private String b(String str, String str2) {
        String str3 = new String();
        for (ListTableBean listTableBean : this.B) {
            if (listTableBean.getIdTable().equalsIgnoreCase(str2)) {
                for (ListGroupBean listGroupBean : listTableBean.getListGroupBeans()) {
                    if (listGroupBean.getLabel().equalsIgnoreCase(str)) {
                        str3 = listGroupBean.getCode();
                    }
                }
            }
        }
        return str3;
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
