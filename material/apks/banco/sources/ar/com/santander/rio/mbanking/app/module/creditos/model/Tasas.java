package ar.com.santander.rio.mbanking.app.module.creditos.model;

public class Tasas {
    String a;
    String b;
    String c;
    String d;

    public String getTipo() {
        return this.a;
    }

    public void setTipo(String str) {
        this.a = str;
    }

    public String getTasaNominalAnual() {
        return this.b;
    }

    public void setTasaNominalAnual(String str) {
        this.b = str;
    }

    public String getCuotas() {
        return this.c;
    }

    public void setCuotas(String str) {
        this.c = str;
    }

    public String getLeyenda() {
        return this.d;
    }

    public void setLeyenda(String str) {
        this.d = str;
    }
}
