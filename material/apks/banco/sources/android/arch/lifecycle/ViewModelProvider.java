package android.arch.lifecycle;

import android.app.Application;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;

public class ViewModelProvider {
    private final Factory a;
    private final ViewModelStore b;

    public static class AndroidViewModelFactory extends NewInstanceFactory {
        private static AndroidViewModelFactory a;
        private Application b;

        public static AndroidViewModelFactory getInstance(@NonNull Application application) {
            if (a == null) {
                a = new AndroidViewModelFactory(application);
            }
            return a;
        }

        public AndroidViewModelFactory(@NonNull Application application) {
            this.b = application;
        }

        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            if (!AndroidViewModel.class.isAssignableFrom(cls)) {
                return super.create(cls);
            }
            try {
                return (ViewModel) cls.getConstructor(new Class[]{Application.class}).newInstance(new Object[]{this.b});
            } catch (NoSuchMethodException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot create an instance of ");
                sb.append(cls);
                throw new RuntimeException(sb.toString(), e);
            } catch (IllegalAccessException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Cannot create an instance of ");
                sb2.append(cls);
                throw new RuntimeException(sb2.toString(), e2);
            } catch (InstantiationException e3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Cannot create an instance of ");
                sb3.append(cls);
                throw new RuntimeException(sb3.toString(), e3);
            } catch (InvocationTargetException e4) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Cannot create an instance of ");
                sb4.append(cls);
                throw new RuntimeException(sb4.toString(), e4);
            }
        }
    }

    public interface Factory {
        @NonNull
        <T extends ViewModel> T create(@NonNull Class<T> cls);
    }

    public static class NewInstanceFactory implements Factory {
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            try {
                return (ViewModel) cls.newInstance();
            } catch (InstantiationException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot create an instance of ");
                sb.append(cls);
                throw new RuntimeException(sb.toString(), e);
            } catch (IllegalAccessException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Cannot create an instance of ");
                sb2.append(cls);
                throw new RuntimeException(sb2.toString(), e2);
            }
        }
    }

    public ViewModelProvider(@NonNull ViewModelStoreOwner viewModelStoreOwner, @NonNull Factory factory) {
        this(viewModelStoreOwner.getViewModelStore(), factory);
    }

    public ViewModelProvider(@NonNull ViewModelStore viewModelStore, @NonNull Factory factory) {
        this.a = factory;
        this.b = viewModelStore;
    }

    @NonNull
    public <T extends ViewModel> T get(@NonNull Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("android.arch.lifecycle.ViewModelProvider.DefaultKey:");
        sb.append(canonicalName);
        return get(sb.toString(), cls);
    }

    @MainThread
    @NonNull
    public <T extends ViewModel> T get(@NonNull String str, @NonNull Class<T> cls) {
        T a2 = this.b.a(str);
        if (cls.isInstance(a2)) {
            return a2;
        }
        T create = this.a.create(cls);
        this.b.a(str, create);
        return create;
    }
}
