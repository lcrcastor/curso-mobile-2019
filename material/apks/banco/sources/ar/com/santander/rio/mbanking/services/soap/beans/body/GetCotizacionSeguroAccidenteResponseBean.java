package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCotizacionSeguroAccidenteResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCotizacionSeguroAccidenteResponseBean() {
        this.getCotizacionSeguroAccidenteBodyResponseBean = new GetCotizacionSeguroAccidenteBodyResponseBean();
    }

    public GetCotizacionSeguroAccidenteResponseBean(PrivateHeaderBean privateHeaderBean, GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getCotizacionSeguroAccidenteBodyResponseBean = getCotizacionSeguroAccidenteBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getCotizacionSeguroAccidenteBodyResponseBean;
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

    public GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean() {
        return this.getCotizacionSeguroAccidenteBodyResponseBean;
    }

    public void setGetSegurosBodyResponseBean(GetCotizacionSeguroAccidenteBodyResponseBean getCotizacionSeguroAccidenteBodyResponseBean2) {
        this.getCotizacionSeguroAccidenteBodyResponseBean = getCotizacionSeguroAccidenteBodyResponseBean2;
    }
}
