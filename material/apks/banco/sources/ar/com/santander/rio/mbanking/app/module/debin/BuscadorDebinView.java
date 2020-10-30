package ar.com.santander.rio.mbanking.app.module.debin;

import android.app.FragmentManager;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import java.util.ArrayList;

public interface BuscadorDebinView extends IBaseView {
    FragmentManager getFragmentManager();

    void setBackgroundVisibleInit();

    void setBuscadorView();

    void setResultados(ArrayList<ListDebinesBean> arrayList, String str, String str2);
}
