package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class CancelaMandatoExtEnvRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public CancelaMandatoExtEnvBodyRequestBean cancelaMandatoExtEnvBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public CancelaMandatoExtEnvRequestBean() {
    }

    public CancelaMandatoExtEnvRequestBean(HeaderBean headerBean2, CancelaMandatoExtEnvBodyRequestBean cancelaMandatoExtEnvBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.cancelaMandatoExtEnvBodyRequestBean = cancelaMandatoExtEnvBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
