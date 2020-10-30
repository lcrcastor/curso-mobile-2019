package com.facebook.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphObjectException;
import com.facebook.NativeAppCallAttachmentStore;
import com.facebook.NativeAppCallContentProvider;
import com.facebook.Settings;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.DialogFeatureConfig;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import com.facebook.model.GraphObjectList;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookDialog {
    public static final String COMPLETION_GESTURE_CANCEL = "cancel";
    public static final String RESULT_ARGS_DIALOG_COMPLETE_KEY = "didComplete";
    public static final String RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY = "completionGesture";
    public static final String RESULT_ARGS_DIALOG_COMPLETION_ID_KEY = "postId";
    private static NativeAppCallAttachmentStore a;
    private Activity b;
    private Fragment c;
    private PendingCall d;
    private OnPresentCallback e;

    public static abstract class Builder<CONCRETE extends Builder<?>> {
        protected final Activity activity;
        protected final PendingCall appCall;
        protected final String applicationId;
        protected String applicationName;
        protected Fragment fragment;
        protected HashMap<String, Bitmap> imageAttachments = new HashMap<>();
        protected HashMap<String, File> mediaAttachmentFiles = new HashMap<>();

        /* access modifiers changed from: 0000 */
        public void d() {
        }

        /* access modifiers changed from: protected */
        public abstract EnumSet<? extends DialogFeature> getDialogFeatures();

        /* access modifiers changed from: protected */
        public abstract Bundle getMethodArguments();

        /* access modifiers changed from: protected */
        public Bundle setBundleExtras(Bundle bundle) {
            return bundle;
        }

        public Builder(Activity activity2) {
            Validate.notNull(activity2, "activity");
            this.activity = activity2;
            this.applicationId = Utility.getMetadataApplicationId(activity2);
            this.appCall = new PendingCall((int) NativeProtocol.DIALOG_REQUEST_CODE);
        }

        public CONCRETE setRequestCode(int i) {
            this.appCall.a(i);
            return this;
        }

        public CONCRETE setApplicationName(String str) {
            this.applicationName = str;
            return this;
        }

        public CONCRETE setFragment(Fragment fragment2) {
            this.fragment = fragment2;
            return this;
        }

        public FacebookDialog build() {
            Bundle bundleExtras;
            d();
            String a = FacebookDialog.b(getDialogFeatures());
            int a2 = FacebookDialog.b((Context) this.activity, a, FacebookDialog.b(this.applicationId, a, (Iterable<? extends DialogFeature>) getDialogFeatures()));
            if (NativeProtocol.isVersionCompatibleWithBucketedIntent(a2)) {
                bundleExtras = getMethodArguments();
            } else {
                bundleExtras = setBundleExtras(new Bundle());
            }
            Bundle bundle = bundleExtras;
            Intent createPlatformActivityIntent = NativeProtocol.createPlatformActivityIntent(this.activity, this.appCall.getCallId().toString(), a, a2, this.applicationName, bundle);
            if (createPlatformActivityIntent == null) {
                FacebookDialog.b(this.activity, this.fragment, FacebookDialog.b(a, bundle.containsKey(NativeProtocol.EXTRA_PHOTOS), false), AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_FAILED);
                throw new FacebookException("Unable to create Intent; this likely means the Facebook app is not installed.");
            }
            this.appCall.a(createPlatformActivityIntent);
            FacebookDialog facebookDialog = new FacebookDialog(this.activity, this.fragment, this.appCall, e());
            return facebookDialog;
        }

        /* access modifiers changed from: protected */
        public String getWebFallbackUrlInternal() {
            String str;
            String str2;
            Iterator it = getDialogFeatures().iterator();
            if (it.hasNext()) {
                DialogFeature dialogFeature = (DialogFeature) it.next();
                str = dialogFeature.name();
                str2 = dialogFeature.getAction();
            } else {
                str2 = null;
                str = null;
            }
            DialogFeatureConfig dialogFeatureConfig = Utility.getDialogFeatureConfig(this.applicationId, str2, str);
            if (dialogFeatureConfig != null) {
                Uri fallbackUrl = dialogFeatureConfig.getFallbackUrl();
                if (fallbackUrl != null) {
                    Bundle methodArguments = getMethodArguments();
                    Bundle queryParamsForPlatformActivityIntentWebFallback = ServerProtocol.getQueryParamsForPlatformActivityIntentWebFallback(this.activity, this.appCall.getCallId().toString(), NativeProtocol.getLatestKnownVersion(), this.applicationName, methodArguments);
                    if (queryParamsForPlatformActivityIntentWebFallback == null) {
                        return null;
                    }
                    if (fallbackUrl.isRelative()) {
                        fallbackUrl = Utility.buildUri(ServerProtocol.getDialogAuthority(), fallbackUrl.toString(), queryParamsForPlatformActivityIntentWebFallback);
                    }
                    return fallbackUrl.toString();
                }
            }
            return null;
        }

        public boolean canPresent() {
            return FacebookDialog.b(this.activity, getDialogFeatures());
        }

        /* access modifiers changed from: 0000 */
        public OnPresentCallback e() {
            return new OnPresentCallback() {
                public void a(Context context) {
                    if (Builder.this.imageAttachments != null && Builder.this.imageAttachments.size() > 0) {
                        FacebookDialog.b().addAttachmentsForCall(context, Builder.this.appCall.getCallId(), Builder.this.imageAttachments);
                    }
                    if (Builder.this.mediaAttachmentFiles != null && Builder.this.mediaAttachmentFiles.size() > 0) {
                        FacebookDialog.b().addAttachmentFilesForCall(context, Builder.this.appCall.getCallId(), Builder.this.mediaAttachmentFiles);
                    }
                }
            };
        }

        /* access modifiers changed from: protected */
        public List<String> addImageAttachments(Collection<Bitmap> collection) {
            ArrayList arrayList = new ArrayList();
            for (Bitmap bitmap : collection) {
                String uuid = UUID.randomUUID().toString();
                addImageAttachment(uuid, bitmap);
                arrayList.add(NativeAppCallContentProvider.getAttachmentUrl(this.applicationId, this.appCall.getCallId(), uuid));
            }
            return arrayList;
        }

        /* access modifiers changed from: protected */
        public List<String> addImageAttachmentFiles(Collection<File> collection) {
            ArrayList arrayList = new ArrayList();
            for (File file : collection) {
                String uuid = UUID.randomUUID().toString();
                addImageAttachment(uuid, file);
                arrayList.add(NativeAppCallContentProvider.getAttachmentUrl(this.applicationId, this.appCall.getCallId(), uuid));
            }
            return arrayList;
        }

        /* access modifiers changed from: protected */
        public String addVideoAttachmentFile(File file) {
            String uuid = UUID.randomUUID().toString();
            addVideoAttachment(uuid, file);
            return NativeAppCallContentProvider.getAttachmentUrl(this.applicationId, this.appCall.getCallId(), uuid);
        }

        /* access modifiers changed from: protected */
        public void putExtra(Bundle bundle, String str, String str2) {
            if (str2 != null) {
                bundle.putString(str, str2);
            }
        }

        /* access modifiers changed from: protected */
        public CONCRETE addImageAttachment(String str, Bitmap bitmap) {
            this.imageAttachments.put(str, bitmap);
            return this;
        }

        /* access modifiers changed from: protected */
        public CONCRETE addImageAttachment(String str, File file) {
            this.mediaAttachmentFiles.put(str, file);
            return this;
        }

        /* access modifiers changed from: protected */
        public CONCRETE addVideoAttachment(String str, File file) {
            this.mediaAttachmentFiles.put(str, file);
            return this;
        }
    }

    public interface Callback {
        void onComplete(PendingCall pendingCall, Bundle bundle);

        void onError(PendingCall pendingCall, Exception exc, Bundle bundle);
    }

    public interface DialogFeature {
        String getAction();

        int getMinVersion();

        String name();
    }

    public static class MessageDialogBuilder extends ShareDialogBuilderBase<MessageDialogBuilder> {
        public MessageDialogBuilder setFriends(List<String> list) {
            return this;
        }

        public MessageDialogBuilder setPlace(String str) {
            return this;
        }

        public MessageDialogBuilder(Activity activity) {
            super(activity);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(MessageDialogFeature.MESSAGE_DIALOG);
        }
    }

    public enum MessageDialogFeature implements DialogFeature {
        MESSAGE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140204),
        PHOTOS(NativeProtocol.PROTOCOL_VERSION_20140324);
        
        private int a;

        public String getAction() {
            return NativeProtocol.ACTION_MESSAGE_DIALOG;
        }

        private MessageDialogFeature(int i) {
            this.a = i;
        }

        public int getMinVersion() {
            return this.a;
        }
    }

    interface OnPresentCallback {
        void a(Context context);
    }

    public static class OpenGraphActionDialogBuilder extends OpenGraphDialogBuilderBase<OpenGraphActionDialogBuilder> {
        @Deprecated
        public OpenGraphActionDialogBuilder(Activity activity, OpenGraphAction openGraphAction, String str, String str2) {
            super(activity, openGraphAction, str, str2);
        }

        public OpenGraphActionDialogBuilder(Activity activity, OpenGraphAction openGraphAction, String str) {
            super(activity, openGraphAction, str);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(OpenGraphActionDialogFeature.OG_ACTION_DIALOG);
        }
    }

    public enum OpenGraphActionDialogFeature implements DialogFeature {
        OG_ACTION_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618);
        
        private int a;

        public String getAction() {
            return NativeProtocol.ACTION_OGACTIONPUBLISH_DIALOG;
        }

        private OpenGraphActionDialogFeature(int i) {
            this.a = i;
        }

        public int getMinVersion() {
            return this.a;
        }
    }

    static abstract class OpenGraphDialogBuilderBase<CONCRETE extends OpenGraphDialogBuilderBase<?>> extends Builder<CONCRETE> {
        private String a;
        private OpenGraphAction b;
        private String c;
        private boolean d;

        @Deprecated
        public OpenGraphDialogBuilderBase(Activity activity, OpenGraphAction openGraphAction, String str, String str2) {
            super(activity);
            Validate.notNull(openGraphAction, "action");
            Validate.notNullOrEmpty(str, "actionType");
            Validate.notNullOrEmpty(str2, "previewPropertyName");
            if (openGraphAction.getProperty(str2) == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("A property named \"");
                sb.append(str2);
                sb.append("\" was not found on the action.  The name of ");
                sb.append("the preview property must match the name of an action property.");
                throw new IllegalArgumentException(sb.toString());
            }
            String type = openGraphAction.getType();
            if (Utility.isNullOrEmpty(type) || type.equals(str)) {
                this.b = openGraphAction;
                this.c = str;
                this.a = str2;
                return;
            }
            throw new IllegalArgumentException("'actionType' must match the type of 'action' if it is specified. Consider using OpenGraphDialogBuilderBase(Activity activity, OpenGraphAction action, String previewPropertyName) instead.");
        }

        public OpenGraphDialogBuilderBase(Activity activity, OpenGraphAction openGraphAction, String str) {
            super(activity);
            Validate.notNull(openGraphAction, "action");
            Validate.notNullOrEmpty(openGraphAction.getType(), "action.getType()");
            Validate.notNullOrEmpty(str, "previewPropertyName");
            if (openGraphAction.getProperty(str) == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("A property named \"");
                sb.append(str);
                sb.append("\" was not found on the action.  The name of ");
                sb.append("the preview property must match the name of an action property.");
                throw new IllegalArgumentException(sb.toString());
            }
            this.b = openGraphAction;
            this.c = openGraphAction.getType();
            this.a = str;
        }

        public CONCRETE setDataErrorsFatal(boolean z) {
            this.d = z;
            return this;
        }

        public CONCRETE setImageAttachmentsForAction(List<Bitmap> list) {
            return setImageAttachmentsForAction(list, false);
        }

        public CONCRETE setImageAttachmentsForAction(List<Bitmap> list, boolean z) {
            Validate.containsNoNulls(list, "bitmaps");
            if (this.b == null) {
                throw new FacebookException("Can not set attachments prior to setting action.");
            }
            a(addImageAttachments(list), z);
            return this;
        }

        public CONCRETE setImageAttachmentFilesForAction(List<File> list) {
            return setImageAttachmentFilesForAction(list, false);
        }

        public CONCRETE setImageAttachmentFilesForAction(List<File> list, boolean z) {
            Validate.containsNoNulls(list, "bitmapFiles");
            if (this.b == null) {
                throw new FacebookException("Can not set attachments prior to setting action.");
            }
            a(addImageAttachmentFiles(list), z);
            return this;
        }

        private void a(List<String> list, boolean z) {
            List image = this.b.getImage();
            if (image == null) {
                image = new ArrayList(list.size());
            }
            for (String str : list) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", str);
                    if (z) {
                        jSONObject.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, true);
                    }
                    image.add(jSONObject);
                } catch (JSONException e) {
                    throw new FacebookException("Unable to attach images", e);
                }
            }
            this.b.setImage(image);
        }

        public CONCRETE setImageAttachmentsForObject(String str, List<Bitmap> list) {
            return setImageAttachmentsForObject(str, list, false);
        }

        public CONCRETE setImageAttachmentsForObject(String str, List<Bitmap> list, boolean z) {
            Validate.notNull(str, "objectProperty");
            Validate.containsNoNulls(list, "bitmaps");
            if (this.b == null) {
                throw new FacebookException("Can not set attachments prior to setting action.");
            }
            a(str, addImageAttachments(list), z);
            return this;
        }

        public CONCRETE setImageAttachmentFilesForObject(String str, List<File> list) {
            return setImageAttachmentFilesForObject(str, list, false);
        }

        public CONCRETE setImageAttachmentFilesForObject(String str, List<File> list, boolean z) {
            Validate.notNull(str, "objectProperty");
            Validate.containsNoNulls(list, "bitmapFiles");
            if (this.b == null) {
                throw new FacebookException("Can not set attachments prior to setting action.");
            }
            a(str, addImageAttachmentFiles(list), z);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public void a(String str, List<String> list, boolean z) {
            try {
                OpenGraphObject openGraphObject = (OpenGraphObject) this.b.getPropertyAs(str, OpenGraphObject.class);
                if (openGraphObject == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Action does not contain a property '");
                    sb.append(str);
                    sb.append("'");
                    throw new IllegalArgumentException(sb.toString());
                } else if (!openGraphObject.getCreateObject()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("The Open Graph object in '");
                    sb2.append(str);
                    sb2.append("' is not marked for creation");
                    throw new IllegalArgumentException(sb2.toString());
                } else {
                    GraphObjectList image = openGraphObject.getImage();
                    if (image == null) {
                        image = Factory.createList(GraphObject.class);
                    }
                    for (String str2 : list) {
                        GraphObject create = Factory.create();
                        create.setProperty("url", str2);
                        if (z) {
                            create.setProperty(NativeProtocol.IMAGE_USER_GENERATED_KEY, Boolean.valueOf(true));
                        }
                        image.add(create);
                    }
                    openGraphObject.setImage(image);
                }
            } catch (FacebookGraphObjectException unused) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Property '");
                sb3.append(str);
                sb3.append("' is not a graph object");
                throw new IllegalArgumentException(sb3.toString());
            }
        }

        /* access modifiers changed from: protected */
        public Bundle setBundleExtras(Bundle bundle) {
            putExtra(bundle, NativeProtocol.EXTRA_PREVIEW_PROPERTY_NAME, this.a);
            putExtra(bundle, NativeProtocol.EXTRA_ACTION_TYPE, this.c);
            bundle.putBoolean(NativeProtocol.EXTRA_DATA_FAILURES_FATAL, this.d);
            putExtra(bundle, NativeProtocol.EXTRA_ACTION, a(this.b.getInnerJSONObject()).toString());
            return bundle;
        }

        /* access modifiers changed from: protected */
        public Bundle getMethodArguments() {
            Bundle bundle = new Bundle();
            putExtra(bundle, NativeProtocol.METHOD_ARGS_PREVIEW_PROPERTY_NAME, this.a);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_ACTION_TYPE, this.c);
            bundle.putBoolean(NativeProtocol.METHOD_ARGS_DATA_FAILURES_FATAL, this.d);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_ACTION, a(this.b.getInnerJSONObject()).toString());
            return bundle;
        }

        private JSONObject a(JSONObject jSONObject) {
            try {
                JSONObject jSONObject2 = new JSONObject(jSONObject.toString());
                Iterator keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    if (!str.equalsIgnoreCase("image")) {
                        jSONObject2.put(str, a(jSONObject2.get(str)));
                    }
                }
                return jSONObject2;
            } catch (JSONException e) {
                throw new FacebookException((Throwable) e);
            }
        }

        private Object a(Object obj) {
            if (obj == null) {
                return null;
            }
            if (obj instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject.optBoolean(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY)) {
                    return obj;
                }
                if (jSONObject.has("id")) {
                    return jSONObject.getString("id");
                }
                if (jSONObject.has("url")) {
                    return jSONObject.getString("url");
                }
            } else if (obj instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) obj;
                JSONArray jSONArray2 = new JSONArray();
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    jSONArray2.put(a(jSONArray.get(i)));
                }
                return jSONArray2;
            }
            return obj;
        }
    }

    public static class OpenGraphMessageDialogBuilder extends OpenGraphDialogBuilderBase<OpenGraphMessageDialogBuilder> {
        public OpenGraphMessageDialogBuilder(Activity activity, OpenGraphAction openGraphAction, String str) {
            super(activity, openGraphAction, str);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG);
        }
    }

    public enum OpenGraphMessageDialogFeature implements DialogFeature {
        OG_MESSAGE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140204);
        
        private int a;

        public String getAction() {
            return NativeProtocol.ACTION_OGMESSAGEPUBLISH_DIALOG;
        }

        private OpenGraphMessageDialogFeature(int i) {
            this.a = i;
        }

        public int getMinVersion() {
            return this.a;
        }
    }

    public static class PendingCall implements Parcelable {
        public static final Creator<PendingCall> CREATOR = new Creator<PendingCall>() {
            /* renamed from: a */
            public PendingCall createFromParcel(Parcel parcel) {
                return new PendingCall(parcel);
            }

            /* renamed from: a */
            public PendingCall[] newArray(int i) {
                return new PendingCall[i];
            }
        };
        private UUID a;
        private Intent b;
        private int c;

        public int describeContents() {
            return 0;
        }

        public PendingCall(int i) {
            this.a = UUID.randomUUID();
            this.c = i;
        }

        private PendingCall(Parcel parcel) {
            this.a = UUID.fromString(parcel.readString());
            this.b = (Intent) parcel.readParcelable(null);
            this.c = parcel.readInt();
        }

        /* access modifiers changed from: private */
        public void a(Intent intent) {
            this.b = intent;
        }

        public Intent getRequestIntent() {
            return this.b;
        }

        public UUID getCallId() {
            return this.a;
        }

        /* access modifiers changed from: private */
        public void a(int i) {
            this.c = i;
        }

        public int getRequestCode() {
            return this.c;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a.toString());
            parcel.writeParcelable(this.b, 0);
            parcel.writeInt(this.c);
        }
    }

    static abstract class PhotoDialogBuilderBase<CONCRETE extends PhotoDialogBuilderBase<?>> extends Builder<CONCRETE> {
        static int a = 6;
        private String b;
        private ArrayList<String> c;
        private ArrayList<String> d = new ArrayList<>();

        /* access modifiers changed from: 0000 */
        public abstract int a();

        public PhotoDialogBuilderBase(Activity activity) {
            super(activity);
        }

        public CONCRETE setPlace(String str) {
            this.b = str;
            return this;
        }

        public CONCRETE setFriends(List<String> list) {
            this.c = list == null ? null : new ArrayList<>(list);
            return this;
        }

        public CONCRETE addPhotos(Collection<Bitmap> collection) {
            this.d.addAll(addImageAttachments(collection));
            return this;
        }

        public CONCRETE addPhotoFiles(Collection<File> collection) {
            this.d.addAll(addImageAttachmentFiles(collection));
            return this;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            super.d();
            if (this.d.isEmpty()) {
                throw new FacebookException("Must specify at least one photo.");
            } else if (this.d.size() > a()) {
                throw new FacebookException(String.format("Cannot add more than %d photos.", new Object[]{Integer.valueOf(a())}));
            }
        }

        /* access modifiers changed from: protected */
        public Bundle setBundleExtras(Bundle bundle) {
            putExtra(bundle, NativeProtocol.EXTRA_APPLICATION_ID, this.applicationId);
            putExtra(bundle, NativeProtocol.EXTRA_APPLICATION_NAME, this.applicationName);
            putExtra(bundle, NativeProtocol.EXTRA_PLACE_TAG, this.b);
            bundle.putStringArrayList(NativeProtocol.EXTRA_PHOTOS, this.d);
            if (!Utility.isNullOrEmpty((Collection<T>) this.c)) {
                bundle.putStringArrayList(NativeProtocol.EXTRA_FRIEND_TAGS, this.c);
            }
            return bundle;
        }

        /* access modifiers changed from: protected */
        public Bundle getMethodArguments() {
            Bundle bundle = new Bundle();
            putExtra(bundle, NativeProtocol.METHOD_ARGS_PLACE_TAG, this.b);
            bundle.putStringArrayList(NativeProtocol.METHOD_ARGS_PHOTOS, this.d);
            if (!Utility.isNullOrEmpty((Collection<T>) this.c)) {
                bundle.putStringArrayList(NativeProtocol.METHOD_ARGS_FRIEND_TAGS, this.c);
            }
            return bundle;
        }
    }

    public static class PhotoMessageDialogBuilder extends PhotoDialogBuilderBase<PhotoMessageDialogBuilder> {
        public PhotoMessageDialogBuilder setFriends(List<String> list) {
            return this;
        }

        public PhotoMessageDialogBuilder setPlace(String str) {
            return this;
        }

        public PhotoMessageDialogBuilder(Activity activity) {
            super(activity);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(MessageDialogFeature.MESSAGE_DIALOG, MessageDialogFeature.PHOTOS);
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return a;
        }
    }

    public static class PhotoShareDialogBuilder extends PhotoDialogBuilderBase<PhotoShareDialogBuilder> {
        public PhotoShareDialogBuilder(Activity activity) {
            super(activity);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(ShareDialogFeature.SHARE_DIALOG, ShareDialogFeature.PHOTOS);
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return a;
        }
    }

    public static class ShareDialogBuilder extends ShareDialogBuilderBase<ShareDialogBuilder> {
        public ShareDialogBuilder(Activity activity) {
            super(activity);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(ShareDialogFeature.SHARE_DIALOG);
        }
    }

    static abstract class ShareDialogBuilderBase<CONCRETE extends ShareDialogBuilderBase<?>> extends Builder<CONCRETE> {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private ArrayList<String> f;
        private String g;
        private boolean h;
        protected String link;

        public ShareDialogBuilderBase(Activity activity) {
            super(activity);
        }

        public CONCRETE setName(String str) {
            this.a = str;
            return this;
        }

        public CONCRETE setCaption(String str) {
            this.b = str;
            return this;
        }

        public CONCRETE setDescription(String str) {
            this.c = str;
            return this;
        }

        public CONCRETE setLink(String str) {
            this.link = str;
            return this;
        }

        public CONCRETE setPicture(String str) {
            this.d = str;
            return this;
        }

        public CONCRETE setPlace(String str) {
            this.e = str;
            return this;
        }

        public CONCRETE setFriends(List<String> list) {
            this.f = list == null ? null : new ArrayList<>(list);
            return this;
        }

        public CONCRETE setRef(String str) {
            this.g = str;
            return this;
        }

        public CONCRETE setDataErrorsFatal(boolean z) {
            this.h = z;
            return this;
        }

        /* access modifiers changed from: protected */
        public Bundle setBundleExtras(Bundle bundle) {
            putExtra(bundle, NativeProtocol.EXTRA_APPLICATION_ID, this.applicationId);
            putExtra(bundle, NativeProtocol.EXTRA_APPLICATION_NAME, this.applicationName);
            putExtra(bundle, NativeProtocol.EXTRA_TITLE, this.a);
            putExtra(bundle, NativeProtocol.EXTRA_SUBTITLE, this.b);
            putExtra(bundle, NativeProtocol.EXTRA_DESCRIPTION, this.c);
            putExtra(bundle, NativeProtocol.EXTRA_LINK, this.link);
            putExtra(bundle, NativeProtocol.EXTRA_IMAGE, this.d);
            putExtra(bundle, NativeProtocol.EXTRA_PLACE_TAG, this.e);
            putExtra(bundle, NativeProtocol.EXTRA_REF, this.g);
            bundle.putBoolean(NativeProtocol.EXTRA_DATA_FAILURES_FATAL, this.h);
            if (!Utility.isNullOrEmpty((Collection<T>) this.f)) {
                bundle.putStringArrayList(NativeProtocol.EXTRA_FRIEND_TAGS, this.f);
            }
            return bundle;
        }

        /* access modifiers changed from: protected */
        public Bundle getMethodArguments() {
            Bundle bundle = new Bundle();
            putExtra(bundle, "TITLE", this.a);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_SUBTITLE, this.b);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_DESCRIPTION, this.c);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_LINK, this.link);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_IMAGE, this.d);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_PLACE_TAG, this.e);
            putExtra(bundle, NativeProtocol.METHOD_ARGS_REF, this.g);
            bundle.putBoolean(NativeProtocol.METHOD_ARGS_DATA_FAILURES_FATAL, this.h);
            if (!Utility.isNullOrEmpty((Collection<T>) this.f)) {
                bundle.putStringArrayList(NativeProtocol.METHOD_ARGS_FRIEND_TAGS, this.f);
            }
            return bundle;
        }
    }

    public enum ShareDialogFeature implements DialogFeature {
        SHARE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618),
        PHOTOS(NativeProtocol.PROTOCOL_VERSION_20140204),
        VIDEO(NativeProtocol.PROTOCOL_VERSION_20141028);
        
        private int a;

        public String getAction() {
            return NativeProtocol.ACTION_FEED_DIALOG;
        }

        private ShareDialogFeature(int i) {
            this.a = i;
        }

        public int getMinVersion() {
            return this.a;
        }
    }

    static abstract class VideoDialogBuilderBase<CONCRETE extends VideoDialogBuilderBase<?>> extends Builder<CONCRETE> {
        private String a;
        private String b;

        public VideoDialogBuilderBase(Activity activity) {
            super(activity);
        }

        public CONCRETE setPlace(String str) {
            this.a = str;
            return this;
        }

        public CONCRETE addVideoFile(File file) {
            this.b = addVideoAttachmentFile(file);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            super.d();
            if (this.b == null || this.b.isEmpty()) {
                throw new FacebookException("Must specify at least one video.");
            }
        }

        /* access modifiers changed from: protected */
        public Bundle getMethodArguments() {
            Bundle bundle = new Bundle();
            putExtra(bundle, NativeProtocol.METHOD_ARGS_PLACE_TAG, this.a);
            bundle.putString(NativeProtocol.METHOD_ARGS_VIDEO, this.b);
            return bundle;
        }
    }

    public static class VideoShareDialogBuilder extends VideoDialogBuilderBase<VideoShareDialogBuilder> {
        public VideoShareDialogBuilder(Activity activity) {
            super(activity);
        }

        /* access modifiers changed from: protected */
        public EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(ShareDialogFeature.SHARE_DIALOG, ShareDialogFeature.VIDEO);
        }
    }

    public static boolean getNativeDialogDidComplete(Bundle bundle) {
        if (bundle.containsKey(RESULT_ARGS_DIALOG_COMPLETE_KEY)) {
            return bundle.getBoolean(RESULT_ARGS_DIALOG_COMPLETE_KEY);
        }
        return bundle.getBoolean("com.facebook.platform.extra.DID_COMPLETE", false);
    }

    public static String getNativeDialogCompletionGesture(Bundle bundle) {
        if (bundle.containsKey(RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY)) {
            return bundle.getString(RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY);
        }
        return bundle.getString("com.facebook.platform.extra.COMPLETION_GESTURE");
    }

    public static String getNativeDialogPostId(Bundle bundle) {
        if (bundle.containsKey(RESULT_ARGS_DIALOG_COMPLETION_ID_KEY)) {
            return bundle.getString(RESULT_ARGS_DIALOG_COMPLETION_ID_KEY);
        }
        return bundle.getString("com.facebook.platform.extra.POST_ID");
    }

    private FacebookDialog(Activity activity, Fragment fragment, PendingCall pendingCall, OnPresentCallback onPresentCallback) {
        this.b = activity;
        this.c = fragment;
        this.d = pendingCall;
        this.e = onPresentCallback;
    }

    public PendingCall present() {
        b(this.b, this.c, a(this.d.getRequestIntent()), AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_COMPLETED);
        if (this.e != null) {
            try {
                this.e.a(this.b);
            } catch (Exception e2) {
                throw new FacebookException((Throwable) e2);
            }
        }
        if (this.c != null) {
            this.c.startActivityForResult(this.d.getRequestIntent(), this.d.getRequestCode());
        } else {
            this.b.startActivityForResult(this.d.getRequestIntent(), this.d.getRequestCode());
        }
        return this.d;
    }

    public static boolean handleActivityResult(Context context, PendingCall pendingCall, int i, Intent intent, Callback callback) {
        if (i != pendingCall.getRequestCode()) {
            return false;
        }
        if (a != null) {
            a.cleanupAttachmentsForCall(context, pendingCall.getCallId());
        }
        if (callback != null) {
            if (NativeProtocol.isErrorResult(intent)) {
                Bundle errorDataFromResultIntent = NativeProtocol.getErrorDataFromResultIntent(intent);
                callback.onError(pendingCall, NativeProtocol.getExceptionFromErrorData(errorDataFromResultIntent), errorDataFromResultIntent);
            } else {
                callback.onComplete(pendingCall, NativeProtocol.getSuccessResultsFromIntent(intent));
            }
        }
        return true;
    }

    public static boolean canPresentShareDialog(Context context, ShareDialogFeature... shareDialogFeatureArr) {
        return b(context, EnumSet.of(ShareDialogFeature.SHARE_DIALOG, shareDialogFeatureArr));
    }

    public static boolean canPresentMessageDialog(Context context, MessageDialogFeature... messageDialogFeatureArr) {
        return b(context, EnumSet.of(MessageDialogFeature.MESSAGE_DIALOG, messageDialogFeatureArr));
    }

    public static boolean canPresentOpenGraphActionDialog(Context context, OpenGraphActionDialogFeature... openGraphActionDialogFeatureArr) {
        return b(context, EnumSet.of(OpenGraphActionDialogFeature.OG_ACTION_DIALOG, openGraphActionDialogFeatureArr));
    }

    public static boolean canPresentOpenGraphMessageDialog(Context context, OpenGraphMessageDialogFeature... openGraphMessageDialogFeatureArr) {
        return b(context, EnumSet.of(OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG, openGraphMessageDialogFeatureArr));
    }

    /* access modifiers changed from: private */
    public static boolean b(Context context, Iterable<? extends DialogFeature> iterable) {
        String b2 = b(iterable);
        String applicationId = Settings.getApplicationId();
        if (Utility.isNullOrEmpty(applicationId)) {
            applicationId = Utility.getMetadataApplicationId(context);
        }
        return b(context, b2, b(applicationId, b2, iterable)) != -1;
    }

    /* access modifiers changed from: private */
    public static int b(Context context, String str, int[] iArr) {
        return NativeProtocol.getLatestAvailableProtocolVersionForAction(context, str, iArr);
    }

    /* access modifiers changed from: private */
    public static NativeAppCallAttachmentStore b() {
        if (a == null) {
            a = new NativeAppCallAttachmentStore();
        }
        return a;
    }

    /* access modifiers changed from: private */
    public static int[] b(String str, String str2, Iterable<? extends DialogFeature> iterable) {
        int[] iArr = null;
        for (DialogFeature a2 : iterable) {
            iArr = Utility.intersectRanges(iArr, a(str, str2, a2));
        }
        return iArr;
    }

    private static int[] a(String str, String str2, DialogFeature dialogFeature) {
        DialogFeatureConfig dialogFeatureConfig = Utility.getDialogFeatureConfig(str, str2, dialogFeature.name());
        if (dialogFeatureConfig != null) {
            return dialogFeatureConfig.getVersionSpec();
        }
        return new int[]{dialogFeature.getMinVersion()};
    }

    /* access modifiers changed from: private */
    public static String b(Iterable<? extends DialogFeature> iterable) {
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            return ((DialogFeature) it.next()).getAction();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static void b(Activity activity, Fragment fragment, String str, String str2) {
        if (fragment != null) {
            activity = fragment.getActivity();
        }
        AppEventsLogger newLogger = AppEventsLogger.newLogger(activity);
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME, str2);
        newLogger.logSdkEvent(str, null, bundle);
    }

    private static String a(Intent intent) {
        String stringExtra = intent.getStringExtra(NativeProtocol.EXTRA_PROTOCOL_ACTION);
        boolean hasExtra = intent.hasExtra(NativeProtocol.EXTRA_PHOTOS);
        Bundle bundleExtra = intent.getBundleExtra(NativeProtocol.EXTRA_PROTOCOL_METHOD_ARGS);
        boolean z = false;
        if (bundleExtra != null) {
            ArrayList stringArrayList = bundleExtra.getStringArrayList(NativeProtocol.METHOD_ARGS_PHOTOS);
            String string = bundleExtra.getString(NativeProtocol.METHOD_ARGS_VIDEO);
            if (stringArrayList != null && !stringArrayList.isEmpty()) {
                hasExtra = true;
            }
            if (string != null && !string.isEmpty()) {
                z = true;
            }
        }
        return b(stringExtra, hasExtra, z);
    }

    /* access modifiers changed from: private */
    public static String b(String str, boolean z, boolean z2) {
        if (str.equals(NativeProtocol.ACTION_FEED_DIALOG)) {
            if (z2) {
                return AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_VIDEO_SHARE;
            }
            return z ? AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_PHOTO_SHARE : AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_SHARE;
        } else if (str.equals(NativeProtocol.ACTION_MESSAGE_DIALOG)) {
            return z ? AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_PHOTO_MESSAGE : AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_MESSAGE;
        } else {
            if (str.equals(NativeProtocol.ACTION_OGACTIONPUBLISH_DIALOG)) {
                return AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_OG_SHARE;
            }
            if (str.equals(NativeProtocol.ACTION_OGMESSAGEPUBLISH_DIALOG)) {
                return AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_OG_MESSAGE;
            }
            if (str.equals(NativeProtocol.ACTION_LIKE_DIALOG)) {
                return AnalyticsEvents.EVENT_NATIVE_DIALOG_TYPE_LIKE;
            }
            throw new FacebookException("An unspecified action was presented");
        }
    }
}
