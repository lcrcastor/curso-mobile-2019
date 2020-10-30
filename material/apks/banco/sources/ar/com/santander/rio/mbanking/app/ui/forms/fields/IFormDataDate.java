package ar.com.santander.rio.mbanking.app.ui.forms.fields;

public interface IFormDataDate extends IFormData {
    String getDateFormat();

    IFormData setDateFormat(String str);
}
