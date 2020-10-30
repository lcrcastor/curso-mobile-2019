package bolts;

import android.net.Uri;
import java.util.Collections;
import java.util.List;

public class AppLink {
    private Uri a;
    private List<Target> b;
    private Uri c;

    public static class Target {
        private final Uri a;
        private final String b;
        private final String c;
        private final String d;

        public Target(String str, String str2, Uri uri, String str3) {
            this.b = str;
            this.c = str2;
            this.a = uri;
            this.d = str3;
        }

        public Uri getUrl() {
            return this.a;
        }

        public String getAppName() {
            return this.d;
        }

        public String getClassName() {
            return this.c;
        }

        public String getPackageName() {
            return this.b;
        }
    }

    public AppLink(Uri uri, List<Target> list, Uri uri2) {
        this.a = uri;
        if (list == null) {
            list = Collections.emptyList();
        }
        this.b = list;
        this.c = uri2;
    }

    public Uri getSourceUrl() {
        return this.a;
    }

    public List<Target> getTargets() {
        return Collections.unmodifiableList(this.b);
    }

    public Uri getWebUrl() {
        return this.c;
    }
}
