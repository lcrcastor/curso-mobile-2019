package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;

public class LinkedVariables {
    private static final boolean DEBUG = false;
    public static int sCreation = 0;
    private SolverVariable candidate = null;
    int currentSize = 0;
    float epsilon = 0.001f;
    private Link head = null;
    private final Cache mCache;
    private final ArrayRow mRow;

    static class Link {
        Link next;
        float value;
        SolverVariable variable;

        public Link() {
            LinkedVariables.sCreation++;
        }

        public String toString() {
            return "" + this.value + " " + this.variable;
        }
    }

    public LinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
    }

    public String toString() {
        String result = "";
        for (Link current = this.head; current != null; current = current.next) {
            result = result + " -> (" + current + ")";
        }
        return result;
    }

    public boolean hasAtLeastOnePositiveVariable() {
        for (Link current = this.head; current != null; current = current.next) {
            if (current.value > 0.0f) {
                return true;
            }
        }
        return false;
    }

    public void invert() {
        Link current = this.head;
        while (current != null) {
            current.value *= -1.0f;
            current = current.next;
        }
    }

    public void divideByAmount(float amount) {
        Link current = this.head;
        while (current != null) {
            current.value /= amount;
            current = current.next;
        }
    }

    public void updateClientEquations(ArrayRow row) {
        for (Link current = this.head; current != null; current = current.next) {
            current.variable.addClientEquation(row);
        }
    }

    public SolverVariable pickPivotCandidate() {
        SolverVariable restrictedCandidate = null;
        SolverVariable unrestrictedCandidate = null;
        for (Link current = this.head; current != null; current = current.next) {
            float amount = current.value;
            if (amount < 0.0f) {
                if (amount > (-this.epsilon)) {
                    current.value = 0.0f;
                    amount = 0.0f;
                }
            } else if (amount < this.epsilon) {
                current.value = 0.0f;
                amount = 0.0f;
            }
            if (amount != 0.0f) {
                if (current.variable.mType == Type.UNRESTRICTED) {
                    if (amount < 0.0f) {
                        return current.variable;
                    }
                    if (unrestrictedCandidate == null) {
                        unrestrictedCandidate = current.variable;
                    }
                } else if (amount < 0.0f && restrictedCandidate == null) {
                    restrictedCandidate = current.variable;
                }
            }
        }
        if (unrestrictedCandidate == null) {
            return restrictedCandidate;
        }
        return unrestrictedCandidate;
    }

    public void updateFromRow(ArrayRow self, ArrayRow definition) {
        Link previous = null;
        Link newVariables = (Link) this.mCache.linkedVariablesPool.acquire();
        if (newVariables == null) {
            newVariables = new Link();
        }
        newVariables.next = null;
        Link lastOfNewVariables = newVariables;
        for (Link current = this.head; current != null; current = current.next) {
            if (current.variable == definition.variable) {
                float amount = current.value;
                if (!definition.isSimpleDefinition) {
                    for (Link definitionCurrent = ((LinkedVariables) definition.variables).head; definitionCurrent != null; definitionCurrent = definitionCurrent.next) {
                        Link temp = (Link) this.mCache.linkedVariablesPool.acquire();
                        if (temp == null) {
                            temp = new Link();
                        }
                        temp.variable = definitionCurrent.variable;
                        temp.value = definitionCurrent.value * amount;
                        temp.next = null;
                        lastOfNewVariables.next = temp;
                        lastOfNewVariables = temp;
                    }
                }
                self.constantValue += definition.constantValue * amount;
                definition.variable.removeClientEquation(self);
                if (previous == null) {
                    this.head = current.next;
                } else {
                    previous.next = current.next;
                }
                this.mCache.linkedVariablesPool.release(current);
                this.currentSize--;
            } else {
                previous = current;
            }
        }
        Link current2 = newVariables.next;
        while (current2 != null) {
            add(current2.variable, current2.value);
            Link previous2 = current2;
            current2 = current2.next;
            this.mCache.linkedVariablesPool.release(previous2);
        }
        this.mCache.linkedVariablesPool.release(newVariables);
    }

    public void updateFromSystem(ArrayRow self, ArrayRow[] rows) {
        Link previous = null;
        Link newVariables = (Link) this.mCache.linkedVariablesPool.acquire();
        if (newVariables == null) {
            newVariables = new Link();
        }
        newVariables.next = null;
        Link lastOfNewVariables = newVariables;
        for (Link current = this.head; current != null; current = current.next) {
            int definitionIndex = current.variable.definitionId;
            if (definitionIndex != -1) {
                float amount = current.value;
                ArrayRow definition = rows[definitionIndex];
                if (!definition.isSimpleDefinition) {
                    for (Link definitionCurrent = ((LinkedVariables) definition.variables).head; definitionCurrent != null; definitionCurrent = definitionCurrent.next) {
                        Link temp = (Link) this.mCache.linkedVariablesPool.acquire();
                        if (temp == null) {
                            temp = new Link();
                        }
                        temp.variable = definitionCurrent.variable;
                        temp.value = definitionCurrent.value * amount;
                        temp.next = null;
                        lastOfNewVariables.next = temp;
                        lastOfNewVariables = temp;
                    }
                }
                self.constantValue += definition.constantValue * amount;
                definition.variable.removeClientEquation(self);
                if (previous == null) {
                    this.head = current.next;
                } else {
                    previous.next = current.next;
                }
                this.mCache.linkedVariablesPool.release(current);
                this.currentSize--;
            } else {
                previous = current;
            }
        }
        Link current2 = newVariables.next;
        while (current2 != null) {
            add(current2.variable, current2.value);
            Link previous2 = current2;
            current2 = current2.next;
            this.mCache.linkedVariablesPool.release(previous2);
        }
        this.mCache.linkedVariablesPool.release(newVariables);
    }

    public SolverVariable getPivotCandidate() {
        if (this.candidate == null) {
            for (Link current = this.head; current != null; current = current.next) {
                if (current.value < 0.0f && (this.candidate == null || current.variable.definitionId < this.candidate.definitionId)) {
                    this.candidate = current.variable;
                }
            }
        }
        return this.candidate;
    }

    public final int size() {
        return this.currentSize;
    }

    public final SolverVariable getVariable(int index) {
        Link current = this.head;
        for (int count = 0; count != index; count++) {
            current = current.next;
        }
        if (current != null) {
            return current.variable;
        }
        return null;
    }

    public final float getVariableValue(int index) {
        Link current = this.head;
        for (int count = 0; count != index; count++) {
            current = current.next;
        }
        if (current != null) {
            return current.value;
        }
        return 0.0f;
    }

    public final void updateArray(LinkedVariables target, float amount) {
        if (amount != 0.0f) {
            for (Link current = this.head; current != null; current = current.next) {
                target.put(current.variable, target.get(current.variable) + (current.value * amount));
            }
        }
    }

    public final void setVariable(int index, float value) {
        Link current = this.head;
        for (int count = 0; count != index; count++) {
            current = current.next;
        }
        current.value = value;
    }

    public final float get(SolverVariable v) {
        for (Link current = this.head; current != null; current = current.next) {
            if (current.variable == v) {
                return current.value;
            }
        }
        return 0.0f;
    }

    public final void put(SolverVariable variable, float value) {
        if (value == 0.0f) {
            remove(variable);
            return;
        }
        Link previous = null;
        for (Link current = this.head; current != null; current = current.next) {
            if (current.variable == variable) {
                current.value = value;
                return;
            }
            if (current.variable.id < variable.id) {
                previous = current;
            }
        }
        Link current2 = (Link) this.mCache.linkedVariablesPool.acquire();
        if (current2 == null) {
            current2 = new Link();
        }
        current2.value = value;
        current2.variable = variable;
        current2.next = null;
        if (previous != null) {
            current2.next = previous.next;
            previous.next = current2;
        } else {
            current2.next = this.head;
            this.head = current2;
        }
        if (this.head == null) {
            this.head = current2;
        }
        this.currentSize++;
    }

    public final void add(SolverVariable variable, float value) {
        if (value == 0.0f) {
            remove(variable);
            return;
        }
        Link current = this.head;
        Link previous = null;
        while (current != null) {
            if (current.variable == variable) {
                current.value += value;
                if (current.value == 0.0f) {
                    if (current == this.head) {
                        this.head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                    current.variable.removeClientEquation(this.mRow);
                    this.mCache.linkedVariablesPool.release(current);
                    this.currentSize--;
                    return;
                }
                return;
            }
            if (current.variable.id < variable.id) {
                previous = current;
            }
            current = current.next;
        }
        Link current2 = (Link) this.mCache.linkedVariablesPool.acquire();
        if (current2 == null) {
            current2 = new Link();
        }
        current2.value = value;
        current2.variable = variable;
        current2.next = null;
        if (previous != null) {
            current2.next = previous.next;
            previous.next = current2;
        } else {
            current2.next = this.head;
            this.head = current2;
        }
        if (this.head == null) {
            this.head = current2;
        }
        this.currentSize++;
    }

    public final void clear() {
        Link current = this.head;
        while (current != null) {
            Link previous = current;
            current = current.next;
            this.mCache.linkedVariablesPool.release(previous);
        }
        this.head = null;
        this.currentSize = 0;
    }

    public final boolean containsKey(SolverVariable variable) {
        for (Link current = this.head; current != null; current = current.next) {
            if (current.variable == variable) {
                return true;
            }
        }
        return false;
    }

    public final float remove(SolverVariable variable) {
        if (this.candidate == variable) {
            this.candidate = null;
        }
        Link previous = null;
        for (Link current = this.head; current != null; current = current.next) {
            if (current.variable == variable) {
                float value = current.value;
                if (current == this.head) {
                    this.head = current.next;
                } else {
                    previous.next = current.next;
                }
                current.variable.removeClientEquation(this.mRow);
                this.mCache.linkedVariablesPool.release(current);
                this.currentSize--;
                return value;
            }
            previous = current;
        }
        return 0.0f;
    }

    public int sizeInBytes() {
        return 0 + 16;
    }

    public void display() {
        int count = size();
        System.out.print("{ ");
        for (int i = 0; i < count; i++) {
            SolverVariable v = getVariable(i);
            if (v != null) {
                System.out.print(v + " = " + getVariableValue(i) + " ");
            }
        }
        System.out.println(" }");
    }
}
