package com.jmperezra.accordion_multilevel.items;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.jmperezra.accordion_multilevel.R;
import com.jmperezra.accordion_multilevel.animations.ArrowRotateAnimation;
import com.jmperezra.accordion_multilevel.animations.ItemExpandOrCollapseAnimation;
import com.jmperezra.accordion_multilevel.commons.AttrItem;
import com.jmperezra.accordion_multilevel.commons.TypeStateCheck;
import com.jmperezra.accordion_multilevel.items.styles.StyleItem;
import com.jmperezra.accordion_multilevel.listeners.ItemViewListener;

public class HandlerItemView implements OnClickListener {
    private final int a;
    private final int b;
    private ViewGroup c;
    private ImageView d;
    private ImageView e;
    private TextView f;
    /* access modifiers changed from: private */
    public ViewGroup g;
    private ViewGroup h;
    /* access modifiers changed from: private */
    public AttrItem i;
    private ItemViewListener j;
    private Context k;

    public void setStyleItem(StyleItem styleItem) {
    }

    public HandlerItemView(Context context, AttrItem attrItem) {
        this.a = R.id.key_tag_state_check;
        this.b = R.id.key_tag_state_check_view;
        this.i = attrItem;
        this.k = context;
        this.c = (ViewGroup) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.layout_item_accordion, null);
        this.d = (ImageView) this.c.findViewById(R.id.ivArrow);
        this.d.setOnClickListener(this);
        this.f = (TextView) this.c.findViewById(R.id.tvLabel);
        this.f.setOnClickListener(this);
        this.e = (ImageView) this.c.findViewById(R.id.ivCheck);
        this.e.setOnClickListener(this);
        i();
        this.h = (ViewGroup) this.c.findViewById(R.id.vgRowItem);
        this.g = (ViewGroup) this.c.findViewById(R.id.vgChildren);
        updateVisibilityChildren(Boolean.valueOf(attrItem.isInitAllExpand()));
    }

    public HandlerItemView(Context context, AttrItem attrItem, boolean z) {
        this(context, attrItem);
        if (z) {
            setFirstLevel();
        }
    }

    public void setItemViewListener(ItemViewListener itemViewListener) {
        this.j = itemViewListener;
    }

    public void setTextLabel(String str) {
        if (this.f != null) {
            this.f.setText(str);
        }
    }

    public void setViewChildren(HandlerItemView handlerItemView) {
        if (this.g != null) {
            this.g.addView(handlerItemView.getView());
        }
    }

    public void setVisibilityArrow(boolean z) {
        if (this.d == null) {
            return;
        }
        if (z) {
            this.d.setVisibility(0);
        } else {
            this.d.setVisibility(4);
        }
    }

    public ViewGroup getView() {
        return this.c;
    }

    public void setStateCheckBox(boolean z) {
        if (z) {
            b();
        } else {
            e();
        }
    }

    public TypeStateCheck getTypeStateCheck() {
        return (TypeStateCheck) this.e.getTag(this.b);
    }

    public void setStateCheckBox(TypeStateCheck typeStateCheck) {
        if (typeStateCheck.equals(TypeStateCheck.CHECK_ALL)) {
            b();
        } else if (typeStateCheck.equals(TypeStateCheck.UNCHECK)) {
            e();
        } else if (typeStateCheck.equals(TypeStateCheck.CHECK_PARTIAL)) {
            g();
        }
    }

    private void a() {
        this.j.onCheckItem();
    }

    private boolean b() {
        if (this.e == null) {
            return false;
        }
        this.e.setTag(this.b, TypeStateCheck.CHECK_ALL);
        return this.e.post(new Runnable() {
            public void run() {
                HandlerItemView.this.c();
            }
        });
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.e != null) {
            this.e.setTag(this.a, TypeStateCheck.CHECK_ALL);
            this.e.setTag(this.b, TypeStateCheck.CHECK_ALL);
            ImageView imageView = this.e;
            StringBuilder sb = new StringBuilder();
            sb.append(this.f.getText());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.k.getString(R.string.item_seleccionado));
            imageView.setContentDescription(sb.toString());
            this.e.setImageResource(this.i != null ? this.i.getResource_id_checkbox_checked() : R.drawable.ic_checkbox_checked);
            this.e.setAlpha(1.0f);
        }
    }

    private void d() {
        this.j.onUnCheckItem();
    }

    private boolean e() {
        if (this.e == null) {
            return false;
        }
        this.e.setTag(this.b, TypeStateCheck.UNCHECK);
        return this.e.post(new Runnable() {
            public void run() {
                HandlerItemView.this.f();
            }
        });
    }

    /* access modifiers changed from: private */
    public void f() {
        if (this.e != null) {
            this.e.setTag(this.b, TypeStateCheck.UNCHECK);
            this.e.setTag(this.a, TypeStateCheck.UNCHECK);
            ImageView imageView = this.e;
            StringBuilder sb = new StringBuilder();
            sb.append(this.f.getText());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.k.getString(R.string.item_no_seleccionado));
            imageView.setContentDescription(sb.toString());
            this.e.setImageResource(this.i != null ? this.i.getResource_id_checkbox_unchecked() : R.drawable.ic_checkbox_unchecked);
            this.e.setAlpha(1.0f);
        }
    }

    private boolean g() {
        if (this.e == null) {
            return false;
        }
        this.e.setTag(this.b, TypeStateCheck.CHECK_PARTIAL);
        return this.e.post(new Runnable() {
            public void run() {
                HandlerItemView.this.h();
            }
        });
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.e != null) {
            this.e.setTag(this.a, TypeStateCheck.UNCHECK);
            this.e.setTag(this.b, TypeStateCheck.CHECK_PARTIAL);
            this.e.setImageResource(this.i.getResource_id_checkbox_checked());
            this.e.setAlpha(this.i.getResource_id_opacity_notallchecked());
        }
    }

    private void i() {
        if (this.d != null) {
            this.d.setImageResource(this.i != null ? this.i.getResource_id_arrow() : R.drawable.ic_arrow_small);
        }
    }

    public void setVisibilityCheckBox(int i2) {
        if (this.e != null) {
            this.e.setVisibility(i2);
            this.e.setContentDescription(this.f.getText());
        }
    }

    private void j() {
        if (this.d != null) {
            this.d.startAnimation(ArrowRotateAnimation.turnArrow(this.i.getDurationAnimationArrow(), 0, 90));
            this.d.setContentDescription(this.k.getText(R.string.IDXX_OPTION_ACCORDION_COLLAPSE));
        }
    }

    private void k() {
        if (this.d != null) {
            this.d.startAnimation(ArrowRotateAnimation.turnArrow(this.i.getDurationAnimationArrow(), 90, 0));
            this.d.setContentDescription(this.k.getText(R.string.IDXX_OPTION_ACCORDION_EXPAND));
        }
    }

    public void rotateDownArrow() {
        if (this.d != null) {
            this.d.startAnimation(ArrowRotateAnimation.turnArrow(0, 0, 90));
            this.d.setContentDescription(this.k.getText(R.string.IDXX_OPTION_ACCORDION_COLLAPSE));
        }
    }

    private void l() {
        j();
        m();
        this.j.onExpandItems();
    }

    private void m() {
        if (this.g != null) {
            new Handler().post(new Runnable() {
                public void run() {
                    ItemExpandOrCollapseAnimation.expand(HandlerItemView.this.i.getDurationAnimationExpandAndCollapseItems(), HandlerItemView.this.g);
                }
            });
        }
    }

    private void n() {
        k();
        o();
        this.j.onCollapseItems();
    }

    private void o() {
        if (this.g != null) {
            new Handler().post(new Runnable() {
                public void run() {
                    ItemExpandOrCollapseAnimation.collapse(HandlerItemView.this.i.getDurationAnimationExpandAndCollapseItems(), HandlerItemView.this.g);
                }
            });
        }
    }

    public boolean isExpand() {
        return this.g.getVisibility() == 0;
    }

    public boolean isChecked() {
        if (this.e != null) {
            TypeStateCheck typeStateCheck = (TypeStateCheck) this.e.getTag(this.a);
            if (typeStateCheck != null) {
                return typeStateCheck.equals(TypeStateCheck.CHECK_ALL);
            }
        }
        return false;
    }

    public void onClick(View view) {
        if (this.d.getId() == view.getId()) {
            p();
        } else if (this.f.getId() == view.getId()) {
            q();
        } else if (this.e.getId() == view.getId()) {
            r();
        }
    }

    private void p() {
        if (this.j.onClickArrow()) {
            t();
        }
    }

    private void q() {
        if (this.j.onClickLabel()) {
            t();
        }
    }

    private void r() {
        if (this.j.onClickCheckBox()) {
            s();
        }
    }

    private void s() {
        if (isChecked()) {
            d();
            ImageView imageView = this.e;
            StringBuilder sb = new StringBuilder();
            sb.append(this.f.getText());
            sb.append(" no seleccionado");
            imageView.setContentDescription(sb.toString());
            return;
        }
        a();
        ImageView imageView2 = this.e;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.f.getText());
        sb2.append(" seleccionado");
        imageView2.setContentDescription(sb2.toString());
    }

    private void t() {
        if (isExpand()) {
            n();
        } else {
            l();
        }
    }

    public void updateVisibilityChildren(Boolean bool) {
        if (this.g != null && bool != null) {
            if (bool.booleanValue()) {
                this.g.setVisibility(0);
            } else {
                this.g.setVisibility(8);
            }
        }
    }

    public void setPaddingLeft(int i2) {
        if (this.h != null) {
            this.h.setPadding(i2 * this.k.getResources().getDimensionPixelOffset(R.dimen.padding_left_item_accordion), this.h.getPaddingTop(), this.h.getPaddingRight(), this.h.getPaddingBottom());
        }
    }

    public void setFirstLevel() {
        if (this.f != null) {
            this.f.setTypeface(Typeface.createFromAsset(this.k.getApplicationContext().getAssets(), "fonts/OpenSans-Regular.otf"));
            this.f.setTextSize(13.0f);
            this.f.setTextColor(this.k.getResources().getColor(R.color.generic_black));
        }
    }
}
