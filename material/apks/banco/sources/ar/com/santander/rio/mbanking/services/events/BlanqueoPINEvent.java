package ar.com.santander.rio.mbanking.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinResponseBean;

public class BlanqueoPINEvent extends WebServiceEvent implements Parcelable {
    public static final Creator<BlanqueoPINEvent> CREATOR = new Creator<BlanqueoPINEvent>() {
        /* renamed from: a */
        public BlanqueoPINEvent createFromParcel(Parcel parcel) {
            return new BlanqueoPINEvent(parcel);
        }

        /* renamed from: a */
        public BlanqueoPINEvent[] newArray(int i) {
            return new BlanqueoPINEvent[i];
        }
    };
    private BlanqueoPinResponseBean a;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public BlanqueoPINEvent() {
    }

    public BlanqueoPINEvent(BlanqueoPinResponseBean blanqueoPinResponseBean) {
        this.a = blanqueoPinResponseBean;
    }

    protected BlanqueoPINEvent(Parcel parcel) {
    }

    public BlanqueoPinResponseBean getBlanqueoPinResponseBean() {
        return this.a;
    }

    public void setBlanqueoPinResponseBean(BlanqueoPinResponseBean blanqueoPinResponseBean) {
        this.a = blanqueoPinResponseBean;
    }
}
