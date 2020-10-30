package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertController.AlertParams;
import android.support.v7.appcompat.R;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AlertDialog extends AppCompatDialog implements DialogInterface {
    final AlertController a;

    public static class Builder {
        private final AlertParams a;
        private final int b;

        public Builder(@NonNull Context context) {
            this(context, AlertDialog.a(context, 0));
        }

        public Builder(@NonNull Context context, @StyleRes int i) {
            this.a = new AlertParams(new ContextThemeWrapper(context, AlertDialog.a(context, i)));
            this.b = i;
        }

        @NonNull
        public Context getContext() {
            return this.a.mContext;
        }

        public Builder setTitle(@StringRes int i) {
            this.a.mTitle = this.a.mContext.getText(i);
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.a.mTitle = charSequence;
            return this;
        }

        public Builder setCustomTitle(@Nullable View view) {
            this.a.mCustomTitleView = view;
            return this;
        }

        public Builder setMessage(@StringRes int i) {
            this.a.mMessage = this.a.mContext.getText(i);
            return this;
        }

        public Builder setMessage(@Nullable CharSequence charSequence) {
            this.a.mMessage = charSequence;
            return this;
        }

        public Builder setIcon(@DrawableRes int i) {
            this.a.mIconId = i;
            return this;
        }

        public Builder setIcon(@Nullable Drawable drawable) {
            this.a.mIcon = drawable;
            return this;
        }

        public Builder setIconAttribute(@AttrRes int i) {
            TypedValue typedValue = new TypedValue();
            this.a.mContext.getTheme().resolveAttribute(i, typedValue, true);
            this.a.mIconId = typedValue.resourceId;
            return this;
        }

        public Builder setPositiveButton(@StringRes int i, OnClickListener onClickListener) {
            this.a.mPositiveButtonText = this.a.mContext.getText(i);
            this.a.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, OnClickListener onClickListener) {
            this.a.mPositiveButtonText = charSequence;
            this.a.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButtonIcon(Drawable drawable) {
            this.a.mPositiveButtonIcon = drawable;
            return this;
        }

        public Builder setNegativeButton(@StringRes int i, OnClickListener onClickListener) {
            this.a.mNegativeButtonText = this.a.mContext.getText(i);
            this.a.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, OnClickListener onClickListener) {
            this.a.mNegativeButtonText = charSequence;
            this.a.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButtonIcon(Drawable drawable) {
            this.a.mNegativeButtonIcon = drawable;
            return this;
        }

        public Builder setNeutralButton(@StringRes int i, OnClickListener onClickListener) {
            this.a.mNeutralButtonText = this.a.mContext.getText(i);
            this.a.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, OnClickListener onClickListener) {
            this.a.mNeutralButtonText = charSequence;
            this.a.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButtonIcon(Drawable drawable) {
            this.a.mNeutralButtonIcon = drawable;
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.a.mCancelable = z;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.a.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.a.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.a.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setItems(@ArrayRes int i, OnClickListener onClickListener) {
            this.a.mItems = this.a.mContext.getResources().getTextArray(i);
            this.a.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, OnClickListener onClickListener) {
            this.a.mItems = charSequenceArr;
            this.a.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setAdapter(ListAdapter listAdapter, OnClickListener onClickListener) {
            this.a.mAdapter = listAdapter;
            this.a.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCursor(Cursor cursor, OnClickListener onClickListener, String str) {
            this.a.mCursor = cursor;
            this.a.mLabelColumn = str;
            this.a.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMultiChoiceItems(@ArrayRes int i, boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.a.mItems = this.a.mContext.getResources().getTextArray(i);
            this.a.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.a.mCheckedItems = zArr;
            this.a.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.a.mItems = charSequenceArr;
            this.a.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.a.mCheckedItems = zArr;
            this.a.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String str, String str2, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.a.mCursor = cursor;
            this.a.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.a.mIsCheckedColumn = str;
            this.a.mLabelColumn = str2;
            this.a.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(@ArrayRes int i, int i2, OnClickListener onClickListener) {
            this.a.mItems = this.a.mContext.getResources().getTextArray(i);
            this.a.mOnClickListener = onClickListener;
            this.a.mCheckedItem = i2;
            this.a.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int i, String str, OnClickListener onClickListener) {
            this.a.mCursor = cursor;
            this.a.mOnClickListener = onClickListener;
            this.a.mCheckedItem = i;
            this.a.mLabelColumn = str;
            this.a.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArr, int i, OnClickListener onClickListener) {
            this.a.mItems = charSequenceArr;
            this.a.mOnClickListener = onClickListener;
            this.a.mCheckedItem = i;
            this.a.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int i, OnClickListener onClickListener) {
            this.a.mAdapter = listAdapter;
            this.a.mOnClickListener = onClickListener;
            this.a.mCheckedItem = i;
            this.a.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
            this.a.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setView(int i) {
            this.a.mView = null;
            this.a.mViewLayoutResId = i;
            this.a.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view) {
            this.a.mView = view;
            this.a.mViewLayoutResId = 0;
            this.a.mViewSpacingSpecified = false;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Deprecated
        public Builder setView(View view, int i, int i2, int i3, int i4) {
            this.a.mView = view;
            this.a.mViewLayoutResId = 0;
            this.a.mViewSpacingSpecified = true;
            this.a.mViewSpacingLeft = i;
            this.a.mViewSpacingTop = i2;
            this.a.mViewSpacingRight = i3;
            this.a.mViewSpacingBottom = i4;
            return this;
        }

        @Deprecated
        public Builder setInverseBackgroundForced(boolean z) {
            this.a.mForceInverseBackground = z;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder setRecycleOnMeasureEnabled(boolean z) {
            this.a.mRecycleOnMeasure = z;
            return this;
        }

        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(this.a.mContext, this.b);
            this.a.apply(alertDialog.a);
            alertDialog.setCancelable(this.a.mCancelable);
            if (this.a.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.a.mOnCancelListener);
            alertDialog.setOnDismissListener(this.a.mOnDismissListener);
            if (this.a.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.a.mOnKeyListener);
            }
            return alertDialog;
        }

        public AlertDialog show() {
            AlertDialog create = create();
            create.show();
            return create;
        }
    }

    protected AlertDialog(@NonNull Context context) {
        this(context, 0);
    }

    protected AlertDialog(@NonNull Context context, @StyleRes int i) {
        super(context, a(context, i));
        this.a = new AlertController(getContext(), this, getWindow());
    }

    protected AlertDialog(@NonNull Context context, boolean z, @Nullable OnCancelListener onCancelListener) {
        this(context, 0);
        setCancelable(z);
        setOnCancelListener(onCancelListener);
    }

    static int a(@NonNull Context context, @StyleRes int i) {
        if (((i >>> 24) & 255) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public Button getButton(int i) {
        return this.a.d(i);
    }

    public ListView getListView() {
        return this.a.b();
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.a.a(charSequence);
    }

    public void setCustomTitle(View view) {
        this.a.b(view);
    }

    public void setMessage(CharSequence charSequence) {
        this.a.b(charSequence);
    }

    public void setView(View view) {
        this.a.c(view);
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        this.a.a(view, i, i2, i3, i4);
    }

    public void setButton(int i, CharSequence charSequence, Message message) {
        this.a.a(i, charSequence, (OnClickListener) null, message, (Drawable) null);
    }

    public void setButton(int i, CharSequence charSequence, OnClickListener onClickListener) {
        this.a.a(i, charSequence, onClickListener, (Message) null, (Drawable) null);
    }

    public void setButton(int i, CharSequence charSequence, Drawable drawable, OnClickListener onClickListener) {
        this.a.a(i, charSequence, onClickListener, (Message) null, drawable);
    }

    public void setIcon(int i) {
        this.a.b(i);
    }

    public void setIcon(Drawable drawable) {
        this.a.a(drawable);
    }

    public void setIconAttribute(int i) {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(i, typedValue, true);
        this.a.b(typedValue.resourceId);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a.a();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.a.a(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.a.b(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }
}
