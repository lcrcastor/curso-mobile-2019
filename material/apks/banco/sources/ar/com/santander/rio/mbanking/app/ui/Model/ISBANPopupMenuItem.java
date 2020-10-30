package ar.com.santander.rio.mbanking.app.ui.Model;

public class ISBANPopupMenuItem {
    public static final int DEFAULT = 3;
    public static final int ID_NOT_SET = 0;
    public static final int SELECTED = 2;
    public static final int TITLE = 1;
    private int a;
    private int b;
    private String c;

    public ISBANPopupMenuItem(String str) {
        this(3, str);
    }

    public ISBANPopupMenuItem(int i, String str) {
        this(i, 0, str);
    }

    public ISBANPopupMenuItem(int i, int i2, String str) {
        this.a = i;
        this.b = i2;
        this.c = str;
    }

    public int getMenuItemType() {
        return this.a;
    }

    public int getMenuItemID() {
        return this.b;
    }

    public String getmMenuItemText() {
        return this.c;
    }
}
