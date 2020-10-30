package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetProximasCuotasCreditosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetProximasCuotasCreditosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public GetProximasCuotasCreditosBodyRequestBean mGetProximasCuotasCreditosBodyRequestBean;

    public GetProximasCuotasCreditosRequestBean(HeaderBean headerBean2, GetProximasCuotasCreditosBodyRequestBean getProximasCuotasCreditosBodyRequestBean) {
        this.headerBean = headerBean2;
        this.mGetProximasCuotasCreditosBodyRequestBean = getProximasCuotasCreditosBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
