package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetClasificadoresBodyRequestBean {
    public String prevHashCode;

    public GetClasificadoresBodyRequestBean() {
    }

    public GetClasificadoresBodyRequestBean(String str) {
        this.prevHashCode = str;
    }
}
