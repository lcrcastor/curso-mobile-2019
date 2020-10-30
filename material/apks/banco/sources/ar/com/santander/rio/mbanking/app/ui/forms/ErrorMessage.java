package ar.com.santander.rio.mbanking.app.ui.forms;

public class ErrorMessage {
    private String a = "";
    private Boolean b = Boolean.valueOf(false);

    public String getMessage() {
        return this.a;
    }

    public void setMessage(String str) {
        this.a = str;
    }

    public Boolean getValid() {
        return this.b;
    }

    public void setValid(Boolean bool) {
        this.b = bool;
    }

    public ErrorMessage(String str, Boolean bool) {
        this.a = str;
        this.b = bool;
    }
}
