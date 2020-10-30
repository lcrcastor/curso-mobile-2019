package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.Iterator;

public class SeleccionarFondoPresenter extends BasePresenter<SeleccionarFondoView> {
    protected Context mContext;

    public SeleccionarFondoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public Context getContext() {
        return ((SeleccionarFondoView) getBaseView()).getContext();
    }

    public void onFundSelected(FondoBean fondoBean) {
        ((SeleccionarFondoView) getBaseView()).gotoNextFlow(fondoBean);
    }

    public void showSelectAccountDialog(CuentaFondosBean cuentaFondosBean, final ArrayList<CuentaFondosBean> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CuentaFondosBean cuentaFondosBean2 = (CuentaFondosBean) it.next();
            StringBuilder sb = new StringBuilder();
            sb.append(getContext().getString(R.string.TITLE_ACCOUNT));
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(UtilAccount.formatCuentaTitulo(cuentaFondosBean2.getNumero()));
            arrayList2.add(sb.toString());
            try {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getContext().getString(R.string.TITLE_ACCOUNT));
                sb2.append(UtilsCuentas.SEPARAOR2);
                sb2.append(new CAccessibility(getContext()).applyFilterCharacterToCharacter(UtilAccount.formatCuentaTitulo(cuentaFondosBean2.getNumero())));
                arrayList3.add(sb2.toString());
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
                    sb.append(SeleccionarFondoPresenter.this.getContext().getString(R.string.TITLE_ACCOUNT));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(UtilAccount.formatCuentaTitulo(cuentaFondosBean.getNumero()));
                    if (str.equalsIgnoreCase(sb.toString())) {
                        ((SeleccionarFondoView) SeleccionarFondoPresenter.this.getBaseView()).setSelectedAccount(cuentaFondosBean);
                        return;
                    }
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popupSelectAccount");
    }
}
