package android.support.v7.view.menu;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.view.ActionProvider.VisibilityListener;
import android.view.MenuItem;
import android.view.View;

@RequiresApi(16)
@RestrictTo({Scope.LIBRARY_GROUP})
class MenuItemWrapperJB extends MenuItemWrapperICS {

    class ActionProviderWrapperJB extends ActionProviderWrapper implements VisibilityListener {
        ActionProvider.VisibilityListener c;

        public ActionProviderWrapperJB(Context context, android.view.ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        public View onCreateActionView(MenuItem menuItem) {
            return this.a.onCreateActionView(menuItem);
        }

        public boolean overridesItemVisibility() {
            return this.a.overridesItemVisibility();
        }

        public boolean isVisible() {
            return this.a.isVisible();
        }

        public void refreshVisibility() {
            this.a.refreshVisibility();
        }

        public void setVisibilityListener(ActionProvider.VisibilityListener visibilityListener) {
            this.c = visibilityListener;
            this.a.setVisibilityListener(visibilityListener != null ? this : null);
        }

        public void onActionProviderVisibilityChanged(boolean z) {
            if (this.c != null) {
                this.c.onActionProviderVisibilityChanged(z);
            }
        }
    }

    MenuItemWrapperJB(Context context, SupportMenuItem supportMenuItem) {
        super(context, supportMenuItem);
    }

    /* access modifiers changed from: 0000 */
    public ActionProviderWrapper a(android.view.ActionProvider actionProvider) {
        return new ActionProviderWrapperJB(this.a, actionProvider);
    }
}
