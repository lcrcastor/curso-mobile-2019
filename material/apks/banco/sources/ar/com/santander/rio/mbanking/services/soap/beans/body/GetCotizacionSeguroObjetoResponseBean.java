package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCotizacionSeguroObjetoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCotizacionSeguroObjetoResponseBean() {
        this.getCotizacionSeguroObjetoBodyResponseBean = new GetCotizacionSeguroObjetoBodyResponseBean();
    }

    public GetCotizacionSeguroObjetoResponseBean(PrivateHeaderBean privateHeaderBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getCotizacionSeguroObjetoBodyResponseBean = getCotizacionSeguroObjetoBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getCotizacionSeguroObjetoBodyResponseBean;
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

    public GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean() {
        return this.getCotizacionSeguroObjetoBodyResponseBean;
    }

    public void setGetCotizacionSeguroObjetoResponseBean(GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean2) {
        this.getCotizacionSeguroObjetoBodyResponseBean = getCotizacionSeguroObjetoBodyResponseBean2;
    }
}
