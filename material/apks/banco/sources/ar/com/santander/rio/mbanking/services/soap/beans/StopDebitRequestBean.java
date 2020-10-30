package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.StopDebitBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class StopDebitRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public StopDebitBodyRequestBean stopDebitBodyRequestBean;

    public StopDebitRequestBean() {
        this.headerBean = new HeaderBean();
        this.stopDebitBodyRequestBean = new StopDebitBodyRequestBean();
    }

    public StopDebitRequestBean(StopDebitBodyRequestBean stopDebitBodyRequestBean2) {
        this.stopDebitBodyRequestBean = stopDebitBodyRequestBean2;
    }

    public StopDebitRequestBean(HeaderBean headerBean2, StopDebitBodyRequestBean stopDebitBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.stopDebitBodyRequestBean = stopDebitBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
