package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsDescriptionRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsDescriptionBodyRequestBean consDescriptionBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public ConsDescriptionRequestBean() {
        this.headerBean = new HeaderBean();
        this.consDescriptionBodyRequestBean = new ConsDescriptionBodyRequestBean();
    }

    public ConsDescriptionRequestBean(ConsDescriptionBodyRequestBean consDescriptionBodyRequestBean2) {
        this.consDescriptionBodyRequestBean = consDescriptionBodyRequestBean2;
    }

    public ConsDescriptionRequestBean(HeaderBean headerBean2, ConsDescriptionBodyRequestBean consDescriptionBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.consDescriptionBodyRequestBean = consDescriptionBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
