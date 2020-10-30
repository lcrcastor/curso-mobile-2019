package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class AccessibilityEvent {
    public String accessibilityText;
    public String originalText;

    public AccessibilityEvent() {
    }

    public AccessibilityEvent(String str, String str2) {
        this.originalText = str;
        this.accessibilityText = str2;
    }
}
