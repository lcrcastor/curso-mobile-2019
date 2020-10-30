package ar.com.santander.rio.mbanking.app.module.creditCards;

import java.util.HashMap;

public class TarjetasDetalleConsumoPresenterImp implements TarjetasDetalleConsumoPresenter {
    public TarjetasView tarjetasView;

    public TarjetasDetalleConsumoPresenterImp(TarjetasView tarjetasView2) {
        this.tarjetasView = tarjetasView2;
    }

    public void onCreatePage(String str, HashMap<String, String> hashMap) {
        this.tarjetasView.setTarjetasDetalleConsumoView(str, hashMap);
    }
}
