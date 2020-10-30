package ar.com.santander.rio.mbanking.components.share.intent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

public interface ShareIntent {
    void addFlags(int i);

    Intent getAllIntents();

    Intent getIntent(PackageManager packageManager, String str);

    void setAction(String str);

    void setExtra(String str, Uri uri);

    void setExtra(String str, String str2);

    void setExtras(Bundle bundle);

    void setParcelableArrayExtra(String str, Uri uri);

    void setType(String str);
}
