package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CnsTasasPFTListaPlazoFijoBean {
    @SerializedName("plazoFijo")
    public List<CnsTasasPFTPlazoFijoBean> plazoFijo;

    public CnsTasasPFTListaPlazoFijoBean() {
        this.plazoFijo = new ArrayList();
    }

    public CnsTasasPFTListaPlazoFijoBean(List<CnsTasasPFTPlazoFijoBean> list) {
        this.plazoFijo = list;
    }

    public List<CnsTasasPFTPlazoFijoBean> getLstPlazoFijo() {
        return this.plazoFijo;
    }

    public void setLstPlazoFijo(List<CnsTasasPFTPlazoFijoBean> list) {
        this.plazoFijo = list;
    }
}
