package android.support.v7.app;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDelegateImplV9.PanelFeatureState;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import android.view.Window.Callback;
import java.util.List;

@RequiresApi(24)
class AppCompatDelegateImplN extends AppCompatDelegateImplV23 {

    class AppCompatWindowCallbackN extends AppCompatWindowCallbackV23 {
        AppCompatWindowCallbackN(Callback callback) {
            super(callback);
        }

        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            PanelFeatureState a = AppCompatDelegateImplN.this.a(0, true);
            if (a == null || a.j == null) {
                super.onProvideKeyboardShortcuts(list, menu, i);
            } else {
                super.onProvideKeyboardShortcuts(list, a.j, i);
            }
        }
    }

    AppCompatDelegateImplN(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    /* access modifiers changed from: 0000 */
    public Callback a(Callback callback) {
        return new AppCompatWindowCallbackN(callback);
    }
}
