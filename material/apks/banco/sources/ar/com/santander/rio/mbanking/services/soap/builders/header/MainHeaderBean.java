package ar.com.santander.rio.mbanking.services.soap.builders.header;

import android.app.Activity;
import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;

public class MainHeaderBean {
    private Context context;
    private HeaderBeanBuilder mHeaderBeanBuilder;
    private PrivateHeaderBeanBuilder mPrivateHeaderBean;

    public MainHeaderBean(Context context2) {
        this.context = context2;
    }

    public void setHeaderBeanBuilder(HeaderBeanBuilder headerBeanBuilder) {
        this.mHeaderBeanBuilder = headerBeanBuilder;
    }

    public void setPrivateHeaderBeanBuilder(PrivateHeaderBeanBuilder privateHeaderBeanBuilder) {
        this.mPrivateHeaderBean = privateHeaderBeanBuilder;
    }

    public HeaderBean getHeaderBean() {
        return this.mHeaderBeanBuilder.getHeaderBean();
    }

    public PrivateHeaderBean getPrivateHeaderBean() {
        return this.mPrivateHeaderBean.getPrivateHeaderBean();
    }

    public void constructHeaderBean(String str, String str2, Boolean bool, Boolean bool2, Activity activity) {
        this.mHeaderBeanBuilder.createNewHeaderBeanBuilder(this.context);
        this.mHeaderBeanBuilder.buildComunData();
        this.mHeaderBeanBuilder.buildAccess();
        this.mHeaderBeanBuilder.buildDevices();
        this.mHeaderBeanBuilder.buildLocalitation();
        this.mHeaderBeanBuilder.buildSessionUser();
        this.mHeaderBeanBuilder.buildServices(str, str2);
        this.mHeaderBeanBuilder.buildSecurity(bool, bool2, activity);
    }

    public void constructPrivateHeaderBean(String str, String str2, Boolean bool, Boolean bool2, Activity activity) {
        this.mPrivateHeaderBean.createNewHeaderBeanBuilder(this.context);
        this.mPrivateHeaderBean.buildComunData();
        this.mPrivateHeaderBean.buildAccess();
        this.mPrivateHeaderBean.buildDevices();
        this.mPrivateHeaderBean.buildLocalitation();
        this.mPrivateHeaderBean.buildSessionUser();
        this.mPrivateHeaderBean.buildServices(str, str2);
        this.mPrivateHeaderBean.buildSecurity(bool, bool2, activity);
    }
}
