package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    /* access modifiers changed from: private */
    public T a;
    /* access modifiers changed from: private */
    public Bundle b;
    /* access modifiers changed from: private */
    public LinkedList<C0015zza> c;
    private final zzf<T> d = new zzf<T>() {
        public void zza(T t) {
            zza.this.a = t;
            Iterator it = zza.this.c.iterator();
            while (it.hasNext()) {
                ((C0015zza) it.next()).a(zza.this.a);
            }
            zza.this.c.clear();
            zza.this.b = null;
        }
    };

    /* renamed from: com.google.android.gms.dynamic.zza$zza reason: collision with other inner class name */
    interface C0015zza {
        int a();

        void a(LifecycleDelegate lifecycleDelegate);
    }

    private void a(int i) {
        while (!this.c.isEmpty() && ((C0015zza) this.c.getLast()).a() >= i) {
            this.c.removeLast();
        }
    }

    private void a(Bundle bundle, C0015zza zza) {
        if (this.a != null) {
            zza.a(this.a);
            return;
        }
        if (this.c == null) {
            this.c = new LinkedList<>();
        }
        this.c.add(zza);
        if (bundle != null) {
            if (this.b == null) {
                this.b = (Bundle) bundle.clone();
            } else {
                this.b.putAll(bundle);
            }
        }
        zza(this.d);
    }

    public static void zzb(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        String zzi = zzi.zzi(context, isGooglePlayServicesAvailable);
        String zzk = zzi.zzk(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzi);
        linearLayout.addView(textView);
        if (zzk != null) {
            Button button = new Button(context);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzk);
            linearLayout.addView(button);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    context.startActivity(GooglePlayServicesUtil.zzfm(isGooglePlayServicesAvailable));
                }
            });
        }
    }

    public void onCreate(final Bundle bundle) {
        a(bundle, (C0015zza) new C0015zza() {
            public int a() {
                return 1;
            }

            public void a(LifecycleDelegate lifecycleDelegate) {
                zza.this.a.onCreate(bundle);
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        final FrameLayout frameLayout2 = frameLayout;
        final LayoutInflater layoutInflater2 = layoutInflater;
        final ViewGroup viewGroup2 = viewGroup;
        final Bundle bundle2 = bundle;
        AnonymousClass4 r0 = new C0015zza() {
            public int a() {
                return 2;
            }

            public void a(LifecycleDelegate lifecycleDelegate) {
                frameLayout2.removeAllViews();
                frameLayout2.addView(zza.this.a.onCreateView(layoutInflater2, viewGroup2, bundle2));
            }
        };
        a(bundle, (C0015zza) r0);
        if (this.a == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.a != null) {
            this.a.onDestroy();
        } else {
            a(1);
        }
    }

    public void onDestroyView() {
        if (this.a != null) {
            this.a.onDestroyView();
        } else {
            a(2);
        }
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        a(bundle2, (C0015zza) new C0015zza() {
            public int a() {
                return 0;
            }

            public void a(LifecycleDelegate lifecycleDelegate) {
                zza.this.a.onInflate(activity, bundle, bundle2);
            }
        });
    }

    public void onLowMemory() {
        if (this.a != null) {
            this.a.onLowMemory();
        }
    }

    public void onPause() {
        if (this.a != null) {
            this.a.onPause();
        } else {
            a(5);
        }
    }

    public void onResume() {
        a((Bundle) null, (C0015zza) new C0015zza() {
            public int a() {
                return 5;
            }

            public void a(LifecycleDelegate lifecycleDelegate) {
                zza.this.a.onResume();
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.a != null) {
            this.a.onSaveInstanceState(bundle);
            return;
        }
        if (this.b != null) {
            bundle.putAll(this.b);
        }
    }

    public void onStart() {
        a((Bundle) null, (C0015zza) new C0015zza() {
            public int a() {
                return 4;
            }

            public void a(LifecycleDelegate lifecycleDelegate) {
                zza.this.a.onStart();
            }
        });
    }

    public void onStop() {
        if (this.a != null) {
            this.a.onStop();
        } else {
            a(4);
        }
    }

    /* access modifiers changed from: protected */
    public void zza(FrameLayout frameLayout) {
        zzb(frameLayout);
    }

    public abstract void zza(zzf<T> zzf);

    public T zzbdt() {
        return this.a;
    }
}
