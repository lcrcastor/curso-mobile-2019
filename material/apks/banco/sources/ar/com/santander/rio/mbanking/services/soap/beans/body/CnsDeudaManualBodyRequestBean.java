package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsDeudaManualBodyRequestBean {
    @SerializedName("empServ")
    public String empServ;
    @SerializedName("identificacion")
    public String identificacion;

    public CnsDeudaManualBodyRequestBean(String str, String str2) {
        this.empServ = str;
        this.identificacion = str2;
    }
}
