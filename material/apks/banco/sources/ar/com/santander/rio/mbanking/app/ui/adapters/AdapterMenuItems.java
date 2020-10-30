package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectMenuItem;

public class AdapterMenuItems extends ArrayAdapter<ObjectMenuItem> {
    Context a;
    int b;
    ObjectMenuItem[] c = null;

    public AdapterMenuItems(Context context, int i, ObjectMenuItem[] objectMenuItemArr) {
        super(context, i, objectMenuItemArr);
        this.b = i;
        this.a = context;
        this.c = objectMenuItemArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((Activity) this.a).getLayoutInflater().inflate(this.b, viewGroup, false);
        }
        ObjectMenuItem objectMenuItem = this.c[i];
        return view;
    }
}
