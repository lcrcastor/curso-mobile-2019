package com.facebook.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.facebook.FacebookException;
import com.facebook.android.R;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageRequest.Builder;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.ImageResponse;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

class GraphObjectAdapter<T extends GraphObject> extends BaseAdapter implements SectionIndexer {
    static final /* synthetic */ boolean b = true;
    private final Map<String, ImageRequest> a = new HashMap();
    private final LayoutInflater c;
    private List<String> d = new ArrayList();
    private Map<String, ArrayList<T>> e = new HashMap();
    private Map<String, T> f = new HashMap();
    private boolean g;
    /* access modifiers changed from: private */
    public List<String> h;
    private String i;
    private boolean j;
    private boolean k;
    private Filter<T> l;
    private DataNeededListener m;
    private GraphObjectCursor<T> n;
    private Context o;
    private Map<String, ImageResponse> p = new HashMap();
    private ArrayList<String> q = new ArrayList<>();
    private OnErrorListener r;

    public interface DataNeededListener {
        void onDataNeeded();
    }

    interface Filter<T> {
        boolean a(T t);
    }

    interface ItemPicture extends GraphObject {
        ItemPictureData a();
    }

    interface ItemPictureData extends GraphObject {
        String a();
    }

    public interface OnErrorListener {
        void onError(GraphObjectAdapter<?> graphObjectAdapter, FacebookException facebookException);
    }

    public static class SectionAndItem<T extends GraphObject> {
        public T graphObject;
        public String sectionKey;

        public enum Type {
            GRAPH_OBJECT,
            SECTION_HEADER,
            ACTIVITY_CIRCLE
        }

        public SectionAndItem(String str, T t) {
            this.sectionKey = str;
            this.graphObject = t;
        }

