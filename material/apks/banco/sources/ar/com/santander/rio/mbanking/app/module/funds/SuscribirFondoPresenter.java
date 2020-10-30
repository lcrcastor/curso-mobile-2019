package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.SimularSuscripcionFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularSuscripcionFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularSuscripcionFondo;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuscribirFondoPresenter extends BasePresenter<SuscribirFondoView> {
    private boolean a = true;

    public SuscribirFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((SuscribirFondoView) getBaseView()).setFondoSeleccionadoView();
    }

    public Context getContext() {
        return ((SuscribirFondoView) getBaseView()).getContext();
    }

    public void showSelectAccountDialog(CuentaFondosBean cuentaFondosBean, final ArrayList<CuentaFondosBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CuentaFondosBean cuentaFondosBean2 = (CuentaFondosBean) it.next();
            try {
                if (cuentaFondosBean2.getTipoCuenta().equalsIgnoreCase("08")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(getContext().getString(R.string.TITLE_ACCOUNT));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(UtilAccount.formatCuentaTitulo(cuentaFondosBean2.getNumero()));
                    arrayList2.add(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(getContext().getString(R.string.TITLE_ACCOUNT));
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    sb2.append(new CAccessibility(getContext()).applyFilterCharacterToCharacter(UtilAccount.formatCuentaTitulo(cuentaFondosBean2.getNumero())));
                    arrayList3.add(sb2.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String string = getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getContext().getString(R.string.TITLE_ACCOUNT));
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(UtilAccount.formatCuentaTitulo(cuentaFondosBean.getNumero()));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, null, arrayList2, string, null, null, sb3.toString(), arrayList3);
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
                    StringBuilder sb = new StringBuilder();
                    sb.append(SuscribirFondoPresenter.this.getContext().getString(R.string.TITLE_ACCOUNT));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(UtilAccount.formatCuentaTitulo(cuentaFondosBean.getNumero().toString()));
                    if (str.equalsIgnoreCase(sb.toString())) {
                        ((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).setSelectedAccount(cuentaFondosBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAccount");
    }

    public void showSelectFundDialog(FondoBean fondoBean, final ArrayList<FondoBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Html.fromHtml(((FondoBean) it.next()).getNombre()).toString());
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList2, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, fondoBean.getNombre());
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
                    FondoBean fondoBean = (FondoBean) it.next();
                    if (str.equalsIgnoreCase(fondoBean.getNombre().toString())) {
                        ((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).setSelectedFondo(fondoBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectFund");
    }

    public void showAmountTypeDialog(TextView textView) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_TOTAL));
        arrayList.add(getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_OTRO));
        arrayList.add(getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_CUOTAPARTES));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, textView.getText().toString());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(SuscribirFondoPresenter.this.getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_TOTAL))) {
                    ((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).setSelectedAmountTypeTotal();
                } else if (str.equalsIgnoreCase(SuscribirFondoPresenter.this.getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_OTRO))) {
                    ((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).setSelectedAmountTypeOtro();
                } else if (str.equalsIgnoreCase(SuscribirFondoPresenter.this.getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_CUOTAPARTES))) {
                    ((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).setSelectedAmountTypeCuotapartes();
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAmountType");
    }

    public void showSelectDestinationAccountDialog(Cuenta cuenta, final ArrayList<Cuenta> arrayList, String str, List<CuentaOperativaBean> list) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        List<CuentaOperativaBean> cuentasValidas = ((SuscribirFondoView) getBaseView()).getCuentasValidas(list);
        String str2 = "";
        if (cuenta != null) {
            str2 = CAccounts.getInstance(((SuscribirFondoView) getBaseView()).getSessionManager()).getAbrevAccount(cuenta);
        }
        String str3 = str2;
        for (CuentaOperativaBean cuentaOperativaBean : cuentasValidas) {
            if (TextUtils.isEmpty(str) || cuentaOperativaBean.getTipoCta().equalsIgnoreCase("02") || UtilAccount.getCurrencyOfAccount(((SuscribirFondoView) getBaseView()).getSessionManager(), cuentaOperativaBean).equalsIgnoreCase(str)) {
                String descCtaDebito = cuentaOperativaBean.getDescCtaDebito();
                arrayList2.add(descCtaDebito);
                if (cuenta != null && str3.equals(cuentaOperativaBean.getDescCtaDestino())) {
                    str3 = cuentaOperativaBean.getDescCtaDebito();
                }
                try {
                    arrayList3.add(new CAccessibility(getContext()).applyFilterGeneral(descCtaDebito));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, null, arrayList2, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, str3, arrayList3);
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
                    Cuenta cuenta = (Cuenta) it.next();
                    if (((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).getDestinoDeCuentaOpEnLista(str).equalsIgnoreCase(CAccounts.getInstance(((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).getSessionManager()).getAbrevAccount(cuenta))) {
                        ((SuscribirFondoView) SuscribirFondoPresenter.this.getBaseView()).setSelectedDestinationAccount(cuenta);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAccount");
    }

    public void gotoConfirmar(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, Cuenta cuenta) {
        if (this.a) {
            this.a = false;
            if (((SuscribirFondoView) getBaseView()).isValidDataSuscribir()) {
                a(fondoBean, cuentaFondosBean, str, cuenta);
            }
        }
    }

    private void a(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, Cuenta cuenta) {
        if (getBaseView() != null) {
            ((SuscribirFondoView) getBaseView()).showProgressIndicator(VSimularSuscripcionFondo.nameService);
        }
        String str2 = "0";
        try {
            if (fondoBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION) || UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLAR)) {
                str2 = "2";
            }
        } catch (Exception unused) {
        }
        String str3 = str2;
        String str4 = cuenta.getTipo().equalsIgnoreCase("02") ? str3 == "0" ? "09" : "10" : cuenta.getTipo();
        this.mDataManager.simularSuscripcionFondo(cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), fondoBean.getId(), str, str3, str4, cuenta.getNroSuc(), cuenta.getNumero());
    }

    @Subscribe
    public void onSimularSuscripcionFondo(SimularSuscripcionFondoEvent simularSuscripcionFondoEvent) {
        this.a = true;
        final SimularSuscripcionFondoEvent simularSuscripcionFondoEvent2 = simularSuscripcionFondoEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(this, getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), true, true, (BaseActivity) getContext()) {
            final /* synthetic */ SuscribirFondoPresenter b;

            {
                this.b = r10;
            }

            public void onOk() {
                this.b.onSimularSuscripcionFondoResultOk(simularSuscripcionFondoEvent2);
            }

            public void onRes8Error(WebServiceEvent webServiceEvent) {
                ((SuscribirFondoView) this.b.getBaseView()).getAnalyticsManager().trackScreen(((SuscribirFondoView) this.b.getBaseView()).getContext().getString(R.string.analytics_screen_suscribirCuotapartes_fuera_horario));
                ((SuscribirFondoView) this.b.getBaseView()).gotoFueraHorarioFondo(webServiceEvent.getMessageToShow());
            }
        };
        r1.handleWSResponse(simularSuscripcionFondoEvent);
    }

    public void onSimularSuscripcionFondoResultOk(SimularSuscripcionFondoEvent simularSuscripcionFondoEvent) {
        try {
            ((SuscribirFondoView) getBaseView()).gotoConfirmarSuscripcionFondo(((SimularSuscripcionFondoResponseBean) simularSuscripcionFondoEvent.getBeanResponse()).getSimularSuscripcionFondoBodyResponseBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onSimularSuscripcionFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
