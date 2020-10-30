package ar.com.santander.rio.mbanking.services.soap.builders.header;

import android.app.Activity;
import android.content.Context;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.constants.ConstantsWS;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;

public class PrivateHeaderBeanBuilder extends HeaderBeanBuilder {
    private Context context;
    protected PrivateHeaderBean mHeaderBean = new PrivateHeaderBean();
    private String mNup;
    private String mSessionUser;
    private SettingsManager settingsManager;

    public PrivateHeaderBeanBuilder(String str, String str2, Context context2) {
        this.mSessionUser = str;
        this.mNup = str2;
        this.context = context2;
        this.settingsManager = new SettingsManager(context2);
    }

    public PrivateHeaderBean getPrivateHeaderBean() {
        return this.mHeaderBean;
    }

    public void buildSessionUser() {
        this.mHeaderBean.session = this.mSessionUser;
        this.mHeaderBean.nup = this.mNup;
    }

    public void buildComunData() {
        this.mHeaderBean.idApplication = ConstantsWS.ID_APPLICATION;
        this.mHeaderBean.idDevice = this.settingsManager.getKeyUniqueID();
        this.mHeaderBean.timeRequest = UtilDate.getCurrentDateTimeToString(ConstantsWS.FORMAT_DATETIME_REQUEST);
        this.mHeaderBean.version = UtilDeviceInfo.getVersion(this.context);
        this.mHeaderBean.ip = UtilDeviceInfo.getIp(this.context);
    }

    public void buildAccess() {
        this.mHeaderBean.accessHeaderBean = ConstantsWS.getAccessApp();
    }

    public void buildDevices() {
        this.mHeaderBean.deviceHeaderBean.idRuntime = "5";
        this.mHeaderBean.deviceHeaderBean.marca = UtilDeviceInfo.getMarca();
        this.mHeaderBean.deviceHeaderBean.modelo = UtilDeviceInfo.getModelo();
        this.mHeaderBean.deviceHeaderBean.versionSO = UtilDeviceInfo.getVersionSO();
        this.mHeaderBean.deviceHeaderBean.idTerminal = this.settingsManager.getLastIdTerminal();
    }

    public void buildLocalitation() {
        this.mHeaderBean.localitationHeaderBean.latitude = UtilDeviceInfo.getLatitude();
        this.mHeaderBean.localitationHeaderBean.longitude = UtilDeviceInfo.getLongitude();
    }

    public void buildServices(String str, String str2) {
        this.mHeaderBean.serviceHeaderBean.name = str;
        this.mHeaderBean.serviceHeaderBean.version = str2;
    }

    public void buildSecurity(Boolean bool, Boolean bool2, Activity activity) {
        if (bool.booleanValue()) {
            this.mHeaderBean.securityHeaderBean.rsa = UtilDeviceInfo.getRSAData(activity);
            if (bool2.booleanValue()) {
                this.mHeaderBean.securityHeaderBean.token = UtilDeviceInfo.getNewToken(activity);
                return;
            }
            this.mHeaderBean.securityHeaderBean.token = "";
            return;
        }
        this.mHeaderBean.securityHeaderBean = null;
    }
}
