package ar.com.santander.rio.mbanking.services.soap.request;

import android.app.Activity;
import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.builders.header.MainHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.builders.header.PrivateHeaderBeanBuilder;

public abstract class PrivateBaseRequest extends BaseRequest {
    Context context;

    public PrivateBaseRequest(Context context2) {
        super(context2);
        this.context = context2;
    }

    public PrivateBaseRequest(Context context2, boolean z) {
        super(context2, z);
        this.context = context2;
    }

    protected PrivateBaseRequest() {
    }

    /* access modifiers changed from: protected */
    public HeaderBean getPrivateHeaderBean(ServiceHeaderBean serviceHeaderBean, String str, String str2) {
        return getPrivateHeaderBean(serviceHeaderBean, str, str2, Boolean.valueOf(false), Boolean.valueOf(false), null);
    }

    /* access modifiers changed from: protected */
    public HeaderBean getPrivateHeaderBean(ServiceHeaderBean serviceHeaderBean, String str, String str2, Boolean bool, Boolean bool2, Activity activity) {
        MainHeaderBean mainHeaderBean = new MainHeaderBean(this.context);
        mainHeaderBean.setPrivateHeaderBeanBuilder(new PrivateHeaderBeanBuilder(str, str2, this.context));
        mainHeaderBean.constructPrivateHeaderBean(serviceHeaderBean.name, serviceHeaderBean.version, bool, bool2, activity);
        return mainHeaderBean.getPrivateHeaderBean();
    }
}
