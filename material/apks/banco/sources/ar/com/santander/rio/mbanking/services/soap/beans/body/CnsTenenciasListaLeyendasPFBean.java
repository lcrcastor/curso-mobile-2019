package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CnsTenenciasListaLeyendasPFBean {
    @SerializedName("leyenda")
    public List<CnsTenenciasLeyendaPFBean> listaLeyendasPF;

    public CnsTenenciasListaLeyendasPFBean(List<CnsTenenciasLeyendaPFBean> list) {
        this.listaLeyendasPF = list;
    }

    public String getLeyendas(String str) {
        String str2 = "";
        for (CnsTenenciasLeyendaPFBean cnsTenenciasLeyendaPFBean : this.listaLeyendasPF) {
            if (str.equals(cnsTenenciasLeyendaPFBean.subproducto)) {
                str2 = str2.concat(cnsTenenciasLeyendaPFBean.descripcion);
            }
        }
        return str2;
    }
}
