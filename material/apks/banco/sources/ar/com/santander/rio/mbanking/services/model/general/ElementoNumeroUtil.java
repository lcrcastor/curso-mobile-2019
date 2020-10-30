package ar.com.santander.rio.mbanking.services.model.general;

public class ElementoNumeroUtil {
    private String descripAdicDatoUtil;
    private String descripDatoUtil;
    private String tel1DatoUtil;
    private String tel2DatoUtil;

    public ElementoNumeroUtil() {
    }

    public ElementoNumeroUtil(String str, String str2, String str3, String str4) {
        this.descripDatoUtil = str;
        this.descripAdicDatoUtil = str2;
        this.tel1DatoUtil = str3;
        this.tel2DatoUtil = str4;
    }

    public String getDescripDatoUtil() {
        return this.descripDatoUtil;
    }

    public void setDescripDatoUtil(String str) {
        this.descripDatoUtil = str;
    }

    public String getDescripAdicDatoUtil() {
        return this.descripAdicDatoUtil;
    }

    public void setDescripAdicDatoUtil(String str) {
        this.descripAdicDatoUtil = str;
    }

    public String getTel1DatoUtil() {
        return this.tel1DatoUtil;
    }

    public void setTel1DatoUtil(String str) {
        this.tel1DatoUtil = str;
    }

    public String getTel2DatoUtil() {
        return this.tel2DatoUtil;
    }

    public void setTel2DatoUtil(String str) {
        this.tel2DatoUtil = str;
    }
}
