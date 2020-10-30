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
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetFondosEvent;
import ar.com.santander.rio.mbanking.services.events.SimularTransferenciaFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularTransferenciaFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFondos;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularTransferenciaFondo;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;

public class TransferirFondoPresenter extends BasePresenter<TransferirFondoView> {
    public TransferirFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((TransferirFondoView) getBaseView()).setTransferirFondoView();
    }

    public Context getContext() {
        return ((TransferirFondoView) getBaseView()).getContext();
    }

    public void showSelectAccountDialog(CuentaFondosBean cuentaFondosBean, final ArrayList<CuentaFondosBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((CuentaFondosBean) it.next()).getNumero());
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList2, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, cuentaFondosBean.getNumero());
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
                    if (str.equalsIgnoreCase(cuentaFondosBean.getNumero().toString())) {
                        ((TransferirFondoView) TransferirFondoPresenter.this.getBaseView()).setSelectedAccount(cuentaFondosBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAccount");
    }

    public void showSelectOriginFundDialog(FondoBean fondoBean, final ArrayList<FondoBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((FondoBean) it.next()).getNombre());
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
                        ((TransferirFondoView) TransferirFondoPresenter.this.getBaseView()).setSelectedFondoOrigen(fondoBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectFund");
    }

    public void showAmountTypeDialog(TextView textView) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getContext().getResources().getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL));
        arrayList.add(getContext().getResources().getString(R.string.F24_30_SELECTED_AMOUNT_OTRO));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList, getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, textView.getText().toString());
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(TransferirFondoPresenter.this.getContext().getResources().getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL))) {
                    ((TransferirFondoView) TransferirFondoPresenter.this.getBaseView()).setSelectedAmountTypeTotal();
                } else if (str.equalsIgnoreCase(TransferirFondoPresenter.this.getContext().getResources().getString(R.string.F24_30_SELECTED_AMOUNT_OTRO))) {
                    ((TransferirFondoView) TransferirFondoPresenter.this.getBaseView()).setSelectedAmountTypeOtro();
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAmountType");
    }

    public void gotoConfirmar(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, FondoBean fondoBean2, String str) {
        if (((TransferirFondoView) getBaseView()).isValidDataTransferir()) {
            callSimularTransferenciaFondo(cuentaFondosBean, fondoBean, fondoBean2, str);
        }
    }

    public void callSimularTransferenciaFondo(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, FondoBean fondoBean2, String str) {
        ((TransferirFondoView) getBaseView()).showProgressIndicator(VSimularTransferenciaFondo.nameService);
        String str2 = "0";
        if (fondoBean.getMoneda().equalsIgnoreCase("2") || UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLAR)) {
            str2 = "2";
        }
        this.mDataManager.simularTransferenciaFondo(cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), str, str2, fondoBean.getId(), fondoBean2.getId());
    }

    @Subscribe
    public void simularTransferenciaFondo(SimularTransferenciaFondoEvent simularTransferenciaFondoEvent) {
        ((TransferirFondoView) getBaseView()).dismissProgressIndicator();
        final SimularTransferenciaFondoEvent simularTransferenciaFondoEvent2 = simularTransferenciaFondoEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) getContext()) {
            public void onOk() {
                TransferirFondoPresenter.this.a(simularTransferenciaFondoEvent2);
            }

            public void onRes8Error(WebServiceEvent webServiceEvent) {
                ((TransferirFondoView) TransferirFondoPresenter.this.getBaseView()).gotoFueraHorarioFondo(webServiceEvent.getMessageToShow());
            }
        };
        r1.handleWSResponse(simularTransferenciaFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(SimularTransferenciaFondoEvent simularTransferenciaFondoEvent) {
        try {
            ((TransferirFondoView) getBaseView()).gotoConfirmacion(((SimularTransferenciaFondoResponseBean) simularTransferenciaFondoEvent.getBeanResponse()).getSimularTransferenciaFondoBodyResponseBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "simularTransferenciaFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void callGetFondos() {
        ((TransferirFondoView) getBaseView()).showProgressIndicator(VGetFondos.nameService);
        this.mDataManager.getFondos();
    }

    @Subscribe
    public void getFondo(GetFondosEvent getFondosEvent) {
        ((TransferirFondoView) getBaseView()).dismissProgressIndicator();
        final GetFondosEvent getFondosEvent2 = getFondosEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) getContext()) {
            public void onOk() {
                TransferirFondoPresenter.this.a(getFondosEvent2);
            }
        };
        r1.handleWSResponse(getFondosEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetFondosEvent getFondosEvent) {
        try {
            ((TransferirFondoView) getBaseView()).showSelectDestinyFundActivity(((GetFondosResponseBean) getFondosEvent.getBeanResponse()).getGetFondosBodyResponseBean().getListaCategoriasFondosBean().getCategoriasFondosBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
