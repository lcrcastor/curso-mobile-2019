package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsDescriptionResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public ConsDescriptionResponseBean() {
        this.headerBean = new HeaderBean();
        this.consDescriptionBodyResponseBean = new ConsDescriptionBodyResponseBean();
    }

    public ConsDescriptionResponseBean(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean2) {
        this.consDescriptionBodyResponseBean = consDescriptionBodyResponseBean2;
    }

    public ConsDescriptionResponseBean(HeaderBean headerBean2, ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.consDescriptionBodyResponseBean = consDescriptionBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consDescriptionBodyResponseBean;
    }
}
