package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ConsDescriptionBodyRequestBean {
    @SerializedName("listaTablas")
    public List<ListTableArrayBean> listTableBeans = new ArrayList();

    public ConsDescriptionBodyRequestBean() {
        this.listTableBeans.add(new ListTableArrayBean(true));
    }
}
