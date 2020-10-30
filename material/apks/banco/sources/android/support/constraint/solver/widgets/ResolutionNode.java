package android.support.constraint.solver.widgets;

import java.util.HashSet;
import java.util.Iterator;

public class ResolutionNode {
    public static final int REMOVED = 2;
    public static final int RESOLVED = 1;
    public static final int UNRESOLVED = 0;
    HashSet<ResolutionNode> h = new HashSet<>(2);
    int i = 0;

    public void remove(ResolutionDimension resolutionDimension) {
    }

    public void resolve() {
    }

    public void addDependent(ResolutionNode resolutionNode) {
        this.h.add(resolutionNode);
    }

    public void reset() {
        this.i = 0;
        this.h.clear();
    }

    public void invalidate() {
        this.i = 0;
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            ((ResolutionNode) it.next()).invalidate();
        }
    }

    public void invalidateAnchors() {
        if (this instanceof ResolutionAnchor) {
            this.i = 0;
        }
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            ((ResolutionNode) it.next()).invalidateAnchors();
        }
    }

    public void didResolve() {
        this.i = 1;
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            ((ResolutionNode) it.next()).resolve();
        }
    }

    public boolean isResolved() {
        return this.i == 1;
    }
}
