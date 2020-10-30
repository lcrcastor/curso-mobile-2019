package android.support.v4.app;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class RemoteInput {
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    private final String a;
    private final CharSequence b;
    private final CharSequence[] c;
    private final boolean d;
    private final Bundle e;
    private final Set<String> f;

    public static final class Builder {
        private final String a;
        private CharSequence b;
        private CharSequence[] c;
        private boolean d = true;
        private Bundle e = new Bundle();
        private final Set<String> f = new HashSet();

        public Builder(String str) {
            if (str == null) {
                throw new IllegalArgumentException("Result key can't be null");
            }
            this.a = str;
        }

        public Builder setLabel(CharSequence charSequence) {
            this.b = charSequence;
            return this;
        }

        public Builder setChoices(CharSequence[] charSequenceArr) {
            this.c = charSequenceArr;
            return this;
        }

        public Builder setAllowDataType(String str, boolean z) {
            if (z) {
                this.f.add(str);
            } else {
                this.f.remove(str);
            }
            return this;
        }

        public Builder setAllowFreeFormInput(boolean z) {
            this.d = z;
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.e.putAll(bundle);
            }
            return this;
        }

        public Bundle getExtras() {
            return this.e;
        }

        public RemoteInput build() {
            RemoteInput remoteInput = new RemoteInput(this.a, this.b, this.c, this.d, this.e, this.f);
            return remoteInput;
        }
    }

    RemoteInput(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set) {
        this.a = str;
        this.b = charSequence;
        this.c = charSequenceArr;
        this.d = z;
        this.e = bundle;
        this.f = set;
    }

    public String getResultKey() {
        return this.a;
    }

    public CharSequence getLabel() {
        return this.b;
    }

    public CharSequence[] getChoices() {
        return this.c;
    }

    public Set<String> getAllowedDataTypes() {
        return this.f;
    }

    public boolean isDataOnly() {
        return !getAllowFreeFormInput() && (getChoices() == null || getChoices().length == 0) && getAllowedDataTypes() != null && !getAllowedDataTypes().isEmpty();
    }

    public boolean getAllowFreeFormInput() {
        return this.d;
    }

    public Bundle getExtras() {
        return this.e;
    }

    public static Map<String, Uri> getDataResultsFromIntent(Intent intent, String str) {
        if (VERSION.SDK_INT >= 26) {
            return android.app.RemoteInput.getDataResultsFromIntent(intent, str);
        }
        if (VERSION.SDK_INT >= 16) {
            Intent a2 = a(intent);
            if (a2 == null) {
                return null;
            }
            HashMap hashMap = new HashMap();
            for (String str2 : a2.getExtras().keySet()) {
                if (str2.startsWith("android.remoteinput.dataTypeResultsData")) {
                    String substring = str2.substring("android.remoteinput.dataTypeResultsData".length());
                    if (!substring.isEmpty()) {
                        String string = a2.getBundleExtra(str2).getString(str);
                        if (string != null && !string.isEmpty()) {
                            hashMap.put(substring, Uri.parse(string));
                        }
                    }
                }
            }
            if (hashMap.isEmpty()) {
                hashMap = null;
            }
            return hashMap;
        }
        Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
        return null;
    }

    public static Bundle getResultsFromIntent(Intent intent) {
        if (VERSION.SDK_INT >= 20) {
            return android.app.RemoteInput.getResultsFromIntent(intent);
        }
        if (VERSION.SDK_INT >= 16) {
            Intent a2 = a(intent);
            if (a2 == null) {
                return null;
            }
            return (Bundle) a2.getExtras().getParcelable(EXTRA_RESULTS_DATA);
        }
        Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
        return null;
    }

    public static void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
        if (VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addResultsToIntent(a(remoteInputArr), intent, bundle);
            return;
        }
        if (VERSION.SDK_INT >= 20) {
            Bundle resultsFromIntent = getResultsFromIntent(intent);
            if (resultsFromIntent != null) {
                resultsFromIntent.putAll(bundle);
                bundle = resultsFromIntent;
            }
            for (RemoteInput remoteInput : remoteInputArr) {
                Map dataResultsFromIntent = getDataResultsFromIntent(intent, remoteInput.getResultKey());
                android.app.RemoteInput.addResultsToIntent(a(new RemoteInput[]{remoteInput}), intent, bundle);
                if (dataResultsFromIntent != null) {
                    addDataResultToIntent(remoteInput, intent, dataResultsFromIntent);
                }
            }
        } else if (VERSION.SDK_INT >= 16) {
            Intent a2 = a(intent);
            if (a2 == null) {
                a2 = new Intent();
            }
            Bundle bundleExtra = a2.getBundleExtra(EXTRA_RESULTS_DATA);
            if (bundleExtra == null) {
                bundleExtra = new Bundle();
            }
            for (RemoteInput remoteInput2 : remoteInputArr) {
                Object obj = bundle.get(remoteInput2.getResultKey());
                if (obj instanceof CharSequence) {
                    bundleExtra.putCharSequence(remoteInput2.getResultKey(), (CharSequence) obj);
                }
            }
            a2.putExtra(EXTRA_RESULTS_DATA, bundleExtra);
            intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, a2));
        } else {
            Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
        }
    }

    public static void addDataResultToIntent(RemoteInput remoteInput, Intent intent, Map<String, Uri> map) {
        if (VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addDataResultToIntent(a(remoteInput), intent, map);
        } else if (VERSION.SDK_INT >= 16) {
            Intent a2 = a(intent);
            if (a2 == null) {
                a2 = new Intent();
            }
            for (Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                Uri uri = (Uri) entry.getValue();
                if (str != null) {
                    Bundle bundleExtra = a2.getBundleExtra(a(str));
                    if (bundleExtra == null) {
                        bundleExtra = new Bundle();
                    }
                    bundleExtra.putString(remoteInput.getResultKey(), uri.toString());
                    a2.putExtra(a(str), bundleExtra);
                }
            }
            intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, a2));
        } else {
            Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
        }
    }

    private static String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("android.remoteinput.dataTypeResultsData");
        sb.append(str);
        return sb.toString();
    }

    @RequiresApi(20)
    static android.app.RemoteInput[] a(RemoteInput[] remoteInputArr) {
        if (remoteInputArr == null) {
            return null;
        }
        android.app.RemoteInput[] remoteInputArr2 = new android.app.RemoteInput[remoteInputArr.length];
        for (int i = 0; i < remoteInputArr.length; i++) {
            remoteInputArr2[i] = a(remoteInputArr[i]);
        }
        return remoteInputArr2;
    }

    @RequiresApi(20)
    static android.app.RemoteInput a(RemoteInput remoteInput) {
        return new android.app.RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build();
    }

    @RequiresApi(16)
    private static Intent a(Intent intent) {
        ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return null;
        }
        ClipDescription description = clipData.getDescription();
        if (description.hasMimeType("text/vnd.android.intent") && description.getLabel().equals(RESULTS_CLIP_LABEL)) {
            return clipData.getItemAt(0).getIntent();
        }
        return null;
    }
}
