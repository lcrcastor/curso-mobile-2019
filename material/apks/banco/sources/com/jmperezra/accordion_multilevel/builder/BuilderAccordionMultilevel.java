package com.jmperezra.accordion_multilevel.builder;

import android.view.ViewGroup;
import com.jmperezra.accordion_multilevel.commons.AttrItem;
import com.jmperezra.accordion_multilevel.items.HandlerItem;
import com.jmperezra.accordion_multilevel.items.ItemState;
import com.jmperezra.accordion_multilevel.listeners.AccordionMultilevelListener;
import com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener;
import java.util.List;

public class BuilderAccordionMultilevel {
    private ViewGroup a;
    private AttrItem b;
    private BuildItemsAccordionAsync c;
    private AccordionMultilevelListener d;
    private List<ItemState> e;
    /* access modifiers changed from: private */
    public List<HandlerItem> f;
    private BuildAccordionMultilevelListener g;

    public BuilderAccordionMultilevel(ViewGroup viewGroup, List<ItemState> list, AttrItem attrItem) {
        this.a = viewGroup;
        this.e = list;
        this.b = attrItem;
    }

    public void setBuildAccordionMultilevelListener(BuildAccordionMultilevelListener buildAccordionMultilevelListener) {
        this.g = buildAccordionMultilevelListener;
    }

    public void setAccordionMultilevelListener(AccordionMultilevelListener accordionMultilevelListener) {
        this.d = accordionMultilevelListener;
    }

    public void create() {
        this.c = new BuildItemsAccordionAsync(this.e, this.a) {
            public void onAccordionMultilevelCreated(List<HandlerItem> list) {
                BuilderAccordionMultilevel.this.f = list;
            }
        };
        this.c.setAttrItem(this.b);
        this.c.setAccordionMultilevelListener(this.d);
        this.c.setBuilderListener(this.g);
        this.c.execute(new Void[0]);
    }

    public List<ItemState> getListItemModels() {
        return this.e;
    }

    public List<HandlerItem> getLstItemsModelView() {
        return this.f;
    }
}
