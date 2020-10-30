package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroMovilBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCotizacionSeguroMovilResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCotizacionSeguroMovilBodyResponseBean getCotizacionSeguroMovilBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCotizacionSeguroMovilResponseBean() {
        this.getCotizacionSeguroMovilBodyResponseBean = new GetCotizacionSeguroMovilBodyResponseBean();
    }

    public GetCotizacionSeguroMovilResponseBean(PrivateHeaderBean privateHeaderBean, GetCotizacionSeguroMovilBodyResponseBean getCotizacionSeguroMovilBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getCotizacionSeguroMovilBodyResponseBean = getCotizacionSeguroMovilBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getCotizacionSeguroMovilBodyResponseBean;
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

    public GetCotizacionSeguroMovilBodyResponseBean getGetCotizacionSeguroMovilBodyResponseBean() {
        return this.getCotizacionSeguroMovilBodyResponseBean;
    }

    public void setGetCotizacionSeguroMovilBodyResponseBean(GetCotizacionSeguroMovilBodyResponseBean getCotizacionSeguroMovilBodyResponseBean2) {
        this.getCotizacionSeguroMovilBodyResponseBean = getCotizacionSeguroMovilBodyResponseBean2;
    }
}
