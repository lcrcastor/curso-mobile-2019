package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ConfigsBean {
    @SerializedName("configuracion")
    public List<ConfigBean> configBean;

    public ConfigBean getConfigBeanForKey(String str) {
        if (this.configBean == null) {
            return null;
        }
        for (ConfigBean configBean2 : this.configBean) {
            if (configBean2.f257id.equals(str)) {
                return configBean2;
            }
        }
        return null;
    }
}
