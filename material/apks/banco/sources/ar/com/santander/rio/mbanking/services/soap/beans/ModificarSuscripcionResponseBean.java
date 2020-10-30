package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ModificarSuscripcionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ModificarSuscripcionResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public ModificarSuscripcionBodyResponseBean modificarSuscripcionBodyResponseBean;

    public ModificarSuscripcionResponseBean(ModificarSuscripcionBodyResponseBean modificarSuscripcionBodyResponseBean2) {
        this.modificarSuscripcionBodyResponseBean = modificarSuscripcionBodyResponseBean2;
    }

    public ModificarSuscripcionResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.modificarSuscripcionBodyResponseBean;
    }
}
