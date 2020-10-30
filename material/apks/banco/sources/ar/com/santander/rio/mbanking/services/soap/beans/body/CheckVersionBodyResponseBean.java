package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CheckVersionBodyResponseBean extends ErrorBodyBean {
    @SerializedName("idTerminal")
    private String idTerminal;
    private String modeloComercial;
    public String result;
    private String token;
    @SerializedName("update")
    public UpdateBean updateBean;
    private WebViewHome webViewHome;

    public CheckVersionBodyResponseBean() {
        this.updateBean = new UpdateBean();
    }

    public CheckVersionBodyResponseBean(String str, UpdateBean updateBean2, String str2, String str3) {
        this.result = str;
        this.updateBean = updateBean2;
        this.modeloComercial = str2;
        this.idTerminal = str3;
    }

    public String getModeloComercial() {
        return this.modeloComercial;
    }

    public void setModeloComercial(String str) {
        this.modeloComercial = str;
    }

    public String getIdTerminal() {
        return this.idTerminal;
    }

    public void setIdTerminal(String str) {
        this.idTerminal = str;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public UpdateBean getUpdateBean() {
        return this.updateBean;
    }

    public void setUpdateBean(UpdateBean updateBean2) {
        this.updateBean = updateBean2;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public WebViewHome getWebViewHome() {
        return this.webViewHome;
    }

    public void setWebViewHome(WebViewHome webViewHome2) {
        this.webViewHome = webViewHome2;
    }
}
