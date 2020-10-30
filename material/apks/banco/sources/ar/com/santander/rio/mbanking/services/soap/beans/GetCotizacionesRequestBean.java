package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetCotizacionesBodyRequestBean getCotizacionesBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetCotizacionesRequestBean(PrivateHeaderBean privateHeaderBean, GetCotizacionesBodyRequestBean getCotizacionesBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getCotizacionesBodyRequestBean = getCotizacionesBodyRequestBean2;
    }

    public GetCotizacionesRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
