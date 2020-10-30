package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VGetTurno {
    public static final String nameService = "getTurno";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "1.0");
            case V1_0:
                return new ServiceHeaderBean(nameService, "1.0");
            default:
                return new ServiceHeaderBean(nameService, "1.0");
        }
    }
}
