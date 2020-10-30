package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;

public class DestinoVinculado {
    @SerializedName("destVincSecuencia")
    String destVincSecuencia;
    @SerializedName("destVincTipo")
    String destVincTipo;

    public String getDestVincSecuencia() {
        return this.destVincSecuencia;
    }

    public void setDestVincSecuencia(String str) {
        this.destVincSecuencia = str;
    }

    public String getDestVincTipo() {
        return this.destVincTipo;
    }

    public void setDestVincTipo(String str) {
        this.destVincTipo = str;
    }
}
