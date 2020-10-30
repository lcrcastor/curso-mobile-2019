package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VTransferencias {
    public static final String nameService = "transferencias";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "1.5");
            case V1_4:
                return new ServiceHeaderBean(nameService, "1.4");
            case V1_5:
                return new ServiceHeaderBean(nameService, "1.5");
            default:
                return new ServiceHeaderBean(nameService, "1.5");
        }
    }
}
