package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VSolicitudPrestamoPreacordado {
    public static final String nameService = "solicitudPrestamoPreacordado";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.5");
            case V1_1:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.1");
            case V1_2:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.2");
            case V1_3:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.3");
            case V1_4:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.4");
            case V1_5:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.5");
            default:
                return new ServiceHeaderBean("solicitudPrestamoPreacordado", "1.5");
        }
    }
}
