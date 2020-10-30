package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaCategoriasFondosBean {
    @SerializedName("categoria")
    private List<CategoriaFondosBean> categoriasFondosBean;

    public ListaCategoriasFondosBean() {
        this.categoriasFondosBean = new ArrayList();
    }

    public ListaCategoriasFondosBean(List<CategoriaFondosBean> list) {
        this.categoriasFondosBean = list;
    }

    public List<CategoriaFondosBean> getCategoriasFondosBean() {
        return this.categoriasFondosBean;
    }

    public void setCategoriasFondosBean(List<CategoriaFondosBean> list) {
        this.categoriasFondosBean = list;
    }
}
