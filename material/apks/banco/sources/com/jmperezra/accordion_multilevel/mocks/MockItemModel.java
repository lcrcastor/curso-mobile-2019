package com.jmperezra.accordion_multilevel.mocks;

import com.jmperezra.accordion_multilevel.items.BasicItemState;
import com.jmperezra.accordion_multilevel.items.ItemState;
import java.util.ArrayList;
import java.util.List;

public class MockItemModel {
    public static List<ItemState> getItems() {
        BasicItemState basicItemState = new BasicItemState(Integer.valueOf(1), "Todos los filtros");
        BasicItemState basicItemState2 = new BasicItemState(Integer.valueOf(2), "Item 1.1");
        basicItemState2.setSelected(true);
        basicItemState2.addChild(new BasicItemState(Integer.valueOf(8), "Item 1.1.1"));
        BasicItemState basicItemState3 = new BasicItemState(Integer.valueOf(3), "Item 1.2");
        basicItemState3.setSelected(true);
        BasicItemState basicItemState4 = new BasicItemState(Integer.valueOf(4), "Item 1.3");
        basicItemState.addChild(basicItemState2);
        basicItemState.addChild(basicItemState3);
        int i = 0;
        while (i < 3) {
            Integer valueOf = Integer.valueOf(i + 10);
            StringBuilder sb = new StringBuilder();
            sb.append("Item 1.4.");
            i++;
            sb.append(i);
            basicItemState.addChild(new BasicItemState(valueOf, sb.toString()));
        }
        BasicItemState basicItemState5 = new BasicItemState(Integer.valueOf(6), "Item 1.3.1");
        BasicItemState basicItemState6 = new BasicItemState(Integer.valueOf(7), "Item 1.3.2");
        basicItemState4.addChild(basicItemState5);
        basicItemState4.addChild(basicItemState6);
        basicItemState4.setSelected(true);
        basicItemState4.setExpand(false);
        basicItemState.addChild(basicItemState4);
        ArrayList arrayList = new ArrayList();
        arrayList.add(basicItemState);
        return arrayList;
    }
}
