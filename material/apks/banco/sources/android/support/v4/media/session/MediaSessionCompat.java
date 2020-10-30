package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.media.RemoteControlClient.OnMetadataUpdateListener;
import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.MediaMetadataCompat.Builder;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.asn1.eac.EACTags;

public class MediaSessionCompat {
    public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
    public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
    public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
    public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
    public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
    public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
    static int a;
    private final MediaSessionImpl b;
    private final MediaControllerCompat c;
    private final ArrayList<OnActiveChangeListener> d;

    public static abstract class Callback {
        /* access modifiers changed from: private */
        public WeakReference<MediaSessionImpl> a;
        final Object b;
        private CallbackHandler c = null;
        private boolean d;

        class CallbackHandler extends Handler {
            CallbackHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message message) {
                if (message.what == 1) {
                    Callback.this.a();
                }
            }
        }

        @RequiresApi(21)
        class StubApi21 implements Callback {
            StubApi21() {
            }

            /* JADX WARNING: type inference failed for: r1v0 */
            /* JADX WARNING: type inference failed for: r1v1, types: [android.support.v4.media.session.MediaSessionCompat$QueueItem] */
            /* JADX WARNING: type inference failed for: r1v3, types: [android.support.v4.media.session.MediaSessionCompat$QueueItem] */
            /* JADX WARNING: type inference failed for: r1v4, types: [android.os.IBinder] */
            /* JADX WARNING: type inference failed for: r1v5, types: [android.os.IBinder] */
            /* JADX WARNING: type inference failed for: r1v6 */
            /* JADX WARNING: type inference failed for: r1v7 */
            /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
              assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.IBinder, android.support.v4.media.session.MediaSessionCompat$QueueItem]
              uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], android.support.v4.media.session.MediaSessionCompat$QueueItem, android.os.IBinder]
              mth insns count: 100
            	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
            	at java.util.ArrayList.forEach(ArrayList.java:1249)
            	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.util.ArrayList.forEach(ArrayList.java:1249)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.util.ArrayList.forEach(ArrayList.java:1249)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.util.ArrayList.forEach(ArrayList.java:1249)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:30)
            	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
            	at java.util.ArrayList.forEach(ArrayList.java:1249)
            	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
            	at jadx.core.ProcessClass.process(ProcessClass.java:35)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
             */
            /* JADX WARNING: Unknown variable types count: 3 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void a(java.lang.String r3, android.os.Bundle r4, android.os.ResultReceiver r5) {
                /*
                    r2 = this;
                    java.lang.String r0 = "android.support.v4.media.session.command.GET_EXTRA_BINDER"
                    boolean r0 = r3.equals(r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    r1 = 0
                    if (r0 == 0) goto L_0x0036
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.ref.WeakReference r3 = r3.a     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.Object r3 = r3.get()     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21 r3 = (android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi21) r3     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r3 == 0) goto L_0x00ee
                    android.os.Bundle r4 = new android.os.Bundle     // Catch:{ BadParcelableException -> 0x00e7 }
                    r4.<init>()     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.MediaSessionCompat$Token r3 = r3.c()     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.IMediaSession r3 = r3.getExtraBinder()     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.String r0 = "android.support.v4.media.session.EXTRA_BINDER"
                    if (r3 != 0) goto L_0x0029
                    goto L_0x002d
                L_0x0029:
                    android.os.IBinder r1 = r3.asBinder()     // Catch:{ BadParcelableException -> 0x00e7 }
                L_0x002d:
                    android.support.v4.app.BundleCompat.putBinder(r4, r0, r1)     // Catch:{ BadParcelableException -> 0x00e7 }
                    r3 = 0
                    r5.send(r3, r4)     // Catch:{ BadParcelableException -> 0x00e7 }
                    goto L_0x00ee
                L_0x0036:
                    java.lang.String r0 = "android.support.v4.media.session.command.ADD_QUEUE_ITEM"
                    boolean r0 = r3.equals(r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r0 == 0) goto L_0x0056
                    java.lang.Class<android.support.v4.media.MediaDescriptionCompat> r3 = android.support.v4.media.MediaDescriptionCompat.class
                    java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ BadParcelableException -> 0x00e7 }
                    r4.setClassLoader(r3)     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.String r5 = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"
                    android.os.Parcelable r4 = r4.getParcelable(r5)     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.MediaDescriptionCompat r4 = (android.support.v4.media.MediaDescriptionCompat) r4     // Catch:{ BadParcelableException -> 0x00e7 }
                    r3.onAddQueueItem(r4)     // Catch:{ BadParcelableException -> 0x00e7 }
                    goto L_0x00ee
                L_0x0056:
                    java.lang.String r0 = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT"
                    boolean r0 = r3.equals(r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r0 == 0) goto L_0x007c
                    java.lang.Class<android.support.v4.media.MediaDescriptionCompat> r3 = android.support.v4.media.MediaDescriptionCompat.class
                    java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ BadParcelableException -> 0x00e7 }
                    r4.setClassLoader(r3)     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.String r5 = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"
                    android.os.Parcelable r5 = r4.getParcelable(r5)     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.MediaDescriptionCompat r5 = (android.support.v4.media.MediaDescriptionCompat) r5     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.String r0 = "android.support.v4.media.session.command.ARGUMENT_INDEX"
                    int r4 = r4.getInt(r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    r3.onAddQueueItem(r5, r4)     // Catch:{ BadParcelableException -> 0x00e7 }
                    goto L_0x00ee
                L_0x007c:
                    java.lang.String r0 = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM"
                    boolean r0 = r3.equals(r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r0 == 0) goto L_0x009b
                    java.lang.Class<android.support.v4.media.MediaDescriptionCompat> r3 = android.support.v4.media.MediaDescriptionCompat.class
                    java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ BadParcelableException -> 0x00e7 }
                    r4.setClassLoader(r3)     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.String r5 = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"
                    android.os.Parcelable r4 = r4.getParcelable(r5)     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.MediaDescriptionCompat r4 = (android.support.v4.media.MediaDescriptionCompat) r4     // Catch:{ BadParcelableException -> 0x00e7 }
                    r3.onRemoveQueueItem(r4)     // Catch:{ BadParcelableException -> 0x00e7 }
                    goto L_0x00ee
                L_0x009b:
                    java.lang.String r0 = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT"
                    boolean r0 = r3.equals(r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r0 == 0) goto L_0x00e1
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.ref.WeakReference r3 = r3.a     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.Object r3 = r3.get()     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21 r3 = (android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi21) r3     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r3 == 0) goto L_0x00ee
                    java.util.List r5 = r3.j     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r5 == 0) goto L_0x00ee
                    java.lang.String r5 = "android.support.v4.media.session.command.ARGUMENT_INDEX"
                    r0 = -1
                    int r4 = r4.getInt(r5, r0)     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r4 < 0) goto L_0x00d5
                    java.util.List r5 = r3.j     // Catch:{ BadParcelableException -> 0x00e7 }
                    int r5 = r5.size()     // Catch:{ BadParcelableException -> 0x00e7 }
                    if (r4 >= r5) goto L_0x00d5
                    java.util.List r3 = r3.j     // Catch:{ BadParcelableException -> 0x00e7 }
                    java.lang.Object r3 = r3.get(r4)     // Catch:{ BadParcelableException -> 0x00e7 }
                    r1 = r3
                    android.support.v4.media.session.MediaSessionCompat$QueueItem r1 = (android.support.v4.media.session.MediaSessionCompat.QueueItem) r1     // Catch:{ BadParcelableException -> 0x00e7 }
                L_0x00d5:
                    if (r1 == 0) goto L_0x00ee
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    android.support.v4.media.MediaDescriptionCompat r4 = r1.getDescription()     // Catch:{ BadParcelableException -> 0x00e7 }
                    r3.onRemoveQueueItem(r4)     // Catch:{ BadParcelableException -> 0x00e7 }
                    goto L_0x00ee
                L_0x00e1:
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00e7 }
                    r0.onCommand(r3, r4, r5)     // Catch:{ BadParcelableException -> 0x00e7 }
                    goto L_0x00ee
                L_0x00e7:
                    java.lang.String r3 = "MediaSessionCompat"
                    java.lang.String r4 = "Could not unparcel the extra data."
                    android.util.Log.e(r3, r4)
                L_0x00ee:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.MediaSessionCompat.Callback.StubApi21.a(java.lang.String, android.os.Bundle, android.os.ResultReceiver):void");
            }

            public boolean a(Intent intent) {
                return Callback.this.onMediaButtonEvent(intent);
            }

            public void a() {
                Callback.this.onPlay();
            }

            public void a(String str, Bundle bundle) {
                Callback.this.onPlayFromMediaId(str, bundle);
            }

            public void b(String str, Bundle bundle) {
                Callback.this.onPlayFromSearch(str, bundle);
            }

            public void a(long j) {
                Callback.this.onSkipToQueueItem(j);
            }

            public void b() {
                Callback.this.onPause();
            }

            public void c() {
                Callback.this.onSkipToNext();
            }

            public void d() {
                Callback.this.onSkipToPrevious();
            }

            public void e() {
                Callback.this.onFastForward();
            }

            public void f() {
                Callback.this.onRewind();
            }

            public void g() {
                Callback.this.onStop();
            }

            public void b(long j) {
                Callback.this.onSeekTo(j);
            }

            public void a(Object obj) {
                Callback.this.onSetRating(RatingCompat.fromRating(obj));
            }

            public void c(String str, Bundle bundle) {
                if (str.equals("android.support.v4.media.session.action.PLAY_FROM_URI")) {
                    Callback.this.onPlayFromUri((Uri) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI"), (Bundle) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                } else if (str.equals("android.support.v4.media.session.action.PREPARE")) {
                    Callback.this.onPrepare();
                } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID")) {
                    Callback.this.onPrepareFromMediaId(bundle.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH")) {
                    Callback.this.onPrepareFromSearch(bundle.getString("android.support.v4.media.session.action.ARGUMENT_QUERY"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_URI")) {
                    Callback.this.onPrepareFromUri((Uri) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                } else if (str.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED")) {
                    Callback.this.onSetCaptioningEnabled(bundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED"));
                } else if (str.equals("android.support.v4.media.session.action.SET_REPEAT_MODE")) {
                    Callback.this.onSetRepeatMode(bundle.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE"));
                } else if (str.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE")) {
                    Callback.this.onSetShuffleMode(bundle.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE"));
                } else if (str.equals("android.support.v4.media.session.action.SET_RATING")) {
                    bundle.setClassLoader(RatingCompat.class.getClassLoader());
                    Callback.this.onSetRating((RatingCompat) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_RATING"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                } else {
                    Callback.this.onCustomAction(str, bundle);
                }
            }
        }

        @RequiresApi(23)
        class StubApi23 extends StubApi21 implements android.support.v4.media.session.MediaSessionCompatApi23.Callback {
            StubApi23() {
                super();
            }

            public void onPlayFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPlayFromUri(uri, bundle);
            }
        }

        @RequiresApi(24)
        class StubApi24 extends StubApi23 implements android.support.v4.media.session.MediaSessionCompatApi24.Callback {
            StubApi24() {
                super();
            }

            public void onPrepare() {
                Callback.this.onPrepare();
            }

            public void onPrepareFromMediaId(String str, Bundle bundle) {
                Callback.this.onPrepareFromMediaId(str, bundle);
            }

            public void onPrepareFromSearch(String str, Bundle bundle) {
                Callback.this.onPrepareFromSearch(str, bundle);
            }

            public void onPrepareFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPrepareFromUri(uri, bundle);
            }
        }

        public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String str, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRemoveQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        @Deprecated
        public void onRemoveQueueItemAt(int i) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetCaptioningEnabled(boolean z) {
        }

        public void onSetRating(RatingCompat ratingCompat) {
        }

        public void onSetRating(RatingCompat ratingCompat, Bundle bundle) {
        }

        public void onSetRepeatMode(int i) {
        }

        public void onSetShuffleMode(int i) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onStop() {
        }

        public Callback() {
            if (VERSION.SDK_INT >= 24) {
                this.b = MediaSessionCompatApi24.a((android.support.v4.media.session.MediaSessionCompatApi24.Callback) new StubApi24());
            } else if (VERSION.SDK_INT >= 23) {
                this.b = MediaSessionCompatApi23.a(new StubApi23());
            } else if (VERSION.SDK_INT >= 21) {
                this.b = MediaSessionCompatApi21.a((Callback) new StubApi21());
            } else {
                this.b = null;
            }
        }

        /* access modifiers changed from: private */
        public void a(MediaSessionImpl mediaSessionImpl, Handler handler) {
            this.a = new WeakReference<>(mediaSessionImpl);
            if (this.c != null) {
                this.c.removeCallbacksAndMessages(null);
            }
            this.c = new CallbackHandler(handler.getLooper());
        }

        public boolean onMediaButtonEvent(Intent intent) {
            long j;
            MediaSessionImpl mediaSessionImpl = (MediaSessionImpl) this.a.get();
            if (mediaSessionImpl == null || this.c == null) {
                return false;
            }
            KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
            if (keyEvent == null || keyEvent.getAction() != 0) {
                return false;
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 79 || keyCode == 85) {
                if (keyEvent.getRepeatCount() > 0) {
                    a();
                } else if (this.d) {
                    this.c.removeMessages(1);
                    this.d = false;
                    PlaybackStateCompat d2 = mediaSessionImpl.d();
                    if (d2 == null) {
                        j = 0;
                    } else {
                        j = d2.getActions();
                    }
                    if ((j & 32) != 0) {
                        onSkipToNext();
                    }
                } else {
                    this.d = true;
                    this.c.sendEmptyMessageDelayed(1, (long) ViewConfiguration.getDoubleTapTimeout());
                }
                return true;
            }
            a();
            return false;
        }

        /* access modifiers changed from: private */
        public void a() {
            long j;
            if (this.d) {
                boolean z = false;
                this.d = false;
                this.c.removeMessages(1);
                MediaSessionImpl mediaSessionImpl = (MediaSessionImpl) this.a.get();
                if (mediaSessionImpl != null) {
                    PlaybackStateCompat d2 = mediaSessionImpl.d();
                    if (d2 == null) {
                        j = 0;
                    } else {
                        j = d2.getActions();
                    }
                    boolean z2 = d2 != null && d2.getState() == 3;
                    boolean z3 = (j & 516) != 0;
                    if ((j & 514) != 0) {
                        z = true;
                    }
                    if (z2 && z) {
                        onPause();
                    } else if (!z2 && z3) {
                        onPlay();
                    }
                }
            }
        }
    }

    interface MediaSessionImpl {
        void a(int i);

        void a(PendingIntent pendingIntent);

        void a(Bundle bundle);

        void a(MediaMetadataCompat mediaMetadataCompat);

        void a(VolumeProviderCompat volumeProviderCompat);

        void a(Callback callback, Handler handler);

        void a(PlaybackStateCompat playbackStateCompat);

        void a(CharSequence charSequence);

        void a(String str, Bundle bundle);

        void a(List<QueueItem> list);

        void a(boolean z);

        boolean a();

        void b();

        void b(int i);

        void b(PendingIntent pendingIntent);

        void b(boolean z);

        Token c();

        void c(int i);

        PlaybackStateCompat d();

        void d(int i);

        Object e();

        void e(int i);

        Object f();

        String g();
    }

    @RequiresApi(18)
    static class MediaSessionImplApi18 extends MediaSessionImplBase {
        private static boolean x = true;

        MediaSessionImplApi18(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            super(context, str, componentName, pendingIntent);
        }

        public void a(Callback callback, Handler handler) {
            super.a(callback, handler);
            if (callback == null) {
                this.d.setPlaybackPositionUpdateListener(null);
                return;
            }
            this.d.setPlaybackPositionUpdateListener(new OnPlaybackPositionUpdateListener() {
                public void onPlaybackPositionUpdate(long j) {
                    MediaSessionImplApi18.this.a(18, (Object) Long.valueOf(j));
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public void b(PlaybackStateCompat playbackStateCompat) {
            long position = playbackStateCompat.getPosition();
            float playbackSpeed = playbackStateCompat.getPlaybackSpeed();
            long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (playbackStateCompat.getState() == 3) {
                long j = 0;
                if (position > 0) {
                    if (lastPositionUpdateTime > 0) {
                        j = elapsedRealtime - lastPositionUpdateTime;
                        if (playbackSpeed > BitmapDescriptorFactory.HUE_RED && playbackSpeed != 1.0f) {
                            j = (long) (((float) j) * playbackSpeed);
                        }
                    }
                    position += j;
                }
            }
            this.d.setPlaybackState(g(playbackStateCompat.getState()), position, playbackSpeed);
        }

        /* access modifiers changed from: 0000 */
        public int a(long j) {
            int a = super.a(j);
            return (j & 256) != 0 ? a | 256 : a;
        }

        /* access modifiers changed from: 0000 */
        public void a(PendingIntent pendingIntent, ComponentName componentName) {
            if (x) {
                try {
                    this.c.registerMediaButtonEventReceiver(pendingIntent);
                } catch (NullPointerException unused) {
                    Log.w("MediaSessionCompat", "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                    x = false;
                }
            }
            if (!x) {
                super.a(pendingIntent, componentName);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(PendingIntent pendingIntent, ComponentName componentName) {
            if (x) {
                this.c.unregisterMediaButtonEventReceiver(pendingIntent);
            } else {
                super.b(pendingIntent, componentName);
            }
        }
    }

    @RequiresApi(19)
    static class MediaSessionImplApi19 extends MediaSessionImplApi18 {
        MediaSessionImplApi19(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            super(context, str, componentName, pendingIntent);
        }

        public void a(Callback callback, Handler handler) {
            super.a(callback, handler);
            if (callback == null) {
                this.d.setMetadataUpdateListener(null);
                return;
            }
            this.d.setMetadataUpdateListener(new OnMetadataUpdateListener() {
                public void onMetadataUpdate(int i, Object obj) {
                    if (i == 268435457 && (obj instanceof Rating)) {
                        MediaSessionImplApi19.this.a(19, (Object) RatingCompat.fromRating(obj));
                    }
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public int a(long j) {
            int a = super.a(j);
            return (j & 128) != 0 ? a | 512 : a;
        }

        /* access modifiers changed from: 0000 */
        public MetadataEditor b(Bundle bundle) {
            MetadataEditor b = super.b(bundle);
            if (((this.l == null ? 0 : this.l.getActions()) & 128) != 0) {
                b.addEditableKey(268435457);
            }
            if (bundle == null) {
                return b;
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_YEAR)) {
                b.putLong(8, bundle.getLong(MediaMetadataCompat.METADATA_KEY_YEAR));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_RATING)) {
                b.putObject(101, bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_RATING));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_USER_RATING)) {
                b.putObject(268435457, bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_USER_RATING));
            }
            return b;
        }
    }

    @RequiresApi(21)
    static class MediaSessionImplApi21 implements MediaSessionImpl {
        int a;
        boolean b;
        int c;
        int d;
        private final Object e;
        private final Token f;
        /* access modifiers changed from: private */
        public boolean g = false;
        /* access modifiers changed from: private */
        public final RemoteCallbackList<IMediaControllerCallback> h = new RemoteCallbackList<>();
        /* access modifiers changed from: private */
        public PlaybackStateCompat i;
        /* access modifiers changed from: private */
        public List<QueueItem> j;
        /* access modifiers changed from: private */
        public MediaMetadataCompat k;

        class ExtraSession extends Stub {
            public List<QueueItem> getQueue() {
                return null;
            }

            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            public void setShuffleModeEnabledRemoved(boolean z) {
            }

            ExtraSession() {
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                throw new AssertionError();
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                throw new AssertionError();
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (!MediaSessionImplApi21.this.g) {
                    MediaSessionImplApi21.this.h.register(iMediaControllerCallback);
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplApi21.this.h.unregister(iMediaControllerCallback);
            }

            public String getPackageName() {
                throw new AssertionError();
            }

            public String getTag() {
                throw new AssertionError();
            }

            public PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            public long getFlags() {
                throw new AssertionError();
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            public void adjustVolume(int i, int i2, String str) {
                throw new AssertionError();
            }

            public void setVolumeTo(int i, int i2, String str) {
                throw new AssertionError();
            }

            public void prepare() {
                throw new AssertionError();
            }

            public void prepareFromMediaId(String str, Bundle bundle) {
                throw new AssertionError();
            }

            public void prepareFromSearch(String str, Bundle bundle) {
                throw new AssertionError();
            }

            public void prepareFromUri(Uri uri, Bundle bundle) {
                throw new AssertionError();
            }

            public void play() {
                throw new AssertionError();
            }

            public void playFromMediaId(String str, Bundle bundle) {
                throw new AssertionError();
            }

            public void playFromSearch(String str, Bundle bundle) {
                throw new AssertionError();
            }

            public void playFromUri(Uri uri, Bundle bundle) {
                throw new AssertionError();
            }

            public void skipToQueueItem(long j) {
                throw new AssertionError();
            }

            public void pause() {
                throw new AssertionError();
            }

            public void stop() {
                throw new AssertionError();
            }

            public void next() {
                throw new AssertionError();
            }

            public void previous() {
                throw new AssertionError();
            }

            public void fastForward() {
                throw new AssertionError();
            }

            public void rewind() {
                throw new AssertionError();
            }

            public void seekTo(long j) {
                throw new AssertionError();
            }

            public void rate(RatingCompat ratingCompat) {
                throw new AssertionError();
            }

            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                throw new AssertionError();
            }

            public void setCaptioningEnabled(boolean z) {
                throw new AssertionError();
            }

            public void setRepeatMode(int i) {
                throw new AssertionError();
            }

            public void setShuffleMode(int i) {
                throw new AssertionError();
            }

            public void sendCustomAction(String str, Bundle bundle) {
                throw new AssertionError();
            }

            public MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionCompat.b(MediaSessionImplApi21.this.i, MediaSessionImplApi21.this.k);
            }

            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                throw new AssertionError();
            }

            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            public void removeQueueItemAt(int i) {
                throw new AssertionError();
            }

            public CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            public Bundle getExtras() {
                throw new AssertionError();
            }

            public int getRatingType() {
                return MediaSessionImplApi21.this.a;
            }

            public boolean isCaptioningEnabled() {
                return MediaSessionImplApi21.this.b;
            }

            public int getRepeatMode() {
                return MediaSessionImplApi21.this.c;
            }

            public int getShuffleMode() {
                return MediaSessionImplApi21.this.d;
            }

            public boolean isTransportControlEnabled() {
                throw new AssertionError();
            }
        }

        public Object f() {
            return null;
        }

        public MediaSessionImplApi21(Context context, String str) {
            this.e = MediaSessionCompatApi21.a(context, str);
            this.f = new Token(MediaSessionCompatApi21.e(this.e), new ExtraSession());
        }

        public MediaSessionImplApi21(Object obj) {
            this.e = MediaSessionCompatApi21.a(obj);
            this.f = new Token(MediaSessionCompatApi21.e(this.e), new ExtraSession());
        }

        public void a(Callback callback, Handler handler) {
            MediaSessionCompatApi21.a(this.e, callback == null ? null : callback.b, handler);
            if (callback != null) {
                callback.a(this, handler);
            }
        }

        public void a(int i2) {
            MediaSessionCompatApi21.a(this.e, i2);
        }

        public void b(int i2) {
            MediaSessionCompatApi21.b(this.e, i2);
        }

        public void a(VolumeProviderCompat volumeProviderCompat) {
            MediaSessionCompatApi21.a(this.e, volumeProviderCompat.getVolumeProvider());
        }

        public void a(boolean z) {
            MediaSessionCompatApi21.a(this.e, z);
        }

        public boolean a() {
            return MediaSessionCompatApi21.c(this.e);
        }

        public void a(String str, Bundle bundle) {
            if (VERSION.SDK_INT < 23) {
                for (int beginBroadcast = this.h.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.h.getBroadcastItem(beginBroadcast)).onEvent(str, bundle);
                    } catch (RemoteException unused) {
                    }
                }
                this.h.finishBroadcast();
            }
            MediaSessionCompatApi21.a(this.e, str, bundle);
        }

        public void b() {
            this.g = true;
            MediaSessionCompatApi21.d(this.e);
        }

        public Token c() {
            return this.f;
        }

        public void a(PlaybackStateCompat playbackStateCompat) {
            Object obj;
            this.i = playbackStateCompat;
            for (int beginBroadcast = this.h.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.h.getBroadcastItem(beginBroadcast)).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.h.finishBroadcast();
            Object obj2 = this.e;
            if (playbackStateCompat == null) {
                obj = null;
            } else {
                obj = playbackStateCompat.getPlaybackState();
            }
            MediaSessionCompatApi21.b(obj2, obj);
        }

        public PlaybackStateCompat d() {
            return this.i;
        }

        public void a(MediaMetadataCompat mediaMetadataCompat) {
            Object obj;
            this.k = mediaMetadataCompat;
            Object obj2 = this.e;
            if (mediaMetadataCompat == null) {
                obj = null;
            } else {
                obj = mediaMetadataCompat.getMediaMetadata();
            }
            MediaSessionCompatApi21.c(obj2, obj);
        }

        public void a(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.a(this.e, pendingIntent);
        }

        public void b(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.b(this.e, pendingIntent);
        }

        public void a(List<QueueItem> list) {
            ArrayList arrayList;
            this.j = list;
            if (list != null) {
                arrayList = new ArrayList();
                for (QueueItem queueItem : list) {
                    arrayList.add(queueItem.getQueueItem());
                }
            } else {
                arrayList = null;
            }
            MediaSessionCompatApi21.a(this.e, (List<Object>) arrayList);
        }

        public void a(CharSequence charSequence) {
            MediaSessionCompatApi21.a(this.e, charSequence);
        }

        public void c(int i2) {
            if (VERSION.SDK_INT < 22) {
                this.a = i2;
            } else {
                MediaSessionCompatApi22.a(this.e, i2);
            }
        }

        public void b(boolean z) {
            if (this.b != z) {
                this.b = z;
                for (int beginBroadcast = this.h.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.h.getBroadcastItem(beginBroadcast)).onCaptioningEnabledChanged(z);
                    } catch (RemoteException unused) {
                    }
                }
                this.h.finishBroadcast();
            }
        }

        public void d(int i2) {
            if (this.c != i2) {
                this.c = i2;
                for (int beginBroadcast = this.h.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.h.getBroadcastItem(beginBroadcast)).onRepeatModeChanged(i2);
                    } catch (RemoteException unused) {
                    }
                }
                this.h.finishBroadcast();
            }
        }

        public void e(int i2) {
            if (this.d != i2) {
                this.d = i2;
                for (int beginBroadcast = this.h.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.h.getBroadcastItem(beginBroadcast)).onShuffleModeChanged(i2);
                    } catch (RemoteException unused) {
                    }
                }
                this.h.finishBroadcast();
            }
        }

        public void a(Bundle bundle) {
            MediaSessionCompatApi21.a(this.e, bundle);
        }

        public Object e() {
            return this.e;
        }

        public String g() {
            if (VERSION.SDK_INT < 24) {
                return null;
            }
            return MediaSessionCompatApi24.a(this.e);
        }
    }

    static class MediaSessionImplBase implements MediaSessionImpl {
        private final MediaSessionStub A;
        private final Token B;
        private MessageHandler C;
        private boolean D = false;
        private boolean E = false;
        private android.support.v4.media.VolumeProviderCompat.Callback F = new android.support.v4.media.VolumeProviderCompat.Callback() {
            public void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                if (MediaSessionImplBase.this.w == volumeProviderCompat) {
                    ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(MediaSessionImplBase.this.u, MediaSessionImplBase.this.v, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume());
                    MediaSessionImplBase.this.a(parcelableVolumeInfo);
                }
            }
        };
        final String a;
        final String b;
        final AudioManager c;
        final RemoteControlClient d;
        final Object e = new Object();
        final RemoteCallbackList<IMediaControllerCallback> f = new RemoteCallbackList<>();
        boolean g = false;
        boolean h = false;
        volatile Callback i;
        int j;
        MediaMetadataCompat k;
        PlaybackStateCompat l;
        PendingIntent m;
        List<QueueItem> n;
        CharSequence o;
        int p;
        boolean q;
        int r;
        int s;
        Bundle t;
        int u;
        int v;
        VolumeProviderCompat w;
        private final Context x;
        private final ComponentName y;
        private final PendingIntent z;

        static final class Command {
            public final String a;
            public final Bundle b;
            public final ResultReceiver c;

            public Command(String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.a = str;
                this.b = bundle;
                this.c = resultReceiver;
            }
        }

        class MediaSessionStub extends Stub {
            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            public void setShuffleModeEnabledRemoved(boolean z) {
            }

            MediaSessionStub() {
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                MediaSessionImplBase.this.a(1, (Object) new Command(str, bundle, resultReceiverWrapper.a));
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                boolean z = true;
                if ((MediaSessionImplBase.this.j & 1) == 0) {
                    z = false;
                }
                if (z) {
                    MediaSessionImplBase.this.a(21, (Object) keyEvent);
                }
                return z;
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (MediaSessionImplBase.this.g) {
                    try {
                        iMediaControllerCallback.onSessionDestroyed();
                    } catch (Exception unused) {
                    }
                } else {
                    MediaSessionImplBase.this.f.register(iMediaControllerCallback);
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplBase.this.f.unregister(iMediaControllerCallback);
            }

            public String getPackageName() {
                return MediaSessionImplBase.this.a;
            }

            public String getTag() {
                return MediaSessionImplBase.this.b;
            }

            public PendingIntent getLaunchPendingIntent() {
                PendingIntent pendingIntent;
                synchronized (MediaSessionImplBase.this.e) {
                    pendingIntent = MediaSessionImplBase.this.m;
                }
                return pendingIntent;
            }

            public long getFlags() {
                long j;
                synchronized (MediaSessionImplBase.this.e) {
                    j = (long) MediaSessionImplBase.this.j;
                }
                return j;
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                int i;
                int i2;
                int i3;
                int i4;
                int i5;
                synchronized (MediaSessionImplBase.this.e) {
                    i = MediaSessionImplBase.this.u;
                    i2 = MediaSessionImplBase.this.v;
                    VolumeProviderCompat volumeProviderCompat = MediaSessionImplBase.this.w;
                    if (i == 2) {
                        int volumeControl = volumeProviderCompat.getVolumeControl();
                        int maxVolume = volumeProviderCompat.getMaxVolume();
                        i3 = volumeProviderCompat.getCurrentVolume();
                        i4 = maxVolume;
                        i5 = volumeControl;
                    } else {
                        i4 = MediaSessionImplBase.this.c.getStreamMaxVolume(i2);
                        i3 = MediaSessionImplBase.this.c.getStreamVolume(i2);
                        i5 = 2;
                    }
                }
                ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(i, i2, i5, i4, i3);
                return parcelableVolumeInfo;
            }

            public void adjustVolume(int i, int i2, String str) {
                MediaSessionImplBase.this.b(i, i2);
            }

            public void setVolumeTo(int i, int i2, String str) {
                MediaSessionImplBase.this.c(i, i2);
            }

            public void prepare() {
                MediaSessionImplBase.this.f(3);
            }

            public void prepareFromMediaId(String str, Bundle bundle) {
                MediaSessionImplBase.this.a(4, (Object) str, bundle);
            }

            public void prepareFromSearch(String str, Bundle bundle) {
                MediaSessionImplBase.this.a(5, (Object) str, bundle);
            }

            public void prepareFromUri(Uri uri, Bundle bundle) {
                MediaSessionImplBase.this.a(6, (Object) uri, bundle);
            }

            public void play() {
                MediaSessionImplBase.this.f(7);
            }

            public void playFromMediaId(String str, Bundle bundle) {
                MediaSessionImplBase.this.a(8, (Object) str, bundle);
            }

            public void playFromSearch(String str, Bundle bundle) {
                MediaSessionImplBase.this.a(9, (Object) str, bundle);
            }

            public void playFromUri(Uri uri, Bundle bundle) {
                MediaSessionImplBase.this.a(10, (Object) uri, bundle);
            }

            public void skipToQueueItem(long j) {
                MediaSessionImplBase.this.a(11, (Object) Long.valueOf(j));
            }

            public void pause() {
                MediaSessionImplBase.this.f(12);
            }

            public void stop() {
                MediaSessionImplBase.this.f(13);
            }

            public void next() {
                MediaSessionImplBase.this.f(14);
            }

            public void previous() {
                MediaSessionImplBase.this.f(15);
            }

            public void fastForward() {
                MediaSessionImplBase.this.f(16);
            }

            public void rewind() {
                MediaSessionImplBase.this.f(17);
            }

            public void seekTo(long j) {
                MediaSessionImplBase.this.a(18, (Object) Long.valueOf(j));
            }

            public void rate(RatingCompat ratingCompat) {
                MediaSessionImplBase.this.a(19, (Object) ratingCompat);
            }

            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                MediaSessionImplBase.this.a(31, (Object) ratingCompat, bundle);
            }

            public void setCaptioningEnabled(boolean z) {
                MediaSessionImplBase.this.a(29, (Object) Boolean.valueOf(z));
            }

            public void setRepeatMode(int i) {
                MediaSessionImplBase.this.a(23, i);
            }

            public void setShuffleMode(int i) {
                MediaSessionImplBase.this.a(30, i);
            }

            public void sendCustomAction(String str, Bundle bundle) {
                MediaSessionImplBase.this.a(20, (Object) str, bundle);
            }

            public MediaMetadataCompat getMetadata() {
                return MediaSessionImplBase.this.k;
            }

            public PlaybackStateCompat getPlaybackState() {
                PlaybackStateCompat playbackStateCompat;
                MediaMetadataCompat mediaMetadataCompat;
                synchronized (MediaSessionImplBase.this.e) {
                    playbackStateCompat = MediaSessionImplBase.this.l;
                    mediaMetadataCompat = MediaSessionImplBase.this.k;
                }
                return MediaSessionCompat.b(playbackStateCompat, mediaMetadataCompat);
            }

            public List<QueueItem> getQueue() {
                List<QueueItem> list;
                synchronized (MediaSessionImplBase.this.e) {
                    list = MediaSessionImplBase.this.n;
                }
                return list;
            }

            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                MediaSessionImplBase.this.a(25, (Object) mediaDescriptionCompat);
            }

            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                MediaSessionImplBase.this.a(26, (Object) mediaDescriptionCompat, i);
            }

            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                MediaSessionImplBase.this.a(27, (Object) mediaDescriptionCompat);
            }

            public void removeQueueItemAt(int i) {
                MediaSessionImplBase.this.a(28, i);
            }

            public CharSequence getQueueTitle() {
                return MediaSessionImplBase.this.o;
            }

            public Bundle getExtras() {
                Bundle bundle;
                synchronized (MediaSessionImplBase.this.e) {
                    bundle = MediaSessionImplBase.this.t;
                }
                return bundle;
            }

            public int getRatingType() {
                return MediaSessionImplBase.this.p;
            }

            public boolean isCaptioningEnabled() {
                return MediaSessionImplBase.this.q;
            }

            public int getRepeatMode() {
                return MediaSessionImplBase.this.r;
            }

            public int getShuffleMode() {
                return MediaSessionImplBase.this.s;
            }

            public boolean isTransportControlEnabled() {
                return (MediaSessionImplBase.this.j & 2) != 0;
            }
        }

        class MessageHandler extends Handler {
            public MessageHandler(Looper looper) {
                super(looper);
            }

            public void a(int i, Object obj, Bundle bundle) {
                Message obtainMessage = obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }

            public void a(int i, Object obj, int i2) {
                obtainMessage(i, i2, 0, obj).sendToTarget();
            }

            public void handleMessage(Message message) {
                Callback callback = MediaSessionImplBase.this.i;
                if (callback != null) {
                    switch (message.what) {
                        case 1:
                            Command command = (Command) message.obj;
                            callback.onCommand(command.a, command.b, command.c);
                            break;
                        case 2:
                            MediaSessionImplBase.this.b(message.arg1, 0);
                            break;
                        case 3:
                            callback.onPrepare();
                            break;
                        case 4:
                            callback.onPrepareFromMediaId((String) message.obj, message.getData());
                            break;
                        case 5:
                            callback.onPrepareFromSearch((String) message.obj, message.getData());
                            break;
                        case 6:
                            callback.onPrepareFromUri((Uri) message.obj, message.getData());
                            break;
                        case 7:
                            callback.onPlay();
                            break;
                        case 8:
                            callback.onPlayFromMediaId((String) message.obj, message.getData());
                            break;
                        case 9:
                            callback.onPlayFromSearch((String) message.obj, message.getData());
                            break;
                        case 10:
                            callback.onPlayFromUri((Uri) message.obj, message.getData());
                            break;
                        case 11:
                            callback.onSkipToQueueItem(((Long) message.obj).longValue());
                            break;
                        case 12:
                            callback.onPause();
                            break;
                        case 13:
                            callback.onStop();
                            break;
                        case 14:
                            callback.onSkipToNext();
                            break;
                        case 15:
                            callback.onSkipToPrevious();
                            break;
                        case 16:
                            callback.onFastForward();
                            break;
                        case 17:
                            callback.onRewind();
                            break;
                        case 18:
                            callback.onSeekTo(((Long) message.obj).longValue());
                            break;
                        case 19:
                            callback.onSetRating((RatingCompat) message.obj);
                            break;
                        case 20:
                            callback.onCustomAction((String) message.obj, message.getData());
                            break;
                        case 21:
                            KeyEvent keyEvent = (KeyEvent) message.obj;
                            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                            intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                            if (!callback.onMediaButtonEvent(intent)) {
                                a(keyEvent, callback);
                                break;
                            }
                            break;
                        case 22:
                            MediaSessionImplBase.this.c(message.arg1, 0);
                            break;
                        case 23:
                            callback.onSetRepeatMode(message.arg1);
                            break;
                        case 25:
                            callback.onAddQueueItem((MediaDescriptionCompat) message.obj);
                            break;
                        case 26:
                            callback.onAddQueueItem((MediaDescriptionCompat) message.obj, message.arg1);
                            break;
                        case 27:
                            callback.onRemoveQueueItem((MediaDescriptionCompat) message.obj);
                            break;
                        case 28:
                            if (MediaSessionImplBase.this.n != null) {
                                QueueItem queueItem = (message.arg1 < 0 || message.arg1 >= MediaSessionImplBase.this.n.size()) ? null : (QueueItem) MediaSessionImplBase.this.n.get(message.arg1);
                                if (queueItem != null) {
                                    callback.onRemoveQueueItem(queueItem.getDescription());
                                    break;
                                }
                            }
                            break;
                        case 29:
                            callback.onSetCaptioningEnabled(((Boolean) message.obj).booleanValue());
                            break;
                        case 30:
                            callback.onSetShuffleMode(message.arg1);
                            break;
                        case 31:
                            callback.onSetRating((RatingCompat) message.obj, message.getData());
                            break;
                    }
                }
            }

            private void a(KeyEvent keyEvent, Callback callback) {
                if (keyEvent != null && keyEvent.getAction() == 0) {
                    long actions = MediaSessionImplBase.this.l == null ? 0 : MediaSessionImplBase.this.l.getActions();
                    int keyCode = keyEvent.getKeyCode();
                    if (keyCode != 79) {
                        switch (keyCode) {
                            case 85:
                                break;
                            case 86:
                                if ((actions & 1) != 0) {
                                    callback.onStop();
                                    break;
                                }
                                break;
                            case 87:
                                if ((actions & 32) != 0) {
                                    callback.onSkipToNext();
                                    break;
                                }
                                break;
                            case 88:
                                if ((actions & 16) != 0) {
                                    callback.onSkipToPrevious();
                                    break;
                                }
                                break;
                            case 89:
                                if ((actions & 8) != 0) {
                                    callback.onRewind();
                                    break;
                                }
                                break;
                            case 90:
                                if ((actions & 64) != 0) {
                                    callback.onFastForward();
                                    break;
                                }
                                break;
                            default:
                                switch (keyCode) {
                                    case EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE /*126*/:
                                        if ((actions & 4) != 0) {
                                            callback.onPlay();
                                            break;
                                        }
                                        break;
                                    case CertificateBody.profileType /*127*/:
                                        if ((actions & 2) != 0) {
                                            callback.onPause();
                                            break;
                                        }
                                        break;
                                }
                        }
                    }
                    Log.w("MediaSessionCompat", "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int a(long j2) {
            int i2 = (j2 & 1) != 0 ? 32 : 0;
            if ((j2 & 2) != 0) {
                i2 |= 16;
            }
            if ((j2 & 4) != 0) {
                i2 |= 4;
            }
            if ((j2 & 8) != 0) {
                i2 |= 2;
            }
            if ((j2 & 16) != 0) {
                i2 |= 1;
            }
            if ((j2 & 32) != 0) {
                i2 |= 128;
            }
            if ((j2 & 64) != 0) {
                i2 |= 64;
            }
            return (j2 & 512) != 0 ? i2 | 8 : i2;
        }

        public void b(PendingIntent pendingIntent) {
        }

        public Object e() {
            return null;
        }

        public Object f() {
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int g(int i2) {
            switch (i2) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                case 8:
                    return 8;
                case 7:
                    return 9;
                case 9:
                    return 7;
                case 10:
                case 11:
                    return 6;
                default:
                    return -1;
            }
        }

        public String g() {
            return null;
        }

        public MediaSessionImplBase(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            if (componentName == null) {
                throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
            }
            this.x = context;
            this.a = context.getPackageName();
            this.c = (AudioManager) context.getSystemService("audio");
            this.b = str;
            this.y = componentName;
            this.z = pendingIntent;
            this.A = new MediaSessionStub();
            this.B = new Token(this.A);
            this.p = 0;
            this.u = 1;
            this.v = 3;
            this.d = new RemoteControlClient(pendingIntent);
        }

        public void a(Callback callback, Handler handler) {
            this.i = callback;
            if (callback != null) {
                if (handler == null) {
                    handler = new Handler();
                }
                synchronized (this.e) {
                    if (this.C != null) {
                        this.C.removeCallbacksAndMessages(null);
                    }
                    this.C = new MessageHandler(handler.getLooper());
                    this.i.a(this, handler);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void f(int i2) {
            a(i2, (Object) null);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3) {
            a(i2, (Object) null, i3);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, Object obj) {
            a(i2, obj, (Bundle) null);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, Object obj, int i3) {
            synchronized (this.e) {
                if (this.C != null) {
                    this.C.a(i2, obj, i3);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, Object obj, Bundle bundle) {
            synchronized (this.e) {
                if (this.C != null) {
                    this.C.a(i2, obj, bundle);
                }
            }
        }

        public void a(int i2) {
            synchronized (this.e) {
                this.j = i2;
            }
            h();
        }

        public void b(int i2) {
            if (this.w != null) {
                this.w.setCallback(null);
            }
            this.u = 1;
            ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.u, this.v, 2, this.c.getStreamMaxVolume(this.v), this.c.getStreamVolume(this.v));
            a(parcelableVolumeInfo);
        }

        public void a(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat == null) {
                throw new IllegalArgumentException("volumeProvider may not be null");
            }
            if (this.w != null) {
                this.w.setCallback(null);
            }
            this.u = 2;
            this.w = volumeProviderCompat;
            ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.u, this.v, this.w.getVolumeControl(), this.w.getMaxVolume(), this.w.getCurrentVolume());
            a(parcelableVolumeInfo);
            volumeProviderCompat.setCallback(this.F);
        }

        public void a(boolean z2) {
            if (z2 != this.h) {
                this.h = z2;
                if (h()) {
                    a(this.k);
                    a(this.l);
                }
            }
        }

        public boolean a() {
            return this.h;
        }

        public void a(String str, Bundle bundle) {
            b(str, bundle);
        }

        public void b() {
            this.h = false;
            this.g = true;
            h();
            i();
        }

        public Token c() {
            return this.B;
        }

        public void a(PlaybackStateCompat playbackStateCompat) {
            synchronized (this.e) {
                this.l = playbackStateCompat;
            }
            c(playbackStateCompat);
            if (this.h) {
                if (playbackStateCompat == null) {
                    this.d.setPlaybackState(0);
                    this.d.setTransportControlFlags(0);
                } else {
                    b(playbackStateCompat);
                    this.d.setTransportControlFlags(a(playbackStateCompat.getActions()));
                }
            }
        }

        public PlaybackStateCompat d() {
            PlaybackStateCompat playbackStateCompat;
            synchronized (this.e) {
                playbackStateCompat = this.l;
            }
            return playbackStateCompat;
        }

        /* access modifiers changed from: 0000 */
        public void b(PlaybackStateCompat playbackStateCompat) {
            this.d.setPlaybackState(g(playbackStateCompat.getState()));
        }

        public void a(MediaMetadataCompat mediaMetadataCompat) {
            Bundle bundle;
            if (mediaMetadataCompat != null) {
                mediaMetadataCompat = new Builder(mediaMetadataCompat, MediaSessionCompat.a).build();
            }
            synchronized (this.e) {
                this.k = mediaMetadataCompat;
            }
            b(mediaMetadataCompat);
            if (this.h) {
                if (mediaMetadataCompat == null) {
                    bundle = null;
                } else {
                    bundle = mediaMetadataCompat.getBundle();
                }
                b(bundle).apply();
            }
        }

        /* access modifiers changed from: 0000 */
        public MetadataEditor b(Bundle bundle) {
            MetadataEditor editMetadata = this.d.editMetadata(true);
            if (bundle == null) {
                return editMetadata;
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ART)) {
                Bitmap bitmap = (Bitmap) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_ART);
                if (bitmap != null) {
                    bitmap = bitmap.copy(bitmap.getConfig(), false);
                }
                editMetadata.putBitmap(100, bitmap);
            } else if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)) {
                Bitmap bitmap2 = (Bitmap) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
                if (bitmap2 != null) {
                    bitmap2 = bitmap2.copy(bitmap2.getConfig(), false);
                }
                editMetadata.putBitmap(100, bitmap2);
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM)) {
                editMetadata.putString(1, bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST)) {
                editMetadata.putString(13, bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ARTIST)) {
                editMetadata.putString(2, bundle.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_AUTHOR)) {
                editMetadata.putString(3, bundle.getString(MediaMetadataCompat.METADATA_KEY_AUTHOR));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_COMPILATION)) {
                editMetadata.putString(15, bundle.getString(MediaMetadataCompat.METADATA_KEY_COMPILATION));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_COMPOSER)) {
                editMetadata.putString(4, bundle.getString(MediaMetadataCompat.METADATA_KEY_COMPOSER));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DATE)) {
                editMetadata.putString(5, bundle.getString(MediaMetadataCompat.METADATA_KEY_DATE));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER)) {
                editMetadata.putLong(14, bundle.getLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                editMetadata.putLong(9, bundle.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_GENRE)) {
                editMetadata.putString(6, bundle.getString(MediaMetadataCompat.METADATA_KEY_GENRE));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_TITLE)) {
                editMetadata.putString(7, bundle.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)) {
                editMetadata.putLong(0, bundle.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_WRITER)) {
                editMetadata.putString(11, bundle.getString(MediaMetadataCompat.METADATA_KEY_WRITER));
            }
            return editMetadata;
        }

        public void a(PendingIntent pendingIntent) {
            synchronized (this.e) {
                this.m = pendingIntent;
            }
        }

        public void a(List<QueueItem> list) {
            this.n = list;
            b(list);
        }

        public void a(CharSequence charSequence) {
            this.o = charSequence;
            b(charSequence);
        }

        public void c(int i2) {
            this.p = i2;
        }

        public void b(boolean z2) {
            if (this.q != z2) {
                this.q = z2;
                c(z2);
            }
        }

        public void d(int i2) {
            if (this.r != i2) {
                this.r = i2;
                h(i2);
            }
        }

        public void e(int i2) {
            if (this.s != i2) {
                this.s = i2;
                i(i2);
            }
        }

        public void a(Bundle bundle) {
            this.t = bundle;
            c(bundle);
        }

        /* access modifiers changed from: 0000 */
        public boolean h() {
            if (this.h) {
                if (!this.D && (this.j & 1) != 0) {
                    a(this.z, this.y);
                    this.D = true;
                } else if (this.D && (this.j & 1) == 0) {
                    b(this.z, this.y);
                    this.D = false;
                }
                if (!this.E && (this.j & 2) != 0) {
                    this.c.registerRemoteControlClient(this.d);
                    this.E = true;
                    return true;
                } else if (this.E && (this.j & 2) == 0) {
                    this.d.setPlaybackState(0);
                    this.c.unregisterRemoteControlClient(this.d);
                    this.E = false;
                }
            } else {
                if (this.D) {
                    b(this.z, this.y);
                    this.D = false;
                }
                if (this.E) {
                    this.d.setPlaybackState(0);
                    this.c.unregisterRemoteControlClient(this.d);
                    this.E = false;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void a(PendingIntent pendingIntent, ComponentName componentName) {
            this.c.registerMediaButtonEventReceiver(componentName);
        }

        /* access modifiers changed from: 0000 */
        public void b(PendingIntent pendingIntent, ComponentName componentName) {
            this.c.unregisterMediaButtonEventReceiver(componentName);
        }

        /* access modifiers changed from: 0000 */
        public void b(int i2, int i3) {
            if (this.u != 2) {
                this.c.adjustStreamVolume(this.v, i2, i3);
            } else if (this.w != null) {
                this.w.onAdjustVolume(i2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void c(int i2, int i3) {
            if (this.u != 2) {
                this.c.setStreamVolume(this.v, i2, i3);
            } else if (this.w != null) {
                this.w.onSetVolumeTo(i2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ParcelableVolumeInfo parcelableVolumeInfo) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onVolumeInfoChanged(parcelableVolumeInfo);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void i() {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onSessionDestroyed();
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
            this.f.kill();
        }

        private void b(String str, Bundle bundle) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onEvent(str, bundle);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void c(PlaybackStateCompat playbackStateCompat) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void b(MediaMetadataCompat mediaMetadataCompat) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onMetadataChanged(mediaMetadataCompat);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void b(List<QueueItem> list) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onQueueChanged(list);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void b(CharSequence charSequence) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onQueueTitleChanged(charSequence);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void c(boolean z2) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onCaptioningEnabledChanged(z2);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void h(int i2) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onRepeatModeChanged(i2);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void i(int i2) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onShuffleModeChanged(i2);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }

        private void c(Bundle bundle) {
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.f.getBroadcastItem(beginBroadcast)).onExtrasChanged(bundle);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
        }
    }

    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    public static final class QueueItem implements Parcelable {
        public static final Creator<QueueItem> CREATOR = new Creator<QueueItem>() {
            /* renamed from: a */
            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            /* renamed from: a */
            public QueueItem[] newArray(int i) {
                return new QueueItem[i];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat a;
        private final long b;
        private Object c;

        public int describeContents() {
            return 0;
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            this(null, mediaDescriptionCompat, j);
        }

        private QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if (j == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            } else {
                this.a = mediaDescriptionCompat;
                this.b = j;
                this.c = obj;
            }
        }

        QueueItem(Parcel parcel) {
            this.a = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.b = parcel.readLong();
        }

        public MediaDescriptionCompat getDescription() {
            return this.a;
        }

        public long getQueueId() {
            return this.b;
        }

        public void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
            parcel.writeLong(this.b);
        }

        public Object getQueueItem() {
            if (this.c != null || VERSION.SDK_INT < 21) {
                return this.c;
            }
            this.c = QueueItem.a(this.a.getMediaDescription(), this.b);
            return this.c;
        }

        public static QueueItem fromQueueItem(Object obj) {
            if (obj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            return new QueueItem(obj, MediaDescriptionCompat.fromMediaDescription(QueueItem.a(obj)), QueueItem.b(obj));
        }

        public static List<QueueItem> fromQueueItemList(List<?> list) {
            if (list == null || VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object fromQueueItem : list) {
                arrayList.add(fromQueueItem(fromQueueItem));
            }
            return arrayList;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("MediaSession.QueueItem {Description=");
            sb.append(this.a);
            sb.append(", Id=");
            sb.append(this.b);
            sb.append(" }");
            return sb.toString();
        }
    }

    static final class ResultReceiverWrapper implements Parcelable {
        public static final Creator<ResultReceiverWrapper> CREATOR = new Creator<ResultReceiverWrapper>() {
            /* renamed from: a */
            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            /* renamed from: a */
            public ResultReceiverWrapper[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }
        };
        /* access modifiers changed from: private */
        public ResultReceiver a;

        public int describeContents() {
            return 0;
        }

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            this.a = resultReceiver;
        }

        ResultReceiverWrapper(Parcel parcel) {
            this.a = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    public static final class Token implements Parcelable {
        public static final Creator<Token> CREATOR = new Creator<Token>() {
            /* renamed from: a */
            public Token createFromParcel(Parcel parcel) {
                Object obj;
                if (VERSION.SDK_INT >= 21) {
                    obj = parcel.readParcelable(null);
                } else {
                    obj = parcel.readStrongBinder();
                }
                return new Token(obj);
            }

            /* renamed from: a */
            public Token[] newArray(int i) {
                return new Token[i];
            }
        };
        private final Object a;
        private final IMediaSession b;

        public int describeContents() {
            return 0;
        }

        Token(Object obj) {
            this(obj, null);
        }

        Token(Object obj, IMediaSession iMediaSession) {
            this.a = obj;
            this.b = iMediaSession;
        }

        public static Token fromToken(Object obj) {
            return fromToken(obj, null);
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public static Token fromToken(Object obj, IMediaSession iMediaSession) {
            if (obj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            return new Token(MediaSessionCompatApi21.b(obj), iMediaSession);
        }

        public void writeToParcel(Parcel parcel, int i) {
            if (VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable) this.a, i);
            } else {
                parcel.writeStrongBinder((IBinder) this.a);
            }
        }

        public int hashCode() {
            if (this.a == null) {
                return 0;
            }
            return this.a.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            if (this.a == null) {
                if (token.a != null) {
                    z = false;
                }
                return z;
            } else if (token.a == null) {
                return false;
            } else {
                return this.a.equals(token.a);
            }
        }

        public Object getToken() {
            return this.a;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public IMediaSession getExtraBinder() {
            return this.b;
        }
    }

    public MediaSessionCompat(Context context, String str) {
        this(context, str, null, null);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        this.d = new ArrayList<>();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("tag must not be null or empty");
        } else {
            if (componentName == null) {
                componentName = MediaButtonReceiver.a(context);
                if (componentName == null) {
                    Log.w("MediaSessionCompat", "Couldn't find a unique registered media button receiver in the given context.");
                }
            }
            if (componentName != null && pendingIntent == null) {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setComponent(componentName);
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            }
            if (VERSION.SDK_INT >= 21) {
                this.b = new MediaSessionImplApi21(context, str);
                setCallback(new Callback() {
                });
                this.b.b(pendingIntent);
            } else if (VERSION.SDK_INT >= 19) {
                this.b = new MediaSessionImplApi19(context, str, componentName, pendingIntent);
            } else if (VERSION.SDK_INT >= 18) {
                this.b = new MediaSessionImplApi18(context, str, componentName, pendingIntent);
            } else {
                this.b = new MediaSessionImplBase(context, str, componentName, pendingIntent);
            }
            this.c = new MediaControllerCompat(context, this);
            if (a == 0) {
                a = (int) TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics());
            }
        }
    }

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        this.d = new ArrayList<>();
        this.b = mediaSessionImpl;
        if (VERSION.SDK_INT >= 21 && !MediaSessionCompatApi21.f(mediaSessionImpl.e())) {
            setCallback(new Callback() {
            });
        }
        this.c = new MediaControllerCompat(context, this);
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        MediaSessionImpl mediaSessionImpl = this.b;
        if (handler == null) {
            handler = new Handler();
        }
        mediaSessionImpl.a(callback, handler);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.b.a(pendingIntent);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.b.b(pendingIntent);
    }

    public void setFlags(int i) {
        this.b.a(i);
    }

    public void setPlaybackToLocal(int i) {
        this.b.b(i);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        this.b.a(volumeProviderCompat);
    }

    public void setActive(boolean z) {
        this.b.a(z);
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            ((OnActiveChangeListener) it.next()).onActiveChanged();
        }
    }

    public boolean isActive() {
        return this.b.a();
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        this.b.a(str, bundle);
    }

    public void release() {
        this.b.b();
    }

    public Token getSessionToken() {
        return this.b.c();
    }

    public MediaControllerCompat getController() {
        return this.c;
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.b.a(playbackStateCompat);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.b.a(mediaMetadataCompat);
    }

    public void setQueue(List<QueueItem> list) {
        this.b.a(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.b.a(charSequence);
    }

    public void setRatingType(int i) {
        this.b.c(i);
    }

    public void setCaptioningEnabled(boolean z) {
        this.b.b(z);
    }

    public void setRepeatMode(int i) {
        this.b.d(i);
    }

    public void setShuffleMode(int i) {
        this.b.e(i);
    }

    public void setExtras(Bundle bundle) {
        this.b.a(bundle);
    }

    public Object getMediaSession() {
        return this.b.e();
    }

    public Object getRemoteControlClient() {
        return this.b.f();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public String getCallingPackage() {
        return this.b.g();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.d.add(onActiveChangeListener);
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.d.remove(onActiveChangeListener);
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object obj) {
        if (context == null || obj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        return new MediaSessionCompat(context, (MediaSessionImpl) new MediaSessionImplApi21(obj));
    }

    /* access modifiers changed from: private */
    public static PlaybackStateCompat b(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat) {
        if (playbackStateCompat != null) {
            long j = -1;
            if (playbackStateCompat.getPosition() != -1) {
                if (playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 4 || playbackStateCompat.getState() == 5) {
                    long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime();
                    if (lastPositionUpdateTime > 0) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        long playbackSpeed = ((long) (playbackStateCompat.getPlaybackSpeed() * ((float) (elapsedRealtime - lastPositionUpdateTime)))) + playbackStateCompat.getPosition();
                        if (mediaMetadataCompat != null && mediaMetadataCompat.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                            j = mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                        }
                        long j2 = (j < 0 || playbackSpeed <= j) ? playbackSpeed < 0 ? 0 : playbackSpeed : j;
                        return new PlaybackStateCompat.Builder(playbackStateCompat).setState(playbackStateCompat.getState(), j2, playbackStateCompat.getPlaybackSpeed(), elapsedRealtime).build();
                    }
                }
                return playbackStateCompat;
            }
        }
        return playbackStateCompat;
    }
}
