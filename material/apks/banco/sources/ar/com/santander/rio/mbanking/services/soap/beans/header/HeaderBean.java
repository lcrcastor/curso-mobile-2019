package ar.com.santander.rio.mbanking.services.soap.beans.header;

import com.google.gson.annotations.SerializedName;

public class HeaderBean {
    public static final String accesoPublicoString = "S";
    @SerializedName("accesoPublico")
    public String accesoPublico = "N";
    @SerializedName("acceso")
    public String accessHeaderBean;
    @SerializedName("dispositivo")
    public DeviceHeaderBean deviceHeaderBean;
    @SerializedName("idAplicacion")
    public String idApplication;
    @SerializedName("idDispositivo")
    public String idDevice;
    @SerializedName("ip")
    public String ip;
    @SerializedName("ubicacion")
    public LocalitationHeaderBean localitationHeaderBean;
    @SerializedName("seguridad")
    public SecurityHeaderBean securityHeaderBean;
    @SerializedName("servicio")
    public ServiceHeaderBean serviceHeaderBean;
    @SerializedName("timeStamp")
    public String timeRequest;
    @SerializedName("version")
    public String version;

    public HeaderBean() {
        this.accesoPublico = "S";
        this.deviceHeaderBean = new DeviceHeaderBean();
        this.localitationHeaderBean = new LocalitationHeaderBean();
        this.serviceHeaderBean = new ServiceHeaderBean();
        this.securityHeaderBean = new SecurityHeaderBean();
    }

    public HeaderBean(String str, String str2, String str3, String str4, String str5, DeviceHeaderBean deviceHeaderBean2, LocalitationHeaderBean localitationHeaderBean2, ServiceHeaderBean serviceHeaderBean2, String str6, SecurityHeaderBean securityHeaderBean2) {
        this.accesoPublico = "S";
        this.idApplication = str;
        this.idDevice = str2;
        this.timeRequest = str3;
        this.version = str4;
        this.accessHeaderBean = str5;
        this.deviceHeaderBean = deviceHeaderBean2;
        this.localitationHeaderBean = localitationHeaderBean2;
        this.serviceHeaderBean = serviceHeaderBean2;
        this.ip = str6;
        this.securityHeaderBean = securityHeaderBean2;
    }

    public void makePublic() {
        this.accesoPublico = "S";
    }
}
