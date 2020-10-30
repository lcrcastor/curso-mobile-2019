package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CDistanceAcc extends CElementBaseAcc {
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterDistance(str);
    }
}
