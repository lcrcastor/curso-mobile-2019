package android.support.v4.media;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCompatProxy;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String KEY_MEDIA_ITEM = "media_item";
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String KEY_SEARCH_RESULTS = "search_results";
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    static final boolean a = Log.isLoggable("MBServiceCompat", 3);
    final ArrayMap<IBinder, ConnectionRecord> b = new ArrayMap<>();
    ConnectionRecord c;
    final ServiceHandler d = new ServiceHandler();
    Token e;
    private MediaBrowserServiceImpl f;

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final String a;
        private final Bundle b;

        public BrowserRoot(@NonNull String str, @Nullable Bundle bundle) {
            if (str == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.a = str;
            this.b = bundle;
        }

        public String getRootId() {
            return this.a;
        }

        public Bundle getExtras() {
            return this.b;
        }
    }

    class ConnectionRecord implements DeathRecipient {
        String a;
        Bundle b;
        ServiceCallbacks c;
        BrowserRoot d;
        HashMap<String, List<Pair<IBinder, Bundle>>> e = new HashMap<>();

        ConnectionRecord() {
        }

        public void binderDied() {
            MediaBrowserServiceCompat.this.d.post(new Runnable() {
                public void run() {
                    MediaBrowserServiceCompat.this.b.remove(ConnectionRecord.this.c.a());
                }
            });
        }
    }

    interface MediaBrowserServiceImpl {
        IBinder a(Intent intent);

        void a();

        void a(Token token);

        void a(String str, Bundle bundle);

        Bundle b();
    }

    @RequiresApi(21)
    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl, ServiceCompatProxy {
        final List<Bundle> a = new ArrayList();
        Object b;
        Messenger c;

        MediaBrowserServiceImplApi21() {
        }

        public void a() {
            this.b = MediaBrowserServiceCompatApi21.a((Context) MediaBrowserServiceCompat.this, (ServiceCompatProxy) this);
            MediaBrowserServiceCompatApi21.a(this.b);
        }

        public IBinder a(Intent intent) {
            return MediaBrowserServiceCompatApi21.a(this.b, intent);
        }

        public void a(final Token token) {
            MediaBrowserServiceCompat.this.d.a(new Runnable() {
                public void run() {
                    if (!MediaBrowserServiceImplApi21.this.a.isEmpty()) {
                        IMediaSession extraBinder = token.getExtraBinder();
                        if (extraBinder != null) {
                            for (Bundle putBinder : MediaBrowserServiceImplApi21.this.a) {
                                BundleCompat.putBinder(putBinder, "extra_session_binder", extraBinder.asBinder());
                            }
                        }
                        MediaBrowserServiceImplApi21.this.a.clear();
                    }
                    MediaBrowserServiceCompatApi21.a(MediaBrowserServiceImplApi21.this.b, token.getToken());
                }
            });
        }

        public void a(String str, Bundle bundle) {
            b(str, bundle);
            c(str, bundle);
        }

        public Bundle b() {
            Bundle bundle = null;
            if (this.c == null) {
                return null;
            }
            if (MediaBrowserServiceCompat.this.c == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            }
            if (MediaBrowserServiceCompat.this.c.b != null) {
                bundle = new Bundle(MediaBrowserServiceCompat.this.c.b);
            }
            return bundle;
        }

        public BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
            Bundle bundle2;
            IBinder iBinder;
            if (bundle == null || bundle.getInt("extra_client_version", 0) == 0) {
                bundle2 = null;
            } else {
                bundle.remove("extra_client_version");
                this.c = new Messenger(MediaBrowserServiceCompat.this.d);
                bundle2 = new Bundle();
                bundle2.putInt("extra_service_version", 2);
                BundleCompat.putBinder(bundle2, "extra_messenger", this.c.getBinder());
                if (MediaBrowserServiceCompat.this.e != null) {
                    IMediaSession extraBinder = MediaBrowserServiceCompat.this.e.getExtraBinder();
                    String str2 = "extra_session_binder";
                    if (extraBinder == null) {
                        iBinder = null;
                    } else {
                        iBinder = extraBinder.asBinder();
                    }
                    BundleCompat.putBinder(bundle2, str2, iBinder);
                } else {
                    this.a.add(bundle2);
                }
            }
            BrowserRoot onGetRoot = MediaBrowserServiceCompat.this.onGetRoot(str, i, bundle);
            if (onGetRoot == null) {
                return null;
            }
            if (bundle2 == null) {
                bundle2 = onGetRoot.getExtras();
            } else if (onGetRoot.getExtras() != null) {
                bundle2.putAll(onGetRoot.getExtras());
            }
            return new BrowserRoot(onGetRoot.getRootId(), bundle2);
        }

        public void onLoadChildren(String str, final ResultWrapper<List<Parcel>> resultWrapper) {
            MediaBrowserServiceCompat.this.onLoadChildren(str, new Result<List<MediaItem>>(str) {
                /* access modifiers changed from: 0000 */
                public void a(List<MediaItem> list) {
                    ArrayList arrayList;
                    if (list != null) {
                        arrayList = new ArrayList();
                        for (MediaItem mediaItem : list) {
                            Parcel obtain = Parcel.obtain();
                            mediaItem.writeToParcel(obtain, 0);
                            arrayList.add(obtain);
                        }
                    } else {
                        arrayList = null;
                    }
                    resultWrapper.a(arrayList);
                }

                public void detach() {
                    resultWrapper.a();
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public void b(String str, Bundle bundle) {
            MediaBrowserServiceCompatApi21.a(this.b, str);
        }

        /* access modifiers changed from: 0000 */
        public void c(final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.d.post(new Runnable() {
                public void run() {
                    for (IBinder iBinder : MediaBrowserServiceCompat.this.b.keySet()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(iBinder);
                        List<Pair> list = (List) connectionRecord.e.get(str);
                        if (list != null) {
                            for (Pair pair : list) {
                                if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle) pair.second)) {
                                    MediaBrowserServiceCompat.this.a(str, connectionRecord, (Bundle) pair.second);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @RequiresApi(23)
    class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        MediaBrowserServiceImplApi23() {
            super();
        }

        public void a() {
            this.b = MediaBrowserServiceCompatApi23.a(MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.a(this.b);
        }

        public void onLoadItem(String str, final ResultWrapper<Parcel> resultWrapper) {
            MediaBrowserServiceCompat.this.onLoadItem(str, new Result<MediaItem>(str) {
                /* access modifiers changed from: 0000 */
                public void a(MediaItem mediaItem) {
                    if (mediaItem == null) {
                        resultWrapper.a(null);
                        return;
                    }
                    Parcel obtain = Parcel.obtain();
                    mediaItem.writeToParcel(obtain, 0);
                    resultWrapper.a(obtain);
                }

                public void detach() {
                    resultWrapper.a();
                }
            });
        }
    }

    @RequiresApi(26)
    class MediaBrowserServiceImplApi26 extends MediaBrowserServiceImplApi23 implements MediaBrowserServiceCompatApi26.ServiceCompatProxy {
        MediaBrowserServiceImplApi26() {
            super();
        }

        public void a() {
            this.b = MediaBrowserServiceCompatApi26.a(MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.a(this.b);
        }

        public void onLoadChildren(String str, final ResultWrapper resultWrapper, Bundle bundle) {
            MediaBrowserServiceCompat.this.onLoadChildren(str, new Result<List<MediaItem>>(str) {
                /* access modifiers changed from: 0000 */
                public void a(List<MediaItem> list) {
                    ArrayList arrayList;
                    if (list != null) {
                        arrayList = new ArrayList();
                        for (MediaItem mediaItem : list) {
                            Parcel obtain = Parcel.obtain();
                            mediaItem.writeToParcel(obtain, 0);
                            arrayList.add(obtain);
                        }
                    } else {
                        arrayList = null;
                    }
                    resultWrapper.a(arrayList, b());
                }

                public void detach() {
                    resultWrapper.a();
                }
            }, bundle);
        }

        public Bundle b() {
            if (MediaBrowserServiceCompat.this.c == null) {
                return MediaBrowserServiceCompatApi26.a(this.b);
            }
            return MediaBrowserServiceCompat.this.c.b == null ? null : new Bundle(MediaBrowserServiceCompat.this.c.b);
        }

        /* access modifiers changed from: 0000 */
        public void b(String str, Bundle bundle) {
            if (bundle != null) {
                MediaBrowserServiceCompatApi26.a(this.b, str, bundle);
            } else {
                super.b(str, bundle);
            }
        }
    }

    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger b;

        MediaBrowserServiceImplBase() {
        }

        public void a() {
            this.b = new Messenger(MediaBrowserServiceCompat.this.d);
        }

        public IBinder a(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.b.getBinder();
            }
            return null;
        }

        public void a(final Token token) {
            MediaBrowserServiceCompat.this.d.post(new Runnable() {
                public void run() {
                    Iterator it = MediaBrowserServiceCompat.this.b.values().iterator();
                    while (it.hasNext()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) it.next();
                        try {
                            connectionRecord.c.a(connectionRecord.d.getRootId(), token, connectionRecord.d.getExtras());
                        } catch (RemoteException unused) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Connection for ");
                            sb.append(connectionRecord.a);
                            sb.append(" is no longer valid.");
                            Log.w("MBServiceCompat", sb.toString());
                            it.remove();
                        }
                    }
                }
            });
        }

        public void a(@NonNull final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.d.post(new Runnable() {
                public void run() {
                    for (IBinder iBinder : MediaBrowserServiceCompat.this.b.keySet()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(iBinder);
                        List<Pair> list = (List) connectionRecord.e.get(str);
                        if (list != null) {
                            for (Pair pair : list) {
                                if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle) pair.second)) {
                                    MediaBrowserServiceCompat.this.a(str, connectionRecord, (Bundle) pair.second);
                                }
                            }
                        }
                    }
                }
            });
        }

        public Bundle b() {
            if (MediaBrowserServiceCompat.this.c == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            } else if (MediaBrowserServiceCompat.this.c.b == null) {
                return null;
            } else {
                return new Bundle(MediaBrowserServiceCompat.this.c.b);
            }
        }
    }

    public static class Result<T> {
        private final Object a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private int f;

        /* access modifiers changed from: 0000 */
        public void a(T t) {
        }

        Result(Object obj) {
            this.a = obj;
        }

        public void sendResult(T t) {
            if (this.c || this.e) {
                StringBuilder sb = new StringBuilder();
                sb.append("sendResult() called when either sendResult() or sendError() had already been called for: ");
                sb.append(this.a);
                throw new IllegalStateException(sb.toString());
            }
            this.c = true;
            a(t);
        }

        public void sendProgressUpdate(Bundle bundle) {
            if (this.c || this.e) {
                StringBuilder sb = new StringBuilder();
                sb.append("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
                sb.append(this.a);
                throw new IllegalStateException(sb.toString());
            }
            a(bundle);
            this.d = true;
            b(bundle);
        }

        public void sendError(Bundle bundle) {
            if (this.c || this.e) {
                StringBuilder sb = new StringBuilder();
                sb.append("sendError() called when either sendResult() or sendError() had already been called for: ");
                sb.append(this.a);
                throw new IllegalStateException(sb.toString());
            }
            this.e = true;
            c(bundle);
        }

        public void detach() {
            if (this.b) {
                StringBuilder sb = new StringBuilder();
                sb.append("detach() called when detach() had already been called for: ");
                sb.append(this.a);
                throw new IllegalStateException(sb.toString());
            } else if (this.c) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("detach() called when sendResult() had already been called for: ");
                sb2.append(this.a);
                throw new IllegalStateException(sb2.toString());
            } else if (this.e) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("detach() called when sendError() had already been called for: ");
                sb3.append(this.a);
                throw new IllegalStateException(sb3.toString());
            } else {
                this.b = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.b || this.c || this.e;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            this.f = i;
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void b(Bundle bundle) {
            StringBuilder sb = new StringBuilder();
            sb.append("It is not supported to send an interim update for ");
            sb.append(this.a);
            throw new UnsupportedOperationException(sb.toString());
        }

        /* access modifiers changed from: 0000 */
        public void c(Bundle bundle) {
            StringBuilder sb = new StringBuilder();
            sb.append("It is not supported to send an error for ");
            sb.append(this.a);
            throw new UnsupportedOperationException(sb.toString());
        }

        private void a(Bundle bundle) {
            if (bundle != null && bundle.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float f2 = bundle.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (f2 < -1.0E-5f || f2 > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0].");
                }
            }
        }
    }

    class ServiceBinderImpl {
        ServiceBinderImpl() {
        }

        public void a(String str, int i, Bundle bundle, ServiceCallbacks serviceCallbacks) {
            if (!MediaBrowserServiceCompat.this.a(str, i)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Package/uid mismatch: uid=");
                sb.append(i);
                sb.append(" package=");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            ServiceHandler serviceHandler = MediaBrowserServiceCompat.this.d;
            final ServiceCallbacks serviceCallbacks2 = serviceCallbacks;
            final String str2 = str;
            final Bundle bundle2 = bundle;
            final int i2 = i;
            AnonymousClass1 r1 = new Runnable() {
                public void run() {
                    IBinder a2 = serviceCallbacks2.a();
                    MediaBrowserServiceCompat.this.b.remove(a2);
                    ConnectionRecord connectionRecord = new ConnectionRecord();
                    connectionRecord.a = str2;
                    connectionRecord.b = bundle2;
                    connectionRecord.c = serviceCallbacks2;
                    connectionRecord.d = MediaBrowserServiceCompat.this.onGetRoot(str2, i2, bundle2);
                    if (connectionRecord.d == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("No root for client ");
                        sb.append(str2);
                        sb.append(" from service ");
                        sb.append(getClass().getName());
                        Log.i("MBServiceCompat", sb.toString());
                        try {
                            serviceCallbacks2.b();
                        } catch (RemoteException unused) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Calling onConnectFailed() failed. Ignoring. pkg=");
                            sb2.append(str2);
                            Log.w("MBServiceCompat", sb2.toString());
                        }
                    } else {
                        try {
                            MediaBrowserServiceCompat.this.b.put(a2, connectionRecord);
                            a2.linkToDeath(connectionRecord, 0);
                            if (MediaBrowserServiceCompat.this.e != null) {
                                serviceCallbacks2.a(connectionRecord.d.getRootId(), MediaBrowserServiceCompat.this.e, connectionRecord.d.getExtras());
                            }
                        } catch (RemoteException unused2) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Calling onConnect() failed. Dropping client. pkg=");
                            sb3.append(str2);
                            Log.w("MBServiceCompat", sb3.toString());
                            MediaBrowserServiceCompat.this.b.remove(a2);
                        }
                    }
                }
            };
            serviceHandler.a(r1);
        }

        public void a(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.d.a(new Runnable() {
                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.remove(serviceCallbacks.a());
                    if (connectionRecord != null) {
                        connectionRecord.c.a().unlinkToDeath(connectionRecord, 0);
                    }
                }
            });
        }

        public void a(String str, IBinder iBinder, Bundle bundle, ServiceCallbacks serviceCallbacks) {
            ServiceHandler serviceHandler = MediaBrowserServiceCompat.this.d;
            final ServiceCallbacks serviceCallbacks2 = serviceCallbacks;
            final String str2 = str;
            final IBinder iBinder2 = iBinder;
            final Bundle bundle2 = bundle;
            AnonymousClass3 r1 = new Runnable() {
                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(serviceCallbacks2.a());
                    if (connectionRecord == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("addSubscription for callback that isn't registered id=");
                        sb.append(str2);
                        Log.w("MBServiceCompat", sb.toString());
                        return;
                    }
                    MediaBrowserServiceCompat.this.a(str2, connectionRecord, iBinder2, bundle2);
                }
            };
            serviceHandler.a(r1);
        }

        public void a(final String str, final IBinder iBinder, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.d.a(new Runnable() {
                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(serviceCallbacks.a());
                    if (connectionRecord == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("removeSubscription for callback that isn't registered id=");
                        sb.append(str);
                        Log.w("MBServiceCompat", sb.toString());
                        return;
                    }
                    if (!MediaBrowserServiceCompat.this.a(str, connectionRecord, iBinder)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("removeSubscription called for ");
                        sb2.append(str);
                        sb2.append(" which is not subscribed");
                        Log.w("MBServiceCompat", sb2.toString());
                    }
                }
            });
        }

        public void a(final String str, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.d.a(new Runnable() {
                    public void run() {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(serviceCallbacks.a());
                        if (connectionRecord == null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("getMediaItem for callback that isn't registered id=");
                            sb.append(str);
                            Log.w("MBServiceCompat", sb.toString());
                            return;
                        }
                        MediaBrowserServiceCompat.this.a(str, connectionRecord, resultReceiver);
                    }
                });
            }
        }

        public void a(final ServiceCallbacks serviceCallbacks, final Bundle bundle) {
            MediaBrowserServiceCompat.this.d.a(new Runnable() {
                public void run() {
                    IBinder a2 = serviceCallbacks.a();
                    MediaBrowserServiceCompat.this.b.remove(a2);
                    ConnectionRecord connectionRecord = new ConnectionRecord();
                    connectionRecord.c = serviceCallbacks;
                    connectionRecord.b = bundle;
                    MediaBrowserServiceCompat.this.b.put(a2, connectionRecord);
                    try {
                        a2.linkToDeath(connectionRecord, 0);
                    } catch (RemoteException unused) {
                        Log.w("MBServiceCompat", "IBinder is already dead.");
                    }
                }
            });
        }

        public void b(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.d.a(new Runnable() {
                public void run() {
                    IBinder a2 = serviceCallbacks.a();
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.remove(a2);
                    if (connectionRecord != null) {
                        a2.unlinkToDeath(connectionRecord, 0);
                    }
                }
            });
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver, ServiceCallbacks serviceCallbacks) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                ServiceHandler serviceHandler = MediaBrowserServiceCompat.this.d;
                final ServiceCallbacks serviceCallbacks2 = serviceCallbacks;
                final String str2 = str;
                final Bundle bundle2 = bundle;
                final ResultReceiver resultReceiver2 = resultReceiver;
                AnonymousClass8 r1 = new Runnable() {
                    public void run() {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(serviceCallbacks2.a());
                        if (connectionRecord == null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("search for callback that isn't registered query=");
                            sb.append(str2);
                            Log.w("MBServiceCompat", sb.toString());
                            return;
                        }
                        MediaBrowserServiceCompat.this.a(str2, bundle2, connectionRecord, resultReceiver2);
                    }
                };
                serviceHandler.a(r1);
            }
        }

        public void b(String str, Bundle bundle, ResultReceiver resultReceiver, ServiceCallbacks serviceCallbacks) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                ServiceHandler serviceHandler = MediaBrowserServiceCompat.this.d;
                final ServiceCallbacks serviceCallbacks2 = serviceCallbacks;
                final String str2 = str;
                final Bundle bundle2 = bundle;
                final ResultReceiver resultReceiver2 = resultReceiver;
                AnonymousClass9 r1 = new Runnable() {
                    public void run() {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.b.get(serviceCallbacks2.a());
                        if (connectionRecord == null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("sendCustomAction for callback that isn't registered action=");
                            sb.append(str2);
                            sb.append(", extras=");
                            sb.append(bundle2);
                            Log.w("MBServiceCompat", sb.toString());
                            return;
                        }
                        MediaBrowserServiceCompat.this.b(str2, bundle2, connectionRecord, resultReceiver2);
                    }
                };
                serviceHandler.a(r1);
            }
        }
    }

    interface ServiceCallbacks {
        IBinder a();

        void a(String str, Token token, Bundle bundle);

        void a(String str, List<MediaItem> list, Bundle bundle);

        void b();
    }

    static class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger a;

        ServiceCallbacksCompat(Messenger messenger) {
            this.a = messenger;
        }

        public IBinder a() {
            return this.a.getBinder();
        }

        public void a(String str, Token token, Bundle bundle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt("extra_service_version", 2);
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", str);
            bundle2.putParcelable("data_media_session_token", token);
            bundle2.putBundle("data_root_hints", bundle);
            a(1, bundle2);
        }

        public void b() {
            a(2, null);
        }

        public void a(String str, List<MediaItem> list, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", str);
            bundle2.putBundle("data_options", bundle);
            if (list != null) {
                bundle2.putParcelableArrayList("data_media_item_list", list instanceof ArrayList ? (ArrayList) list : new ArrayList(list));
            }
            a(3, bundle2);
        }

        private void a(int i, Bundle bundle) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 2;
            obtain.setData(bundle);
            this.a.send(obtain);
        }
    }

    final class ServiceHandler extends Handler {
        private final ServiceBinderImpl b = new ServiceBinderImpl();

        ServiceHandler() {
        }

        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1:
                    this.b.a(data.getString("data_package_name"), data.getInt("data_calling_uid"), data.getBundle("data_root_hints"), (ServiceCallbacks) new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 2:
                    this.b.a(new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 3:
                    this.b.a(data.getString("data_media_item_id"), BundleCompat.getBinder(data, "data_callback_token"), data.getBundle("data_options"), (ServiceCallbacks) new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 4:
                    this.b.a(data.getString("data_media_item_id"), BundleCompat.getBinder(data, "data_callback_token"), (ServiceCallbacks) new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 5:
                    this.b.a(data.getString("data_media_item_id"), (ResultReceiver) data.getParcelable("data_result_receiver"), (ServiceCallbacks) new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 6:
                    this.b.a(new ServiceCallbacksCompat(message.replyTo), data.getBundle("data_root_hints"));
                    return;
                case 7:
                    this.b.b(new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 8:
                    this.b.a(data.getString("data_search_query"), data.getBundle("data_search_extras"), (ResultReceiver) data.getParcelable("data_result_receiver"), (ServiceCallbacks) new ServiceCallbacksCompat(message.replyTo));
                    return;
                case 9:
                    this.b.b(data.getString("data_custom_action"), data.getBundle("data_custom_action_extras"), (ResultReceiver) data.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unhandled message: ");
                    sb.append(message);
                    sb.append("\n  Service version: ");
                    sb.append(2);
                    sb.append("\n  Client version: ");
                    sb.append(message.arg1);
                    Log.w("MBServiceCompat", sb.toString());
                    return;
            }
        }

        public boolean sendMessageAtTime(Message message, long j) {
            Bundle data = message.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt("data_calling_uid", Binder.getCallingUid());
            return super.sendMessageAtTime(message, j);
        }

        public void a(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run();
            } else {
                post(runnable);
            }
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result);

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 26) {
            this.f = new MediaBrowserServiceImplApi26();
        } else if (VERSION.SDK_INT >= 23) {
            this.f = new MediaBrowserServiceImplApi23();
        } else if (VERSION.SDK_INT >= 21) {
            this.f = new MediaBrowserServiceImplApi21();
        } else {
            this.f = new MediaBrowserServiceImplBase();
        }
        this.f.a();
    }

    public IBinder onBind(Intent intent) {
        return this.f.a(intent);
    }

    public void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result, @NonNull Bundle bundle) {
        result.a(1);
        onLoadChildren(str, result);
    }

    public void onLoadItem(String str, @NonNull Result<MediaItem> result) {
        result.a(2);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String str, Bundle bundle, @NonNull Result<List<MediaItem>> result) {
        result.a(4);
        result.sendResult(null);
    }

    public void onCustomAction(@NonNull String str, Bundle bundle, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    public void setSessionToken(Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.e != null) {
            throw new IllegalStateException("The session token has already been set.");
        } else {
            this.e = token;
            this.f.a(token);
        }
    }

    @Nullable
    public Token getSessionToken() {
        return this.e;
    }

    public final Bundle getBrowserRootHints() {
        return this.f.b();
    }

    public void notifyChildrenChanged(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.f.a(str, null);
    }

    public void notifyChildrenChanged(@NonNull String str, @NonNull Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (bundle == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        } else {
            this.f.a(str, bundle);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(String str, int i) {
        if (str == null) {
            return false;
        }
        for (String equals : getPackageManager().getPackagesForUid(i)) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, ConnectionRecord connectionRecord, IBinder iBinder, Bundle bundle) {
        List<Pair> list = (List) connectionRecord.e.get(str);
        if (list == null) {
            list = new ArrayList<>();
        }
        for (Pair pair : list) {
            if (iBinder == pair.first && MediaBrowserCompatUtils.areSameOptions(bundle, (Bundle) pair.second)) {
                return;
            }
        }
        list.add(new Pair(iBinder, bundle));
        connectionRecord.e.put(str, list);
        a(str, connectionRecord, bundle);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(String str, ConnectionRecord connectionRecord, IBinder iBinder) {
        boolean z = false;
        if (iBinder == null) {
            if (connectionRecord.e.remove(str) != null) {
                z = true;
            }
            return z;
        }
        List list = (List) connectionRecord.e.get(str);
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (iBinder == ((Pair) it.next()).first) {
                    it.remove();
                    z = true;
                }
            }
            if (list.size() == 0) {
                connectionRecord.e.remove(str);
            }
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, ConnectionRecord connectionRecord, Bundle bundle) {
        final ConnectionRecord connectionRecord2 = connectionRecord;
        final String str2 = str;
        final Bundle bundle2 = bundle;
        AnonymousClass1 r0 = new Result<List<MediaItem>>(str) {
            /* access modifiers changed from: 0000 */
            public void a(List<MediaItem> list) {
                if (MediaBrowserServiceCompat.this.b.get(connectionRecord2.c.a()) != connectionRecord2) {
                    if (MediaBrowserServiceCompat.a) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
                        sb.append(connectionRecord2.a);
                        sb.append(" id=");
                        sb.append(str2);
                        Log.d("MBServiceCompat", sb.toString());
                    }
                    return;
                }
                if ((b() & 1) != 0) {
                    list = MediaBrowserServiceCompat.this.a(list, bundle2);
                }
                try {
                    connectionRecord2.c.a(str2, list, bundle2);
                } catch (RemoteException unused) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Calling onLoadChildren() failed for id=");
                    sb2.append(str2);
                    sb2.append(" package=");
                    sb2.append(connectionRecord2.a);
                    Log.w("MBServiceCompat", sb2.toString());
                }
            }
        };
        this.c = connectionRecord;
        if (bundle == null) {
            onLoadChildren(str, r0);
        } else {
            onLoadChildren(str, r0, bundle);
        }
        this.c = null;
        if (!r0.a()) {
            StringBuilder sb = new StringBuilder();
            sb.append("onLoadChildren must call detach() or sendResult() before returning for package=");
            sb.append(connectionRecord.a);
            sb.append(" id=");
            sb.append(str);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public List<MediaItem> a(List<MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i == -1 && i2 == -1) {
            return list;
        }
        int i3 = i2 * i;
        int i4 = i3 + i2;
        if (i < 0 || i2 < 1 || i3 >= list.size()) {
            return Collections.EMPTY_LIST;
        }
        if (i4 > list.size()) {
            i4 = list.size();
        }
        return list.subList(i3, i4);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, ConnectionRecord connectionRecord, final ResultReceiver resultReceiver) {
        AnonymousClass2 r0 = new Result<MediaItem>(str) {
            /* access modifiers changed from: 0000 */
            public void a(MediaItem mediaItem) {
                if ((b() & 2) != 0) {
                    resultReceiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, mediaItem);
                resultReceiver.send(0, bundle);
            }
        };
        this.c = connectionRecord;
        onLoadItem(str, r0);
        this.c = null;
        if (!r0.a()) {
            StringBuilder sb = new StringBuilder();
            sb.append("onLoadItem must call detach() or sendResult() before returning for id=");
            sb.append(str);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, Bundle bundle, ConnectionRecord connectionRecord, final ResultReceiver resultReceiver) {
        AnonymousClass3 r0 = new Result<List<MediaItem>>(str) {
            /* access modifiers changed from: 0000 */
            public void a(List<MediaItem> list) {
                if ((b() & 4) != 0 || list == null) {
                    resultReceiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[]) list.toArray(new MediaItem[0]));
                resultReceiver.send(0, bundle);
            }
        };
        this.c = connectionRecord;
        onSearch(str, bundle, r0);
        this.c = null;
        if (!r0.a()) {
            StringBuilder sb = new StringBuilder();
            sb.append("onSearch must call detach() or sendResult() before returning for query=");
            sb.append(str);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(String str, Bundle bundle, ConnectionRecord connectionRecord, final ResultReceiver resultReceiver) {
        AnonymousClass4 r0 = new Result<Bundle>(str) {
            /* access modifiers changed from: 0000 */
            public void a(Bundle bundle) {
                resultReceiver.send(0, bundle);
            }

            /* access modifiers changed from: 0000 */
            public void b(Bundle bundle) {
                resultReceiver.send(1, bundle);
            }

            /* access modifiers changed from: 0000 */
            public void c(Bundle bundle) {
                resultReceiver.send(-1, bundle);
            }
        };
        this.c = connectionRecord;
        onCustomAction(str, bundle, r0);
        this.c = null;
        if (!r0.a()) {
            StringBuilder sb = new StringBuilder();
            sb.append("onCustomAction must call detach() or sendResult() or sendError() before returning for action=");
            sb.append(str);
            sb.append(" extras=");
            sb.append(bundle);
            throw new IllegalStateException(sb.toString());
        }
    }
}
