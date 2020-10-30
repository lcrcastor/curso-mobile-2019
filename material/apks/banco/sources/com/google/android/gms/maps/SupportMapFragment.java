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
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.internal.zzai;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class SupportMapFragment extends Fragment {
    private final zzb a = new zzb(this);

    static class zza implements MapLifecycleDelegate {
        private final Fragment a;
        private final IMapFragmentDelegate b;

        public zza(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
            this.b = (IMapFragmentDelegate) zzac.zzy(iMapFragmentDelegate);
            this.a = (Fragment) zzac.zzy(fragment);
        }

        public void a() {
            try {
                this.b.onExitAmbient();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void a(Bundle bundle) {
            try {
                this.b.onEnterAmbient(bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void getMapAsync(final OnMapReadyCallback onMapReadyCallback) {
            try {
                this.b.getMapAsync(new com.google.android.gms.maps.internal.zzt.zza() {
                    public void zza(IGoogleMapDelegate iGoogleMapDelegate) {
                        onMapReadyCallback.onMapReady(new GoogleMap(iGoogleMapDelegate));
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
            if (arguments != null && arguments.containsKey("MapOptions")) {
                zzah.zza(bundle, "MapOptions", arguments.getParcelable("MapOptions"));
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
                this.b.onInflate(zze.zzac(activity), (GoogleMapOptions) bundle.getParcelable("MapOptions"), bundle2);
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
            try {
                this.b.onStart();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onStop() {
            try {
                this.b.onStop();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
    }

    static class zzb extends com.google.android.gms.dynamic.zza<zza> {
        protected zzf<zza> a;
        private final Fragment b;
        private Activity c;
        private final List<OnMapReadyCallback> d = new ArrayList();

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
                    IMapFragmentDelegate zzah = zzai.zzdp(this.c).zzah(zze.zzac(this.c));
                    if (zzah != null) {
                        this.a.zza(new zza(this.b, zzah));
                        for (OnMapReadyCallback mapAsync : this.d) {
                            ((zza) zzbdt()).getMapAsync(mapAsync);
                        }
                        this.d.clear();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException unused) {
                }
            }
        }

        public void a(Bundle bundle) {
            if (zzbdt() != null) {
                ((zza) zzbdt()).a(bundle);
            }
        }

        public void a(OnMapReadyCallback onMapReadyCallback) {
            if (zzbdt() != null) {
                ((zza) zzbdt()).getMapAsync(onMapReadyCallback);
            } else {
                this.d.add(onMapReadyCallback);
            }
        }

        public void b() {
            if (zzbdt() != null) {
                ((zza) zzbdt()).a();
            }
        }

        /* access modifiers changed from: protected */
        public void zza(zzf<zza> zzf) {
            this.a = zzf;
            a();
        }
    }

    public static SupportMapFragment newInstance() {
        return new SupportMapFragment();
    }

    public static SupportMapFragment newInstance(GoogleMapOptions googleMapOptions) {
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("MapOptions", googleMapOptions);
        supportMapFragment.setArguments(bundle);
        return supportMapFragment;
    }

    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        zzac.zzhq("getMapAsync must be called on the main thread.");
        this.a.a(onMapReadyCallback);
    }

    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportMapFragment.class.getClassLoader());
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
        View onCreateView = this.a.onCreateView(layoutInflater, viewGroup, bundle);
        onCreateView.setClickable(true);
        return onCreateView;
    }

    public void onDestroy() {
        this.a.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.a.onDestroyView();
        super.onDestroyView();
    }

    public final void onEnterAmbient(Bundle bundle) {
        zzac.zzhq("onEnterAmbient must be called on the main thread.");
        this.a.a(bundle);
    }

    public final void onExitAmbient() {
        zzac.zzhq("onExitAmbient must be called on the main thread.");
        this.a.b();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        this.a.a(activity);
        GoogleMapOptions createFromAttributes = GoogleMapOptions.createFromAttributes(activity, attributeSet);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("MapOptions", createFromAttributes);
        this.a.onInflate(activity, bundle2, bundle);
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
            bundle.setClassLoader(SupportMapFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.a.onSaveInstanceState(bundle);
    }

    public void onStart() {
        super.onStart();
        this.a.onStart();
    }

    public void onStop() {
        this.a.onStop();
        super.onStop();
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }
}
