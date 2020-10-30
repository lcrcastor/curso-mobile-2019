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
import ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios;
import ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroOperacionesRealizadas;
import ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroPrepararEnvio;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.OnBoardingTextStylingSet;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CargaDatosInicialesExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaAgendadosEnvEfeEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaMandatosExtEnvEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCargaDatosInicialesExtEnv;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAgendadosEnvEfe;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaMandatosExtEnv;
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
import java.util.List;
import javax.inject.Inject;

public class EnvioDineroFragment extends BaseFragment implements OnClickListener {
    boolean a;
    @Inject
    SessionManager ad;
    @Inject
    AnalyticsManager ae;
    /* access modifiers changed from: private */
    public List<ExtEnvCuentaBean> af;
    /* access modifiers changed from: private */
    public ExtEnvCuentaBean ag = null;
    /* access modifiers changed from: private */
    public Integer ah;
    /* access modifiers changed from: private */
    public Integer ai;
    private OnBoardingTextStylingSet aj;
    ImageView b;
    @InjectView(2131362250)
    Button btn_continuar;
    ImageView c;
    @InjectView(2131362254)
    AmountView cuentaSaldoValue;
    Intent d;
    NumericEditTextWithPrefixAccesibility e;
    @InjectView(2131364756)
    View envioDineroView;
    View f;
    View g;
    SharedPreferences h;
    @Inject
    IDataManager i;
    @InjectView(2131362258)
    TextView idCuenta;
    @InjectView(2131362252)
    ImageView img_botonAyudaLimiteDiario;
    @InjectView(2131362251)
    CustomSpinner lbl_cambiarCuenta;
    @InjectView(2131362256)
    TextView lbl_limiteDiario;
    @InjectView(2131362261)
    LinearLayout selectorCuentas;

    public void onClick(View view) {
    }

    private void z() {
        this.ah = Integer.valueOf(this.ad.getDatosInicialesExtExv().datosExtEnv.importeMultiplo);
        this.ai = Integer.valueOf(this.ad.getDatosInicialesExtExv().datosExtEnv.importeLimite);
    }

