package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BackEventListener;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CustomActionBarListener;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PaymentPresenter;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PaymentServicesView;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PresenterPaymentFactory;
import ar.com.santander.rio.mbanking.app.module.payments.commons.TypeDebt;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.BasePaymentAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.CodRamo;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.TipoAlta;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.infinitescroll.InfiniteScrollListView;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaRecargaEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroAccidenteEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.GetSegurosEvent;
import ar.com.santander.rio.mbanking.services.events.RecargaCelularesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCndDeuda;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultsKeys;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.TestBatteryActivity;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import javax.inject.Inject;

public class PagosFragment extends BaseFragment implements OnClickListener, BackEventListener, CustomActionBarListener, PaymentServicesView, IDialogListenerExtended {
    public static final String NO_MOSTRAR_LINK = "NM";
    @Inject
    SettingsManager a;
    private BasePaymentAdapter ad;
    private ProgresIndicator ae;
    private int af = 0;
    private CuentaDebitoBean ag;
    private boolean ah = false;
    private boolean ai = false;
    private boolean aj = false;
    private String ak;
    private SegurosBean al = new SegurosBean();
    /* access modifiers changed from: private */
    public boolean am = false;
    private OptionsToShare an;
    private boolean ao = false;
    private final int b = 0;
    @InjectView(2131364189)
    public Button btnConfirmPayment;
    private final int c = 1;
    private final String d = "selector_account";
    @InjectView(2131364594)
    public TextView descripcionLeyenda;
    private final String e = "error_validate";
    private final String f = "error_empty_account";
    private final String g = "confirm_payment";
    private View h;
    private View i;
    @InjectView(2131364900)
    public LinearLayout layoutLink;
    @InjectView(2131365102)
    public TextView linkSeguro;
    @Inject
    public AnalyticsManager mAnalitycsManager;
    @InjectView(2131366377)
    public ViewFlipper mControlPager;
    @Inject
    public IDataManager mDataManager;
    @InjectView(2131364723)
    public InfiniteScrollListView mListPaymentServices;
    public View mMainView;
    @Inject
    public SessionManager mSessionManager;
    public PaymentPresenter presenterPayment;
    @InjectView(2131365800)
    public ScrollView svPageConfirm;
    @InjectView(2131365802)
    public ScrollView svPagePrepare;
    @InjectView(2131365803)
    public ScrollView svPageReceipt;
    @InjectView(2131366118)
    public TextView tvLegendBottom;
    @InjectView(2131366122)
    public TextView tvLegendTop;
    @InjectView(2131366319)
    public View vPageConfirmPaymentServices;
    @InjectView(2131366336)
    public View vPagePreparePaymentServices;
    @InjectView(2131366337)
    public View vPageReceiptPaymentServices;
    @InjectView(2131366348)
    public View vPageTablePaymentServices;
    @InjectView(2131366388)
    public View vgButtonReceipt;
    @InjectView(2131366392)
    public ViewGroup vgWrapperFormPreparePayment;
    @InjectView(2131366395)
    public ViewGroup vgWrapperReceiptBottomPayment;
    @InjectView(2131366397)
    public ViewGroup vgWrapperReceiptTopPayment;
    @InjectView(2131366400)
    public ViewGroup vgWrapperTableConfirmPayment;
    @InjectView(2131365299)
    public View viewContainer;
    @InjectView(2131366450)
    public View wrapperLegend;

