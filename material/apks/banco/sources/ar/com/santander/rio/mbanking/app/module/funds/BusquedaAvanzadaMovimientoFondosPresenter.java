package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetMovimientosFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetMovimientosFondo;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.Date;

public class BusquedaAvanzadaMovimientoFondosPresenter extends BasePresenter<BusquedaAvanzadaMovimientoFondosView> {
    public final String cFECHA_DESDE = "FECHA_DESDE";
    public final String cFECHA_HASTA = "FECHA_HASTA";
    protected Context mContext;

    public BusquedaAvanzadaMovimientoFondosPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreate() {
        ((BusquedaAvanzadaMovimientoFondosView) getBaseView()).setBusquedaAvanzadaView();
    }

    public void showSelectDateDialog(final String str, String str2) {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), UtilDate.getDateFormat(str2, this.mContext.getString(R.string.FORMAT_FULL_DATE), Constants.FORMAT_DATE_APP), Constants.FORMAT_DATE_APP);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                if (str.equalsIgnoreCase("FECHA_DESDE")) {
                    ((BusquedaAvanzadaMovimientoFondosView) BusquedaAvanzadaMovimientoFondosPresenter.this.getBaseView()).setFechaDesde(date);
                } else {
                    ((BusquedaAvanzadaMovimientoFondosView) BusquedaAvanzadaMovimientoFondosPresenter.this.getBaseView()).setFechaHasta(date);
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "SELECT_DATE_DIAGLO");
    }

    public void callGetMovimientos(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, String str2, String str3, String str4) {
        ((BusquedaAvanzadaMovimientoFondosView) getBaseView()).showProgressIndicator(VGetMovimientosFondo.nameService);
        this.mDataManager.getMovimientosFondo(fondoBean.getId(), str, str2, str3, str4, "N", cuentaFondosBean.getNumero());
    }

    @Subscribe
    public void onGetMovimientosFondo(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        ((BusquedaAvanzadaMovimientoFondosView) getBaseView()).dismissProgressIndicator();
        final GetMovimientosFondoEvent getMovimientosFondoEvent2 = getMovimientosFondoEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                BusquedaAvanzadaMovimientoFondosPresenter.this.onGetMovimientoFondoOk(getMovimientosFondoEvent2);
            }
        };
        r1.handleWSResponse(getMovimientosFondoEvent);
    }

    public void onGetMovimientoFondoOk(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        try {
            ((BusquedaAvanzadaMovimientoFondosView) getBaseView()).gotoMovimiento(getMovimientosFondoEvent.getResponseBean().getGetMovimientosFondoBodyResponseBean().getListaMovimientosFondosBean().getMovimientosFondosBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetMovimientoFondoOk: ", e);
            e.fillInStackTrace();
        }
    }
}
