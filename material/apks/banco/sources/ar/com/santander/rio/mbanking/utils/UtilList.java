package ar.com.santander.rio.mbanking.utils;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UtilList {
    public static List<String> orderArrayIndexStringNumeric(ArrayList<String> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                arrayList2.add(Integer.valueOf(Integer.parseInt((String) it.next())));
            } catch (NumberFormatException e) {
                Log.e("@dev", "Error al crear el entero desde una cadena", e);
            }
        }
        Collections.sort(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((Integer) it2.next()).toString());
        }
        return arrayList3;
    }
}
