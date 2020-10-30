package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.util.BroadcastHelper;

public class DefaultBadger implements Badger {
    public void executeBadge(Context context, ComponentName componentName, int i) {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", i);
        intent.putExtra("badge_count_package_name", componentName.getPackageName());
        intent.putExtra("badge_count_class_name", componentName.getClassName());
        if (BroadcastHelper.canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unable to resolve intent: ");
        sb.append(intent.toString());
        throw new ShortcutBadgeException(sb.toString());
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"fr.neamar.kiss", "com.quaap.launchtime", "com.quaap.launchtime_official"});
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Context context) {
        return BroadcastHelper.canResolveBroadcast(context, new Intent("android.intent.action.BADGE_COUNT_UPDATE"));
    }
}
