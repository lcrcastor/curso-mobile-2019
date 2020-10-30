package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class DatosIniciales {
    @SerializedName("documento")
    public String documento;
    @SerializedName("fechaNac")
    public String fechaNac;

    public DatosIniciales(String str, String str2) {
        this.documento = str;
        this.fechaNac = str2;
    }
}
