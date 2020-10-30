package ar.com.santander.rio.mbanking.app.ui.Model;

public class Hyperlink {
    protected String mText;
    protected String mTitle;

    public Hyperlink() {
    }

    public Hyperlink(String str, String str2) {
        this.mTitle = str;
        this.mText = str2;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String str) {
        this.mText = str;
    }
}
