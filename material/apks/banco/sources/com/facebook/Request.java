package com.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.model.OpenGraphObject.Factory;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Request {
    public static final int MAXIMUM_BATCH_SIZE = 50;
    public static final String TAG = "Request";
    private static String a;
    private static Pattern b = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    private static volatile String p;
    private Session c;
    private HttpMethod d;
    private String e;
    private GraphObject f;
    private String g;
    private String h;
    private boolean i;
    private Bundle j;
    private Callback k;
    private String l;
    private Object m;
    private String n;
    private boolean o;

    static class Attachment {
        private final Request a;
        private final Object b;

        public Attachment(Request request, Object obj) {
            this.a = request;
            this.b = obj;
        }

        public Request a() {
            return this.a;
        }

        public Object b() {
            return this.b;
        }
    }

    public interface Callback {
        void onCompleted(Response response);
    }

    public interface GraphPlaceListCallback {
        void onCompleted(List<GraphPlace> list, Response response);
    }

    public interface GraphUserCallback {
        void onCompleted(GraphUser graphUser, Response response);
    }

    public interface GraphUserListCallback {
        void onCompleted(List<GraphUser> list, Response response);
    }

    interface KeyValueSerializer {
        void a(String str, String str2);
    }

    public interface OnProgressCallback extends Callback {
        void onProgress(long j, long j2);
    }

    static class ParcelFileDescriptorWithMimeType implements Parcelable {
        public static final Creator<ParcelFileDescriptorWithMimeType> CREATOR = new Creator<ParcelFileDescriptorWithMimeType>() {
            /* renamed from: a */
            public ParcelFileDescriptorWithMimeType createFromParcel(Parcel parcel) {
                return new ParcelFileDescriptorWithMimeType(parcel);
            }

            /* renamed from: a */
            public ParcelFileDescriptorWithMimeType[] newArray(int i) {
                return new ParcelFileDescriptorWithMimeType[i];
            }
        };
        private final String a;
        private final ParcelFileDescriptor b;

        public int describeContents() {
            return 1;
        }

        public String a() {
            return this.a;
        }

        public ParcelFileDescriptor b() {
            return this.b;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeFileDescriptor(this.b.getFileDescriptor());
        }

        public ParcelFileDescriptorWithMimeType(ParcelFileDescriptor parcelFileDescriptor, String str) {
            this.a = str;
            this.b = parcelFileDescriptor;
        }

        private ParcelFileDescriptorWithMimeType(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readFileDescriptor();
        }
    }

    static class Serializer implements KeyValueSerializer {
        private final OutputStream a;
        private final Logger b;
        private boolean c = true;

        public Serializer(OutputStream outputStream, Logger logger) {
            this.a = outputStream;
            this.b = logger;
        }

        public void a(String str, Object obj, Request request) {
            if (this.a instanceof RequestOutputStream) {
                ((RequestOutputStream) this.a).a(request);
            }
            if (Request.d(obj)) {
                a(str, Request.e(obj));
            } else if (obj instanceof Bitmap) {
                a(str, (Bitmap) obj);
            } else if (obj instanceof byte[]) {
                a(str, (byte[]) obj);
            } else if (obj instanceof ParcelFileDescriptor) {
                a(str, (ParcelFileDescriptor) obj, (String) null);
            } else if (obj instanceof ParcelFileDescriptorWithMimeType) {
                a(str, (ParcelFileDescriptorWithMimeType) obj);
            } else {
                throw new IllegalArgumentException("value is not a supported type: String, Bitmap, byte[]");
            }
        }

        public void a(String str, JSONArray jSONArray, Collection<Request> collection) {
            if (!(this.a instanceof RequestOutputStream)) {
                a(str, jSONArray.toString());
                return;
            }
            RequestOutputStream requestOutputStream = (RequestOutputStream) this.a;
            a(str, (String) null, (String) null);
            a("[", new Object[0]);
            int i = 0;
            for (Request request : collection) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                requestOutputStream.a(request);
                if (i > 0) {
                    a(",%s", jSONObject.toString());
                } else {
                    a("%s", jSONObject.toString());
                }
                i++;
            }
            a("]", new Object[0]);
            if (this.b != null) {
                Logger logger = this.b;
                StringBuilder sb = new StringBuilder();
                sb.append("    ");
                sb.append(str);
                logger.appendKeyValue(sb.toString(), jSONArray.toString());
            }
        }

        public void a(String str, String str2) {
            a(str, (String) null, (String) null);
            b("%s", str2);
            a();
            if (this.b != null) {
                Logger logger = this.b;
                StringBuilder sb = new StringBuilder();
                sb.append("    ");
                sb.append(str);
                logger.appendKeyValue(sb.toString(), str2);
            }
        }

        public void a(String str, Bitmap bitmap) {
            a(str, str, "image/png");
            bitmap.compress(CompressFormat.PNG, 100, this.a);
            b("", new Object[0]);
            a();
            if (this.b != null) {
                Logger logger = this.b;
                StringBuilder sb = new StringBuilder();
                sb.append("    ");
                sb.append(str);
                logger.appendKeyValue(sb.toString(), "<Image>");
            }
        }

        public void a(String str, byte[] bArr) {
            a(str, str, "content/unknown");
            this.a.write(bArr);
            b("", new Object[0]);
            a();
            if (this.b != null) {
                Logger logger = this.b;
                StringBuilder sb = new StringBuilder();
                sb.append("    ");
                sb.append(str);
                logger.appendKeyValue(sb.toString(), String.format("<Data: %d>", new Object[]{Integer.valueOf(bArr.length)}));
            }
        }

        public void a(String str, ParcelFileDescriptorWithMimeType parcelFileDescriptorWithMimeType) {
            a(str, parcelFileDescriptorWithMimeType.b(), parcelFileDescriptorWithMimeType.a());
        }

        /* JADX WARNING: Removed duplicated region for block: B:30:0x0081  */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x0086  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(java.lang.String r6, android.os.ParcelFileDescriptor r7, java.lang.String r8) {
            /*
                r5 = this;
                if (r8 != 0) goto L_0x0004
                java.lang.String r8 = "content/unknown"
            L_0x0004:
                r5.a(r6, r6, r8)
                java.io.OutputStream r8 = r5.a
                boolean r8 = r8 instanceof com.facebook.ProgressNoopOutputStream
                r0 = 0
                if (r8 == 0) goto L_0x001b
                java.io.OutputStream r8 = r5.a
                com.facebook.ProgressNoopOutputStream r8 = (com.facebook.ProgressNoopOutputStream) r8
                long r1 = r7.getStatSize()
                r8.a(r1)
                r2 = 0
                goto L_0x0043
            L_0x001b:
                r8 = 0
                android.os.ParcelFileDescriptor$AutoCloseInputStream r1 = new android.os.ParcelFileDescriptor$AutoCloseInputStream     // Catch:{ all -> 0x007c }
                r1.<init>(r7)     // Catch:{ all -> 0x007c }
                java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0079 }
                r7.<init>(r1)     // Catch:{ all -> 0x0079 }
                r8 = 8192(0x2000, float:1.14794E-41)
                byte[] r8 = new byte[r8]     // Catch:{ all -> 0x0077 }
                r2 = 0
            L_0x002b:
                int r3 = r7.read(r8)     // Catch:{ all -> 0x0077 }
                r4 = -1
                if (r3 == r4) goto L_0x0039
                java.io.OutputStream r4 = r5.a     // Catch:{ all -> 0x0077 }
                r4.write(r8, r0, r3)     // Catch:{ all -> 0x0077 }
                int r2 = r2 + r3
                goto L_0x002b
            L_0x0039:
                if (r7 == 0) goto L_0x003e
                r7.close()
            L_0x003e:
                if (r1 == 0) goto L_0x0043
                r1.close()
            L_0x0043:
                java.lang.String r7 = ""
                java.lang.Object[] r8 = new java.lang.Object[r0]
                r5.b(r7, r8)
                r5.a()
                com.facebook.internal.Logger r7 = r5.b
                if (r7 == 0) goto L_0x0076
                com.facebook.internal.Logger r7 = r5.b
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r1 = "    "
                r8.append(r1)
                r8.append(r6)
                java.lang.String r6 = r8.toString()
                java.lang.String r8 = "<Data: %d>"
                r1 = 1
                java.lang.Object[] r1 = new java.lang.Object[r1]
                java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
                r1[r0] = r2
                java.lang.String r8 = java.lang.String.format(r8, r1)
                r7.appendKeyValue(r6, r8)
            L_0x0076:
                return
            L_0x0077:
                r6 = move-exception
                goto L_0x007f
            L_0x0079:
                r6 = move-exception
                r7 = r8
                goto L_0x007f
            L_0x007c:
                r6 = move-exception
                r7 = r8
                r1 = r7
            L_0x007f:
                if (r7 == 0) goto L_0x0084
                r7.close()
            L_0x0084:
                if (r1 == 0) goto L_0x0089
                r1.close()
            L_0x0089:
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.Request.Serializer.a(java.lang.String, android.os.ParcelFileDescriptor, java.lang.String):void");
        }

        public void a() {
            b("--%s", "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
        }

        public void a(String str, String str2, String str3) {
            a("Content-Disposition: form-data; name=\"%s\"", str);
            if (str2 != null) {
                a("; filename=\"%s\"", str2);
            }
            b("", new Object[0]);
            if (str3 != null) {
                b("%s: %s", "Content-Type", str3);
            }
            b("", new Object[0]);
        }

        public void a(String str, Object... objArr) {
            if (this.c) {
                this.a.write("--".getBytes());
                this.a.write("3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f".getBytes());
                this.a.write("\r\n".getBytes());
                this.c = false;
            }
            this.a.write(String.format(str, objArr).getBytes());
        }

        public void b(String str, Object... objArr) {
            a(str, objArr);
            a("\r\n", new Object[0]);
        }
    }

    public Request() {
        this(null, null, null, null, null);
    }

    public Request(Session session, String str) {
        this(session, str, null, null, null);
    }

    public Request(Session session, String str, Bundle bundle, HttpMethod httpMethod) {
        this(session, str, bundle, httpMethod, null);
    }

    public Request(Session session, String str, Bundle bundle, HttpMethod httpMethod, Callback callback) {
        this(session, str, bundle, httpMethod, callback, null);
    }

    public Request(Session session, String str, Bundle bundle, HttpMethod httpMethod, Callback callback, String str2) {
        this.i = true;
        this.o = false;
        this.c = session;
        this.e = str;
        this.k = callback;
        this.n = str2;
        setHttpMethod(httpMethod);
        if (bundle != null) {
            this.j = new Bundle(bundle);
        } else {
            this.j = new Bundle();
        }
        if (this.n == null) {
            this.n = ServerProtocol.getAPIVersion();
        }
    }

    Request(Session session, URL url) {
        this.i = true;
        this.o = false;
        this.c = session;
        this.l = url.toString();
        setHttpMethod(HttpMethod.GET);
        this.j = new Bundle();
    }

    public static Request newPostRequest(Session session, String str, GraphObject graphObject, Callback callback) {
        Request request = new Request(session, str, null, HttpMethod.POST, callback);
        request.setGraphObject(graphObject);
        return request;
    }

    public static Request newMeRequest(Session session, final GraphUserCallback graphUserCallback) {
        Session session2 = session;
        Request request = new Request(session2, "me", null, null, new Callback() {
            public void onCompleted(Response response) {
                if (graphUserCallback != null) {
                    graphUserCallback.onCompleted((GraphUser) response.getGraphObjectAs(GraphUser.class), response);
                }
            }
        });
        return request;
    }

    public static Request newMyFriendsRequest(Session session, final GraphUserListCallback graphUserListCallback) {
        Session session2 = session;
        Request request = new Request(session2, "me/friends", null, null, new Callback() {
            public void onCompleted(Response response) {
                if (graphUserListCallback != null) {
                    graphUserListCallback.onCompleted(Request.b(response, GraphUser.class), response);
                }
            }
        });
        return request;
    }

    public static Request newUploadPhotoRequest(Session session, Bitmap bitmap, Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("picture", bitmap);
        Request request = new Request(session, "me/photos", bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newUploadPhotoRequest(Session session, File file, Callback callback) {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("picture", open);
        Request request = new Request(session, "me/photos", bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newUploadVideoRequest(Session session, File file, Callback callback) {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(file.getName(), open);
        Request request = new Request(session, "me/videos", bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newGraphPathRequest(Session session, String str, Callback callback) {
        Request request = new Request(session, str, null, null, callback);
        return request;
    }

    public static Request newPlacesSearchRequest(Session session, Location location, int i2, int i3, String str, final GraphPlaceListCallback graphPlaceListCallback) {
        if (location != null || !Utility.isNullOrEmpty(str)) {
            Bundle bundle = new Bundle(5);
            bundle.putString("type", "place");
            bundle.putInt("limit", i3);
            if (location != null) {
                bundle.putString("center", String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
                bundle.putInt("distance", i2);
            }
            if (!Utility.isNullOrEmpty(str)) {
                bundle.putString("q", str);
            }
            Session session2 = session;
            Request request = new Request(session2, "search", bundle, HttpMethod.GET, new Callback() {
                public void onCompleted(Response response) {
                    if (graphPlaceListCallback != null) {
                        graphPlaceListCallback.onCompleted(Request.b(response, GraphPlace.class), response);
                    }
                }
            });
            return request;
        }
        throw new FacebookException("Either location or searchText must be specified.");
    }

    public static Request newStatusUpdateRequest(Session session, String str, Callback callback) {
        return a(session, str, (String) null, null, callback);
    }

    private static Request a(Session session, String str, String str2, List<String> list, Callback callback) {
        Bundle bundle = new Bundle();
        bundle.putString("message", str);
        if (str2 != null) {
            bundle.putString("place", str2);
        }
        if (list != null && list.size() > 0) {
            bundle.putString("tags", TextUtils.join(",", list));
        }
        Request request = new Request(session, "me/feed", bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newStatusUpdateRequest(Session session, String str, GraphPlace graphPlace, List<GraphUser> list, Callback callback) {
        List list2;
        String str2 = null;
        if (list != null) {
            list2 = new ArrayList(list.size());
            for (GraphUser id2 : list) {
                list2.add(id2.getId());
            }
        } else {
            list2 = null;
        }
        if (graphPlace != null) {
            str2 = graphPlace.getId();
        }
        return a(session, str, str2, list2, callback);
    }

    public static Request newCustomAudienceThirdPartyIdRequest(Session session, Context context, Callback callback) {
        return newCustomAudienceThirdPartyIdRequest(session, context, null, callback);
    }

    public static Request newCustomAudienceThirdPartyIdRequest(Session session, Context context, String str, Callback callback) {
        if (session == null) {
            session = Session.getActiveSession();
        }
        if (session != null && !session.isOpened()) {
            session = null;
        }
        Session session2 = session;
        if (str == null) {
            if (session2 != null) {
                str = session2.getApplicationId();
            } else {
                str = Utility.getMetadataApplicationId(context);
            }
        }
        if (str == null) {
            throw new FacebookException("Facebook App ID cannot be determined");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/custom_audience_third_party_id");
        String sb2 = sb.toString();
        AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        Bundle bundle = new Bundle();
        if (session2 == null) {
            String attributionId = attributionIdentifiers.getAttributionId() != null ? attributionIdentifiers.getAttributionId() : attributionIdentifiers.getAndroidAdvertiserId();
            if (attributionIdentifiers.getAttributionId() != null) {
                bundle.putString("udid", attributionId);
            }
        }
        if (Settings.getLimitEventAndDataUsage(context) || attributionIdentifiers.isTrackingLimited()) {
            bundle.putString("limit_event_usage", "1");
        }
        Request request = new Request(session2, sb2, bundle, HttpMethod.GET, callback);
        return request;
    }

    public static Request newUploadStagingResourceWithImageRequest(Session session, Bitmap bitmap, Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("file", bitmap);
        Request request = new Request(session, "me/staging_resources", bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newUploadStagingResourceWithImageRequest(Session session, File file, Callback callback) {
        ParcelFileDescriptorWithMimeType parcelFileDescriptorWithMimeType = new ParcelFileDescriptorWithMimeType(ParcelFileDescriptor.open(file, 268435456), "image/png");
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("file", parcelFileDescriptorWithMimeType);
        Request request = new Request(session, "me/staging_resources", bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newPostOpenGraphObjectRequest(Session session, OpenGraphObject openGraphObject, Callback callback) {
        if (openGraphObject == null) {
            throw new FacebookException("openGraphObject cannot be null");
        } else if (Utility.isNullOrEmpty(openGraphObject.getType())) {
            throw new FacebookException("openGraphObject must have non-null 'type' property");
        } else if (Utility.isNullOrEmpty(openGraphObject.getTitle())) {
            throw new FacebookException("openGraphObject must have non-null 'title' property");
        } else {
            String format = String.format("me/objects/%s", new Object[]{openGraphObject.getType()});
            Bundle bundle = new Bundle();
            bundle.putString("object", openGraphObject.getInnerJSONObject().toString());
            Request request = new Request(session, format, bundle, HttpMethod.POST, callback);
            return request;
        }
    }

    public static Request newPostOpenGraphObjectRequest(Session session, String str, String str2, String str3, String str4, String str5, GraphObject graphObject, Callback callback) {
        OpenGraphObject createForPost = Factory.createForPost(OpenGraphObject.class, str, str2, str3, str4, str5);
        if (graphObject != null) {
            createForPost.setData(graphObject);
        }
        return newPostOpenGraphObjectRequest(session, createForPost, callback);
    }

    public static Request newPostOpenGraphActionRequest(Session session, OpenGraphAction openGraphAction, Callback callback) {
        if (openGraphAction == null) {
            throw new FacebookException("openGraphAction cannot be null");
        } else if (Utility.isNullOrEmpty(openGraphAction.getType())) {
            throw new FacebookException("openGraphAction must have non-null 'type' property");
        } else {
            return newPostRequest(session, String.format("me/%s", new Object[]{openGraphAction.getType()}), openGraphAction, callback);
        }
    }

    public static Request newDeleteObjectRequest(Session session, String str, Callback callback) {
        Request request = new Request(session, str, null, HttpMethod.DELETE, callback);
        return request;
    }

    public static Request newUpdateOpenGraphObjectRequest(Session session, OpenGraphObject openGraphObject, Callback callback) {
        if (openGraphObject == null) {
            throw new FacebookException("openGraphObject cannot be null");
        }
        String id2 = openGraphObject.getId();
        if (id2 == null) {
            throw new FacebookException("openGraphObject must have an id");
        }
        Bundle bundle = new Bundle();
        bundle.putString("object", openGraphObject.getInnerJSONObject().toString());
        Request request = new Request(session, id2, bundle, HttpMethod.POST, callback);
        return request;
    }

    public static Request newUpdateOpenGraphObjectRequest(Session session, String str, String str2, String str3, String str4, String str5, GraphObject graphObject, Callback callback) {
        OpenGraphObject createForPost = Factory.createForPost(OpenGraphObject.class, null, str2, str3, str4, str5);
        createForPost.setId(str);
        createForPost.setData(graphObject);
        return newUpdateOpenGraphObjectRequest(session, createForPost, callback);
    }

    public final GraphObject getGraphObject() {
        return this.f;
    }

    public final void setGraphObject(GraphObject graphObject) {
        this.f = graphObject;
    }

    public final String getGraphPath() {
        return this.e;
    }

    public final void setGraphPath(String str) {
        this.e = str;
    }

    public final HttpMethod getHttpMethod() {
        return this.d;
    }

    public final void setHttpMethod(HttpMethod httpMethod) {
        if (this.l == null || httpMethod == HttpMethod.GET) {
            if (httpMethod == null) {
                httpMethod = HttpMethod.GET;
            }
            this.d = httpMethod;
            return;
        }
        throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }

    public final String getVersion() {
        return this.n;
    }

    public final void setVersion(String str) {
        this.n = str;
    }

    public final void setSkipClientToken(boolean z) {
        this.o = z;
    }

    public final Bundle getParameters() {
        return this.j;
    }

    public final void setParameters(Bundle bundle) {
        this.j = bundle;
    }

    public final Session getSession() {
        return this.c;
    }

    public final void setSession(Session session) {
        this.c = session;
    }

    public final String getBatchEntryName() {
        return this.g;
    }

    public final void setBatchEntryName(String str) {
        this.g = str;
    }

    public final String getBatchEntryDependsOn() {
        return this.h;
    }

    public final void setBatchEntryDependsOn(String str) {
        this.h = str;
    }

    public final boolean getBatchEntryOmitResultOnSuccess() {
        return this.i;
    }

    public final void setBatchEntryOmitResultOnSuccess(boolean z) {
        this.i = z;
    }

    public static final String getDefaultBatchApplicationId() {
        return a;
    }

    public static final void setDefaultBatchApplicationId(String str) {
        a = str;
    }

    public final Callback getCallback() {
        return this.k;
    }

    public final void setCallback(Callback callback) {
        this.k = callback;
    }

    public final void setTag(Object obj) {
        this.m = obj;
    }

    public final Object getTag() {
        return this.m;
    }

    @Deprecated
    public static RequestAsyncTask executePostRequestAsync(Session session, String str, GraphObject graphObject, Callback callback) {
        return newPostRequest(session, str, graphObject, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeMeRequestAsync(Session session, GraphUserCallback graphUserCallback) {
        return newMeRequest(session, graphUserCallback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeMyFriendsRequestAsync(Session session, GraphUserListCallback graphUserListCallback) {
        return newMyFriendsRequest(session, graphUserListCallback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeUploadPhotoRequestAsync(Session session, Bitmap bitmap, Callback callback) {
        return newUploadPhotoRequest(session, bitmap, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeUploadPhotoRequestAsync(Session session, File file, Callback callback) {
        return newUploadPhotoRequest(session, file, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeGraphPathRequestAsync(Session session, String str, Callback callback) {
        return newGraphPathRequest(session, str, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executePlacesSearchRequestAsync(Session session, Location location, int i2, int i3, String str, GraphPlaceListCallback graphPlaceListCallback) {
        return newPlacesSearchRequest(session, location, i2, i3, str, graphPlaceListCallback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeStatusUpdateRequestAsync(Session session, String str, Callback callback) {
        return newStatusUpdateRequest(session, str, callback).executeAsync();
    }

    public final Response executeAndWait() {
        return executeAndWait(this);
    }

    public final RequestAsyncTask executeAsync() {
        return executeBatchAsync(this);
    }

    public static HttpURLConnection toHttpConnection(Request... requestArr) {
        return toHttpConnection((Collection<Request>) Arrays.asList(requestArr));
    }

    public static HttpURLConnection toHttpConnection(Collection<Request> collection) {
        Validate.notEmptyAndContainsNoNulls(collection, "requests");
        return toHttpConnection(new RequestBatch(collection));
    }

    public static HttpURLConnection toHttpConnection(RequestBatch requestBatch) {
        URL url;
        try {
            if (requestBatch.size() == 1) {
                url = new URL(requestBatch.get(0).b());
            } else {
                url = new URL(ServerProtocol.getGraphUrlBase());
            }
            try {
                HttpURLConnection a2 = a(url);
                a(requestBatch, a2);
                return a2;
            } catch (IOException e2) {
                throw new FacebookException("could not construct request body", e2);
            } catch (JSONException e3) {
                throw new FacebookException("could not construct request body", e3);
            }
        } catch (MalformedURLException e4) {
            throw new FacebookException("could not construct URL for request", e4);
        }
    }

    public static Response executeAndWait(Request request) {
        List executeBatchAndWait = executeBatchAndWait(request);
        if (executeBatchAndWait != null && executeBatchAndWait.size() == 1) {
            return (Response) executeBatchAndWait.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    public static List<Response> executeBatchAndWait(Request... requestArr) {
        Validate.notNull(requestArr, "requests");
        return executeBatchAndWait((Collection<Request>) Arrays.asList(requestArr));
    }

    public static List<Response> executeBatchAndWait(Collection<Request> collection) {
        return executeBatchAndWait(new RequestBatch(collection));
    }

    public static List<Response> executeBatchAndWait(RequestBatch requestBatch) {
        Validate.notEmptyAndContainsNoNulls(requestBatch, "requests");
        try {
            return executeConnectionAndWait(toHttpConnection(requestBatch), requestBatch);
        } catch (Exception e2) {
            List<Response> a2 = Response.a(requestBatch.c(), null, new FacebookException((Throwable) e2));
            a(requestBatch, a2);
            return a2;
        }
    }

    public static RequestAsyncTask executeBatchAsync(Request... requestArr) {
        Validate.notNull(requestArr, "requests");
        return executeBatchAsync((Collection<Request>) Arrays.asList(requestArr));
    }

    public static RequestAsyncTask executeBatchAsync(Collection<Request> collection) {
        return executeBatchAsync(new RequestBatch(collection));
    }

    public static RequestAsyncTask executeBatchAsync(RequestBatch requestBatch) {
        Validate.notEmptyAndContainsNoNulls(requestBatch, "requests");
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(requestBatch);
        requestAsyncTask.a();
        return requestAsyncTask;
    }

    public static List<Response> executeConnectionAndWait(HttpURLConnection httpURLConnection, Collection<Request> collection) {
        return executeConnectionAndWait(httpURLConnection, new RequestBatch(collection));
    }

    public static List<Response> executeConnectionAndWait(HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        List<Response> a2 = Response.a(httpURLConnection, requestBatch);
        Utility.disconnectQuietly(httpURLConnection);
        int size = requestBatch.size();
        if (size != a2.size()) {
            throw new FacebookException(String.format("Received %d responses while expecting %d", new Object[]{Integer.valueOf(a2.size()), Integer.valueOf(size)}));
        }
        a(requestBatch, a2);
        HashSet hashSet = new HashSet();
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            Request request = (Request) it.next();
            if (request.c != null) {
                hashSet.add(request.c);
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            ((Session) it2.next()).b();
        }
        return a2;
    }

    public static RequestAsyncTask executeConnectionAsync(HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        return executeConnectionAsync(null, httpURLConnection, requestBatch);
    }

    public static RequestAsyncTask executeConnectionAsync(Handler handler, HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        Validate.notNull(httpURLConnection, "connection");
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(httpURLConnection, requestBatch);
        requestBatch.a(handler);
        requestAsyncTask.a();
        return requestAsyncTask;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Request: ");
        sb.append(" session: ");
        sb.append(this.c);
        sb.append(", graphPath: ");
        sb.append(this.e);
        sb.append(", graphObject: ");
        sb.append(this.f);
        sb.append(", httpMethod: ");
        sb.append(this.d);
        sb.append(", parameters: ");
        sb.append(this.j);
        sb.append("}");
        return sb.toString();
    }

    static void a(final RequestBatch requestBatch, List<Response> list) {
        int size = requestBatch.size();
        final ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            Request request = requestBatch.get(i2);
            if (request.k != null) {
                arrayList.add(new Pair(request.k, list.get(i2)));
            }
        }
        if (arrayList.size() > 0) {
            AnonymousClass4 r7 = new Runnable() {
                public void run() {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        ((Callback) pair.first).onCompleted((Response) pair.second);
                    }
                    for (com.facebook.RequestBatch.Callback onBatchCompleted : requestBatch.d()) {
                        onBatchCompleted.onBatchCompleted(requestBatch);
                    }
                }
            };
            Handler b2 = requestBatch.b();
            if (b2 == null) {
                r7.run();
            } else {
                b2.post(r7);
            }
        }
    }

    static HttpURLConnection a(URL url) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("User-Agent", f());
        httpURLConnection.setRequestProperty("Content-Type", e());
        httpURLConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
        httpURLConnection.setChunkedStreamingMode(0);
        return httpURLConnection;
    }

    private void c() {
        if (this.c != null) {
            if (!this.c.isOpened()) {
                throw new FacebookException("Session provided to a Request in un-opened state.");
            } else if (!this.j.containsKey("access_token")) {
                String accessToken = this.c.getAccessToken();
                Logger.registerAccessToken(accessToken);
                this.j.putString("access_token", accessToken);
            }
        } else if (!this.o && !this.j.containsKey("access_token")) {
            String applicationId = Settings.getApplicationId();
            String clientToken = Settings.getClientToken();
            if (Utility.isNullOrEmpty(applicationId) || Utility.isNullOrEmpty(clientToken)) {
                Log.d(TAG, "Warning: Sessionless Request needs token but missing either application ID or client token.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(applicationId);
                sb.append("|");
                sb.append(clientToken);
                this.j.putString("access_token", sb.toString());
            }
        }
        this.j.putString(CommonUtils.SDK, AbstractSpiCall.ANDROID_CLIENT_TYPE);
        this.j.putString("format", "json");
    }

    private String a(String str) {
        Builder encodedPath = new Builder().encodedPath(str);
        for (String str2 : this.j.keySet()) {
            Object obj = this.j.get(str2);
            if (obj == null) {
                obj = "";
            }
            if (d(obj)) {
                encodedPath.appendQueryParameter(str2, e(obj).toString());
            } else if (this.d == HttpMethod.GET) {
                throw new IllegalArgumentException(String.format("Unsupported parameter type for GET request: %s", new Object[]{obj.getClass().getSimpleName()}));
            }
        }
        return encodedPath.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        if (this.l != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String d2 = d();
        c();
        return a(d2);
    }

    /* access modifiers changed from: 0000 */
    public final String b() {
        String str;
        if (this.l != null) {
            return this.l.toString();
        }
        if (getHttpMethod() != HttpMethod.POST || this.e == null || !this.e.endsWith("/videos")) {
            str = ServerProtocol.getGraphUrlBase();
        } else {
            str = ServerProtocol.getGraphVideoUrlBase();
        }
        String format = String.format("%s/%s", new Object[]{str, d()});
        c();
        return a(format);
    }

    private String d() {
        if (b.matcher(this.e).matches()) {
            return this.e;
        }
        return String.format("%s/%s", new Object[]{this.n, this.e});
    }

    private void a(JSONArray jSONArray, Map<String, Attachment> map) {
        JSONObject jSONObject = new JSONObject();
        if (this.g != null) {
            jSONObject.put("name", this.g);
            jSONObject.put("omit_response_on_success", this.i);
        }
        if (this.h != null) {
            jSONObject.put("depends_on", this.h);
        }
        String a2 = a();
        jSONObject.put("relative_url", a2);
        jSONObject.put("method", this.d);
        if (this.c != null) {
            Logger.registerAccessToken(this.c.getAccessToken());
        }
        ArrayList arrayList = new ArrayList();
        for (String str : this.j.keySet()) {
            Object obj = this.j.get(str);
            if (c(obj)) {
                String format = String.format("%s%d", new Object[]{"file", Integer.valueOf(map.size())});
                arrayList.add(format);
                map.put(format, new Attachment(this, obj));
            }
        }
        if (!arrayList.isEmpty()) {
            jSONObject.put("attached_files", TextUtils.join(",", arrayList));
        }
        if (this.f != null) {
            final ArrayList arrayList2 = new ArrayList();
            a(this.f, a2, (KeyValueSerializer) new KeyValueSerializer() {
                public void a(String str, String str2) {
                    arrayList2.add(String.format("%s=%s", new Object[]{str, URLEncoder.encode(str2, "UTF-8")}));
                }
            });
            jSONObject.put("body", TextUtils.join("&", arrayList2));
        }
        jSONArray.put(jSONObject);
    }

    private static boolean a(RequestBatch requestBatch) {
        for (com.facebook.RequestBatch.Callback callback : requestBatch.d()) {
            if (callback instanceof com.facebook.RequestBatch.OnProgressCallback) {
                return true;
            }
        }
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            if (((Request) it.next()).getCallback() instanceof OnProgressCallback) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.io.OutputStream, com.facebook.internal.Logger] */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r6v9, types: [com.facebook.ProgressOutputStream] */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v3, types: [java.io.OutputStream, com.facebook.internal.Logger]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], java.io.OutputStream, com.facebook.internal.Logger]
      mth insns count: 69
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
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final void a(com.facebook.RequestBatch r12, java.net.HttpURLConnection r13) {
        /*
            com.facebook.internal.Logger r0 = new com.facebook.internal.Logger
            com.facebook.LoggingBehavior r1 = com.facebook.LoggingBehavior.REQUESTS
            java.lang.String r2 = "Request"
            r0.<init>(r1, r2)
            int r1 = r12.size()
            r2 = 0
            r3 = 1
            if (r1 != r3) goto L_0x0018
            com.facebook.Request r4 = r12.get(r2)
            com.facebook.HttpMethod r4 = r4.d
            goto L_0x001a
        L_0x0018:
            com.facebook.HttpMethod r4 = com.facebook.HttpMethod.POST
        L_0x001a:
            java.lang.String r5 = r4.name()
            r13.setRequestMethod(r5)
            java.net.URL r5 = r13.getURL()
            java.lang.String r6 = "Request:\n"
            r0.append(r6)
            java.lang.String r6 = "Id"
            java.lang.String r7 = r12.a()
            r0.appendKeyValue(r6, r7)
            java.lang.String r6 = "URL"
            r0.appendKeyValue(r6, r5)
            java.lang.String r6 = "Method"
            java.lang.String r7 = r13.getRequestMethod()
            r0.appendKeyValue(r6, r7)
            java.lang.String r6 = "User-Agent"
            java.lang.String r7 = "User-Agent"
            java.lang.String r7 = r13.getRequestProperty(r7)
            r0.appendKeyValue(r6, r7)
            java.lang.String r6 = "Content-Type"
            java.lang.String r7 = "Content-Type"
            java.lang.String r7 = r13.getRequestProperty(r7)
            r0.appendKeyValue(r6, r7)
            int r6 = r12.getTimeout()
            r13.setConnectTimeout(r6)
            int r6 = r12.getTimeout()
            r13.setReadTimeout(r6)
            com.facebook.HttpMethod r6 = com.facebook.HttpMethod.POST
            if (r4 != r6) goto L_0x006a
            r2 = 1
        L_0x006a:
            if (r2 != 0) goto L_0x0070
            r0.log()
            return
        L_0x0070:
            r13.setDoOutput(r3)
            r2 = 0
            boolean r3 = a(r12)     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x00a1
            com.facebook.ProgressNoopOutputStream r3 = new com.facebook.ProgressNoopOutputStream     // Catch:{ all -> 0x00b7 }
            android.os.Handler r4 = r12.b()     // Catch:{ all -> 0x00b7 }
            r3.<init>(r4)     // Catch:{ all -> 0x00b7 }
            a(r12, r2, r1, r5, r3)     // Catch:{ all -> 0x00b7 }
            int r4 = r3.a()     // Catch:{ all -> 0x00b7 }
            java.util.Map r9 = r3.b()     // Catch:{ all -> 0x00b7 }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00b7 }
            java.io.OutputStream r13 = r13.getOutputStream()     // Catch:{ all -> 0x00b7 }
            r7.<init>(r13)     // Catch:{ all -> 0x00b7 }
            com.facebook.ProgressOutputStream r13 = new com.facebook.ProgressOutputStream     // Catch:{ all -> 0x00b7 }
            long r10 = (long) r4     // Catch:{ all -> 0x00b7 }
            r6 = r13
            r8 = r12
            r6.<init>(r7, r8, r9, r10)     // Catch:{ all -> 0x00b7 }
            r2 = r13
            goto L_0x00ab
        L_0x00a1:
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00b7 }
            java.io.OutputStream r13 = r13.getOutputStream()     // Catch:{ all -> 0x00b7 }
            r3.<init>(r13)     // Catch:{ all -> 0x00b7 }
            r2 = r3
        L_0x00ab:
            a(r12, r0, r1, r5, r2)     // Catch:{ all -> 0x00b7 }
            if (r2 == 0) goto L_0x00b3
            r2.close()
        L_0x00b3:
            r0.log()
            return
        L_0x00b7:
            r12 = move-exception
            if (r2 == 0) goto L_0x00bd
            r2.close()
        L_0x00bd:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Request.a(com.facebook.RequestBatch, java.net.HttpURLConnection):void");
    }

    private static void a(RequestBatch requestBatch, Logger logger, int i2, URL url, OutputStream outputStream) {
        Serializer serializer = new Serializer(outputStream, logger);
        if (i2 == 1) {
            Request request = requestBatch.get(0);
            HashMap hashMap = new HashMap();
            for (String str : request.j.keySet()) {
                Object obj = request.j.get(str);
                if (c(obj)) {
                    hashMap.put(str, new Attachment(request, obj));
                }
            }
            if (logger != null) {
                logger.append("  Parameters:\n");
            }
            a(request.j, serializer, request);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            a((Map<String, Attachment>) hashMap, serializer);
            if (request.f != null) {
                a(request.f, url.getPath(), (KeyValueSerializer) serializer);
                return;
            }
            return;
        }
        String b2 = b(requestBatch);
        if (Utility.isNullOrEmpty(b2)) {
            throw new FacebookException("At least one request in a batch must have an open Session, or a default app ID must be specified.");
        }
        serializer.a("batch_app_id", b2);
        HashMap hashMap2 = new HashMap();
        a(serializer, (Collection<Request>) requestBatch, (Map<String, Attachment>) hashMap2);
        if (logger != null) {
            logger.append("  Attachments:\n");
        }
        a((Map<String, Attachment>) hashMap2, serializer);
    }

    private static boolean b(String str) {
        Matcher matcher = b.matcher(str);
        if (matcher.matches()) {
            str = matcher.group(1);
        }
        if (str.startsWith("me/") || str.startsWith("/me/")) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0031  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(com.facebook.model.GraphObject r5, java.lang.String r6, com.facebook.Request.KeyValueSerializer r7) {
        /*
            boolean r0 = b(r6)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = ":"
            int r0 = r6.indexOf(r0)
            java.lang.String r3 = "?"
            int r6 = r6.indexOf(r3)
            r3 = 3
            if (r0 <= r3) goto L_0x001e
            r3 = -1
            if (r6 == r3) goto L_0x001c
            if (r0 >= r6) goto L_0x001e
        L_0x001c:
            r6 = 1
            goto L_0x001f
        L_0x001e:
            r6 = 0
        L_0x001f:
            java.util.Map r5 = r5.asMap()
            java.util.Set r5 = r5.entrySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x002b:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0058
            java.lang.Object r0 = r5.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            if (r6 == 0) goto L_0x0049
            java.lang.Object r3 = r0.getKey()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "image"
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x0049
            r3 = 1
            goto L_0x004a
        L_0x0049:
            r3 = 0
        L_0x004a:
            java.lang.Object r4 = r0.getKey()
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r0 = r0.getValue()
            a(r4, r0, r7, r3)
            goto L_0x002b
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Request.a(com.facebook.model.GraphObject, java.lang.String, com.facebook.Request$KeyValueSerializer):void");
    }

    private static void a(String str, Object obj, KeyValueSerializer keyValueSerializer, boolean z) {
        Class cls = obj.getClass();
        if (GraphObject.class.isAssignableFrom(cls)) {
            obj = ((GraphObject) obj).getInnerJSONObject();
            cls = obj.getClass();
        } else if (GraphObjectList.class.isAssignableFrom(cls)) {
            obj = ((GraphObjectList) obj).getInnerJSONArray();
            cls = obj.getClass();
        }
        if (JSONObject.class.isAssignableFrom(cls)) {
            JSONObject jSONObject = (JSONObject) obj;
            if (z) {
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    a(String.format("%s[%s]", new Object[]{str, str2}), jSONObject.opt(str2), keyValueSerializer, z);
                }
            } else if (jSONObject.has("id")) {
                a(str, jSONObject.optString("id"), keyValueSerializer, z);
            } else if (jSONObject.has("url")) {
                a(str, jSONObject.optString("url"), keyValueSerializer, z);
            } else if (jSONObject.has(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY)) {
                a(str, jSONObject.toString(), keyValueSerializer, z);
            }
        } else if (JSONArray.class.isAssignableFrom(cls)) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                a(String.format("%s[%d]", new Object[]{str, Integer.valueOf(i2)}), jSONArray.opt(i2), keyValueSerializer, z);
            }
        } else if (String.class.isAssignableFrom(cls) || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls)) {
            keyValueSerializer.a(str, obj.toString());
        } else if (Date.class.isAssignableFrom(cls)) {
            keyValueSerializer.a(str, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format((Date) obj));
        }
    }

    private static void a(Bundle bundle, Serializer serializer, Request request) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (d(obj)) {
                serializer.a(str, obj, request);
            }
        }
    }

    private static void a(Map<String, Attachment> map, Serializer serializer) {
        for (String str : map.keySet()) {
            Attachment attachment = (Attachment) map.get(str);
            if (c(attachment.b())) {
                serializer.a(str, attachment.b(), attachment.a());
            }
        }
    }

    private static void a(Serializer serializer, Collection<Request> collection, Map<String, Attachment> map) {
        JSONArray jSONArray = new JSONArray();
        for (Request a2 : collection) {
            a2.a(jSONArray, map);
        }
        serializer.a("batch", jSONArray, collection);
    }

    private static String e() {
        return String.format("multipart/form-data; boundary=%s", new Object[]{"3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f"});
    }

    private static String f() {
        if (p == null) {
            p = String.format("%s.%s", new Object[]{"FBAndroidSDK", "3.21.1"});
        }
        return p;
    }

    private static String b(RequestBatch requestBatch) {
        if (!Utility.isNullOrEmpty(requestBatch.e())) {
            return requestBatch.e();
        }
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            Session session = ((Request) it.next()).c;
            if (session != null) {
                return session.getApplicationId();
            }
        }
        return a;
    }

    /* access modifiers changed from: private */
    public static <T extends GraphObject> List<T> b(Response response, Class<T> cls) {
        GraphMultiResult graphMultiResult = (GraphMultiResult) response.getGraphObjectAs(GraphMultiResult.class);
        if (graphMultiResult == null) {
            return null;
        }
        GraphObjectList data = graphMultiResult.getData();
        if (data == null) {
            return null;
        }
        return data.castToListOf(cls);
    }

    private static boolean c(Object obj) {
        return (obj instanceof Bitmap) || (obj instanceof byte[]) || (obj instanceof ParcelFileDescriptor) || (obj instanceof ParcelFileDescriptorWithMimeType);
    }

    /* access modifiers changed from: private */
    public static boolean d(Object obj) {
        return (obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Number) || (obj instanceof Date);
    }

    /* access modifiers changed from: private */
    public static String e(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if ((obj instanceof Boolean) || (obj instanceof Number)) {
            return obj.toString();
        }
        if (obj instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(obj);
        }
        throw new IllegalArgumentException("Unsupported parameter type.");
    }
}
