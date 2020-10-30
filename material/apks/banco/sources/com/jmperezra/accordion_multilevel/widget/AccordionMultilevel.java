package com.jmperezra.accordion_multilevel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import com.jmperezra.accordion_multilevel.R;
import com.jmperezra.accordion_multilevel.builder.BuilderAccordionMultilevel;
import com.jmperezra.accordion_multilevel.commons.AttrItem;
import com.jmperezra.accordion_multilevel.items.ItemState;
import com.jmperezra.accordion_multilevel.listeners.AccordionMultilevelListener;
import com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener;
import java.util.ArrayList;
import java.util.List;

public class AccordionMultilevel extends LinearLayout {
    private BuilderAccordionMultilevel a;
    private AttrItem b = new AttrItem();
    private BuildAccordionMultilevelListener c;

    public AccordionMultilevel(Context context) {
        super(context);
        a();
    }

    public AccordionMultilevel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
        a(attributeSet);
    }

    private void a() {
        setOrientation(1);
    }

    private void a(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.AccordionMultilevel, 0, 0);
        this.b.setResource_id_arrow(obtainStyledAttributes.getResourceId(R.styleable.AccordionMultilevel_imageArrow, R.drawable.ic_arrow_small));
        this.b.setResource_id_checkbox_checked(obtainStyledAttributes.getResourceId(R.styleable.AccordionMultilevel_imageCheckBox_Checked, R.drawable.ic_checkbox_checked));
        this.b.setResource_id_checkbox_unchecked(obtainStyledAttributes.getResourceId(R.styleable.AccordionMultilevel_imageCheckBox_UnChecked, R.drawable.ic_checkbox_unchecked));
        this.b.setBackground_AccordionMultilevel(obtainStyledAttributes.getResourceId(R.styleable.AccordionMultilevel_background_AccordionMultilevel, R.drawable.border_layout));
        this.b.setDrawable_Separator(obtainStyledAttributes.getResourceId(R.styleable.AccordionMultilevel_drawable_Separator, R.drawable.border_bottom_layout));
        this.b.setInitAllExpand(obtainStyledAttributes.getBoolean(R.styleable.AccordionMultilevel_initAllExpand, false));
        this.b.setDurationAnimationArrow(obtainStyledAttributes.getInteger(R.styleable.AccordionMultilevel_durationAnimationArrow, getResources().getInteger(R.integer.DURATION_ARROW_ROTATE)));
        this.b.setDurationAnimationExpandAndCollapseItems(obtainStyledAttributes.getInteger(R.styleable.AccordionMultilevel_durationAnimationExpandAndCollapseItems, getResources().getInteger(R.integer.DURATION_ITEM_EXPAND_COLLAPSE)));
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.opacityCheckPartial, typedValue, true);
        this.b.setResource_id_opacity_notallchecked(obtainStyledAttributes.getFloat(R.styleable.AccordionMultilevel_opacity_NotAllChecked, typedValue.getFloat()));
    }

    public void build(List<ItemState> list) {
        build(list, null);
    }

    public void build(List<ItemState> list, AccordionMultilevelListener accordionMultilevelListener) {
        this.a = new BuilderAccordionMultilevel(this, list, this.b);
        if (accordionMultilevelListener != null) {
            this.a.setAccordionMultilevelListener(accordionMultilevelListener);
        }
        if (this.c != null) {
            this.a.setBuildAccordionMultilevelListener(this.c);
        }
        this.a.create();
    }

    public void setBuilderListener(BuildAccordionMultilevelListener buildAccordionMultilevelListener) {
        this.c = buildAccordionMultilevelListener;
    }

    public List<ItemState> getItemsSelected() {
        return a(new ArrayList(), this.a.getListItemModels());
    }

    private List<ItemState> a(List<ItemState> list, List<ItemState> list2) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list2 != null) {
            for (ItemState itemState : list2) {
                if (itemState.isSelected() && itemState.isSelectable()) {
                    list.add(itemState);
                }
                if (itemState.getChildren() != null && itemState.getChildren().size() > 0) {
                    a(list, itemState.getChildren());
                }
            }
        }
        return list;
    }

    public List<ItemState> getStateItems() {
        return this.a.getListItemModels();
    }
}
