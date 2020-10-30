package ar.com.santander.rio.mbanking.app.module.creditos.model;

import java.util.List;

public class InicioProd {
    String a;
    String b;
    String c;
    List<TasasProd> d;

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

    public List<TasasProd> getTasas() {
        return this.d;
    }

    public void setTasas(List<TasasProd> list) {
        this.d = list;
    }
}
