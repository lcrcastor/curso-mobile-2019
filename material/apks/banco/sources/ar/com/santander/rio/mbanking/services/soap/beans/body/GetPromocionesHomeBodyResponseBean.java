package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetPromocionesHomeBodyResponseBean extends ErrorBodyBean {
    @SerializedName("pagina")
    public String pagina;
    @SerializedName("paginas")
    public String paginas;
    @SerializedName("promociones")
    public PromocionesBean promocionesBean;

    public String getPagina() {
        return this.pagina;
    }

    public void setPagina(String str) {
        this.pagina = str;
    }

    public String getPaginas() {
        return this.paginas;
    }

    public void setPaginas(String str) {
        this.paginas = str;
    }

    public PromocionesBean getPromocionesBean() {
        return this.promocionesBean;
    }

    public void setPromocionesBean(PromocionesBean promocionesBean2) {
        this.promocionesBean = promocionesBean2;
    }
}
