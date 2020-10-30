package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
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
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularRescateFondo;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarRescateFondoPresenter extends BasePresenter<ConfirmarRescateFondoView> {
    public ConfirmarRescateFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean) {
        ((ConfirmarRescateFondoView) getBaseView()).setConfirmarRescateFondoView(rescatarFondoBodyResponseBean);
    }

    public Context getContext() {
        return ((ConfirmarRescateFondoView) getBaseView()).getContext();
    }

    public void showConfirmarDialog(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, String str, RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean, Cuenta cuenta) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F24_21_TITLE_CONFIRM_DIALOG), String.format(getContext().getString(R.string.F24_21_BODY_CONFIRM_DIALOG), new Object[]{Html.fromHtml(fondoBean.getNombre())}), null, null, getContext().getString(R.string.IDX_ALERT_BTN_YES), getContext().getString(R.string.IDX_ALERT_BTN_NO), null);
        final CuentaFondosBean cuentaFondosBean2 = cuentaFondosBean;
        final FondoBean fondoBean2 = fondoBean;
        final String str2 = str;
        final RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean2 = rescatarFondoBodyResponseBean;
        final Cuenta cuenta2 = cuenta;
        AnonymousClass1 r1 = new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmarRescateFondoPresenter.this.callRescatarFondo(cuentaFondosBean2, fondoBean2, str2, rescatarFondoBodyResponseBean2, cuenta2);
            }
        };
        newInstance.setDialogListener(r1);
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
    }

    public void showTyC(String str) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getContext().getString(R.string.F24_20_LBL_TITLE_TERMINOS));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        getContext().startActivity(intent);
    }

    public void gotoComprobante(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, String str, RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean, Cuenta cuenta, String str2) {
        showConfirmarDialog(cuentaFondosBean, fondoBean, str, rescatarFondoBodyResponseBean, cuenta);
    }

    public void callRescatarFondo(CuentaFondosBean cuentaFondosBean, FondoBean fondoBean, String str, RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean, Cuenta cuenta) {
        ((ConfirmarRescateFondoView) getBaseView()).showProgressIndicator(VSimularRescateFondo.nameService);
        String str2 = "0";
        if (UtilCurrency.isDolares(fondoBean.getMoneda())) {
            str2 = "2";
        }
        String str3 = str2;
        String str4 = cuenta.getTipo().equalsIgnoreCase("02") ? str3 == "0" ? "09" : "10" : cuenta.getTipo();
        this.mDataManager.rescatarFondo(cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), fondoBean.getId(), str3, str, str4, cuenta.getNroSuc(), cuenta.getNumero(), rescatarFondoBodyResponseBean.getComision(), rescatarFondoBodyResponseBean.getImporteNeto());
    }

    @Subscribe
    public void rescatarFondo(RescatarFondoEvent rescatarFondoEvent) {
        ((ConfirmarRescateFondoView) getBaseView()).dismissProgressIndicator();
        final RescatarFondoEvent rescatarFondoEvent2 = rescatarFondoEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) getContext()) {
            public void onOk() {
                ConfirmarRescateFondoPresenter.this.a(rescatarFondoEvent2);
            }

            public void onRes8Error(WebServiceEvent webServiceEvent) {
                ((ConfirmarRescateFondoView) ConfirmarRescateFondoPresenter.this.getBaseView()).gotoFueraHorarioFondo(this.mErrorEvent.getMessageToShow());
            }
        };
        r1.handleWSResponse(rescatarFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(RescatarFondoEvent rescatarFondoEvent) {
        try {
            ((ConfirmarRescateFondoView) getBaseView()).gotoComprobante(((RescatarFondoResponseBean) rescatarFondoEvent.getBeanResponse()).getRescatarFondoBodyResponseBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "rescatarFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
