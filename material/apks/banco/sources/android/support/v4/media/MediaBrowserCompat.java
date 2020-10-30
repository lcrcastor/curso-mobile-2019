package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public final class MediaBrowserCompat {
    public static final String CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD";
    public static final String CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE";
    public static final String EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS";
    public static final String EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID";
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    static final boolean a = Log.isLoggable("MediaBrowserCompat", 3);
    private final MediaBrowserImpl b;

    static class CallbackHandler extends Handler {
        private final WeakReference<MediaBrowserServiceCallbackImpl> a;
        private WeakReference<Messenger> b;

        CallbackHandler(MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl) {
            this.a = new WeakReference<>(mediaBrowserServiceCallbackImpl);
        }

        public void handleMessage(Message message) {
            if (this.b != null && this.b.get() != null && this.a.get() != null) {
                Bundle data = message.getData();
                data.setClassLoader(MediaSessionCompat.class.getClassLoader());
                MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl = (MediaBrowserServiceCallbackImpl) this.a.get();
                Messenger messenger = (Messenger) this.b.get();
                try {
                    switch (message.what) {
                        case 1:
                            mediaBrowserServiceCallbackImpl.a(messenger, data.getString("data_media_item_id"), (Token) data.getParcelable("data_media_session_token"), data.getBundle("data_root_hints"));
                            break;
                        case 2:
                            mediaBrowserServiceCallbackImpl.a(messenger);
                            break;
                        case 3:
                            mediaBrowserServiceCallbackImpl.a(messenger, data.getString("data_media_item_id"), (List) data.getParcelableArrayList("data_media_item_list"), data.getBundle("data_options"));
                            break;
                        default:
                            String str = "MediaBrowserCompat";
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unhandled message: ");
                            sb.append(message);
                            sb.append("\n  Client version: ");
                            sb.append(1);
                            sb.append("\n  Service version: ");
                            sb.append(message.arg1);
                            Log.w(str, sb.toString());
                            break;
                    }
                } catch (BadParcelableException unused) {
                    Log.e("MediaBrowserCompat", "Could not unparcel the data.");
                    if (message.what == 1) {
                        mediaBrowserServiceCallbackImpl.a(messenger);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Messenger messenger) {
            this.b = new WeakReference<>(messenger);
        }
    }

    public static class ConnectionCallback {
        final Object a;
        ConnectionCallbackInternal b;

        interface ConnectionCallbackInternal {
            void a();

            void b();

            void c();
        }

        class StubApi21 implements ConnectionCallback {
            StubApi21() {
            }

            public void a() {
                if (ConnectionCallback.this.b != null) {
                    ConnectionCallback.this.b.a();
                }
                ConnectionCallback.this.onConnected();
            }

            public void b() {
                if (ConnectionCallback.this.b != null) {
                    ConnectionCallback.this.b.b();
                }
                ConnectionCallback.this.onConnectionSuspended();
            }

            public void c() {
                if (ConnectionCallback.this.b != null) {
                    ConnectionCallback.this.b.c();
                }
                ConnectionCallback.this.onConnectionFailed();
            }
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }

        public ConnectionCallback() {
            if (VERSION.SDK_INT >= 21) {
                this.a = MediaBrowserCompatApi21.a((ConnectionCallback) new StubApi21());
            } else {
                this.a = null;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ConnectionCallbackInternal connectionCallbackInternal) {
            this.b = connectionCallbackInternal;
        }
    }

    public static abstract class CustomActionCallback {
        public void onError(String str, Bundle bundle, Bundle bundle2) {
        }

        public void onProgressUpdate(String str, Bundle bundle, Bundle bundle2) {
        }

        public void onResult(String str, Bundle bundle, Bundle bundle2) {
        }
    }

    static class CustomActionResultReceiver extends ResultReceiver {
        private final String d;
        private final Bundle e;
        private final CustomActionCallback f;

        CustomActionResultReceiver(String str, Bundle bundle, CustomActionCallback customActionCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = bundle;
            this.f = customActionCallback;
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int i, Bundle bundle) {
            if (this.f != null) {
                switch (i) {
                    case -1:
                        this.f.onError(this.d, this.e, bundle);
                        break;
                    case 0:
                        this.f.onResult(this.d, this.e, bundle);
                        break;
                    case 1:
                        this.f.onProgressUpdate(this.d, this.e, bundle);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unknown result code: ");
                        sb.append(i);
                        sb.append(" (extras=");
                        sb.append(this.e);
                        sb.append(", resultData=");
                        sb.append(bundle);
                        sb.append(")");
                        Log.w("MediaBrowserCompat", sb.toString());
                        break;
                }
            }
        }
    }

    public static abstract class ItemCallback {
        final Object a;

        class StubApi23 implements ItemCallback {
            StubApi23() {
            }

            public void a(Parcel parcel) {
                if (parcel == null) {
                    ItemCallback.this.onItemLoaded(null);
                    return;
                }
                parcel.setDataPosition(0);
                MediaItem mediaItem = (MediaItem) MediaItem.CREATOR.createFromParcel(parcel);
                parcel.recycle();
                ItemCallback.this.onItemLoaded(mediaItem);
            }

            public void a(@NonNull String str) {
                ItemCallback.this.onError(str);
            }
        }

        public void onError(@NonNull String str) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }

        public ItemCallback() {
            if (VERSION.SDK_INT >= 23) {
                this.a = MediaBrowserCompatApi23.a(new StubApi23());
            } else {
                this.a = null;
            }
        }
    }

    static class ItemReceiver extends ResultReceiver {
        private final String d;
        private final ItemCallback e;

        ItemReceiver(String str, ItemCallback itemCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = itemCallback;
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                this.e.onError(this.d);
                return;
            }
            Parcelable parcelable = bundle.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
            if (parcelable == null || (parcelable instanceof MediaItem)) {
                this.e.onItemLoaded((MediaItem) parcelable);
            } else {
                this.e.onError(this.d);
            }
        }
    }

    interface MediaBrowserImpl {
        void a(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback);

        void a(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback);

        void a(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback);

        void a(@NonNull String str, @NonNull ItemCallback itemCallback);

        void a(@NonNull String str, SubscriptionCallback subscriptionCallback);

        void d();

        void e();

        boolean f();

        ComponentName g();

        @NonNull
        String h();

        @Nullable
        Bundle i();

        @NonNull
        Token j();
    }

    @RequiresApi(21)
    static class MediaBrowserImplApi21 implements ConnectionCallbackInternal, MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        final Context a;
        protected final Object b;
        protected final Bundle c;
        protected final CallbackHandler d = new CallbackHandler(this);
        protected int e;
        protected ServiceBinderWrapper f;
        protected Messenger g;
        private final ArrayMap<String, Subscription> h = new ArrayMap<>();
        private Token i;

        public void a(Messenger messenger) {
        }

        public void a(Messenger messenger, String str, Token token, Bundle bundle) {
        }

        public void c() {
        }

        MediaBrowserImplApi21(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.a = context;
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt("extra_client_version", 1);
            this.c = new Bundle(bundle);
            connectionCallback.a(this);
            this.b = MediaBrowserCompatApi21.a(context, componentName, connectionCallback.a, this.c);
        }

        public void d() {
            MediaBrowserCompatApi21.a(this.b);
        }

        public void e() {
            if (!(this.f == null || this.g == null)) {
                try {
                    this.f.c(this.g);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error unregistering client messenger.");
                }
            }
            MediaBrowserCompatApi21.b(this.b);
        }

        public boolean f() {
            return MediaBrowserCompatApi21.c(this.b);
        }

        public ComponentName g() {
            return MediaBrowserCompatApi21.d(this.b);
        }

        @NonNull
        public String h() {
            return MediaBrowserCompatApi21.e(this.b);
        }

        @Nullable
        public Bundle i() {
            return MediaBrowserCompatApi21.f(this.b);
        }

        @NonNull
        public Token j() {
            if (this.i == null) {
                this.i = Token.fromToken(MediaBrowserCompatApi21.g(this.b));
            }
            return this.i;
        }

        public void a(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            Bundle bundle2;
            Subscription subscription = (Subscription) this.h.get(str);
            if (subscription == null) {
                subscription = new Subscription();
                this.h.put(str, subscription);
            }
            subscriptionCallback.a(subscription);
            if (bundle == null) {
                bundle2 = null;
            } else {
                bundle2 = new Bundle(bundle);
            }
            subscription.a(this.a, bundle2, subscriptionCallback);
            if (this.f == null) {
                MediaBrowserCompatApi21.a(this.b, str, subscriptionCallback.b);
                return;
            }
            try {
                this.f.a(str, subscriptionCallback.c, bundle2, this.g);
            } catch (RemoteException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Remote error subscribing media item: ");
                sb.append(str);
                Log.i("MediaBrowserCompat", sb.toString());
            }
        }

        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            Subscription subscription = (Subscription) this.h.get(str);
            if (subscription != null) {
                if (this.f == null) {
                    if (subscriptionCallback == null) {
                        MediaBrowserCompatApi21.a(this.b, str);
                    } else {
                        List c2 = subscription.c();
                        List b2 = subscription.b();
                        for (int size = c2.size() - 1; size >= 0; size--) {
                            if (c2.get(size) == subscriptionCallback) {
                                c2.remove(size);
                                b2.remove(size);
                            }
                        }
                        if (c2.size() == 0) {
                            MediaBrowserCompatApi21.a(this.b, str);
                        }
                    }
                } else if (subscriptionCallback == null) {
                    try {
                        this.f.a(str, (IBinder) null, this.g);
                    } catch (RemoteException unused) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("removeSubscription failed with RemoteException parentId=");
                        sb.append(str);
                        Log.d("MediaBrowserCompat", sb.toString());
                    }
                } else {
                    List c3 = subscription.c();
                    List b3 = subscription.b();
                    for (int size2 = c3.size() - 1; size2 >= 0; size2--) {
                        if (c3.get(size2) == subscriptionCallback) {
                            this.f.a(str, subscriptionCallback.c, this.g);
                            c3.remove(size2);
                            b3.remove(size2);
                        }
                    }
                }
                if (subscription.a() || subscriptionCallback == null) {
                    this.h.remove(str);
                }
            }
        }

        public void a(@NonNull final String str, @NonNull final ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (itemCallback == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (!MediaBrowserCompatApi21.c(this.b)) {
                Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
                this.d.post(new Runnable() {
                    public void run() {
                        itemCallback.onError(str);
                    }
                });
            } else if (this.f == null) {
                this.d.post(new Runnable() {
                    public void run() {
                        itemCallback.onError(str);
                    }
                });
            } else {
                try {
                    this.f.a(str, (ResultReceiver) new ItemReceiver(str, itemCallback, this.d), this.g);
                } catch (RemoteException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Remote error getting media item: ");
                    sb.append(str);
                    Log.i("MediaBrowserCompat", sb.toString());
                    this.d.post(new Runnable() {
                        public void run() {
                            itemCallback.onError(str);
                        }
                    });
                }
            }
        }

        public void a(@NonNull final String str, final Bundle bundle, @NonNull final SearchCallback searchCallback) {
            if (!f()) {
                throw new IllegalStateException("search() called while not connected");
            } else if (this.f == null) {
                Log.i("MediaBrowserCompat", "The connected service doesn't support search.");
                this.d.post(new Runnable() {
                    public void run() {
                        searchCallback.onError(str, bundle);
                    }
                });
            } else {
                try {
                    this.f.a(str, bundle, (ResultReceiver) new SearchResultReceiver(str, bundle, searchCallback, this.d), this.g);
                } catch (RemoteException e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Remote error searching items with query: ");
                    sb.append(str);
                    Log.i("MediaBrowserCompat", sb.toString(), e2);
                    this.d.post(new Runnable() {
                        public void run() {
                            searchCallback.onError(str, bundle);
                        }
                    });
                }
            }
        }

        public void a(@NonNull final String str, final Bundle bundle, @Nullable final CustomActionCallback customActionCallback) {
            if (!f()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot send a custom action (");
                sb.append(str);
                sb.append(") with ");
                sb.append("extras ");
                sb.append(bundle);
                sb.append(" because the browser is not connected to the ");
                sb.append("service.");
                throw new IllegalStateException(sb.toString());
            }
            if (this.f == null) {
                Log.i("MediaBrowserCompat", "The connected service doesn't support sendCustomAction.");
                if (customActionCallback != null) {
                    this.d.post(new Runnable() {
                        public void run() {
                            customActionCallback.onError(str, bundle, null);
                        }
                    });
                }
            }
            try {
                this.f.b(str, bundle, new CustomActionResultReceiver(str, bundle, customActionCallback, this.d), this.g);
            } catch (RemoteException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Remote error sending a custom action: action=");
                sb2.append(str);
                sb2.append(", extras=");
                sb2.append(bundle);
                Log.i("MediaBrowserCompat", sb2.toString(), e2);
                if (customActionCallback != null) {
                    this.d.post(new Runnable() {
                        public void run() {
                            customActionCallback.onError(str, bundle, null);
                        }
                    });
                }
            }
        }

        public void a() {
            Bundle f2 = MediaBrowserCompatApi21.f(this.b);
            if (f2 != null) {
                this.e = f2.getInt("extra_service_version", 0);
                IBinder binder = BundleCompat.getBinder(f2, "extra_messenger");
                if (binder != null) {
                    this.f = new ServiceBinderWrapper(binder, this.c);
                    this.g = new Messenger(this.d);
                    this.d.a(this.g);
                    try {
                        this.f.b(this.g);
                    } catch (RemoteException unused) {
                        Log.i("MediaBrowserCompat", "Remote error registering client messenger.");
                    }
                }
                IMediaSession asInterface = Stub.asInterface(BundleCompat.getBinder(f2, "extra_session_binder"));
                if (asInterface != null) {
                    this.i = Token.fromToken(MediaBrowserCompatApi21.g(this.b), asInterface);
                }
            }
        }

        public void b() {
            this.f = null;
            this.g = null;
            this.i = null;
            this.d.a(null);
        }

        public void a(Messenger messenger, String str, List list, Bundle bundle) {
            if (this.g == messenger) {
                Subscription subscription = (Subscription) this.h.get(str);
                if (subscription == null) {
                    if (MediaBrowserCompat.a) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("onLoadChildren for id that isn't subscribed id=");
                        sb.append(str);
                        Log.d("MediaBrowserCompat", sb.toString());
                    }
                    return;
                }
                SubscriptionCallback a2 = subscription.a(this.a, bundle);
                if (a2 != null) {
                    if (bundle == null) {
                        if (list == null) {
                            a2.onError(str);
                        } else {
                            a2.onChildrenLoaded(str, list);
                        }
                    } else if (list == null) {
                        a2.onError(str, bundle);
                    } else {
                        a2.onChildrenLoaded(str, list, bundle);
                    }
                }
            }
        }
    }

    @RequiresApi(23)
    static class MediaBrowserImplApi23 extends MediaBrowserImplApi21 {
        MediaBrowserImplApi23(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        public void a(@NonNull String str, @NonNull ItemCallback itemCallback) {
            if (this.f == null) {
                MediaBrowserCompatApi23.a(this.b, str, itemCallback.a);
            } else {
                super.a(str, itemCallback);
            }
        }
    }

    @RequiresApi(26)
    static class MediaBrowserImplApi26 extends MediaBrowserImplApi23 {
        MediaBrowserImplApi26(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        public void a(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            if (this.f != null && this.e >= 2) {
                super.a(str, bundle, subscriptionCallback);
            } else if (bundle == null) {
                MediaBrowserCompatApi21.a(this.b, str, subscriptionCallback.b);
            } else {
                MediaBrowserCompatApi26.a(this.b, str, bundle, subscriptionCallback.b);
            }
        }

        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            if (this.f != null && this.e >= 2) {
                super.a(str, subscriptionCallback);
            } else if (subscriptionCallback == null) {
                MediaBrowserCompatApi21.a(this.b, str);
            } else {
                MediaBrowserCompatApi26.a(this.b, str, subscriptionCallback.b);
            }
        }
    }

    static class MediaBrowserImplBase implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        final Context a;
        final ComponentName b;
        final ConnectionCallback c;
        final Bundle d;
        final CallbackHandler e = new CallbackHandler(this);
        int f = 1;
        MediaServiceConnection g;
        ServiceBinderWrapper h;
        Messenger i;
        private final ArrayMap<String, Subscription> j = new ArrayMap<>();
        private String k;
        private Token l;
        private Bundle m;

        class MediaServiceConnection implements ServiceConnection {
            MediaServiceConnection() {
            }

            public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
                a((Runnable) new Runnable() {
                    public void run() {
                        if (MediaBrowserCompat.a) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("MediaServiceConnection.onServiceConnected name=");
                            sb.append(componentName);
                            sb.append(" binder=");
                            sb.append(iBinder);
                            Log.d("MediaBrowserCompat", sb.toString());
                            MediaBrowserImplBase.this.b();
                        }
                        if (MediaServiceConnection.this.a("onServiceConnected")) {
                            MediaBrowserImplBase.this.h = new ServiceBinderWrapper(iBinder, MediaBrowserImplBase.this.d);
                            MediaBrowserImplBase.this.i = new Messenger(MediaBrowserImplBase.this.e);
                            MediaBrowserImplBase.this.e.a(MediaBrowserImplBase.this.i);
                            MediaBrowserImplBase.this.f = 2;
                            try {
                                if (MediaBrowserCompat.a) {
                                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                                    MediaBrowserImplBase.this.b();
                                }
                                MediaBrowserImplBase.this.h.a(MediaBrowserImplBase.this.a, MediaBrowserImplBase.this.i);
                            } catch (RemoteException unused) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("RemoteException during connect for ");
                                sb2.append(MediaBrowserImplBase.this.b);
                                Log.w("MediaBrowserCompat", sb2.toString());
                                if (MediaBrowserCompat.a) {
                                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                                    MediaBrowserImplBase.this.b();
                                }
                            }
                        }
                    }
                });
            }

            public void onServiceDisconnected(final ComponentName componentName) {
                a((Runnable) new Runnable() {
                    public void run() {
                        if (MediaBrowserCompat.a) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("MediaServiceConnection.onServiceDisconnected name=");
                            sb.append(componentName);
                            sb.append(" this=");
                            sb.append(this);
                            sb.append(" mServiceConnection=");
                            sb.append(MediaBrowserImplBase.this.g);
                            Log.d("MediaBrowserCompat", sb.toString());
                            MediaBrowserImplBase.this.b();
                        }
                        if (MediaServiceConnection.this.a("onServiceDisconnected")) {
                            MediaBrowserImplBase.this.h = null;
                            MediaBrowserImplBase.this.i = null;
                            MediaBrowserImplBase.this.e.a(null);
                            MediaBrowserImplBase.this.f = 4;
                            MediaBrowserImplBase.this.c.onConnectionSuspended();
                        }
                    }
                });
            }

            private void a(Runnable runnable) {
                if (Thread.currentThread() == MediaBrowserImplBase.this.e.getLooper().getThread()) {
                    runnable.run();
                } else {
                    MediaBrowserImplBase.this.e.post(runnable);
                }
            }

            /* access modifiers changed from: 0000 */
            public boolean a(String str) {
                if (MediaBrowserImplBase.this.g == this && MediaBrowserImplBase.this.f != 0 && MediaBrowserImplBase.this.f != 1) {
                    return true;
                }
                if (!(MediaBrowserImplBase.this.f == 0 || MediaBrowserImplBase.this.f == 1)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(" for ");
                    sb.append(MediaBrowserImplBase.this.b);
                    sb.append(" with mServiceConnection=");
                    sb.append(MediaBrowserImplBase.this.g);
                    sb.append(" this=");
                    sb.append(this);
                    Log.i("MediaBrowserCompat", sb.toString());
                }
                return false;
            }
        }

        public MediaBrowserImplBase(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            Bundle bundle2;
            if (context == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if (componentName == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if (connectionCallback == null) {
                throw new IllegalArgumentException("connection callback must not be null");
            } else {
                this.a = context;
                this.b = componentName;
                this.c = connectionCallback;
                if (bundle == null) {
                    bundle2 = null;
                } else {
                    bundle2 = new Bundle(bundle);
                }
                this.d = bundle2;
            }
        }

        public void d() {
            if (this.f == 0 || this.f == 1) {
                this.f = 2;
                this.e.post(new Runnable() {
                    public void run() {
                        boolean z;
                        if (MediaBrowserImplBase.this.f != 0) {
                            MediaBrowserImplBase.this.f = 2;
                            if (MediaBrowserCompat.a && MediaBrowserImplBase.this.g != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("mServiceConnection should be null. Instead it is ");
                                sb.append(MediaBrowserImplBase.this.g);
                                throw new RuntimeException(sb.toString());
                            } else if (MediaBrowserImplBase.this.h != null) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("mServiceBinderWrapper should be null. Instead it is ");
                                sb2.append(MediaBrowserImplBase.this.h);
                                throw new RuntimeException(sb2.toString());
                            } else if (MediaBrowserImplBase.this.i != null) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("mCallbacksMessenger should be null. Instead it is ");
                                sb3.append(MediaBrowserImplBase.this.i);
                                throw new RuntimeException(sb3.toString());
                            } else {
                                Intent intent = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
                                intent.setComponent(MediaBrowserImplBase.this.b);
                                MediaBrowserImplBase.this.g = new MediaServiceConnection();
                                try {
                                    z = MediaBrowserImplBase.this.a.bindService(intent, MediaBrowserImplBase.this.g, 1);
                                } catch (Exception unused) {
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append("Failed binding to service ");
                                    sb4.append(MediaBrowserImplBase.this.b);
                                    Log.e("MediaBrowserCompat", sb4.toString());
                                    z = false;
                                }
                                if (!z) {
                                    MediaBrowserImplBase.this.a();
                                    MediaBrowserImplBase.this.c.onConnectionFailed();
                                }
                                if (MediaBrowserCompat.a) {
                                    Log.d("MediaBrowserCompat", "connect...");
                                    MediaBrowserImplBase.this.b();
                                }
                            }
                        }
                    }
                });
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("connect() called while neigther disconnecting nor disconnected (state=");
            sb.append(a(this.f));
            sb.append(")");
            throw new IllegalStateException(sb.toString());
        }

        public void e() {
            this.f = 0;
            this.e.post(new Runnable() {
                public void run() {
                    if (MediaBrowserImplBase.this.i != null) {
                        try {
                            MediaBrowserImplBase.this.h.a(MediaBrowserImplBase.this.i);
                        } catch (RemoteException unused) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("RemoteException during connect for ");
                            sb.append(MediaBrowserImplBase.this.b);
                            Log.w("MediaBrowserCompat", sb.toString());
                        }
                    }
                    int i = MediaBrowserImplBase.this.f;
                    MediaBrowserImplBase.this.a();
                    if (i != 0) {
                        MediaBrowserImplBase.this.f = i;
                    }
                    if (MediaBrowserCompat.a) {
                        Log.d("MediaBrowserCompat", "disconnect...");
                        MediaBrowserImplBase.this.b();
                    }
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.g != null) {
                this.a.unbindService(this.g);
            }
            this.f = 1;
            this.g = null;
            this.h = null;
            this.i = null;
            this.e.a(null);
            this.k = null;
            this.l = null;
        }

        public boolean f() {
            return this.f == 3;
        }

        @NonNull
        public ComponentName g() {
            if (f()) {
                return this.b;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("getServiceComponent() called while not connected (state=");
            sb.append(this.f);
            sb.append(")");
            throw new IllegalStateException(sb.toString());
        }

        @NonNull
        public String h() {
            if (f()) {
                return this.k;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("getRoot() called while not connected(state=");
            sb.append(a(this.f));
            sb.append(")");
            throw new IllegalStateException(sb.toString());
        }

        @Nullable
        public Bundle i() {
            if (f()) {
                return this.m;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("getExtras() called while not connected (state=");
            sb.append(a(this.f));
            sb.append(")");
            throw new IllegalStateException(sb.toString());
        }

        @NonNull
        public Token j() {
            if (f()) {
                return this.l;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("getSessionToken() called while not connected(state=");
            sb.append(this.f);
            sb.append(")");
            throw new IllegalStateException(sb.toString());
        }

        public void a(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            Bundle bundle2;
            Subscription subscription = (Subscription) this.j.get(str);
            if (subscription == null) {
                subscription = new Subscription();
                this.j.put(str, subscription);
            }
            if (bundle == null) {
                bundle2 = null;
            } else {
                bundle2 = new Bundle(bundle);
            }
            subscription.a(this.a, bundle2, subscriptionCallback);
            if (f()) {
                try {
                    this.h.a(str, subscriptionCallback.c, bundle2, this.i);
                } catch (RemoteException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("addSubscription failed with RemoteException parentId=");
                    sb.append(str);
                    Log.d("MediaBrowserCompat", sb.toString());
                }
            }
        }

        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            Subscription subscription = (Subscription) this.j.get(str);
            if (subscription != null) {
                if (subscriptionCallback == null) {
                    try {
                        if (f()) {
                            this.h.a(str, (IBinder) null, this.i);
                        }
                    } catch (RemoteException unused) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("removeSubscription failed with RemoteException parentId=");
                        sb.append(str);
                        Log.d("MediaBrowserCompat", sb.toString());
                    }
                } else {
                    List c2 = subscription.c();
                    List b2 = subscription.b();
                    for (int size = c2.size() - 1; size >= 0; size--) {
                        if (c2.get(size) == subscriptionCallback) {
                            if (f()) {
                                this.h.a(str, subscriptionCallback.c, this.i);
                            }
                            c2.remove(size);
                            b2.remove(size);
                        }
                    }
                }
                if (subscription.a() || subscriptionCallback == null) {
                    this.j.remove(str);
                }
            }
        }

        public void a(@NonNull final String str, @NonNull final ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (itemCallback == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (!f()) {
                Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
                this.e.post(new Runnable() {
                    public void run() {
                        itemCallback.onError(str);
                    }
                });
            } else {
                try {
                    this.h.a(str, (ResultReceiver) new ItemReceiver(str, itemCallback, this.e), this.i);
                } catch (RemoteException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Remote error getting media item: ");
                    sb.append(str);
                    Log.i("MediaBrowserCompat", sb.toString());
                    this.e.post(new Runnable() {
                        public void run() {
                            itemCallback.onError(str);
                        }
                    });
                }
            }
        }

        public void a(@NonNull final String str, final Bundle bundle, @NonNull final SearchCallback searchCallback) {
            if (!f()) {
                StringBuilder sb = new StringBuilder();
                sb.append("search() called while not connected (state=");
                sb.append(a(this.f));
                sb.append(")");
                throw new IllegalStateException(sb.toString());
            }
            try {
                this.h.a(str, bundle, (ResultReceiver) new SearchResultReceiver(str, bundle, searchCallback, this.e), this.i);
            } catch (RemoteException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Remote error searching items with query: ");
                sb2.append(str);
                Log.i("MediaBrowserCompat", sb2.toString(), e2);
                this.e.post(new Runnable() {
                    public void run() {
                        searchCallback.onError(str, bundle);
                    }
                });
            }
        }

        public void a(@NonNull final String str, final Bundle bundle, @Nullable final CustomActionCallback customActionCallback) {
            if (!f()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot send a custom action (");
                sb.append(str);
                sb.append(") with ");
                sb.append("extras ");
                sb.append(bundle);
                sb.append(" because the browser is not connected to the ");
                sb.append("service.");
                throw new IllegalStateException(sb.toString());
            }
            try {
                this.h.b(str, bundle, new CustomActionResultReceiver(str, bundle, customActionCallback, this.e), this.i);
            } catch (RemoteException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Remote error sending a custom action: action=");
                sb2.append(str);
                sb2.append(", extras=");
                sb2.append(bundle);
                Log.i("MediaBrowserCompat", sb2.toString(), e2);
                if (customActionCallback != null) {
                    this.e.post(new Runnable() {
                        public void run() {
                            customActionCallback.onError(str, bundle, null);
                        }
                    });
                }
            }
        }

        public void a(Messenger messenger, String str, Token token, Bundle bundle) {
            if (a(messenger, "onConnect")) {
                if (this.f != 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("onConnect from service while mState=");
                    sb.append(a(this.f));
                    sb.append("... ignoring");
                    Log.w("MediaBrowserCompat", sb.toString());
                    return;
                }
                this.k = str;
                this.l = token;
                this.m = bundle;
                this.f = 3;
                if (MediaBrowserCompat.a) {
                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                    b();
                }
                this.c.onConnected();
                try {
                    for (Entry entry : this.j.entrySet()) {
                        String str2 = (String) entry.getKey();
                        Subscription subscription = (Subscription) entry.getValue();
                        List c2 = subscription.c();
                        List b2 = subscription.b();
                        for (int i2 = 0; i2 < c2.size(); i2++) {
                            this.h.a(str2, ((SubscriptionCallback) c2.get(i2)).c, (Bundle) b2.get(i2), this.i);
                        }
                    }
                } catch (RemoteException unused) {
                    Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException.");
                }
            }
        }

        public void a(Messenger messenger) {
            StringBuilder sb = new StringBuilder();
            sb.append("onConnectFailed for ");
            sb.append(this.b);
            Log.e("MediaBrowserCompat", sb.toString());
            if (a(messenger, "onConnectFailed")) {
                if (this.f != 2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("onConnect from service while mState=");
                    sb2.append(a(this.f));
                    sb2.append("... ignoring");
                    Log.w("MediaBrowserCompat", sb2.toString());
                    return;
                }
                a();
                this.c.onConnectionFailed();
            }
        }

        public void a(Messenger messenger, String str, List list, Bundle bundle) {
            if (a(messenger, "onLoadChildren")) {
                if (MediaBrowserCompat.a) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("onLoadChildren for ");
                    sb.append(this.b);
                    sb.append(" id=");
                    sb.append(str);
                    Log.d("MediaBrowserCompat", sb.toString());
                }
                Subscription subscription = (Subscription) this.j.get(str);
                if (subscription == null) {
                    if (MediaBrowserCompat.a) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("onLoadChildren for id that isn't subscribed id=");
                        sb2.append(str);
                        Log.d("MediaBrowserCompat", sb2.toString());
                    }
                    return;
                }
                SubscriptionCallback a2 = subscription.a(this.a, bundle);
                if (a2 != null) {
                    if (bundle == null) {
                        if (list == null) {
                            a2.onError(str);
                        } else {
                            a2.onChildrenLoaded(str, list);
                        }
                    } else if (list == null) {
                        a2.onError(str, bundle);
                    } else {
                        a2.onChildrenLoaded(str, list, bundle);
                    }
                }
            }
        }

        private static String a(int i2) {
            switch (i2) {
                case 0:
                    return "CONNECT_STATE_DISCONNECTING";
                case 1:
                    return "CONNECT_STATE_DISCONNECTED";
                case 2:
                    return "CONNECT_STATE_CONNECTING";
                case 3:
                    return "CONNECT_STATE_CONNECTED";
                case 4:
                    return "CONNECT_STATE_SUSPENDED";
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("UNKNOWN/");
                    sb.append(i2);
                    return sb.toString();
            }
        }

        private boolean a(Messenger messenger, String str) {
            if (this.i == messenger && this.f != 0 && this.f != 1) {
                return true;
            }
            if (!(this.f == 0 || this.f == 1)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" for ");
                sb.append(this.b);
                sb.append(" with mCallbacksMessenger=");
                sb.append(this.i);
                sb.append(" this=");
                sb.append(this);
                Log.i("MediaBrowserCompat", sb.toString());
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            Log.d("MediaBrowserCompat", "MediaBrowserCompat...");
            StringBuilder sb = new StringBuilder();
            sb.append("  mServiceComponent=");
            sb.append(this.b);
            Log.d("MediaBrowserCompat", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("  mCallback=");
            sb2.append(this.c);
            Log.d("MediaBrowserCompat", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("  mRootHints=");
            sb3.append(this.d);
            Log.d("MediaBrowserCompat", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("  mState=");
            sb4.append(a(this.f));
            Log.d("MediaBrowserCompat", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append("  mServiceConnection=");
            sb5.append(this.g);
            Log.d("MediaBrowserCompat", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("  mServiceBinderWrapper=");
            sb6.append(this.h);
            Log.d("MediaBrowserCompat", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append("  mCallbacksMessenger=");
            sb7.append(this.i);
            Log.d("MediaBrowserCompat", sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("  mRootId=");
            sb8.append(this.k);
            Log.d("MediaBrowserCompat", sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append("  mMediaSessionToken=");
            sb9.append(this.l);
            Log.d("MediaBrowserCompat", sb9.toString());
        }
    }

    interface MediaBrowserServiceCallbackImpl {
        void a(Messenger messenger);

        void a(Messenger messenger, String str, Token token, Bundle bundle);

        void a(Messenger messenger, String str, List list, Bundle bundle);
    }

    public static class MediaItem implements Parcelable {
        public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
            /* renamed from: a */
            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            /* renamed from: a */
            public MediaItem[] newArray(int i) {
                return new MediaItem[i];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final int a;
        private final MediaDescriptionCompat b;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        public int describeContents() {
            return 0;
        }

        public static MediaItem fromMediaItem(Object obj) {
            if (obj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            return new MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaItem.b(obj)), MediaItem.a(obj));
        }

        public static List<MediaItem> fromMediaItemList(List<?> list) {
            if (list == null || VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList(list.size());
            for (Object fromMediaItem : list) {
                arrayList.add(fromMediaItem(fromMediaItem));
            }
            return arrayList;
        }

        public MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (TextUtils.isEmpty(mediaDescriptionCompat.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            } else {
                this.a = i;
                this.b = mediaDescriptionCompat;
            }
        }

        MediaItem(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            this.b.writeToParcel(parcel, i);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("MediaItem{");
            sb.append("mFlags=");
            sb.append(this.a);
            sb.append(", mDescription=");
            sb.append(this.b);
            sb.append('}');
            return sb.toString();
        }

        public int getFlags() {
            return this.a;
        }

        public boolean isBrowsable() {
            return (this.a & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.a & 2) != 0;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() {
            return this.b;
        }

        @Nullable
        public String getMediaId() {
            return this.b.getMediaId();
        }
    }

    public static abstract class SearchCallback {
        public void onError(@NonNull String str, Bundle bundle) {
        }

        public void onSearchResult(@NonNull String str, Bundle bundle, @NonNull List<MediaItem> list) {
        }
    }

    static class SearchResultReceiver extends ResultReceiver {
        private final String d;
        private final Bundle e;
        private final SearchCallback f;

        SearchResultReceiver(String str, Bundle bundle, SearchCallback searchCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = bundle;
            this.f = searchCallback;
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)) {
                this.f.onError(this.d, this.e);
                return;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS);
            ArrayList arrayList = null;
            if (parcelableArray != null) {
                arrayList = new ArrayList();
                for (Parcelable parcelable : parcelableArray) {
                    arrayList.add((MediaItem) parcelable);
                }
            }
            this.f.onSearchResult(this.d, this.e, arrayList);
        }
    }

    static class ServiceBinderWrapper {
        private Messenger a;
        private Bundle b;

        public ServiceBinderWrapper(IBinder iBinder, Bundle bundle) {
            this.a = new Messenger(iBinder);
            this.b = bundle;
        }

        /* access modifiers changed from: 0000 */
        public void a(Context context, Messenger messenger) {
            Bundle bundle = new Bundle();
            bundle.putString("data_package_name", context.getPackageName());
            bundle.putBundle("data_root_hints", this.b);
            a(1, bundle, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void a(Messenger messenger) {
            a(2, (Bundle) null, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void a(String str, IBinder iBinder, Bundle bundle, Messenger messenger) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", str);
            BundleCompat.putBinder(bundle2, "data_callback_token", iBinder);
            bundle2.putBundle("data_options", bundle);
            a(3, bundle2, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void a(String str, IBinder iBinder, Messenger messenger) {
            Bundle bundle = new Bundle();
            bundle.putString("data_media_item_id", str);
            BundleCompat.putBinder(bundle, "data_callback_token", iBinder);
            a(4, bundle, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void a(String str, ResultReceiver resultReceiver, Messenger messenger) {
            Bundle bundle = new Bundle();
            bundle.putString("data_media_item_id", str);
            bundle.putParcelable("data_result_receiver", resultReceiver);
            a(5, bundle, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void b(Messenger messenger) {
            Bundle bundle = new Bundle();
            bundle.putBundle("data_root_hints", this.b);
            a(6, bundle, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void c(Messenger messenger) {
            a(7, (Bundle) null, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void a(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_search_query", str);
            bundle2.putBundle("data_search_extras", bundle);
            bundle2.putParcelable("data_result_receiver", resultReceiver);
            a(8, bundle2, messenger);
        }

        /* access modifiers changed from: 0000 */
        public void b(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_custom_action", str);
            bundle2.putBundle("data_custom_action_extras", bundle);
            bundle2.putParcelable("data_result_receiver", resultReceiver);
            a(9, bundle2, messenger);
        }

        private void a(int i, Bundle bundle, Messenger messenger) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 1;
            obtain.setData(bundle);
            obtain.replyTo = messenger;
            this.a.send(obtain);
        }
    }

    static class Subscription {
        private final List<SubscriptionCallback> a = new ArrayList();
        private final List<Bundle> b = new ArrayList();

        public boolean a() {
            return this.a.isEmpty();
        }

        public List<Bundle> b() {
            return this.b;
        }

        public List<SubscriptionCallback> c() {
            return this.a;
        }

        public SubscriptionCallback a(Context context, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.b.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.b.get(i), bundle)) {
                    return (SubscriptionCallback) this.a.get(i);
                }
            }
            return null;
        }

        public void a(Context context, Bundle bundle, SubscriptionCallback subscriptionCallback) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.b.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.b.get(i), bundle)) {
                    this.a.set(i, subscriptionCallback);
                    return;
                }
            }
            this.a.add(subscriptionCallback);
            this.b.add(bundle);
        }
    }

    public static abstract class SubscriptionCallback {
        WeakReference<Subscription> a;
        /* access modifiers changed from: private */
        public final Object b;
        /* access modifiers changed from: private */
        public final IBinder c = new Binder();

        class StubApi21 implements SubscriptionCallback {
            StubApi21() {
            }

            public void a(@NonNull String str, List<?> list) {
                Subscription subscription = SubscriptionCallback.this.a == null ? null : (Subscription) SubscriptionCallback.this.a.get();
                if (subscription == null) {
                    SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list));
                    return;
                }
                List fromMediaItemList = MediaItem.fromMediaItemList(list);
                List c = subscription.c();
                List b = subscription.b();
                for (int i = 0; i < c.size(); i++) {
                    Bundle bundle = (Bundle) b.get(i);
                    if (bundle == null) {
                        SubscriptionCallback.this.onChildrenLoaded(str, fromMediaItemList);
                    } else {
                        SubscriptionCallback.this.onChildrenLoaded(str, a(fromMediaItemList, bundle), bundle);
                    }
                }
            }

            public void a(@NonNull String str) {
                SubscriptionCallback.this.onError(str);
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
        }

        class StubApi26 extends StubApi21 implements SubscriptionCallback {
            StubApi26() {
                super();
            }

            public void a(@NonNull String str, List<?> list, @NonNull Bundle bundle) {
                SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list), bundle);
            }

            public void a(@NonNull String str, @NonNull Bundle bundle) {
                SubscriptionCallback.this.onError(str, bundle);
            }
        }

        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list) {
        }

        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list, @NonNull Bundle bundle) {
        }

        public void onError(@NonNull String str) {
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
        }

        public SubscriptionCallback() {
            if (VERSION.SDK_INT >= 26) {
                this.b = MediaBrowserCompatApi26.a(new StubApi26());
            } else if (VERSION.SDK_INT >= 21) {
                this.b = MediaBrowserCompatApi21.a((SubscriptionCallback) new StubApi21());
            } else {
                this.b = null;
            }
        }

        /* access modifiers changed from: private */
        public void a(Subscription subscription) {
            this.a = new WeakReference<>(subscription);
        }
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (VERSION.SDK_INT >= 26) {
            this.b = new MediaBrowserImplApi26(context, componentName, connectionCallback, bundle);
        } else if (VERSION.SDK_INT >= 23) {
            this.b = new MediaBrowserImplApi23(context, componentName, connectionCallback, bundle);
        } else if (VERSION.SDK_INT >= 21) {
            this.b = new MediaBrowserImplApi21(context, componentName, connectionCallback, bundle);
        } else {
            this.b = new MediaBrowserImplBase(context, componentName, connectionCallback, bundle);
        }
    }

    public void connect() {
        this.b.d();
    }

    public void disconnect() {
        this.b.e();
    }

    public boolean isConnected() {
        return this.b.f();
    }

    @NonNull
    public ComponentName getServiceComponent() {
        return this.b.g();
    }

    @NonNull
    public String getRoot() {
        return this.b.h();
    }

    @Nullable
    public Bundle getExtras() {
        return this.b.i();
    }

    @NonNull
    public Token getSessionToken() {
        return this.b.j();
    }

    public void subscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        } else {
            this.b.a(str, (Bundle) null, subscriptionCallback);
        }
    }

    public void subscribe(@NonNull String str, @NonNull Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        } else if (bundle == null) {
            throw new IllegalArgumentException("options are null");
        } else {
            this.b.a(str, bundle, subscriptionCallback);
        }
    }

    public void unsubscribe(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        }
        this.b.a(str, (SubscriptionCallback) null);
    }

    public void unsubscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        } else {
            this.b.a(str, subscriptionCallback);
        }
    }

    public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
        this.b.a(str, itemCallback);
    }

    public void search(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("query cannot be empty");
        } else if (searchCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        } else {
            this.b.a(str, bundle, searchCallback);
        }
    }

    public void sendCustomAction(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("action cannot be empty");
        }
        this.b.a(str, bundle, customActionCallback);
    }
}
