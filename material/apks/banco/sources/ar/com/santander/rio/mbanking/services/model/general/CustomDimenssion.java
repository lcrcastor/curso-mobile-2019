package ar.com.santander.rio.mbanking.services.model.general;

public class CustomDimenssion {
    private int customDimenssion;
    private String parametro;
    private String screen;

    public CustomDimenssion() {
    }

    public CustomDimenssion(int i, String str, String str2) {
        this.customDimenssion = i;
        this.parametro = str;
        this.screen = str2;
    }

    public int getCustomDimenssion() {
        return this.customDimenssion;
    }

    public void setCustomDimenssion(int i) {
        this.customDimenssion = i;
    }

    public String getParametro() {
        return this.parametro;
    }

    public void setParametro(String str) {
        this.parametro = str;
    }

    public String getScreen() {
        return this.screen;
    }

    public void setScreen(String str) {
        this.screen = str;
    }
}
