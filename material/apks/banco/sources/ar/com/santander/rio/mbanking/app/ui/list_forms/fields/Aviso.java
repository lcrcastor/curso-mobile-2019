package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class Aviso extends Data implements IAviso {
    private String a;
    private String b;
    private TypeField c = TypeField.AVISO;

    public IAviso setLabel(String str) {
        this.a = str;
        return this;
    }

    public IAviso setSpanable(String str) {
        this.b = str;
        return this;
    }

    public String getSpanable() {
        return this.b;
    }

    public String getLabel() {
        return this.a;
    }

    public int getType() {
        return this.c.getValue();
    }
}
