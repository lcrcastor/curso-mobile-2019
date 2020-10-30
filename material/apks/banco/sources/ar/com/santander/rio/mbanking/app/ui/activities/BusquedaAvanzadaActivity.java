package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaMandatosExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAgendadosEnvEfeListaDestinatariosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvFiltroBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaMandatosExtEnv;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class BusquedaAvanzadaActivity extends BaseActivity {
    @Inject
    AnalyticsManager A;
    @InjectView(2131362411)
    Button btnBuscar;
    @InjectView(2131362428)
    NumericEditText inpImporteDesde;
    @InjectView(2131362429)
    NumericEditText inpImporteHasta;
    @InjectView(2131362413)
    TextView lblBusquedaAvanzada;
    @InjectView(2131362421)
    TextView lblDesde;
    @InjectView(2131362418)
    TextView lblDestinatario;
    @InjectView(2131362414)
    TextView lblDestinatarios;
    @InjectView(2131362419)
    TextView lblEstado;
    @InjectView(2131362415)
    TextView lblEstadoData;
    @InjectView(2131362420)
    TextView lblFechaAltaEstado;
    @InjectView(2131362416)
    TextView lblFechaDesde;
    @InjectView(2131362417)
    TextView lblFechaHasta;
    @InjectView(2131362422)
    TextView lblHasta;
    @InjectView(2131362423)
    TextView lblImporte;
    @InjectView(2131362424)
    TextView lblImporteDesde;
    @InjectView(2131362425)
    TextView lblImporteHasta;
    ImageView p;
    ImageView q;
    String r = "DialogDate";
    @InjectView(2131362430)
    RelativeLayout rllDestinatarios;
    View s;
    ArrayList<ObjectDestinatarios> t;
    String u;
    ExtEnvFiltroBean v;
    ExtEnvFiltroBean w;
    @Inject
    Bus x;
    @Inject
    SessionManager y;
    @Inject
    IDataManager z;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_busqueda_avanzada);
        ButterKnife.inject((Activity) this);
        setActionBarType(ActionBarType.CANCELAR_CONFIRMAR);
        this.s = getSupportActionBar().getCustomView();
        this.p = (ImageView) this.s.findViewById(R.id.cancel_imgButton);
        this.q = (ImageView) this.s.findViewById(R.id.confirm_imgButton);
        this.p.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BusquedaAvanzadaActivity.this.setResult(0, BusquedaAvanzadaActivity.this.getIntent());
                BusquedaAvanzadaActivity.this.onBackPressed();
            }
        });
        this.A.trackScreen(getString(R.string.analytics_enviodinero_busqueda_avanzada_operaciones));
        this.A.trackScreen(getString(R.string.analytics_extraccion_busqueda_avanzada_operaciones_realizadas));
        Intent intent = getIntent();
        this.v = (ExtEnvFiltroBean) intent.getParcelableExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FILTROS_AVANZADOS));
        this.u = intent.getStringExtra(getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD));
        if (this.v == null) {
            this.lblEstadoData.setText(getString(R.string.ID21XX_ENVIARDINERO_BTN_ESTADO));
            this.lblFechaDesde.setText(new DateTime().toString(getString(R.string.FORMAT_FULL_DATE)));
            this.lblFechaHasta.setText(new DateTime().toString(getString(R.string.FORMAT_FULL_DATE)));
        } else {
            if (this.v.estado != null) {
                this.lblEstadoData.setText(CExtEnv.getDescripcionEstadoMandato(this.y, this.v.estado));
            }
            if (this.v.fechaAltaDesde != null) {
                this.lblFechaDesde.setText(UtilDate.getDateFormat(this.v.fechaAltaDesde, Constants.FORMAT_DATE_APP_2, getString(R.string.FORMAT_FULL_DATE)));
            }
            if (this.v.fechaAltaHasta != null) {
                this.lblFechaHasta.setText(UtilDate.getDateFormat(this.v.fechaAltaHasta, Constants.FORMAT_DATE_APP_2, getString(R.string.FORMAT_FULL_DATE)));
            }
            if (this.v.importeDesde != null) {
                this.inpImporteDesde.setText(this.v.importeDesde);
            }
            if (this.v.importeHasta != null) {
                this.inpImporteHasta.setText(this.v.importeHasta);
            }
            this.t = intent.getParcelableArrayListExtra(getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS));
        }
        String str = this.u;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 68813) {
            if (hashCode == 69121 && str.equals(TipoOperacion.ExtraccionSinTarjeta)) {
                c = 0;
            }
        } else if (str.equals(TipoOperacion.EnvioDinero)) {
            c = 1;
        }
        switch (c) {
            case 0:
                this.rllDestinatarios.setVisibility(8);
                findViewById(R.id.F12_11_txv_separador9).setVisibility(8);
                break;
            case 1:
                if (this.t != null) {
                    this.lblDestinatarios.setText(a(this.t));
                    break;
                }
                break;
        }
        this.p.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_CANCELAR)));
        this.q.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_LISTO)));
        TextView textView = this.lblEstadoData;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.ID2107_ENVEFECT_LBL_ESTADO));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.lblEstadoData.getText().toString());
        textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
        this.btnBuscar.setContentDescription(CAccessibility.applyFilterMaskBotton(this.btnBuscar.getText().toString()));
        TextView textView2 = this.lblFechaDesde;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.CONTENT_FECHA));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(getString(R.string.ID2091_EXTTARJETA_LBL_DESDE));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(this.lblFechaDesde.getText().toString());
        textView2.setContentDescription(CAccessibility.applyFilterMaskSelector(sb2.toString()));
        TextView textView3 = this.lblFechaHasta;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.CONTENT_FECHA));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(getString(R.string.ID2092_EXTTARJETA_LBL_HASTA));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(this.lblFechaHasta.getText().toString());
        textView3.setContentDescription(CAccessibility.applyFilterMaskSelector(sb3.toString()));
        this.lblImporte.setContentDescription(this.lblImporte.getText().toString().replace("$", getString(R.string.ACCESSIBILITY_TRANSFERENCIAS_PESOS_ARGENTINOS)));
        this.inpImporteDesde.setContentDescription(getString(R.string.ACCESSIBILITY_TRANSFERENCIAS_PESOS_ARGENTINOS));
        TextView textView4 = this.lblDestinatarios;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.ID2114_ENVEFECT_LBL_DEST));
        sb4.append(UtilsCuentas.SEPARAOR2);
        sb4.append(this.lblDestinatarios.getText().toString());
        textView4.setContentDescription(CAccessibility.applyFilterMaskSelector(sb4.toString()));
        AnonymousClass2 r6 = new OnClickListener() {
            public void onClick(View view) {
                BusquedaAvanzadaActivity.this.c();
            }
        };
        this.q.setOnClickListener(r6);
        this.btnBuscar.setOnClickListener(r6);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.x.register(this);
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.x.unregister(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void a(TextView textView, String str) {
        crearSelectorFecha(textView, str).show(getSupportFragmentManager(), this.r);
    }

    @OnClick({2131362416})
    public void mostrarSelectorFechaDesde(TextView textView) {
        a(textView, getString(R.string.ID2091_EXTTARJETA_LBL_DESDE));
    }

    @OnClick({2131362417})
    public void mostrarSelectorFechaHasta(TextView textView) {
        a(textView, getString(R.string.ID2092_EXTTARJETA_LBL_HASTA));
    }

    @OnClick({2131362414})
    public void mostrarSelectorDestinatario(TextView textView) {
        showProgress(VConsultaAgendadosEnvEfe.nameService);
        CExtEnv.consultaAgendados(this.z);
    }

    @OnClick({2131362415})
    public void mostrarSelectorEstado(TextView textView) {
        crearSelectorEstados(textView, b()).show(getSupportFragmentManager(), "TAG_DIALOG_FILTER_TRANSACTION");
    }

    public IsbanDatePickerDialogFragment crearSelectorFecha(final TextView textView, final String str) {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(textView.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                textView.setText(new DateTime((Object) date).toString(BusquedaAvanzadaActivity.this.getString(R.string.FORMAT_FULL_DATE)));
                TextView textView = textView;
                StringBuilder sb = new StringBuilder();
                sb.append(BusquedaAvanzadaActivity.this.getString(R.string.CONTENT_FECHA));
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(str);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(textView.getText().toString());
                textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
            }
        });
        return newInstance;
    }

    public IsbanDialogFragment crearSelectorEstados(final TextView textView, ArrayList<String> arrayList) {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCIONAR));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getString(R.string.ID2107_ENVEFECT_LBL_ESTADO));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(sb.toString(), null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, textView.getText().toString());
        newInstance.setSelectedOption(textView.getText().toString());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                textView.setText(str);
                TextView textView = textView;
                StringBuilder sb = new StringBuilder();
                sb.append(BusquedaAvanzadaActivity.this.getString(R.string.ID2107_ENVEFECT_LBL_ESTADO));
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(textView.getText().toString());
                textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
            }
        });
        return newInstance;
    }

    private ArrayList<String> b() {
        return new ArrayList<>(Arrays.asList(CExtEnv.getEstadosMandato(this.y)));
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lblBusquedaAvanzada, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblEstado, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblFechaAltaEstado, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblDesde, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblHasta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblImporte, -1.5f, false);
        UtilStyleCommons.setLetterSpacing(this.lblImporteDesde, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblImporteHasta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblDestinatario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblDestinatarios, -1.5f);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
            Intent intent2 = new Intent();
            intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
            if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
            }
            setResult(-1, intent2);
            finish();
            return;
        }
        this.t = intent.getExtras().getParcelableArrayList(getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS));
        if (this.t != null) {
            this.lblDestinatarios.setText(a(this.t));
            TextView textView = this.lblDestinatarios;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.ID2114_ENVEFECT_LBL_DEST));
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.lblDestinatarios.getText().toString());
            textView.setContentDescription(CAccessibility.applyFilterMaskSelector(sb.toString()));
        }
    }

    private String a(ArrayList<ObjectDestinatarios> arrayList) {
        if (arrayList.size() == 0) {
            return getString(R.string.ID21XX_ENVIARDINERO_BTN_DEST);
        }
        if (arrayList.size() == 1) {
            return Html.fromHtml(((ObjectDestinatarios) arrayList.get(0)).getNombre()).toString();
        }
        return String.format("%s destinatarios", new Object[]{Integer.valueOf(arrayList.size())});
    }

    /* access modifiers changed from: private */
    public void c() {
        Date date;
        Date date2;
        final TextView textView;
        String str;
        String string;
        String string2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_WS_1);
        Date date3 = new Date();
        try {
            date = simpleDateFormat.parse(UtilDate.getDateFormat(this.lblFechaDesde.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_WS_1));
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        try {
            date2 = simpleDateFormat.parse(UtilDate.getDateFormat(this.lblFechaHasta.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_WS_1));
        } catch (ParseException e2) {
            e2.printStackTrace();
            date2 = null;
        }
        if (date == null || UtilDate.isDateAfter(date, date3)) {
            str = this.u.equalsIgnoreCase(TipoOperacion.EnvioDinero) ? getString(R.string.USER200020) : this.u.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta) ? getString(R.string.USER200011) : null;
            textView = this.lblFechaDesde;
        } else {
            str = null;
            textView = null;
        }
        if (date2 == null || UtilDate.isDateBefore(date2, date) || UtilDate.isDateAfter(date2, date3)) {
            if (UtilDate.isDateAfter(date2, date3)) {
                string2 = getString(R.string.USER200012);
            } else {
                string2 = getString(R.string.USER200021);
            }
            str = string2;
            textView = this.lblFechaHasta;
        }
        double doubleValue = this.inpImporteDesde.getText().toString().isEmpty() ? 0.0d : CAmountIU.getInstance().getDoubleFromInputUser(this.inpImporteDesde.getText().toString()).doubleValue();
        double doubleValue2 = this.inpImporteHasta.getText().toString().isEmpty() ? 0.0d : CAmountIU.getInstance().getDoubleFromInputUser(this.inpImporteHasta.getText().toString()).doubleValue();
        if (doubleValue < 0.0d) {
            str = getString(R.string.USER200009);
            textView = this.inpImporteDesde;
        }
        if (doubleValue2 < 0.0d) {
            str = getString(R.string.USER200009_HASTA);
            textView = this.inpImporteHasta;
        }
        if (!this.inpImporteDesde.getText().toString().isEmpty() && !this.inpImporteHasta.getText().toString().isEmpty() && doubleValue2 < doubleValue) {
            if (this.u.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
                string = getString(R.string.USER200023);
            } else {
                if (this.u.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
                    string = getString(R.string.USER200010);
                }
                textView = this.inpImporteHasta;
            }
            str = string;
            textView = this.inpImporteHasta;
        }
        String str2 = str;
        if (textView != null) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str2, null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                    textView.requestFocus();
                }
            });
            newInstance.show(getSupportFragmentManager(), "Dialog");
            return;
        }
        ExtEnvFiltroBean extEnvFiltroBean = new ExtEnvFiltroBean(null, UtilDate.getDateFormat(this.lblFechaDesde.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP_2), UtilDate.getDateFormat(this.lblFechaHasta.getText().toString(), getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP_2), null, null, null);
        if (!this.lblEstado.getText().toString().isEmpty()) {
            extEnvFiltroBean.estado = CExtEnv.getCodigoEstadoMandato(this.y, this.lblEstadoData.getText().toString());
        }
        if (!this.inpImporteDesde.getText().toString().isEmpty()) {
            extEnvFiltroBean.importeDesde = String.valueOf(CAmountIU.getInstance().getDoubleFromInputUser(this.inpImporteDesde.getText().toString()).intValue());
        } else {
            extEnvFiltroBean.importeDesde = "";
        }
        if (!this.inpImporteHasta.getText().toString().isEmpty()) {
            extEnvFiltroBean.importeHasta = String.valueOf(CAmountIU.getInstance().getDoubleFromInputUser(this.inpImporteHasta.getText().toString()).intValue());
        } else {
            extEnvFiltroBean.importeHasta = "";
        }
        ArrayList arrayList = new ArrayList();
        if (this.t != null && this.t.size() > 0) {
            for (int i = 0; i < this.t.size(); i++) {
                arrayList.add(new AMAgendadosEnvEfeDestinatarioBean(null, ((ObjectDestinatarios) this.t.get(i)).getTipoDoc(), ((ObjectDestinatarios) this.t.get(i)).getDocumento(), null));
            }
        }
        extEnvFiltroBean.listaDestinatario = new ConsultaAgendadosEnvEfeListaDestinatariosBean((List<AMAgendadosEnvEfeDestinatarioBean>) arrayList);
        this.w = extEnvFiltroBean;
        showProgress(VConsultaMandatosExtEnv.nameService);
        CExtEnv.consultaMandatos(this.z, this.u, "AVA", this.w);
    }

    @Subscribe
    public void onConsultaMandatos(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        dismissProgress();
        final ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent2 = consultaMandatosExtEnvEvent;
        AnonymousClass6 r0 = new BaseWSResponseHandler(this, TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                BusquedaAvanzadaActivity.this.a(consultaMandatosExtEnvEvent2);
            }

            public void onRes4Error() {
                BusquedaAvanzadaActivity.this.a(consultaMandatosExtEnvEvent2);
            }
        };
        r0.handleWSResponse(consultaMandatosExtEnvEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        try {
            Intent intent = getIntent();
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_FILTROS_AVANZADOS), this.w);
            intent.putParcelableArrayListExtra(getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS), this.t);
            intent.putParcelableArrayListExtra(getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS), CExtEnv.getMandatos(consultaMandatosExtEnvEvent.getBeanResponse() != null ? ((ConsultaMandatosExtEnvResponseBean) consultaMandatosExtEnvEvent.getBeanResponse()).consultaMandatosExtEnvBodyResponseBean.listaMandatos : null));
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS_RES4), consultaMandatosExtEnvEvent.getResult() == TypeResult.BEAN_ERROR_RES_4 ? consultaMandatosExtEnvEvent.getMessageToShow() : "");
            dismissProgress();
            setResult(-1, intent);
            onBackPressed();
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaMandatosResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    @Subscribe
    public void onConsultaAgendados(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        dismissProgress();
        final ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent2 = consultaAgendadosEnvEfeEvent;
        AnonymousClass7 r0 = new BaseWSResponseHandler(this, TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this) {
            public void onOk() {
                BusquedaAvanzadaActivity.this.a(consultaAgendadosEnvEfeEvent2);
            }
        };
        r0.handleWSResponse(consultaAgendadosEnvEfeEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        try {
            Intent intent = new Intent(this, ActivityEnvioDineroListadoDestinatarios.class);
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIOS_MODE), getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIOS_MULTIPLE_CHOICE));
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_LISTA_DESTINATARIOS), this.t);
            intent.putParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS), CExtEnv.getDestinatarios(((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios));
            intent.putExtra(getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS_RES4), ((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios == null ? consultaAgendadosEnvEfeEvent.getMessageToShow() : "");
            startActivityForResult(intent, 12);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaAgendadosResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void clearScreenData() {
        this.inpImporteDesde.setText("");
        this.inpImporteHasta.setText("");
    }
}
