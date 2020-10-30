package ar.com.santander.rio.mbanking.app.module.creditos.model;

import java.util.List;

public class Inicio {
    String a;
    String b;
    String c;
    List<Tasas> d;

    public String getImporteMaximoPesos() {
        return this.a;
    }

    public void setImporteMaximoPesos(String str) {
        this.a = str;
    }

    public String getImporteMinimoPesos() {
        return this.b;
    }

    public void setImporteMinimoPesos(String str) {
        this.b = str;
    }

    public String getImporteMaximoCuotas() {
        return this.c;
    }

    public void setImporteMaximoCuotas(String str) {
        this.c = str;
    }

    public List<Tasas> getTasas() {
        return this.d;
    }

    public void setTasas(List<Tasas> list) {
        this.d = list;
    }
}
