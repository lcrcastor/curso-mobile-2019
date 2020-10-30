package ar.com.santander.rio.mbanking.inject;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.module.BaseActivityModule;
import ar.com.santander.rio.mbanking.inject.modules.AndroidModule;

public class Modules {
    public static Object[] listModulesForApplication(BaseApplication baseApplication) {
        return new Object[]{new AndroidModule(baseApplication)};
    }

    public static Object[] listModulesForActivity(BaseActivity baseActivity) {
        return new Object[]{new BaseActivityModule(baseActivity)};
    }

    private Modules() {
    }
}
