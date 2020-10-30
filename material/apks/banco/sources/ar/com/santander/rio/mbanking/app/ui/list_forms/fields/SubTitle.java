package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class SubTitle extends Data implements ISubTitle {
    private String a;
    private TypeField b = TypeField.SUB_TITLE;

    private SubTitle(String str) {
        this.a = str;
    }

    public SubTitle() {
    }

    public static SubTitle newInstance(String str) {
        if (!str.isEmpty()) {
            return new SubTitle(str);
        }
        return null;
    }

    public static SubTitle newInstance() {
        return new SubTitle();
    }

    public ISubTitle setSubTitle(String str) {
        this.a = str;
        return this;
    }

    public String getSubTitle() {
        return this.a;
    }

    public int getType() {
        return this.b.getValue();
    }
}
