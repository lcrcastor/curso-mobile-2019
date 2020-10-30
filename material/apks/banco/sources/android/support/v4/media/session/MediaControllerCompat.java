package android.support.v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.app.SupportActivity.ExtraData;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback.Stub;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class MediaControllerCompat {
    private final MediaControllerImpl a;
    private final Token b;
    private final HashSet<Callback> c = new HashSet<>();

    public static abstract class Callback implements DeathRecipient {
        MessageHandler a;
        boolean b;
        /* access modifiers changed from: private */
        public final Object c;

        class MessageHandler extends Handler {
            boolean a = false;

            MessageHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message message) {
                if (this.a) {
                    switch (message.what) {
                        case 1:
                            Callback.this.onSessionEvent((String) message.obj, message.getData());
                            break;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj);
                            break;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj);
                            break;
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) message.obj);
                            break;
                        case 5:
                            Callback.this.onQueueChanged((List) message.obj);
                            break;
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) message.obj);
                            break;
                        case 7:
                            Callback.this.onExtrasChanged((Bundle) message.obj);
                            break;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            break;
                        case 9:
                            Callback.this.onRepeatModeChanged(((Integer) message.obj).intValue());
                            break;
                        case 11:
                            Callback.this.onCaptioningEnabledChanged(((Boolean) message.obj).booleanValue());
                            break;
                        case 12:
                            Callback.this.onShuffleModeChanged(((Integer) message.obj).intValue());
                            break;
                        case 13:
                            Callback.this.onSessionReady();
                            break;
                    }
                }
            }
        }

        static class StubApi21 implements android.support.v4.media.session.MediaControllerCompatApi21.Callback {
            private final WeakReference<Callback> a;

            StubApi21(Callback callback) {
                this.a = new WeakReference<>(callback);
            }

            public void onSessionDestroyed() {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            public void onSessionEvent(String str, Bundle bundle) {
                Callback callback = (Callback) this.a.get();
                if (callback == null) {
                    return;
                }
                if (!callback.b || VERSION.SDK_INT >= 23) {
                    callback.onSessionEvent(str, bundle);
                }
            }

            public void onPlaybackStateChanged(Object obj) {
                Callback callback = (Callback) this.a.get();
                if (callback != null && !callback.b) {
                    callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(obj));
                }
            }

            public void onMetadataChanged(Object obj) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(obj));
                }
            }

            public void onQueueChanged(List<?> list) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.onQueueChanged(QueueItem.fromQueueItemList(list));
                }
            }

            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.onQueueTitleChanged(charSequence);
                }
            }

            public void onExtrasChanged(Bundle bundle) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.onExtrasChanged(bundle);
                }
            }

            public void onAudioInfoChanged(int i, int i2, int i3, int i4, int i5) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    PlaybackInfo playbackInfo = new PlaybackInfo(i, i2, i3, i4, i5);
                    callback.onAudioInfoChanged(playbackInfo);
                }
            }
        }

        static class StubCompat extends Stub {
            private final WeakReference<Callback> a;

            public void onShuffleModeChangedRemoved(boolean z) {
            }

            StubCompat(Callback callback) {
                this.a = new WeakReference<>(callback);
            }

            public void onEvent(String str, Bundle bundle) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(1, str, bundle);
                }
            }

            public void onSessionDestroyed() {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(8, null, null);
                }
            }

            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(2, playbackStateCompat, null);
                }
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(3, mediaMetadataCompat, null);
                }
            }

            public void onQueueChanged(List<QueueItem> list) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(5, list, null);
                }
            }

            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(6, charSequence, null);
                }
            }

            public void onCaptioningEnabledChanged(boolean z) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(11, Boolean.valueOf(z), null);
                }
            }

            public void onRepeatModeChanged(int i) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(9, Integer.valueOf(i), null);
                }
            }

            public void onShuffleModeChanged(int i) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(12, Integer.valueOf(i), null);
                }
            }

            public void onExtrasChanged(Bundle bundle) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(7, bundle, null);
                }
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(4, parcelableVolumeInfo != null ? new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume) : null, null);
                }
            }

            public void onSessionReady() {
                Callback callback = (Callback) this.a.get();
                if (callback != null) {
                    callback.a(13, null, null);
                }
            }
        }

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onCaptioningEnabledChanged(boolean z) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onQueueChanged(List<QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onRepeatModeChanged(int i) {
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String str, Bundle bundle) {
        }

        public void onSessionReady() {
        }

        public void onShuffleModeChanged(int i) {
        }

        public Callback() {
            if (VERSION.SDK_INT >= 21) {
                this.c = MediaControllerCompatApi21.a((android.support.v4.media.session.MediaControllerCompatApi21.Callback) new StubApi21(this));
            } else {
                this.c = new StubCompat(this);
            }
        }

        public void binderDied() {
            onSessionDestroyed();
        }

        /* access modifiers changed from: 0000 */
        public void a(Handler handler) {
            if (handler != null) {
                this.a = new MessageHandler(handler.getLooper());
                this.a.a = true;
            } else if (this.a != null) {
                this.a.a = false;
                this.a.removeCallbacksAndMessages(null);
                this.a = null;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Object obj, Bundle bundle) {
            if (this.a != null) {
                Message obtainMessage = this.a.obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }
        }
    }

    static class MediaControllerExtraData extends ExtraData {
        private final MediaControllerCompat a;

        MediaControllerExtraData(MediaControllerCompat mediaControllerCompat) {
            this.a = mediaControllerCompat;
        }

        /* access modifiers changed from: 0000 */
        public MediaControllerCompat a() {
            return this.a;
        }
    }

    interface MediaControllerImpl {
        TransportControls a();

        void a(int i, int i2);

        void a(MediaDescriptionCompat mediaDescriptionCompat);

        void a(MediaDescriptionCompat mediaDescriptionCompat, int i);

        void a(Callback callback);

        void a(Callback callback, Handler handler);

        void a(String str, Bundle bundle, ResultReceiver resultReceiver);

        boolean a(KeyEvent keyEvent);

        PlaybackStateCompat b();

        void b(int i, int i2);

        void b(MediaDescriptionCompat mediaDescriptionCompat);

        MediaMetadataCompat c();

        List<QueueItem> d();

        CharSequence e();

        Bundle f();

        int g();

        boolean h();

        int i();

        int j();

        long k();

        PlaybackInfo l();

        PendingIntent m();

        boolean n();

        String o();

        Object p();
    }

    @RequiresApi(21)
    static class MediaControllerImplApi21 implements MediaControllerImpl {
        protected final Object a;
        private final List<Callback> b = new ArrayList();
        /* access modifiers changed from: private */
        public IMediaSession c;
        private HashMap<Callback, ExtraCallback> d = new HashMap<>();

        static class ExtraBinderRequestResultReceiver extends ResultReceiver {
            private WeakReference<MediaControllerImplApi21> a;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21, Handler handler) {
                super(handler);
                this.a = new WeakReference<>(mediaControllerImplApi21);
            }

            /* access modifiers changed from: protected */
            public void onReceiveResult(int i, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerImplApi21) this.a.get();
                if (mediaControllerImplApi21 != null && bundle != null) {
                    mediaControllerImplApi21.c = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER"));
                    mediaControllerImplApi21.r();
                }
            }
        }

        static class ExtraCallback extends StubCompat {
            ExtraCallback(Callback callback) {
                super(callback);
            }

            public void onSessionDestroyed() {
                throw new AssertionError();
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                throw new AssertionError();
            }

            public void onQueueChanged(List<QueueItem> list) {
                throw new AssertionError();
            }

            public void onQueueTitleChanged(CharSequence charSequence) {
                throw new AssertionError();
            }

            public void onExtrasChanged(Bundle bundle) {
                throw new AssertionError();
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                throw new AssertionError();
            }
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat mediaSessionCompat) {
            this.a = MediaControllerCompatApi21.a(context, mediaSessionCompat.getSessionToken().getToken());
            this.c = mediaSessionCompat.getSessionToken().getExtraBinder();
            if (this.c == null) {
                q();
            }
        }

        public MediaControllerImplApi21(Context context, Token token) {
            this.a = MediaControllerCompatApi21.a(context, token.getToken());
            if (this.a == null) {
                throw new RemoteException();
            }
            this.c = token.getExtraBinder();
            if (this.c == null) {
                q();
            }
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(android.support.v4.media.session.MediaControllerCompat.Callback r3, android.os.Handler r4) {
            /*
                r2 = this;
                java.lang.Object r0 = r2.a
                java.lang.Object r1 = r3.c
                android.support.v4.media.session.MediaControllerCompatApi21.a(r0, r1, r4)
                android.support.v4.media.session.IMediaSession r4 = r2.c
                if (r4 == 0) goto L_0x0029
                android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback r4 = new android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback
                r4.<init>(r3)
                java.util.HashMap<android.support.v4.media.session.MediaControllerCompat$Callback, android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback> r0 = r2.d
                r0.put(r3, r4)
                r0 = 1
                r3.b = r0
                android.support.v4.media.session.IMediaSession r3 = r2.c     // Catch:{ RemoteException -> 0x0020 }
                r3.registerCallbackListener(r4)     // Catch:{ RemoteException -> 0x0020 }
                goto L_0x0035
            L_0x0020:
                r3 = move-exception
                java.lang.String r4 = "MediaControllerCompat"
                java.lang.String r0 = "Dead object in registerCallback."
                android.util.Log.e(r4, r0, r3)
                goto L_0x0035
            L_0x0029:
                java.util.List<android.support.v4.media.session.MediaControllerCompat$Callback> r4 = r2.b
                monitor-enter(r4)
                r0 = 0
                r3.b = r0     // Catch:{ all -> 0x0036 }
                java.util.List<android.support.v4.media.session.MediaControllerCompat$Callback> r0 = r2.b     // Catch:{ all -> 0x0036 }
                r0.add(r3)     // Catch:{ all -> 0x0036 }
                monitor-exit(r4)     // Catch:{ all -> 0x0036 }
            L_0x0035:
                return
            L_0x0036:
                r3 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0036 }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21.a(android.support.v4.media.session.MediaControllerCompat$Callback, android.os.Handler):void");
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(android.support.v4.media.session.MediaControllerCompat.Callback r3) {
            /*
                r2 = this;
                java.lang.Object r0 = r2.a
                java.lang.Object r1 = r3.c
                android.support.v4.media.session.MediaControllerCompatApi21.a(r0, r1)
                android.support.v4.media.session.IMediaSession r0 = r2.c
                if (r0 == 0) goto L_0x0029
                java.util.HashMap<android.support.v4.media.session.MediaControllerCompat$Callback, android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback> r0 = r2.d     // Catch:{ RemoteException -> 0x0020 }
                java.lang.Object r0 = r0.remove(r3)     // Catch:{ RemoteException -> 0x0020 }
                android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback r0 = (android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21.ExtraCallback) r0     // Catch:{ RemoteException -> 0x0020 }
                if (r0 == 0) goto L_0x0032
                r1 = 0
                r3.b = r1     // Catch:{ RemoteException -> 0x0020 }
                android.support.v4.media.session.IMediaSession r3 = r2.c     // Catch:{ RemoteException -> 0x0020 }
                r3.unregisterCallbackListener(r0)     // Catch:{ RemoteException -> 0x0020 }
                goto L_0x0032
            L_0x0020:
                r3 = move-exception
                java.lang.String r0 = "MediaControllerCompat"
                java.lang.String r1 = "Dead object in unregisterCallback."
                android.util.Log.e(r0, r1, r3)
                goto L_0x0032
            L_0x0029:
                java.util.List<android.support.v4.media.session.MediaControllerCompat$Callback> r0 = r2.b
                monitor-enter(r0)
                java.util.List<android.support.v4.media.session.MediaControllerCompat$Callback> r1 = r2.b     // Catch:{ all -> 0x0033 }
                r1.remove(r3)     // Catch:{ all -> 0x0033 }
                monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            L_0x0032:
                return
            L_0x0033:
                r3 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0033 }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21.a(android.support.v4.media.session.MediaControllerCompat$Callback):void");
        }

        public boolean a(KeyEvent keyEvent) {
            return MediaControllerCompatApi21.a(this.a, keyEvent);
        }

        public TransportControls a() {
            Object b2 = MediaControllerCompatApi21.b(this.a);
            if (b2 != null) {
                return new TransportControlsApi21(b2);
            }
            return null;
        }

        public PlaybackStateCompat b() {
            if (this.c != null) {
                try {
                    return this.c.getPlaybackState();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
                }
            }
            Object c2 = MediaControllerCompatApi21.c(this.a);
            return c2 != null ? PlaybackStateCompat.fromPlaybackState(c2) : null;
        }

        public MediaMetadataCompat c() {
            Object d2 = MediaControllerCompatApi21.d(this.a);
            if (d2 != null) {
                return MediaMetadataCompat.fromMediaMetadata(d2);
            }
            return null;
        }

        public List<QueueItem> d() {
            List e = MediaControllerCompatApi21.e(this.a);
            if (e != null) {
                return QueueItem.fromQueueItemList(e);
            }
            return null;
        }

        public void a(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((k() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", mediaDescriptionCompat);
            a("android.support.v4.media.session.command.ADD_QUEUE_ITEM", bundle, null);
        }

        public void a(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if ((k() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", mediaDescriptionCompat);
            bundle.putInt("android.support.v4.media.session.command.ARGUMENT_INDEX", i);
            a("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT", bundle, null);
        }

        public void b(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((k() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", mediaDescriptionCompat);
            a("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM", bundle, null);
        }

        public CharSequence e() {
            return MediaControllerCompatApi21.f(this.a);
        }

        public Bundle f() {
            return MediaControllerCompatApi21.g(this.a);
        }

        public int g() {
            if (VERSION.SDK_INT < 22 && this.c != null) {
                try {
                    return this.c.getRatingType();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getRatingType.", e);
                }
            }
            return MediaControllerCompatApi21.h(this.a);
        }

        public boolean h() {
            if (this.c != null) {
                try {
                    return this.c.isCaptioningEnabled();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
                }
            }
            return false;
        }

        public int i() {
            if (this.c != null) {
                try {
                    return this.c.getRepeatMode();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
                }
            }
            return -1;
        }

        public int j() {
            if (this.c != null) {
                try {
                    return this.c.getShuffleMode();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
                }
            }
            return -1;
        }

        public long k() {
            return MediaControllerCompatApi21.i(this.a);
        }

        public PlaybackInfo l() {
            Object j = MediaControllerCompatApi21.j(this.a);
            if (j == null) {
                return null;
            }
            PlaybackInfo playbackInfo = new PlaybackInfo(android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(j), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(j), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(j), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(j), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(j));
            return playbackInfo;
        }

        public PendingIntent m() {
            return MediaControllerCompatApi21.k(this.a);
        }

        public void a(int i, int i2) {
            MediaControllerCompatApi21.a(this.a, i, i2);
        }

        public void b(int i, int i2) {
            MediaControllerCompatApi21.b(this.a, i, i2);
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            MediaControllerCompatApi21.a(this.a, str, bundle, resultReceiver);
        }

        public boolean n() {
            return this.c != null;
        }

        public String o() {
            return MediaControllerCompatApi21.l(this.a);
        }

        public Object p() {
            return this.a;
        }

        private void q() {
            a("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
        }

        /* access modifiers changed from: private */
        public void r() {
            if (this.c != null) {
                synchronized (this.b) {
                    for (Callback callback : this.b) {
                        ExtraCallback extraCallback = new ExtraCallback(callback);
                        this.d.put(callback, extraCallback);
                        callback.b = true;
                        try {
                            this.c.registerCallbackListener(extraCallback);
                            callback.onSessionReady();
                        } catch (RemoteException e) {
                            Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                        }
                    }
                    this.b.clear();
                }
            }
        }
    }

    @RequiresApi(23)
    static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
        public MediaControllerImplApi23(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat);
        }

        public MediaControllerImplApi23(Context context, Token token) {
            super(context, token);
        }

        public TransportControls a() {
            Object b = MediaControllerCompatApi21.b(this.a);
            if (b != null) {
                return new TransportControlsApi23(b);
            }
            return null;
        }
    }

    @RequiresApi(24)
    static class MediaControllerImplApi24 extends MediaControllerImplApi23 {
        public MediaControllerImplApi24(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat);
        }

        public MediaControllerImplApi24(Context context, Token token) {
            super(context, token);
        }

        public TransportControls a() {
            Object b = MediaControllerCompatApi21.b(this.a);
            if (b != null) {
                return new TransportControlsApi24(b);
            }
            return null;
        }
    }

    static class MediaControllerImplBase implements MediaControllerImpl {
        private IMediaSession a;
        private TransportControls b;

        public boolean n() {
            return true;
        }

        public Object p() {
            return null;
        }

        public MediaControllerImplBase(Token token) {
            this.a = IMediaSession.Stub.asInterface((IBinder) token.getToken());
        }

        public void a(Callback callback, Handler handler) {
            if (callback == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.a.asBinder().linkToDeath(callback, 0);
                this.a.registerCallbackListener((IMediaControllerCallback) callback.c);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                callback.onSessionDestroyed();
            }
        }

        public void a(Callback callback) {
            if (callback == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.a.unregisterCallbackListener((IMediaControllerCallback) callback.c);
                this.a.asBinder().unlinkToDeath(callback, 0);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
            }
        }

        public boolean a(KeyEvent keyEvent) {
            if (keyEvent == null) {
                throw new IllegalArgumentException("event may not be null.");
            }
            try {
                this.a.sendMediaButton(keyEvent);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", e);
            }
            return false;
        }

        public TransportControls a() {
            if (this.b == null) {
                this.b = new TransportControlsBase(this.a);
            }
            return this.b;
        }

        public PlaybackStateCompat b() {
            try {
                return this.a.getPlaybackState();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
                return null;
            }
        }

        public MediaMetadataCompat c() {
            try {
                return this.a.getMetadata();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getMetadata.", e);
                return null;
            }
        }

        public List<QueueItem> d() {
            try {
                return this.a.getQueue();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getQueue.", e);
                return null;
            }
        }

        public void a(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.a.getFlags() & 4) == 0) {
                    throw new UnsupportedOperationException("This session doesn't support queue management operations");
                }
                this.a.addQueueItem(mediaDescriptionCompat);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in addQueueItem.", e);
            }
        }

        public void a(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            try {
                if ((this.a.getFlags() & 4) == 0) {
                    throw new UnsupportedOperationException("This session doesn't support queue management operations");
                }
                this.a.addQueueItemAt(mediaDescriptionCompat, i);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in addQueueItemAt.", e);
            }
        }

        public void b(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.a.getFlags() & 4) == 0) {
                    throw new UnsupportedOperationException("This session doesn't support queue management operations");
                }
                this.a.removeQueueItem(mediaDescriptionCompat);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in removeQueueItem.", e);
            }
        }

        public CharSequence e() {
            try {
                return this.a.getQueueTitle();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getQueueTitle.", e);
                return null;
            }
        }

        public Bundle f() {
            try {
                return this.a.getExtras();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getExtras.", e);
                return null;
            }
        }

        public int g() {
            try {
                return this.a.getRatingType();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRatingType.", e);
                return 0;
            }
        }

        public boolean h() {
            try {
                return this.a.isCaptioningEnabled();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
                return false;
            }
        }

        public int i() {
            try {
                return this.a.getRepeatMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
                return -1;
            }
        }

        public int j() {
            try {
                return this.a.getShuffleMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
                return -1;
            }
        }

        public long k() {
            try {
                return this.a.getFlags();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getFlags.", e);
                return 0;
            }
        }

        public PlaybackInfo l() {
            try {
                ParcelableVolumeInfo volumeAttributes = this.a.getVolumeAttributes();
                PlaybackInfo playbackInfo = new PlaybackInfo(volumeAttributes.volumeType, volumeAttributes.audioStream, volumeAttributes.controlType, volumeAttributes.maxVolume, volumeAttributes.currentVolume);
                return playbackInfo;
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPlaybackInfo.", e);
                return null;
            }
        }

        public PendingIntent m() {
            try {
                return this.a.getLaunchPendingIntent();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getSessionActivity.", e);
                return null;
            }
        }

        public void a(int i, int i2) {
            try {
                this.a.setVolumeTo(i, i2, null);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setVolumeTo.", e);
            }
        }

        public void b(int i, int i2) {
            try {
                this.a.adjustVolume(i, i2, null);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in adjustVolume.", e);
            }
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            try {
                this.a.sendCommand(str, bundle, new ResultReceiverWrapper(resultReceiver));
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in sendCommand.", e);
            }
        }

        public String o() {
            try {
                return this.a.getPackageName();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPackageName.", e);
                return null;
            }
        }
    }

    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final int a;
        private final int b;
        private final int c;
        private final int d;
        private final int e;

        PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
        }

        public int getPlaybackType() {
            return this.a;
        }

        public int getAudioStream() {
            return this.b;
        }

        public int getVolumeControl() {
            return this.c;
        }

        public int getMaxVolume() {
            return this.d;
        }

        public int getCurrentVolume() {
            return this.e;
        }
    }

    public static abstract class TransportControls {
        public static final String EXTRA_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE";

        public abstract void fastForward();

        public abstract void pause();

        public abstract void play();

        public abstract void playFromMediaId(String str, Bundle bundle);

        public abstract void playFromSearch(String str, Bundle bundle);

        public abstract void playFromUri(Uri uri, Bundle bundle);

        public abstract void prepare();

        public abstract void prepareFromMediaId(String str, Bundle bundle);

        public abstract void prepareFromSearch(String str, Bundle bundle);

        public abstract void prepareFromUri(Uri uri, Bundle bundle);

        public abstract void rewind();

        public abstract void seekTo(long j);

        public abstract void sendCustomAction(CustomAction customAction, Bundle bundle);

        public abstract void sendCustomAction(String str, Bundle bundle);

        public abstract void setCaptioningEnabled(boolean z);

        public abstract void setRating(RatingCompat ratingCompat);

        public abstract void setRating(RatingCompat ratingCompat, Bundle bundle);

        public abstract void setRepeatMode(int i);

        public abstract void setShuffleMode(int i);

        public abstract void skipToNext();

        public abstract void skipToPrevious();

        public abstract void skipToQueueItem(long j);

        public abstract void stop();

        TransportControls() {
        }
    }

    static class TransportControlsApi21 extends TransportControls {
        protected final Object a;

        public TransportControlsApi21(Object obj) {
            this.a = obj;
        }

        public void prepare() {
            sendCustomAction("android.support.v4.media.session.action.PREPARE", (Bundle) null);
        }

        public void prepareFromMediaId(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", str);
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle2);
        }

        public void prepareFromSearch(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", str);
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle2);
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri);
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle2);
        }

        public void play() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.play(this.a);
        }

        public void pause() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.pause(this.a);
        }

        public void stop() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.stop(this.a);
        }

        public void seekTo(long j) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.seekTo(this.a, j);
        }

        public void fastForward() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.fastForward(this.a);
        }

        public void rewind() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.rewind(this.a);
        }

        public void skipToNext() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToNext(this.a);
        }

        public void skipToPrevious() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToPrevious(this.a);
        }

        public void setRating(RatingCompat ratingCompat) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.setRating(this.a, ratingCompat != null ? ratingCompat.getRating() : null);
        }

        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_RATING", ratingCompat);
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.SET_RATING", bundle2);
        }

        public void setCaptioningEnabled(boolean z) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED", z);
            sendCustomAction("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED", bundle);
        }

        public void setRepeatMode(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", i);
            sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle);
        }

        public void setShuffleMode(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE", i);
            sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE", bundle);
        }

        public void playFromMediaId(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.playFromMediaId(this.a, str, bundle);
        }

        public void playFromSearch(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.playFromSearch(this.a, str, bundle);
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            if (uri == null || Uri.EMPTY.equals(uri)) {
                throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri);
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle2);
        }

        public void skipToQueueItem(long j) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.a, j);
        }

        public void sendCustomAction(CustomAction customAction, Bundle bundle) {
            MediaControllerCompat.b(customAction.getAction(), bundle);
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.a, customAction.getAction(), bundle);
        }

        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.b(str, bundle);
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.a, str, bundle);
        }
    }

    @RequiresApi(23)
    static class TransportControlsApi23 extends TransportControlsApi21 {
        public TransportControlsApi23(Object obj) {
            super(obj);
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi23.TransportControls.playFromUri(this.a, uri, bundle);
        }
    }

    @RequiresApi(24)
    static class TransportControlsApi24 extends TransportControlsApi23 {
        public TransportControlsApi24(Object obj) {
            super(obj);
        }

        public void prepare() {
            android.support.v4.media.session.MediaControllerCompatApi24.TransportControls.prepare(this.a);
        }

        public void prepareFromMediaId(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi24.TransportControls.prepareFromMediaId(this.a, str, bundle);
        }

        public void prepareFromSearch(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi24.TransportControls.prepareFromSearch(this.a, str, bundle);
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi24.TransportControls.prepareFromUri(this.a, uri, bundle);
        }
    }

    static class TransportControlsBase extends TransportControls {
        private IMediaSession a;

        public TransportControlsBase(IMediaSession iMediaSession) {
            this.a = iMediaSession;
        }

        public void prepare() {
            try {
                this.a.prepare();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepare.", e);
            }
        }

        public void prepareFromMediaId(String str, Bundle bundle) {
            try {
                this.a.prepareFromMediaId(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepareFromMediaId.", e);
            }
        }

        public void prepareFromSearch(String str, Bundle bundle) {
            try {
                this.a.prepareFromSearch(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepareFromSearch.", e);
            }
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            try {
                this.a.prepareFromUri(uri, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepareFromUri.", e);
            }
        }

        public void play() {
            try {
                this.a.play();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in play.", e);
            }
        }

        public void playFromMediaId(String str, Bundle bundle) {
            try {
                this.a.playFromMediaId(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in playFromMediaId.", e);
            }
        }

        public void playFromSearch(String str, Bundle bundle) {
            try {
                this.a.playFromSearch(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in playFromSearch.", e);
            }
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            try {
                this.a.playFromUri(uri, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in playFromUri.", e);
            }
        }

        public void skipToQueueItem(long j) {
            try {
                this.a.skipToQueueItem(j);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in skipToQueueItem.", e);
            }
        }

        public void pause() {
            try {
                this.a.pause();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in pause.", e);
            }
        }

        public void stop() {
            try {
                this.a.stop();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in stop.", e);
            }
        }

        public void seekTo(long j) {
            try {
                this.a.seekTo(j);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in seekTo.", e);
            }
        }

        public void fastForward() {
            try {
                this.a.fastForward();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in fastForward.", e);
            }
        }

        public void skipToNext() {
            try {
                this.a.next();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in skipToNext.", e);
            }
        }

        public void rewind() {
            try {
                this.a.rewind();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in rewind.", e);
            }
        }

        public void skipToPrevious() {
            try {
                this.a.previous();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in skipToPrevious.", e);
            }
        }

        public void setRating(RatingCompat ratingCompat) {
            try {
                this.a.rate(ratingCompat);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setRating.", e);
            }
        }

        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            try {
                this.a.rateWithExtras(ratingCompat, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setRating.", e);
            }
        }

        public void setCaptioningEnabled(boolean z) {
            try {
                this.a.setCaptioningEnabled(z);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setCaptioningEnabled.", e);
            }
        }

        public void setRepeatMode(int i) {
            try {
                this.a.setRepeatMode(i);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setRepeatMode.", e);
            }
        }

        public void setShuffleMode(int i) {
            try {
                this.a.setShuffleMode(i);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setShuffleMode.", e);
            }
        }

        public void sendCustomAction(CustomAction customAction, Bundle bundle) {
            sendCustomAction(customAction.getAction(), bundle);
        }

        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.b(str, bundle);
            try {
                this.a.sendCustomAction(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in sendCustomAction.", e);
            }
        }
    }

    public static void setMediaController(@NonNull Activity activity, MediaControllerCompat mediaControllerCompat) {
        if (activity instanceof SupportActivity) {
            ((SupportActivity) activity).putExtraData(new MediaControllerExtraData(mediaControllerCompat));
        }
        if (VERSION.SDK_INT >= 21) {
            Object obj = null;
            if (mediaControllerCompat != null) {
                obj = MediaControllerCompatApi21.a((Context) activity, mediaControllerCompat.getSessionToken().getToken());
            }
            MediaControllerCompatApi21.a(activity, obj);
        }
    }

    public static MediaControllerCompat getMediaController(@NonNull Activity activity) {
        MediaControllerCompat mediaControllerCompat = null;
        if (activity instanceof SupportActivity) {
            MediaControllerExtraData mediaControllerExtraData = (MediaControllerExtraData) ((SupportActivity) activity).getExtraData(MediaControllerExtraData.class);
            if (mediaControllerExtraData != null) {
                mediaControllerCompat = mediaControllerExtraData.a();
            }
            return mediaControllerCompat;
        }
        if (VERSION.SDK_INT >= 21) {
            Object a2 = MediaControllerCompatApi21.a(activity);
            if (a2 == null) {
                return null;
            }
            try {
                return new MediaControllerCompat((Context) activity, Token.fromToken(MediaControllerCompatApi21.a(a2)));
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getMediaController.", e);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static void b(String str, Bundle bundle) {
        if (str != null) {
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1348483723) {
                if (hashCode == 503011406 && str.equals(MediaSessionCompat.ACTION_UNFOLLOW)) {
                    c2 = 1;
                }
            } else if (str.equals(MediaSessionCompat.ACTION_FOLLOW)) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                case 1:
                    if (bundle == null || !bundle.containsKey(MediaSessionCompat.ARGUMENT_MEDIA_ATTRIBUTE)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action ");
                        sb.append(str);
                        sb.append(".");
                        throw new IllegalArgumentException(sb.toString());
                    }
            }
        }
    }

    public MediaControllerCompat(Context context, @NonNull MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        this.b = mediaSessionCompat.getSessionToken();
        if (VERSION.SDK_INT >= 24) {
            this.a = new MediaControllerImplApi24(context, mediaSessionCompat);
        } else if (VERSION.SDK_INT >= 23) {
            this.a = new MediaControllerImplApi23(context, mediaSessionCompat);
        } else if (VERSION.SDK_INT >= 21) {
            this.a = new MediaControllerImplApi21(context, mediaSessionCompat);
        } else {
            this.a = new MediaControllerImplBase(this.b);
        }
    }

    public MediaControllerCompat(Context context, @NonNull Token token) {
        if (token == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.b = token;
        if (VERSION.SDK_INT >= 24) {
            this.a = new MediaControllerImplApi24(context, token);
        } else if (VERSION.SDK_INT >= 23) {
            this.a = new MediaControllerImplApi23(context, token);
        } else if (VERSION.SDK_INT >= 21) {
            this.a = new MediaControllerImplApi21(context, token);
        } else {
            this.a = new MediaControllerImplBase(this.b);
        }
    }

    public TransportControls getTransportControls() {
        return this.a.a();
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.a.a(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public PlaybackStateCompat getPlaybackState() {
        return this.a.b();
    }

    public MediaMetadataCompat getMetadata() {
        return this.a.c();
    }

    public List<QueueItem> getQueue() {
        return this.a.d();
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.a.a(mediaDescriptionCompat);
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        this.a.a(mediaDescriptionCompat, i);
    }

    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.a.b(mediaDescriptionCompat);
    }

    @Deprecated
    public void removeQueueItemAt(int i) {
        List queue = getQueue();
        if (queue != null && i >= 0 && i < queue.size()) {
            QueueItem queueItem = (QueueItem) queue.get(i);
            if (queueItem != null) {
                removeQueueItem(queueItem.getDescription());
            }
        }
    }

    public CharSequence getQueueTitle() {
        return this.a.e();
    }

    public Bundle getExtras() {
        return this.a.f();
    }

    public int getRatingType() {
        return this.a.g();
    }

    public boolean isCaptioningEnabled() {
        return this.a.h();
    }

    public int getRepeatMode() {
        return this.a.i();
    }

    public int getShuffleMode() {
        return this.a.j();
    }

    public long getFlags() {
        return this.a.k();
    }

    public PlaybackInfo getPlaybackInfo() {
        return this.a.l();
    }

    public PendingIntent getSessionActivity() {
        return this.a.m();
    }

    public Token getSessionToken() {
        return this.b;
    }

    public void setVolumeTo(int i, int i2) {
        this.a.a(i, i2);
    }

    public void adjustVolume(int i, int i2) {
        this.a.b(i, i2);
    }

    public void registerCallback(@NonNull Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(@NonNull Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (handler == null) {
            handler = new Handler();
        }
        callback.a(handler);
        this.a.a(callback, handler);
        this.c.add(callback);
    }

    public void unregisterCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        try {
            this.c.remove(callback);
            this.a.a(callback);
        } finally {
            callback.a((Handler) null);
        }
    }

    public void sendCommand(@NonNull String str, Bundle bundle, ResultReceiver resultReceiver) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command must neither be null nor empty");
        }
        this.a.a(str, bundle, resultReceiver);
    }

    public boolean isSessionReady() {
        return this.a.n();
    }

    public String getPackageName() {
        return this.a.o();
    }

    public Object getMediaController() {
        return this.a.p();
    }
}
