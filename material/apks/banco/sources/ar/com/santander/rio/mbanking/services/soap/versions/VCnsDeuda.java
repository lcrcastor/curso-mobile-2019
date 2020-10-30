package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VCnsDeuda {
    public static final String nameService = "cnsDeuda";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
            case V1_3:
                return new ServiceHeaderBean(nameService, "1.3");
            case V1_0:
                return new ServiceHeaderBean(nameService, "1.0");
            case V1_1:
                return new ServiceHeaderBean(nameService, "1.1");
            case V1_2:
                return new ServiceHeaderBean(nameService, "1.2");
            default:
                return new ServiceHeaderBean(nameService, "1.0");
        }
    }
}
