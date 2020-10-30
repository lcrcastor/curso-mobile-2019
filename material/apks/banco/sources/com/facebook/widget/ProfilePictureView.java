package com.facebook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.android.R;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageRequest.Builder;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.ImageResponse;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.net.URISyntaxException;

public class ProfilePictureView extends FrameLayout {
    public static final int CUSTOM = -1;
    public static final int LARGE = -4;
    public static final int NORMAL = -3;
    public static final int SMALL = -2;
    public static final String TAG = "ProfilePictureView";
    private String a;
    private int b = 0;
    private int c = 0;
    private boolean d = true;
    private Bitmap e;
    private ImageView f;
    private int g = -1;
    private ImageRequest h;
    private OnErrorListener i;
    private Bitmap j = null;

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    public ProfilePictureView(Context context) {
        super(context);
        a(context);
    }

    public ProfilePictureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
        a(attributeSet);
    }

    public ProfilePictureView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context);
        a(attributeSet);
    }

    public final int getPresetSize() {
        return this.g;
    }

    public final void setPresetSize(int i2) {
        switch (i2) {
            case -4:
            case -3:
            case -2:
            case -1:
                this.g = i2;
                requestLayout();
                return;
            default:
                throw new IllegalArgumentException("Must use a predefined preset size");
        }
    }

    public final boolean isCropped() {
        return this.d;
    }

    public final void setCropped(boolean z) {
        this.d = z;
        a(false);
    }

    public final String getProfileId() {
        return this.a;
    }

    public final void setProfileId(String str) {
        boolean z;
        if (Utility.isNullOrEmpty(this.a) || !this.a.equalsIgnoreCase(str)) {
            a();
            z = true;
        } else {
            z = false;
        }
        this.a = str;
        a(z);
    }

    public final OnErrorListener getOnErrorListener() {
        return this.i;
    }

    public final void setOnErrorListener(OnErrorListener onErrorListener) {
        this.i = onErrorListener;
    }

    public final void setDefaultProfilePicture(Bitmap bitmap) {
        this.j = bitmap;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        boolean z;
        LayoutParams layoutParams = getLayoutParams();
        int size = MeasureSpec.getSize(i3);
        int size2 = MeasureSpec.getSize(i2);
        if (MeasureSpec.getMode(i3) == 1073741824 || layoutParams.height != -2) {
            z = false;
        } else {
            size = c(true);
            i3 = MeasureSpec.makeMeasureSpec(size, 1073741824);
            z = true;
        }
        if (MeasureSpec.getMode(i2) != 1073741824 && layoutParams.width == -2) {
            size2 = c(true);
            i2 = MeasureSpec.makeMeasureSpec(size2, 1073741824);
            z = true;
        }
        if (z) {
            setMeasuredDimension(size2, size);
            measureChildren(i2, i3);
            return;
        }
        super.onMeasure(i2, i3);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        a(false);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ProfilePictureView_superState", onSaveInstanceState);
        bundle.putString("ProfilePictureView_profileId", this.a);
        bundle.putInt("ProfilePictureView_presetSize", this.g);
        bundle.putBoolean("ProfilePictureView_isCropped", this.d);
        bundle.putParcelable("ProfilePictureView_bitmap", this.e);
        bundle.putInt("ProfilePictureView_width", this.c);
        bundle.putInt("ProfilePictureView_height", this.b);
        bundle.putBoolean("ProfilePictureView_refresh", this.h != null);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable.getClass() != Bundle.class) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("ProfilePictureView_superState"));
        this.a = bundle.getString("ProfilePictureView_profileId");
        this.g = bundle.getInt("ProfilePictureView_presetSize");
        this.d = bundle.getBoolean("ProfilePictureView_isCropped");
        this.c = bundle.getInt("ProfilePictureView_width");
        this.b = bundle.getInt("ProfilePictureView_height");
        setImageBitmap((Bitmap) bundle.getParcelable("ProfilePictureView_bitmap"));
        if (bundle.getBoolean("ProfilePictureView_refresh")) {
            a(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.h = null;
    }

    private void a(Context context) {
        removeAllViews();
        this.f = new ImageView(context);
        this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.f.setScaleType(ScaleType.CENTER_INSIDE);
        addView(this.f);
    }

    private void a(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_profile_picture_view);
        setPresetSize(obtainStyledAttributes.getInt(0, -1));
        this.d = obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.recycle();
    }

    private void a(boolean z) {
        boolean b2 = b();
        if (this.a == null || this.a.length() == 0 || (this.c == 0 && this.b == 0)) {
            a();
        } else if (b2 || z) {
            b(true);
        }
    }

    private void a() {
        if (this.j == null) {
            setImageBitmap(BitmapFactory.decodeResource(getResources(), isCropped() ? R.drawable.com_facebook_profile_picture_blank_square : R.drawable.com_facebook_profile_picture_blank_portrait));
            return;
        }
        b();
        setImageBitmap(Bitmap.createScaledBitmap(this.j, this.c, this.b, false));
    }

    private void setImageBitmap(Bitmap bitmap) {
        if (this.f != null && bitmap != null) {
            this.e = bitmap;
            this.f.setImageBitmap(bitmap);
        }
    }

    private void b(boolean z) {
        try {
            ImageRequest build = new Builder(getContext(), ImageRequest.getProfilePictureUrl(this.a, this.c, this.b)).setAllowCachedRedirects(z).setCallerTag(this).setCallback(new Callback() {
                public void onCompleted(ImageResponse imageResponse) {
                    ProfilePictureView.this.a(imageResponse);
                }
            }).build();
            if (this.h != null) {
                ImageDownloader.cancelRequest(this.h);
            }
            this.h = build;
            ImageDownloader.downloadAsync(build);
        } catch (URISyntaxException e2) {
            Logger.log(LoggingBehavior.REQUESTS, 6, TAG, e2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void a(ImageResponse imageResponse) {
        if (imageResponse.getRequest() == this.h) {
            this.h = null;
            Bitmap bitmap = imageResponse.getBitmap();
            Exception error = imageResponse.getError();
            if (error != null) {
                OnErrorListener onErrorListener = this.i;
                if (onErrorListener != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error in downloading profile picture for profileId: ");
                    sb.append(getProfileId());
                    onErrorListener.onError(new FacebookException(sb.toString(), error));
                    return;
                }
                Logger.log(LoggingBehavior.REQUESTS, 6, TAG, error.toString());
            } else if (bitmap != null) {
                setImageBitmap(bitmap);
                if (imageResponse.isCachedRedirect()) {
                    b(false);
                }
            }
        }
    }

    private boolean b() {
        int height = getHeight();
        int width = getWidth();
        boolean z = true;
        if (width < 1 || height < 1) {
            return false;
        }
        int c2 = c(false);
        if (c2 != 0) {
            height = c2;
            width = height;
        }
        if (width <= height) {
            height = isCropped() ? width : 0;
        } else {
            width = isCropped() ? height : 0;
        }
        if (width == this.c && height == this.b) {
            z = false;
        }
        this.c = width;
        this.b = height;
        return z;
    }

    private int c(boolean z) {
        int i2;
        switch (this.g) {
            case -4:
                i2 = R.dimen.com_facebook_profilepictureview_preset_size_large;
                break;
            case -3:
                i2 = R.dimen.com_facebook_profilepictureview_preset_size_normal;
                break;
            case -2:
                i2 = R.dimen.com_facebook_profilepictureview_preset_size_small;
                break;
            case -1:
                if (z) {
                    i2 = R.dimen.com_facebook_profilepictureview_preset_size_normal;
                    break;
                } else {
                    return 0;
                }
            default:
                return 0;
        }
        return getResources().getDimensionPixelSize(i2);
    }
}
