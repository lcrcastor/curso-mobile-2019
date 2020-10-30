package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetProximasCuotasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetProximasCuotasResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public GetProximasCuotasBodyResponseBean mGetProximasCuotasBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetProximasCuotasResponseBean(PrivateHeaderBean privateHeaderBean, GetProximasCuotasBodyResponseBean getProximasCuotasBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.mGetProximasCuotasBodyResponseBean = getProximasCuotasBodyResponseBean;
    }

    public GetProximasCuotasResponseBean() {
        this.mGetProximasCuotasBodyResponseBean = new GetProximasCuotasBodyResponseBean();
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.mGetProximasCuotasBodyResponseBean;
    }

    public GetProximasCuotasBodyResponseBean getGetCategoriasBodyResponseBean() {
        return this.mGetProximasCuotasBodyResponseBean;
    }

    public void setmGetProximasCuotasBodyResponseBean(GetProximasCuotasBodyResponseBean getProximasCuotasBodyResponseBean) {
        this.mGetProximasCuotasBodyResponseBean = getProximasCuotasBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
