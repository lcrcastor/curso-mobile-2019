package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.Locale;

public class FormDataDate extends FormData implements Parcelable, IFormDataDate {
    public static final Creator<FormDataDate> CREATOR = new Creator<FormDataDate>() {
        /* renamed from: a */
        public FormDataDate createFromParcel(Parcel parcel) {
            return new FormDataDate(parcel);
        }

        /* renamed from: a */
        public FormDataDate[] newArray(int i) {
            return new FormDataDate[i];
        }
    };
    protected InputType INPUTTYPE = InputType.DATE;
    protected String dateFormat = "dd/MM/yy";
    protected String selectionTitle = "Ingresá la fecha";

    public void ApplyAccesibilityFiltersLabels() {
    }

    public void ApplyAccesibilityFiltersValues() {
    }

    public int describeContents() {
        return 0;
    }

    public FormDataDate(Locale locale) {
        this.locale = locale;
    }

    protected FormDataDate(Parcel parcel) {
        super(parcel);
        this.selectionTitle = parcel.readString();
        this.dateFormat = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.selectionTitle);
        parcel.writeString(this.dateFormat);
    }

    public static FormDataDate newInstance(@NonNull Locale locale) {
        return new FormDataDate(locale);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int getType() {
        return this.INPUTTYPE.getValue();
    }

    public ErrorMessage isValid() {
        return new ErrorMessage("", Boolean.valueOf(true));
    }

    public IFormData setDateFormat(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.dateFormat = str;
        }
        return this;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }
}
