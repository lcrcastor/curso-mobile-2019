package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import ar.com.santander.rio.mbanking.app.ui.forms.InputType;

public class FormDataTitle extends FormData implements IFormDataTitle {
    protected InputType INPUTTYPE = InputType.TITLE;
    private String a;

    public FormDataTitle(String str) {
        this.a = str;
    }

    public FormDataTitle() {
    }

    public static FormDataTitle newInstance() {
        return new FormDataTitle();
    }

    public static FormDataTitle newInstance(String str) {
        if (!str.isEmpty()) {
            return new FormDataTitle(str);
        }
        return null;
    }

    public IFormDataTitle setTitle(String str) {
        this.a = str;
        return this;
    }

    public String getTitle() {
        return this.a;
    }

    public int getType() {
        return this.INPUTTYPE.getValue();
    }
}
