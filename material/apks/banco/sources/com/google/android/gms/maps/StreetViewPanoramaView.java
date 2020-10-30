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
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzai;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class StreetViewPanoramaView extends FrameLayout {
    private final zzb a;
    private StreetViewPanorama b;

    static class zza implements StreetViewLifecycleDelegate {
        private final ViewGroup a;
        private final IStreetViewPanoramaViewDelegate b;
        private View c;

        public zza(ViewGroup viewGroup, IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate) {
            this.b = (IStreetViewPanoramaViewDelegate) zzac.zzy(iStreetViewPanoramaViewDelegate);
            this.a = (ViewGroup) zzac.zzy(viewGroup);
        }

        public IStreetViewPanoramaViewDelegate a() {
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
            throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
        }

        public void onDestroy() {
            try {
                this.b.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
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
        private final ViewGroup b;
        private final Context c;
        private final StreetViewPanoramaOptions d;
        private final List<OnStreetViewPanoramaReadyCallback> e = new ArrayList();

        zzb(ViewGroup viewGroup, Context context, StreetViewPanoramaOptions streetViewPanoramaOptions) {
            this.b = viewGroup;
            this.c = context;
            this.d = streetViewPanoramaOptions;
        }

        public void a() {
            if (this.a != null && zzbdt() == null) {
                try {
                    this.a.zza(new zza(this.b, zzai.zzdp(this.c).zza(zze.zzac(this.c), this.d)));
                    for (OnStreetViewPanoramaReadyCallback streetViewPanoramaAsync : this.e) {
                        ((zza) zzbdt()).getStreetViewPanoramaAsync(streetViewPanoramaAsync);
                    }
                    this.e.clear();
                } catch (RemoteException e2) {
                    throw new RuntimeRemoteException(e2);
                } catch (GooglePlayServicesNotAvailableException unused) {
                }
            }
        }

        public void a(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            if (zzbdt() != null) {
                ((zza) zzbdt()).getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
            } else {
                this.e.add(onStreetViewPanoramaReadyCallback);
            }
        }

        /* access modifiers changed from: protected */
        public void zza(zzf<zza> zzf) {
            this.a = zzf;
            a();
        }
    }

    public StreetViewPanoramaView(Context context) {
        super(context);
        this.a = new zzb(this, context, null);
    }

    public StreetViewPanoramaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new zzb(this, context, null);
    }

    public StreetViewPanoramaView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new zzb(this, context, null);
    }

    public StreetViewPanoramaView(Context context, StreetViewPanoramaOptions streetViewPanoramaOptions) {
        super(context);
        this.a = new zzb(this, context, streetViewPanoramaOptions);
    }

    @Deprecated
    public final StreetViewPanorama getStreetViewPanorama() {
        if (this.b != null) {
            return this.b;
        }
        this.a.a();
        if (this.a.zzbdt() == null) {
            return null;
        }
        try {
            this.b = new StreetViewPanorama(((zza) this.a.zzbdt()).a().getStreetViewPanorama());
            return this.b;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        zzac.zzhq("getStreetViewPanoramaAsync() must be called on the main thread");
        this.a.a(onStreetViewPanoramaReadyCallback);
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
}
