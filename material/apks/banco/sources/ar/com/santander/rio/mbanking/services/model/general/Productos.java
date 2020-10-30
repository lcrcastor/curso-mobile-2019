package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Productos {
    @SerializedName("cuentas")
    Cuentas cuentas;
    @SerializedName("cuentasBP")
    Cuentas cuentasBP;
    @SerializedName("cuentasTitulo")
    Cuentas cuentasTitulo;
    @SerializedName("prestamos")
    Prestamos prestamos;
    @SerializedName("tarjetas")
    Tarjetas tarjetas;
    @SerializedName("tarjetasDebito")
    TarjetasDebito tarjetasDebito;

    public Cuentas getCuentas() {
        return this.cuentas;
    }

    public void setCuentas(Cuentas cuentas2) {
        this.cuentas = cuentas2;
    }

    public Tarjetas getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(Tarjetas tarjetas2) {
        this.tarjetas = tarjetas2;
    }

    public TarjetasDebito getTarjetasDebito() {
        return this.tarjetasDebito;
    }

    public void setTarjetasDebito(TarjetasDebito tarjetasDebito2) {
        this.tarjetasDebito = tarjetasDebito2;
    }

    public Cuentas getCuentasTitulo() {
        return this.cuentasTitulo;
    }

    public void setCuentasTitulo(Cuentas cuentas2) {
        this.cuentasTitulo = cuentas2;
    }

    public Prestamos getPrestamos() {
        return this.prestamos;
    }

    public void setPrestamos(Prestamos prestamos2) {
        this.prestamos = prestamos2;
    }

    public Cuentas getCuentasBP() {
        return this.cuentasBP;
    }

    public void setCuentasBP(Cuentas cuentas2) {
        this.cuentasBP = cuentas2;
    }
}
