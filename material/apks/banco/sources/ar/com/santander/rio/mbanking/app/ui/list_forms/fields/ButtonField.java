package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class ButtonField extends Data implements IButtonField {
    private String a;
    private TypeField b = TypeField.BUTTON;

    public ButtonField(String str) {
        this.a = str;
    }

    public ButtonField() {
    }

    public static ButtonField newInstance() {
        return new ButtonField();
    }

    public static ButtonField newInstance(String str) {
        if (!str.isEmpty()) {
            return new ButtonField(str);
        }
        return null;
    }

    public IButtonField setText(String str) {
        this.a = str;
        return this;
    }

    public String getText() {
        return this.a;
    }

    public int getType() {
        return this.b.getValue();
    }
}
