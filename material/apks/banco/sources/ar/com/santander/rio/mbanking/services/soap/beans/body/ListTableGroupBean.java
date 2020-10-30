package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListTableGroupBean {
    @SerializedName("idTabla")
    public String idTable;
    @SerializedName("listaGrupos")
    public List<ListTableGroupBean> listTableGroupBeans;

    public ListTableGroupBean() {
    }

    public ListTableGroupBean(String str, List<ListTableGroupBean> list) {
        this.idTable = str;
        this.listTableGroupBeans = list;
    }
}
