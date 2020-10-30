package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaCtaOperativa {
    @SerializedName("cuentaOperativa")
    public List<CuentaOperativa> cuentaoperativa;

    public ListaCtaOperativa() {
    }

    public ListaCtaOperativa(List<CuentaOperativa> list) {
        this.cuentaoperativa = list;
    }

    public List<CuentaOperativa> getCuentaoperativa() {
        return this.cuentaoperativa;
    }

    public void setCuentaoperativa(List<CuentaOperativa> list) {
        this.cuentaoperativa = list;
    }
}
