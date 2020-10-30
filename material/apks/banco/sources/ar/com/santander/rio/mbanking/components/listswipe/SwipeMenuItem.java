package ar.com.santander.rio.mbanking.components.listswipe;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class SwipeMenuItem {
    private int a;
    private Context b;
    private String c;
    private Drawable d;
    private Drawable e;
    private int f;
    private int g;
    private int h;

    public SwipeMenuItem(Context context) {
        this.b = context;
    }

    public int getId() {
        return this.a;
    }

    public void setId(int i) {
        this.a = i;
    }

    public int getTitleColor() {
        return this.f;
    }

    public int getTitleSize() {
        return this.g;
    }

    public void setTitleSize(int i) {
        this.g = i;
    }

    public void setTitleColor(int i) {
        this.f = i;
    }

    public String getTitle() {
        return this.c;
    }

    public void setTitle(String str) {
        this.c = str;
    }

    public void setTitle(int i) {
        setTitle(this.b.getString(i));
    }

    public Drawable getIcon() {
        return this.d;
    }

    public void setIcon(Drawable drawable) {
        this.d = drawable;
    }

    public void setIcon(int i) {
        this.d = this.b.getResources().getDrawable(i);
    }

    public Drawable getBackground() {
        return this.e;
    }

    public void setBackground(Drawable drawable) {
        this.e = drawable;
    }

    public void setBackground(int i) {
        this.e = this.b.getResources().getDrawable(i);
    }

    public int getWidth() {
        return this.h;
    }

    public void setWidth(int i) {
        this.h = i;
    }
}
