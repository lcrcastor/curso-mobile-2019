package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCategoriasSuperClubBodyResponseBean extends ErrorBodyBean {
    @SerializedName("categorias")
    public CategoriasSuperClubBean categorias;

    public GetCategoriasSuperClubBodyResponseBean() {
    }

    public GetCategoriasSuperClubBodyResponseBean(CategoriasSuperClubBean categoriasSuperClubBean) {
        this.categorias = categoriasSuperClubBean;
    }
}
