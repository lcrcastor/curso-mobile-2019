package ar.com.santander.rio.mbanking.components.mapInfo;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.model.general.PlaceMap;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleGeo implements FinderPlaces {
    public String getStringToSearch(String str, String str2, Context context) {
        try {
            return str.replace("/xml", Constants.OUT_JSON).replace("{query}", URLEncoder.encode(str2, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        } catch (Exception unused) {
            return str;
        }
    }

    public ArrayList<PlaceMap> getListPlaceMap(StringBuilder sb) {
        try {
            JSONArray jSONArray = new JSONObject(sb.toString()).getJSONArray("results");
            ArrayList<PlaceMap> arrayList = new ArrayList<>(jSONArray.length());
            int i = 0;
            while (i < jSONArray.length()) {
                try {
                    PlaceMap placeMap = new PlaceMap();
                    placeMap.setName(jSONArray.getJSONObject(i).getString("formatted_address"));
                    placeMap.setPlace_id(jSONArray.getJSONObject(i).getString("place_id"));
                    arrayList.add(placeMap);
                    i++;
                } catch (Exception unused) {
                    return arrayList;
                }
            }
            return arrayList;
        } catch (Exception unused2) {
            return null;
        }
    }
}
