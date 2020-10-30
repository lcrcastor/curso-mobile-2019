package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.app.module.transfers.agendaDestinatarios.AgendaDestinatariosTransferenciasPresenter;
import ar.com.santander.rio.mbanking.app.module.transfers.agendaDestinatarios.AgendaDestinatariosTransferenciasView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.adapters.AgendaDestinatariosAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.inject.Inject;

public class AgendaDestinatariosTransferenciasActivity extends BaseMvpActivity implements OnClickListener, AgendaDestinatariosTransferenciasView {
    private CuentasPropiasBean A = null;
    private String B = "N";
    private AgendaDestinatarios C = null;
    private String D = "";
    @InjectView(2131365651)
    ClearableEditText clrFilter;
    @InjectView(2131365105)
    ListView lstDestinatarios;
    @Inject
    AnalyticsManager p;
    @Inject
    SessionManager q;
    @Inject
    IDataManager r;
    private AgendaDestinatariosTransferenciasPresenter s;
    /* access modifiers changed from: private */
    public AgendaDestinatariosAdapter t;
    private ImageView u;
    private ArrayList<AgendaDestinatarios> v = new ArrayList<>();
    private AgendadosBean w;
    private AgendaDestinatarios x;
    private String y = null;
    private DatosCuentasBean z = null;

    public class SortBasedOnName implements Comparator {
        public SortBasedOnName() {
        }

        public int compare(Object obj, Object obj2) {
            return ((AgendaDestinatarios) obj).getTitulo().compareToIgnoreCase(((AgendaDestinatarios) obj2).getTitulo());
        }
    }

