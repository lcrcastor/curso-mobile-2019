package android.support.v4.view;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public abstract class ActionProvider {
    private final Context a;
    private SubUiVisibilityListener b;
    private VisibilityListener c;

    @RestrictTo({Scope.LIBRARY_GROUP})
    public interface SubUiVisibilityListener {
        void onSubUiVisibilityChanged(boolean z);
    }

    public interface VisibilityListener {
        void onActionProviderVisibilityChanged(boolean z);
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public abstract View onCreateActionView();

    public boolean onPerformDefaultAction() {
        return false;
    }

    public void onPrepareSubMenu(SubMenu subMenu) {
    }

    public boolean overridesItemVisibility() {
        return false;
    }

    public ActionProvider(Context context) {
        this.a = context;
    }

    public Context getContext() {
        return this.a;
    }

    public View onCreateActionView(MenuItem menuItem) {
        return onCreateActionView();
    }

    public void refreshVisibility() {
        if (this.c != null && overridesItemVisibility()) {
            this.c.onActionProviderVisibilityChanged(isVisible());
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void subUiVisibilityChanged(boolean z) {
        if (this.b != null) {
            this.b.onSubUiVisibilityChanged(z);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setSubUiVisibilityListener(SubUiVisibilityListener subUiVisibilityListener) {
        this.b = subUiVisibilityListener;
    }

    public void setVisibilityListener(VisibilityListener visibilityListener) {
        if (!(this.c == null || visibilityListener == null)) {
            StringBuilder sb = new StringBuilder();
            sb.append("setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this ");
            sb.append(getClass().getSimpleName());
            sb.append(" instance while it is still in use somewhere else?");
            Log.w("ActionProvider(support)", sb.toString());
        }
        this.c = visibilityListener;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void reset() {
        this.c = null;
        this.b = null;
    }
}
