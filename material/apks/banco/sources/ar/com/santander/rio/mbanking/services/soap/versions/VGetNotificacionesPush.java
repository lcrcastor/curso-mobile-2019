package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VGetNotificacionesPush {
    public static final String nameService = "getNotificacionesPush";

    /* renamed from: ar.com.santander.rio.mbanking.services.soap.versions.VGetNotificacionesPush$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices = new int[EVersionServices.values().length];

        static {
            try {
                $SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices[EVersionServices.CURRENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        int i = AnonymousClass1.$SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices[eVersionServices.ordinal()];
        return new ServiceHeaderBean(nameService, "1.0");
    }
}
