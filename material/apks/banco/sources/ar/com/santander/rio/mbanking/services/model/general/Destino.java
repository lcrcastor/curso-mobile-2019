package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Destino {
    @SerializedName("id")
    private String _id = "";
    @SerializedName("codOperacion")
    private String codOperacion = "";
    @SerializedName("destinoDescripcion")
    private String destinoDescripcion = "";
    @SerializedName("destinoEmpresaCel")
    private String destinoEmpresaCel = "";
    @SerializedName("destinoEstado")
    private String destinoEstado = "";
    @SerializedName("destinoSecuencia")
    private String destinoSecuencia = "";
    @SerializedName("destinoTipo")
    private String destinoTipo = "";

    public String getDestinoTipo() {
        return this.destinoTipo;
    }

    public void setDestinoTipo(String str) {
        this.destinoTipo = str;
    }

    public String getDestinoDescripcion() {
        return this.destinoDescripcion;
    }

    public void setDestinoDescripcion(String str) {
        this.destinoDescripcion = str;
    }

    public String getDestinoEmpresaCel() {
        return this.destinoEmpresaCel;
    }

    public void setDestinoEmpresaCel(String str) {
        this.destinoEmpresaCel = str;
    }

    public String getDestinoSecuencia() {
        return this.destinoSecuencia;
    }

    public void setDestinoSecuencia(String str) {
        this.destinoSecuencia = str;
    }

    public String getDestinoEstado() {
        return this.destinoEstado;
    }

    public void setDestinoEstado(String str) {
        this.destinoEstado = str;
    }

    public String getCodOperacion() {
        return this.codOperacion;
    }

    public void setCodOperacion(String str) {
        this.codOperacion = str;
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String str) {
        this._id = str;
    }
}
