package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaLeyendaProd {
    @SerializedName("leyenda")
    public List<Leyenda> leyendaList;

    public List<Leyenda> getLeyendaList() {
        return this.leyendaList;
    }

    public void setLeyendaList(List<Leyenda> list) {
        this.leyendaList = list;
    }
}
