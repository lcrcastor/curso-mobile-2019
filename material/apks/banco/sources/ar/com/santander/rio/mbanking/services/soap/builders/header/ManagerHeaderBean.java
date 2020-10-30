package ar.com.santander.rio.mbanking.services.soap.builders.header;

import android.app.Activity;
import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class ManagerHeaderBean {
    public static HeaderBean getPublicHeaderBean(ServiceHeaderBean serviceHeaderBean, Context context) {
        return getPublicHeaderBean(serviceHeaderBean, context, Boolean.valueOf(false), Boolean.valueOf(false), null);
    }

    public static HeaderBean getPublicHeaderBean(ServiceHeaderBean serviceHeaderBean, Context context, Boolean bool, Boolean bool2, Activity activity) {
        MainHeaderBean mainHeaderBean = new MainHeaderBean(context);
        mainHeaderBean.setHeaderBeanBuilder(new HeaderBeanBuilder());
        mainHeaderBean.constructHeaderBean(serviceHeaderBean.name, serviceHeaderBean.version, bool, bool2, activity);
        mainHeaderBean.getHeaderBean().accesoPublico = Constants.WS_ACCESS_PUBLIC;
        return mainHeaderBean.getHeaderBean();
    }

    public static PrivateHeaderBean getPrivateHeaderBean(ServiceHeaderBean serviceHeaderBean, Context context, String str, String str2) {
        return getPrivateHeaderBean(serviceHeaderBean, context, str, str2, Boolean.valueOf(false), Boolean.valueOf(false), null);
    }

    public static PrivateHeaderBean getPrivateHeaderBean(ServiceHeaderBean serviceHeaderBean, Context context, String str, String str2, Boolean bool, Boolean bool2, Activity activity) {
        MainHeaderBean mainHeaderBean = new MainHeaderBean(context);
        mainHeaderBean.setPrivateHeaderBeanBuilder(new PrivateHeaderBeanBuilder(str, str2, context));
        mainHeaderBean.constructPrivateHeaderBean(serviceHeaderBean.name, serviceHeaderBean.version, bool, bool2, activity);
        mainHeaderBean.getPrivateHeaderBean().accesoPublico = Constants.WS_ACCESS_PRIVATE;
        return mainHeaderBean.getPrivateHeaderBean();
    }
}
