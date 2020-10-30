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

public class FormDataSelection extends FormData implements Parcelable, IFormDataSelection {
    public static final Creator<FormDataSelection> CREATOR = new Creator<FormDataSelection>() {
        /* renamed from: a */
        public FormDataSelection createFromParcel(Parcel parcel) {
            return new FormDataSelection(parcel);
        }

        /* renamed from: a */
        public FormDataSelection[] newArray(int i) {
            return new FormDataSelection[i];
        }
    };
    protected InputType INPUTTYPE = InputType.STRING_SELECTION;
    protected String selectionTitle = "Seleccioná una opción";

    public void ApplyAccesibilityFiltersLabels() {
    }

    public void ApplyAccesibilityFiltersValues() {
    }

    public int describeContents() {
        return 0;
    }

    public FormDataSelection(Locale locale) {
        this.locale = locale;
    }

    protected FormDataSelection(Parcel parcel) {
        super(parcel);
        this.selectionTitle = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.selectionTitle);
    }

    public static FormDataSelection newInstance(@NonNull Locale locale) {
        return new FormDataSelection(locale);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int getType() {
        return this.INPUTTYPE.getValue();
    }

    public ErrorMessage isValid() {
        ErrorMessage errorMessage = new ErrorMessage("", Boolean.valueOf(true));
        return (!TextUtils.isEmpty(this.valueText) || !this.requiered.booleanValue()) ? errorMessage : new ErrorMessage(this.messageValidation, Boolean.valueOf(false));
    }

    public String getTitle() {
        return this.selectionTitle;
    }

    public IFormDataSelection setStringList(ArrayList<String> arrayList) {
        if (!arrayList.isEmpty()) {
            this.stringList = arrayList;
        }
        return this;
    }

    public ArrayList<String> getStringList() {
        return this.stringList;
    }
}
