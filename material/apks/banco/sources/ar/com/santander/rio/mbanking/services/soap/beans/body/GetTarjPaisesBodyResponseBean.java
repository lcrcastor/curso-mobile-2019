package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetTarjPaisesBodyResponseBean extends ErrorBodyBean {
    @SerializedName("duracionMax")
    private String duracionMax;
    @SerializedName("fechaInicioMax")
    private String fechaInicioMax;
    @SerializedName("paises")
    private PaisesBean paises;
    @SerializedName("usuarios")
    private UsuariosMarcacionBean usuarios;

    public GetTarjPaisesBodyResponseBean() {
    }

    public GetTarjPaisesBodyResponseBean(String str, String str2, PaisesBean paisesBean, UsuariosMarcacionBean usuariosMarcacionBean) {
        this.fechaInicioMax = str;
        this.duracionMax = str2;
        this.paises = paisesBean;
        this.usuarios = usuariosMarcacionBean;
    }

    public String getFechaInicioMax() {
        return this.fechaInicioMax;
    }

    public void setFechaInicioMax(String str) {
        this.fechaInicioMax = str;
    }

    public String getDuracionMax() {
        return this.duracionMax;
    }

    public void setDuracionMax(String str) {
        this.duracionMax = str;
    }

    public PaisesBean getPaises() {
        return this.paises;
    }

    public void setPaises(PaisesBean paisesBean) {
        this.paises = paisesBean;
    }

    public UsuariosMarcacionBean getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(UsuariosMarcacionBean usuariosMarcacionBean) {
        this.usuarios = usuariosMarcacionBean;
    }
}
