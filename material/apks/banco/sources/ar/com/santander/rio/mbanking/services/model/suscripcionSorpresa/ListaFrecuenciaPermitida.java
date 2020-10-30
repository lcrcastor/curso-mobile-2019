package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ListaFrecuenciaPermitida {
    @SerializedName("FrecuenciaPermitida")
    ArrayList<FrecuenciaPermitida> listaFrecuenciaPermitida;

    public ArrayList<FrecuenciaPermitida> getListaFrecuenciaPermitida() {
        return this.listaFrecuenciaPermitida;
    }

    public void setListaFrecuenciaPermitida(ArrayList<FrecuenciaPermitida> arrayList) {
        this.listaFrecuenciaPermitida = arrayList;
    }
}
