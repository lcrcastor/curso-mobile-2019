package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsDeudaCBBodyRequestBean {
    @SerializedName("codBar")
    public String codBar;
    @SerializedName("empServ")
    public String empServ;

    public CnsDeudaCBBodyRequestBean(String str, String str2) {
        this.codBar = str;
        this.empServ = str2;
    }
}
