package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Limites {
    @SerializedName("limite")
    private List<Limite> limites;

    public Limites(List<Limite> list) {
        this.limites = list;
    }

    public Limites() {
    }

    public List<Limite> getLimites() {
        return this.limites;
    }

    public void setLimites(List<Limite> list) {
        this.limites = list;
    }
}
