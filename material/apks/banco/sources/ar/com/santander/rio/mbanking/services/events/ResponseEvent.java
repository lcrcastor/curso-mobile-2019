package ar.com.santander.rio.mbanking.services.events;

public class ResponseEvent extends WebServiceEvent {
    private String a;
    private String b;

    public ResponseEvent() {
    }

    public ResponseEvent(String str, String str2) {
        this.a = str2;
        this.b = str;
    }

    public String getmValue() {
        return this.a;
    }

    public void setmValue(String str) {
        this.a = str;
    }

    public String getmTag() {
        return this.b;
    }

    public void setmTag(String str) {
        this.b = str;
    }
}
