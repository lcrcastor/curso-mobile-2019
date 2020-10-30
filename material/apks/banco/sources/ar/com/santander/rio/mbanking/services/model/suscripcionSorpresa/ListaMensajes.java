package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ListaMensajes {
    @SerializedName("mensaje")
    ArrayList<Mensaje> listaMensajes;

    public ArrayList<Mensaje> getListaMensajes() {
        return this.listaMensajes;
    }

    public void setListaMensajes(ArrayList<Mensaje> arrayList) {
        this.listaMensajes = arrayList;
    }
}
