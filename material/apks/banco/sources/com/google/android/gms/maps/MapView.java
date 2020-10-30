package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzai;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class MapView extends FrameLayout {
    private final zzb a;

    static class zza implements MapLifecycleDelegate {
        private final ViewGroup a;
        private final IMapViewDelegate b;
        private View c;

        public zza(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.b = (IMapViewDelegate) zzac.zzy(iMapViewDelegate);
            this.a = (ViewGroup) zzac.zzy(viewGroup);
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
            try {
                this.b.onCreate(bundle);
                this.c = (View) zze.zzae(this.b.getView());
                this.a.removeAllViews();
                this.a.addView(this.c);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        public void onDestroy() {
            try {
                this.b.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
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
        private final ViewGroup b;
        private final Context c;
        private final GoogleMapOptions d;
        private final List<OnMapReadyCallback> e = new ArrayList();

        zzb(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.b = viewGroup;
            this.c = context;
            this.d = googleMapOptions;
        }

        public void a() {
            if (this.a != null && zzbdt() == null) {
                try {
                    MapsInitializer.initialize(this.c);
                    IMapViewDelegate zza = zzai.zzdp(this.c).zza(zze.zzac(this.c), this.d);
                    if (zza != null) {
                        this.a.zza(new zza(this.b, zza));
                        for (OnMapReadyCallback mapAsync : this.e) {
                            ((zza) zzbdt()).getMapAsync(mapAsync);
                        }
                        this.e.clear();
                    }
                } catch (RemoteException e2) {
                    throw new RuntimeRemoteException(e2);
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
                this.e.add(onMapReadyCallback);
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

    public MapView(Context context) {
        super(context);
        this.a = new zzb(this, context, null);
        a();
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        a();
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        a();
    }

    public MapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context);
        this.a = new zzb(this, context, googleMapOptions);
        a();
    }

    private void a() {
        setClickable(true);
    }

    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        zzac.zzhq("getMapAsync() must be called on the main thread");
        this.a.a(onMapReadyCallback);
    }

    public final void onCreate(Bundle bundle) {
        this.a.onCreate(bundle);
        if (this.a.zzbdt() == null) {
            com.google.android.gms.dynamic.zza.zzb(this);
        }
    }

    public final void onDestroy() {
        this.a.onDestroy();
    }

    public final void onEnterAmbient(Bundle bundle) {
        zzac.zzhq("onEnterAmbient() must be called on the main thread");
        this.a.a(bundle);
    }

    public final void onExitAmbient() {
        zzac.zzhq("onExitAmbient() must be called on the main thread");
        this.a.b();
    }

    public final void onLowMemory() {
        this.a.onLowMemory();
    }

    public final void onPause() {
        this.a.onPause();
    }

    public final void onResume() {
        this.a.onResume();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.a.onSaveInstanceState(bundle);
    }

    public final void onStart() {
        this.a.onStart();
    }

    public final void onStop() {
        this.a.onStop();
    }
}
