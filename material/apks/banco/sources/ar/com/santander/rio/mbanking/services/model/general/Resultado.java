package ar.com.santander.rio.mbanking.services.model.general;

public class Resultado {
    public static int LOGIN_STATUS_ERROR = 1;
    public static int LOGIN_STATUS_OK = 0;
    public static int LOGIN_STATUS_WARNING = -1;
    int result;

    public int getResult() {
        return this.result;
    }

    public void setResult(int i) {
        this.result = i;
    }
}
