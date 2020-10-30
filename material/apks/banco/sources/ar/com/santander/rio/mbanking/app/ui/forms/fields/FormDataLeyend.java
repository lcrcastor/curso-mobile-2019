package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.Locale;

public class FormDataLeyend extends FormData implements Parcelable, IFormDataLeyend {
    public static final Creator<FormDataLeyend> CREATOR = new Creator<FormDataLeyend>() {
        /* renamed from: a */
        public FormDataLeyend createFromParcel(Parcel parcel) {
            return new FormDataLeyend(parcel);
        }

        /* renamed from: a */
        public FormDataLeyend[] newArray(int i) {
            return new FormDataLeyend[i];
        }
    };

    public void ApplyAccesibilityFiltersLabels() {
    }

    public void ApplyAccesibilityFiltersValues() {
    }

    public int describeContents() {
        return 0;
    }

    protected FormDataLeyend(Parcel parcel) {
        super(parcel);
    }

    public FormDataLeyend(Locale locale) {
        this.locale = locale;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public ErrorMessage isValid() {
        return new ErrorMessage("", Boolean.valueOf(true));
    }

    public Locale getLocale() {
        return this.locale;
    }

    public static FormDataLeyend newInstance(@NonNull Locale locale) {
        return new FormDataLeyend(locale);
    }

    public int getType() {
        return InputType.LEYEND.getValue();
    }
}
