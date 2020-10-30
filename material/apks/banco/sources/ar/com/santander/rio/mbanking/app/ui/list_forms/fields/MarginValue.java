package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

public class MarginValue {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;

    public MarginValue(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public MarginValue() {
    }

    public int getTop() {
        return this.a;
    }

    public void setTop(int i) {
        this.a = i;
    }

    public int getBottom() {
        return this.b;
    }

    public void setBottom(int i) {
        this.b = i;
    }

    public int getRight() {
        return this.c;
    }

    public void setRight(int i) {
        this.c = i;
    }

    public int getLeft() {
        return this.d;
    }

    public void setLeft(int i) {
        this.d = i;
    }
}
