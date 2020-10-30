package android.support.constraint.solver;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;

public class SolverVariable {
    public static final int STRENGTH_BARRIER = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 6;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static int f = 1;
    private static int g = 1;
    private static int h = 1;
    private static int i = 1;
    private static int j = 1;
    int a = -1;
    float[] b = new float[7];
    Type c;
    public float computedValue;
    ArrayRow[] d = new ArrayRow[8];
    int e = 0;

    /* renamed from: id reason: collision with root package name */
    public int f234id = -1;
    private String k;
    public int strength = 0;
    public int usageInRowCount = 0;

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    static void a() {
        g++;
    }

    public SolverVariable(String str, Type type) {
        this.k = str;
        this.c = type;
    }

    public SolverVariable(Type type, String str) {
        this.c = type;
    }

    public final void addToRow(ArrayRow arrayRow) {
        int i2 = 0;
        while (i2 < this.e) {
            if (this.d[i2] != arrayRow) {
                i2++;
            } else {
                return;
            }
        }
        if (this.e >= this.d.length) {
            this.d = (ArrayRow[]) Arrays.copyOf(this.d, this.d.length * 2);
        }
        this.d[this.e] = arrayRow;
        this.e++;
    }

    public final void removeFromRow(ArrayRow arrayRow) {
        int i2 = this.e;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.d[i3] == arrayRow) {
                for (int i4 = 0; i4 < (i2 - i3) - 1; i4++) {
                    int i5 = i3 + i4;
                    this.d[i5] = this.d[i5 + 1];
                }
                this.e--;
                return;
            }
        }
    }

    public final void updateReferencesWithNewDefinition(ArrayRow arrayRow) {
        int i2 = this.e;
        for (int i3 = 0; i3 < i2; i3++) {
            this.d[i3].variables.a(this.d[i3], arrayRow, false);
        }
        this.e = 0;
    }

    public void reset() {
        this.k = null;
        this.c = Type.UNKNOWN;
        this.strength = 0;
        this.f234id = -1;
        this.a = -1;
        this.computedValue = BitmapDescriptorFactory.HUE_RED;
        this.e = 0;
        this.usageInRowCount = 0;
    }

    public String getName() {
        return this.k;
    }

    public void setName(String str) {
        this.k = str;
    }

    public void setType(Type type, String str) {
        this.c = type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.k);
        return sb.toString();
    }
}
