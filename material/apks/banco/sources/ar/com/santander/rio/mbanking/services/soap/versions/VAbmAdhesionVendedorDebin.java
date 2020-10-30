package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VAbmAdhesionVendedorDebin extends WebServiceEvent {
    public static final String nameService = "abmAdhesionVendedor";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "1.1");
            case V1_1:
                return new ServiceHeaderBean(nameService, "1.1");
            case V1_0:
                return new ServiceHeaderBean(nameService, "1.0");
            default:
                return new ServiceHeaderBean(nameService, "1.1");
        }
    }
}
