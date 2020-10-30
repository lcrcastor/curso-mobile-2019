package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionCompraProtegidaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionCompraProtegidaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCotizacionCompraProtegidaBodyRequestBean getCotizacionCompraProtegidaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetCotizacionCompraProtegidaRequestBean() {
    }

    public GetCotizacionCompraProtegidaRequestBean(PrivateHeaderBean privateHeaderBean, GetCotizacionCompraProtegidaBodyRequestBean getCotizacionCompraProtegidaBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getCotizacionCompraProtegidaBodyRequestBean = getCotizacionCompraProtegidaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
