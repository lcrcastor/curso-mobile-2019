package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ListaSuscripciones {
    @SerializedName("suscripcion")
    ArrayList<Suscripcion> listaSuscripciones;

    public ArrayList<Suscripcion> getListaSuscripciones() {
        return this.listaSuscripciones;
    }

    public void setListaSuscripciones(ArrayList<Suscripcion> arrayList) {
        this.listaSuscripciones = arrayList;
    }
}
