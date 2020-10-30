package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ActivityChooserModel.OnChooseActivityListener;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public class ShareActionProvider extends ActionProvider {
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    final Context a;
    String b = DEFAULT_SHARE_HISTORY_FILE_NAME;
    OnShareTargetSelectedListener c;
    private int d = 4;
    private final ShareMenuItemOnMenuItemClickListener e = new ShareMenuItemOnMenuItemClickListener();
    private OnChooseActivityListener f;

    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent);
    }

    class ShareActivityChooserModelPolicy implements OnChooseActivityListener {
        ShareActivityChooserModelPolicy() {
        }

        public boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
            if (ShareActionProvider.this.c != null) {
                ShareActionProvider.this.c.onShareTargetSelected(ShareActionProvider.this, intent);
            }
            return false;
        }
    }

    class ShareMenuItemOnMenuItemClickListener implements OnMenuItemClickListener {
        ShareMenuItemOnMenuItemClickListener() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent b = ActivityChooserModel.a(ShareActionProvider.this.a, ShareActionProvider.this.b).b(menuItem.getItemId());
            if (b != null) {
                String action = b.getAction();
                if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                    ShareActionProvider.this.a(b);
                }
                ShareActionProvider.this.a.startActivity(b);
            }
            return true;
        }
    }

    public boolean hasSubMenu() {
        return true;
    }

    public ShareActionProvider(Context context) {
        super(context);
        this.a = context;
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener onShareTargetSelectedListener) {
        this.c = onShareTargetSelectedListener;
        a();
    }

    public View onCreateActionView() {
        ActivityChooserView activityChooserView = new ActivityChooserView(this.a);
        if (!activityChooserView.isInEditMode()) {
            activityChooserView.setActivityChooserModel(ActivityChooserModel.a(this.a, this.b));
        }
        TypedValue typedValue = new TypedValue();
        this.a.getTheme().resolveAttribute(R.attr.actionModeShareDrawable, typedValue, true);
        activityChooserView.setExpandActivityOverflowButtonDrawable(AppCompatResources.getDrawable(this.a, typedValue.resourceId));
        activityChooserView.setProvider(this);
        activityChooserView.setDefaultActionButtonContentDescription(R.string.abc_shareactionprovider_share_with_application);
        activityChooserView.setExpandActivityOverflowButtonContentDescription(R.string.abc_shareactionprovider_share_with);
        return activityChooserView;
    }

    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        ActivityChooserModel a2 = ActivityChooserModel.a(this.a, this.b);
        PackageManager packageManager = this.a.getPackageManager();
        int a3 = a2.a();
        int min = Math.min(a3, this.d);
        for (int i = 0; i < min; i++) {
            ResolveInfo a4 = a2.a(i);
            subMenu.add(0, i, i, a4.loadLabel(packageManager)).setIcon(a4.loadIcon(packageManager)).setOnMenuItemClickListener(this.e);
        }
        if (min < a3) {
            SubMenu addSubMenu = subMenu.addSubMenu(0, min, min, this.a.getString(R.string.abc_activity_chooser_view_see_all));
            for (int i2 = 0; i2 < a3; i2++) {
                ResolveInfo a5 = a2.a(i2);
                addSubMenu.add(0, i2, i2, a5.loadLabel(packageManager)).setIcon(a5.loadIcon(packageManager)).setOnMenuItemClickListener(this.e);
            }
        }
    }

    public void setShareHistoryFileName(String str) {
        this.b = str;
        a();
    }

    public void setShareIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                a(intent);
            }
        }
        ActivityChooserModel.a(this.a, this.b).a(intent);
    }

    private void a() {
        if (this.c != null) {
            if (this.f == null) {
                this.f = new ShareActivityChooserModelPolicy();
            }
            ActivityChooserModel.a(this.a, this.b).a(this.f);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Intent intent) {
        if (VERSION.SDK_INT >= 21) {
            intent.addFlags(134742016);
        } else {
            intent.addFlags(524288);
        }
    }
}
