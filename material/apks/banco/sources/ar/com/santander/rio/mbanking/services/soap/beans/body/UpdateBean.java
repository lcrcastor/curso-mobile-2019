package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class UpdateBean {
    public String url;
    public String version;

    public UpdateBean() {
    }

    public UpdateBean(String str, String str2) {
        this.version = str;
        this.url = str2;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
