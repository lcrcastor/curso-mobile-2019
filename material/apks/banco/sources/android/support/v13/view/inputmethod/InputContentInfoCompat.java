package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputContentInfo;

public final class InputContentInfoCompat {
    private final InputContentInfoCompatImpl a;

    @RequiresApi(25)
    static final class InputContentInfoCompatApi25Impl implements InputContentInfoCompatImpl {
        @NonNull
        final InputContentInfo a;

        InputContentInfoCompatApi25Impl(@NonNull Object obj) {
            this.a = (InputContentInfo) obj;
        }

        InputContentInfoCompatApi25Impl(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.a = new InputContentInfo(uri, clipDescription, uri2);
        }

        @NonNull
        public Uri a() {
            return this.a.getContentUri();
        }

        @NonNull
        public ClipDescription b() {
            return this.a.getDescription();
        }

        @Nullable
        public Uri c() {
            return this.a.getLinkUri();
        }

        @Nullable
        public Object d() {
            return this.a;
        }

        public void e() {
            this.a.requestPermission();
        }

        public void f() {
            this.a.releasePermission();
        }
    }

    static final class InputContentInfoCompatBaseImpl implements InputContentInfoCompatImpl {
        @NonNull
        private final Uri a;
        @NonNull
        private final ClipDescription b;
        @Nullable
        private final Uri c;

        @Nullable
        public Object d() {
            return null;
        }

        public void e() {
        }

        public void f() {
        }

        InputContentInfoCompatBaseImpl(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.a = uri;
            this.b = clipDescription;
            this.c = uri2;
        }

        @NonNull
        public Uri a() {
            return this.a;
        }

        @NonNull
        public ClipDescription b() {
            return this.b;
        }

        @Nullable
        public Uri c() {
            return this.c;
        }
    }

    interface InputContentInfoCompatImpl {
        @NonNull
        Uri a();

        @NonNull
        ClipDescription b();

        @Nullable
        Uri c();

        @Nullable
        Object d();

        void e();

        void f();
    }

    public InputContentInfoCompat(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
        if (VERSION.SDK_INT >= 25) {
            this.a = new InputContentInfoCompatApi25Impl(uri, clipDescription, uri2);
        } else {
            this.a = new InputContentInfoCompatBaseImpl(uri, clipDescription, uri2);
        }
    }

    private InputContentInfoCompat(@NonNull InputContentInfoCompatImpl inputContentInfoCompatImpl) {
        this.a = inputContentInfoCompatImpl;
    }

    @NonNull
    public Uri getContentUri() {
        return this.a.a();
    }

    @NonNull
    public ClipDescription getDescription() {
        return this.a.b();
    }

    @Nullable
    public Uri getLinkUri() {
        return this.a.c();
    }

    @Nullable
    public static InputContentInfoCompat wrap(@Nullable Object obj) {
        if (obj != null && VERSION.SDK_INT >= 25) {
            return new InputContentInfoCompat(new InputContentInfoCompatApi25Impl(obj));
        }
        return null;
    }

    @Nullable
    public Object unwrap() {
        return this.a.d();
    }

    public void requestPermission() {
        this.a.e();
    }

    public void releasePermission() {
        this.a.f();
    }
}
