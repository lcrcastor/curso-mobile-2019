package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import com.google.gson.annotations.SerializedName;

public class Cliente {
    @SerializedName("destinos")
    Destinos destinos;
    @SerializedName("Estado")
    String estado;
}
