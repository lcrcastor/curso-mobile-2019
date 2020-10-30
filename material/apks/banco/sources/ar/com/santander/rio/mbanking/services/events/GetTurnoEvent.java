package ar.com.santander.rio.mbanking.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoResponseBean;

public class GetTurnoEvent extends WebServiceEvent implements Parcelable {
    public static final Creator<GetTurnoEvent> CREATOR = new Creator<GetTurnoEvent>() {
        /* renamed from: a */
        public GetTurnoEvent createFromParcel(Parcel parcel) {
            return new GetTurnoEvent(parcel);
        }

        /* renamed from: a */
        public GetTurnoEvent[] newArray(int i) {
            return new GetTurnoEvent[i];
        }
    };
    private GetTurnoResponseBean a;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public GetTurnoEvent() {
    }

    protected GetTurnoEvent(Parcel parcel) {
    }

    public GetTurnoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetTurnoResponseBean getTurnoResponseBean) {
        this.a = getTurnoResponseBean;
    }
}
