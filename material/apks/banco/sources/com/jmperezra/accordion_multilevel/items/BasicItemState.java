package com.jmperezra.accordion_multilevel.items;

import java.util.ArrayList;
import java.util.List;

public class BasicItemState extends BaseItemState {
    private String a;
    private Integer b;
    private List<ItemState> c;

    public BasicItemState(Integer num, String str) {
        this.b = num;
        this.a = str;
    }

    public BasicItemState(Integer num, String str, List<ItemState> list) {
        this.b = num;
        this.a = str;
        this.c = list;
    }

    public Integer getId() {
        return this.b;
    }

    public String getLabel() {
        return this.a;
    }

    public List<ItemState> getChildren() {
        return this.c;
    }

    public void addChild(ItemState itemState) {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        this.c.add(itemState);
    }
}
