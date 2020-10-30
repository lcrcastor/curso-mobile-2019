package ar.com.santander.rio.mbanking.components.mapInfo;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.model.general.PlaceMap;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GooglePlace implements FinderPlaces {
    public String getStringToSearch(String str, String str2, Context context) {
        StringBuilder sb = new StringBuilder("/place/queryautocomplete/json");
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("?sensor=true&key=");
            sb2.append(context.getString(R.string.google_maps_api_key));
            sb.append(sb2.toString());
            sb.append("&components=country:ES");
            sb.append("&language=es");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("&input=");
            sb3.append(URLEncoder.encode(str2, "utf8"));
            sb.append(sb3.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception unused) {
        }
        return sb.toString();
    }

    public ArrayList<PlaceMap> getListPlaceMap(StringBuilder sb) {
        try {
            JSONArray jSONArray = new JSONObject(sb.toString()).getJSONArray("predictions");
            ArrayList<PlaceMap> arrayList = new ArrayList<>(jSONArray.length());
            int i = 0;
            while (i < jSONArray.length()) {
                try {
                    PlaceMap placeMap = new PlaceMap();
                    placeMap.setName(jSONArray.getJSONObject(i).getString("description"));
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
