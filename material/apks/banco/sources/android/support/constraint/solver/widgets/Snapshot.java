package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import java.util.ArrayList;

public class Snapshot {
    private int a;
    private int b;
    private int c;
    private int d;
    private ArrayList<Connection> e = new ArrayList<>();

    static class Connection {
        private ConstraintAnchor a;
        private ConstraintAnchor b;
        private int c;
        private Strength d;
        private int e;

        public Connection(ConstraintAnchor constraintAnchor) {
            this.a = constraintAnchor;
            this.b = constraintAnchor.getTarget();
            this.c = constraintAnchor.getMargin();
            this.d = constraintAnchor.getStrength();
            this.e = constraintAnchor.getConnectionCreator();
        }

        public void a(ConstraintWidget constraintWidget) {
            this.a = constraintWidget.getAnchor(this.a.getType());
            if (this.a != null) {
                this.b = this.a.getTarget();
                this.c = this.a.getMargin();
                this.d = this.a.getStrength();
                this.e = this.a.getConnectionCreator();
                return;
            }
            this.b = null;
            this.c = 0;
            this.d = Strength.STRONG;
            this.e = 0;
        }

        public void b(ConstraintWidget constraintWidget) {
            constraintWidget.getAnchor(this.a.getType()).connect(this.b, this.c, this.d, this.e);
        }
    }

    public Snapshot(ConstraintWidget constraintWidget) {
        this.a = constraintWidget.getX();
        this.b = constraintWidget.getY();
        this.c = constraintWidget.getWidth();
        this.d = constraintWidget.getHeight();
        ArrayList anchors = constraintWidget.getAnchors();
        int size = anchors.size();
        for (int i = 0; i < size; i++) {
            this.e.add(new Connection((ConstraintAnchor) anchors.get(i)));
        }
    }

    public void updateFrom(ConstraintWidget constraintWidget) {
        this.a = constraintWidget.getX();
        this.b = constraintWidget.getY();
        this.c = constraintWidget.getWidth();
        this.d = constraintWidget.getHeight();
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            ((Connection) this.e.get(i)).a(constraintWidget);
        }
    }

    public void applyTo(ConstraintWidget constraintWidget) {
        constraintWidget.setX(this.a);
        constraintWidget.setY(this.b);
        constraintWidget.setWidth(this.c);
        constraintWidget.setHeight(this.d);
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            ((Connection) this.e.get(i)).b(constraintWidget);
        }
    }
}
