package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PromocionesBean {
    @SerializedName("promocionSucursal")
    public List<PromocionSucursalBean> promocionSucursalBeanList;

    public PromocionesBean() {
    }

    public PromocionesBean(List<PromocionSucursalBean> list) {
        this.promocionSucursalBeanList = list;
    }

    public List<PromocionSucursalBean> getPromocionSucursalBeanList() {
        return this.promocionSucursalBeanList;
    }

    public void setPromocionSucursalBeanList(List<PromocionSucursalBean> list) {
        this.promocionSucursalBeanList = list;
    }

    public PromocionSucursalBean getFirstPromocionSucursal() {
        if (this.promocionSucursalBeanList != null && this.promocionSucursalBeanList.size() > 0) {
            this.promocionSucursalBeanList.get(0);
        }
        return null;
    }
}
