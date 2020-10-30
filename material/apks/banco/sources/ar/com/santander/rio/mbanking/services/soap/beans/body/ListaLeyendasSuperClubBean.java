package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ListaLeyendasSuperClubBean {
    @SerializedName("leyenda")
    public ArrayList<LeyendaSuperClubBean> leyenda;

    public ListaLeyendasSuperClubBean() {
        this.leyenda = new ArrayList<>();
    }

    public ListaLeyendasSuperClubBean(ArrayList<LeyendaSuperClubBean> arrayList) {
        this.leyenda = arrayList;
    }
}
