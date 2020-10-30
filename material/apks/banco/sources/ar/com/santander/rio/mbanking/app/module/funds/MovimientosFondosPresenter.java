package ar.com.santander.rio.mbanking.app.module.funds;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetMovimientosFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetMovimientosFondo;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class MovimientosFondosPresenter extends BasePresenter<MovimientosFondoView> {
    public MovimientosFondosPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(List<MovimientoFondosBean> list, String str) {
        ((MovimientosFondoView) getBaseView()).setMovimientosFondoView(list, str);
    }

    public void showSelectSearchDialog(final FondoBean fondoBean, final CuentaFondosBean cuentaFondosBean) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(((MovimientosFondoView) getBaseView()).getContext().getString(R.string.F24_06_LBL_POPUP_MOVIMIENTOS_ULTIMA_SEMANA));
        arrayList.add(((MovimientosFondoView) getBaseView()).getContext().getString(R.string.F24_06_LBL_POPUP_BUSQUEDA_AVANZADA));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, ((MovimientosFondoView) getBaseView()).getContext().getString(R.string.F24_TXT_DIALOG_CANCELAR), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.F24_06_LBL_POPUP_MOVIMIENTOS_ULTIMA_SEMANA))) {
                    MovimientosFondosPresenter.this.onGetMovimiento(fondoBean, cuentaFondosBean);
                    ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_buscar_movimientos), ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_ultimos_7dias));
                } else if (str.equalsIgnoreCase(((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.F24_06_LBL_POPUP_BUSQUEDA_AVANZADA))) {
                    ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).gotoBusquedaAvanzada();
                    ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_busqueda_avanzada), ((MovimientosFondoView) MovimientosFondosPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_importe));
                }
            }
        });
        newInstance.setCancelable(true);
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "SelectSearchDialog");
    }

    public void onGetMovimiento(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean) {
        ((MovimientosFondoView) getBaseView()).showProgressIndicator(VGetMovimientosFondo.nameService);
        this.mDataManager.getMovimientosFondo(fondoBean.getId(), cuentaFondosBean.getNumero(), Boolean.valueOf(true));
    }

    @Subscribe
    public void onGetMovimientosFondo(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        ((MovimientosFondoView) getBaseView()).dismissProgressIndicator();
        final GetMovimientosFondoEvent getMovimientosFondoEvent2 = getMovimientosFondoEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(((MovimientosFondoView) getBaseView()).getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) ((MovimientosFondoView) getBaseView()).getContext()) {
            public void onOk() {
                MovimientosFondosPresenter.this.onGetMovimientoFondoOk(getMovimientosFondoEvent2);
            }
        };
        r1.handleWSResponse(getMovimientosFondoEvent);
    }

    public void onGetMovimientoFondoOk(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        try {
            onCreatePage(getMovimientosFondoEvent.getResponseBean().getGetMovimientosFondoBodyResponseBean().getListaMovimientosFondosBean().getMovimientosFondosBean(), FondosConstants.ORIGEN_BUSQUEDA_AVANZADA);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetMovimientoFondoOk: ", e);
        }
    }
}
