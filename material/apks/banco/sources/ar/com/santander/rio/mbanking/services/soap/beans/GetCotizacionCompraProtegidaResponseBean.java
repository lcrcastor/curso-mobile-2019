package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionCompraProtegidaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCotizacionCompraProtegidaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCotizacionCompraProtegidaBodyResponseBean getCotizacionCompraProtegidaBodyResponseBean = new GetCotizacionCompraProtegidaBodyResponseBean();
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public Object getErrorBeanObject() {
        return this.getCotizacionCompraProtegidaBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public GetCotizacionCompraProtegidaBodyResponseBean getGetCotizacionCompraProtegidaBodyResponseBean() {
        return this.getCotizacionCompraProtegidaBodyResponseBean;
    }

    public void setGetCotizacionCompraProtegidaBodyResponseBean(GetCotizacionCompraProtegidaBodyResponseBean getCotizacionCompraProtegidaBodyResponseBean2) {
        this.getCotizacionCompraProtegidaBodyResponseBean = getCotizacionCompraProtegidaBodyResponseBean2;
    }
}
