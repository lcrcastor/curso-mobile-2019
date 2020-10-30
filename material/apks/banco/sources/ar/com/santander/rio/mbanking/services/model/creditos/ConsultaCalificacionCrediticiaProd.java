package ar.com.santander.rio.mbanking.services.model.creditos;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountResponseBean;
import com.google.gson.annotations.SerializedName;

public class ConsultaCalificacionCrediticiaProd {
    @SerializedName("datosCuenta")
    public AccountResponseBean accountResponseBean;
    @SerializedName("datosCalifCred")
    public DatosCalificacionCrediticiaProd datosCalificacionCrediticiaProd;

    public AccountResponseBean getAccountResponseBean() {
        return this.accountResponseBean;
    }

    public void setAccountResponseBean(AccountResponseBean accountResponseBean2) {
        this.accountResponseBean = accountResponseBean2;
    }

    public DatosCalificacionCrediticiaProd getDatosCalificacionCrediticiaProd() {
        return this.datosCalificacionCrediticiaProd;
    }

    public void setDatosCalificacionCrediticiaProd(DatosCalificacionCrediticiaProd datosCalificacionCrediticiaProd2) {
        this.datosCalificacionCrediticiaProd = datosCalificacionCrediticiaProd2;
    }
}
