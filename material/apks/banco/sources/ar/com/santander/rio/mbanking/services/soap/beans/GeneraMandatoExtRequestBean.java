package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GeneraMandatoExtRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GeneraMandatoExtBodyRequestBean generaMandatoExtBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GeneraMandatoExtRequestBean() {
    }

    public GeneraMandatoExtRequestBean(HeaderBean headerBean2, GeneraMandatoExtBodyRequestBean generaMandatoExtBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.generaMandatoExtBodyRequestBean = generaMandatoExtBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
