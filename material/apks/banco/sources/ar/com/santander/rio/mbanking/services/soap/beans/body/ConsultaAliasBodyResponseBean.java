package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import com.google.gson.annotations.SerializedName;

public class ConsultaAliasBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cuenta")
    private Cuenta cuenta;

    public ConsultaAliasBodyResponseBean() {
    }

    public ConsultaAliasBodyResponseBean(Cuenta cuenta2) {
        this.cuenta = cuenta2;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta2) {
        this.cuenta = cuenta2;
    }
}
