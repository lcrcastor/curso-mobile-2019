package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetNumerosUtilesBodyRequestBean {
    public String prevHashCode;

    public GetNumerosUtilesBodyRequestBean() {
    }

    public GetNumerosUtilesBodyRequestBean(String str) {
        this.prevHashCode = str;
    }
}
