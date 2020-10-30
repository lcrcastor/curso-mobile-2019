package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaPrestamosPermitidosBodyRequestBean {
    @SerializedName("datosCuenta")
    AccountRequestBean datosCuenta;

    public AccountRequestBean getDatosCuenta() {
        return this.datosCuenta;
    }

    public void setDatosCuenta(AccountRequestBean accountRequestBean) {
        this.datosCuenta = accountRequestBean;
    }
}
