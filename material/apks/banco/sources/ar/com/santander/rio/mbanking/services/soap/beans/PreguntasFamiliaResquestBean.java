package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntasFamiliaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class PreguntasFamiliaResquestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean header;
    @SerializedName("body")
    public PreguntasFamiliaBodyRequestBean preguntasFamiliaBodyRequestBean;

    public PreguntasFamiliaResquestBean() {
    }

    public PreguntasFamiliaResquestBean(PrivateHeaderBean privateHeaderBean, PreguntasFamiliaBodyRequestBean preguntasFamiliaBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.preguntasFamiliaBodyRequestBean = preguntasFamiliaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
