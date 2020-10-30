package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import ar.com.santander.rio.mbanking.app.ui.forms.InputType;

public class FormDataHeader extends FormData implements IFormDataHeader {
    protected InputType INPUTTYPE = InputType.HEADER;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public FormDataHeader() {
    }

    public FormDataHeader(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public static FormDataHeader newInstance() {
        return new FormDataHeader();
    }

    public static FormDataHeader newInstance(String str, String str2) {
        if (str.isEmpty() || str2.isEmpty()) {
            return null;
        }
        return new FormDataHeader(str, str2);
    }

    public IFormDataHeader setIconUrl(String str) {
        this.e = str;
        return this;
    }

    public IFormDataHeader setTitle(String str) {
        this.a = str;
        return this;
    }

    public IFormDataHeader setSubTitle(String str) {
        this.b = str;
        return this;
    }

    public IFormDataHeader setDescription(String str) {
        this.c = str;
        return this;
    }

    public String getContentDescriptionSubTitle() {
        return this.d;
    }

    public IFormDataHeader setContentDescriptionSubTitle(String str) {
        this.d = str;
        return this;
    }

    public String getTitle() {
        return this.a;
    }

    public String getIconUrl() {
        return this.e;
    }

    public String getSubTitle() {
        return this.b;
    }

    public String getDescription() {
        return this.c;
    }

    public int getType() {
        return this.INPUTTYPE.getValue();
    }
}
