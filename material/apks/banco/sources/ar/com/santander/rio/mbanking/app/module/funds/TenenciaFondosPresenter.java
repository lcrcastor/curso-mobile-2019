package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.CuentasUtils;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetFondosEvent;
import ar.com.santander.rio.mbanking.services.events.GetTenenciaFondosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTenenciaFondos;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TenenciaFondosPresenter extends BasePresenter<TenenciaFondosView> {
    private InversionesAnalyticsImpl a;
    private GetTenenciaFondosBodyResponseBean b;
    private String c;
    protected Context context;
    private boolean d = true;
    protected SessionManager sessionManager;

    public TenenciaFondosPresenter(Bus bus, IDataManager iDataManager, SessionManager sessionManager2, Context context2, AnalyticsManager analyticsManager) {
        super(bus, iDataManager);
        this.context = context2;
        this.sessionManager = sessionManager2;
        this.a = new InversionesAnalyticsImpl(context2, analyticsManager);
    }

    public void onCreatePage(List<CuentaFondosBean> list, Boolean bool, String str, String str2, List<Leyenda> list2, String str3) {
        ((TenenciaFondosView) getBaseView()).setTenenciaFondosView(list, bool, str, str2, list2, str3);
    }

    public void onGetTenencias() {
        ((TenenciaFondosView) getBaseView()).showProgressIndicator(VGetTenenciaFondos.nameService);
        this.mDataManager.getTenenciaFondos();
    }

    @Subscribe
    public void onGetTenenciaFondos(GetTenenciaFondosEvent getTenenciaFondosEvent) {
        ((TenenciaFondosView) getBaseView()).dismissProgressIndicator();
        final GetTenenciaFondosEvent getTenenciaFondosEvent2 = getTenenciaFondosEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.context, TypeOption.INITIAL_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.context) {
            public void onOk() {
                TenenciaFondosPresenter.this.onGetTenenciaFondosResultOk(getTenenciaFondosEvent2);
            }

            public void onRes4Error() {
                TenenciaFondosPresenter.this.onGetTenenciaFondosResult4(getTenenciaFondosEvent2);
            }

            /* access modifiers changed from: protected */
            public void onServerError() {
                TenenciaFondosPresenter.this.onGetTenenciaFondosResult4(getTenenciaFondosEvent2);
            }
        };
        r1.handleWSResponse(getTenenciaFondosEvent);
    }

    public void onGetTenenciaFondosResult4(GetTenenciaFondosEvent getTenenciaFondosEvent) {
        this.a.fondosComunesErrorTotal();
        if (getTenenciaFondosEvent.getErrorBodyBean() == null || TextUtils.isEmpty(getTenenciaFondosEvent.getErrorBodyBean().resCod) || !getTenenciaFondosEvent.getErrorBodyBean().resCod.equalsIgnoreCase("USER000431")) {
            List arrayList = new ArrayList();
            if (!((this.sessionManager.getLoginUnico().getProductos().getCuentasTitulo() == null || this.sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo() == null || this.sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo().size() <= 0) && (this.sessionManager.getLoginUnico().getProductos().getCuentasBP() == null || this.sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP() == null || this.sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP().size() <= 0))) {
                arrayList = a(CuentasUtils.getAllCuentasBPTRL(this.sessionManager));
            }
            List list = arrayList;
            if (getTenenciaFondosEvent.getResult().equals(TypeResult.SERVER_ERROR)) {
                onCreatePage(list, Boolean.valueOf(true), null, null, null, this.context.getString(R.string.MSG_USER000002_General_errorNoconexion));
            } else if (getTenenciaFondosEvent.getResult().equals(TypeResult.BEAN_ERROR_RES_4)) {
                onCreatePage(list, Boolean.valueOf(true), null, null, null, getTenenciaFondosEvent.getErrorBodyBean().resDesc);
            } else if (list.size() == 0) {
                onCreatePage(list, Boolean.valueOf(true), null, null, null, this.context.getString(R.string.F24_00_TXT_SIN_CUENTAS_TITULO));
            }
        } else {
            ((SantanderRioMainActivity) this.context).setErrorFragment(getTenenciaFondosEvent.getErrorBodyBean().resDesc);
        }
    }

    public void onGetTenenciaFondosResultOk(GetTenenciaFondosEvent getTenenciaFondosEvent) {
        try {
            this.b = getTenenciaFondosEvent.getResponseBean().getGetTenenciaFondosBodyResponseBean();
            ((TenenciaFondosView) getBaseView()).setTenenciaFondosBodyResponseBean(this.b);
            new ArrayList();
            List a2 = a(CuentasUtils.getAllCuentasBPTRL(this.sessionManager));
            if (getTenenciaFondosEvent.getResponseBean().getGetTenenciaFondosBodyResponseBean().getListaCuentasFondosBean().getCuentasFondosBean().size() > 0) {
                a2 = a(getTenenciaFondosEvent.getResponseBean().getGetTenenciaFondosBodyResponseBean().getListaCuentasFondosBean().getCuentasFondosBean(), a2);
            }
            List list = a2;
            boolean z = list.size() == 0;
            if (z) {
                onCreatePage(list, Boolean.valueOf(z), null, null, null, this.context.getString(R.string.F24_00_TXT_SIN_CUENTAS_TITULO));
            } else {
                onCreatePage(list, Boolean.valueOf(z), getTenenciaFondosEvent.getResponseBean().getGetTenenciaFondosBodyResponseBean().getContratoSusc(), getTenenciaFondosEvent.getResponseBean().getGetTenenciaFondosBodyResponseBean().getContratoTransf(), getTenenciaFondosEvent.getResponseBean().getGetTenenciaFondosBodyResponseBean().getListaLeyendas().getLstLeyendas(), null);
            }
        } catch (Exception e) {
            onGetTenenciaFondosResult4(getTenenciaFondosEvent);
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetTenenciaFondosResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    private List<CuentaFondosBean> a(List<CuentaFondosBean> list, List<CuentaFondosBean> list2) {
        for (CuentaFondosBean cuentaFondosBean : list2) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CuentaFondosBean cuentaFondosBean2 = (CuentaFondosBean) it.next();
                try {
                    if (Integer.parseInt(cuentaFondosBean.getNumero()) == Integer.parseInt(cuentaFondosBean2.getNumero()) && Integer.parseInt(cuentaFondosBean.getSucursalCuenta()) == Integer.parseInt(cuentaFondosBean2.getSucursalCuenta()) && Integer.parseInt(cuentaFondosBean.getTipoCuenta()) == Integer.parseInt(cuentaFondosBean2.getTipoCuenta())) {
                        if (cuentaFondosBean2.getListaFondos() != null) {
                            cuentaFondosBean.setListaFondos(cuentaFondosBean2.getListaFondos());
                        }
                        cuentaFondosBean.setImportePesos(!TextUtils.isEmpty(cuentaFondosBean2.getImportePesos()) ? cuentaFondosBean2.getImportePesos() : "");
                        cuentaFondosBean.setImporteDolares(!TextUtils.isEmpty(cuentaFondosBean2.getImporteDolares()) ? cuentaFondosBean2.getImporteDolares() : "");
                        cuentaFondosBean.setMensajeTitulo(cuentaFondosBean2.getMensajeTitulo());
                        cuentaFondosBean.setMensajeDescripcion(cuentaFondosBean2.getMensajeDescripcion());
                        cuentaFondosBean.setMensajeCodigo(cuentaFondosBean2.getMensajeCodigo());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list2;
    }

    private List<CuentaFondosBean> a(List<Cuenta> list) {
        ArrayList arrayList = new ArrayList();
        for (Cuenta cuenta : list) {
            CuentaFondosBean cuentaFondosBean = new CuentaFondosBean();
            cuentaFondosBean.setNumero(TextUtils.isEmpty(cuenta.getNumero()) ? "" : cuenta.getNumero());
            cuentaFondosBean.setSucursalCuenta(TextUtils.isEmpty(cuenta.getNroSuc()) ? "" : cuenta.getNroSuc());
            cuentaFondosBean.setTipoCuenta(TextUtils.isEmpty(cuenta.getTipo()) ? "" : cuenta.getTipo());
            cuentaFondosBean.setListaFondos(new ListaFondosBean());
            cuentaFondosBean.setImportePesos("");
            cuentaFondosBean.setImporteDolares("");
            arrayList.add(cuentaFondosBean);
        }
        return arrayList;
    }

    public void showSelectAccountDialog(CuentaFondosBean cuentaFondosBean, final ArrayList<CuentaFondosBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CuentaFondosBean cuentaFondosBean2 = (CuentaFondosBean) it.next();
            try {
                if (cuentaFondosBean2.getTipoCuenta().equalsIgnoreCase("08") || (Integer.valueOf(cuentaFondosBean2.getSucursalCuenta()).intValue() >= 250 && Integer.valueOf(cuentaFondosBean2.getSucursalCuenta()).intValue() <= 259)) {
                    arrayList2.add(cuentaFondosBean2.getFormattedSucCuentaLarge());
                    arrayList3.add(cuentaFondosBean2.getFormattedSucCuentaLarge());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, this.context.getString(R.string.TEXT_SELECTOR_ACCOUNT), null, arrayList2, this.context.getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, cuentaFondosBean.getFormattedSucCuentaLarge(), arrayList3);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    CuentaFondosBean cuentaFondosBean = (CuentaFondosBean) it.next();
                    if (str.equalsIgnoreCase(cuentaFondosBean.getFormattedSucCuentaLarge())) {
                        ((TenenciaFondosView) TenenciaFondosPresenter.this.getBaseView()).setSelectedAccount(cuentaFondosBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((TenenciaFondosView) getBaseView()).getFragmentManager(), "SelectorCuenta");
    }

    public View getViewList(ArrayList<FondoBean> arrayList) {
        LayoutInflater layoutInflater;
        TableLayout tableLayout = new TableLayout(this.context);
        LayoutInflater layoutInflater2 = (LayoutInflater) this.context.getSystemService("layout_inflater");
        Iterator it = arrayList.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            final FondoBean fondoBean = (FondoBean) it.next();
            LinearLayout linearLayout = (LinearLayout) layoutInflater2.inflate(R.layout.list_item_tenencia_fondos, null);
            TextView textView = (TextView) linearLayout.findViewById(R.id.F24_00_LBL_NOMBRE_FONDO);
            TextView textView2 = (TextView) linearLayout.findViewById(R.id.F24_00_LBL_DATA_COTIZACION);
            TextView textView3 = (TextView) linearLayout.findViewById(R.id.F24_00_LBL_DATA_VALOR_CUOTAPARTE);
            TextView textView4 = (TextView) linearLayout.findViewById(R.id.F24_00_LBL_DATA_CUOTAPARTE);
            TextView textView5 = (TextView) linearLayout.findViewById(R.id.F24_00_LBL_DATA_IMPORTE);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.F24_00_IMG_FLECHA_COTIZACION);
            if (!TextUtils.isEmpty(fondoBean.getVariacionCotizaDiaria())) {
                imageView.setVisibility(i);
                textView2.setVisibility(i);
                if (fondoBean.getVariacionCotizaDiaria().indexOf("-") == -1) {
                    layoutInflater = layoutInflater2;
                    textView2.setText(fondoBean.getVariacionCotizaDiaria().replace(Constants.SYMBOL_POSITIVE, ""));
                    imageView.setImageResource(R.drawable.green_arrow);
                } else {
                    layoutInflater = layoutInflater2;
                    imageView.setImageResource(R.drawable.red_arrow);
                    textView2.setText(fondoBean.getVariacionCotizaDiaria().replace("-", ""));
                }
            } else {
                layoutInflater = layoutInflater2;
                imageView.setVisibility(8);
                textView2.setVisibility(8);
            }
            textView4.setText(fondoBean.getCantidadCuotapartes());
            textView3.setText(fondoBean.getValorCuotaParte());
            try {
                textView3.setContentDescription(new CAccessibility(textView3.getContext()).applyFilterAmount(fondoBean.getValorCuotaParte()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()));
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(fondoBean.getImporte());
            textView5.setText(sb.toString());
            try {
                CAccessibility cAccessibility = new CAccessibility(textView5.getContext());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()));
                sb2.append(fondoBean.getImporte());
                textView5.setContentDescription(cAccessibility.applyFilterAmount(sb2.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            textView.setText(Html.fromHtml(fondoBean.getNombre()));
            try {
                textView.setContentDescription(FondosConstants.applyAccesibilityFilterName(this.context, textView.getText().toString()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            linearLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ((TenenciaFondosView) TenenciaFondosPresenter.this.getBaseView()).gotoDetalleFondo(fondoBean);
                }
            });
            if (i2 % 2 != 0) {
                linearLayout.setBackgroundColor(this.context.getResources().getColor(R.color.white));
            }
            tableLayout.addView(linearLayout, i2);
            i2++;
            layoutInflater2 = layoutInflater;
            i = 0;
        }
        return tableLayout;
    }

    public void callGetFondos(String str) {
        if (this.d) {
            this.d = false;
            this.c = str;
            ((TenenciaFondosView) getBaseView()).showProgressIndicator(VGetTenenciaFondos.nameService);
            this.mDataManager.getFondos();
        }
    }

    @Subscribe
    public void onGetFondos(GetFondosEvent getFondosEvent) {
        ((TenenciaFondosView) getBaseView()).dismissProgressIndicator();
        this.d = true;
        final GetFondosEvent getFondosEvent2 = getFondosEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(this.context, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.context) {
            public void onOk() {
                TenenciaFondosPresenter.this.onGetFondosResponseOk(getFondosEvent2);
            }
        };
        r1.handleWSResponse(getFondosEvent);
    }

    public void onGetFondosResponseOk(GetFondosEvent getFondosEvent) {
        try {
            if (this.c.equalsIgnoreCase(FondosConstants.ORIGEN_INFORMACION)) {
                ((TenenciaFondosView) getBaseView()).gotoInformacionFondosActivity(getFondosEvent.getResponseBean().getGetFondosBodyResponseBean().getListaCategoriasFondosBean().getCategoriasFondosBean());
            } else if (this.c.equalsIgnoreCase(FondosConstants.ORIGEN_SUSCRIBIR)) {
                ((TenenciaFondosView) getBaseView()).gotoSuscribirFondoActivity(getFondosEvent.getResponseBean().getGetFondosBodyResponseBean().getListaCategoriasFondosBean().getCategoriasFondosBean(), this.b);
            } else if (this.c.equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
                ((TenenciaFondosView) getBaseView()).gotoUltimosMovimientosActivity(getFondosEvent.getResponseBean().getGetFondosBodyResponseBean().getListaCategoriasFondosBean().getCategoriasFondosBean());
            }
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetFondosResponseOk: ", e);
            e.fillInStackTrace();
        }
    }
}
