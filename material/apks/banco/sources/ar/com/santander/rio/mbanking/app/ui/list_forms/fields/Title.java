package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class Title extends Data implements ITitle {
    private String a;
    private TypeField b = TypeField.TITLE;

    public Title(String str) {
        this.a = str;
    }

    public Title() {
    }

    public static Title newInstance() {
        return new Title();
    }

    public static Title newInstance(String str) {
        if (!str.isEmpty()) {
            return new Title(str);
        }
        return null;
    }

    public ITitle setTitle(String str) {
        this.a = str;
        return this;
    }

    public String getTitle() {
        return this.a;
    }

    public int getType() {
        return this.b.getValue();
    }
}
