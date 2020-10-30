package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListTableBean {
    @SerializedName("idTabla")
    public String idTable;
    @SerializedName("listaGrupos")
    public List<ListGroupBean> listGroupBeans = new ArrayList();

    public String getIdTable() {
        return this.idTable;
    }

    public void setIdTable(String str) {
        this.idTable = str;
    }

    public List<ListGroupBean> getListGroupBeans() {
        return this.listGroupBeans;
    }

    public void setListGroupBeans(List<ListGroupBean> list) {
        this.listGroupBeans = list;
    }
}
