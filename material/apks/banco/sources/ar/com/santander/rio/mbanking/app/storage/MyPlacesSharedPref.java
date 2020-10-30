package ar.com.santander.rio.mbanking.app.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ar.com.santander.rio.mbanking.services.model.general.PlaceMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class MyPlacesSharedPref {
    private final String a = "index_my_places";
    private List<PlaceMap> b;
    private int c = -1;
    private SharedPreferences d;

    public MyPlacesSharedPref(Context context, int i) {
        this.c = i;
        limitMyPlaces(i);
        this.d = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void limitMyPlaces(int i) {
        if (i > 0) {
            this.b = new ArrayList(i);
        } else {
            this.b = new ArrayList();
        }
    }

    public void addNewPlace(PlaceMap placeMap) {
        this.b = getMyPlacesFromStorage();
        a(placeMap);
        a();
    }

    private void a(PlaceMap placeMap) {
        this.b.add(0, placeMap);
        if (this.c > 0 && this.b.size() > this.c) {
            this.b.remove(this.b.size() - 1);
        }
    }

    private void a() {
        this.d.edit().putString("index_my_places", new Gson().toJson((Object) this.b)).commit();
    }

    public List<PlaceMap> getMyPlacesFromStorage() {
        try {
            if (this.d.contains("index_my_places")) {
                return (List) new Gson().fromJson(this.d.getString("index_my_places", null), new TypeToken<ArrayList<PlaceMap>>() {
                }.getType());
            }
        } catch (Exception unused) {
        }
        return this.b;
    }
}
