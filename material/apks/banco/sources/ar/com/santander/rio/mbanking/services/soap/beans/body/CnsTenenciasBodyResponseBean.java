package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTenenciasBodyResponseBean extends ErrorBodyBean {
    @SerializedName("leyendasTenencia")
    public CnsTenenciasListaLeyendasPFBean leyendasTenencia;
    @SerializedName("listaPlazoFijo")
    public CnsTenenciasListaDatosPFBean listaPlazoFijo;

    public CnsTenenciasBodyResponseBean(CnsTenenciasListaDatosPFBean cnsTenenciasListaDatosPFBean, CnsTenenciasListaLeyendasPFBean cnsTenenciasListaLeyendasPFBean) {
        this.listaPlazoFijo = cnsTenenciasListaDatosPFBean;
        this.leyendasTenencia = cnsTenenciasListaLeyendasPFBean;
    }
}
