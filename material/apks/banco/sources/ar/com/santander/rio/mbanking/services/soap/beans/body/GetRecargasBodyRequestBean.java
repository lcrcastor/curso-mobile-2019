package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetRecargasBodyRequestBean {
    @SerializedName("fechaNacimiento")
    private String fechaNacimiento;
    @SerializedName("nroDocumento")
    private String nroDocumento;
    @SerializedName("tipoRecarga")
    private String tipoRecarga;

    public GetRecargasBodyRequestBean(String str, String str2, String str3) {
        this.tipoRecarga = str;
        this.nroDocumento = str2;
        this.fechaNacimiento = str3;
    }
}
