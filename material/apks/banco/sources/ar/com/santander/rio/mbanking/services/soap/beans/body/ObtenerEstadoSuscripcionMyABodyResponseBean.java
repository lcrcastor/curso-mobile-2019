package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.Cliente;
import com.google.gson.annotations.SerializedName;

public class ObtenerEstadoSuscripcionMyABodyResponseBean extends ErrorBodyBean {
    @SerializedName("cliente")
    Cliente cliente;
}
