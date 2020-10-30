package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetListaTjWomenResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetListaTjWomenBodyResponseBean getListaTjWomenBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    /* access modifiers changed from: 0000 */
    public void GetListaTjWomenResponseBean() {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public void setPreAutorizacionesBodyResponseBean(GetListaTjWomenBodyResponseBean getListaTjWomenBodyResponseBean2) {
        this.getListaTjWomenBodyResponseBean = getListaTjWomenBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getListaTjWomenBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetListaTjWomenBodyResponseBean getListaTjWomenBodyResponseBean() {
        return this.getListaTjWomenBodyResponseBean;
    }
}
