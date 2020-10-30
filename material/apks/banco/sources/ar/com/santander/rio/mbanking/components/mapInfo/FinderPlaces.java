package ar.com.santander.rio.mbanking.components.mapInfo;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.model.general.PlaceMap;
import java.util.ArrayList;

public interface FinderPlaces {
    ArrayList<PlaceMap> getListPlaceMap(StringBuilder sb);

    String getStringToSearch(String str, String str2, Context context);
}
