package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import ar.com.santander.rio.mbanking.app.ui.forms.InputType;

public class FormDataButton extends FormData implements IFormDataButton {
    protected InputType INPUTTYPE = InputType.BUTTON_RED;
    private String a;
    private boolean b;

    public FormDataButton(String str) {
        this.a = str;
    }

    public FormDataButton() {
    }

    public static FormDataButton newInstance() {
        return new FormDataButton();
    }

    public static FormDataButton newInstance(String str) {
        if (!str.isEmpty()) {
            return new FormDataButton(str);
        }
        return null;
    }

    public IFormDataButton setText(String str) {
        this.a = str;
        return this;
    }

    public IFormDataButton setEnableButton(boolean z) {
        this.b = z;
        return this;
    }

    public String getText() {
        return this.a;
    }

    public boolean isEnableButton() {
        return this.b;
    }

    public int getType() {
        return this.INPUTTYPE.getValue();
    }
}
