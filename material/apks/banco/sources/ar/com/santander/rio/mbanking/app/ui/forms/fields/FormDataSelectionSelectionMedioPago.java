package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FormDataSelectionSelectionMedioPago extends FormDataSelection implements IFormDataSelectionMedioPago {
    public static final Creator<FormDataSelectionKeyValue> CREATOR = new Creator<FormDataSelectionKeyValue>() {
        /* renamed from: a */
        public FormDataSelectionKeyValue createFromParcel(Parcel parcel) {
            return new FormDataSelectionKeyValue(parcel);
        }

        /* renamed from: a */
        public FormDataSelectionKeyValue[] newArray(int i) {
            return new FormDataSelectionKeyValue[i];
        }
    };
    private InputType a = InputType.MEDIO_PAGO_SELECTION;
    private List<MedioPago> b = new ArrayList();
    private MedioPago c = new MedioPago();

    public static class MedioPago {
        private String a;
        private String b;
        private String c;
        /* access modifiers changed from: private */
        public String d;
        private boolean e;
        private boolean f;

        public MedioPago() {
        }

        MedioPago(MedioPago medioPago) {
            this.a = medioPago.a;
            this.b = medioPago.b;
            this.c = medioPago.c;
            this.d = medioPago.d;
            this.e = medioPago.e;
            this.f = medioPago.f;
        }

        public String getTipo() {
            return this.a;
        }

        public void setTipo(String str) {
            this.a = str;
        }

        public String getNro() {
            return this.b;
        }

        public void setNro(String str) {
            this.b = str;
        }

        public String getSucursal() {
            return this.c;
        }

        public void setSucursal(String str) {
            this.c = str;
        }

        public String getDescripcion() {
            return this.d;
        }

        public void setDescripcion(String str) {
            this.d = str;
        }

        public boolean isAccount() {
            return this.e;
        }

        public void setAccount(boolean z) {
            this.e = z;
        }

        public boolean isCreditCard() {
            return this.f;
        }

        public void setCreditCard(boolean z) {
            this.f = z;
        }

        public boolean isEmpty() {
            return TextUtils.isEmpty(this.a) || TextUtils.isEmpty(this.b) || TextUtils.isEmpty(this.d);
        }
    }

    public int describeContents() {
        return 0;
    }

    public IFormDataSelection setStringList(ArrayList<String> arrayList) {
        return this;
    }

    public static FormDataSelectionSelectionMedioPago newInstance(@NonNull Locale locale) {
        return new FormDataSelectionSelectionMedioPago(locale);
    }

    private FormDataSelectionSelectionMedioPago(Locale locale) {
        super(locale);
    }

    public void setValueTextData(String str) {
        if (str != null) {
            this.valueText = str;
        }
    }

    public IFormDataSelectionMedioPago setStringMedioPagoList(List<MedioPago> list) {
        if (list != null && list.size() > 0) {
            this.b = list;
        }
        return this;
    }

    public List<MedioPago> getStringMedioPagoList() {
        return this.b;
    }

    public MedioPago getMedioPagoValue() {
        return this.c;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public ErrorMessage isValid() {
        ErrorMessage errorMessage = new ErrorMessage("", Boolean.valueOf(true));
        return (this.c == null || !this.c.isEmpty() || !this.requiered.booleanValue()) ? errorMessage : new ErrorMessage(this.messageValidation, Boolean.valueOf(false));
    }

    public String getTitle() {
        return this.selectionTitle;
    }

    public IFormData setValueText(String str) {
        Iterator it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MedioPago medioPago = (MedioPago) it.next();
            if (medioPago.d.equalsIgnoreCase(str)) {
                this.c = new MedioPago(medioPago);
                break;
            }
        }
        return this;
    }

    public ArrayList<String> getStringList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (MedioPago a2 : this.b) {
            arrayList.add(a2.d);
        }
        return arrayList;
    }

    public int getType() {
        return this.a.getValue();
    }
}
