package android.support.constraint.solver;

import java.util.Arrays;

class ArrayBackedVariables {
    private static final boolean DEBUG = false;
    private SolverVariable candidate;
    private int currentSize;
    private int currentWriteSize;
    private int[] indexes;
    private int maxSize;
    private final int threshold;
    private float[] values;
    private SolverVariable[] variables;

    public ArrayBackedVariables(ArrayRow arrayRow, Cache cache) {
        this.variables = null;
        this.values = null;
        this.indexes = null;
        this.threshold = 16;
        this.maxSize = 4;
        this.currentSize = 0;
        this.currentWriteSize = 0;
        this.candidate = null;
        this.variables = new SolverVariable[this.maxSize];
        this.values = new float[this.maxSize];
        this.indexes = new int[this.maxSize];
    }

    public SolverVariable getPivotCandidate() {
        if (this.candidate == null) {
            int i = 0;
            while (true) {
                if (i >= this.currentSize) {
                    break;
                }
                int idx = this.indexes[i];
                if (this.values[idx] < 0.0f) {
                    this.candidate = this.variables[idx];
                    break;
                }
                i++;
            }
        }
        return this.candidate;
    }

    /* access modifiers changed from: 0000 */
    public void increaseSize() {
        this.maxSize *= 2;
        this.variables = (SolverVariable[]) Arrays.copyOf(this.variables, this.maxSize);
        this.values = Arrays.copyOf(this.values, this.maxSize);
        this.indexes = Arrays.copyOf(this.indexes, this.maxSize);
    }

    public final int size() {
        return this.currentSize;
    }

    public final SolverVariable getVariable(int index) {
        return this.variables[this.indexes[index]];
    }

    public final float getVariableValue(int index) {
        return this.values[this.indexes[index]];
    }

    public final void updateArray(ArrayBackedVariables target, float amount) {
        if (amount != 0.0f) {
            for (int i = 0; i < this.currentSize; i++) {
                int idx = this.indexes[i];
                target.add(this.variables[idx], this.values[idx] * amount);
            }
        }
    }

    public void setVariable(int index, float value) {
        int idx = this.indexes[index];
        this.values[idx] = value;
        if (value < 0.0f) {
            this.candidate = this.variables[idx];
        }
    }

    public final float get(SolverVariable v) {
        if (this.currentSize < 16) {
            for (int i = 0; i < this.currentSize; i++) {
                int idx = this.indexes[i];
                if (this.variables[idx] == v) {
                    return this.values[idx];
                }
            }
        } else {
            int start = 0;
            int end = this.currentSize - 1;
            while (start <= end) {
                int index = start + ((end - start) / 2);
                int idx2 = this.indexes[index];
                SolverVariable current = this.variables[idx2];
                if (current == v) {
                    return this.values[idx2];
                }
                if (current.id < v.id) {
                    start = index + 1;
                } else {
                    end = index - 1;
                }
            }
        }
        return 0.0f;
    }

    public void put(SolverVariable variable, float value) {
        if (value == 0.0f) {
            remove(variable);
            return;
        }
        while (true) {
            int firstEmptyIndex = -1;
            for (int i = 0; i < this.currentWriteSize; i++) {
                if (this.variables[i] == variable) {
                    this.values[i] = value;
                    if (value < 0.0f) {
                        this.candidate = variable;
                        return;
                    }
                    return;
                }
                if (firstEmptyIndex == -1 && this.variables[i] == null) {
                    firstEmptyIndex = i;
                }
            }
            if (firstEmptyIndex == -1 && this.currentWriteSize < this.maxSize) {
                firstEmptyIndex = this.currentWriteSize;
            }
            if (firstEmptyIndex != -1) {
                this.variables[firstEmptyIndex] = variable;
                this.values[firstEmptyIndex] = value;
                boolean inserted = false;
                int j = 0;
                while (true) {
                    if (j >= this.currentSize) {
                        break;
                    }
                    if (this.variables[this.indexes[j]].id > variable.id) {
                        System.arraycopy(this.indexes, j, this.indexes, j + 1, this.currentSize - j);
                        this.indexes[j] = firstEmptyIndex;
                        inserted = true;
                        break;
                    }
                    j++;
                }
                if (!inserted) {
                    this.indexes[this.currentSize] = firstEmptyIndex;
                }
                this.currentSize++;
                if (firstEmptyIndex + 1 > this.currentWriteSize) {
                    this.currentWriteSize = firstEmptyIndex + 1;
                }
                if (value < 0.0f) {
                    this.candidate = variable;
                    return;
                }
                return;
            }
            increaseSize();
        }
    }

