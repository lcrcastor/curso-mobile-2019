package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CnsEmpresaDatosEmpresas {
    @SerializedName("datosEmpresa")
    public List<CnsEmpresaDatosEmpresa> lstCnsEmpresaDatosEmpresa;

    public CnsEmpresaDatosEmpresas(List<CnsEmpresaDatosEmpresa> list) {
        this.lstCnsEmpresaDatosEmpresa = list;
    }

    public List<CnsEmpresaDatosEmpresa> getLstCnsEmpresaDatosEmpresa() {
        return this.lstCnsEmpresaDatosEmpresa;
    }

    public void setLstCnsEmpresaDatosEmpresa(List<CnsEmpresaDatosEmpresa> list) {
        this.lstCnsEmpresaDatosEmpresa = list;
    }
}
