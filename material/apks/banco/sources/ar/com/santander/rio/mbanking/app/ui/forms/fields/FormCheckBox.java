package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcelable;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.Locale;

public class FormCheckBox extends FormDataIntent implements Parcelable, IFormCheckBox {
    private InputType a = InputType.CHECKBOX;
    protected String description;
    protected String linkDescription;
    protected String url;

    public FormCheckBox(Locale locale) {
        this.locale = locale;
    }

    public static FormCheckBox newInstance(@NonNull Locale locale) {
        return new FormCheckBox(locale);
    }

    public IFormCheckBox setDescription(String str) {
        if (str != null) {
            this.description = str;
        }
        return this;
    }

    public IFormCheckBox setLink(String str) {
        if (str != null) {
            this.linkDescription = str;
        }
        return this;
    }

    public int getType() {
        return this.a.getValue();
    }

    public IFormCheckBox setUrl(String str) {
        if (str != null) {
            this.url = str;
        }
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() {
        return this.linkDescription;
    }

    public String getUrl() {
        return this.url;
    }
}