    public void onPointerCaptureChanged(boolean z2) {
    }

    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        setContentView((int) R.layout.agenda_destinatarios_transferencias);
        ButterKnife.inject((Activity) this);
        initialize();
        configureActionBar();
        configureLayout();
    }

    public void initialize() {
        this.x = (AgendaDestinatarios) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO);
        this.y = getIntent().getStringExtra(TransferenciasConstants.cINTENT_EXTRA_FILTER_CURRENCY);
        this.z = (DatosCuentasBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTA_PROPIA);
        this.A = (CuentasPropiasBean) getIntent().getParcelableExtra(TransferenciasConstants.cINTENT_EXTRA_CUENTAS_PROPIAS);
        this.w = this.q.getAgendaTransferencia();
        this.D = getIntent().getStringExtra(TransferenciasConstants.cINTENT_EXTRA_ACCION_VERIFICAR);
        b();
        this.s = new AgendaDestinatariosTransferenciasPresenter(this.mBus, this.r, this.q, this);
        this.s.attachView(this);
    }

    private void b() {
        String str;
        AgendaDestinatariosTransferenciasActivity agendaDestinatariosTransferenciasActivity = this;
        agendaDestinatariosTransferenciasActivity.v.clear();
        boolean z2 = false;
        try {
            if (agendaDestinatariosTransferenciasActivity.A != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(agendaDestinatariosTransferenciasActivity.q.getLoginUnico().getDatosPersonales().getNombre());
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(agendaDestinatariosTransferenciasActivity.q.getLoginUnico().getDatosPersonales().getApellido().toUpperCase());
                String sb2 = sb.toString();
                int i = 0;
                while (i < agendaDestinatariosTransferenciasActivity.A.getListDatosCuentasBean().size()) {
                    if (!((DatosCuentasBean) agendaDestinatariosTransferenciasActivity.A.getListDatosCuentasBean().get(i)).getTipoDestino().equalsIgnoreCase("01") || (agendaDestinatariosTransferenciasActivity.z != null && agendaDestinatariosTransferenciasActivity.z.getDescCtaDestino().equalsIgnoreCase(((DatosCuentasBean) agendaDestinatariosTransferenciasActivity.A.getListDatosCuentasBean().get(i)).getDescCtaDestino()))) {
                        str = sb2;
                    } else {
                        String str2 = sb2;
                        AgendaDestinatarios agendaDestinatarios = r5;
                        str = sb2;
                        ArrayList<AgendaDestinatarios> arrayList = agendaDestinatariosTransferenciasActivity.v;
                        AgendaDestinatarios agendaDestinatarios2 = new AgendaDestinatarios(str2, "", TransferenciasConstants.cINTENT_EXTRA_DATA_TIPO_DE_CUENTA_PROPIA, ((DatosCuentasBean) agendaDestinatariosTransferenciasActivity.A.getListDatosCuentasBean().get(i)).getDescCtaDestino(), Boolean.valueOf(z2), "", "", "01", "", "", "", "");
                        arrayList.add(agendaDestinatarios);
                    }
                    i++;
                    sb2 = str;
                    z2 = false;
                }
            }
        } catch (Exception e) {
            Log.e("TransferenciasFragment", "generarListaDestinatariosCompleta - propias: ", e);
        }
        try {
            if (agendaDestinatariosTransferenciasActivity.w.getListAgDestBSRBean() != null) {
                int i2 = 0;
                while (i2 < agendaDestinatariosTransferenciasActivity.w.getListAgDestBSRBean().size()) {
                    int i3 = 0;
                    while (i3 < ((AgDestBSRBean) agendaDestinatariosTransferenciasActivity.w.getListAgDestBSRBean().get(i2)).getListDatosCuentasDestBSRBean().size()) {
                        DatosCuentasDestBSRBean datosCuentasDestBSRBean = (DatosCuentasDestBSRBean) ((AgDestBSRBean) agendaDestinatariosTransferenciasActivity.w.getListAgDestBSRBean().get(i2)).getListDatosCuentasDestBSRBean().get(i3);
                        if (datosCuentasDestBSRBean.getTipoDestino().equalsIgnoreCase("02")) {
                            ArrayList<AgendaDestinatarios> arrayList2 = agendaDestinatariosTransferenciasActivity.v;
                            AgendaDestinatarios agendaDestinatarios3 = r6;
                            AgendaDestinatarios agendaDestinatarios4 = new AgendaDestinatarios(datosCuentasDestBSRBean.getNombreTitular(), datosCuentasDestBSRBean.getDescripcion(), TransferenciasConstants.cBANCO_SR_TERCEROS, datosCuentasDestBSRBean.getDescCtaDestinoBSR(), Boolean.valueOf(false), datosCuentasDestBSRBean.getTipoDescripcion(), datosCuentasDestBSRBean.getEmail(), datosCuentasDestBSRBean.getTipoDestino(), datosCuentasDestBSRBean.getDescripcion(), TransferenciasConstants.cBANCO_SR_TERCEROS, "", datosCuentasDestBSRBean.getAlias());
                            arrayList2.add(agendaDestinatarios3);
                        }
                        i3++;
                        agendaDestinatariosTransferenciasActivity = this;
                    }
                    i2++;
                    agendaDestinatariosTransferenciasActivity = this;
                }
            }
        } catch (Exception e2) {
            Log.e("TransferenciasFragment", "generarListaDestinatariosCompleta - bsr: ", e2);
        }
        AgendaDestinatariosTransferenciasActivity agendaDestinatariosTransferenciasActivity2 = this;
        try {
            if (agendaDestinatariosTransferenciasActivity2.w.getListAgDestOBBean() != null) {
                int i4 = 0;
                while (i4 < agendaDestinatariosTransferenciasActivity2.w.getListAgDestOBBean().size()) {
                    int i5 = 0;
                    while (i5 < ((AgDestOBBean) agendaDestinatariosTransferenciasActivity2.w.getListAgDestOBBean().get(i4)).getListDatosCuentasDestOBBean().size()) {
                        DatosCuentasDestOBBean datosCuentasDestOBBean = (DatosCuentasDestOBBean) ((AgDestOBBean) agendaDestinatariosTransferenciasActivity2.w.getListAgDestOBBean().get(i4)).getListDatosCuentasDestOBBean().get(i5);
                        if (((DatosCuentasDestOBBean) ((AgDestOBBean) agendaDestinatariosTransferenciasActivity2.w.getListAgDestOBBean().get(i4)).getListDatosCuentasDestOBBean().get(i5)).getTipoDestino().equalsIgnoreCase("03")) {
                            ArrayList<AgendaDestinatarios> arrayList3 = agendaDestinatariosTransferenciasActivity2.v;
                            AgendaDestinatarios agendaDestinatarios5 = r6;
                            AgendaDestinatarios agendaDestinatarios6 = new AgendaDestinatarios(datosCuentasDestOBBean.getNombreTitular(), datosCuentasDestOBBean.getDescripcion(), datosCuentasDestOBBean.getBanco().toUpperCase(), datosCuentasDestOBBean.getCbu(), Boolean.valueOf(false), "", datosCuentasDestOBBean.getEmail(), datosCuentasDestOBBean.getTipoDestino(), datosCuentasDestOBBean.getDescripcion(), datosCuentasDestOBBean.getBanco(), datosCuentasDestOBBean.getBeneficiario(), datosCuentasDestOBBean.getAlias());
                            arrayList3.add(agendaDestinatarios5);
                        }
                        i5++;
                        agendaDestinatariosTransferenciasActivity2 = this;
                    }
                    i4++;
                    agendaDestinatariosTransferenciasActivity2 = this;
                }
            }
        } catch (Exception e3) {
            Log.e("TransferenciasFragment", "generarListaDestinatariosCompleta - ob: ", e3);
        }
        Collections.sort(this.v, new SortBasedOnName());
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        this.u = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.back_imgButton);
        if (this.u != null) {
            this.u.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    AgendaDestinatariosTransferenciasActivity.this.onBackPressed();
                }
            });
        }
    }

    public void configureLayout() {
        if (this.y != null) {
            c();
        }
        AgendaDestinatariosAdapter agendaDestinatariosAdapter = new AgendaDestinatariosAdapter(this, this.v, this, this.x, null, null, null);
        this.t = agendaDestinatariosAdapter;
        this.lstDestinatarios.setAdapter(this.t);
        this.clrFilter.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                AgendaDestinatariosTransferenciasActivity.this.t.getFilter().filter(AgendaDestinatariosTransferenciasActivity.this.clrFilter.getText().toString().toLowerCase(Constants.LOCALE_DEFAULT_APP));
            }
        });
    }

    private void c() {
        if (this.v != null && this.v.size() > 0) {
            ArrayList arrayList = (ArrayList) this.v.clone();
            this.v.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                AgendaDestinatarios agendaDestinatarios = (AgendaDestinatarios) it.next();
                if (agendaDestinatarios.getInfo1().equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_TIPO_DE_CUENTA_PROPIA)) {
                    if ((this.y.equalsIgnoreCase("ARS") && !agendaDestinatarios.getInfo2().contains(Constants.SYMBOL_CURRENCY_DOLAR)) || (this.y.equalsIgnoreCase("USD") && agendaDestinatarios.getInfo2().contains(Constants.SYMBOL_CURRENCY_DOLAR))) {
                        this.v.add(agendaDestinatarios);
                    }
                } else if (!agendaDestinatarios.getInfo1().equalsIgnoreCase(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                    this.v.add(agendaDestinatarios);
                } else if ((this.y.equalsIgnoreCase("ARS") && !agendaDestinatarios.getInfo2().contains(Constants.SYMBOL_CURRENCY_DOLAR)) || (this.y.equalsIgnoreCase("USD") && agendaDestinatarios.getInfo2().contains(Constants.SYMBOL_CURRENCY_DOLAR))) {
                    this.v.add(agendaDestinatarios);
                }
            }
        }
    }

    public void attachView() {
        if (!this.s.isViewAttached()) {
            this.s.attachView(this);
        }
    }

    public void detachView() {
        if (this.s.isViewAttached()) {
            this.s.detachView();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.back_imgButton) {
            onBackPressed();
        } else if (id2 == R.id.imageViewCheck) {
            this.B = "N";
            hideKeyboard();
            ImageView imageView = (ImageView) view.findViewById(R.id.imageViewCheck);
            this.C = (AgendaDestinatarios) view.getTag();
            this.t.updateAdapterChecked();
            this.t.updateCheckBox(imageView, true);
            this.C.setChecked(true);
            String str = TransferenciasConstants.cBANCO_OB;
            if (this.C.getInfo1().equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_TIPO_DE_CUENTA_PROPIA)) {
                str = TransferenciasConstants.cBANCO_PROPIA;
            } else if (this.C.getInfo1().equalsIgnoreCase(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
                str = TransferenciasConstants.cBANCO_SR_TERCEROS;
            }
            this.s.onDestSelected(this.C, str, this.z, this.w, this.D);
        }
    }

    public void resetSelectedFromList() {
        try {
            if (this.C != null) {
                this.C.setChecked(false);
                View childAt = this.lstDestinatarios.getChildAt(this.C.getPosition());
                if (childAt != null) {
                    ImageView imageView = (ImageView) childAt.findViewById(R.id.imageViewCheck);
                    if (imageView != null) {
                        this.t.updateCheckBox(imageView, false);
                    }
                }
            }
            resetDestinatarioSeleccionado();
            this.t.updateAdapterChecked();
            this.t.notifyDataSetChanged();
        } catch (Exception unused) {
        }
    }

    public void resetDestinatarioSeleccionado() {
        this.C = null;
    }

    public void clearScreenData() {
        this.clrFilter.setText("");
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    public void gotoNextFlow(AgendaDestinatarios agendaDestinatarios, VerificaDatosSalidaOBBean verificaDatosSalidaOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean) {
        Intent intent = new Intent();
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO, agendaDestinatarios);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_VERIFICA_DATOS_TRANSF, verificaDatosSalidaOBBean);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_OB_BEAN, datosCuentasDestOBBean);
        intent.putExtra(TransferenciasConstants.cINTENT_EXTRA_DESTINATARIO_BSR_BEAN, datosCuentasDestBSRBean);
        setResult(-1, intent);
        finish();
        hideKeyboard();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
