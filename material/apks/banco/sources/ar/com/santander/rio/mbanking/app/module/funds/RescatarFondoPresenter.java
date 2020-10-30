package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.RescatarFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularRescateFondo;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RescatarFondoPresenter extends BasePresenter<RescatarFondoView> {
    public RescatarFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((RescatarFondoView) getBaseView()).setRescatarFondoView();
    }

    public Context getContext() {
        return ((RescatarFondoView) getBaseView()).getContext();
    }

    public void showSelectAccountDialog(CuentaFondosBean cuentaFondosBean, final ArrayList<CuentaFondosBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(UtilAccount.formatCuentaTitulo(((CuentaFondosBean) it.next()).getNumero()));
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList2, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, UtilAccount.formatCuentaTitulo(cuentaFondosBean.getNumero()));
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
                    if (str.equalsIgnoreCase(UtilAccount.formatCuentaTitulo(cuentaFondosBean.getNumero().toString()))) {
                        ((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).setSelectedAccount(cuentaFondosBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAccount");
    }

    public void showSelectFundDialog(FondoBean fondoBean, final ArrayList<FondoBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Html.fromHtml(((FondoBean) it.next()).getNombre()).toString());
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList2, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, Html.fromHtml(fondoBean.getNombre()).toString());
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
                    if (str.equalsIgnoreCase(Html.fromHtml(fondoBean.getNombre()).toString())) {
                        ((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).setSelectedFondo(fondoBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectFund");
    }

    public void showSelectDestinationAccountDialog(Cuenta cuenta, final ArrayList<Cuenta> arrayList, String str, List<CuentaOperativaBean> list) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        List<CuentaOperativaBean> cuentasValidas = ((RescatarFondoView) getBaseView()).getCuentasValidas(list);
        String str2 = "";
        if (cuenta != null) {
            str2 = CAccounts.getInstance(((RescatarFondoView) getBaseView()).getSessionManager()).getAbrevAccount(cuenta);
        }
        String str3 = str2;
        for (CuentaOperativaBean cuentaOperativaBean : cuentasValidas) {
            String descCtaDestino = cuentaOperativaBean.getDescCtaDestino();
            arrayList2.add(descCtaDestino);
            if (cuenta != null && str3.equals(cuentaOperativaBean.getDescCtaDestino())) {
                str3 = cuentaOperativaBean.getDescCtaDestino();
            }
            try {
                arrayList3.add(new CAccessibility(getContext()).applyFilterAccount(descCtaDestino));
            } catch (Exception e) {
                e.printStackTrace();
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
                    if (str.equalsIgnoreCase(CAccounts.getInstance(((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).getSessionManager()).getAbrevAccount(cuenta))) {
                        ((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).setSelectedDestinationAccount(cuenta);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAccount");
    }

    public void showAmountTypeDialog(TextView textView) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_TOTAL));
        arrayList.add(getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_OTRO));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, textView.getText().toString());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(RescatarFondoPresenter.this.getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_TOTAL))) {
                    ((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).setSelectedAmountTypeTotal();
                } else if (str.equalsIgnoreCase(RescatarFondoPresenter.this.getContext().getResources().getString(R.string.F24_20_SELECTED_AMOUNT_OTRO))) {
                    ((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).setSelectedAmountTypeOtro();
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAmountType");
    }

    public void gotoConfirmar(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, String str, Cuenta cuenta) {
        if (((RescatarFondoView) getBaseView()).isValidDataRescatar()) {
            callSimularRescateFondo(cuentaFondosBean, fondoBean, str, cuenta);
        }
    }

    public void callSimularRescateFondo(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, String str, Cuenta cuenta) {
        ((RescatarFondoView) getBaseView()).showProgressIndicator(VSimularRescateFondo.nameService);
        String str2 = "0";
        if (UtilCurrency.isDolares(fondoBean.getMoneda())) {
            str2 = "2";
        }
        String str3 = str2;
        String str4 = cuenta.getTipo().equalsIgnoreCase("02") ? str3 == "0" ? "09" : "10" : cuenta.getTipo();
        this.mDataManager.simularRescateFondo(cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), fondoBean.getId(), str3, str, str4, cuenta.getNroSuc(), cuenta.getNumero());
    }

    @Subscribe
    public void simularRescateFondo(RescatarFondoEvent rescatarFondoEvent) {
        ((RescatarFondoView) getBaseView()).dismissProgressIndicator();
        final RescatarFondoEvent rescatarFondoEvent2 = rescatarFondoEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) getContext()) {
            public void onOk() {
                RescatarFondoPresenter.this.a(rescatarFondoEvent2);
            }

            public void onRes8Error(WebServiceEvent webServiceEvent) {
                ((RescatarFondoView) RescatarFondoPresenter.this.getBaseView()).gotoFueraHorarioFondo(webServiceEvent.getMessageToShow());
            }
        };
        r1.handleWSResponse(rescatarFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(RescatarFondoEvent rescatarFondoEvent) {
        try {
            ((RescatarFondoView) getBaseView()).gotoConfirmacion(((RescatarFondoResponseBean) rescatarFondoEvent.getBeanResponse()).getRescatarFondoBodyResponseBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "simularRescateFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
