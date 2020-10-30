package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class AbmTurnoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public AbmTurnoBodyResponseBean abmTurnoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public AbmTurnoResponseBean() {
        this.abmTurnoBodyResponseBean = new AbmTurnoBodyResponseBean();
    }

    public AbmTurnoResponseBean(PrivateHeaderBean privateHeaderBean, AbmTurnoBodyResponseBean abmTurnoBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.abmTurnoBodyResponseBean = abmTurnoBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.abmTurnoBodyResponseBean;
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

    public AbmTurnoBodyResponseBean getAbmTurnoBodyResponseBean() {
        return this.abmTurnoBodyResponseBean;
    }

    public void setAbmTurnoBodyResponseBean(AbmTurnoBodyResponseBean abmTurnoBodyResponseBean2) {
        this.abmTurnoBodyResponseBean = abmTurnoBodyResponseBean2;
    }
}
