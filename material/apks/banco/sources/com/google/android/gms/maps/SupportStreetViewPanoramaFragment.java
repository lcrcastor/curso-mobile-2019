package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.internal.zzai;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class SupportStreetViewPanoramaFragment extends Fragment {
    private final zzb a = new zzb(this);
    private StreetViewPanorama b;

    static class zza implements StreetViewLifecycleDelegate {
        private final Fragment a;
        private final IStreetViewPanoramaFragmentDelegate b;

        public zza(Fragment fragment, IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate) {
            this.b = (IStreetViewPanoramaFragmentDelegate) zzac.zzy(iStreetViewPanoramaFragmentDelegate);
            this.a = (Fragment) zzac.zzy(fragment);
        }

        public IStreetViewPanoramaFragmentDelegate a() {
            return this.b;
        }

        public void getStreetViewPanoramaAsync(final OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            try {
                this.b.getStreetViewPanoramaAsync(new com.google.android.gms.maps.internal.zzaf.zza() {
                    public void zza(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) {
                        onStreetViewPanoramaReadyCallback.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onCreate(Bundle bundle) {
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
            Bundle arguments = this.a.getArguments();
            if (arguments != null && arguments.containsKey("StreetViewPanoramaOptions")) {
                zzah.zza(bundle, "StreetViewPanoramaOptions", arguments.getParcelable("StreetViewPanoramaOptions"));
            }
            this.b.onCreate(bundle);
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) zze.zzae(this.b.onCreateView(zze.zzac(layoutInflater), zze.zzac(viewGroup), bundle));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroy() {
            try {
                this.b.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            try {
                this.b.onDestroyView();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.b.onInflate(zze.zzac(activity), null, bundle2);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onLowMemory() {
            try {
                this.b.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onPause() {
            try {
                this.b.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onResume() {
            try {
                this.b.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.b.onSaveInstanceState(bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onStart() {
        }

        public void onStop() {
        }
    }

    static class zzb extends com.google.android.gms.dynamic.zza<zza> {
        protected zzf<zza> a;
        private final Fragment b;
        private Activity c;
        private final List<OnStreetViewPanoramaReadyCallback> d = new ArrayList();

        zzb(Fragment fragment) {
            this.b = fragment;
        }

        /* access modifiers changed from: private */
        public void a(Activity activity) {
            this.c = activity;
            a();
        }

        public void a() {
            if (!(this.c == null || this.a == null || zzbdt() != null)) {
                try {
                    MapsInitializer.initialize(this.c);
                    this.a.zza(new zza(this.b, zzai.zzdp(this.c).zzai(zze.zzac(this.c))));
                    for (OnStreetViewPanoramaReadyCallback streetViewPanoramaAsync : this.d) {
                        ((zza) zzbdt()).getStreetViewPanoramaAsync(streetViewPanoramaAsync);
                    }
                    this.d.clear();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException unused) {
                }
            }
        }

        public void a(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            if (zzbdt() != null) {
                ((zza) zzbdt()).getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
            } else {
                this.d.add(onStreetViewPanoramaReadyCallback);
            }
        }

        /* access modifiers changed from: protected */
        public void zza(zzf<zza> zzf) {
            this.a = zzf;
            a();
        }
    }

    public static SupportStreetViewPanoramaFragment newInstance() {
        return new SupportStreetViewPanoramaFragment();
    }

    public static SupportStreetViewPanoramaFragment newInstance(StreetViewPanoramaOptions streetViewPanoramaOptions) {
        SupportStreetViewPanoramaFragment supportStreetViewPanoramaFragment = new SupportStreetViewPanoramaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("StreetViewPanoramaOptions", streetViewPanoramaOptions);
        supportStreetViewPanoramaFragment.setArguments(bundle);
        return supportStreetViewPanoramaFragment;
    }

    @Deprecated
    public final StreetViewPanorama getStreetViewPanorama() {
        IStreetViewPanoramaFragmentDelegate zzbrx = zzbrx();
        if (zzbrx == null) {
            return null;
        }
        try {
            IStreetViewPanoramaDelegate streetViewPanorama = zzbrx.getStreetViewPanorama();
            if (streetViewPanorama == null) {
                return null;
            }
            if (this.b == null || this.b.a().asBinder() != streetViewPanorama.asBinder()) {
                this.b = new StreetViewPanorama(streetViewPanorama);
            }
            return this.b;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        zzac.zzhq("getStreetViewPanoramaAsync() must be called on the main thread");
        this.a.a(onStreetViewPanoramaReadyCallback);
    }

    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.a.a(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.a.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        this.a.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.a.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        this.a.a(activity);
        this.a.onInflate(activity, new Bundle(), bundle);
    }

    public void onLowMemory() {
        this.a.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.a.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.a.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.a.onSaveInstanceState(bundle);
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    /* access modifiers changed from: protected */
    public IStreetViewPanoramaFragmentDelegate zzbrx() {
        this.a.a();
        if (this.a.zzbdt() == null) {
            return null;
        }
        return ((zza) this.a.zzbdt()).a();
    }
}
