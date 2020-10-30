package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CTypeDocument;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.exceptions.EmptyAppShare;
import ar.com.santander.rio.mbanking.app.module.accounts.account.DispatchShareIntentAccount;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.BajaAliasActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.ModificacionAliasActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.NuevoAliasActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.CuentasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.DismissListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaAliasEvent;
import ar.com.santander.rio.mbanking.services.events.DetCtasEvent;
import ar.com.santander.rio.mbanking.services.events.HideAccesibilityEvent;
import ar.com.santander.rio.mbanking.services.events.ResponseEvent;
import ar.com.santander.rio.mbanking.services.events.ViewsEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaAlias;
import ar.com.santander.rio.mbanking.services.soap.versions.VDetCtas;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.IHorizontalScrollListListener;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList.ToggleItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

@SuppressLint({"ValidFragment"})
public class CuentasFragment extends BaseFragment implements IDialogListenerExtended, IHorizontalScrollListListener {
    @Inject
    SettingsManager a;
    private Boolean ad = Boolean.valueOf(false);
    private Boolean ae = Boolean.valueOf(false);
    private String af = null;
    private List<Cuenta> ag;
    private boolean ah = false;
    private int ai = 0;
    private String aj;
    private String ak;
    private String al;
    private Cuenta am;
    private String an;
    @Inject
    public AnalyticsManager analyticsManager;
    @InjectView(2131364169)
    AmountView avLimiteExtraccion;
    @Inject
    IDataManager b;
    @InjectView(2131361828)
    Button botonABMAlias;
    @InjectView(2131361829)
    Button botonModificarAlias;
    @Inject
    SessionManager c;
    @InjectView(2131364554)
    TextView cuentaAliasLabel;
    @InjectView(2131364555)
    TextView cuentaAliasValue;
    @InjectView(2131364556)
    TextView cuentaCBUValue;
    @InjectView(2131364557)
    AmountView cuentaSaldoValue;
    @InjectView(2131364558)
    public View cuentasContainer;
    View d;
    private final int e = R.id.vgLoadTransactionsAccount;
    private String f;
    private String g;
    @InjectView(2131364722)
    View grayDividerLimiteExt;
    private Cuenta h = null;
    private String i = null;
    @InjectView(2131365680)
    TextView idCuenta;
    @InjectView(2131364798)
    ImageView image_ko_result;
    @InjectView(2131364553)
    ImageView imgAliasHelp;
    @InjectView(2131364869)
    ImageView ivArrow_limite_extraccion;
    @InjectView(2131364936)
    RelativeLayout layoutKO;
    @InjectView(2131364952)
    LinearLayout layoutNoAccounts;
    @InjectView(2131364957)
    LinearLayout layoutOK;
    @InjectView(2131365010)
    AmountView limiteDescubierto;
    @InjectView(2131365011)
    LinearLayout limite__descubierto_row;
    @InjectView(2131365012)
    GridLayout limite_extraction_row;
    @Inject
    public AccountAnalytics mAccountAnalytics;
    @InjectView(2131366379)
    View selectorAccount;
    @InjectView(2131364561)
    LinearLayout selectorCuentas;
    @InjectView(2131365722)
    LinearLayout shareAlias;
    @InjectView(2131365734)
    public SlidingUpPanelLayout slTransactions;
    @InjectView(2131365806)
    HorizontalScrollList tabSelector;
    @InjectView(2131366022)
    TextView textKOResult;
    public FrameLayout vgLayoutTransactions;
    @InjectView(2131366321)
    LinearLayout viewAliasCuenta;
    @InjectView(2131364559)
    LinearLayout viewCBU;
    @InjectView(2131364435)
    TextView viewCopyContentCBU;
    @InjectView(2131364560)
    LinearLayout viewSaldo;
    @InjectView(2131365723)
    TextView viewShareContentAlias;
    @InjectView(2131366445)
    ViewGroup wrapperCount;

    public void OnCheckedChangeListener(List<ToggleItem> list) {
    }

    public void onNegativeButton(String str) {
    }

    public void onPositiveButton(String str) {
    }

    public void onSimpleActionButton(String str) {
    }

    public CuentasFragment(String str) {
        this.an = str;
    }

