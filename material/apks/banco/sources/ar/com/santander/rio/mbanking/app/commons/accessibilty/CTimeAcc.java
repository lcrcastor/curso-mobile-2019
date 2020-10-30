package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CTimeAcc extends CElementBaseAcc {
    /* access modifiers changed from: protected */
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterTime(str);
    }
}
