package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConfirmarRecalificacionResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConfirmarRecalificacionBodyResponseBean confirmarRecalificacionBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public Class getClassBean() {
        return null;
    }

    public Object getErrorBeanObject() {
        return null;
    }

    public void confirmarRecalificacionBodyResponseBean(ConfirmarRecalificacionBodyResponseBean confirmarRecalificacionBodyResponseBean2) {
        this.confirmarRecalificacionBodyResponseBean = confirmarRecalificacionBodyResponseBean2;
    }
}
