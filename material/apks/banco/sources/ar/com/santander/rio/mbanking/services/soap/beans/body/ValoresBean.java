package ar.com.santander.rio.mbanking.services.soap.beans.body;

import java.util.ArrayList;
import java.util.List;

public class ValoresBean {
    private List<String> idCV;

    public ValoresBean(List<String> list) {
        this.idCV = list;
    }

    public ValoresBean() {
        this.idCV = new ArrayList();
    }
}
