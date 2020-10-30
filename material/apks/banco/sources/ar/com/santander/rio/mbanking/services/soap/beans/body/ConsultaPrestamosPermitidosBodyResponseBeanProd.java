package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamosProd;
import ar.com.santander.rio.mbanking.services.model.creditos.PrestamosPermitidosProd;
import ar.com.santander.rio.mbanking.services.model.general.ListaLeyendaProd;
import com.google.gson.annotations.SerializedName;

public class ConsultaPrestamosPermitidosBodyResponseBeanProd extends ErrorBodyBean {
    @SerializedName("datosCuenta")
    AccountRequestBean datosCuenta;
    @SerializedName("datosPrest")
    DatosPrestamosProd datosPrestamosProd;
    @SerializedName("leyenda")
    ListaLeyendaProd listaLeyendaProd;
    @SerializedName("prestPermitidos")
    PrestamosPermitidosProd prestamosPermitidosProd;

    public AccountRequestBean getDatosCuenta() {
        return this.datosCuenta;
    }

    public void setDatosCuenta(AccountRequestBean accountRequestBean) {
        this.datosCuenta = accountRequestBean;
    }

    public DatosPrestamosProd getDatosPrestamosProd() {
        return this.datosPrestamosProd;
    }

    public void setDatosPrestamosProd(DatosPrestamosProd datosPrestamosProd2) {
        this.datosPrestamosProd = datosPrestamosProd2;
    }

    public PrestamosPermitidosProd getPrestamosPermitidosProd() {
        return this.prestamosPermitidosProd;
    }

    public void setPrestamosPermitidosProd(PrestamosPermitidosProd prestamosPermitidosProd2) {
        this.prestamosPermitidosProd = prestamosPermitidosProd2;
    }

    public ListaLeyendaProd getListaLeyendaProd() {
        return this.listaLeyendaProd;
    }

    public void setListaLeyendaProd(ListaLeyendaProd listaLeyendaProd2) {
        this.listaLeyendaProd = listaLeyendaProd2;
    }
}
