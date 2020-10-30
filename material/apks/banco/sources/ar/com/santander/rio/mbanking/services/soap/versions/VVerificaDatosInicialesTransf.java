package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VVerificaDatosInicialesTransf {
    public static final String nameService = "verificaDatosInicialesTransf";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "1.4");
            case V1_3:
                return new ServiceHeaderBean(nameService, "1.3");
            case V1_4:
                return new ServiceHeaderBean(nameService, "1.4");
            default:
                return new ServiceHeaderBean(nameService, "1.4");
        }
    }
}
