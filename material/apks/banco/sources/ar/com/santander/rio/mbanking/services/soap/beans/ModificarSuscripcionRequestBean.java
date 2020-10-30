package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ModificarSuscripcionnBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ModificarSuscripcionRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public ModificarSuscripcionnBodyRequestBean modificarSuscripcionnBodyRequestBean;

    public ModificarSuscripcionRequestBean(PrivateHeaderBean privateHeaderBean, ModificarSuscripcionnBodyRequestBean modificarSuscripcionnBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.modificarSuscripcionnBodyRequestBean = modificarSuscripcionnBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public ModificarSuscripcionnBodyRequestBean getModificarSuscripcionnBodyRequestBean() {
        return this.modificarSuscripcionnBodyRequestBean;
    }

    public void setModificarSuscripcionnBodyRequestBean(ModificarSuscripcionnBodyRequestBean modificarSuscripcionnBodyRequestBean2) {
        this.modificarSuscripcionnBodyRequestBean = modificarSuscripcionnBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
