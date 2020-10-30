package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class Footer extends Data implements IFooter {
    private String a;
    private TypeField b = TypeField.FOOTER;

    public Footer() {
    }

    public Footer(String str) {
        this.a = str;
    }

    public static Footer newInstance() {
        return new Footer();
    }

    public static Footer newInstance(String str) {
        if (!str.isEmpty()) {
            return new Footer(str);
        }
        return null;
    }

    public IFooter setLabel(String str) {
        this.a = str;
        return this;
    }

    public String getLabel() {
        return this.a;
    }

    public int getType() {
        return this.b.getValue();
    }
}
