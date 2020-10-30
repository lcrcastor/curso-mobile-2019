package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail.TitulosValoresDetailContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail.TitulosValoresDetailContract.View;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Titulo;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.List;

public class TitulosValoresDetailPresenter extends BasePresenter<View> implements Presenter {
    public TitulosValoresDetailPresenter(Bus bus) {
        super(bus);
    }

    public TitulosValoresDetailPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void getTitulo(Titulo titulo) {
        ((View) this.mBaseView).setHeaderData(titulo.getCodEspecie(), titulo.getDescripcion(), titulo.getTenValHoy());
        a(titulo);
    }

    private void a(Titulo titulo) {
        List valuesAsList = titulo.getValuesAsList();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        TitulosValoresViewElements[] values = TitulosValoresViewElements.values();
        for (int i = 0; i < valuesAsList.size(); i++) {
            if (!TextUtils.isEmpty((CharSequence) valuesAsList.get(i))) {
                arrayList.add(valuesAsList.get(i));
                arrayList2.add(values[i]);
            }
        }
        ((View) this.mBaseView).setRecyclerViewData(arrayList, arrayList2);
    }

    public void attachView(View view) {
        this.mBaseView = view;
    }

    public void detachView() {
        ((View) this.mBaseView).detachView();
    }
}
