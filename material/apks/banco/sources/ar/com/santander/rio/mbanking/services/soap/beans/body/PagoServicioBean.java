package ar.com.santander.rio.mbanking.services.soap.beans.body;

import java.util.ArrayList;

public class PagoServicioBean {
    public String empresa;
    public String fechavto;
    public String formato;
    public String importe;

    public PagoServicioBean() {
    }

    public PagoServicioBean(String str, String str2, String str3, String str4) {
        this.fechavto = str;
        this.empresa = str2;
        this.importe = str3;
        this.formato = str4;
    }

    public static ArrayList<PagoServicioBean> registrospagoservicio() {
        ArrayList<PagoServicioBean> arrayList = new ArrayList<>();
        arrayList.add(new PagoServicioBean("20/12", "Edesur Editable", "$200", "editable"));
        arrayList.add(new PagoServicioBean("20/12", "Edesur No Editable", "$200", "no_editable"));
        arrayList.add(new PagoServicioBean("20/12", "Edesur Afip", "$200", "afip"));
        return arrayList;
    }
}
