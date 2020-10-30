package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CBaseAccesibility extends CElementBaseAcc {
    private String a;

    public CBaseAccesibility(String str) {
        this.a = str;
    }

    public CBaseAccesibility() {
    }

    /* access modifiers changed from: protected */
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return this.a != null ? this.a : str;
    }

    public String getCustomString() {
        return this.a;
    }

    public void setCustomString(String str) {
        this.a = str;
    }
}
