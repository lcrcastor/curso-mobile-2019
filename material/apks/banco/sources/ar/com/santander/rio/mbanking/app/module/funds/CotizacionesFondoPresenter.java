package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetDetalleFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleFondo;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class CotizacionesFondoPresenter extends BasePresenter<CotizacionesFondoView> {
    /* access modifiers changed from: private */
    public Context a;
    private FondoBean b;

    public CotizacionesFondoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void callGetDetalleFondo(FondoBean fondoBean) {
        this.b = fondoBean;
        ((CotizacionesFondoView) getBaseView()).showProgressIndicator(VGetDetalleFondo.nameService);
        this.mDataManager.getDetalleFondo(fondoBean.getId());
    }

    @Subscribe
    public void getDetalleFondo(GetDetalleFondoEvent getDetalleFondoEvent) {
        ((CotizacionesFondoView) getBaseView()).dismissProgressIndicator();
        final GetDetalleFondoEvent getDetalleFondoEvent2 = getDetalleFondoEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.a) {
            public void onOk() {
                CotizacionesFondoPresenter.this.a(getDetalleFondoEvent2);
            }
        };
        r1.handleWSResponse(getDetalleFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetDetalleFondoEvent getDetalleFondoEvent) {
        try {
            ((CotizacionesFondoView) getBaseView()).gotoInformacionFondo(((GetDetalleFondoResponseBean) getDetalleFondoEvent.getBeanResponse()).getGetDetalleFondoBodyResponseBean().getInformacionFondo(), this.b);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsdetCtasIndResultOk: ", e);
        }
    }

    public void showValoresDialog(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_CUOTA));
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMO_DIA));
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMOS_30_DIAS));
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMOS_90_DIAS));
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMO_ANO));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, this.a.getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, str, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_CUOTA))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesValorCuota();
                } else if (str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMO_DIA))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesUltimoDia();
                } else if (str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMOS_30_DIAS))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesUltimos30();
                } else if (str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMOS_90_DIAS))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesUltimos90();
                } else if (str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMO_ANO))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesUltimoAno();
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "showValoresDialog");
    }

    public void showTiposFondosDialog(String str, ArrayList<String> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList2.add((String) arrayList.get(i));
            }
        }
        if (str.equalsIgnoreCase(this.a.getString(R.string.F24_13_SELECTOR_CAMBIAR_TIPO_TODOS_TIPOS))) {
            str = this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_TIPO_TODOS_TIPOS);
        }
        ArrayList arrayList3 = arrayList2;
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, null, arrayList3, this.a.getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, str, arrayList2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_TIPO_TODOS)) || str.equalsIgnoreCase(CotizacionesFondoPresenter.this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_TIPO_TODOS_TIPOS))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesTodosTipos();
                } else {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).displayCotizacionesFiltro(str);
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "showTiposFondosDialog");
    }

    public void showOrdenarListaDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_MAYOR_VARIACION));
        arrayList.add(this.a.getResources().getString(R.string.F24_13_SELECTOR_CAMBIAR_MENOR_VARIACION));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, this.a.getString(R.string.F24_00_ACTIONSHEET_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equals(CotizacionesFondoPresenter.this.a.getString(R.string.F24_13_SELECTOR_CAMBIAR_MAYOR_VARIACION))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).sortCotizacionesDescendente();
                } else if (str.equals(CotizacionesFondoPresenter.this.a.getString(R.string.F24_13_SELECTOR_CAMBIAR_MENOR_VARIACION))) {
                    ((CotizacionesFondoView) CotizacionesFondoPresenter.this.getBaseView()).sortCotizacionesAscendente();
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "showOrdenarListaDialog");
    }
}
