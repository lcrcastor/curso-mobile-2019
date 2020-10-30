package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.appcompat.R;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter extends ResourceCursorAdapter implements OnClickListener {
    private final SearchManager a = ((SearchManager) this.mContext.getSystemService("search"));
    private final SearchView b;
    private final SearchableInfo c;
    private final Context d;
    private final WeakHashMap<String, ConstantState> e;
    private final int f;
    private boolean g = false;
    private int h = 1;
    private ColorStateList i;
    private int j = -1;
    private int k = -1;
    private int l = -1;
    private int m = -1;
    private int n = -1;
    private int o = -1;

    static final class ChildViewCache {
        public final TextView a;
        public final TextView b;
        public final ImageView c;
        public final ImageView d;
        public final ImageView e;

        public ChildViewCache(View view) {
            this.a = (TextView) view.findViewById(16908308);
            this.b = (TextView) view.findViewById(16908309);
            this.c = (ImageView) view.findViewById(16908295);
            this.d = (ImageView) view.findViewById(16908296);
            this.e = (ImageView) view.findViewById(R.id.edit_query);
        }
    }

    public boolean hasStableIds() {
        return false;
    }

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), (Cursor) null, true);
        this.b = searchView;
        this.c = searchableInfo;
        this.f = searchView.getSuggestionCommitIconResId();
        this.d = context;
        this.e = weakHashMap;
    }

    public void a(int i2) {
        this.h = i2;
    }

    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? "" : charSequence.toString();
        if (this.b.getVisibility() != 0 || this.b.getWindowVisibility() != 0) {
            return null;
        }
        try {
            Cursor a2 = a(this.c, charSequence2, 50);
            if (a2 != null) {
                a2.getCount();
                return a2;
            }
        } catch (RuntimeException e2) {
            Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e2);
        }
        return null;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        a(getCursor());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        a(getCursor());
    }

    private void a(Cursor cursor) {
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null && !extras.getBoolean("in_progress")) {
        }
    }

    public void changeCursor(Cursor cursor) {
        if (this.g) {
            Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        try {
            super.changeCursor(cursor);
            if (cursor != null) {
                this.j = cursor.getColumnIndex("suggest_text_1");
                this.k = cursor.getColumnIndex("suggest_text_2");
                this.l = cursor.getColumnIndex("suggest_text_2_url");
                this.m = cursor.getColumnIndex("suggest_icon_1");
                this.n = cursor.getColumnIndex("suggest_icon_2");
                this.o = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception e2) {
            Log.e("SuggestionsAdapter", "error changing cursor and caching columns", e2);
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View newView = super.newView(context, cursor, viewGroup);
        newView.setTag(new ChildViewCache(newView));
        ((ImageView) newView.findViewById(R.id.edit_query)).setImageResource(this.f);
        return newView;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        CharSequence charSequence;
        ChildViewCache childViewCache = (ChildViewCache) view.getTag();
        int i2 = this.o != -1 ? cursor.getInt(this.o) : 0;
        if (childViewCache.a != null) {
            a(childViewCache.a, (CharSequence) a(cursor, this.j));
        }
        if (childViewCache.b != null) {
            String a2 = a(cursor, this.l);
            if (a2 != null) {
                charSequence = a((CharSequence) a2);
            } else {
                charSequence = a(cursor, this.k);
            }
            if (TextUtils.isEmpty(charSequence)) {
                if (childViewCache.a != null) {
                    childViewCache.a.setSingleLine(false);
                    childViewCache.a.setMaxLines(2);
                }
            } else if (childViewCache.a != null) {
                childViewCache.a.setSingleLine(true);
                childViewCache.a.setMaxLines(1);
            }
            a(childViewCache.b, charSequence);
        }
        if (childViewCache.c != null) {
            a(childViewCache.c, b(cursor), 4);
        }
        if (childViewCache.d != null) {
            a(childViewCache.d, c(cursor), 8);
        }
        if (this.h == 2 || (this.h == 1 && (i2 & 1) != 0)) {
            childViewCache.e.setVisibility(0);
            childViewCache.e.setTag(childViewCache.a.getText());
            childViewCache.e.setOnClickListener(this);
            return;
        }
        childViewCache.e.setVisibility(8);
    }

    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.b.a((CharSequence) tag);
        }
    }

    private CharSequence a(CharSequence charSequence) {
        if (this.i == null) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(R.attr.textColorSearchUrl, typedValue, true);
            this.i = this.mContext.getResources().getColorStateList(typedValue.resourceId);
        }
        SpannableString spannableString = new SpannableString(charSequence);
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, 0, this.i, null);
        spannableString.setSpan(textAppearanceSpan, 0, charSequence.length(), 33);
        return spannableString;
    }

    private void a(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    private Drawable b(Cursor cursor) {
        if (this.m == -1) {
            return null;
        }
        Drawable a2 = a(cursor.getString(this.m));
        if (a2 != null) {
            return a2;
        }
        return d(cursor);
    }

    private Drawable c(Cursor cursor) {
        if (this.n == -1) {
            return null;
        }
        return a(cursor.getString(this.n));
    }

    private void a(ImageView imageView, Drawable drawable, int i2) {
        imageView.setImageDrawable(drawable);
        if (drawable == null) {
            imageView.setVisibility(i2);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    public CharSequence convertToString(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        String a2 = a(cursor, "suggest_intent_query");
        if (a2 != null) {
            return a2;
        }
        if (this.c.shouldRewriteQueryFromData()) {
            String a3 = a(cursor, "suggest_intent_data");
            if (a3 != null) {
                return a3;
            }
        }
        if (this.c.shouldRewriteQueryFromText()) {
            String a4 = a(cursor, "suggest_text_1");
            if (a4 != null) {
                return a4;
            }
        }
        return null;
    }

    public View getView(int i2, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i2, view, viewGroup);
        } catch (RuntimeException e2) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e2);
            View newView = newView(this.mContext, this.mCursor, viewGroup);
            if (newView != null) {
                ((ChildViewCache) newView.getTag()).a.setText(e2.toString());
            }
            return newView;
        }
    }

    public View getDropDownView(int i2, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i2, view, viewGroup);
        } catch (RuntimeException e2) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e2);
            View newDropDownView = newDropDownView(this.mContext, this.mCursor, viewGroup);
            if (newDropDownView != null) {
                ((ChildViewCache) newDropDownView.getTag()).a.setText(e2.toString());
            }
            return newDropDownView;
        }
    }

    private Drawable a(String str) {
        if (str == null || str.isEmpty() || "0".equals(str)) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(str);
            StringBuilder sb = new StringBuilder();
            sb.append("android.resource://");
            sb.append(this.d.getPackageName());
            sb.append("/");
            sb.append(parseInt);
            String sb2 = sb.toString();
            Drawable b2 = b(sb2);
            if (b2 != null) {
                return b2;
            }
            Drawable drawable = ContextCompat.getDrawable(this.d, parseInt);
            a(sb2, drawable);
            return drawable;
        } catch (NumberFormatException unused) {
            Drawable b3 = b(str);
            if (b3 != null) {
                return b3;
            }
            Drawable b4 = b(Uri.parse(str));
            a(str, b4);
            return b4;
        } catch (NotFoundException unused2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Icon resource not found: ");
            sb3.append(str);
            Log.w("SuggestionsAdapter", sb3.toString());
            return null;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:7|8|9) */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r2 = new java.lang.StringBuilder();
        r2.append("Resource does not exist: ");
        r2.append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
        throw new java.io.FileNotFoundException(r2.toString());
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0012 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable b(android.net.Uri r7) {
        /*
            r6 = this;
            r0 = 0
            java.lang.String r1 = r7.getScheme()     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r2 = "android.resource"
            boolean r1 = r2.equals(r1)     // Catch:{ FileNotFoundException -> 0x0089 }
            if (r1 == 0) goto L_0x0029
            android.graphics.drawable.Drawable r1 = r6.a(r7)     // Catch:{ NotFoundException -> 0x0012 }
            return r1
        L_0x0012:
            java.io.FileNotFoundException r1 = new java.io.FileNotFoundException     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0089 }
            r2.<init>()     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r3 = "Resource does not exist: "
            r2.append(r3)     // Catch:{ FileNotFoundException -> 0x0089 }
            r2.append(r7)     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r2 = r2.toString()     // Catch:{ FileNotFoundException -> 0x0089 }
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0089 }
            throw r1     // Catch:{ FileNotFoundException -> 0x0089 }
        L_0x0029:
            android.content.Context r1 = r6.d     // Catch:{ FileNotFoundException -> 0x0089 }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ FileNotFoundException -> 0x0089 }
            java.io.InputStream r1 = r1.openInputStream(r7)     // Catch:{ FileNotFoundException -> 0x0089 }
            if (r1 != 0) goto L_0x004c
            java.io.FileNotFoundException r1 = new java.io.FileNotFoundException     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0089 }
            r2.<init>()     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r3 = "Failed to open "
            r2.append(r3)     // Catch:{ FileNotFoundException -> 0x0089 }
            r2.append(r7)     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r2 = r2.toString()     // Catch:{ FileNotFoundException -> 0x0089 }
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0089 }
            throw r1     // Catch:{ FileNotFoundException -> 0x0089 }
        L_0x004c:
            android.graphics.drawable.Drawable r2 = android.graphics.drawable.Drawable.createFromStream(r1, r0)     // Catch:{ all -> 0x006c }
            r1.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x006b
        L_0x0054:
            r1 = move-exception
            java.lang.String r3 = "SuggestionsAdapter"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0089 }
            r4.<init>()     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r5 = "Error closing icon stream for "
            r4.append(r5)     // Catch:{ FileNotFoundException -> 0x0089 }
            r4.append(r7)     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r4 = r4.toString()     // Catch:{ FileNotFoundException -> 0x0089 }
            android.util.Log.e(r3, r4, r1)     // Catch:{ FileNotFoundException -> 0x0089 }
        L_0x006b:
            return r2
        L_0x006c:
            r2 = move-exception
            r1.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0088
        L_0x0071:
            r1 = move-exception
            java.lang.String r3 = "SuggestionsAdapter"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0089 }
            r4.<init>()     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r5 = "Error closing icon stream for "
            r4.append(r5)     // Catch:{ FileNotFoundException -> 0x0089 }
            r4.append(r7)     // Catch:{ FileNotFoundException -> 0x0089 }
            java.lang.String r4 = r4.toString()     // Catch:{ FileNotFoundException -> 0x0089 }
            android.util.Log.e(r3, r4, r1)     // Catch:{ FileNotFoundException -> 0x0089 }
        L_0x0088:
            throw r2     // Catch:{ FileNotFoundException -> 0x0089 }
        L_0x0089:
            r1 = move-exception
            java.lang.String r2 = "SuggestionsAdapter"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Icon not found: "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r7 = ", "
            r3.append(r7)
            java.lang.String r7 = r1.getMessage()
            r3.append(r7)
            java.lang.String r7 = r3.toString()
            android.util.Log.w(r2, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SuggestionsAdapter.b(android.net.Uri):android.graphics.drawable.Drawable");
    }

    private Drawable b(String str) {
        ConstantState constantState = (ConstantState) this.e.get(str);
        if (constantState == null) {
            return null;
        }
        return constantState.newDrawable();
    }

    private void a(String str, Drawable drawable) {
        if (drawable != null) {
            this.e.put(str, drawable.getConstantState());
        }
    }

    private Drawable d(Cursor cursor) {
        Drawable a2 = a(this.c.getSearchActivity());
        if (a2 != null) {
            return a2;
        }
        return this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v2, types: [android.graphics.drawable.Drawable$ConstantState] */
    /* JADX WARNING: type inference failed for: r2v3, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: type inference failed for: r2v4, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.graphics.drawable.Drawable, android.graphics.drawable.Drawable$ConstantState]
      uses: [java.lang.Object, android.graphics.drawable.Drawable]
      mth insns count: 21
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable a(android.content.ComponentName r4) {
        /*
            r3 = this;
            java.lang.String r0 = r4.flattenToShortString()
            java.util.WeakHashMap<java.lang.String, android.graphics.drawable.Drawable$ConstantState> r1 = r3.e
            boolean r1 = r1.containsKey(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0023
            java.util.WeakHashMap<java.lang.String, android.graphics.drawable.Drawable$ConstantState> r4 = r3.e
            java.lang.Object r4 = r4.get(r0)
            android.graphics.drawable.Drawable$ConstantState r4 = (android.graphics.drawable.Drawable.ConstantState) r4
            if (r4 != 0) goto L_0x0018
            goto L_0x0022
        L_0x0018:
            android.content.Context r0 = r3.d
            android.content.res.Resources r0 = r0.getResources()
            android.graphics.drawable.Drawable r2 = r4.newDrawable(r0)
        L_0x0022:
            return r2
        L_0x0023:
            android.graphics.drawable.Drawable r4 = r3.b(r4)
            if (r4 != 0) goto L_0x002a
            goto L_0x002e
        L_0x002a:
            android.graphics.drawable.Drawable$ConstantState r2 = r4.getConstantState()
        L_0x002e:
            java.util.WeakHashMap<java.lang.String, android.graphics.drawable.Drawable$ConstantState> r1 = r3.e
            r1.put(r0, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SuggestionsAdapter.a(android.content.ComponentName):android.graphics.drawable.Drawable");
    }

    private Drawable b(ComponentName componentName) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128);
            int iconResource = activityInfo.getIconResource();
            if (iconResource == 0) {
                return null;
            }
            Drawable drawable = packageManager.getDrawable(componentName.getPackageName(), iconResource, activityInfo.applicationInfo);
            if (drawable != null) {
                return drawable;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid icon resource ");
            sb.append(iconResource);
            sb.append(" for ");
            sb.append(componentName.flattenToShortString());
            Log.w("SuggestionsAdapter", sb.toString());
            return null;
        } catch (NameNotFoundException e2) {
            Log.w("SuggestionsAdapter", e2.toString());
            return null;
        }
    }

    public static String a(Cursor cursor, String str) {
        return a(cursor, cursor.getColumnIndex(str));
    }

    private static String a(Cursor cursor, int i2) {
        if (i2 == -1) {
            return null;
        }
        try {
            return cursor.getString(i2);
        } catch (Exception e2) {
            Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", e2);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public Drawable a(Uri uri) {
        int i2;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            StringBuilder sb = new StringBuilder();
            sb.append("No authority: ");
            sb.append(uri);
            throw new FileNotFoundException(sb.toString());
        }
        try {
            Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(authority);
            List pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("No path: ");
                sb2.append(uri);
                throw new FileNotFoundException(sb2.toString());
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    i2 = Integer.parseInt((String) pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Single path segment is not a resource ID: ");
                    sb3.append(uri);
                    throw new FileNotFoundException(sb3.toString());
                }
            } else if (size == 2) {
                i2 = resourcesForApplication.getIdentifier((String) pathSegments.get(1), (String) pathSegments.get(0), authority);
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("More than two path segments: ");
                sb4.append(uri);
                throw new FileNotFoundException(sb4.toString());
            }
            if (i2 != 0) {
                return resourcesForApplication.getDrawable(i2);
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append("No resource found for: ");
            sb5.append(uri);
            throw new FileNotFoundException(sb5.toString());
        } catch (NameNotFoundException unused2) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("No package found for authority: ");
            sb6.append(uri);
            throw new FileNotFoundException(sb6.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public Cursor a(SearchableInfo searchableInfo, String str, int i2) {
        String[] strArr = null;
        if (searchableInfo == null) {
            return null;
        }
        String suggestAuthority = searchableInfo.getSuggestAuthority();
        if (suggestAuthority == null) {
            return null;
        }
        Builder fragment = new Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            fragment.appendPath(str);
        }
        String[] strArr2 = strArr;
        if (i2 > 0) {
            fragment.appendQueryParameter("limit", String.valueOf(i2));
        }
        return this.mContext.getContentResolver().query(fragment.build(), null, suggestSelection, strArr2, null);
    }
}
