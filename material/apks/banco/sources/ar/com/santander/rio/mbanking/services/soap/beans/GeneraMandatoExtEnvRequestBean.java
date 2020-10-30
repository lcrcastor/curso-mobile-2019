package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GeneraMandatoExtEnvRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GeneraMandatoEnvBodyRequestBean generaMandatoEnvBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GeneraMandatoExtEnvRequestBean() {
    }

    public GeneraMandatoExtEnvRequestBean(HeaderBean headerBean2, GeneraMandatoEnvBodyRequestBean generaMandatoEnvBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.generaMandatoEnvBodyRequestBean = generaMandatoEnvBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
