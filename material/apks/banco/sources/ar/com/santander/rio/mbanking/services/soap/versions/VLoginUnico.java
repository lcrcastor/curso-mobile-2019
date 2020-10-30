package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VLoginUnico {
    public static final String nameService = "loginUnico";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "2.2");
            case V1_6:
                return new ServiceHeaderBean(nameService, "1.6");
            case V1_7:
                return new ServiceHeaderBean(nameService, "1.7");
            case V1_8:
                return new ServiceHeaderBean(nameService, "1.8");
            case V1_9:
                return new ServiceHeaderBean(nameService, "1.9");
            case V2_0:
                return new ServiceHeaderBean(nameService, "2.0");
            case V2_1:
                return new ServiceHeaderBean(nameService, "2.1");
            case V1_2:
                return new ServiceHeaderBean(nameService, "2.2");
            default:
                return new ServiceHeaderBean(nameService, "2.2");
        }
    }
}
