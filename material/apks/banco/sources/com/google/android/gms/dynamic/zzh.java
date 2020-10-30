package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

public final class zzh extends zza {
    private Fragment a;

    private zzh(Fragment fragment) {
        this.a = fragment;
    }

    public static zzh zza(Fragment fragment) {
        if (fragment != null) {
            return new zzh(fragment);
        }
        return null;
    }

    public Bundle getArguments() {
        return this.a.getArguments();
    }

    public int getId() {
        return this.a.getId();
    }

    public boolean getRetainInstance() {
        return this.a.getRetainInstance();
    }

    public String getTag() {
        return this.a.getTag();
    }

    public int getTargetRequestCode() {
        return this.a.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() {
        return this.a.getUserVisibleHint();
    }

    public zzd getView() {
        return zze.zzac(this.a.getView());
    }

    public boolean isAdded() {
        return this.a.isAdded();
    }

    public boolean isDetached() {
        return this.a.isDetached();
    }

    public boolean isHidden() {
        return this.a.isHidden();
    }

    public boolean isInLayout() {
        return this.a.isInLayout();
    }

    public boolean isRemoving() {
        return this.a.isRemoving();
    }

    public boolean isResumed() {
        return this.a.isResumed();
    }

    public boolean isVisible() {
        return this.a.isVisible();
    }

    public void setHasOptionsMenu(boolean z) {
        this.a.setHasOptionsMenu(z);
    }

    public void setMenuVisibility(boolean z) {
        this.a.setMenuVisibility(z);
    }

    public void setRetainInstance(boolean z) {
        this.a.setRetainInstance(z);
    }

    public void setUserVisibleHint(boolean z) {
        this.a.setUserVisibleHint(z);
    }

    public void startActivity(Intent intent) {
        this.a.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int i) {
        this.a.startActivityForResult(intent, i);
    }

    public void zzac(zzd zzd) {
        this.a.registerForContextMenu((View) zze.zzae(zzd));
    }

    public void zzad(zzd zzd) {
        this.a.unregisterForContextMenu((View) zze.zzae(zzd));
    }

    public zzd zzbdu() {
        return zze.zzac(this.a.getActivity());
    }

    public zzc zzbdv() {
        return zza(this.a.getParentFragment());
    }

    public zzd zzbdw() {
        return zze.zzac(this.a.getResources());
    }

    public zzc zzbdx() {
        return zza(this.a.getTargetFragment());
    }
}
