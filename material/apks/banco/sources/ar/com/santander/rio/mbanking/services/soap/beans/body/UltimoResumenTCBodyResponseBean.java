package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.TarjetaResumen;
import com.google.gson.annotations.SerializedName;

public class UltimoResumenTCBodyResponseBean extends ErrorBodyBean {
    @SerializedName("tarjeta")
    private TarjetaResumen tarjeta;

    public UltimoResumenTCBodyResponseBean(TarjetaResumen tarjetaResumen) {
        this.tarjeta = tarjetaResumen;
    }

    public UltimoResumenTCBodyResponseBean(String str, TarjetaResumen tarjetaResumen) {
        super(str);
        this.tarjeta = tarjetaResumen;
    }

    public UltimoResumenTCBodyResponseBean(String str, String str2, String str3, String str4, String str5, TarjetaResumen tarjetaResumen) {
        super(str, str2, str3, str4, str5);
        this.tarjeta = tarjetaResumen;
    }

    public UltimoResumenTCBodyResponseBean() {
    }

    public TarjetaResumen getTarjeta() {
        return this.tarjeta;
    }

    public void setTarjeta(TarjetaResumen tarjetaResumen) {
        this.tarjeta = tarjetaResumen;
    }
}
