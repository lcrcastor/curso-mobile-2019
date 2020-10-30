package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CAccountAcc extends CElementBaseAcc {
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterAccount(str);
    }
}
