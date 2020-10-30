package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.Locale;

public class FormDataSection extends FormData implements Parcelable, IFormDataSection {
    public static final Creator<FormDataSection> CREATOR = new Creator<FormDataSection>() {
        /* renamed from: a */
        public FormDataSection createFromParcel(Parcel parcel) {
            return new FormDataSection(parcel);
        }

        /* renamed from: a */
        public FormDataSection[] newArray(int i) {
            return new FormDataSection[i];
        }
    };
    protected Boolean requiered = Boolean.valueOf(false);

    public void ApplyAccesibilityFiltersLabels() {
    }

    public void ApplyAccesibilityFiltersValues() {
    }

    public int describeContents() {
        return 0;
    }

    public Locale getLocale() {
        return null;
    }

    public FormDataSection() {
    }

    protected FormDataSection(Parcel parcel) {
        Boolean bool;
        super(parcel);
        boolean z = false;
        byte readByte = parcel.readByte();
        if (readByte == 0) {
            bool = null;
        } else {
            if (readByte == 1) {
                z = true;
            }
            bool = Boolean.valueOf(z);
        }
        this.requiered = bool;
    }

    public FormDataSection(Locale locale) {
        this.locale = locale;
    }

    public static FormDataSection newInstance(@NonNull Locale locale) {
        return new FormDataSection(locale);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        int i2 = this.requiered == null ? 0 : this.requiered.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i2);
    }

    public ErrorMessage isValid() {
        return new ErrorMessage("", Boolean.valueOf(true));
    }

    public int getType() {
        return InputType.SECTION.getValue();
    }
}
