package ar.com.santander.rio.mbanking.app.ui.Model;

public class MenuOption {
    private OptionType a;
    private boolean b;
    private boolean c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;

    public enum OptionType {
        NAME,
        DISCONNECT,
        OPTION,
        HOME,
        PRIVATE_ACCESS,
        RESTRICTED_NO_CONNECTION
    }

    public MenuOption() {
    }

    public MenuOption(OptionType optionType, boolean z, String str, String str2, String str3, String str4) {
        this.a = optionType;
        this.d = str;
        this.e = str2;
        this.f = str3;
        this.b = z;
        this.g = str4;
    }

    public String getTitle() {
        return this.d;
    }

    public void setTitle(String str) {
        this.d = str;
    }

    public String getSubtitle() {
        return this.e;
    }

    public void setSubtitle(String str) {
        this.e = str;
    }

    public String getSubtitle2() {
        return this.f;
    }

    public void setSubtitle2(String str) {
        this.f = str;
    }

    public boolean isSelected() {
        return this.b;
    }

    public void setSelected(boolean z) {
        this.b = z;
    }

    public String getAction() {
        return this.g != null ? this.g : "";
    }

    public void setAction(String str) {
        this.g = str;
    }

    public OptionType getOptionType() {
        return this.a;
    }

    public void setOptionType(OptionType optionType) {
        this.a = optionType;
    }

    public String getValue() {
        return this.h;
    }

    public void setValue(String str) {
        this.h = str;
    }

    public boolean isPressed() {
        return this.c;
    }

    public void setPressed(boolean z) {
        this.c = z;
    }
}
