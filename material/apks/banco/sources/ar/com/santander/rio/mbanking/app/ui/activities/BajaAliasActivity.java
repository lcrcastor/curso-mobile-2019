package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.bajaAlias.BajaAliasComprobantePresenter;
import ar.com.santander.rio.mbanking.app.module.bajaAlias.BajaAliasComprobanteView;
import ar.com.santander.rio.mbanking.app.module.bajaAlias.BajaAliasConfirmacionPresenter;
import ar.com.santander.rio.mbanking.app.module.bajaAlias.BajaAliasConfirmacionView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.CuentasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils.Documento;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;

public class BajaAliasActivity extends MvpPrivateMenuActivity implements OnClickListener, BajaAliasComprobanteView, BajaAliasConfirmacionView {
    @InjectView(2131363685)
    Button btnContinuar;
    @InjectView(2131363684)
    Button btnVolver;
    @InjectView(2131365640)
    ScrollView comprobanteOperacion;
    @InjectView(2131363686)
    TextView lblComprobanteAlias;
    @InjectView(2131363687)
    TextView lblComprobanteCBU;
    @InjectView(2131363688)
    TextView lblComprobanteDocumento;
    @InjectView(2131363689)
    TextView lblComprobanteFechaEjecucion;
    @InjectView(2131363690)
    TextView lblComprobanteNroCuenta;
    @InjectView(2131363691)
    TextView lblComprobanteNroOperacion;
    @InjectView(2131363692)
    TextView lblComprobanteTipoCuenta;
    @InjectView(2131363693)
    TextView lblComprobanteTitular;
    @InjectView(2131363695)
    TextView lblDataAlias;
    @InjectView(2131363696)
    TextView lblDataCBU;
    @InjectView(2131363697)
    TextView lblDataCUIT;
    @InjectView(2131363698)
    TextView lblDataNumeroCuenta;
    @InjectView(2131363699)
    TextView lblDataTipoCuenta;
    @InjectView(2131363700)
    TextView lblDataTitular;
    @InjectView(2131364174)
    ViewFlipper mControlPager;
    public String numeroComprobante = "";
    @Inject
    AnalyticsManager p;
    /* access modifiers changed from: private */
    public BajaAliasConfirmacionPresenter q;
    private BajaAliasComprobantePresenter r;
    /* access modifiers changed from: private */
    public Cuenta s = null;
    /* access modifiers changed from: private */
    public OptionsToShare t;
    /* access modifiers changed from: private */
    public IsbanDialogFragment u;
    /* access modifiers changed from: private */
    public boolean v = false;

    public void clearScreenData() {
    }

    public void configureLayout() {
    }

    public int getMainLayout() {
        return R.layout.activity_baja_alias;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ButterKnife.inject((Activity) this);
        configureActionBar();
        initialize();
    }

