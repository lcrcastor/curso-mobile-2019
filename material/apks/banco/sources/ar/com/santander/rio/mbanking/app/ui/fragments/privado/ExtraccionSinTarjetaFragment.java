package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio;
import ar.com.santander.rio.mbanking.app.ui.activities.ActivityExtraccionSinTarjetaOperacionesRealizadas;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CargaDatosInicialesExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaMandatosExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.model.general.Destino;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCargaDatosInicialesExtEnv;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaMandatosExtEnv;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class ExtraccionSinTarjetaFragment extends BaseFragment implements OnClickListener {
    boolean a;
    @Inject
    AnalyticsManager ad;
    /* access modifiers changed from: private */
    public List<ExtEnvCuentaBean> ae;
    /* access modifiers changed from: private */
    public ExtEnvCuentaBean af = null;
    /* access modifiers changed from: private */
    public Integer ag;
    /* access modifiers changed from: private */
    public Integer ah;
    ImageView b;
    @InjectView(2131362523)
    Button btn_continuar;
    ImageView c;
    @InjectView(2131362527)
    AmountView cuentaSaldoValue;
    View d;
    View e;
    @InjectView(2131364757)
    View extraccionSinTarjetaView;
    Intent f;
    SharedPreferences g;
    @Inject
    IDataManager h;
    @Inject
    SessionManager i;
    @InjectView(2131362530)
    TextView idCuenta;
    @InjectView(2131362525)
    ImageView img_botonAyudaLimiteDiario;
    @InjectView(2131362526)
    NumericEditTextWithPrefixAccesibility inp_importe;
    @InjectView(2131362528)
    TextView lbl_limiteDiario;
    @InjectView(2131362529)
    TextView lblsaldocuenta;
    @InjectView(2131362524)
    CustomSpinner lblselectorcuentas;
    @InjectView(2131362532)
    LinearLayout selectorCuentas;

    public void onClick(View view) {
    }

    private void z() {
        this.ag = Integer.valueOf(this.i.getDatosInicialesExtExv().datosExtEnv.importeMultiplo);
        this.ah = Integer.valueOf(this.i.getDatosInicialesExtExv().datosExtEnv.importeLimite);
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.e = getActivity().getLayoutInflater().inflate(R.layout.fragment_extraccion_sin_tarjeta, viewGroup, false);
        ButterKnife.inject((Object) this, this.e);
        ((RelativeLayout) this.e.findViewById(R.id.F19_00_rll_scroll)).setMinimumHeight(getActivity().findViewById(16908290).getHeight());
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.d = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        this.b = (ImageView) this.d.findViewById(R.id.menu);
        this.c = (ImageView) this.d.findViewById(R.id.toggle);
        this.b.setContentDescription(getString(R.string.IDXX_CONTENT_DESCRIPTION_BTN_OPTIONS));
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ExtraccionSinTarjetaFragment.this.A();
            }
        });
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ExtraccionSinTarjetaFragment.this.switchDrawer();
            }
        });
        this.btn_continuar.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.ID2005_EXTTARJETA_BTN_CONTINUAR)));
        this.btn_continuar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ButterKnife.inject((Object) this, ExtraccionSinTarjetaFragment.this.e);
                Integer valueWithoutFormat = CExtEnv.getValueWithoutFormat(ExtraccionSinTarjetaFragment.this.inp_importe.getText().toString());
                if (valueWithoutFormat.intValue() <= 0 || valueWithoutFormat.intValue() % ExtraccionSinTarjetaFragment.this.ag.intValue() != 0 || valueWithoutFormat.intValue() > ExtraccionSinTarjetaFragment.this.ah.intValue()) {
                    IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(ExtraccionSinTarjetaFragment.this.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), Utils.formatIsbanHTMLCode(Html.fromHtml(ExtraccionSinTarjetaFragment.this.getString(R.string.USER000040)).toString()), null, null, ExtraccionSinTarjetaFragment.this.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                    newInstance.setDialogListener(new IDialogListener() {
                        public void onItemSelected(String str) {
                        }

                        public void onNegativeButton() {
                        }

                        public void onPositiveButton() {
                        }

                        public void onSimpleActionButton() {
                            ExtraccionSinTarjetaFragment.this.inp_importe.requestFocus();
                        }
                    });
                    newInstance.show(ExtraccionSinTarjetaFragment.this.getActivity().getSupportFragmentManager(), "Dialog");
                    return;
                }
                DatosPersonales datosPersonales = ExtraccionSinTarjetaFragment.this.i.getLoginUnico().getDatosPersonales();
                String str = "";
                Iterator it = ExtraccionSinTarjetaFragment.this.i.getLoginUnico().getDestinosMyA().getDestinos().getDestinos().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Destino destino = (Destino) it.next();
                    if (destino.getDestinoTipo().equals("MAIL")) {
                        str = destino.getDestinoDescripcion();
                        break;
                    }
                }
                Intent intent = new Intent(ExtraccionSinTarjetaFragment.this.getActivity(), ActivityEnvioDineroConfirmacionEnvio.class);
                String string = ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_NOMBRE);
                StringBuilder sb = new StringBuilder();
                sb.append(datosPersonales.getNombre());
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(datosPersonales.getApellido());
                intent.putExtra(string, UtilString.capitalize(sb.toString()));
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_APELLIDO), datosPersonales.getApellido());
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_TIPODOC), datosPersonales.getTipoDocumento());
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_DOCUMENTO), datosPersonales.getNroDocumento());
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_MAIL), str);
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO), valueWithoutFormat.toString());
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA), ExtraccionSinTarjetaFragment.this.af.descCtaDebito);
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA), ExtraccionSinTarjetaFragment.this.af.tipo);
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA), ExtraccionSinTarjetaFragment.this.af.sucursal);
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA), ExtraccionSinTarjetaFragment.this.af.numero);
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_EXTENV_TIPOTRANSACCION), TipoOperacion.ExtraccionSinTarjeta);
                intent.putExtra(ExtraccionSinTarjetaFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_FUNCIONALIDAD), TipoOperacion.ExtraccionSinTarjeta);
                ExtraccionSinTarjetaFragment.this.startActivityForResult(intent, 3);
            }
        });
        this.img_botonAyudaLimiteDiario.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ButterKnife.inject((Object) this, ExtraccionSinTarjetaFragment.this.e);
                ExtraccionSinTarjetaFragment.this.f = new Intent(ExtraccionSinTarjetaFragment.this.getActivity(), InfoActivity.class);
                ExtraccionSinTarjetaFragment.this.f.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                ExtraccionSinTarjetaFragment.this.f.putExtra(InfoActivity.TITLE_TO_SHOW, CExtEnv.getLimiteTitulo(ExtraccionSinTarjetaFragment.this.i));
                ExtraccionSinTarjetaFragment.this.f.putExtra(InfoActivity.TEXT_TO_SHOW, CExtEnv.getLimiteDescripcion(ExtraccionSinTarjetaFragment.this.i));
                ExtraccionSinTarjetaFragment.this.ad.trackScreen(ExtraccionSinTarjetaFragment.this.getString(R.string.analytics_extraccion_limite_diario));
                ExtraccionSinTarjetaFragment.this.startActivity(ExtraccionSinTarjetaFragment.this.f);
            }
        });
        this.inp_importe.setText("");
        this.inp_importe.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!ExtraccionSinTarjetaFragment.this.a) {
                    ExtraccionSinTarjetaFragment.this.a = true;
                    if (ExtraccionSinTarjetaFragment.this.inp_importe.getText().length() > 0) {
                        ExtraccionSinTarjetaFragment.this.btn_continuar.setBackground(ExtraccionSinTarjetaFragment.this.getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                        ExtraccionSinTarjetaFragment.this.btn_continuar.setTextColor(ExtraccionSinTarjetaFragment.this.getResources().getColor(R.color.white));
                        ExtraccionSinTarjetaFragment.this.btn_continuar.setEnabled(true);
                    } else {
                        ExtraccionSinTarjetaFragment.this.btn_continuar.setBackground(ExtraccionSinTarjetaFragment.this.getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
                        ExtraccionSinTarjetaFragment.this.btn_continuar.setTextColor(ExtraccionSinTarjetaFragment.this.getResources().getColor(R.color.white));
                        ExtraccionSinTarjetaFragment.this.btn_continuar.setEnabled(false);
                    }
                    ExtraccionSinTarjetaFragment.this.a = false;
                }
            }
        });
        this.img_botonAyudaLimiteDiario.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_AYUDA_LIMITEDIARIO)));
        showProgress(VCargaDatosInicialesExtEnv.nameService);
        this.h.cargaDatosInicialesExt();
        this.ad.trackScreen(getString(R.string.analytics_extraccion_home));
        return this.e;
    }

    /* access modifiers changed from: private */
    public void A() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID2006_EXTTARJETA_BTN_OPEREALIZADAS));
        arrayList.add(getString(R.string.CONTENT_AYUDA));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(ExtraccionSinTarjetaFragment.this.getString(R.string.ID2006_EXTTARJETA_BTN_OPEREALIZADAS))) {
                    ExtraccionSinTarjetaFragment.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosExt(ExtraccionSinTarjetaFragment.this.h, ExtraccionSinTarjetaFragment.this.i.getDatosInicialesExtExv().filtrosMandatos.filtroDefault);
                } else if (str.equalsIgnoreCase(ExtraccionSinTarjetaFragment.this.getString(R.string.CONTENT_AYUDA))) {
                    ExtraccionSinTarjetaFragment.this.B();
                }
            }
        });
        newInstance.show(getFragmentManager(), "OptionsDialog");
    }

    /* access modifiers changed from: private */
    public void B() {
        ButterKnife.inject((Object) this, this.e);
        this.f = new Intent(getActivity(), InfoActivity.class);
        this.f.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        this.f.putExtra(InfoActivity.TITLE_TO_SHOW, CExtEnv.getFuncionalidadTitulo(this.i));
        this.f.putExtra(InfoActivity.TEXT_TO_SHOW, CExtEnv.getFuncionalidadDescripcion(this.i));
        this.ad.trackScreen(getString(R.string.analytics_extraccion_ayuda));
        startActivity(this.f);
    }

    /* access modifiers changed from: private */
    public void C() {
        if (this.af != null) {
            this.idCuenta.setText(this.af.descCtaDebito);
            a(this.af.saldoCtaDebito, false);
            try {
                this.idCuenta.setContentDescription(new CAccessibility(getActivity()).applyFilterAccount(this.af.descCtaDebito));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onCargaDatosInicialesExtEnv(CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent) {
        dismissProgress();
        final CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent2 = cargaDatosInicialesExtEnvEvent;
        AnonymousClass7 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.EXTRACCION_SIN_TARJETA, this, (BaseActivity) getActivity()) {
            public void onOk() {
                ExtraccionSinTarjetaFragment.this.onCargaDatosInicialesExtEnvResultOk(cargaDatosInicialesExtEnvEvent2);
            }
        };
        r0.handleWSResponse(cargaDatosInicialesExtEnvEvent);
    }

    public void onCargaDatosInicialesExtEnvResultOk(CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent) {
        try {
            z();
            this.extraccionSinTarjetaView.setVisibility(0);
            TextView textView = this.lbl_limiteDiario;
            String charSequence = this.lbl_limiteDiario.getText().toString();
            String string = getResources().getString(R.string.STRING_PARAM_KEY);
            StringBuilder sb = new StringBuilder();
            sb.append("$");
            sb.append(this.ah);
            textView.setText(charSequence.replace(string, sb.toString()));
            TextView textView2 = this.lbl_limiteDiario;
            String charSequence2 = this.lbl_limiteDiario.getText().toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("$");
            sb2.append(this.ah);
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.ah);
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(getResources().getString(R.string.ACCESSIBILITY_TRANSFERENCIAS_PESOS_ARGENTINOS));
            textView2.setContentDescription(charSequence2.replace(sb3, sb4.toString()));
            this.ae = this.i.getDatosInicialesExtExv().listaCuentas.cuenta;
            if (this.ae.size() > 0) {
                this.af = (ExtEnvCuentaBean) this.ae.get(0);
                if (this.ae.size() == 1) {
                    ((CustomSpinner) this.e.findViewById(R.id.F19_00_csp_selectorCuentas_vgSelectorAccount)).setVisibility(8);
                }
            }
            C();
            D();
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCargaDatosInicialesExtEnvResultOk: ", e2);
        }
    }

    private void D() {
        this.g = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!this.g.getBoolean(getResources().getString(R.string.PREFERENCE_ONBOARDING_EXTRACC_SIN_TARJETA), false)) {
            showOnBoarding(R.layout.layout_onboarding_extraccsintarjeta, R.id.f29F1901_BTN_OnBoarding_Page3_Close, R.id.f30F1901_FLP_OnBoarding, getResources().getString(R.string.PREFERENCE_ONBOARDING_EXTRACC_SIN_TARJETA));
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131362524})
    public void y() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (ExtEnvCuentaBean extEnvCuentaBean : this.ae) {
            arrayList.add(extEnvCuentaBean.descCtaDebito);
            try {
                arrayList2.add(new CAccessibility(getActivity()).applyFilterAccount(extEnvCuentaBean.descCtaDebito));
            } catch (Exception unused) {
            }
        }
        if (arrayList.size() > 1) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_cuenta", getString(R.string.ID94_ACCOUNTS_CHANGEACC_LBL_SELECT), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.af.descCtaDebito, arrayList2);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    try {
                        ExtraccionSinTarjetaFragment.this.idCuenta.setContentDescription(new CAccessibility(ExtraccionSinTarjetaFragment.this.getActivity()).applyFilterAccount(str));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ExtraccionSinTarjetaFragment.this.idCuenta.setText(str);
                    for (ExtEnvCuentaBean extEnvCuentaBean : ExtraccionSinTarjetaFragment.this.ae) {
                        if (str.equalsIgnoreCase(extEnvCuentaBean.descCtaDebito)) {
                            ExtraccionSinTarjetaFragment.this.af = extEnvCuentaBean;
                            ExtraccionSinTarjetaFragment.this.C();
                        }
                    }
                }
            });
            newInstance.setCancelable(true);
            newInstance.show(getActivity().getSupportFragmentManager(), "Dialog");
        }
    }

    private void a(String str, boolean z) {
        CAmount cAmount = new CAmount(str);
        cAmount.setSymbolCurrencyDollarOrPeso(z);
        this.cuentaSaldoValue.setColorAmount(cAmount.isAmountPossite());
        this.cuentaSaldoValue.setAmount(cAmount.getAmount());
        this.cuentaSaldoValue.setCElementAcc(new CAmountAcc());
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.idCuenta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblselectorcuentas, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lblsaldocuenta, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.cuentaSaldoValue, -1.5f);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r8, int r9, android.content.Intent r10) {
        /*
            r7 = this;
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r7.inp_importe
            java.lang.String r1 = ""
            r0.setText(r1)
            r0 = 0
            r1 = -1
            if (r9 != r1) goto L_0x0022
            java.lang.String r2 = "PrivateMenuSelectedOptionPosition"
            boolean r2 = r10.hasExtra(r2)
            if (r2 == 0) goto L_0x0022
            android.support.v4.app.FragmentActivity r2 = r7.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r2 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r2
            java.lang.String r3 = "PrivateMenuSelectedOptionPosition"
            int r3 = r10.getIntExtra(r3, r0)
            r2.onPrivateMenuOptionSelectedInNestedActivity(r3)
        L_0x0022:
            r2 = 3
            r3 = 4
            if (r9 != r1) goto L_0x00ab
            java.lang.String r4 = "WS_ERROR_DO_ACTION"
            boolean r4 = r10.hasExtra(r4)
            if (r4 == 0) goto L_0x00ab
            java.lang.String r4 = "WS_ERROR_DO_ACTION"
            java.lang.String r4 = r10.getStringExtra(r4)
            int r5 = r4.hashCode()
            switch(r5) {
                case -1667304550: goto L_0x0064;
                case -1442009346: goto L_0x005a;
                case -1365838438: goto L_0x0050;
                case -171755572: goto L_0x0046;
                case 4216548: goto L_0x003c;
                default: goto L_0x003b;
            }
        L_0x003b:
            goto L_0x006e
        L_0x003c:
            java.lang.String r5 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x006e
            r4 = 4
            goto L_0x006f
        L_0x0046:
            java.lang.String r5 = "GO_TO_HOME"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x006e
            r4 = 0
            goto L_0x006f
        L_0x0050:
            java.lang.String r5 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x006e
            r4 = 1
            goto L_0x006f
        L_0x005a:
            java.lang.String r5 = "GO_TO_CUENTAS"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x006e
            r4 = 2
            goto L_0x006f
        L_0x0064:
            java.lang.String r5 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x006e
            r4 = 3
            goto L_0x006f
        L_0x006e:
            r4 = -1
        L_0x006f:
            switch(r4) {
                case 0: goto L_0x00a2;
                case 1: goto L_0x00ab;
                case 2: goto L_0x0096;
                case 3: goto L_0x0086;
                case 4: goto L_0x0073;
                default: goto L_0x0072;
            }
        L_0x0072:
            goto L_0x00ab
        L_0x0073:
            android.support.v4.app.FragmentActivity r4 = r7.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r5 = r10.getStringExtra(r5)
            r6 = 2131230955(0x7f0800eb, float:1.8077977E38)
            r4.setErrorFragment(r5, r6)
            goto L_0x00ab
        L_0x0086:
            android.support.v4.app.FragmentActivity r4 = r7.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r5 = r10.getStringExtra(r5)
            r4.setErrorFragment(r5)
            goto L_0x00ab
        L_0x0096:
            android.support.v4.app.FragmentActivity r4 = r7.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            java.lang.String r5 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r4.goToOption(r5)
            goto L_0x00ab
        L_0x00a2:
            android.support.v4.app.FragmentActivity r4 = r7.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r4 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r4
            r4.gotoHome()
        L_0x00ab:
            if (r8 == r2) goto L_0x00b1
            r2 = 7
            if (r8 == r2) goto L_0x00b1
            goto L_0x00d6
        L_0x00b1:
            if (r9 != r1) goto L_0x00d6
            java.lang.String r8 = "RECARGAR_PANTALLA_INICIAL_EXT"
            boolean r8 = r10.getBooleanExtra(r8, r0)
            if (r8 == 0) goto L_0x00d6
            android.view.View r8 = r7.extraccionSinTarjetaView
            r8.setVisibility(r3)
            java.lang.String r8 = "cargaDatosInicialesExtEnv"
            r7.showProgress(r8)
            ar.com.santander.rio.mbanking.managers.data.IDataManager r8 = r7.h
            r8.cargaDatosInicialesExt()
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r8 = r7.ad
            r9 = 2131757650(0x7f100a52, float:1.9146242E38)
            java.lang.String r9 = r7.getString(r9)
            r8.trackScreen(r9)
        L_0x00d6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.ExtraccionSinTarjetaFragment.onActivityResult(int, int, android.content.Intent):void");
    }

    @Subscribe
    public void onConsultaMandatos(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        dismissProgress();
        final ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent2 = consultaMandatosExtEnvEvent;
        AnonymousClass9 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.EXTRACCION_SIN_TARJETA, this, (BaseActivity) getActivity()) {
            public void onOk() {
                ExtraccionSinTarjetaFragment.this.onConsultaMandatosResultOk(consultaMandatosExtEnvEvent2);
            }

            public void onRes4Error() {
                ExtraccionSinTarjetaFragment.this.onConsultaMandatosResultOk(consultaMandatosExtEnvEvent2);
            }
        };
        r0.handleWSResponse(consultaMandatosExtEnvEvent);
    }

    public void onConsultaMandatosResultOk(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        try {
            Intent intent = new Intent(getActivity(), ActivityExtraccionSinTarjetaOperacionesRealizadas.class);
            intent.putParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS), CExtEnv.getMandatos(consultaMandatosExtEnvEvent.getBeanResponse() != null ? ((ConsultaMandatosExtEnvResponseBean) consultaMandatosExtEnvEvent.getBeanResponse()).consultaMandatosExtEnvBodyResponseBean.listaMandatos : null));
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS_RES4), consultaMandatosExtEnvEvent.getResult() == TypeResult.BEAN_ERROR_RES_4 ? consultaMandatosExtEnvEvent.getMessageToShow() : "");
            startActivityForResult(intent, 7);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaMandatosResultOk: ", e2);
        }
    }
}
