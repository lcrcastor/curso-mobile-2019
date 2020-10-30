package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VTransferirFondo {
    public static final String nameService = "transferirFondo";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "1.1");
            case V1_0:
                return new ServiceHeaderBean(nameService, "1.1");
            default:
                return new ServiceHeaderBean(nameService, "1.1");
        }
    }
}