    public void initialize() {
        this.s = (Cuenta) getIntent().getParcelableExtra(CuentasConstants.cINTENT_EXTRA_ALIAS_CUENTA);
        if (this.s.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS) || this.s.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
            this.s.setTipo(LoginConstants.TIPO_CUENTA_CU);
        }
        this.q = new BajaAliasConfirmacionPresenter(this.mBus, this.mDataManager, this);
        this.r = new BajaAliasComprobantePresenter(this.mBus);
        goToConfirmacionBajaAlias();
    }

    public void detachView() {
        if (this.r.isViewAttached()) {
            this.r.detachView();
        }
        if (this.q.isViewAttached()) {
            this.q.detachView();
        }
    }

    public void attachView() {
        switch (this.mControlPager.getDisplayedChild()) {
            case 0:
                if (!this.q.isViewAttached()) {
                    this.q.attachView(this);
                    return;
                }
                return;
            case 1:
                if (!this.r.isViewAttached()) {
                    this.r.attachView(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(true);
        ((ImageView) this.mActionBar.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BajaAliasActivity.this.onBackPressed();
            }
        });
    }

    public boolean canExit(int i) {
        if (!this.v) {
            final int i2 = i;
            AnonymousClass2 r1 = new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
                public String getSubjectReceiptToShare() {
                    return "Comprobante de Eliminación de Alias";
                }

                public View getViewToShare() {
                    return BajaAliasActivity.this.comprobanteOperacion;
                }

                public void receiveIntentAppShare(Intent intent) {
                    BajaAliasActivity.this.startActivityForResult(Intent.createChooser(intent, BajaAliasActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
                }

                public String getFileName() {
                    return "Comprobante de Eliminación de Alias - ".concat(BajaAliasActivity.this.numeroComprobante);
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    BajaAliasActivity.this.v = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    BajaAliasActivity.this.v = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    BajaAliasActivity.this.v = true;
                    BajaAliasActivity.this.onClickItem(i2);
                }
            };
            r1.showAlert();
        }
        return this.v;
    }

    public void gotoPage(int i) {
        gotoPage(i, true);
    }

    public void gotoPage(int i, boolean z) {
        if (this.mControlPager != null) {
            detachView();
            switch (i) {
                case 0:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.noAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.noAnimation());
                    hideKeyboard();
                    break;
                case 1:
                    this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                    this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                    break;
            }
            this.mControlPager.setDisplayedChild(i);
            attachView();
        }
    }

    public void goToConfirmacionBajaAlias() {
        gotoPage(0);
        this.q.onCreatePage();
    }

    public void setBajaAliasConfirmacionView() {
        this.p.trackScreen(getString(R.string.analytics_screen_baja_alias_confirmacion));
        this.lblDataAlias.setText(Html.fromHtml(this.s.getAlias()));
        this.lblDataCBU.setText(this.s.getClaveBancariaUnificada());
        this.lblDataNumeroCuenta.setText(UtilAccount.getAccountFormat(this.s.getNroSuc(), this.s.getNumero()));
        this.lblDataTipoCuenta.setText(UtilAccount.getAccountTypeDescription(this.sessionManager, this.s.getNumero(), this.s.getTipo()));
        this.btnContinuar.setOnClickListener(this);
        try {
            TextView textView = this.lblDataTitular;
            StringBuilder sb = new StringBuilder();
            sb.append(Html.fromHtml(this.sessionManager.getLoginUnico().getDatosPersonales().getNombre()).toString());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(Html.fromHtml(this.sessionManager.getLoginUnico().getDatosPersonales().getApellido()).toString());
            textView.setText(sb.toString());
            this.lblDataCUIT.setText(Documento.format(this.sessionManager.getLoginUnico().getDatosPersonales().getTipoDocumento(), this.sessionManager.getLoginUnico().getDatosPersonales().getNroDocumento()));
        } catch (Exception unused) {
            this.lblDataTitular.setText("");
            this.lblDataCUIT.setText("");
        }
        try {
            this.lblDataAlias.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.lblDataAlias.getText().toString()));
            this.lblDataCBU.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lblDataCBU.getText().toString()));
            this.lblDataNumeroCuenta.setContentDescription(new CAccessibility(this).applyFilterAccount(this.lblDataNumeroCuenta.getText().toString()));
            this.lblDataTipoCuenta.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.lblDataTipoCuenta.getText().toString()));
            this.lblDataTitular.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.lblDataTitular.getText().toString()));
            this.lblDataCUIT.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lblDataCUIT.getText().toString()));
        } catch (Exception unused2) {
        }
    }

    public void goToComprobanteBajaAlias(String str, String str2, String str3) {
        gotoPage(1);
        this.r.onCreatePage(str, str2, str3);
    }

    public void setBajaAliasComprobanteView(String str, String str2, String str3) {
        this.p.trackTransaction(getString(R.string.analytics_transaction_hits_baja_alias), str);
        this.p.trackScreen(getString(R.string.analytics_screen_baja_alias_comprobante));
        b();
        this.numeroComprobante = str;
        this.lblComprobanteAlias.setText(Html.fromHtml(this.s.getAlias()));
        this.lblComprobanteCBU.setText(this.s.getClaveBancariaUnificada());
        this.lblComprobanteNroCuenta.setText(UtilAccount.getAccountFormat(this.s.getNroSuc(), this.s.getNumero()));
        this.lblComprobanteTipoCuenta.setText(UtilAccount.getAccountTypeDescription(this.sessionManager, this.s.getNumero(), this.s.getTipo()));
        try {
            TextView textView = this.lblComprobanteTitular;
            StringBuilder sb = new StringBuilder();
            sb.append(Html.fromHtml(this.sessionManager.getLoginUnico().getDatosPersonales().getNombre()).toString());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(Html.fromHtml(this.sessionManager.getLoginUnico().getDatosPersonales().getApellido()).toString());
            textView.setText(sb.toString());
            this.lblComprobanteDocumento.setText(CUIT.format(str3));
        } catch (Exception unused) {
            this.lblComprobanteTitular.setText("");
            this.lblComprobanteDocumento.setText("");
        }
        this.lblComprobanteNroOperacion.setText(str);
        this.lblComprobanteFechaEjecucion.setText(UtilDate.getDateFormat(str2, Constants.FORMAT_DATE_WS_1, Constants.FORMAT_DATE_APP_2));
        this.btnVolver.setOnClickListener(this);
        try {
            this.lblComprobanteAlias.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.lblComprobanteAlias.getText().toString()));
            this.lblComprobanteCBU.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lblComprobanteCBU.getText().toString()));
            this.lblComprobanteNroCuenta.setContentDescription(new CAccessibility(this).applyFilterAccount(this.lblComprobanteNroCuenta.getText().toString()));
            this.lblComprobanteTipoCuenta.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.lblComprobanteTipoCuenta.getText().toString()));
            this.lblComprobanteTitular.setContentDescription(new CAccessibility(this).applyFilterGeneral(this.lblComprobanteTitular.getText().toString()));
            this.lblComprobanteDocumento.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lblComprobanteDocumento.getText().toString()));
            this.lblComprobanteNroOperacion.setContentDescription(new CAccessibility(this).applyFilterCharacterToCharacter(this.lblComprobanteNroOperacion.getText().toString()));
            this.lblComprobanteFechaEjecucion.setContentDescription(new CAccessibility(this).applyFilterDate(this.lblComprobanteFechaEjecucion.getText().toString()));
        } catch (Exception unused2) {
        }
    }

    public void onBackPressed() {
        switch (this.mControlPager.getCurrentView().getId()) {
            case R.id.layout_baja_alias_comprobante /*2131364901*/:
                if (!this.v) {
                    this.t.showAlert();
                    return;
                }
                setResult(-1);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.layout_baja_alias_confirmacion /*2131364902*/:
                setResult(0);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            default:
                return;
        }
    }

    private void b() {
        setActionBarType(ActionBarType.MAIN_WITH_MENU);
        this.mActionBar = getSupportActionBar().getCustomView();
        lockMenu(false);
        ((ImageView) this.mActionBar.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BajaAliasActivity.this.switchDrawer();
            }
        });
        enableShareButton();
    }

    public void enableShareButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.menu);
        if (findViewById != null) {
            findViewById.setContentDescription(getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
            this.t = d();
            c();
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BajaAliasActivity.this.u.show(BajaAliasActivity.this.getSupportFragmentManager(), "Dialog");
                }
            });
        }
    }

    private void c() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        this.u = IsbanDialogFragment.newInstance("mPopupMenu", getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, getString(R.string.IDXX_SHARE_OPTION_RECEIPT_CANCEL), null, null, null, arrayList);
        this.u.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(BajaAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    BajaAliasActivity.this.t.optionShareSelected();
                } else if (str.equalsIgnoreCase(BajaAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    BajaAliasActivity.this.t.optionDownloadSelected();
                } else if (str.equalsIgnoreCase(BajaAliasActivity.this.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DONT_DOWNLOAD))) {
                    BajaAliasActivity.this.onBackPressed();
                }
            }
        });
        this.u.setCancelable(true);
    }

    private OptionsToShare d() {
        return new OptionsToShareImpl(this, getApplicationContext(), getSupportFragmentManager()) {
            public String getSubjectReceiptToShare() {
                return "Comprobante de Eliminación de Alias";
            }

            public View getViewToShare() {
                return BajaAliasActivity.this.comprobanteOperacion;
            }

            public void receiveIntentAppShare(Intent intent) {
                BajaAliasActivity.this.startActivityForResult(Intent.createChooser(intent, BajaAliasActivity.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 99);
            }

            public String getFileName() {
                return "Comprobante de Eliminación de Alias - ".concat(BajaAliasActivity.this.numeroComprobante);
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
                BajaAliasActivity.this.v = true;
            }

            public void optionShareSelected() {
                super.optionShareSelected();
                BajaAliasActivity.this.v = true;
            }

            public void onAbortShare() {
                super.onAbortShare();
                BajaAliasActivity.this.v = true;
                BajaAliasActivity.this.onBackPressed();
            }
        };
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F28_01_BTN_COMPROBANTE_VOLVER /*2131363684*/:
                onBackPressed();
                return;
            case R.id.F28_01_BTN_CONTINUAR /*2131363685*/:
                e();
                return;
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.t.onRequestPermissionsResult(i, strArr, iArr);
    }

    private void e() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpConfirmacionABM", getString(R.string.ID4279_F28_01_CUENTAS_TEXT_BAJA_ALIAS_CONFIRMAR_POPUP_TITULO), getString(R.string.ID4280_F28_01_CUENTAS_TEXT_BAJA_ALIAS_CONFIRMAR_POPUP_MENSAJE), getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                String str = "";
                String str2 = "";
                if (!TextUtils.isEmpty(BajaAliasActivity.this.s.getNroSuc())) {
                    str = BajaAliasActivity.this.s.getNroSuc().substring(BajaAliasActivity.this.s.getNroSuc().length() - 3, BajaAliasActivity.this.s.getNroSuc().length());
                }
                if (!TextUtils.isEmpty(BajaAliasActivity.this.s.getNumero())) {
                    str2 = BajaAliasActivity.this.s.getNumero().substring(BajaAliasActivity.this.s.getNumero().length() - 7, BajaAliasActivity.this.s.getNumero().length());
                }
                BajaAliasActivity.this.q.onConfirmar("B", BajaAliasActivity.this.s.getAlias(), null, null, new CuentaShortBean(BajaAliasActivity.this.s.getTipo(), str, str2));
            }

            public void onNegativeButton() {
                BajaAliasActivity.this.p.trackEvent(BajaAliasActivity.this.getString(R.string.analytics_category_baja_alias), BajaAliasActivity.this.getString(R.string.analytics_event_action_baja_alias_confirmar_eliminacion), BajaAliasActivity.this.getString(R.string.analytics_event_baja_alias_confirmar_eliminacion_no));
            }
        });
        newInstance.show(getSupportFragmentManager(), "popUpConfirmacionABM");
    }
}
