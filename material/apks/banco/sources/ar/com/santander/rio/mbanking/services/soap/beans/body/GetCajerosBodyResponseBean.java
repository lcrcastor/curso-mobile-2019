package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCajerosBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cajeros")
    private CajerosBean cajerosBeans;
    private String pagina;
    private String paginas;

    public GetCajerosBodyResponseBean() {
    }

    public String getPagina() {
        return this.pagina;
    }

    public String getPaginas() {
        return this.paginas;
    }

    public void setPagina(String str) {
        this.pagina = str;
    }

    public void setPaginas(String str) {
        this.paginas = str;
    }

    public void setCajerosBeans(CajerosBean cajerosBean) {
        this.cajerosBeans = cajerosBean;
    }

    public CajerosBean getCajerosBeans() {
        return this.cajerosBeans;
    }

    public GetCajerosBodyResponseBean(String str, String str2, CajerosBean cajerosBean) {
        this.pagina = str;
        this.paginas = str2;
        this.cajerosBeans = cajerosBean;
    }
}
