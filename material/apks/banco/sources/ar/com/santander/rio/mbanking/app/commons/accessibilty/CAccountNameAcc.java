package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CAccountNameAcc extends CElementBaseAcc {
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterAccountName(str);
    }
}
