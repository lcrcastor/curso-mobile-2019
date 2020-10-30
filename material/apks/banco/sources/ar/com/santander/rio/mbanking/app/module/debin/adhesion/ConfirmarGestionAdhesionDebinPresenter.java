package ar.com.santander.rio.mbanking.app.module.debin.adhesion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.AbmAdhesionVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VAbmAdhesionVendedorDebin;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarGestionAdhesionDebinPresenter extends BasePresenter<ConfirmarGestionAdhesionDebinView> {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public Integer b;

    public ConfirmarGestionAdhesionDebinPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage() {
        ((ConfirmarGestionAdhesionDebinView) getBaseView()).setConfirmarGestionCuentaView();
    }

    public void callAbmAdhesionVendedor(final String str, final CuentaVendedor cuentaVendedor) {
        String string;
        String string2;
        if (str.equalsIgnoreCase("A")) {
            string = this.a.getString(R.string.ID_4447_DEBIN_CONFIRMARADHESIONDECUENTATITLE_POPUP);
            string2 = this.a.getString(R.string.ID_XXXX_DEBIN_CONFIRMARADHESIONDECUENTA_POPUP);
        } else {
            string = this.a.getString(R.string.ID_4447_DEBIN_CONFIRMARADHESIONDECUENTATITLE_POPUP);
            string2 = this.a.getString(R.string.ID_XXXX_DEBIN_CONFIRMARDESVINCULACIONDECUENTA_POPUP);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(string, string2, null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ((ConfirmarGestionAdhesionDebinView) ConfirmarGestionAdhesionDebinPresenter.this.getBaseView()).showProgressIndicator(VAbmAdhesionVendedorDebin.nameService);
                ConfirmarGestionAdhesionDebinPresenter.this.b = cuentaVendedor.getStatusAdhesion();
                cuentaVendedor.setStatusAdhesion(null);
                if (cuentaVendedor.getSucursal().length() > 3) {
                    cuentaVendedor.setSucursal(cuentaVendedor.getSucursal().substring(cuentaVendedor.getSucursal().length() - 3));
                }
                if (cuentaVendedor.getNumero().length() > 7) {
                    cuentaVendedor.setNumero(cuentaVendedor.getNumero().substring(cuentaVendedor.getNumero().length() - 7));
                }
                if (str.equalsIgnoreCase("A")) {
                    ConfirmarGestionAdhesionDebinPresenter.this.mDataManager.abmAdhesionVendedor(str, cuentaVendedor, (BaseActivity) ConfirmarGestionAdhesionDebinPresenter.this.a, true);
                } else if (str.equalsIgnoreCase("B")) {
                    ConfirmarGestionAdhesionDebinPresenter.this.mDataManager.abmAdhesionVendedor(str, cuentaVendedor, (BaseActivity) ConfirmarGestionAdhesionDebinPresenter.this.a, false);
                }
            }

            public void onNegativeButton() {
                if (str.equalsIgnoreCase("A")) {
                    ((ConfirmarGestionAdhesionDebinView) ConfirmarGestionAdhesionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(ConfirmarGestionAdhesionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), ConfirmarGestionAdhesionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), ConfirmarGestionAdhesionDebinPresenter.this.a.getString(R.string.f223analytics_trackevent_label_Cancelar_adhesin_cuenta));
                } else {
                    ((ConfirmarGestionAdhesionDebinView) ConfirmarGestionAdhesionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(ConfirmarGestionAdhesionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), ConfirmarGestionAdhesionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), ConfirmarGestionAdhesionDebinPresenter.this.a.getString(R.string.f225analytics_trackevent_label_Cancelar_desvinculacin_cuenta));
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
    }

    @Subscribe
    public void onAbmAdhesionVendedorDebin(AbmAdhesionVendedorEvent abmAdhesionVendedorEvent) {
        ((ConfirmarGestionAdhesionDebinView) getBaseView()).setStatusACuenta(this.b);
        ((ConfirmarGestionAdhesionDebinView) getBaseView()).dismissProgressIndicator();
        final AbmAdhesionVendedorEvent abmAdhesionVendedorEvent2 = abmAdhesionVendedorEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ConfirmarGestionAdhesionDebinPresenter.this.a(abmAdhesionVendedorEvent2);
            }
        };
        r1.handleWSResponse(abmAdhesionVendedorEvent);
    }

    /* access modifiers changed from: private */
    public void a(AbmAdhesionVendedorEvent abmAdhesionVendedorEvent) {
        try {
            ((ConfirmarGestionAdhesionDebinView) getBaseView()).gotoComprobanteGestionAdhesionDebinView(abmAdhesionVendedorEvent.getResponse().getAbmAdhesionVendedorBodyResponseBean(), this.b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
