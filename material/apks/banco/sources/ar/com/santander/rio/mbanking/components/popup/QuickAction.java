package ar.com.santander.rio.mbanking.components.popup;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import java.util.ArrayList;
import java.util.List;

public class QuickAction extends PopupWindows implements android.widget.PopupWindow.OnDismissListener {
    private View a;
    private ImageView b;
    private ImageView c;
    private LayoutInflater d;
    private ViewGroup e;
    private ScrollView f;
    /* access modifiers changed from: private */
    public OnActionItemClickListener g;
    private OnDismissListener h;
    private List<ActionItem> i = new ArrayList();
    /* access modifiers changed from: private */
    public boolean j;
    private int k;
    private int l;
    private int m = 0;

    public interface OnActionItemClickListener {
        void onItemClick(QuickAction quickAction, int i, int i2);
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public QuickAction(Context context) {
        super(context);
        this.d = (LayoutInflater) context.getSystemService("layout_inflater");
        setRootViewId(R.layout.popup_vertical);
        this.k = 0;
    }

    public ActionItem getActionItem(int i2) {
        return (ActionItem) this.i.get(i2);
    }

    public void setRootViewId(int i2) {
        this.a = this.d.inflate(i2, null);
        this.e = (ViewGroup) this.a.findViewById(R.id.tracks);
        this.c = (ImageView) this.a.findViewById(R.id.arrow_down);
        this.b = (ImageView) this.a.findViewById(R.id.arrow_up);
        this.f = (ScrollView) this.a.findViewById(R.id.scroller);
        this.a.setLayoutParams(new LayoutParams(-2, -2));
        setContentView(this.a);
    }

    public void setOnActionItemClickListener(OnActionItemClickListener onActionItemClickListener) {
        this.g = onActionItemClickListener;
    }

    public void addActionItem(ActionItem actionItem) {
        this.i.add(actionItem);
        String title = actionItem.getTitle();
        Drawable icon = actionItem.getIcon();
        int color = actionItem.getColor();
        View inflate = this.d.inflate(R.layout.action_item_vertical, null);
        actionItem.setMainView(inflate);
        ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.wrapperIcon);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_icon);
        TextView textView = (TextView) inflate.findViewById(R.id.view_title);
        if (actionItem.getTypeface() != null) {
            textView.setTypeface(actionItem.getTypeface());
        }
        if (icon != null) {
            imageView.setImageDrawable(icon);
        } else {
            viewGroup.setVisibility(8);
        }
        if (title != null) {
            textView.setText(Html.fromHtml(title));
        } else {
            textView.setVisibility(8);
        }
        textView.setTextColor(color);
        final int i2 = this.k;
        final int actionId = actionItem.getActionId();
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActionItem actionItem = QuickAction.this.getActionItem(i2);
                if (actionItem == null) {
                    return;
                }
                if (!actionItem.isSticky()) {
                    QuickAction.this.j = true;
                    QuickAction.this.dismiss();
                    return;
                }
                QuickAction.this.g.onItemClick(QuickAction.this, i2, actionId);
            }
        });
        inflate.setFocusable(true);
        inflate.setClickable(true);
        this.e.addView(inflate, this.l);
        this.k++;
        this.l++;
    }

    public void removeSeparator() {
        this.e.getChildAt(this.e.getChildCount() - 1).findViewById(R.id.viewSeparator).setVisibility(8);
    }

    public void show(View view) {
        int i2;
        int i3;
        try {
            preShow();
            boolean z = false;
            this.j = false;
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            Rect rect = new Rect(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
            this.a.measure(-2, -2);
            int measuredHeight = this.a.getMeasuredHeight();
            if (this.m == 0) {
                this.m = this.a.getMeasuredWidth();
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int i4 = displayMetrics.widthPixels;
            int i5 = displayMetrics.heightPixels;
            if (rect.left + this.m > i4) {
                i2 = rect.left - (this.m - view.getWidth());
                if (i2 < 0) {
                    i2 = 0;
                }
            } else {
                i2 = view.getWidth() > this.m ? rect.centerX() - (this.m / 2) : rect.left;
            }
            int i6 = rect.top;
            int i7 = i5 - rect.bottom;
            if (i6 > i7) {
                z = true;
            }
            if (!z) {
                int i8 = rect.bottom;
                if (measuredHeight > i7) {
                    this.f.getLayoutParams().height = i7 - 100;
                }
                i3 = i8;
            } else if (measuredHeight > i6) {
                i3 = 75;
                this.f.getLayoutParams().height = (i6 - view.getHeight()) - 10;
            } else {
                i3 = rect.top - measuredHeight;
            }
            a(z ? R.id.arrow_down : R.id.arrow_up, (rect.width() / 2) - 10);
            this.mWindow.showAtLocation(view, 48, i2, i3);
        } catch (Exception unused) {
        }
    }

    private void a(int i2, int i3) {
        ImageView imageView = i2 == R.id.arrow_up ? this.b : this.c;
        ImageView imageView2 = i2 == R.id.arrow_up ? this.c : this.b;
        int measuredWidth = this.b.getMeasuredWidth();
        imageView.setVisibility(0);
        ((MarginLayoutParams) imageView.getLayoutParams()).leftMargin = i3 - measuredWidth;
        imageView2.setVisibility(4);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        setOnDismissListener(this);
        this.h = onDismissListener;
    }

    public void onDismiss() {
        if (!this.j && this.h != null) {
            this.h.onDismiss();
        }
    }
}
