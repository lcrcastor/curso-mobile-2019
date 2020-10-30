package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public class PeriodicTask extends Task {
    public static final Creator<PeriodicTask> CREATOR = new Creator<PeriodicTask>() {
        /* renamed from: a */
        public PeriodicTask createFromParcel(Parcel parcel) {
            return new PeriodicTask(parcel);
        }

        /* renamed from: a */
        public PeriodicTask[] newArray(int i) {
            return new PeriodicTask[i];
        }
    };
    protected long mFlexInSeconds;
    protected long mIntervalInSeconds;

    public static class Builder extends com.google.android.gms.gcm.Task.Builder {
        /* access modifiers changed from: private */
        public long a;
        /* access modifiers changed from: private */
        public long b;

        public Builder() {
            this.a = -1;
            this.b = -1;
            this.isPersisted = true;
        }

        public PeriodicTask build() {
            checkConditions();
            return new PeriodicTask(this);
        }

        /* access modifiers changed from: protected */
        public void checkConditions() {
            long j;
            super.checkConditions();
            if (this.a == -1) {
                throw new IllegalArgumentException("Must call setPeriod(long) to establish an execution interval for this periodic task.");
            } else if (this.a <= 0) {
                long j2 = this.a;
                StringBuilder sb = new StringBuilder(66);
                sb.append("Period set cannot be less than or equal to 0: ");
                sb.append(j2);
                throw new IllegalArgumentException(sb.toString());
            } else {
                if (this.b == -1) {
                    j = (long) (((float) this.a) * 0.1f);
                } else if (this.b > this.a) {
                    j = this.a;
                } else {
                    return;
                }
                this.b = j;
            }
        }

        public Builder setExtras(Bundle bundle) {
            this.extras = bundle;
            return this;
        }

        public Builder setFlex(long j) {
            this.b = j;
            return this;
        }

        public Builder setPeriod(long j) {
            this.a = j;
            return this;
        }

        public Builder setPersisted(boolean z) {
            this.isPersisted = z;
            return this;
        }

        public Builder setRequiredNetwork(int i) {
            this.requiredNetworkState = i;
            return this;
        }

        public Builder setRequiresCharging(boolean z) {
            this.requiresCharging = z;
            return this;
        }

        public Builder setService(Class<? extends GcmTaskService> cls) {
            this.gcmTaskService = cls.getName();
            return this;
        }

        public Builder setTag(String str) {
            this.tag = str;
            return this;
        }

        public Builder setUpdateCurrent(boolean z) {
            this.updateCurrent = z;
            return this;
        }
    }

    @Deprecated
    private PeriodicTask(Parcel parcel) {
        super(parcel);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = parcel.readLong();
        this.mFlexInSeconds = Math.min(parcel.readLong(), this.mIntervalInSeconds);
    }

    private PeriodicTask(Builder builder) {
        super((com.google.android.gms.gcm.Task.Builder) builder);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = builder.a;
        this.mFlexInSeconds = Math.min(builder.b, this.mIntervalInSeconds);
    }

    public long getFlex() {
        return this.mFlexInSeconds;
    }

    public long getPeriod() {
        return this.mIntervalInSeconds;
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putLong("period", this.mIntervalInSeconds);
        bundle.putLong("period_flex", this.mFlexInSeconds);
    }

    public String toString() {
        String valueOf = String.valueOf(super.toString());
        long period = getPeriod();
        long flex = getFlex();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 54);
        sb.append(valueOf);
        sb.append(" period=");
        sb.append(period);
        sb.append(" flex=");
        sb.append(flex);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeLong(this.mIntervalInSeconds);
        parcel.writeLong(this.mFlexInSeconds);
    }
}
