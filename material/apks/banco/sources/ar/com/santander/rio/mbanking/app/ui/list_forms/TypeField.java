package ar.com.santander.rio.mbanking.app.ui.list_forms;

public enum TypeField {
    NONE(0),
    TITLE(1),
    SUB_TITLE(2),
    HEADER(3),
    ITEM(4),
    ITEM_IMAGE(5),
    ITEM_IMAGE_TURNO_CAJA(6),
    FOOTER(7),
    AVISO(8),
    AVISO_ROJO_CLARO(9),
    BUTTON(10),
    BUTTON_MARGIN(11);
    
    private final int a;

    private TypeField(int i) {
        this.a = i;
    }

    public int getValue() {
        return this.a;
    }
}
