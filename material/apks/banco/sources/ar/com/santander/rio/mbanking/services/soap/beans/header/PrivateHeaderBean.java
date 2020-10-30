package ar.com.santander.rio.mbanking.services.soap.beans.header;

import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class PrivateHeaderBean extends HeaderBean implements IBeanWS {
    public static final String accesoPublicoString = "N";
    @SerializedName("nup")
    public String nup;
    @SerializedName("sesionUsuario")
    public String session;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public PrivateHeaderBean() {
    }

    public PrivateHeaderBean(String str, String str2) {
        this.session = str;
        this.nup = str2;
    }

    public PrivateHeaderBean(String str, String str2, String str3, String str4, String str5, DeviceHeaderBean deviceHeaderBean, LocalitationHeaderBean localitationHeaderBean, ServiceHeaderBean serviceHeaderBean, String str6, String str7, String str8, SecurityHeaderBean securityHeaderBean) {
        super(str, str2, str3, str4, str5, deviceHeaderBean, localitationHeaderBean, serviceHeaderBean, str8, securityHeaderBean);
        this.session = str6;
        this.nup = str7;
    }

    public Class getClassBean() {
        return getClass();
    }
}
