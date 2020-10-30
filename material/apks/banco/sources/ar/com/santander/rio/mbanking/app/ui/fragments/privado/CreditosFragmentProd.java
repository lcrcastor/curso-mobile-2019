package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CCFTNALabelACC;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CCFTNA_IVALabelACC;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CTasaValueAcc;
import ar.com.santander.rio.mbanking.app.module.creditos.ComprobantePresenterProd;
import ar.com.santander.rio.mbanking.app.module.creditos.ComprobantePresenterProdImp;
import ar.com.santander.rio.mbanking.app.module.creditos.ConfirmacionPresenterProd;
import ar.com.santander.rio.mbanking.app.module.creditos.ConfirmacionPresenterProdImp;
import ar.com.santander.rio.mbanking.app.module.creditos.CreditosViewProd;
import ar.com.santander.rio.mbanking.app.module.creditos.InicioCreditoPresenterProd;
import ar.com.santander.rio.mbanking.app.module.creditos.InicioCreditoPresenterProdImp;
import ar.com.santander.rio.mbanking.app.module.creditos.SimulacionPresenterProd;
import ar.com.santander.rio.mbanking.app.module.creditos.SimulacionPresenterProdImp;
import ar.com.santander.rio.mbanking.app.module.creditos.model.InicioProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.ResultadoProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.SimulacionProd;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.CreditosAdapterProd;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListenerExtended;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaPrestamosPermitidosEventProd;
import ar.com.santander.rio.mbanking.services.events.ConsultaSolicitudCrediticiaEventProd;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosCuenta;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitidoProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamoProd;
import ar.com.santander.rio.mbanking.services.model.creditos.ListaCuentas;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Cuentas;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;
import ar.com.santander.rio.mbanking.view.tables.RowTwoColumnViewStyled;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class CreditosFragmentProd extends BaseFragment implements OnClickListener, OnItemClickListener, CreditosViewProd, IDialogListenerExtended {
    public static String CUOTAS_DIALOG = "cuotas_dialog";
    public static String DESTINO_DIALOG = "destino_dialog";
    public static String LIQUIDACION_WS = "L";
    public static String PRIMERA_CUOTA_DIALOG = "primera_cuota_dialog";
    public static String SIMULACION_WS = "S";
    @Inject
    IDataManager a;
    SimulacionProd ad;
    ResultadoProd ae;
    ResultadoProd af;
    SantanderRioMainActivity ag;
    private boolean ah = false;
    private OptionsToShare ai;
    private boolean aj = false;
    private List<Cuenta> ak;
    private Cuenta al;
    private SolicitudPrestamoPreacordadoBodyRequestBeanProd am;
    private String an = "";
    private ConsultaSolicitudCrediticiaBodyResponseBeanProd ao;
    private ConsultaPrestamosPermitidosBodyResponseBeanProd ap;
    private SolicitudPrestamoPreacordadoBodyResponseBeanProd aq;
    @Inject
    SessionManager b;
    @Inject
    AnalyticsManager c;
    @InjectView(2131364550)
    public Button confirmar;
    @InjectView(2131364551)
    public Button continuar;
    @InjectView(2131364752)
    public LinearLayout creditosView;
    ViewGroup d;
    InicioCreditoPresenterProd e;
    SimulacionPresenterProd f;
    ConfirmacionPresenterProd g;
    ComprobantePresenterProd h;
    InicioProd i;
    @InjectView(2131364552)
    public Button irCuentas;
    @InjectView(2131365106)
    public ListView listCreditos;
    @InjectView(2131366365)
    public ViewFlipper mControlPager;
    @InjectView(2131365300)
    View pantallaComprobante;
    @InjectView(2131365301)
    View pantallaConfirmacion;
    @InjectView(2131365304)
    View pantallaInicial;
    @InjectView(2131365305)
    View pantallaSimulacion;
    @InjectView(2131366399)
    public ViewGroup vgWrapperSimulacion;
    @InjectView(2131366404)
    View viewComprobanteCreditos;

    public void gotoPage(int i2) {
    }

    public void onNegativeButton(String str) {
    }

    public void onPositiveButton(String str) {
    }

    public void onSimpleActionButton(String str) {
    }

    public void setModalInPageAnimation() {
    }

    public void setModalOutPageAnimation() {
    }

    public void setNextPageAnimation() {
    }

    public void setPreviusPageAnimation() {
    }

    @Nullable
    public /* bridge */ /* synthetic */ Activity getActivity() {
        return super.getActivity();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = new InicioCreditoPresenterProdImp(this);
        this.f = new SimulacionPresenterProdImp(this);
        this.g = new ConfirmacionPresenterProdImp(this);
        this.h = new ComprobantePresenterProdImp(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.creditos_fragment_prod, viewGroup, false);
        Log.d("JOSMAR", "LLAMO AL SERVICIO PARA TRAER DATA DE LA SOLICITUD");
        ButterKnife.inject((Object) this, inflate);
        this.creditosView.setVisibility(4);
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_home));
        C();
        D();
        return inflate;
    }

    private void C() {
        if (this.b != null && this.b.getLoginUnico() != null && this.b.getLoginUnico().getProductos() != null && this.b.getLoginUnico().getProductos().getCuentas() != null) {
            Cuentas cuentas = this.b.getLoginUnico().getProductos().getCuentas();
            if (cuentas.getCuentas() != null && cuentas.getCuentas().size() > 0) {
                this.ak = cuentas.getCuentas();
            }
        }
    }

    private void D() {
        ConsultaSolicitudCrediticiaBodyRequestBeanProd consultaSolicitudCrediticiaBodyRequestBeanProd = new ConsultaSolicitudCrediticiaBodyRequestBeanProd();
        ArrayList arrayList = new ArrayList();
        if (this.ak != null && this.ak.size() > 0) {
            for (Cuenta cuenta : this.ak) {
                DatosCuenta datosCuenta = new DatosCuenta();
                datosCuenta.setNroCta(UtilAccount.formatNumeroCuenta(cuenta.getNumberAccount()));
                datosCuenta.setSucursalCta(UtilAccount.formatSucursalCuenta(cuenta.getNroSuc()));
                arrayList.add(datosCuenta);
            }
            ListaCuentas listaCuentas = new ListaCuentas();
            listaCuentas.setListaDatosCuenta(arrayList);
            consultaSolicitudCrediticiaBodyRequestBeanProd.setListaCuentas(listaCuentas);
            showProgress();
            this.a.consultaSolicitudCrediticiaProd(consultaSolicitudCrediticiaBodyRequestBeanProd);
        }
    }

    @Subscribe
    public void onConsultaSolicitudCrediticia(ConsultaSolicitudCrediticiaEventProd consultaSolicitudCrediticiaEventProd) {
        if (consultaSolicitudCrediticiaEventProd.getResult() == TypeResult.OK) {
            this.ao = ((ConsultaSolicitudCrediticiaResponseBeanProd) consultaSolicitudCrediticiaEventProd.getBeanResponse()).getConsultaSolicitudCrediticiaBodyResponseBeanProd();
            AccountResponseBean accountResponseBean = this.ao.getConsultaCalificacionCrediticiaProd().getAccountResponseBean();
            AccountRequestBean accountRequestBean = new AccountRequestBean();
            for (Cuenta cuenta : this.ak) {
                if (cuenta.getNroSuc().contains(accountResponseBean.sucursal) && cuenta.getNumero().contains(accountResponseBean.numero)) {
                    accountRequestBean.divisa = "0";
                    accountRequestBean.sucursalCta = cuenta.getNroSuc();
                    accountRequestBean.nroCta = accountResponseBean.numero;
                    accountRequestBean.sucursalPaq = cuenta.getSucursalPaq();
                    accountRequestBean.nroPaq = cuenta.getNroPaq();
                    this.al = cuenta;
                }
            }
            this.e.onCreatePage(consultaSolicitudCrediticiaEventProd, accountRequestBean);
            this.e.sendRequestConsultaPrestamosPermitidos(this.a, true);
            return;
        }
        dismisProgress();
        if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(consultaSolicitudCrediticiaEventProd, getTAG());
        }
    }

    @Subscribe
    public void onConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosEventProd consultaPrestamosPermitidosEventProd) {
        dismisProgress();
        if (consultaPrestamosPermitidosEventProd.getResult() == TypeResult.OK) {
            this.creditosView.setVisibility(0);
            this.ap = ((ConsultaPrestamosPermitidosResponseBeanProd) consultaPrestamosPermitidosEventProd.getBeanResponse()).consultaPrestamosPermitidosBodyResponseBeanProd;
            this.e.getResponseConsultaPrestamosPermitidos(consultaPrestamosPermitidosEventProd);
        } else if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(consultaPrestamosPermitidosEventProd, getTAG());
        }
    }

    @Subscribe
    public void onSolicitudPrestamoPreacordado(SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd) {
        dismisProgress();
        if (solicitudPrestamoPreacordadoEventProd.getResult() == TypeResult.OK) {
            this.aq = ((SolicitudPrestamoPreacordadoResponseBeanProd) solicitudPrestamoPreacordadoEventProd.getBeanResponse()).solicitudPrestamoPreacordadoBodyResponseBeanProd;
            if (this.aq.getDatosSolicitudPrestamoProd().getOpcion().equalsIgnoreCase(SIMULACION_WS)) {
                this.f.getResponseSimulacionPrestamoPermitido(solicitudPrestamoPreacordadoEventProd);
            } else {
                this.g.getResponseConfirmacionPrestamoPermitido(solicitudPrestamoPreacordadoEventProd);
            }
        } else if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(solicitudPrestamoPreacordadoEventProd, getTAG());
        }
    }

    public void onBackPressed() {
        if (this.mControlPager.getDisplayedChild() > 1) {
            if (this.mControlPager.getDisplayedChild() != 3) {
                previousPage();
            } else if (!this.aj) {
                F();
            }
        } else if (this.mControlPager.getDisplayedChild() == 1) {
            goToInicio();
        } else {
            super.onBackPressed();
        }
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_home));
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131364551})
    public void y() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_simulacion));
        this.f.validate(((EditText) this.vgWrapperSimulacion.findViewById(R.id.input_amount_id)).getText().toString());
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131364550})
    public void z() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.MSG_USER0000XX_CONFIRMACION), getString(R.string.MSG_USER000061), null, null, getString(R.string.IDX_ALERT_BTN_YES), getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                CreditosFragmentProd.this.c.trackScreen(CreditosFragmentProd.this.getString(R.string.analytics_screen_name_creditos_comprobante));
                CreditosFragmentProd.this.g.sendRequestConfirmacionPrestamoPermitido();
            }
        });
        newInstance.show(getFragmentManager(), "DialogConfirm");
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131364552})
    public void A() {
        if (!this.aj) {
            F();
        } else {
            ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.CUENTAS);
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131364519})
    public void B() {
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_terminos));
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, this.ae.getLeyendaConf());
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getResources().getString(R.string.ID171_CREDITS_VIEW_LBL_CREDITS));
        startActivity(intent);
    }

    public void onStop() {
        super.onStop();
    }

    public void setTitleLayout(String str) {
        TextView textView = (TextView) this.mControlPager.getCurrentView().findViewById(R.id.vTitle);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public Context getActContext() {
        return getActivity();
    }

    public void setInicioProdView(InicioProd inicioProd) {
        this.aj = false;
        this.i = inicioProd;
        inicioProd.getTasas();
        if (!this.ah) {
            this.d = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.creditos_header, this.listCreditos, false);
            this.listCreditos.addHeaderView(this.d, null, true);
            this.ah = true;
        }
        ((TextView) this.d.findViewById(R.id.idImporteMaximoTV)).setText(Html.fromHtml(getString(R.string.ID121_CREDITS_MAIN_LBL_MAXIMPORT)));
        TextView textView = (TextView) this.d.findViewById(R.id.vTitle);
        if (textView != null) {
            textView.setText(getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
        }
        AmountView amountView = (AmountView) this.d.findViewById(R.id.f55creditoimporteMaximo);
        amountView.setColorAmount(true);
        amountView.setCElementAcc(new CAmountAcc());
        amountView.setAmount(inicioProd.getImporteMaximoPesos());
        AmountView amountView2 = (AmountView) this.d.findViewById(R.id.f56creditoimporteMinimo);
        amountView2.setCElementAcc(new CAmountAcc());
        amountView2.setAmount(inicioProd.getImporteMinimoPesos());
        AmountView amountView3 = (AmountView) this.d.findViewById(R.id.f54creditocuotaMaxima);
        amountView3.setCElementAcc(new CAmountAcc());
        amountView3.setAmount(inicioProd.getImporteMaximoCuotas());
        this.listCreditos.setAdapter(new CreditosAdapterProd(getActivity(), 0, inicioProd.getTasas()));
        this.listCreditos.setOnItemClickListener(this);
    }

    public void setSimulacionProdView(SimulacionProd simulacionProd) {
        this.ad = simulacionProd;
        addBlockBodySimulacion(this.f.getViewForm(simulacionProd));
        TextView textView = (TextView) this.pantallaSimulacion.findViewById(R.id.f57creditopage2leyenda);
        textView.setText(Html.fromHtml(simulacionProd.getLeyenda()));
        try {
            textView.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(textView.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setConfirmacionView(ResultadoProd resultadoProd) {
        this.ae = resultadoProd;
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f72creditopage3importe)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f72creditopage3importe)).setContent(resultadoProd.getImporteSolicitud());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f75creditopage3importeNeto)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f75creditopage3importeNeto)).setContent(resultadoProd.getImporteNeto());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f83creditopage3tipoTasa)).setContent(resultadoProd.getTipoTasa());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f75creditopage3importeNeto)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f82creditopage3tasaNominal)).setContent(resultadoProd.getTasaNominalAnual());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f81creditopage3tasaEfectAnual)).setContent(resultadoProd.getTasaEfectAnual());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setmCElementAccLabel(new CCFTNALabelACC());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f59creditopage3CFTNA)).setContent(resultadoProd.getTasaCFTNA());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setmCElementAccLabel(new CCFTNA_IVALabelACC());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f60creditopage3CFTNA_IVA)).setContent(resultadoProd.getTasaCFTNAIVA());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f66creditopage3cuentaDestino)).setmCElementAccValue(new CAccountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f66creditopage3cuentaDestino)).setmCElementAccValue(new CAccountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f66creditopage3cuentaDestino)).setContent(resultadoProd.getCuentaDestino());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f62creditopage3cantidadCuotas)).setContent(resultadoProd.getCantidadCuotas());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f69creditopage3destino)).setContent(resultadoProd.getDestino());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f71creditopage3fechaPrimerVencimiento)).setmCElementAccValue(new CDateAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f71creditopage3fechaPrimerVencimiento)).setContent(resultadoProd.getFechaPrimerVencimiento());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f63creditopage3capitalIntereses)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f63creditopage3capitalIntereses)).setContent(resultadoProd.getCapitalIntereses());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f64creditopage3cargoSeguro)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f64creditopage3cargoSeguro)).setContent(resultadoProd.getCargoSeguro());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).getLabelView().setContentDescription("iva");
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f76creditopage3iva)).setContent(resultadoProd.getIva());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f80creditopage3otrosImpuestos)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f80creditopage3otrosImpuestos)).setContent(resultadoProd.getOtrosImpuestos());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f74creditopage3importeCuota)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaConfirmacion.findViewById(R.id.f74creditopage3importeCuota)).setContent(resultadoProd.getImporte());
        TextView textView = (TextView) this.pantallaConfirmacion.findViewById(R.id.f77creditopage3leyenda);
        textView.setText(Html.fromHtml(resultadoProd.getLeyenda()));
        try {
            textView.setContentDescription(CAccessibility.getInstance(getActContext()).applyFilterGeneral(textView.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setComprobacionView(ResultadoProd resultadoProd) {
        this.af = resultadoProd;
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f95creditopage4importe)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f95creditopage4importe)).setContent(resultadoProd.getImporteSolicitud());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f98creditopage4importeNeto)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f98creditopage4importeNeto)).setContent(resultadoProd.getImporteNeto());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f109creditopage4tipoTasa)).setContent(resultadoProd.getTipoTasa());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f107creditopage4tasaEfectAnual)).setContent(resultadoProd.getTasaEfectAnual());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setmCElementAccLabel(new CCFTNALabelACC());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f105creditopage4tasaCFTNA)).setContent(resultadoProd.getTasaCFTNA());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setmCElementAccLabel(new CCFTNA_IVALabelACC());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f106creditopage4tasaCFTNAIVA)).setContent(resultadoProd.getTasaCFTNAIVA());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).setmCElementAccValue(new CTasaValueAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f108creditopage4tasaNominal)).setContent(resultadoProd.getTasaNominalAnual());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f90creditopage4cuentaDestino)).setmCElementAccValue(new CAccountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f90creditopage4cuentaDestino)).setContent(resultadoProd.getCuentaDestino());
        try {
            ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f90creditopage4cuentaDestino)).getValueView().setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterMaskAccount(((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f90creditopage4cuentaDestino)).getContent()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f86creditopage4cantidadCuotas)).setContent(resultadoProd.getCantidadCuotas());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f94creditopage4fechaPrimerVencimiento)).setmCElementAccValue(new CDateAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f94creditopage4fechaPrimerVencimiento)).setContent(resultadoProd.getFechaPrimerVencimiento());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f87creditopage4capitalIntereses)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f87creditopage4capitalIntereses)).setContent(resultadoProd.getCapitalIntereses());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f88creditopage4cargoSeguro)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f88creditopage4cargoSeguro)).setContent(resultadoProd.getCargoSeguro());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f99creditopage4iva)).getLabelView().setContentDescription("iva");
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f99creditopage4iva)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f99creditopage4iva)).setContent(resultadoProd.getIva());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f104creditopage4otrosImpuestos)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f104creditopage4otrosImpuestos)).setContent(resultadoProd.getOtrosImpuestos());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f97creditopage4importeCuota)).setmCElementAccValue(new CAmountAcc());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f97creditopage4importeCuota)).setContent(resultadoProd.getImporte());
        ((RowTwoColumnViewStyled) this.pantallaComprobante.findViewById(R.id.f103creditopage4numeroComprobante)).setContent(resultadoProd.getNumeroComprobante());
        ((TextView) this.pantallaComprobante.findViewById(R.id.f100creditopage4leyenda)).setText(Html.fromHtml(resultadoProd.getLeyenda()));
        ((TextView) this.pantallaComprobante.findViewById(R.id.f100creditopage4leyenda)).setContentDescription(((TextView) this.pantallaComprobante.findViewById(R.id.f100creditopage4leyenda)).getText().toString());
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        customView.findViewById(R.id.share).setVisibility(0);
        customView.findViewById(R.id.share).setOnClickListener(this);
        customView.findViewById(R.id.toggle).setOnClickListener(this);
        this.c.trackTransaction(getString(R.string.transaction_hit_product_name_transaccion_creditos), resultadoProd.getNumeroComprobante());
    }

    public void goToInicio() {
        if (this.mControlPager.getDisplayedChild() != 0) {
            gotoPage(0, false);
            ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
            ((SantanderRioMainActivity) getActivity()).lockMenu(false);
            enableMenuButton();
            ((SantanderRioMainActivity) getActivity()).hideKeyboard();
            this.e.sendRequestConsultaPrestamosPermitidos(this.a, false);
        }
    }

    public void enableMenuButton() {
        View customView = ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView();
        this.ag = (SantanderRioMainActivity) getActivity();
        if (customView != null) {
            ((ImageView) customView.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CreditosFragmentProd.this.switchDrawer();
                }
            });
        }
    }

    public void goToSimulacion(DatosPrestamoPermitidoProd datosPrestamoPermitidoProd) {
        gotoPage(1, true);
        b(this.pantallaSimulacion);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        enableBackButton();
        this.f.onCreatePage(this.ao, this.ap, datosPrestamoPermitidoProd);
    }

    public void goToConfirmacion(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd) {
        nextPage();
        b(this.pantallaConfirmacion);
        this.g.onCreatePage(datosSolicitudPrestamoProd);
    }

    public void goToComprobante(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd) {
        nextPage();
        b(this.pantallaComprobante);
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        ((SantanderRioMainActivity) getActivity()).lockMenu(false);
        enableMenuButton();
        this.h.onCreatePage(datosSolicitudPrestamoProd);
        this.an = datosSolicitudPrestamoProd.getNumComprobante();
    }

    public void setCantidadCuota(String str) {
        this.ad.setCantidadCuotas(str);
        ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_cant_cuotas)).setText(str);
    }

    public void setPrimeraCuota(String str) {
        this.ad.setPrimeraCuota(str);
        ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_date_id)).setText(str);
    }

    public void setDestino(String str) {
        this.ad.setDestino(str);
        ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_destino)).setText(str);
    }

    public void showCantidadCuotaPicker(ArrayList<String> arrayList, String str) {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        new ArrayList();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_cantidad_cuota", getString(R.string.ID_DIALOG_CRED_CANTIDAD_CUOTAS), null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, str);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), CUOTAS_DIALOG);
    }

    public void showPrimeraCuotaPicker() {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), ((TextView) this.vgWrapperSimulacion.findViewById(R.id.selector_date_id)).getText().toString(), getString(R.string.FORMAT_SHORT_DATE));
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                CreditosFragmentProd.this.f.onPrimeraCuotaSelected(date);
            }
        });
        newInstance.show(getActivity().getSupportFragmentManager(), PRIMERA_CUOTA_DIALOG);
    }

    public void showDestinoPicker(ArrayList<String> arrayList, String str) {
        ArrayList arrayList2 = new ArrayList();
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                arrayList2.add(b((String) it.next()));
            } catch (Exception e2) {
                arrayList2.add("");
                e2.printStackTrace();
            }
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_destino", getString(R.string.ID_DIALOG_CRED_SELECCIONAR_DESTINO), null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, str, arrayList2);
        newInstance.setDialogListenerExtended(this);
        newInstance.setCancelable(true);
        newInstance.show(getActivity().getSupportFragmentManager(), DESTINO_DIALOG);
    }

    public void showProgress() {
        super.showProgress("");
    }

    public void dismisProgress() {
        super.dismissProgress();
    }

    public void showMessage(String str, final String str2) {
        FragmentManager fragmentManager = getFragmentManager();
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
        newInstance.show(fragmentManager, "Dialog");
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
                if (str2.equalsIgnoreCase(CreditosFragmentProd.this.getActContext().getResources().getString(R.string.MSG_USER000051))) {
                    CreditosFragmentProd.this.a(CreditosFragmentProd.this.pantallaSimulacion, CreditosFragmentProd.this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_destino));
                } else if (str2.equalsIgnoreCase(CreditosFragmentProd.this.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_90_DIAS)) || str2.equalsIgnoreCase(CreditosFragmentProd.this.getActContext().getResources().getString(R.string.MSG_USER000042_Prestamos_errorFechaVencimiento))) {
                    CreditosFragmentProd.this.a(CreditosFragmentProd.this.pantallaSimulacion, CreditosFragmentProd.this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id));
                }
            }

            public void onSimpleActionButton() {
                if (str2.equalsIgnoreCase(CreditosFragmentProd.this.getActContext().getResources().getString(R.string.MSG_USER000051))) {
                    CreditosFragmentProd.this.a(CreditosFragmentProd.this.pantallaSimulacion, CreditosFragmentProd.this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_destino));
                } else if (str2.equalsIgnoreCase(CreditosFragmentProd.this.getActContext().getResources().getString(R.string.MSG_USER0000XX_CREDITOS_90_DIAS)) || str2.equalsIgnoreCase(CreditosFragmentProd.this.getActContext().getResources().getString(R.string.MSG_USER000042_Prestamos_errorFechaVencimiento))) {
                    CreditosFragmentProd.this.a(CreditosFragmentProd.this.pantallaSimulacion, CreditosFragmentProd.this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id));
                }
            }
        });
    }

    public IDataManager getDataManager() {
        return this.a;
    }

    public Cuenta getCuenta() {
        return this.al;
    }

    public SolicitudPrestamoPreacordadoBodyRequestBeanProd getSolicitudSimulacion() {
        return this.am;
    }

    public void setSolicitudSimulacion(SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd) {
        this.am = solicitudPrestamoPreacordadoBodyRequestBeanProd;
    }

    public SessionManager getSessionManager() {
        return this.b;
    }

    public void openDestinoSelector() {
        this.f.onDestinosClicked();
    }

    public void addBlockBodySimulacion(View view) {
        a(this.vgWrapperSimulacion, view);
    }

    private void a(ViewGroup viewGroup, View view) {
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup.addView(view);
            E();
        }
    }

    private void E() {
        this.vgWrapperSimulacion.findViewById(R.id.row_selector_date_id).setOnClickListener(this);
        this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_cuotas).setOnClickListener(this);
        this.vgWrapperSimulacion.findViewById(R.id.row_selector_cant_destino).setOnClickListener(this);
    }

    public void clearImporte() {
        ((EditText) this.vgWrapperSimulacion.findViewById(R.id.input_amount_id)).setText("");
    }

    public void nextPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            this.mControlPager.showNext();
        }
    }

    public void previousPage() {
        if (this.mControlPager != null) {
            this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
            this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            this.mControlPager.showPrevious();
        }
    }

    public void gotoPage(int i2, boolean z) {
        if (this.mControlPager != null) {
            if (!z) {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
            } else {
                this.mControlPager.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                this.mControlPager.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
            }
            this.mControlPager.setDisplayedChild(i2);
        }
    }

    public int getIndexViewPage(View view) {
        return this.mControlPager.indexOfChild(view);
    }

    public void enableBackButton() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CreditosFragmentProd.this.onBackPressed();
                }
            });
        }
    }

    public void onItemSelected(String str, String str2) {
        if (str2.equalsIgnoreCase("selector_cantidad_cuota")) {
            setCantidadCuota(str);
            this.f.onNumCuotasSelected(str);
        } else if (str2.equalsIgnoreCase("selector_destino")) {
            setDestino(str);
            this.f.onDestinoSelected(str);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.row_selector_date_id) {
            this.f.onPrimeraCuotaClicked();
        } else if (view.getId() == R.id.row_selector_cant_cuotas) {
            this.f.onCantidadCuotasClicked();
        } else if (view.getId() == R.id.row_selector_cant_destino) {
            this.f.onDestinosClicked();
        } else if (view.getId() == R.id.share) {
            onClickShowActionShareReceipt();
        } else if (view.getId() != R.id.toggle) {
        } else {
            if (!this.aj) {
                F();
            } else {
                switchDrawer();
            }
        }
    }

    public void onClickShowActionShareReceipt() {
        this.aj = true;
        G();
        if (this.ai != null) {
            this.ai.show();
        }
    }

    private void F() {
        if (!this.aj) {
            if (this.ai == null) {
                G();
            }
            this.aj = true;
            this.ai.showAlert();
        }
    }

    private void G() {
        configActionShareReceipt(getString(R.string.IDXX_SHARE_CREDITOS), getString(R.string.IDXX_SHARE_CREDITOS).concat("-").concat(this.an));
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
        this.c.trackScreen(getString(R.string.analytics_screen_name_creditos_configuracion));
        this.e.onItemClicked(i2);
    }

    public Cuenta getCuentaSelected() {
        return this.al;
    }

    public void setCuentaSelected(Cuenta cuenta) {
        this.al = cuenta;
    }

    private void b(final View view) {
        if (view != null) {
            view.post(new Runnable() {
                public void run() {
                    view.scrollTo(0, 0);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public final void a(final View view, final View view2) {
        new Handler().post(new Runnable() {
            public void run() {
                view2.requestFocus();
                view.scrollTo(0, view2.getBottom());
            }
        });
    }

    public void configActionShareReceipt(String str, String str2) {
        final String str3 = str2;
        final String str4 = str;
        AnonymousClass8 r0 = new OptionsToShareImpl((AppCompatActivity) getActivity(), getActivity().getApplicationContext(), getActivity().getSupportFragmentManager()) {
            public View getViewToShare() {
                return CreditosFragmentProd.this.viewComprobanteCreditos;
            }

            public void receiveIntentAppShare(Intent intent) {
                CreditosFragmentProd.this.startActivityForResult(Intent.createChooser(intent, CreditosFragmentProd.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)), 999);
            }

            public String getFileName() {
                return str3;
            }

            public String getSubjectReceiptToShare() {
                return str4;
            }
        };
        this.ai = r0;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 999) {
            UtilFile.deleteDirectory(new File(UtilFile.getPathReceiptTmp()));
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    private String b(String str) {
        if (str == null) {
            return str;
        }
        if (str.endsWith("herram") || str.contains("herram ")) {
            str = str.replace("herram", "herramientas");
        }
        if (str.contains("cancelac ")) {
            str = str.replace("cancelac ", "cancelación ");
        }
        if (str.contains("equip ")) {
            str = str.replace("equip ", "equipos ");
        }
        return str.contains("art hog") ? str.replace("art hog", "artículos para el hogar") : str;
    }
}
