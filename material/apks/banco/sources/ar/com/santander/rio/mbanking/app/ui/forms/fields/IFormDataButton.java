package ar.com.santander.rio.mbanking.app.ui.forms.fields;

public interface IFormDataButton extends IFormData {
    String getText();

    boolean isEnableButton();

    IFormDataButton setEnableButton(boolean z);

    IFormDataButton setText(String str);
}
