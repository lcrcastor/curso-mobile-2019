package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VConsultaPrestamosPermitidosProd {
    public static final String nameService = "consultaPrestamosPermitidos";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean("consultaPrestamosPermitidos", "1.0");
            case V1_0:
                return new ServiceHeaderBean("consultaPrestamosPermitidos", "1.0");
            default:
                return new ServiceHeaderBean("consultaPrestamosPermitidos", "1.0");
        }
    }
}
