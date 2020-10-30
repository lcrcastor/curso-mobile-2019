package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcelable;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import java.util.Locale;

public interface IFormData extends Parcelable {
    void ApplyAccesibilityFiltersLabels();

    void ApplyAccesibilityFiltersValues();

    Boolean getEditable();

    String getHint();

    String getLabelText();

    Locale getLocale();

    int getPosition();

    String getTitle();

    int getType();

    String getValueText();

    ErrorMessage isValid();

    IFormData setEditable(Boolean bool);

    IFormData setHint(String str);

    IFormData setLabelText(String str);

    IFormData setLocale(Locale locale);

    IFormData setMessageValidation(String str);

    IFormData setPosition(int i);

    IFormData setRequired(Boolean bool);

    IFormData setRequiredMessage(String str);

    IFormData setTag(String str);

    IFormData setTitle(String str);

    IFormData setValueText(String str);

    IFormData setValueValidation(String str);
}
