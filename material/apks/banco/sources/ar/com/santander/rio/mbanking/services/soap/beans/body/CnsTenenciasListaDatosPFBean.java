package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CnsTenenciasListaDatosPFBean {
    @SerializedName("datosPF")
    public List<CnsTenenciasDatosPFBean> lstCnsTenencias;

    public CnsTenenciasListaDatosPFBean() {
        this.lstCnsTenencias = new ArrayList();
    }

    public CnsTenenciasListaDatosPFBean(List<CnsTenenciasDatosPFBean> list) {
        this.lstCnsTenencias = list;
    }

    public List<CnsTenenciasDatosPFBean> getLstCnsTenencias() {
        return this.lstCnsTenencias;
    }

    public void setLstCnsTenencias(List<CnsTenenciasDatosPFBean> list) {
        this.lstCnsTenencias = list;
    }
}
