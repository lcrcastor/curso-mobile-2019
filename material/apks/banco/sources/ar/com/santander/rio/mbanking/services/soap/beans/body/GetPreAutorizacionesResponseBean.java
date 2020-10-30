package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetPreAutorizacionesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    /* access modifiers changed from: 0000 */
    public void GetPreAutorizacionesResponseBean() {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public void setPreAutorizacionesBodyResponseBean(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean2) {
        this.getPreAutorizacionesBodyResponseBean = getPreAutorizacionesBodyResponseBean2;
    }

    /* access modifiers changed from: 0000 */
    public void GetPreAutorizacionesResponseBean(GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean2) {
        this.getPreAutorizacionesBodyResponseBean = getPreAutorizacionesBodyResponseBean2;
        this.getPreAutorizacionesBodyResponseBean = getPreAutorizacionesBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getPreAutorizacionesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetPreAutorizacionesBodyResponseBean getPreAutorizacionesBodyResponseBean() {
        return this.getPreAutorizacionesBodyResponseBean;
    }
}
