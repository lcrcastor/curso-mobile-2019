package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;

public class VAbmTurno {
    public static final String nameService = "abmTurno";

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
