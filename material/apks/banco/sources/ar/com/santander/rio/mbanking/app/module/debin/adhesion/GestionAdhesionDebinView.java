package ar.com.santander.rio.mbanking.app.module.debin.adhesion;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;

public interface GestionAdhesionDebinView extends IBaseView {
    void gotoConfirmarGestionCuenta();

    void setCuentasAdheridas(ListaCuentasVendedorBean listaCuentasVendedorBean);

    void setListaCuentasView();
}
