package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionSeguroObjetoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetCotizacionSeguroObjetoBodyRequestBean getCotizacionSeguroObjetoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetCotizacionSeguroObjetoRequestBean() {
    }

    public GetCotizacionSeguroObjetoRequestBean(PrivateHeaderBean privateHeaderBean, GetCotizacionSeguroObjetoBodyRequestBean getCotizacionSeguroObjetoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getCotizacionSeguroObjetoBodyRequestBean = getCotizacionSeguroObjetoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
