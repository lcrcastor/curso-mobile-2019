package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Notificacion {
    @SerializedName("codAlerta")
    public String codAlerta;
    @SerializedName("fechaAlerta")
    public String fechaAlerta;
    @SerializedName("mensaje")
    public String mensaje;
    @SerializedName("titulo")
    public String titulo;
}