    public static PagosFragment getInstance(TypeDebt typeDebt, int i2) {
        PagosFragment pagosFragment = new PagosFragment();
        pagosFragment.presenterPayment = PresenterPaymentFactory.getPaymentPresenter(typeDebt, pagosFragment);
        pagosFragment.af = i2;
        return pagosFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.presenterPayment != null) {
            this.presenterPayment.setAnalyticManager(this.mAnalitycsManager);
            this.ad = this.presenterPayment.getPaymentAdapter();
            this.ae = ProgresIndicator.newInstance(VCndDeuda.nameService);
        }
        setErrorListener(B());
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.presenterPayment != null) {
            this.mMainView = layoutInflater.inflate(this.af, viewGroup, false);
            ButterKnife.inject((Object) this, this.mMainView);
            this.presenterPayment.onUpdateView();
            setAdapterToListPaymentServices(this.ad);
        }
        return this.mMainView;
    }

    public void onResume() {
        super.onResume();
        this.presenterPayment.onResumeEvent();
        if (this.ao) {
            checkReadPhoneStatePermission();
        }
    }

    public void setTitlePage(View view, String str) {
        TextView textView = (TextView) view.findViewById(R.id.vTitle);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public Context getActContext() {
        return getActivity();
    }

    public IDataManager getDataManager() {
        return this.mDataManager;
    }

    @Subscribe
    public void onGetCnsDeuda(CnsDeudaRecargaEvent cnsDeudaRecargaEvent) {
        closeDialogLoading();
        if (cnsDeudaRecargaEvent.getResult() == TypeResult.OK) {
            this.viewContainer.setVisibility(0);
        }
        this.presenterPayment.responseCnsDeudaRecargaEvent(cnsDeudaRecargaEvent);
    }

    @Subscribe
    public void onGetPagoServicios(RecargaCelularesEvent recargaCelularesEvent) {
        closeDialogLoading();
        this.presenterPayment.responsePagoService(recargaCelularesEvent);
    }

    public BasePaymentAdapter getPaymentServicesAdapter() {
        return this.ad;
    }

    public void setAdapterToListPaymentServices(BasePaymentAdapter basePaymentAdapter) {
        if (this.mListPaymentServices != null) {
            this.mListPaymentServices.setAdapter(basePaymentAdapter.getAdapter());
        }
    }

    public void addViewFormPreparePayment(View view) {
        if (this.vgWrapperFormPreparePayment != null) {
            this.vgWrapperFormPreparePayment.addView(view);
        }
    }

    public void addViewFormConfirmPayment(View view) {
        if (this.vgWrapperTableConfirmPayment != null) {
            this.vgWrapperTableConfirmPayment.addView(view);
        }
    }

    public ViewGroup getViewFormPreparePayment() {
        return this.vgWrapperFormPreparePayment;
    }

    public void cleanFormPreparePayment() {
        if (this.vgWrapperFormPreparePayment != null) {
            this.vgWrapperFormPreparePayment.removeAllViews();
        }
    }

    public void cleanViewTableConfirmPayment() {
        if (this.vgWrapperTableConfirmPayment != null) {
            this.vgWrapperTableConfirmPayment.removeAllViews();
        }
    }

    public void cleanViewReceiptPayment() {
        if (this.vgWrapperReceiptTopPayment != null) {
            this.vgWrapperReceiptTopPayment.removeAllViews();
        }
        if (this.vgWrapperReceiptBottomPayment != null) {
            this.vgWrapperReceiptBottomPayment.removeAllViews();
        }
    }

    public String getSelectorAmountValue() {
        View viewSelectorAmount = getViewSelectorAmount();
        if (viewSelectorAmount != null) {
            return ((TextView) viewSelectorAmount).getText().toString();
        }
        return null;
    }

    public void hideViewLegend() {
        if (this.wrapperLegend != null) {
            this.wrapperLegend.setVisibility(8);
        }
    }

    public void showViewLegend() {
        if (this.wrapperLegend != null) {
            this.wrapperLegend.setVisibility(0);
        }
    }

    public void showLink(LinkSeguroBean linkSeguroBean, CuentaDebitoBean cuentaDebitoBean, final String str) {
        if (linkSeguroBean != null && linkSeguroBean.getOpcion() != null && !linkSeguroBean.getOpcion().equals("NM")) {
            this.ag = cuentaDebitoBean;
            this.layoutLink.setVisibility(0);
            this.descripcionLeyenda.setText(Html.fromHtml(linkSeguroBean.getResDesc()));
            this.linkSeguro.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PagosFragment.this.am) {
                        ((SantanderRioMainActivity) PagosFragment.this.getActivity()).showProgress("Servicio");
                        PagosFragment.this.mDataManager.getSeguros();
                    } else if (ContextCompat.checkSelfPermission(PagosFragment.this.getActContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        PagosFragment.this.showRequestPermissionExplation(1);
                    } else {
                        PagosFragment.this.b(str);
                    }
                }
            });
        }
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.write_external_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onPositiveButton() {
                PagosFragment.this.b("");
            }

            public void onNegativeButton() {
                PagosFragment.this.am = true;
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).showProgress("Servicio");
                PagosFragment.this.mDataManager.getSeguros();
            }

            public void onSimpleActionButton() {
                PagosFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, i2);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        if (!this.am) {
            if (this.an == null) {
                c(str);
            }
            this.am = true;
            this.an.showAlert();
        }
    }

    private void c(String str) {
        configActionShareReceiptClickLinkSeguros(getString(R.string.ID385_CELULAR_PROOF_LBL_TITLE), getString(R.string.ID385_CELULAR_PROOF_LBL_TITLE).concat("-").concat(str));
    }

    private void d(String str) {
        configActionShareReceipt(getString(R.string.ID385_CELULAR_PROOF_LBL_TITLE), getString(R.string.ID385_CELULAR_PROOF_LBL_TITLE).concat("-").concat(str));
    }

    public void configActionShareReceipt(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass3 r0 = new OptionsToShareImpl(this, getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public View getViewToShare() {
                return PagosFragment.this.svPageReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                PagosFragment.this.startActivityForResult(Intent.createChooser(intent, PagosFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void onOption2Button() {
                super.onOption2Button();
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).goToOption(FragmentConstants.RECARGA_CELULARES);
            }

            public void onOption1Button() {
                if (ContextCompat.checkSelfPermission(PagosFragment.this.getActContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    PagosFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    return;
                }
                super.onOption1Button();
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).goToOption(FragmentConstants.RECARGA_CELULARES);
            }

            public void onOption3Button() {
                if (ContextCompat.checkSelfPermission(PagosFragment.this.getActContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    PagosFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    return;
                }
                super.onOption3Button();
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).goToOption(FragmentConstants.RECARGA_CELULARES);
            }
        };
        this.an = r0;
    }

    public void configActionShareReceiptClickLinkSeguros(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass4 r0 = new OptionsToShareImpl(this, getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public View getViewToShare() {
                return PagosFragment.this.svPageReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                PagosFragment.this.startActivityForResult(Intent.createChooser(intent, PagosFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void onOption2Button() {
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).showProgress("Servicio");
                PagosFragment.this.mDataManager.getSeguros();
            }

            public void onOption1Button() {
                if (ContextCompat.checkSelfPermission(PagosFragment.this.getActContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                    return;
                }
                super.onOption1Button();
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).showProgress("Servicio");
                PagosFragment.this.mDataManager.getSeguros();
            }

            public void onOption3Button() {
                if (ContextCompat.checkSelfPermission(PagosFragment.this.getActContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    showRequestPermission(1);
                    return;
                }
                super.onOption3Button();
                ((SantanderRioMainActivity) PagosFragment.this.getActivity()).showProgress("Servicio");
                PagosFragment.this.mDataManager.getSeguros();
            }
        };
        this.an = r0;
    }

    @Subscribe
    public void getCotizacionSeguroAccidente(GetCotizacionSeguroAccidenteEvent getCotizacionSeguroAccidenteEvent) {
        dismissProgress();
        AnonymousClass5 r0 = new BaseWSResponseHandler(getActContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getActivity(), getActContext().getString(R.string.ID155_CREDITS_VIEW_LBL_RECEIPT)) {
            /* access modifiers changed from: protected */
            public void onOk() {
            }
        };
        r0.handleWSResponse(getCotizacionSeguroAccidenteEvent);
    }

    @Subscribe
    public void onGetSeguros(GetSegurosEvent getSegurosEvent) {
        ((SantanderRioMainActivity) getActivity()).dismissProgress();
        dismissProgress();
        final GetSegurosEvent getSegurosEvent2 = getSegurosEvent;
        AnonymousClass6 r1 = new BaseWSResponseHandler(getActContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGUROS, this, (BaseActivity) getActContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PagosFragment.this.a(getSegurosEvent2);
            }
        };
        r1.handleWSResponse(getSegurosEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetSegurosEvent getSegurosEvent) {
        try {
            SegurosBean seguros = getSegurosEvent.getResponse().getSegurosBodyResponseBean.getSeguros();
            this.ak = SegurosFragment.getTipoAltaSeguroMovil(getSegurosEvent.getResponse().getSegurosBodyResponseBean.getSeguros().getLinkSeguroBean());
            a(seguros);
            verifyBatery();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void verifyBatery() {
        startActivityForResult(new Intent(getActContext(), TestBatteryActivity.class), 6);
    }

    private void y() {
        if (this.ak.equalsIgnoreCase(TipoAlta.ALTA_DISPOSITIVO)) {
            obtenerCotizacionSeguroMovil();
        } else if (this.ak.equals(TipoAlta.DISPOSITIVO_ASEGURADO) || this.ak.equals(TipoAlta.SEGURO_CARTERA)) {
            gotoSeguroMovil(this.ak, this.al, new CotizacionBean(), getActContext());
        } else {
            goToContratarSeguroMovilSinCotizar();
        }
    }

    public void checkReadPhoneStatePermission() {
        if (ContextCompat.checkSelfPermission(getActContext(), "android.permission.READ_PHONE_STATE") != 0) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_PHONE_STATE"}, 99);
            return;
        }
        y();
    }

    public void obtenerCotizacionSeguroMovil() {
        ((SantanderRioMainActivity) getActivity()).showProgress("Servicio");
        this.mDataManager.getCotizacionSeguroMovil();
    }

    @Subscribe
    public void onGetCotizacionSeguroMovil(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((SantanderRioMainActivity) getActivity()).dismissProgress();
        final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent2 = getCotizacionSeguroMovilEvent;
        AnonymousClass7 r1 = new BaseWSResponseHandler(getActContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, this, (BaseActivity) getActContext()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PagosFragment.this.a(getCotizacionSeguroMovilEvent2);
            }
        };
        r1.handleWSResponse(getCotizacionSeguroMovilEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        gotoSeguroMovil(this.ak, this.al, getCotizacionSeguroMovilEvent.getResponse().getGetCotizacionSeguroMovilBodyResponseBean().getCotizacion(), getActContext());
    }

    public void goToContratarSeguroMovilSinCotizar() {
        Intent intent = new Intent(getActContext(), SeguroMovilActivity.class);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, this.ak);
        if (this.ak.equalsIgnoreCase(TipoAlta.CAMBIO_DISPOSITIVO) || this.ak.equalsIgnoreCase(TipoAlta.ALTA_O_CAMBIO_DISPOSITIVO) || this.ak.equalsIgnoreCase(TipoAlta.DISPOSITIVO_ASEGURADO) || (this.ak.equalsIgnoreCase(TipoAlta.SEGURO_CARTERA) && this.al != null && !this.al.getListaSeguros().isEmpty())) {
            intent.putExtra(SegurosConstants.INTENT_LISTA_SEGUROS, this.al);
        }
        this.ah = this.ak.equalsIgnoreCase(TipoAlta.SEGURO_CARTERA);
        intent.putExtra(SegurosConstants.INTENT_SEGUROS_CARTERA, this.ah);
        startActivityForResult(intent, 2);
    }

    private SegurosBean a(SegurosBean segurosBean) {
        SegurosBean segurosBean2 = new SegurosBean();
        ArrayList arrayList = new ArrayList();
        for (SeguroBean seguroBean : segurosBean.getListaSeguros()) {
            if (seguroBean.getCodRamo().equals(CodRamo.SEGURO_MOVIL)) {
                arrayList.add(seguroBean);
            }
        }
        segurosBean2.setListaSeguros(arrayList);
        if (!arrayList.isEmpty()) {
            this.ai = true;
            this.al.setListaSeguros(arrayList);
        }
        return segurosBean2;
    }

    public void gotoSeguroMovil(String str, SegurosBean segurosBean, CotizacionBean cotizacionBean, Context context) {
        Intent intent = new Intent(context, SeguroMovilActivity.class);
        intent.putExtra(SegurosConstants.INTENT_COTIZACION, cotizacionBean);
        intent.putExtra(SegurosConstants.INTENT_TIPO_ALTA_MOVIL, str);
        if (str.equals(TipoAlta.CAMBIO_DISPOSITIVO) || str.equals(TipoAlta.DISPOSITIVO_ASEGURADO) || str.equals(TipoAlta.SEGURO_CARTERA)) {
            intent.putExtra(SegurosConstants.INTENT_LISTA_SEGUROS, segurosBean);
        }
        startActivityForResult(intent, 2);
    }

    public View getvPageTablePaymentServices() {
        return this.vPageTablePaymentServices;
    }

    public View getvPagePreparePaymentServices() {
        return this.vPagePreparePaymentServices;
    }

    public View getvPageConfirmPaymentServices() {
        return this.vPageConfirmPaymentServices;
    }

    public View getvPageReceiptPaymentServices() {
        return this.vPageReceiptPaymentServices;
    }

    public ViewGroup getVgWrapperReceiptBottomPayment() {
        return this.vgWrapperReceiptBottomPayment;
    }

    public ViewGroup getVgWrapperReceiptTopPayment() {
        return this.vgWrapperReceiptTopPayment;
    }

    public void addViewReceiptTop(View view) {
        if (this.vgWrapperReceiptTopPayment != null) {
            this.vgWrapperReceiptTopPayment.addView(view);
        }
    }

    public void addViewReceiptBottom(View view) {
        if (this.vgWrapperReceiptBottomPayment != null) {
            this.vgWrapperReceiptBottomPayment.addView(view);
        }
    }

    @OnClick({2131364199})
    public void onClickPreparePaymentService() {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        this.presenterPayment.onBtnPrepareClicked();
    }

    @OnClick({2131364189})
    public void onClickButtonConfirmPayment() {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        this.presenterPayment.btnConfirmClicked();
    }

    @OnClick({2131364200})
    public void onClickReceiptPayment() {
        if (!this.am) {
            e("");
        } else {
            this.presenterPayment.onReceiptPaymentClicked();
        }
    }

    public void setListenerClickSelectorAccount() {
        if (getvPagePreparePaymentServices() != null) {
            View findViewById = getvPagePreparePaymentServices().findViewById(R.id.row_selector_account_id);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
            }
        }
    }

    public void setListenerClickSelectorAmount() {
        if (getvPagePreparePaymentServices() != null) {
            View findViewById = getvPagePreparePaymentServices().findViewById(R.id.row_selector_amount_id);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
                View findViewById2 = findViewById.findViewById(R.id.input_amount_id);
                if (findViewById2 != null) {
                    findViewById2.setOnClickListener(this);
                }
            }
        }
    }

    public View getViewSelectorAccount() {
        return this.mMainView.findViewById(R.id.selector_account_id);
    }

    public View getViewSelectorAmount() {
        return this.mMainView.findViewById(R.id.input_amount_id);
    }

    public void setLabelSelectorAccount(String str) {
        TextView textView = (TextView) getViewSelectorAccount();
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setLabelSelectorAmount(String str) {
        TextView textView = (TextView) getViewSelectorAmount();
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void restoreSelectorAccount(String str) {
        TextView textView = (TextView) getViewSelectorAccount();
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void restoreSelectorAmount(String str) {
        TextView textView = (TextView) getViewSelectorAmount();
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void gotoPage(int i2) {
        if (this.mControlPager != null) {
            this.mControlPager.setDisplayedChild(i2);
        }
    }

    public void setNextPageAnimation() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
        }
    }

    public void setPreviusPageAnimation() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
        }
    }

    public void reloadFragment(TypeDebt typeDebt) {
        restartMainActionBar();
        ((SantanderRioMainActivity) getActivity()).changeFragment(getInstance(typeDebt, this.af));
    }

    public void errorEmptyAccount() {
        a("error_empty_account", getString(R.string.IDX_ALERT_LBL_TITLE_ERROR), getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    public int getCurrentIndexViewPage() {
        if (this.mControlPager != null) {
            return this.mControlPager.getDisplayedChild();
        }
        return -1;
    }

    public View getCurrentViewPageShow() {
        return this.mControlPager.getCurrentView();
    }

    public View getViewPageFromIndex(int i2) {
        return this.mControlPager.getChildAt(i2);
    }

    public boolean isVisibleAmountEditable() {
        return (this.vPagePreparePaymentServices == null || this.vPagePreparePaymentServices.findViewById(R.id.input_amount_id) == null) ? false : true;
    }

    public void onBackPressed() {
        int currentIndexViewPage = getCurrentIndexViewPage();
        if (currentIndexViewPage == 0) {
            switchDrawer();
            ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
            View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
            if (customView != null) {
                customView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PagosFragment.this.switchDrawer();
                    }
                });
            }
        } else if (currentIndexViewPage != 3) {
            this.presenterPayment.backPagePressed(getCurrentIndexViewPage());
        } else if (!this.am) {
            e("");
        } else {
            switchDrawer();
        }
    }

    public void previousPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            this.mControlPager.showPrevious();
        }
    }

    /* access modifiers changed from: private */
    public void e(String str) {
        if (!this.am) {
            if (this.an == null) {
                d(str);
            }
            this.am = true;
            this.an.showAlert();
        }
    }

    public void onShowDialogConfirmPayment(String str, String str2) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("confirm_payment", str, str2, null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListenerExtended(this);
        newInstance.show(supportFragmentManager, "DialogConfirm");
    }

    public void onShowDialogError(String str, String str2) {
        a("error_validate", str, str2);
    }

    private void a(String str, String str2, String str3) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, str3, null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListenerExtended(this);
        newInstance.show(supportFragmentManager, "DialogError");
    }

    public void showDialogSelectorAccount(ArrayList<String> arrayList, String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_account", getString(R.string.ID94_ACCOUNTS_CHANGEACC_LBL_SELECT), null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, str);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), "DialogSelectorAccount");
    }

    public void showDialogSelector(int i2, ArrayList<String> arrayList, String str, String str2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(new Integer(i2).toString(), str2, null, arrayList, getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, str);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), TenenciaCreditosFragment.DIALOG_SELECTOR);
    }

    public SessionManager getSessionManager() {
        return this.mSessionManager;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.row_selector_account_id) {
            this.presenterPayment.onSelectorAccountClicked();
        } else if (view.getId() == R.id.row_selector_amount_id || view.getId() == R.id.input_amount_id) {
            this.presenterPayment.onSelectorAmountClicked();
        }
    }

    public ViewGroup getRowSelectorAmount() {
        return (ViewGroup) this.mMainView.findViewById(R.id.row_selector_amount_id);
    }

    public void onItemSelected(String str, String str2) {
        if ("selector_account".equals(str2)) {
            this.presenterPayment.onAccountSelected(str);
        } else if (R.id.input_amount_id == Integer.parseInt(str2)) {
            this.presenterPayment.onAmountSelected(str);
        }
    }

    public void onPositiveButton(String str) {
        if ("confirm_payment".equals(str)) {
            this.presenterPayment.onBtnPossitiveClicked(this.mDataManager);
        }
    }

    public void onNegativeButton(String str) {
        if ("confirm_payment".equals(str)) {
            this.presenterPayment.onBtnNegativeConfirmPaymentClicked();
        }
    }

    public void onSimpleActionButton(String str) {
        if ("error_empty_account".equals(str)) {
            openMenu();
        }
    }

    public void setTextLegendTop(String str) {
        if (this.tvLegendTop != null) {
            this.tvLegendTop.setText(str);
        }
    }

    public void setTextLegendBottom(String str) {
        if (this.tvLegendBottom != null) {
            this.tvLegendBottom.setText(str);
        }
    }

    public void showDialogLoading() {
        if (this.ae != null && !this.ae.isVisible()) {
            this.ae.show(getActivity().getSupportFragmentManager(), "loading");
        }
    }

    public void closeDialogLoading() {
        if (this.ae != null) {
            this.ae.dismiss();
        }
    }

    public void setFocusAndShowKeyboard(EditText editText) {
        editText.requestFocus();
        ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(editText, 1);
    }

    public void setSelectorValue(String str) {
        TextView textView = (TextView) getViewSelectorAmount();
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setLabelButtonConfirm(int i2) {
        if (this.btnConfirmPayment != null) {
            this.btnConfirmPayment.setText(i2);
        }
    }

    public void onTopPagePrepare() {
        a(this.svPagePrepare);
    }

    public void onTopPageConfirm() {
        a(this.svPageConfirm);
    }

    public void onTopPageReceipt() {
        a(this.svPageReceipt);
    }

    private void a(final ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(0, 0);
                }
            });
        }
    }

    public void onDispatchEventsError(WebServiceEvent webServiceEvent) {
        if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(webServiceEvent, getTAG());
        }
    }

    public void loadBarReturn() {
        B().lockMenu(true);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        enableBackButton();
    }

    public void loadBarShare() {
        B().loadActionBarShare(1, this);
        B().lockMenu(false);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.SHARE);
    }

    public void enableBackButton() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.findViewById(R.id.toggle).setOnClickListener(new OneClickListener(new OneClicked() {
                public void onClicked(View view) {
                    PagosFragment.this.onBackPressed();
                }
            }));
        }
    }

    public void restartMainActionBar() {
        B().restartActionBar();
        B().lockMenu(false);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            customView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PagosFragment.this.switchDrawer();
                }
            });
        }
    }

    private void z() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.h = customView.findViewById(R.id.toggle);
            if (this.h != null) {
                this.h.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PagosFragment.this.onBackPressed();
                    }
                });
            }
        }
    }

    private void A() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.SHARE);
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        if (customView != null) {
            this.i = customView.findViewById(R.id.share);
            if (this.i != null) {
                this.i.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PagosFragment.this.presenterPayment.onShareReceipt();
                    }
                });
            }
            View findViewById = customView.findViewById(R.id.toggle);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (!PagosFragment.this.am) {
                            PagosFragment.this.e("");
                        } else {
                            PagosFragment.this.switchDrawer();
                        }
                    }
                });
            }
        }
    }

    private SantanderRioMainActivity B() {
        return (SantanderRioMainActivity) getActivity();
    }

    public void onPause() {
        super.onPause();
        this.ao = false;
        this.presenterPayment.onPauseEvent();
    }

    public void actionBarLoaded(int i2, View view) {
        if (i2 == 0) {
            z();
        } else if (i2 == 1) {
            A();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public String getSelectorAccount() {
        TextView textView = (TextView) getViewSelectorAccount();
        return textView != null ? textView.getText().toString() : "";
    }

    public void showActionShareReceipt(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass15 r0 = new OptionsToShareImpl(this, getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public View getViewToShare() {
                return PagosFragment.this.svPageReceipt;
            }

            public void receiveIntentAppShare(Intent intent) {
                PagosFragment.this.startActivityForResult(Intent.createChooser(intent, PagosFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
            }

            public void optionShareSelected() {
                super.optionShareSelected();
            }

            public void optionCancelSelected() {
                super.optionCancelSelected();
            }
        };
        r0.show(getActContext().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL));
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        this.an.onRequestPermissionsResult(i2, strArr, iArr);
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 999) {
            UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
        }
        if (i2 != 6) {
            super.onActivityResult(i2, i3, intent);
        } else if (i3 == -1 && intent != null) {
            String stringExtra = intent.getStringExtra(ResultsKeys.BATTERY_RESULT_VALUE);
            if (stringExtra.equals(ResultValues.OK)) {
                this.ao = true;
            } else if (stringExtra.equals(ResultValues.FAIL)) {
                this.ak = TipoAlta.NO_ASEGURABLE;
            } else if (i3 == 0 && intent != null && stringExtra.equals(ResultValues.ERROR)) {
                this.ak = TipoAlta.NO_ASEGURABLE;
            }
        }
    }
}
