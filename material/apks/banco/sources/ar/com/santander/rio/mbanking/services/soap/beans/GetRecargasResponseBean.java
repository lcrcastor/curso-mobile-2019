package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetRecargasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetRecargasResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetRecargasBodyResponseBean getRecargasBodyResponseBean;
    @SerializedName("header")
    public HeaderBean header;

    public GetRecargasResponseBean() {
        this.getRecargasBodyResponseBean = new GetRecargasBodyResponseBean();
    }

    public GetRecargasResponseBean(HeaderBean headerBean, GetRecargasBodyResponseBean getRecargasBodyResponseBean2) {
        this.header = headerBean;
        this.getRecargasBodyResponseBean = getRecargasBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getRecargasBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
