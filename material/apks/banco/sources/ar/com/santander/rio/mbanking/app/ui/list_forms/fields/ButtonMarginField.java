package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class ButtonMarginField extends Data implements IButtonField {
    private String a;
    private TypeField b = TypeField.BUTTON_MARGIN;

    public ButtonMarginField(String str) {
        this.a = str;
    }

    public ButtonMarginField() {
    }

    public static ButtonMarginField newInstance() {
        return new ButtonMarginField();
    }

    public static ButtonMarginField newInstance(String str) {
        if (!str.isEmpty()) {
            return new ButtonMarginField(str);
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
