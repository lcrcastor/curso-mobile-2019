package ar.com.santander.rio.mbanking.app.ui.forms.fields;

public interface IFormDataText extends IFormData {
    String getDigits();

    int getKeyboardType();

    Integer getMaxOfCharacters();

    IFormDataText setDigits(String str);

    IFormDataText setKeyboardType(int i);

    IFormDataText setMaxOfCharacters(Integer num);
}
