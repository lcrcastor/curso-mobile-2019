package com.jmperezra.accordion_multilevel.builder;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import com.jmperezra.accordion_multilevel.commons.AttrItem;
import com.jmperezra.accordion_multilevel.items.HandlerItem;
import com.jmperezra.accordion_multilevel.items.HandlerItemView;
import com.jmperezra.accordion_multilevel.items.ItemState;
import com.jmperezra.accordion_multilevel.listeners.AccordionMultilevelListener;
import com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener;
import java.util.ArrayList;
import java.util.List;

public abstract class BuildItemsAccordionAsync extends AsyncTask<Void, HandlerItem, List<HandlerItem>> {
    private List<ItemState> a;
    private ViewGroup b;
    private AttrItem c;
    private AccordionMultilevelListener d;
    private BuildAccordionMultilevelListener e;

    public abstract void onAccordionMultilevelCreated(List<HandlerItem> list);

    public BuildItemsAccordionAsync(List<ItemState> list, ViewGroup viewGroup) {
        this.a = list;
        this.b = viewGroup;
    }

    public void setAttrItem(AttrItem attrItem) {
        this.c = attrItem;
    }

    public void setBuilderListener(BuildAccordionMultilevelListener buildAccordionMultilevelListener) {
        this.e = buildAccordionMultilevelListener;
    }

    public void setAccordionMultilevelListener(AccordionMultilevelListener accordionMultilevelListener) {
        this.d = accordionMultilevelListener;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        if (this.e != null) {
            this.e.onShowDialogBuilding();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public List<HandlerItem> doInBackground(Void... voidArr) {
        try {
            List<HandlerItem> a2 = a(null, null, this.a, 0);
            publishProgress(new HandlerItem[]{new HandlerItem(null, null, 0)});
            return a2;
        } catch (Exception e2) {
            Log.e("@dev", "Error al crear el acordeón", e2);
            publishProgress(new HandlerItem[]{new HandlerItem(null, null, 0)});
            return new ArrayList();
        } catch (Throwable th) {
            publishProgress(new HandlerItem[]{new HandlerItem(null, null, 0)});
            throw th;
        }
    }

    private List<HandlerItem> a(List<HandlerItem> list, HandlerItem handlerItem, List<ItemState> list2, int i) {
        if (list == null) {
            list = new ArrayList<>();
        }
        for (ItemState itemState : list2) {
            HandlerItem handlerItem2 = new HandlerItem(itemState, new HandlerItemView(this.b.getContext(), this.c, i == 0), i);
            handlerItem2.setParentHandlerItem(handlerItem);
            handlerItem2.setAccordionMultilevelListener(this.d);
            handlerItem2.init();
            publishProgress(new HandlerItem[]{handlerItem, handlerItem2});
            if (handlerItem2.hasChildren()) {
                handlerItem2.setChildrenHandlerItem(a(null, handlerItem2, itemState.getChildren(), i + 1));
            }
            list.add(handlerItem2);
        }
        return list;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041 A[Catch:{ Exception -> 0x004b, all -> 0x0049 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onProgressUpdate(com.jmperezra.accordion_multilevel.items.HandlerItem... r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x0064
            int r1 = r5.length     // Catch:{ Exception -> 0x004b }
            r2 = 1
            if (r1 <= r2) goto L_0x0064
            int r1 = r5.length     // Catch:{ Exception -> 0x004b }
            r3 = 2
            if (r1 != r3) goto L_0x0064
            r1 = 0
            r3 = r5[r1]     // Catch:{ Exception -> 0x004b }
            if (r3 == 0) goto L_0x0029
            r3 = r5[r1]     // Catch:{ Exception -> 0x004b }
            com.jmperezra.accordion_multilevel.items.HandlerItemView r3 = r3.getHandlerItemView()     // Catch:{ Exception -> 0x004b }
            if (r3 != 0) goto L_0x0019
            goto L_0x0029
        L_0x0019:
            r1 = r5[r1]     // Catch:{ Exception -> 0x004b }
            com.jmperezra.accordion_multilevel.items.HandlerItemView r1 = r1.getHandlerItemView()     // Catch:{ Exception -> 0x004b }
            r3 = r5[r2]     // Catch:{ Exception -> 0x004b }
            com.jmperezra.accordion_multilevel.items.HandlerItemView r3 = r3.getHandlerItemView()     // Catch:{ Exception -> 0x004b }
            r1.setViewChildren(r3)     // Catch:{ Exception -> 0x004b }
            goto L_0x0038
        L_0x0029:
            android.view.ViewGroup r1 = r4.b     // Catch:{ Exception -> 0x004b }
            r3 = r5[r2]     // Catch:{ Exception -> 0x004b }
            com.jmperezra.accordion_multilevel.items.HandlerItemView r3 = r3.getHandlerItemView()     // Catch:{ Exception -> 0x004b }
            android.view.ViewGroup r3 = r3.getView()     // Catch:{ Exception -> 0x004b }
            r1.addView(r3)     // Catch:{ Exception -> 0x004b }
        L_0x0038:
            r1 = r5[r2]     // Catch:{ Exception -> 0x004b }
            r1.initUI()     // Catch:{ Exception -> 0x004b }
            com.jmperezra.accordion_multilevel.listeners.AccordionMultilevelListener r1 = r4.d     // Catch:{ Exception -> 0x004b }
            if (r1 == 0) goto L_0x0064
            com.jmperezra.accordion_multilevel.listeners.AccordionMultilevelListener r1 = r4.d     // Catch:{ Exception -> 0x004b }
            r5 = r5[r2]     // Catch:{ Exception -> 0x004b }
            r1.onShowItemAccordion(r5)     // Catch:{ Exception -> 0x004b }
            goto L_0x0064
        L_0x0049:
            r5 = move-exception
            goto L_0x0058
        L_0x004b:
            r5 = move-exception
            java.lang.String r1 = "@dev"
            java.lang.String r2 = "Error al actualizar la vista al crear el acordeón"
            android.util.Log.e(r1, r2, r5)     // Catch:{ all -> 0x0049 }
            com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener r5 = r4.e
            if (r5 == 0) goto L_0x006f
            goto L_0x0068
        L_0x0058:
            com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener r1 = r4.e
            if (r1 == 0) goto L_0x0063
            com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener r1 = r4.e
            r1.onHideDialogBuilding()
            r4.e = r0
        L_0x0063:
            throw r5
        L_0x0064:
            com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener r5 = r4.e
            if (r5 == 0) goto L_0x006f
        L_0x0068:
            com.jmperezra.accordion_multilevel.listeners.BuildAccordionMultilevelListener r5 = r4.e
            r5.onHideDialogBuilding()
            r4.e = r0
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jmperezra.accordion_multilevel.builder.BuildItemsAccordionAsync.onProgressUpdate(com.jmperezra.accordion_multilevel.items.HandlerItem[]):void");
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(List<HandlerItem> list) {
        onAccordionMultilevelCreated(list);
        if (this.d != null) {
            this.d.onShowAccordionMultilevel(list);
        }
    }
}
