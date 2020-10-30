package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Saldos {
    @SerializedName("saldo")
    List<Saldo> saldos;

    public Saldos(List<Saldo> list) {
        this.saldos = list;
    }

    public Saldos() {
    }

    public List<Saldo> getSaldos() {
        return this.saldos;
    }

    public void setSaldos(List<Saldo> list) {
        this.saldos = list;
    }
}
