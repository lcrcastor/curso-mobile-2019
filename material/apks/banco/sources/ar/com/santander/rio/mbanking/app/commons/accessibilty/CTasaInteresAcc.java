package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CTasaInteresAcc extends CElementBaseAcc {
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterTasaInteres(str);
    }
}
