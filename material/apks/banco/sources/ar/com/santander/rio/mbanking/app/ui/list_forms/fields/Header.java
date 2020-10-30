package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class Header extends Data implements IHeader {
    private String a;
    private String b;
    private String c;
    private String d;
    private TypeField e = TypeField.HEADER;

    public Header() {
    }

    public Header(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public static Header newInstance() {
        return new Header();
    }

    public static Header newInstance(String str, String str2) {
        if (str.isEmpty() || str2.isEmpty()) {
            return null;
        }
        return new Header(str, str2);
    }

    public IHeader setTitle(String str) {
        this.a = str;
        return this;
    }

    public IHeader setSubTitle(String str) {
        this.b = str;
        return this;
    }

    public IHeader setDescription(String str) {
        this.c = str;
        return this;
    }

    public String getContentDescriptionSubTitle() {
        return this.d;
    }

    public IHeader setContentDescriptionSubTitle(String str) {
        this.d = str;
        return this;
    }

    public String getTitle() {
        return this.a;
    }

    public String getSubTitle() {
        return this.b;
    }

    public String getDescription() {
        return this.c;
    }

    public int getType() {
        return this.e.getValue();
    }
}