    public CuentasFragment(String str, Cuenta cuenta) {
        this.an = str;
        this.am = cuenta;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.d = getActivity().getLayoutInflater().inflate(R.layout.cuentas_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, this.d);
        this.d.requestFocus();
        this.vgLayoutTransactions = (FrameLayout) this.d.findViewById(R.id.vgLoadTransactionsAccount);
        if (this.slTransactions != null) {
            this.slTransactions.setDragView((int) R.id.vgLoadTransactionsAccount);
            this.vgLayoutTransactions.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                }
            });
        }
        if ((!this.an.equals(FragmentConstants.CUENTAS) || !this.c.usuarioPoseeCuentas()) && (!this.an.equals(FragmentConstants.CUENTAS_PRIVADA) || !this.c.usuarioPoseeCuentasBP())) {
            this.layoutNoAccounts.setVisibility(0);
            this.layoutOK.setVisibility(8);
            this.layoutKO.setVisibility(8);
            this.selectorCuentas.setVisibility(8);
            H();
        } else {
            B();
        }
        if (this.c.getLoginUnico() == null || this.ag == null) {
            this.layoutNoAccounts.setVisibility(0);
            this.layoutKO.setVisibility(8);
            this.layoutOK.setVisibility(8);
            this.selectorCuentas.setVisibility(8);
            H();
        } else {
            Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            int i2 = point.x;
            int i3 = point.y;
            if (i2 == 480 && i3 == 800) {
                this.selectorCuentas.setPadding(0, 21, 0, -18);
            }
            B();
            this.mAccountAnalytics.registerScreenHome();
            this.f = getResources().getString(R.string.ID86_ACCOUNTS_TAB_ACCOUNTSTATES);
            this.tabSelector.addItem(this.f, true, "saldo");
            AccessibilityManager accessibilityManager = (AccessibilityManager) getActivity().getApplicationContext().getSystemService("accessibility");
            if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
                this.g = getResources().getString(R.string.ID87_ACCOUNTS_TAB_CBU);
            } else {
                this.g = getResources().getString(R.string.ID87_ACCOUNTS_TAB_CBU_ACCESIBILITY_MODE);
            }
            this.tabSelector.addItem(this.g, false, getResources().getString(R.string.ID87_ACCOUNTS_TAB_CBU_ACCESIBILITY_MODE));
            this.tabSelector.setHorizontalScrollListener(this);
            this.tabSelector.show();
            z();
            A();
            this.imgAliasHelp.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CuentasFragment.this.analyticsManager.trackEvent(CuentasFragment.this.getString(R.string.analytics_category_baja_alias), CuentasFragment.this.getString(R.string.analytics_event_action_baja_alias_click), CuentasFragment.this.getString(R.string.analytics_event_baja_alias_aclaracion_alias));
                    Intent intent = new Intent(CuentasFragment.this.getActivity(), InfoActivity.class);
                    intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                    intent.putExtra(InfoActivity.TITLE_TO_SHOW, CuentasFragment.this.getString(R.string.F01_20_LBL_CARACTERISTICA_ALIAS));
                    intent.putExtra(InfoActivity.TEXT_TO_SHOW, CuentasFragment.this.getString(R.string.F01_20_STR_ALIASAYUDA));
                    CuentasFragment.this.startActivityForResult(intent, 1);
                }
            });
            this.viewCopyContentCBU.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CuentasFragment.this.D();
                }
            });
            this.viewShareContentAlias.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CuentasFragment.this.analyticsManager.trackEvent(CuentasFragment.this.getString(R.string.analytics_category_baja_alias), CuentasFragment.this.getString(R.string.analytics_event_action_baja_alias_click), CuentasFragment.this.getString(R.string.analytics_event_baja_alias_compartir_alias));
                    CuentasFragment.this.C();
                }
            });
            this.idCuenta.setText(this.h.getFormattedName());
            this.idCuenta.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    TextView textView = (TextView) view;
                    if (textView.getLineCount() >= 2) {
                        textView.setTextSize(12.0f);
                    }
                    CuentasFragment.this.idCuenta.removeOnLayoutChangeListener(this);
                    view.setVisibility(8);
                    view.setVisibility(0);
                }
            });
            try {
                this.idCuenta.setContentDescription(new CAccessibility(getActivity()).applyFilterAccount(this.h.getFormattedName()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            I();
        }
        this.slTransactions.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                CuentasFragment.this.slTransactions.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CuentasFragment.this.slTransactions.getLocationInWindow(new int[]{0, 0});
                CuentasFragment.this.slTransactions.setPanelHeight(CuentasFragment.this.slTransactions.getHeight() - CuentasFragment.this.wrapperCount.getHeight());
            }
        });
        if (this.a.getKeyPrimerIngreso()) {
            ((SantanderRioMainActivity) getActivity()).switchDrawer();
            this.a.setKeyPrimerIngreso(false);
        }
        return this.d;
    }

    public void hideForAccessibility() {
        this.d.setImportantForAccessibility(4);
    }

    public void visibleForAccessibility() {
        this.d.setImportantForAccessibility(1);
    }

    @Subscribe
    public void onHideAccesibilityEvent(HideAccesibilityEvent hideAccesibilityEvent) {
        if (hideAccesibilityEvent.getResponse() == 0) {
            visibleForAccessibility();
        } else {
            hideForAccessibility();
        }
    }

    @Subscribe
    public void onViewEvent(ViewsEvent viewsEvent) {
        if (viewsEvent != null && this.slTransactions != null) {
            this.slTransactions.setScrollableView(viewsEvent.getView());
        }
    }

    public void onResume() {
        super.onResume();
    }

    private void z() {
        MovCtasBodyRequestBean movCtasBodyRequestBean = new MovCtasBodyRequestBean();
        movCtasBodyRequestBean.accountRequestBean = new AccountRequestBean(this.h.getTipo(), this.h.getNroSuc(), this.h.getNumero());
        showProgress(VDetCtas.nameService);
        this.i = this.h.getFormattedName().toString();
        this.ad = Boolean.valueOf(false);
        this.ae = Boolean.valueOf(false);
        this.b.getDetCtas(movCtasBodyRequestBean);
        b(this.h);
        this.cuentaSaldoValue.setColorAmount(true);
        this.cuentaSaldoValue.setAmount("-");
        this.cuentaSaldoValue.setCElementAcc(new CAmountAcc());
        this.limiteDescubierto.setTextColor(getResources().getColor(R.color.grey_dark));
        this.limiteDescubierto.setAmount("-");
        this.limiteDescubierto.setCElementAcc(new CAmountAcc());
        this.avLimiteExtraccion.setTextColor(getResources().getColor(R.color.grey_dark));
        this.avLimiteExtraccion.setAmount("-");
        this.avLimiteExtraccion.setCElementAcc(new CAmountAcc());
        if (this.an.equalsIgnoreCase(FragmentConstants.CUENTAS_PRIVADA)) {
            this.limite_extraction_row.setVisibility(8);
            this.grayDividerLimiteExt.setVisibility(8);
        }
        this.slTransactions.setTop(R.id.rowImporteTop);
    }

    private void a(boolean z) {
        this.af = this.h.getFormattedName().toString();
        if (a(this.h)) {
            this.cuentaAliasValue.setText(getString(R.string.F28_00_CUENTAS_TEXT_CONSULTA_PROGRESO_ALIAS));
            this.cuentaAliasLabel.setVisibility(0);
            this.cuentaAliasValue.setVisibility(0);
            this.imgAliasHelp.setVisibility(0);
            this.botonABMAlias.setVisibility(8);
            this.botonModificarAlias.setVisibility(8);
            this.shareAlias.setVisibility(8);
            this.ah = z;
            if (z) {
                showProgress(VConsultaAlias.nameService);
            }
            String str = "";
            String str2 = "";
            if (!TextUtils.isEmpty(this.h.getNroSuc())) {
                str = this.h.getNroSuc().substring(this.h.getNroSuc().length() - 3, this.h.getNroSuc().length());
            }
            if (!TextUtils.isEmpty(this.h.getNumero())) {
                str2 = this.h.getNumero().substring(this.h.getNumero().length() - 7, this.h.getNumero().length());
            }
            this.b.consultaAlias(this.h.getTipo(), str, str2);
            return;
        }
        this.cuentaAliasLabel.setVisibility(8);
        this.cuentaAliasValue.setVisibility(8);
        this.imgAliasHelp.setVisibility(8);
        this.botonABMAlias.setVisibility(8);
        this.botonModificarAlias.setVisibility(8);
        this.shareAlias.setVisibility(8);
    }

    private void A() {
        this.cuentaCBUValue.setText(this.h.getClaveBancariaUnificada());
    }

    private void a(String str, boolean z) {
        CAmount cAmount = new CAmount(str);
        cAmount.setSymbolCurrencyDollarOrPeso(z);
        this.cuentaSaldoValue.setColorAmount(cAmount.isAmountPossite());
        this.cuentaSaldoValue.setAmount(cAmount.getAmount());
        this.cuentaSaldoValue.setCElementAcc(new CAmountAcc());
    }

    private boolean a(Cuenta cuenta) {
        return CAccounts.getInstance(this.c).isAccountOperational(cuenta).booleanValue();
    }

    private void a(String str, String str2) {
        if (str == null || str.equals("")) {
            this.limite__descubierto_row.setVisibility(8);
            return;
        }
        CAmount cAmount = new CAmount(str);
        cAmount.setSymbolCurrency(str2);
        this.limiteDescubierto.setCElementAcc(new CAmountAcc());
        this.limiteDescubierto.setAmount(cAmount.getAmountPossitive());
        this.limiteDescubierto.setTextColor(getResources().getColor(R.color.grey_dark));
        this.limite__descubierto_row.setVisibility(0);
    }

    @Subscribe
    public void onGetDetCtas(DetCtasEvent detCtasEvent) {
        this.ad = Boolean.valueOf(true);
        if (this.ae.booleanValue()) {
            dismissProgress();
        }
        try {
            if (detCtasEvent.getResult() == TypeResult.OK) {
                this.cuentasContainer.setVisibility(0);
                this.layoutKO.setVisibility(8);
                this.layoutNoAccounts.setVisibility(8);
                this.layoutOK.setVisibility(0);
                this.slTransactions.setVisibility(0);
                MovCtasResponseBean movCtasResponseBean = (MovCtasResponseBean) detCtasEvent.getBeanResponse();
                AccountResponseBean accountResponseBean = movCtasResponseBean.movCtasBodyResponseBean.accountResponseBean;
                if (this.h.esCuentaEnDolares()) {
                    a(accountResponseBean.saldoCuentaD, true);
                    a(accountResponseBean.limiteAcuerdoD, Constants.SYMBOL_CURRENCY_DOLAR);
                } else {
                    a(accountResponseBean.saldoCuentaP, false);
                    a(accountResponseBean.limiteAcuerdoP, Constants.SYMBOL_CURRENCY_PESOS);
                }
                if (this.an.equalsIgnoreCase(FragmentConstants.CUENTAS_PRIVADA)) {
                    this.limite_extraction_row.setVisibility(8);
                    this.grayDividerLimiteExt.setVisibility(8);
                }
                this.aj = movCtasResponseBean.movCtasBodyResponseBean.limitesExtraccion.cajeroBanelcoSR;
                this.al = movCtasResponseBean.movCtasBodyResponseBean.limitesExtraccion.cajeroBanelcoOB;
                this.ak = movCtasResponseBean.movCtasBodyResponseBean.limitesExtraccion.cajeroLink;
                if (!this.aj.isEmpty()) {
                    this.limite_extraction_row.setVisibility(0);
                    this.grayDividerLimiteExt.setVisibility(0);
                    this.avLimiteExtraccion.setAmount(this.aj);
                    this.ivArrow_limite_extraccion.setVisibility(4);
                    return;
                }
                this.avLimiteExtraccion.setOnClickListener(null);
                this.ivArrow_limite_extraccion.setVisibility(4);
                this.limite_extraction_row.setVisibility(8);
                this.grayDividerLimiteExt.setVisibility(8);
            } else if (detCtasEvent.getResult() == TypeResult.BEAN_ERROR_RES_4) {
                this.cuentasContainer.setVisibility(0);
                this.slTransactions.setVisibility(8);
                this.layoutKO.setVisibility(0);
                this.layoutNoAccounts.setVisibility(8);
                this.image_ko_result.setImageResource(R.drawable.error_continuacion);
                this.layoutOK.setVisibility(8);
                this.textKOResult.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(detCtasEvent.getMessageToShow())));
            } else {
                dismissProgress();
                if (getErrorListener() != null) {
                    getErrorListener().onWebServiceErrorEvent(detCtasEvent, getTAG());
                }
            }
        } catch (Exception unused) {
            if (getErrorListener() != null) {
                getErrorListener().onWebServiceErrorEvent(detCtasEvent, getTAG());
            }
        }
    }

    @Subscribe
    public void onConsultaAlias(ConsultaAliasEvent consultaAliasEvent) {
        if (this.ah) {
            dismissProgress();
            this.ah = false;
        }
        final ConsultaAliasEvent consultaAliasEvent2 = consultaAliasEvent;
        AnonymousClass7 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.CUENTAS, this, false, (BaseActivity) getContext()) {
            public void onOk() {
                CuentasFragment.this.a(consultaAliasEvent2);
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                if (consultaAliasEvent2.getResult() == TypeResult.BEAN_ERROR_RES_5) {
                    onRes5Error(webServiceEvent);
                    return;
                }
                if (consultaAliasEvent2.getResult() == TypeResult.SERVER_ERROR || TextUtils.isEmpty(consultaAliasEvent2.getMessageToShow())) {
                    consultaAliasEvent2.setMessageToShow(CuentasFragment.this.getString(R.string.MSG_USER000069));
                }
                CuentasFragment.this.b(consultaAliasEvent2);
            }
        };
        r1.handleWSResponse(consultaAliasEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultaAliasEvent consultaAliasEvent) {
        try {
            this.viewAliasCuenta.setVisibility(0);
            ConsultaAliasResponseBean consultaAliasResponseBean = (ConsultaAliasResponseBean) consultaAliasEvent.getBeanResponse();
            if (TextUtils.isEmpty(consultaAliasResponseBean.getConsultaAliasBodyResponseBean().getCuenta().getAlias())) {
                this.analyticsManager.trackScreen(getString(R.string.analytics_screen_baja_alias_sin_alias));
                this.cuentaAliasValue.setText(getString(R.string.ID4278_F28_00_CUENTAS_TEXT_SIN_ALIAS));
                this.h.setAlias("");
                this.cuentaAliasValue.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Regular.otf"));
                this.cuentaAliasValue.setTextColor(getResources().getColor(R.color.grey_medium_light));
                this.cuentaAliasValue.setTextSize(1, 13.0f);
                this.botonABMAlias.setVisibility(0);
                this.botonModificarAlias.setVisibility(8);
                this.botonABMAlias.setText(getString(R.string.ID4268_F01_20_LBL_REFERENCE_CREAR_ALIAS));
                this.botonABMAlias.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_blanco_borde_gris));
                this.botonABMAlias.setTextColor(getResources().getColor(R.color.generic_black));
                this.shareAlias.setVisibility(8);
            } else {
                this.analyticsManager.trackScreen(getString(R.string.analytics_screen_baja_alias_con_alias));
                this.cuentaAliasValue.setText(Html.fromHtml(consultaAliasResponseBean.getConsultaAliasBodyResponseBean().getCuenta().getAlias()));
                this.h.setAlias(this.cuentaAliasValue.getText().toString());
                this.cuentaAliasValue.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Bold.otf"));
                this.cuentaAliasValue.setTextColor(getResources().getColor(R.color.grey_medium_dark));
                this.cuentaAliasValue.setTextSize(1, 17.0f);
                this.botonABMAlias.setText(getString(R.string.ID4269_F01_20_LBL_REFERENCE_ELIMINAR_ALIAS));
                this.botonABMAlias.setVisibility(0);
                this.botonABMAlias.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_blanco_borde_gris));
                this.botonABMAlias.setTextColor(getResources().getColor(R.color.generic_black));
                this.botonModificarAlias.setVisibility(0);
                this.shareAlias.setVisibility(0);
            }
            this.cuentaAliasValue.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.cuentaAliasValue.getText().toString()));
            this.cuentaAliasLabel.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.cuentaAliasLabel.getText().toString()));
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "consultaAliasResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(ConsultaAliasEvent consultaAliasEvent) {
        try {
            this.analyticsManager.trackScreen(getString(R.string.analytics_screen_baja_alias_error_alias));
            this.h.setAlias("");
            this.viewAliasCuenta.setVisibility(0);
            this.botonABMAlias.setVisibility(8);
            this.botonModificarAlias.setVisibility(8);
            this.cuentaAliasValue.setText(Html.fromHtml(consultaAliasEvent.getMessageToShow()));
            this.cuentaAliasValue.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Regular.otf"));
            this.cuentaAliasValue.setTextColor(getResources().getColor(R.color.red_medium_light));
            this.cuentaAliasValue.setTextSize(1, 13.0f);
            this.shareAlias.setVisibility(8);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "consultaAliasResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131366379})
    public void y() {
        e(false);
        try {
            this.mAccountAnalytics.registerEventChangeAccount();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Cuenta cuenta : this.ag) {
                arrayList.add(cuenta.getFormattedName());
                try {
                    arrayList2.add(new CAccessibility(getActivity()).applyFilterAccount(cuenta.getFormattedName()));
                } catch (Exception unused) {
                }
            }
            if (arrayList.size() > 1) {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_cuenta", "Seleccionar Cuenta", null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, this.h.getFormattedName(), arrayList2);
                newInstance.setDialogListenerExtended(this);
                newInstance.setCancelable(true);
                newInstance.show(getActivity().getSupportFragmentManager(), "Dialog");
                newInstance.setDismissListener(new DismissListener() {
                    public void onIsbanDismiss() {
                        CuentasFragment.this.e(true);
                    }
                });
            }
        } catch (Exception unused2) {
            e(true);
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131361828})
    public void b(View view) {
        if (TextUtils.isEmpty(this.h.getAlias())) {
            Intent intent = new Intent(getActivity(), NuevoAliasActivity.class);
            intent.putExtra("NombreCuenta", this.h);
            startActivityForResult(intent, 1);
            return;
        }
        Intent intent2 = new Intent(getActivity(), BajaAliasActivity.class);
        intent2.putExtra(CuentasConstants.cINTENT_EXTRA_ALIAS_CUENTA, this.h);
        startActivityForResult(intent2, 0);
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131361829})
    public void c(View view) {
        Intent intent = new Intent(getActivity(), ModificacionAliasActivity.class);
        intent.putExtra(CuentasConstants.cINTENT_EXTRA_ALIAS_CUENTA, this.h);
        startActivityForResult(intent, 2);
    }

    /* access modifiers changed from: private */
    public void e(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("changeStateSelectorAccount: ");
        sb.append(this.selectorAccount);
        sb.append(" isEnabled: ");
        sb.append(z);
        Log.i("selectorAccount", sb.toString());
        if (this.selectorAccount != null) {
            this.selectorAccount.setEnabled(z);
        }
    }

    private void B() {
        if (this.c != null && this.c.getLoginUnico().getProductos() != null) {
            this.ag = new ArrayList();
            if (this.c.getLoginUnico().getProductos().getCuentas() != null && this.c.getLoginUnico().getProductos().getCuentas().getCuentas() != null) {
                Iterator it = this.c.getLoginUnico().getProductos().getCuentas().getCuentas().iterator();
                loop0:
                while (true) {
                    boolean z = false;
                    while (true) {
                        if (!it.hasNext()) {
                            break loop0;
                        }
                        Cuenta cuenta = (Cuenta) it.next();
                        if (this.an.equalsIgnoreCase(FragmentConstants.CUENTAS) && Integer.valueOf(cuenta.getTipo()).intValue() != 8) {
                            try {
                                for (Cuenta nroCuentaInt : this.ag) {
                                    if (cuenta.getNroCuentaInt() == nroCuentaInt.getNroCuentaInt()) {
                                        z = true;
                                    }
                                }
                                if (!z) {
                                    this.ag.add(cuenta);
                                }
                            } catch (Exception unused) {
                                if (this.am != null) {
                                    this.ag.add(this.am);
                                } else {
                                    this.ag.add(cuenta);
                                }
                            }
                        }
                    }
                }
            }
            if (this.an.equalsIgnoreCase(FragmentConstants.CUENTAS_PRIVADA)) {
                this.ag.addAll(this.c.getLoginUnico().getProductos().getCuentasBP().getCuentasBP());
            }
            if (this.ag == null) {
                this.layoutNoAccounts.setVisibility(0);
                this.layoutKO.setVisibility(8);
                this.layoutOK.setVisibility(8);
                this.selectorCuentas.setVisibility(8);
                H();
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (Cuenta cuenta2 : this.ag) {
                if (cuenta2.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU)) {
                    Cuenta cuenta3 = (Cuenta) cuenta2.clone();
                    cuenta3.setCuentaEnDolares(false);
                    cuenta3.setTipo("09");
                    cuenta3.setFormattedName(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.c), cuenta3.getTipo(), cuenta3.getNroSuc(), cuenta3.getNumero()));
                    arrayList.add(cuenta3);
                    Cuenta cuenta4 = (Cuenta) cuenta2.clone();
                    cuenta4.setCuentaEnDolares(true);
                    cuenta4.setTipo("10");
                    cuenta4.setFormattedName(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.c), cuenta4.getTipo(), cuenta4.getNroSuc(), cuenta4.getNumero()));
                    arrayList.add(cuenta4);
                } else {
                    cuenta2.setFormattedName(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.c), cuenta2.getTipo(), cuenta2.getNroSuc(), cuenta2.getNumero()));
                    arrayList.add(cuenta2);
                }
            }
            this.ag = arrayList;
            if (this.ag.size() > 0) {
                this.h = (Cuenta) this.ag.get(0);
            }
        }
    }

    public void OnNewItemSelected(ToggleItem toggleItem) {
        if (toggleItem.getLabel().equalsIgnoreCase(this.f)) {
            this.mAccountAnalytics.registerScreenAmount();
            this.viewSaldo.setVisibility(0);
            this.viewCBU.setVisibility(8);
            I();
            if (TextUtils.isEmpty(this.i) || !this.i.equalsIgnoreCase(this.h.getFormattedName())) {
                this.i = this.h.getFormattedName().toString();
                z();
            }
        } else if (toggleItem.getLabel().equalsIgnoreCase(this.g)) {
            this.mAccountAnalytics.registerScreenCBU();
            this.viewSaldo.setVisibility(8);
            this.viewCBU.setVisibility(0);
            H();
            if (TextUtils.isEmpty(this.af) || !this.af.equalsIgnoreCase(this.h.getFormattedName())) {
                this.af = this.h.getFormattedName().toString();
                a(true);
            }
        }
    }

    public void onItemSelected(String str, String str2) {
        if (str2.equalsIgnoreCase("selector_cuenta")) {
            try {
                this.idCuenta.setContentDescription(new CAccessibility(getActivity()).applyFilterAccount(str));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.idCuenta.setText(str);
            for (Cuenta cuenta : this.ag) {
                if (str.equalsIgnoreCase(cuenta.getFormattedName())) {
                    this.h = cuenta;
                }
            }
            A();
        }
        if (this.viewSaldo.getVisibility() == 0) {
            z();
        } else {
            a(true);
        }
    }

    /* access modifiers changed from: private */
    public void C() {
        this.mAccountAnalytics.registerEventShare();
        DispatchShareIntentAccount dispatchShareIntentAccount = new DispatchShareIntentAccount(getActivity().getPackageManager());
        dispatchShareIntentAccount.setMessage(G());
        dispatchShareIntentAccount.setSubject(getString(R.string.TEXT_SUBJECT_ACCOUNT_SHARE));
        try {
            List listIntentToShare = dispatchShareIntentAccount.getListIntentToShare();
            Intent createChooser = Intent.createChooser((Intent) listIntentToShare.remove(0), getResources().getString(R.string.TEXT_TITLE_COMPARTIR));
            createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) listIntentToShare.toArray(new Intent[listIntentToShare.size()]));
            startActivity(createChooser);
        } catch (EmptyAppShare unused) {
            Toast.makeText(getActivity(), getString(R.string.IDXX_EMPTY_APP_SHARE), 1).show();
        } catch (Exception unused2) {
            Toast.makeText(getActivity(), getString(R.string.MSG_USER000029_General_errorGenerico), 1).show();
        }
    }

    /* access modifiers changed from: private */
    public void D() {
        ((ClipboardManager) getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("cbu", this.cuentaCBUValue.getText()));
        Toast.makeText(getActivity(), getString(R.string.MSG_CBU_COPIED), 0).show();
    }

    private String E() {
        String str = "";
        ListTableBean consDescripcionTPOCTALARGA = CConsDescripciones.getConsDescripcionTPOCTALARGA(this.c);
        if (consDescripcionTPOCTALARGA != null) {
            for (ListGroupBean listGroupBean : consDescripcionTPOCTALARGA.listGroupBeans) {
                if (listGroupBean.code.equalsIgnoreCase(this.h.getTipo())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(listGroupBean.getLabel());
                    sb.append(UtilsCuentas.SEPARAOR2);
                    str = sb.toString();
                }
            }
        }
        return str;
    }

    private String F() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c.getLoginUnico().getDatosPersonales().getApellido());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.c.getLoginUnico().getDatosPersonales().getNombre());
        return sb.toString();
    }

    private String G() {
        if (TextUtils.isEmpty(this.h.getAlias())) {
            StringBuilder sb = new StringBuilder();
            sb.append(CTypeDocument.getNameDocument(this.c.getLoginUnico().getDatosPersonales().getTipoDocumento()));
            sb.append(" - ");
            sb.append(Integer.parseInt(this.c.getLoginUnico().getDatosPersonales().getNroDocumento()));
            return getString(R.string.MSG_BODY_SHARE_ACCOUNT, E(), UtilAccount.getAccountFormat(this.h.getNroSuc(), this.h.getNumero()), this.h.getClaveBancariaUnificada(), F(), sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(CTypeDocument.getNameDocument(this.c.getLoginUnico().getDatosPersonales().getTipoDocumento()));
        sb2.append(" - ");
        sb2.append(Integer.parseInt(this.c.getLoginUnico().getDatosPersonales().getNroDocumento()));
        return getString(R.string.MSG_BODY_SHARE_ACCOUNT_WITH_ALIAS, E(), UtilAccount.getAccountFormat(this.h.getNroSuc(), this.h.getNumero()), this.h.getClaveBancariaUnificada(), F(), sb2.toString(), this.h.getAlias());
    }

    private void b(Cuenta cuenta) {
        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.vgLoadTransactionsAccount, MovimientosCuentaFragment.getInstance(cuenta));
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    private void H() {
        if (this.slTransactions != null) {
            this.slTransactions.setPanelState(PanelState.HIDDEN);
        }
    }

    private void I() {
        if (this.slTransactions != null) {
            this.slTransactions.setPanelState(PanelState.COLLAPSED);
        }
    }

    @Subscribe
    public void onResponseEvent(ResponseEvent responseEvent) {
        if (MovimientosCuentaFragment.class.getSimpleName().equals(responseEvent.getmTag())) {
            this.ae = Boolean.valueOf(true);
            if (this.ad.booleanValue()) {
                dismissProgress();
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        if (r5.equals(ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants.GO_TO_LOGIN) != false) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
            r4 = this;
            r0 = 0
            r1 = -1
            if (r6 != r1) goto L_0x001f
            if (r7 == 0) goto L_0x001f
            java.lang.String r2 = "PrivateMenuSelectedOptionPosition"
            boolean r2 = r7.hasExtra(r2)
            if (r2 == 0) goto L_0x001f
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = "PrivateMenuSelectedOptionPosition"
            int r6 = r7.getIntExtra(r6, r0)
            r5.onPrivateMenuOptionSelectedInNestedActivity(r6)
            goto L_0x00ca
        L_0x001f:
            r2 = 1
            if (r6 != r1) goto L_0x00b5
            if (r7 == 0) goto L_0x00b5
            java.lang.String r3 = "WS_ERROR_DO_ACTION"
            boolean r3 = r7.hasExtra(r3)
            if (r3 == 0) goto L_0x00b5
            java.lang.String r5 = "WS_ERROR_DO_ACTION"
            java.lang.String r5 = r7.getStringExtra(r5)
            int r6 = r5.hashCode()
            switch(r6) {
                case -1667304550: goto L_0x0061;
                case -1442009346: goto L_0x0057;
                case -1365838438: goto L_0x004d;
                case -171755572: goto L_0x0044;
                case 4216548: goto L_0x003a;
                default: goto L_0x0039;
            }
        L_0x0039:
            goto L_0x006b
        L_0x003a:
            java.lang.String r6 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006b
            r0 = 4
            goto L_0x006c
        L_0x0044:
            java.lang.String r6 = "GO_TO_HOME"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006b
            goto L_0x006c
        L_0x004d:
            java.lang.String r6 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006b
            r0 = 1
            goto L_0x006c
        L_0x0057:
            java.lang.String r6 = "GO_TO_CUENTAS"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006b
            r0 = 2
            goto L_0x006c
        L_0x0061:
            java.lang.String r6 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006b
            r0 = 3
            goto L_0x006c
        L_0x006b:
            r0 = -1
        L_0x006c:
            switch(r0) {
                case 0: goto L_0x00ab;
                case 1: goto L_0x009f;
                case 2: goto L_0x0093;
                case 3: goto L_0x0083;
                case 4: goto L_0x0070;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x00ca
        L_0x0070:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r6 = r7.getStringExtra(r6)
            r7 = 2131231076(0x7f080164, float:1.8078223E38)
            r5.setErrorFragment(r6, r7)
            goto L_0x00ca
        L_0x0083:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r6 = r7.getStringExtra(r6)
            r5.setErrorFragment(r6)
            goto L_0x00ca
        L_0x0093:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r5.goToOption(r6)
            goto L_0x00ca
        L_0x009f:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            java.lang.String r6 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            r5.goToOption(r6)
            goto L_0x00ca
        L_0x00ab:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5
            r5.gotoHome()
            goto L_0x00ca
        L_0x00b5:
            switch(r5) {
                case 0: goto L_0x00c5;
                case 1: goto L_0x00bf;
                case 2: goto L_0x00b9;
                default: goto L_0x00b8;
            }
        L_0x00b8:
            goto L_0x00ca
        L_0x00b9:
            if (r6 != r1) goto L_0x00ca
            r4.a(r2)
            goto L_0x00ca
        L_0x00bf:
            if (r6 != r1) goto L_0x00ca
            r4.a(r2)
            goto L_0x00ca
        L_0x00c5:
            if (r6 != r1) goto L_0x00ca
            r4.a(r2)
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.CuentasFragment.onActivityResult(int, int, android.content.Intent):void");
    }
}
