package android.support.constraint.solver;

public class Cache {
    Pool<ArrayRow> a = new SimplePool(256);
    Pool<SolverVariable> b = new SimplePool(256);
    SolverVariable[] c = new SolverVariable[32];
}
