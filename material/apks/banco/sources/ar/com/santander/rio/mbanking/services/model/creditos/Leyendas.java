package ar.com.santander.rio.mbanking.services.model.creditos;

import ar.com.santander.rio.mbanking.services.model.general.Leyenda;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Leyendas {
    @SerializedName("leyenda")
    @Expose
    private List<Leyenda> leyenda;

    public List<Leyenda> getLeyenda() {
        return this.leyenda;
    }

    public void setLeyenda(List<Leyenda> list) {
        this.leyenda = list;
    }
}