    public void add(SolverVariable variable, float value) {
        if (value != 0.0f) {
            while (true) {
                int firstEmptyIndex = -1;
                for (int i = 0; i < this.currentWriteSize; i++) {
                    if (this.variables[i] == variable) {
                        float[] fArr = this.values;
                        fArr[i] = fArr[i] + value;
                        if (value < 0.0f) {
                            this.candidate = variable;
                        }
                        if (this.values[i] == 0.0f) {
                            remove(variable);
                            return;
                        }
                        return;
                    }
                    if (firstEmptyIndex == -1 && this.variables[i] == null) {
                        firstEmptyIndex = i;
                    }
                }
                if (firstEmptyIndex == -1 && this.currentWriteSize < this.maxSize) {
                    firstEmptyIndex = this.currentWriteSize;
                }
                if (firstEmptyIndex != -1) {
                    this.variables[firstEmptyIndex] = variable;
                    this.values[firstEmptyIndex] = value;
                    boolean inserted = false;
                    int j = 0;
                    while (true) {
                        if (j >= this.currentSize) {
                            break;
                        }
                        if (this.variables[this.indexes[j]].id > variable.id) {
                            System.arraycopy(this.indexes, j, this.indexes, j + 1, this.currentSize - j);
                            this.indexes[j] = firstEmptyIndex;
                            inserted = true;
                            break;
                        }
                        j++;
                    }
                    if (!inserted) {
                        this.indexes[this.currentSize] = firstEmptyIndex;
                    }
                    this.currentSize++;
                    if (firstEmptyIndex + 1 > this.currentWriteSize) {
                        this.currentWriteSize = firstEmptyIndex + 1;
                    }
                    if (value < 0.0f) {
                        this.candidate = variable;
                        return;
                    }
                    return;
                }
                increaseSize();
            }
        }
    }

    public void clear() {
        int length = this.variables.length;
        for (int i = 0; i < length; i++) {
            this.variables[i] = null;
        }
        this.currentSize = 0;
        this.currentWriteSize = 0;
    }

    public boolean containsKey(SolverVariable variable) {
        if (this.currentSize < 8) {
            for (int i = 0; i < this.currentSize; i++) {
                if (this.variables[this.indexes[i]] == variable) {
                    return true;
                }
            }
        } else {
            int start = 0;
            int end = this.currentSize - 1;
            while (start <= end) {
                int index = start + ((end - start) / 2);
                SolverVariable current = this.variables[this.indexes[index]];
                if (current == variable) {
                    return true;
                }
                if (current.id < variable.id) {
                    start = index + 1;
                } else {
                    end = index - 1;
                }
            }
        }
        return false;
    }

    public float remove(SolverVariable variable) {
        if (this.candidate == variable) {
            this.candidate = null;
        }
        for (int i = 0; i < this.currentWriteSize; i++) {
            int idx = this.indexes[i];
            if (this.variables[idx] == variable) {
                float amount = this.values[idx];
                this.variables[idx] = null;
                System.arraycopy(this.indexes, i + 1, this.indexes, i, (this.currentWriteSize - i) - 1);
                this.currentSize--;
                return amount;
            }
        }
        return 0.0f;
    }

    public int sizeInBytes() {
        return 0 + (this.maxSize * 4) + (this.maxSize * 4) + (this.maxSize * 4) + 16;
    }

    public void display() {
        int count = size();
        System.out.print("{ ");
        for (int i = 0; i < count; i++) {
            System.out.print(getVariable(i) + " = " + getVariableValue(i) + " ");
        }
        System.out.println(" }");
    }

    private String getInternalArrays() {
        int count = size();
        String str = "" + "idx { ";
        for (int i = 0; i < count; i++) {
            str = str + this.indexes[i] + " ";
        }
        String str2 = (str + "}\n") + "obj { ";
        for (int i2 = 0; i2 < count; i2++) {
            str2 = str2 + this.variables[i2] + ":" + this.values[i2] + " ";
        }
        return str2 + "}\n";
    }

    public void displayInternalArrays() {
        int count = size();
        System.out.print("idx { ");
        for (int i = 0; i < count; i++) {
            System.out.print(this.indexes[i] + " ");
        }
        System.out.println("}");
        System.out.print("obj { ");
        for (int i2 = 0; i2 < count; i2++) {
            System.out.print(this.variables[i2] + ":" + this.values[i2] + " ");
        }
        System.out.println("}");
    }

    public void updateFromRow(ArrayRow arrayRow, ArrayRow definition) {
    }

    public SolverVariable pickPivotCandidate() {
        return null;
    }

    public void updateFromSystem(ArrayRow goal, ArrayRow[] mRows) {
    }

    public void divideByAmount(float amount) {
    }

    public void updateClientEquations(ArrayRow arrayRow) {
    }

    public boolean hasAtLeastOnePositiveVariable() {
        return false;
    }

    public void invert() {
    }
}