    public void onCreate(Bundle bundle) {
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(bundle);
        this.ae.trackScreen(getString(R.string.analytics_enviodinero_home));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.g = getActivity().getLayoutInflater().inflate(R.layout.fragment_envio_dinero, viewGroup, false);
        ButterKnife.inject((Object) this, this.g);
        ((RelativeLayout) this.g.findViewById(R.id.F12_00_rll_scroll)).setMinimumHeight(getActivity().findViewById(16908290).getHeight());
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.f = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        this.b = (ImageView) this.f.findViewById(R.id.menu);
        this.c = (ImageView) this.f.findViewById(R.id.toggle);
        this.b.setContentDescription(getString(R.string.IDXX_CONTENT_DESCRIPTION_BTN_OPTIONS));
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EnvioDineroFragment.this.showOptionsMenu();
            }
        });
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EnvioDineroFragment.this.switchDrawer();
            }
        });
        this.e = (NumericEditTextWithPrefixAccesibility) this.g.findViewById(R.id.F12_00_inp_importe);
        this.e.setText("");
        this.e.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!EnvioDineroFragment.this.a) {
                    EnvioDineroFragment.this.a = true;
                    if (EnvioDineroFragment.this.e.getText().length() > 0) {
                        EnvioDineroFragment.this.btn_continuar.setBackground(EnvioDineroFragment.this.getResources().getDrawable(R.drawable.boton_redondeado_rojo));
                        EnvioDineroFragment.this.btn_continuar.setTextColor(EnvioDineroFragment.this.getResources().getColor(R.color.white));
                        EnvioDineroFragment.this.btn_continuar.setEnabled(true);
                    } else {
                        EnvioDineroFragment.this.btn_continuar.setBackground(EnvioDineroFragment.this.getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
                        EnvioDineroFragment.this.btn_continuar.setTextColor(EnvioDineroFragment.this.getResources().getColor(R.color.white));
                        EnvioDineroFragment.this.btn_continuar.setEnabled(false);
                    }
                    EnvioDineroFragment.this.a = false;
                }
            }
        });
        new OnClickListener() {
            public void onClick(View view) {
                view.getId();
            }
        };
        this.btn_continuar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Integer valueWithoutFormat = CExtEnv.getValueWithoutFormat(EnvioDineroFragment.this.e.getText().toString());
                if (valueWithoutFormat.intValue() <= 0 || valueWithoutFormat.intValue() % EnvioDineroFragment.this.ah.intValue() != 0 || valueWithoutFormat.intValue() > EnvioDineroFragment.this.ai.intValue()) {
                    IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(EnvioDineroFragment.this.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), Utils.formatIsbanHTMLCode(Html.fromHtml(EnvioDineroFragment.this.getString(R.string.USER000040)).toString()), null, null, EnvioDineroFragment.this.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                    newInstance.setDialogListener(new IDialogListener() {
                        public void onItemSelected(String str) {
                        }

                        public void onNegativeButton() {
                        }

                        public void onPositiveButton() {
                        }

                        public void onSimpleActionButton() {
                            EnvioDineroFragment.this.e.requestFocus();
                        }
                    });
                    newInstance.show(EnvioDineroFragment.this.getActivity().getSupportFragmentManager(), "Dialog");
                    return;
                }
                Intent intent = new Intent(EnvioDineroFragment.this.getActivity(), ActivityEnvioDineroPrepararEnvio.class);
                intent.putExtra(EnvioDineroFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_DINERO), valueWithoutFormat.toString());
                intent.putExtra(EnvioDineroFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_CUENTA), EnvioDineroFragment.this.ag.descCtaDebito);
                intent.putExtra(EnvioDineroFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_TIPOCUENTA), EnvioDineroFragment.this.ag.tipo);
                intent.putExtra(EnvioDineroFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_SUCURSALCUENTA), EnvioDineroFragment.this.ag.sucursal);
                intent.putExtra(EnvioDineroFragment.this.getResources().getString(R.string.INTENT_PUT_EXTRA_NUMEROCUENTA), EnvioDineroFragment.this.ag.numero);
                EnvioDineroFragment.this.startActivityForResult(intent, 10);
            }
        });
        this.img_botonAyudaLimiteDiario.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EnvioDineroFragment.this.d = new Intent(EnvioDineroFragment.this.getActivity(), InfoActivity.class);
                EnvioDineroFragment.this.d.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                EnvioDineroFragment.this.d.putExtra(InfoActivity.TITLE_TO_SHOW, CExtEnv.getLimiteTitulo(EnvioDineroFragment.this.ad));
                EnvioDineroFragment.this.d.putExtra(InfoActivity.TEXT_TO_SHOW, CExtEnv.getLimiteDescripcion(EnvioDineroFragment.this.ad));
                EnvioDineroFragment.this.ae.trackScreen(EnvioDineroFragment.this.getString(R.string.analytics_enviodinero_limite_diario));
                EnvioDineroFragment.this.startActivity(EnvioDineroFragment.this.d);
            }
        });
        this.img_botonAyudaLimiteDiario.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_AYUDA_LIMITEDIARIO)));
        this.btn_continuar.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.ID2005_ENVEFECT_BTN_CONTINUAR)));
        this.lbl_cambiarCuenta.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.ID2001_ENVEFECT_BTN_CAMBIARCTA)));
        this.b.setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.LIST_OPTIONS_DIALOG_FRAGMENT)));
        showProgress(VCargaDatosInicialesExtEnv.nameService);
        this.i.cargaDatosInicialesEnv();
        this.ae.trackScreen(getString(R.string.analytics_enviodinero_home));
        return this.g;
    }

    public void showOptionsMenu() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID2006_ENVEFECT_BTN_OPEREALIZADAS));
        arrayList.add(getString(R.string.ID2007_ENVEFECT_BTN_AGENDEST));
        arrayList.add(getString(R.string.ID2154_ENVIARDINERO_LBL_AYUDA_TIT));
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(EnvioDineroFragment.this.getString(R.string.ID2006_ENVEFECT_BTN_OPEREALIZADAS))) {
                    EnvioDineroFragment.this.showProgress(VConsultaMandatosExtEnv.nameService);
                    CExtEnv.consultaMandatosEnv(EnvioDineroFragment.this.i, EnvioDineroFragment.this.ad.getDatosInicialesExtExv().filtrosMandatos.filtroDefault);
                } else if (str.equalsIgnoreCase(EnvioDineroFragment.this.getString(R.string.ID2007_ENVEFECT_BTN_AGENDEST))) {
                    EnvioDineroFragment.this.showProgress(VConsultaAgendadosEnvEfe.nameService);
                    CExtEnv.consultaAgendados(EnvioDineroFragment.this.i);
                } else if (str.equalsIgnoreCase(EnvioDineroFragment.this.getString(R.string.ID2154_ENVIARDINERO_LBL_AYUDA_TIT))) {
                    EnvioDineroFragment.this.A();
                }
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(getFragmentManager(), "OptionsMenu");
    }

    /* access modifiers changed from: private */
    public void A() {
        this.d = new Intent(getActivity(), InfoActivity.class);
        this.d.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        this.d.putExtra(InfoActivity.TITLE_TO_SHOW, CExtEnv.getFuncionalidadTitulo(this.ad));
        this.d.putExtra(InfoActivity.TEXT_TO_SHOW, CExtEnv.getFuncionalidadDescripcion(this.ad));
        this.ae.trackScreen(getString(R.string.analytics_enviodinero_ayuda));
        startActivity(this.d);
    }

    /* access modifiers changed from: private */
    public void B() {
        if (this.ag != null) {
            this.idCuenta.setText(this.ag.descCtaDebito);
            a(this.ag.saldoCtaDebito, false);
            try {
                this.idCuenta.setContentDescription(new CAccessibility(getActivity()).applyFilterAccount(this.ag.descCtaDebito));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onCargaDatosInicialesExtEnv(CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent) {
        dismissProgress();
        final CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent2 = cargaDatosInicialesExtEnvEvent;
        AnonymousClass8 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.ENVIO_EFECTIVO, this, (BaseActivity) getActivity()) {
            public void onOk() {
                EnvioDineroFragment.this.onCargaDatosInicialesExtEnvResultOk(cargaDatosInicialesExtEnvEvent2);
            }
        };
        r0.handleWSResponse(cargaDatosInicialesExtEnvEvent);
    }

    public void onCargaDatosInicialesExtEnvResultOk(CargaDatosInicialesExtEnvEvent cargaDatosInicialesExtEnvEvent) {
        try {
            z();
            this.envioDineroView.setVisibility(0);
            TextView textView = this.lbl_limiteDiario;
            String charSequence = this.lbl_limiteDiario.getText().toString();
            String string = getResources().getString(R.string.STRING_PARAM_KEY);
            StringBuilder sb = new StringBuilder();
            sb.append("$");
            sb.append(this.ai);
            textView.setText(charSequence.replace(string, sb.toString()));
            TextView textView2 = this.lbl_limiteDiario;
            String charSequence2 = this.lbl_limiteDiario.getText().toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("$");
            sb2.append(this.ai);
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.ai);
            sb4.append(UtilsCuentas.SEPARAOR2);
            sb4.append(getResources().getString(R.string.ACCESSIBILITY_TRANSFERENCIAS_PESOS_ARGENTINOS));
            textView2.setContentDescription(charSequence2.replace(sb3, sb4.toString()));
            this.af = this.ad.getDatosInicialesExtExv().listaCuentas.cuenta;
            if (this.af.size() > 0) {
                this.ag = (ExtEnvCuentaBean) this.af.get(0);
                if (this.af.size() == 1) {
                    ((CustomSpinner) this.g.findViewById(R.id.F12_00_csp_selectorCuentas_vgSelectorAccount)).setVisibility(8);
                }
            }
            B();
            C();
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCargaDatosInicialesExtEnvResultOk: ", e2);
        }
    }

    private void C() {
        this.h = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!this.h.getBoolean(getResources().getString(R.string.PREFERENCE_ONBOARDING_ENVIO_DINERO), false)) {
            this.aj = new OnBoardingTextStylingSet();
            this.aj.put(Integer.valueOf(R.id.f12F1201_LBL_OnBoarding_Page1_Label2), getString(R.string.ID2165_ENVEFECT_LBL_ONBOARDING_2_HTML));
            showOnBoarding(R.layout.layout_onboarding_enviodinero, R.id.f9F1201_BTN_OnBoarding_Page3_Close, R.id.f10F1201_FLP_OnBoarding, getResources().getString(R.string.PREFERENCE_ONBOARDING_ENVIO_DINERO), this.aj);
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131362251})
    public void y() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (ExtEnvCuentaBean extEnvCuentaBean : this.af) {
            arrayList.add(extEnvCuentaBean.descCtaDebito);
            try {
                arrayList2.add(new CAccessibility(getActivity()).applyFilterAccount(extEnvCuentaBean.descCtaDebito));
            } catch (Exception unused) {
            }
        }
        if (arrayList.size() > 1) {
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_cuenta", getString(R.string.ID94_ACCOUNTS_CHANGEACC_LBL_SELECT), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.ag.descCtaDebito, arrayList2);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    try {
                        EnvioDineroFragment.this.idCuenta.setContentDescription(new CAccessibility(EnvioDineroFragment.this.getActivity()).applyFilterAccount(str));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    EnvioDineroFragment.this.idCuenta.setText(str);
                    for (ExtEnvCuentaBean extEnvCuentaBean : EnvioDineroFragment.this.af) {
                        if (str.equalsIgnoreCase(extEnvCuentaBean.descCtaDebito)) {
                            EnvioDineroFragment.this.ag = extEnvCuentaBean;
                            EnvioDineroFragment.this.B();
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
        UtilStyleCommons.setLetterSpacing(this.g.findViewById(R.id.F12_00_lbl_selectorCuentas_idCuenta), -1.5f);
        UtilStyleCommons.setLetterSpacing(this.g.findViewById(R.id.F12_00_lbl_saldoCuenta), -1.5f);
        UtilStyleCommons.setLetterSpacing(this.g.findViewById(R.id.F12_00_inp_importe), -1.5f);
        UtilStyleCommons.setLetterSpacing(this.g.findViewById(R.id.F12_00_lbl_limiteDiario), -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_cambiarCuenta, -1.5f);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r7, int r8, android.content.Intent r9) {
        /*
            r6 = this;
            ar.com.santander.rio.mbanking.view.NumericEditTextWithPrefixAccesibility r0 = r6.e
            java.lang.String r1 = ""
            r0.setText(r1)
            r0 = 0
            r1 = -1
            if (r8 != r1) goto L_0x0022
            java.lang.String r2 = "PrivateMenuSelectedOptionPosition"
            boolean r2 = r9.hasExtra(r2)
            if (r2 == 0) goto L_0x0022
            android.support.v4.app.FragmentActivity r2 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r2 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r2
            java.lang.String r3 = "PrivateMenuSelectedOptionPosition"
            int r3 = r9.getIntExtra(r3, r0)
            r2.onPrivateMenuOptionSelectedInNestedActivity(r3)
        L_0x0022:
            r2 = 4
            if (r8 != r1) goto L_0x00b6
            java.lang.String r3 = "WS_ERROR_DO_ACTION"
            boolean r3 = r9.hasExtra(r3)
            if (r3 == 0) goto L_0x00b6
            java.lang.String r3 = "WS_ERROR_DO_ACTION"
            java.lang.String r3 = r9.getStringExtra(r3)
            int r4 = r3.hashCode()
            switch(r4) {
                case -1667304550: goto L_0x0063;
                case -1442009346: goto L_0x0059;
                case -1365838438: goto L_0x004f;
                case -171755572: goto L_0x0045;
                case 4216548: goto L_0x003b;
                default: goto L_0x003a;
            }
        L_0x003a:
            goto L_0x006d
        L_0x003b:
            java.lang.String r4 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x006d
            r3 = 4
            goto L_0x006e
        L_0x0045:
            java.lang.String r4 = "GO_TO_HOME"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x006d
            r3 = 0
            goto L_0x006e
        L_0x004f:
            java.lang.String r4 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x006d
            r3 = 1
            goto L_0x006e
        L_0x0059:
            java.lang.String r4 = "GO_TO_CUENTAS"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x006d
            r3 = 2
            goto L_0x006e
        L_0x0063:
            java.lang.String r4 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x006d
            r3 = 3
            goto L_0x006e
        L_0x006d:
            r3 = -1
        L_0x006e:
            switch(r3) {
                case 0: goto L_0x00ad;
                case 1: goto L_0x00a1;
                case 2: goto L_0x0095;
                case 3: goto L_0x0085;
                case 4: goto L_0x0072;
                default: goto L_0x0071;
            }
        L_0x0071:
            goto L_0x00b6
        L_0x0072:
            android.support.v4.app.FragmentActivity r3 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r4 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r4 = r9.getStringExtra(r4)
            r5 = 2131230955(0x7f0800eb, float:1.8077977E38)
            r3.setErrorFragment(r4, r5)
            goto L_0x00b6
        L_0x0085:
            android.support.v4.app.FragmentActivity r3 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r4 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r4 = r9.getStringExtra(r4)
            r3.setErrorFragment(r4)
            goto L_0x00b6
        L_0x0095:
            android.support.v4.app.FragmentActivity r3 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r3.goToOption(r4)
            goto L_0x00b6
        L_0x00a1:
            android.support.v4.app.FragmentActivity r3 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            java.lang.String r4 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.ENVIO_EFECTIVO
            r3.goToOption(r4)
            goto L_0x00b6
        L_0x00ad:
            android.support.v4.app.FragmentActivity r3 = r6.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r3 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r3
            r3.gotoHome()
        L_0x00b6:
            r3 = 7
            if (r7 == r3) goto L_0x00c0
            r3 = 10
            if (r7 == r3) goto L_0x00c0
            r8 = 12
            goto L_0x00e5
        L_0x00c0:
            if (r8 != r1) goto L_0x00e5
            java.lang.String r7 = "RECARGAR_PANTALLA_INICIAL_ENV"
            boolean r7 = r9.getBooleanExtra(r7, r0)
            if (r7 == 0) goto L_0x00e5
            android.view.View r7 = r6.envioDineroView
            r7.setVisibility(r2)
            java.lang.String r7 = "cargaDatosInicialesExtEnv"
            r6.showProgress(r7)
            ar.com.santander.rio.mbanking.managers.data.IDataManager r7 = r6.i
            r7.cargaDatosInicialesEnv()
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r7 = r6.ae
            r8 = 2131757587(0x7f100a13, float:1.9146114E38)
            java.lang.String r8 = r6.getString(r8)
            r7.trackScreen(r8)
        L_0x00e5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.EnvioDineroFragment.onActivityResult(int, int, android.content.Intent):void");
    }

    @Subscribe
    public void onConsultaMandatos(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        dismissProgress();
        final ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent2 = consultaMandatosExtEnvEvent;
        AnonymousClass10 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this, (BaseActivity) getActivity()) {
            public void onOk() {
                EnvioDineroFragment.this.onConsultaMandatosResultOk(consultaMandatosExtEnvEvent2);
            }

            public void onRes4Error() {
                EnvioDineroFragment.this.onConsultaMandatosResultOk(consultaMandatosExtEnvEvent2);
            }
        };
        r0.handleWSResponse(consultaMandatosExtEnvEvent);
    }

    public void onConsultaMandatosResultOk(ConsultaMandatosExtEnvEvent consultaMandatosExtEnvEvent) {
        try {
            Intent intent = new Intent(getActivity(), ActivityEnvioDineroOperacionesRealizadas.class);
            intent.putParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS), CExtEnv.getMandatos(consultaMandatosExtEnvEvent.getBeanResponse() != null ? ((ConsultaMandatosExtEnvResponseBean) consultaMandatosExtEnvEvent.getBeanResponse()).consultaMandatosExtEnvBodyResponseBean.listaMandatos : null));
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAMANDATOS_RES4), consultaMandatosExtEnvEvent.getResult() == TypeResult.BEAN_ERROR_RES_4 ? consultaMandatosExtEnvEvent.getMessageToShow() : "");
            startActivityForResult(intent, 7);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaMandatosResultOk: ", e2);
        }
    }

    @Subscribe
    public void onConsultaAgendados(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        dismissProgress();
        final ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent2 = consultaAgendadosEnvEfeEvent;
        AnonymousClass11 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.ENVIO_EFECTIVO, this, (BaseActivity) getActivity()) {
            public void onOk() {
                EnvioDineroFragment.this.onConsultaAgendadosResultOk(consultaAgendadosEnvEfeEvent2);
            }
        };
        r0.handleWSResponse(consultaAgendadosEnvEfeEvent);
    }

    public void onConsultaAgendadosResultOk(ConsultaAgendadosEnvEfeEvent consultaAgendadosEnvEfeEvent) {
        try {
            Intent intent = new Intent(getActivity(), ActivityEnvioDineroListadoDestinatarios.class);
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIOS_MODE), getResources().getString(R.string.ED_INTENT_PUT_EXTRA_DESTINATARIOS_ABM_ONLY));
            intent.putParcelableArrayListExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS), CExtEnv.getDestinatarios(((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios));
            intent.putExtra(getResources().getString(R.string.ED_INTENT_PUT_EXTRA_CONSULTAAGENDADOS_RES4), ((ConsultaAgendadosEnvEfeResponseBean) consultaAgendadosEnvEfeEvent.getBeanResponse()).consultaAgendadosEnvEfeBodyResponseBean.listaDestinatarios == null ? consultaAgendadosEnvEfeEvent.getMessageToShow() : "");
            startActivityForResult(intent, 12);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onConsultaAgendadosResultOk: ", e2);
        }
    }
}
