package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.Locale;

public class FormDataText extends FormData implements Parcelable, IFormDataText {
    public static final Creator<FormDataText> CREATOR = new Creator<FormDataText>() {
        /* renamed from: a */
        public FormDataText createFromParcel(Parcel parcel) {
            return new FormDataText(parcel);
        }

        /* renamed from: a */
        public FormDataText[] newArray(int i) {
            return new FormDataText[i];
        }
    };
    private String a = "abcdefghijklmnñopqrstuvwxyzQWERTYUIOPASDFGHJKLÑZXCVBNMÁÉÍÓÚ1234567890áéíóú ,.";
    private InputType b = InputType.TEXT;
    private int c = 1;
    private Integer d = Integer.valueOf(100);

    public int describeContents() {
        return 0;
    }

    public IFormData setRequiredMessage(String str) {
        return null;
    }

    protected FormDataText(Parcel parcel) {
        super(parcel);
        this.a = parcel.readString();
        this.c = parcel.readInt();
        if (parcel.readByte() == 0) {
            this.d = null;
        } else {
            this.d = Integer.valueOf(parcel.readInt());
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.a);
        parcel.writeInt(this.c);
        if (this.d == null) {
            parcel.writeByte(0);
            return;
        }
        parcel.writeByte(1);
        parcel.writeInt(this.d.intValue());
    }

    public Integer getMaxOfCharacters() {
        return this.d;
    }

    public String getDigits() {
        return this.a;
    }

    public IFormDataText setDigits(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.a = str;
        }
        return this;
    }

    public IFormDataText setMaxOfCharacters(Integer num) {
        if (num.intValue() > 0) {
            this.d = num;
        }
        return this;
    }

    public FormDataText(Locale locale) {
        this.locale = locale;
    }

    public static FormDataText newInstance(@NonNull Locale locale) {
        return new FormDataText(locale);
    }

    public void ApplyAccesibilityFiltersLabels() {
        String str = "";
        if (this.valueText != null) {
            str = this.valueText;
            for (Object obj : this.accesibilityValueFilters) {
            }
        }
        this.contentDescriptionLabelText = str;
    }

    public void ApplyAccesibilityFiltersValues() {
        String str = "";
        if (this.valueText != null) {
            str = this.valueText;
            for (Object obj : this.accesibilityValueFilters) {
            }
        }
        this.contentDescriptionValueText = str;
    }

    public int getType() {
        return this.b.getValue();
    }

    public int getKeyboardType() {
        return this.c;
    }

    public IFormDataText setKeyboardType(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(2));
        arrayList.add(Integer.valueOf(3));
        arrayList.add(Integer.valueOf(1));
        if (arrayList.contains(Integer.valueOf(i))) {
            this.c = i;
        }
        return this;
    }

    public ErrorMessage isValid() {
        ErrorMessage errorMessage = new ErrorMessage("", Boolean.valueOf(true));
        return (!this.requiered.booleanValue() || !TextUtils.isEmpty(this.valueText)) ? errorMessage : new ErrorMessage(this.requieredMessage, Boolean.valueOf(false));
    }

    public Locale getLocale() {
        return this.locale;
    }
}
