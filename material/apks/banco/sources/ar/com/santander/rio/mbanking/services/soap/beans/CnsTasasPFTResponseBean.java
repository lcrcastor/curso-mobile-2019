package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTasasPFTBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsTasasPFTResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsTasasPFTBodyResponseBean cnsTasasPFTBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsTasasPFTResponseBean(PrivateHeaderBean privateHeaderBean, CnsTasasPFTBodyResponseBean cnsTasasPFTBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsTasasPFTBodyResponseBean = cnsTasasPFTBodyResponseBean2;
    }

    public CnsTasasPFTResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cnsTasasPFTBodyResponseBean;
    }
}
