package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsEmpresaBodyRequestBean {
    @SerializedName("empServ")
    public String empServ;
    @SerializedName("hashCode")
    public String hashCode;

    public CnsEmpresaBodyRequestBean(String str, String str2) {
        this.empServ = str;
        this.hashCode = str2;
    }
}
