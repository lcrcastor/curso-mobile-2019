package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetOcupacionesBodyRequestBean {
    @SerializedName("hashCode")
    private String hashCode;

    public GetOcupacionesBodyRequestBean(String str) {
        this.hashCode = str;
    }
}
