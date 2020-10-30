package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VCancelaMandatoExtEnv {
    public static final String nameService = "cancelaMandatoExtEnv";

    /* renamed from: ar.com.santander.rio.mbanking.services.soap.versions.VCancelaMandatoExtEnv$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices = new int[EVersionServices.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices[] r0 = ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices = r0
                int[] r0 = $SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices     // Catch:{ NoSuchFieldError -> 0x0014 }
                ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices r1 = ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices.CURRENT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices     // Catch:{ NoSuchFieldError -> 0x001f }
                ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices r1 = ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices.V1_0     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.soap.versions.VCancelaMandatoExtEnv.AnonymousClass1.<clinit>():void");
        }
    }

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        int i = AnonymousClass1.$SwitchMap$ar$com$santander$rio$mbanking$services$soap$versions$EVersionServices[eVersionServices.ordinal()];
        return new ServiceHeaderBean(nameService, "1.0");
    }
}
