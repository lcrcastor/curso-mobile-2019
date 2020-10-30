package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ListMenuItemView extends LinearLayout implements ItemView {
    private MenuItemImpl a;
    private ImageView b;
    private RadioButton c;
    private TextView d;
    private CheckBox e;
    private TextView f;
    private ImageView g;
    private Drawable h;
    private int i;
    private Context j;
    private boolean k;
    private Drawable l;
    private int m;
    private LayoutInflater n;
    private boolean o;

    public boolean prefersCondensedTitle() {
        return false;
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.MenuView, i2, 0);
        this.h = obtainStyledAttributes.getDrawable(R.styleable.MenuView_android_itemBackground);
        this.i = obtainStyledAttributes.getResourceId(R.styleable.MenuView_android_itemTextAppearance, -1);
        this.k = obtainStyledAttributes.getBoolean(R.styleable.MenuView_preserveIconSpacing, false);
        this.j = context;
        this.l = obtainStyledAttributes.getDrawable(R.styleable.MenuView_subMenuArrow);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        ViewCompat.setBackground(this, this.h);
        this.d = (TextView) findViewById(R.id.title);
        if (this.i != -1) {
            this.d.setTextAppearance(this.j, this.i);
        }
        this.f = (TextView) findViewById(R.id.shortcut);
        this.g = (ImageView) findViewById(R.id.submenuarrow);
        if (this.g != null) {
            this.g.setImageDrawable(this.l);
        }
    }

    public void initialize(MenuItemImpl menuItemImpl, int i2) {
        this.a = menuItemImpl;
        this.m = i2;
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setTitle(menuItemImpl.a((ItemView) this));
        setCheckable(menuItemImpl.isCheckable());
        setShortcut(menuItemImpl.c(), menuItemImpl.a());
        setIcon(menuItemImpl.getIcon());
        setEnabled(menuItemImpl.isEnabled());
        setSubMenuArrowVisible(menuItemImpl.hasSubMenu());
        setContentDescription(menuItemImpl.getContentDescription());
    }

    public void setForceShowIcon(boolean z) {
        this.o = z;
        this.k = z;
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.d.setText(charSequence);
            if (this.d.getVisibility() != 0) {
                this.d.setVisibility(0);
            }
        } else if (this.d.getVisibility() != 8) {
            this.d.setVisibility(8);
        }
    }

    public MenuItemImpl getItemData() {
        return this.a;
    }

    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (z || this.c != null || this.e != null) {
            if (this.a.isExclusiveCheckable()) {
                if (this.c == null) {
                    b();
                }
                compoundButton2 = this.c;
                compoundButton = this.e;
            } else {
                if (this.e == null) {
                    c();
                }
                compoundButton2 = this.e;
                compoundButton = this.c;
            }
            if (z) {
                compoundButton2.setChecked(this.a.isChecked());
                int i2 = z ? 0 : 8;
                if (compoundButton2.getVisibility() != i2) {
                    compoundButton2.setVisibility(i2);
                }
                if (!(compoundButton == null || compoundButton.getVisibility() == 8)) {
                    compoundButton.setVisibility(8);
                }
            } else {
                if (this.e != null) {
                    this.e.setVisibility(8);
                }
                if (this.c != null) {
                    this.c.setVisibility(8);
                }
            }
        }
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        if (this.a.isExclusiveCheckable()) {
            if (this.c == null) {
                b();
            }
            compoundButton = this.c;
        } else {
            if (this.e == null) {
                c();
            }
            compoundButton = this.e;
        }
        compoundButton.setChecked(z);
    }

    private void setSubMenuArrowVisible(boolean z) {
        if (this.g != null) {
            this.g.setVisibility(z ? 0 : 8);
        }
    }

    public void setShortcut(boolean z, char c2) {
        int i2 = (!z || !this.a.c()) ? 8 : 0;
        if (i2 == 0) {
            this.f.setText(this.a.b());
        }
        if (this.f.getVisibility() != i2) {
            this.f.setVisibility(i2);
        }
    }

    public void setIcon(Drawable drawable) {
        boolean z = this.a.shouldShowIcon() || this.o;
        if (!z && !this.k) {
            return;
        }
        if (this.b != null || drawable != null || this.k) {
            if (this.b == null) {
                a();
            }
            if (drawable != null || this.k) {
                ImageView imageView = this.b;
                if (!z) {
                    drawable = null;
                }
                imageView.setImageDrawable(drawable);
                if (this.b.getVisibility() != 0) {
                    this.b.setVisibility(0);
                }
            } else {
                this.b.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.b != null && this.k) {
            LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.b.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i2, i3);
    }

    private void a() {
        this.b = (ImageView) getInflater().inflate(R.layout.abc_list_menu_item_icon, this, false);
        addView(this.b, 0);
    }

    private void b() {
        this.c = (RadioButton) getInflater().inflate(R.layout.abc_list_menu_item_radio, this, false);
        addView(this.c);
    }

    private void c() {
        this.e = (CheckBox) getInflater().inflate(R.layout.abc_list_menu_item_checkbox, this, false);
        addView(this.e);
    }

    public boolean showsIcon() {
        return this.o;
    }

    private LayoutInflater getInflater() {
        if (this.n == null) {
            this.n = LayoutInflater.from(getContext());
        }
        return this.n;
    }
}
