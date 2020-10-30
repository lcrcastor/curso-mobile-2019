package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCategoriasSuperClubBodyRequestBean {
    @SerializedName("idCategoria")
    public String idCategoria;

    public GetCategoriasSuperClubBodyRequestBean() {
    }

    public GetCategoriasSuperClubBodyRequestBean(String str) {
        this.idCategoria = str;
    }
}
