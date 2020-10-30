package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTasasPFTBodyResponseBean extends ErrorBodyBean {
    @SerializedName("listaPlazos")
    public CnsTasasPFTListaPlazoFijoBean listaPlazos;

    public CnsTasasPFTBodyResponseBean(CnsTasasPFTListaPlazoFijoBean cnsTasasPFTListaPlazoFijoBean) {
        this.listaPlazos = cnsTasasPFTListaPlazoFijoBean;
    }
}
