package ar.com.santander.rio.mbanking.app.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class MyLocalAgendaDestPref {
    private final String a = "index_my_agenda_dest";
    private List<ObjectDestinatarios> b;
    private SharedPreferences c;

    private void a() {
    }

    public MyLocalAgendaDestPref(Context context) {
        this.c = PreferenceManager.getDefaultSharedPreferences(context);
        this.b = new ArrayList();
    }

    public void add(ObjectDestinatarios objectDestinatarios) {
        this.b = getMyAgendaDestFromStorage();
        this.b.add(0, objectDestinatarios);
        a();
        b();
    }

    private void b() {
        this.c.edit().putString("index_my_agenda_dest", new Gson().toJson((Object) this.b)).commit();
    }

    public List<ObjectDestinatarios> getMyAgendaDestFromStorage() {
        try {
            if (this.c.contains("index_my_agenda_dest")) {
                this.b = (List) new Gson().fromJson(this.c.getString("index_my_agenda_dest", null), ObjectDestinatarios.class);
            }
        } catch (Exception unused) {
        }
        return this.b;
    }
}
