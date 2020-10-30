package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ListaProductos {
    @SerializedName("producto")
    ArrayList<Producto> productos;

    public ArrayList<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(ArrayList<Producto> arrayList) {
        this.productos = arrayList;
    }
}
