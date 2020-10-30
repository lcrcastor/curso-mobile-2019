package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Cuentas {
    @SerializedName("cuenta")
    List<Cuenta> cuentas;
    @SerializedName("cuentaBP")
    List<Cuenta> cuentasBP;
    @SerializedName("cuentaTitulo")
    List<Cuenta> cuentasTitulo;
    @SerializedName("formaMoneda")
    private String formaMoneda;

    public List<Cuenta> getCuentas() {
        return this.cuentas;
    }

    public void setCuentas(List<Cuenta> list) {
        this.cuentas = list;
    }

    public List<Cuenta> getCuentasTitulo() {
        return this.cuentasTitulo;
    }

    public void setCuentasTitulo(List<Cuenta> list) {
        this.cuentasTitulo = list;
    }

    public List<Cuenta> getCuentasBP() {
        return this.cuentasBP;
    }

    public void setCuentasBP(List<Cuenta> list) {
        this.cuentasBP = list;
    }

    public String getFormaMoneda() {
        return this.formaMoneda;
    }

    public void setFormaMoneda(String str) {
        this.formaMoneda = str;
    }
}
