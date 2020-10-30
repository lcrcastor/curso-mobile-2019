package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ConsDescriptionBodyResponseBean extends ErrorBodyBean {
    @SerializedName("listaTablas")
    public List<ListTableBean> listTableBeans;

    public ConsDescriptionBodyResponseBean() {
        this.listTableBeans = new ArrayList();
        this.listTableBeans = new ArrayList();
    }

    public List<ListTableBean> getListTableBeans() {
        return this.listTableBeans;
    }

    public void setListTableBeans(List<ListTableBean> list) {
        this.listTableBeans = list;
    }
}
