package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class AvisoRojoClaro extends Data implements IAviso {
    private String a;
    private String b;
    private String c;
    private TypeField d = TypeField.AVISO_ROJO_CLARO;

    public AvisoRojoClaro() {
    }

    public static AvisoRojoClaro newInstance() {
        return new AvisoRojoClaro();
    }

    public AvisoRojoClaro(String str, String str2) {
        super(str, str2);
    }

    public AvisoRojoClaro setLabel(String str) {
        this.a = str;
        return this;
    }

    public AvisoRojoClaro setSpanable(String str) {
        this.b = str;
        return this;
    }

    public AvisoRojoClaro setTitle(String str) {
        this.c = str;
        return this;
    }

    public String getTitle() {
        return this.c;
    }

    public String getSpanable() {
        return this.b;
    }

    public String getLabel() {
        return this.a;
    }

    public int getType() {
        return this.d.getValue();
    }
}
