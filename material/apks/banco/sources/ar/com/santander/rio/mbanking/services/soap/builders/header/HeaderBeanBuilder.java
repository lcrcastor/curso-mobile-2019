package ar.com.santander.rio.mbanking.services.soap.builders.header;

import android.app.Activity;
import android.content.Context;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.constants.ConstantsWS;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilDeviceInfo;

public class HeaderBeanBuilder {
    private Context context;
    private HeaderBean mHeaderBean;
    private SettingsManager settingsManager;

    public void buildSessionUser() {
    }

    public HeaderBean getHeaderBean() {
        return this.mHeaderBean;
    }

    public void createNewHeaderBeanBuilder(Context context2) {
        this.mHeaderBean = new HeaderBean();
        this.context = context2;
        this.settingsManager = new SettingsManager(context2);
    }

    public void buildComunData() {
        getHeaderBean().idApplication = ConstantsWS.ID_APPLICATION;
        getHeaderBean().idDevice = this.settingsManager.getKeyUniqueID();
        getHeaderBean().timeRequest = UtilDate.getCurrentDateTimeToString(ConstantsWS.FORMAT_DATETIME_REQUEST);
        getHeaderBean().version = UtilDeviceInfo.getVersion(this.context);
        getHeaderBean().ip = UtilDeviceInfo.getIp(this.context);
    }

    public void buildAccess() {
        getHeaderBean().accessHeaderBean = ConstantsWS.getAccessApp();
    }

    public void buildDevices() {
        getHeaderBean().deviceHeaderBean.idRuntime = "5";
        getHeaderBean().deviceHeaderBean.marca = UtilDeviceInfo.getMarca();
        getHeaderBean().deviceHeaderBean.modelo = UtilDeviceInfo.getModelo();
        getHeaderBean().deviceHeaderBean.versionSO = UtilDeviceInfo.getVersionSO();
        getHeaderBean().deviceHeaderBean.idTerminal = this.settingsManager.getLastIdTerminal();
    }

    public void buildLocalitation() {
        getHeaderBean().localitationHeaderBean.latitude = UtilDeviceInfo.getLatitude();
        getHeaderBean().localitationHeaderBean.longitude = UtilDeviceInfo.getLongitude();
    }

    public void buildServices(String str, String str2) {
        getHeaderBean().serviceHeaderBean.name = str;
        getHeaderBean().serviceHeaderBean.version = str2;
    }

    public void buildSecurity(Boolean bool, Boolean bool2, Activity activity) {
        if (bool.booleanValue()) {
            getHeaderBean().securityHeaderBean.rsa = UtilDeviceInfo.getRSAData(activity);
            if (bool2.booleanValue()) {
                getHeaderBean().securityHeaderBean.token = UtilDeviceInfo.getNewToken(activity);
                return;
            }
            getHeaderBean().securityHeaderBean.token = "";
            return;
        }
        getHeaderBean().securityHeaderBean = null;
    }
}
