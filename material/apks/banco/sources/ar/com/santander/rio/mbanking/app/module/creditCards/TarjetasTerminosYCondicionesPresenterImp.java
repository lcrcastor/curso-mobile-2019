package ar.com.santander.rio.mbanking.app.module.creditCards;

public class TarjetasTerminosYCondicionesPresenterImp implements TarjetasTerminosYCondicionesPresenter {
    public TarjetasView tarjetasView;

    public TarjetasTerminosYCondicionesPresenterImp(TarjetasView tarjetasView2) {
        this.tarjetasView = tarjetasView2;
    }

    public void onCreatePage() {
        this.tarjetasView.setTarjetasTerminosCondicionesView();
    }
}
