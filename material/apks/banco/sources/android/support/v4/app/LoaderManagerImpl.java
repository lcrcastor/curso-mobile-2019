package android.support.v4.app;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.arch.lifecycle.ViewModelStore;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl extends LoaderManager {
    static boolean a = false;
    @NonNull
    private final LifecycleOwner b;
    @NonNull
    private final LoaderViewModel c;
    private boolean d;

    public static class LoaderInfo<D> extends MutableLiveData<D> implements OnLoadCompleteListener<D> {
        private final int a;
        @Nullable
        private final Bundle b;
        @NonNull
        private final Loader<D> c;
        private LifecycleOwner d;
        private LoaderObserver<D> e;

        LoaderInfo(int i, @Nullable Bundle bundle, @NonNull Loader<D> loader) {
            this.a = i;
            this.b = bundle;
            this.c = loader;
            this.c.registerListener(i, this);
        }

        /* access modifiers changed from: 0000 */
        @NonNull
        public Loader<D> b() {
            return this.c;
        }

        /* access modifiers changed from: protected */
        public void onActive() {
            if (LoaderManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("  Starting: ");
                sb.append(this);
                Log.v("LoaderManager", sb.toString());
            }
            this.c.startLoading();
        }

        /* access modifiers changed from: protected */
        public void onInactive() {
            if (LoaderManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("  Stopping: ");
                sb.append(this);
                Log.v("LoaderManager", sb.toString());
            }
            this.c.stopLoading();
        }

        /* access modifiers changed from: 0000 */
        @MainThread
        @NonNull
        public Loader<D> a(@NonNull LifecycleOwner lifecycleOwner, @NonNull LoaderCallbacks<D> loaderCallbacks) {
            LoaderObserver<D> loaderObserver = new LoaderObserver<>(this.c, loaderCallbacks);
            observe(lifecycleOwner, loaderObserver);
            if (this.e != null) {
                removeObserver(this.e);
            }
            this.d = lifecycleOwner;
            this.e = loaderObserver;
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            LifecycleOwner lifecycleOwner = this.d;
            LoaderObserver<D> loaderObserver = this.e;
            if (lifecycleOwner != null && loaderObserver != null) {
                removeObserver(loaderObserver);
                observe(lifecycleOwner, loaderObserver);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            boolean z = false;
            if (!hasActiveObservers()) {
                return false;
            }
            if (this.e != null && !this.e.a()) {
                z = true;
            }
            return z;
        }

        public void removeObserver(@NonNull Observer<D> observer) {
            super.removeObserver(observer);
            this.d = null;
            this.e = null;
        }

        /* access modifiers changed from: 0000 */
        @MainThread
        public void e() {
            if (LoaderManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("  Destroying: ");
                sb.append(this);
                Log.v("LoaderManager", sb.toString());
            }
            this.c.cancelLoad();
            this.c.abandon();
            LoaderObserver<D> loaderObserver = this.e;
            if (loaderObserver != null) {
                removeObserver(loaderObserver);
                loaderObserver.b();
            }
            this.c.unregisterListener(this);
            this.c.reset();
        }

        public void onLoadComplete(@NonNull Loader<D> loader, @Nullable D d2) {
            if (LoaderManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("onLoadComplete: ");
                sb.append(this);
                Log.v("LoaderManager", sb.toString());
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                setValue(d2);
                return;
            }
            if (LoaderManagerImpl.a) {
                Log.w("LoaderManager", "onLoadComplete was incorrectly called on a background thread");
            }
            postValue(d2);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.a);
            sb.append(" : ");
            DebugUtils.buildShortClassTag(this.c, sb);
            sb.append("}}");
            return sb.toString();
        }

        public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mId=");
            printWriter.print(this.a);
            printWriter.print(" mArgs=");
            printWriter.println(this.b);
            printWriter.print(str);
            printWriter.print("mLoader=");
            printWriter.println(this.c);
            Loader<D> loader = this.c;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("  ");
            loader.dump(sb.toString(), fileDescriptor, printWriter, strArr);
            if (this.e != null) {
                printWriter.print(str);
                printWriter.print("mCallbacks=");
                printWriter.println(this.e);
                LoaderObserver<D> loaderObserver = this.e;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("  ");
                loaderObserver.a(sb2.toString(), printWriter);
            }
            printWriter.print(str);
            printWriter.print("mData=");
            printWriter.println(b().dataToString(getValue()));
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.println(hasActiveObservers());
        }
    }

    static class LoaderObserver<D> implements Observer<D> {
        @NonNull
        private final Loader<D> a;
        @NonNull
        private final LoaderCallbacks<D> b;
        private boolean c = false;

        LoaderObserver(@NonNull Loader<D> loader, @NonNull LoaderCallbacks<D> loaderCallbacks) {
            this.a = loader;
            this.b = loaderCallbacks;
        }

        public void onChanged(@Nullable D d) {
            if (LoaderManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("  onLoadFinished in ");
                sb.append(this.a);
                sb.append(": ");
                sb.append(this.a.dataToString(d));
                Log.v("LoaderManager", sb.toString());
            }
            this.b.onLoadFinished(this.a, d);
            this.c = true;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        @MainThread
        public void b() {
            if (this.c) {
                if (LoaderManagerImpl.a) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("  Resetting: ");
                    sb.append(this.a);
                    Log.v("LoaderManager", sb.toString());
                }
                this.b.onLoaderReset(this.a);
            }
        }

        public String toString() {
            return this.b.toString();
        }

        public void a(String str, PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("mDeliveredData=");
            printWriter.println(this.c);
        }
    }

    static class LoaderViewModel extends ViewModel {
        private static final Factory a = new Factory() {
            @NonNull
            public <T extends ViewModel> T create(@NonNull Class<T> cls) {
                return new LoaderViewModel();
            }
        };
        private SparseArrayCompat<LoaderInfo> b = new SparseArrayCompat<>();

        LoaderViewModel() {
        }

        @NonNull
        static LoaderViewModel a(ViewModelStore viewModelStore) {
            return (LoaderViewModel) new ViewModelProvider(viewModelStore, a).get(LoaderViewModel.class);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, @NonNull LoaderInfo loaderInfo) {
            this.b.put(i, loaderInfo);
        }

        /* access modifiers changed from: 0000 */
        public <D> LoaderInfo<D> a(int i) {
            return (LoaderInfo) this.b.get(i);
        }

        /* access modifiers changed from: 0000 */
        public void b(int i) {
            this.b.remove(i);
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                if (((LoaderInfo) this.b.valueAt(i)).d()) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                ((LoaderInfo) this.b.valueAt(i)).c();
            }
        }

        /* access modifiers changed from: protected */
        public void onCleared() {
            super.onCleared();
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                ((LoaderInfo) this.b.valueAt(i)).e();
            }
            this.b.clear();
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (this.b.size() > 0) {
                printWriter.print(str);
                printWriter.println("Loaders:");
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("    ");
                String sb2 = sb.toString();
                for (int i = 0; i < this.b.size(); i++) {
                    LoaderInfo loaderInfo = (LoaderInfo) this.b.valueAt(i);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(this.b.keyAt(i));
                    printWriter.print(": ");
                    printWriter.println(loaderInfo.toString());
                    loaderInfo.dump(sb2, fileDescriptor, printWriter, strArr);
                }
            }
        }
    }

    LoaderManagerImpl(@NonNull LifecycleOwner lifecycleOwner, @NonNull ViewModelStore viewModelStore) {
        this.b = lifecycleOwner;
        this.c = LoaderViewModel.a(viewModelStore);
    }

    /* JADX INFO: finally extract failed */
    @MainThread
    @NonNull
    private <D> Loader<D> a(int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks<D> loaderCallbacks) {
        try {
            this.d = true;
            Loader onCreateLoader = loaderCallbacks.onCreateLoader(i, bundle);
            if (!onCreateLoader.getClass().isMemberClass() || Modifier.isStatic(onCreateLoader.getClass().getModifiers())) {
                LoaderInfo loaderInfo = new LoaderInfo(i, bundle, onCreateLoader);
                if (a) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("  Created new loader ");
                    sb.append(loaderInfo);
                    Log.v("LoaderManager", sb.toString());
                }
                this.c.a(i, loaderInfo);
                this.d = false;
                return loaderInfo.a(this.b, loaderCallbacks);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Object returned from onCreateLoader must not be a non-static inner member class: ");
            sb2.append(onCreateLoader);
            throw new IllegalArgumentException(sb2.toString());
        } catch (Throwable th) {
            this.d = false;
            throw th;
        }
    }

    @MainThread
    @NonNull
    public <D> Loader<D> initLoader(int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks<D> loaderCallbacks) {
        if (this.d) {
            throw new IllegalStateException("Called while creating a loader");
        } else if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("initLoader must be called on the main thread");
        } else {
            LoaderInfo a2 = this.c.a(i);
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append("initLoader in ");
                sb.append(this);
                sb.append(": args=");
                sb.append(bundle);
                Log.v("LoaderManager", sb.toString());
            }
            if (a2 == null) {
                return a(i, bundle, loaderCallbacks);
            }
            if (a) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("  Re-using existing loader ");
                sb2.append(a2);
                Log.v("LoaderManager", sb2.toString());
            }
            return a2.a(this.b, loaderCallbacks);
        }
    }

    @MainThread
    @NonNull
    public <D> Loader<D> restartLoader(int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks<D> loaderCallbacks) {
        if (this.d) {
            throw new IllegalStateException("Called while creating a loader");
        } else if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("restartLoader must be called on the main thread");
        } else {
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append("restartLoader in ");
                sb.append(this);
                sb.append(": args=");
                sb.append(bundle);
                Log.v("LoaderManager", sb.toString());
            }
            destroyLoader(i);
            return a(i, bundle, loaderCallbacks);
        }
    }

    @MainThread
    public void destroyLoader(int i) {
        if (this.d) {
            throw new IllegalStateException("Called while creating a loader");
        } else if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("destroyLoader must be called on the main thread");
        } else {
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append("destroyLoader in ");
                sb.append(this);
                sb.append(" of ");
                sb.append(i);
                Log.v("LoaderManager", sb.toString());
            }
            LoaderInfo a2 = this.c.a(i);
            if (a2 != null) {
                a2.e();
                this.c.b(i);
            }
        }
    }

    @Nullable
    public <D> Loader<D> getLoader(int i) {
        if (this.d) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo a2 = this.c.a(i);
        if (a2 != null) {
            return a2.b();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.c.b();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        DebugUtils.buildShortClassTag(this.b, sb);
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.c.a(str, fileDescriptor, printWriter, strArr);
    }

    public boolean hasRunningLoaders() {
        return this.c.a();
    }
}