        public Type getType() {
            if (this.sectionKey == null) {
                return Type.ACTIVITY_CIRCLE;
            }
            if (this.graphObject == null) {
                return Type.SECTION_HEADER;
            }
            return Type.GRAPH_OBJECT;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(CheckBox checkBox, boolean z) {
    }

    /* access modifiers changed from: 0000 */
    public boolean b(String str) {
        return false;
    }

    /* access modifiers changed from: protected */
    public CharSequence d(T t) {
        return null;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public boolean hasStableIds() {
        return true;
    }

    public GraphObjectAdapter(Context context) {
        this.o = context;
        this.c = LayoutInflater.from(context);
    }

    public void a(List<String> list) {
        this.h = list;
    }

    public void a(String str) {
        this.i = str;
    }

    public boolean b() {
        return this.j;
    }

    public void a(boolean z) {
        this.j = z;
    }

    public boolean c() {
        return this.k;
    }

    public void b(boolean z) {
        this.k = z;
    }

    public void a(DataNeededListener dataNeededListener) {
        this.m = dataNeededListener;
    }

    public void a(OnErrorListener onErrorListener) {
        this.r = onErrorListener;
    }

    public boolean a(GraphObjectCursor<T> graphObjectCursor) {
        if (this.n == graphObjectCursor) {
            return false;
        }
        if (this.n != null) {
            this.n.f();
        }
        this.n = graphObjectCursor;
        d();
        return true;
    }

    public void d() {
        g();
        notifyDataSetChanged();
    }

    public void a(int i2, int i3, int i4) {
        if (i3 >= i2 && this.d.size() != 0) {
            for (int i5 = i3; i5 >= 0; i5--) {
                SectionAndItem a2 = a(i5);
                if (a2.graphObject != null) {
                    ImageRequest imageRequest = (ImageRequest) this.a.get(g(a2.graphObject));
                    if (imageRequest != null) {
                        ImageDownloader.prioritizeRequest(imageRequest);
                    }
                }
            }
            int min = Math.min(i4 + i3, getCount() - 1);
            ArrayList arrayList = new ArrayList();
            for (int max = Math.max(0, i2 - i4); max < i2; max++) {
                SectionAndItem a3 = a(max);
                if (a3.graphObject != null) {
                    arrayList.add(a3.graphObject);
                }
            }
            while (true) {
                i3++;
                if (i3 > min) {
                    break;
                }
                SectionAndItem a4 = a(i3);
                if (a4.graphObject != null) {
                    arrayList.add(a4.graphObject);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                GraphObject graphObject = (GraphObject) it.next();
                URI e2 = e(graphObject);
                String g2 = g(graphObject);
                boolean remove = this.q.remove(g2);
                this.q.add(g2);
                if (!remove) {
                    a(g2, e2, (ImageView) null);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String b(T t) {
        String str;
        if (this.i != null) {
            str = (String) t.getProperty(this.i);
            if (str != null && str.length() > 0) {
                str = str.substring(0, 1).toUpperCase();
            }
        } else {
            str = null;
        }
        return str != null ? str : "";
    }

    /* access modifiers changed from: protected */
    public CharSequence c(T t) {
        return (String) t.getProperty("name");
    }

    /* access modifiers changed from: protected */
    public URI e(T t) {
        String str;
        Object property = t.getProperty("picture");
        if (property instanceof String) {
            str = (String) property;
        } else {
            if (property instanceof JSONObject) {
                ItemPictureData a2 = ((ItemPicture) Factory.create((JSONObject) property).cast(ItemPicture.class)).a();
                if (a2 != null) {
                    str = a2.a();
                }
            }
            str = null;
        }
        if (str != null) {
            try {
                return new URI(str);
            } catch (URISyntaxException unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public View a(String str, View view, ViewGroup viewGroup) {
        TextView textView = (TextView) view;
        if (textView == null) {
            textView = (TextView) this.c.inflate(R.layout.com_facebook_picker_list_section_header, null);
        }
        textView.setText(str);
        return textView;
    }

    /* access modifiers changed from: protected */
    public View a(T t, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = f(t);
        }
        a(view, t);
        return view;
    }

    private View a(View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.c.inflate(R.layout.com_facebook_picker_activity_circle_row, null);
        }
        ((ProgressBar) view.findViewById(R.id.com_facebook_picker_row_activity_circle)).setVisibility(0);
        return view;
    }

    /* access modifiers changed from: protected */
    public int a(T t) {
        return R.layout.com_facebook_picker_list_row;
    }

    /* access modifiers changed from: protected */
    public int a() {
        return R.drawable.com_facebook_profile_default_icon;
    }

    /* access modifiers changed from: protected */
    public View f(T t) {
        View inflate = this.c.inflate(a(t), null);
        ViewStub viewStub = (ViewStub) inflate.findViewById(R.id.com_facebook_picker_checkbox_stub);
        if (viewStub != null) {
            if (!c()) {
                viewStub.setVisibility(8);
            } else {
                a((CheckBox) viewStub.inflate(), false);
            }
        }
        ViewStub viewStub2 = (ViewStub) inflate.findViewById(R.id.com_facebook_picker_profile_pic_stub);
        if (!b()) {
            viewStub2.setVisibility(8);
        } else {
            ((ImageView) viewStub2.inflate()).setVisibility(0);
        }
        return inflate;
    }

    /* access modifiers changed from: protected */
    public void a(View view, T t) {
        String g2 = g(t);
        view.setTag(g2);
        CharSequence c2 = c(t);
        TextView textView = (TextView) view.findViewById(R.id.com_facebook_picker_title);
        if (textView != null) {
            textView.setText(c2, BufferType.SPANNABLE);
        }
        CharSequence d2 = d(t);
        TextView textView2 = (TextView) view.findViewById(R.id.picker_subtitle);
        if (textView2 != null) {
            if (d2 != null) {
                textView2.setText(d2, BufferType.SPANNABLE);
                textView2.setVisibility(0);
            } else {
                textView2.setVisibility(8);
            }
        }
        if (c()) {
            a((CheckBox) view.findViewById(R.id.com_facebook_picker_checkbox), b(g2));
        }
        if (b()) {
            URI e2 = e(t);
            if (e2 != null) {
                ImageView imageView = (ImageView) view.findViewById(R.id.com_facebook_picker_image);
                if (this.p.containsKey(g2)) {
                    ImageResponse imageResponse = (ImageResponse) this.p.get(g2);
                    imageView.setImageBitmap(imageResponse.getBitmap());
                    imageView.setTag(imageResponse.getRequest().getImageUri());
                    return;
                }
                a(g2, e2, imageView);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public String g(T t) {
        if (t.asMap().containsKey("id")) {
            Object property = t.getProperty("id");
            if (property instanceof String) {
                return (String) property;
            }
        }
        throw new FacebookException("Received an object without an ID.");
    }

    /* access modifiers changed from: 0000 */
    public boolean h(T t) {
        return this.l == null || this.l.a(t);
    }

    /* access modifiers changed from: 0000 */
    public void a(Filter<T> filter) {
        this.l = filter;
    }

    /* access modifiers changed from: 0000 */
    public String e() {
        ImageView imageView = (ImageView) f(null).findViewById(R.id.com_facebook_picker_image);
        if (imageView == null) {
            return null;
        }
        LayoutParams layoutParams = imageView.getLayoutParams();
        return String.format(Locale.US, "picture.height(%d).width(%d)", new Object[]{Integer.valueOf(layoutParams.height), Integer.valueOf(layoutParams.width)});
    }

    private boolean f() {
        return this.n != null && this.n.a() && this.m != null && !isEmpty();
    }

    private void g() {
        this.d = new ArrayList();
        this.e = new HashMap();
        this.f = new HashMap();
        boolean z = false;
        this.g = false;
        if (this.n != null && this.n.b() != 0) {
            this.n.c();
            int i2 = 0;
            do {
                GraphObject e2 = this.n.e();
                if (h(e2)) {
                    i2++;
                    String b2 = b((T) e2);
                    if (!this.e.containsKey(b2)) {
                        this.d.add(b2);
                        this.e.put(b2, new ArrayList());
                    }
                    ((List) this.e.get(b2)).add(e2);
                    this.f.put(g(e2), e2);
                }
            } while (this.n.d());
            if (this.h != null) {
                final Collator instance = Collator.getInstance();
                for (List sort : this.e.values()) {
                    Collections.sort(sort, new Comparator<GraphObject>() {
                        /* renamed from: a */
                        public int compare(GraphObject graphObject, GraphObject graphObject2) {
                            return GraphObjectAdapter.b(graphObject, graphObject2, GraphObjectAdapter.this.h, instance);
                        }
                    });
                }
            }
            Collections.sort(this.d, Collator.getInstance());
            if (this.d.size() > 1 && i2 > 1) {
                z = true;
            }
            this.g = z;
        }
    }

    /* access modifiers changed from: 0000 */
    public SectionAndItem<T> a(int i2) {
        String str;
        GraphObject graphObject = null;
        if (this.d.size() == 0) {
            return null;
        }
        if (this.g) {
            Iterator it = this.d.iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = null;
                    break;
                }
                str = (String) it.next();
                int i3 = i2 - 1;
                if (i2 == 0) {
                    break;
                }
                List list = (List) this.e.get(str);
                if (i3 < list.size()) {
                    graphObject = (GraphObject) list.get(i3);
                    break;
                }
                i2 = i3 - list.size();
            }
        } else {
            String str2 = (String) this.d.get(0);
            List list2 = (List) this.e.get(str2);
            if (i2 >= 0 && i2 < list2.size()) {
                graphObject = (GraphObject) ((ArrayList) this.e.get(str2)).get(i2);
                str = str2;
            } else if (b || (this.m != null && this.n.a())) {
                return new SectionAndItem<>(null, null);
            } else {
                throw new AssertionError();
            }
        }
        if (str != null) {
            return new SectionAndItem<>(str, graphObject);
        }
        throw new IndexOutOfBoundsException("position");
    }

    /* access modifiers changed from: 0000 */
    public int a(String str, T t) {
        Iterator it = this.d.iterator();
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            if (this.g) {
                i2++;
            }
            if (str2.equals(str)) {
                z = true;
                break;
            }
            i2 += ((ArrayList) this.e.get(str2)).size();
        }
        if (!z) {
            return -1;
        }
        if (t == null) {
            return i2 - (this.g ? 1 : 0);
        }
        Iterator it2 = ((ArrayList) this.e.get(str)).iterator();
        while (it2.hasNext()) {
            if (Factory.hasSameId((GraphObject) it2.next(), t)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.d.size() == 0;
    }

    public int getCount() {
        int i2 = 0;
        if (this.d.size() == 0) {
            return 0;
        }
        if (this.g) {
            i2 = this.d.size();
        }
        for (List size : this.e.values()) {
            i2 += size.size();
        }
        if (f()) {
            i2++;
        }
        return i2;
    }

    public boolean areAllItemsEnabled() {
        return this.g;
    }

    public boolean isEnabled(int i2) {
        return a(i2).getType() == Type.GRAPH_OBJECT;
    }

    public Object getItem(int i2) {
        SectionAndItem a2 = a(i2);
        if (a2.getType() == Type.GRAPH_OBJECT) {
            return a2.graphObject;
        }
        return null;
    }

    public long getItemId(int i2) {
        SectionAndItem a2 = a(i2);
        if (!(a2 == null || a2.graphObject == null)) {
            String g2 = g(a2.graphObject);
            if (g2 != null) {
                try {
                    return Long.parseLong(g2);
                } catch (NumberFormatException unused) {
                }
            }
        }
        return 0;
    }

    public int getItemViewType(int i2) {
        SectionAndItem a2 = a(i2);
        switch (a2.getType()) {
            case SECTION_HEADER:
                return 0;
            case GRAPH_OBJECT:
                return 1;
            case ACTIVITY_CIRCLE:
                return 2;
            default:
                throw new FacebookException("Unexpected type of section and item.");
        }
    }

    public View getView(int i2, View view, ViewGroup viewGroup) {
        SectionAndItem a2 = a(i2);
        switch (a2.getType()) {
            case SECTION_HEADER:
                return a(a2.sectionKey, view, viewGroup);
            case GRAPH_OBJECT:
                return a(a2.graphObject, view, viewGroup);
            case ACTIVITY_CIRCLE:
                if (b || (this.n.a() && this.m != null)) {
                    this.m.onDataNeeded();
                    return a(view, viewGroup);
                }
                throw new AssertionError();
            default:
                throw new FacebookException("Unexpected type of section and item.");
        }
    }

    public Object[] getSections() {
        if (this.g) {
            return this.d.toArray();
        }
        return new Object[0];
    }

    public int getPositionForSection(int i2) {
        if (this.g) {
            int max = Math.max(0, Math.min(i2, this.d.size() - 1));
            if (max < this.d.size()) {
                return a((String) this.d.get(max), (T) null);
            }
        }
        return 0;
    }

    public int getSectionForPosition(int i2) {
        SectionAndItem a2 = a(i2);
        if (a2 == null || a2.getType() == Type.ACTIVITY_CIRCLE) {
            return 0;
        }
        return Math.max(0, Math.min(this.d.indexOf(a2.sectionKey), this.d.size() - 1));
    }

    public List<T> a(Collection<String> collection) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(collection);
        ArrayList arrayList = new ArrayList(hashSet.size());
        for (String str : hashSet) {
            GraphObject graphObject = (GraphObject) this.f.get(str);
            if (graphObject != null) {
                arrayList.add(graphObject);
            }
        }
        return arrayList;
    }

    private void a(final String str, URI uri, final ImageView imageView) {
        if (uri != null) {
            boolean z = imageView == null;
            if (z || !uri.equals(imageView.getTag())) {
                if (!z) {
                    imageView.setTag(str);
                    imageView.setImageResource(a());
                }
                ImageRequest build = new Builder(this.o.getApplicationContext(), uri).setCallerTag(this).setCallback(new Callback() {
                    public void onCompleted(ImageResponse imageResponse) {
                        GraphObjectAdapter.this.a(imageResponse, str, imageView);
                    }
                }).build();
                this.a.put(str, build);
                ImageDownloader.downloadAsync(build);
            }
        }
    }

    private void a(Exception exc) {
        if (this.r != null) {
            if (!(exc instanceof FacebookException)) {
                exc = new FacebookException((Throwable) exc);
            }
            this.r.onError(this, (FacebookException) exc);
        }
    }

    /* access modifiers changed from: private */
    public void a(ImageResponse imageResponse, String str, ImageView imageView) {
        this.a.remove(str);
        if (imageResponse.getError() != null) {
            a(imageResponse.getError());
        }
        if (imageView == null) {
            if (imageResponse.getBitmap() != null) {
                if (this.p.size() >= 20) {
                    this.p.remove((String) this.q.remove(0));
                }
                this.p.put(str, imageResponse);
            }
        } else if (str.equals(imageView.getTag())) {
            Exception error = imageResponse.getError();
            Bitmap bitmap = imageResponse.getBitmap();
            if (error == null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
                imageView.setTag(imageResponse.getRequest().getImageUri());
            }
        }
    }

    /* access modifiers changed from: private */
    public static int b(GraphObject graphObject, GraphObject graphObject2, Collection<String> collection, Collator collator) {
        for (String str : collection) {
            String str2 = (String) graphObject.getProperty(str);
            String str3 = (String) graphObject2.getProperty(str);
            if (str2 != null && str3 != null) {
                int compare = collator.compare(str2, str3);
                if (compare != 0) {
                    return compare;
                }
            } else if (str2 != null || str3 != null) {
                return str2 == null ? -1 : 1;
            }
        }
        return 0;
    }
}
