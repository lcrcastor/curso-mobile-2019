package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetCotizacionesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCotizacionesBodyResponseBean getCotizacionesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetCotizacionesResponseBean(GetCotizacionesBodyResponseBean getCotizacionesBodyResponseBean2) {
        this.getCotizacionesBodyResponseBean = getCotizacionesBodyResponseBean2;
    }

    public GetCotizacionesResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getCotizacionesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetCotizacionesBodyResponseBean getGetCotizacionesBodyResponseBean() {
        return this.getCotizacionesBodyResponseBean;
    }

    public void setGetCotizacionesBodyResponseBean(GetCotizacionesBodyResponseBean getCotizacionesBodyResponseBean2) {
        this.getCotizacionesBodyResponseBean = getCotizacionesBodyResponseBean2;
    }
}
