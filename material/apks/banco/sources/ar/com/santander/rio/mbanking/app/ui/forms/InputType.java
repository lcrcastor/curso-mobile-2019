package ar.com.santander.rio.mbanking.app.ui.forms;

public enum InputType {
    NONE(0),
    TEXT(1),
    STRING_SELECTION(2),
    DATE(3),
    STRING_SELECTION_KEY_VALUE(4),
    INTENT_SELECTION(5),
    SECTION(6),
    LEYEND(7),
    MEDIO_PAGO_SELECTION(8),
    RECEIPT_FOOTER(9),
    CHECKBOX(10),
    TITLE(11),
    HEADER(12),
    BUTTON_RED(13);
    
    private final int a;

    private InputType(int i) {
        this.a = i;
    }

    public int getValue() {
        return this.a;
    }
}
