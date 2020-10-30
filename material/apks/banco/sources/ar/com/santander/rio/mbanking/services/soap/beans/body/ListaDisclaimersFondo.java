package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaDisclaimersFondo implements Parcelable {
    public static final Creator<ListaDisclaimersFondo> CREATOR = new Creator<ListaDisclaimersFondo>() {
        public ListaDisclaimersFondo createFromParcel(Parcel parcel) {
            return new ListaDisclaimersFondo(parcel);
        }

        public ListaDisclaimersFondo[] newArray(int i) {
            return new ListaDisclaimersFondo[i];
        }
    };
    @SerializedName("disclaimer")
    private List<DisclaimerFondo> disclaimer;

    public int describeContents() {
        return 0;
    }

    public ListaDisclaimersFondo() {
    }

    public ListaDisclaimersFondo(List<DisclaimerFondo> list) {
        this.disclaimer = list;
    }

    protected ListaDisclaimersFondo(Parcel parcel) {
        this.disclaimer = new ArrayList();
        parcel.readList(this.disclaimer, DisclaimerFondo.class.getClassLoader());
    }

    public List<DisclaimerFondo> getDisclaimer() {
        return this.disclaimer;
    }

    public void setDisclaimer(List<DisclaimerFondo> list) {
        this.disclaimer = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.disclaimer);
    }
}
