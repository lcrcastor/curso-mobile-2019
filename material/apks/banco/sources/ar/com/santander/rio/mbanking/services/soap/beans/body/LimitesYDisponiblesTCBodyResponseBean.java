package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.TarjetaLimites;
import com.google.gson.annotations.SerializedName;

public class LimitesYDisponiblesTCBodyResponseBean extends ErrorBodyBean {
    @SerializedName("tarjeta")
    TarjetaLimites tarjeta;

    public LimitesYDisponiblesTCBodyResponseBean(TarjetaLimites tarjetaLimites) {
        this.tarjeta = tarjetaLimites;
    }

    public LimitesYDisponiblesTCBodyResponseBean(String str, TarjetaLimites tarjetaLimites) {
        super(str);
        this.tarjeta = tarjetaLimites;
    }

    public LimitesYDisponiblesTCBodyResponseBean(String str, String str2, String str3, String str4, String str5, TarjetaLimites tarjetaLimites) {
        super(str, str2, str3, str4, str5);
        this.tarjeta = tarjetaLimites;
    }

    public LimitesYDisponiblesTCBodyResponseBean() {
    }

    public TarjetaLimites getTarjetas() {
        return this.tarjeta;
    }

    public void setTarjetas(TarjetaLimites tarjetaLimites) {
        this.tarjeta = tarjetaLimites;
    }
}
