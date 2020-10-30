package android.support.v4.app;

import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.util.LogWriter;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction implements BackStackEntry, OpGenerator {
    final FragmentManagerImpl a;
    ArrayList<Op> b = new ArrayList<>();
    int c;
    int d;
    int e;
    int f;
    int g;
    int h;
    boolean i;
    boolean j = true;
    String k;
    boolean l;
    int m = -1;
    int n;
    CharSequence o;
    int p;
    CharSequence q;
    ArrayList<String> r;
    ArrayList<String> s;
    boolean t = false;
    ArrayList<Runnable> u;

    static final class Op {
        int a;
        Fragment b;
        int c;
        int d;
        int e;
        int f;

        Op() {
        }

        Op(int i, Fragment fragment) {
            this.a = i;
            this.b = fragment;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.m >= 0) {
            sb.append(" #");
            sb.append(this.m);
        }
        if (this.k != null) {
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.k);
        }
        sb.append("}");
        return sb.toString();
    }

    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        a(str, printWriter, true);
    }

    public void a(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.k);
            printWriter.print(" mIndex=");
            printWriter.print(this.m);
            printWriter.print(" mCommitted=");
            printWriter.println(this.l);
            if (this.g != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.g));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.h));
            }
            if (!(this.c == 0 && this.d == 0)) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.c));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.d));
            }
            if (!(this.e == 0 && this.f == 0)) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.e));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.f));
            }
            if (!(this.n == 0 && this.o == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.n));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.o);
            }
            if (!(this.p == 0 && this.q == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.p));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.q);
            }
        }
        if (!this.b.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("    ");
            sb.toString();
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                Op op = (Op) this.b.get(i2);
                switch (op.a) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    case 8:
                        str2 = "SET_PRIMARY_NAV";
                        break;
                    case 9:
                        str2 = "UNSET_PRIMARY_NAV";
                        break;
                    default:
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("cmd=");
                        sb2.append(op.a);
                        str2 = sb2.toString();
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(UtilsCuentas.SEPARAOR2);
                printWriter.println(op.b);
                if (z) {
                    if (!(op.c == 0 && op.d == 0)) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(op.c));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(op.d));
                    }
                    if (op.e != 0 || op.f != 0) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(op.e));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(op.f));
                    }
                }
            }
        }
    }

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        this.a = fragmentManagerImpl;
    }

    public int getId() {
        return this.m;
    }

    public int getBreadCrumbTitleRes() {
        return this.n;
    }

    public int getBreadCrumbShortTitleRes() {
        return this.p;
    }

    public CharSequence getBreadCrumbTitle() {
        if (this.n != 0) {
            return this.a.m.c().getText(this.n);
        }
        return this.o;
    }

    public CharSequence getBreadCrumbShortTitle() {
        if (this.p != 0) {
            return this.a.m.c().getText(this.p);
        }
        return this.q;
    }

    /* access modifiers changed from: 0000 */
    public void a(Op op) {
        this.b.add(op);
        op.c = this.c;
        op.d = this.d;
        op.e = this.e;
        op.f = this.f;
    }

    public FragmentTransaction add(Fragment fragment, String str) {
        a(0, fragment, str, 1);
        return this;
    }

    public FragmentTransaction add(int i2, Fragment fragment) {
        a(i2, fragment, (String) null, 1);
        return this;
    }

    public FragmentTransaction add(int i2, Fragment fragment, String str) {
        a(i2, fragment, str, 1);
        return this;
    }

    private void a(int i2, Fragment fragment, String str, int i3) {
        Class cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(cls.getCanonicalName());
            sb.append(" must be a public static class to be  properly recreated from");
            sb.append(" instance state.");
            throw new IllegalStateException(sb.toString());
        }
        fragment.A = this.a;
        if (str != null) {
            if (fragment.I == null || str.equals(fragment.I)) {
                fragment.I = str;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Can't change tag of fragment ");
                sb2.append(fragment);
                sb2.append(": was ");
                sb2.append(fragment.I);
                sb2.append(" now ");
                sb2.append(str);
                throw new IllegalStateException(sb2.toString());
            }
        }
        if (i2 != 0) {
            if (i2 == -1) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Can't add fragment ");
                sb3.append(fragment);
                sb3.append(" with tag ");
                sb3.append(str);
                sb3.append(" to container view with no id");
                throw new IllegalArgumentException(sb3.toString());
            } else if (fragment.G == 0 || fragment.G == i2) {
                fragment.G = i2;
                fragment.H = i2;
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Can't change container ID of fragment ");
                sb4.append(fragment);
                sb4.append(": was ");
                sb4.append(fragment.G);
                sb4.append(" now ");
                sb4.append(i2);
                throw new IllegalStateException(sb4.toString());
            }
        }
        a(new Op(i3, fragment));
    }

    public FragmentTransaction replace(int i2, Fragment fragment) {
        return replace(i2, fragment, null);
    }

    public FragmentTransaction replace(int i2, Fragment fragment, String str) {
        if (i2 == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        a(i2, fragment, str, 2);
        return this;
    }

    public FragmentTransaction remove(Fragment fragment) {
        a(new Op(3, fragment));
        return this;
    }

    public FragmentTransaction hide(Fragment fragment) {
        a(new Op(4, fragment));
        return this;
    }

    public FragmentTransaction show(Fragment fragment) {
        a(new Op(5, fragment));
        return this;
    }

    public FragmentTransaction detach(Fragment fragment) {
        a(new Op(6, fragment));
        return this;
    }

    public FragmentTransaction attach(Fragment fragment) {
        a(new Op(7, fragment));
        return this;
    }

    public FragmentTransaction setPrimaryNavigationFragment(Fragment fragment) {
        a(new Op(8, fragment));
        return this;
    }

    public FragmentTransaction setCustomAnimations(int i2, int i3) {
        return setCustomAnimations(i2, i3, 0, 0);
    }

    public FragmentTransaction setCustomAnimations(int i2, int i3, int i4, int i5) {
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        return this;
    }

    public FragmentTransaction setTransition(int i2) {
        this.g = i2;
        return this;
    }

    public FragmentTransaction addSharedElement(View view, String str) {
        if (FragmentTransition.a()) {
            String transitionName = ViewCompat.getTransitionName(view);
            if (transitionName == null) {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
            if (this.r == null) {
                this.r = new ArrayList<>();
                this.s = new ArrayList<>();
            } else if (this.s.contains(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append("A shared element with the target name '");
                sb.append(str);
                sb.append("' has already been added to the transaction.");
                throw new IllegalArgumentException(sb.toString());
            } else if (this.r.contains(transitionName)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("A shared element with the source name '");
                sb2.append(transitionName);
                sb2.append(" has already been added to the transaction.");
                throw new IllegalArgumentException(sb2.toString());
            }
            this.r.add(transitionName);
            this.s.add(str);
        }
        return this;
    }

    public FragmentTransaction setTransitionStyle(int i2) {
        this.h = i2;
        return this;
    }

    public FragmentTransaction addToBackStack(String str) {
        if (!this.j) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.i = true;
        this.k = str;
        return this;
    }

    public boolean isAddToBackStackAllowed() {
        return this.j;
    }

    public FragmentTransaction disallowAddToBackStack() {
        if (this.i) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.j = false;
        return this;
    }

    public FragmentTransaction setBreadCrumbTitle(int i2) {
        this.n = i2;
        this.o = null;
        return this;
    }

    public FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        this.n = 0;
        this.o = charSequence;
        return this;
    }

    public FragmentTransaction setBreadCrumbShortTitle(int i2) {
        this.p = i2;
        this.q = null;
        return this;
    }

    public FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        this.p = 0;
        this.q = charSequence;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.i) {
            if (FragmentManagerImpl.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("Bump nesting in ");
                sb.append(this);
                sb.append(" by ");
                sb.append(i2);
                Log.v("FragmentManager", sb.toString());
            }
            int size = this.b.size();
            for (int i3 = 0; i3 < size; i3++) {
                Op op = (Op) this.b.get(i3);
                if (op.b != null) {
                    op.b.z += i2;
                    if (FragmentManagerImpl.a) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Bump nesting of ");
                        sb2.append(op.b);
                        sb2.append(" to ");
                        sb2.append(op.b.z);
                        Log.v("FragmentManager", sb2.toString());
                    }
                }
            }
        }
    }

    public FragmentTransaction runOnCommit(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("runnable cannot be null");
        }
        disallowAddToBackStack();
        if (this.u == null) {
            this.u = new ArrayList<>();
        }
        this.u.add(runnable);
        return this;
    }

    public void a() {
        if (this.u != null) {
            int size = this.u.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((Runnable) this.u.get(i2)).run();
            }
            this.u = null;
        }
    }

    public int commit() {
        return a(false);
    }

    public int commitAllowingStateLoss() {
        return a(true);
    }

    public void commitNow() {
        disallowAddToBackStack();
        this.a.b((OpGenerator) this, false);
    }

    public void commitNowAllowingStateLoss() {
        disallowAddToBackStack();
        this.a.b((OpGenerator) this, true);
    }

    public FragmentTransaction setReorderingAllowed(boolean z) {
        this.t = z;
        return this;
    }

    public FragmentTransaction setAllowOptimization(boolean z) {
        return setReorderingAllowed(z);
    }

    /* access modifiers changed from: 0000 */
    public int a(boolean z) {
        if (this.l) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.a) {
            StringBuilder sb = new StringBuilder();
            sb.append("Commit: ");
            sb.append(this);
            Log.v("FragmentManager", sb.toString());
            PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
            a("  ", (FileDescriptor) null, printWriter, (String[]) null);
            printWriter.close();
        }
        this.l = true;
        if (this.i) {
            this.m = this.a.a(this);
        } else {
            this.m = -1;
        }
        this.a.a((OpGenerator) this, z);
        return this.m;
    }

    public boolean a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (FragmentManagerImpl.a) {
            StringBuilder sb = new StringBuilder();
            sb.append("Run: ");
            sb.append(this);
            Log.v("FragmentManager", sb.toString());
        }
        arrayList.add(this);
        arrayList2.add(Boolean.valueOf(false));
        if (this.i) {
            this.a.b(this);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(int i2) {
        int size = this.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Op op = (Op) this.b.get(i3);
            int i4 = op.b != null ? op.b.H : 0;
            if (i4 != 0 && i4 == i2) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ArrayList<BackStackRecord> arrayList, int i2, int i3) {
        if (i3 == i2) {
            return false;
        }
        int size = this.b.size();
        int i4 = -1;
        for (int i5 = 0; i5 < size; i5++) {
            Op op = (Op) this.b.get(i5);
            int i6 = op.b != null ? op.b.H : 0;
            if (!(i6 == 0 || i6 == i4)) {
                for (int i7 = i2; i7 < i3; i7++) {
                    BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i7);
                    int size2 = backStackRecord.b.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        Op op2 = (Op) backStackRecord.b.get(i8);
                        if ((op2.b != null ? op2.b.H : 0) == i6) {
                            return true;
                        }
                    }
                }
                i4 = i6;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Op op = (Op) this.b.get(i2);
            Fragment fragment = op.b;
            if (fragment != null) {
                fragment.a(this.g, this.h);
            }
            int i3 = op.a;
            if (i3 != 1) {
                switch (i3) {
                    case 3:
                        fragment.a(op.d);
                        this.a.h(fragment);
                        break;
                    case 4:
                        fragment.a(op.d);
                        this.a.i(fragment);
                        break;
                    case 5:
                        fragment.a(op.c);
                        this.a.j(fragment);
                        break;
                    case 6:
                        fragment.a(op.d);
                        this.a.k(fragment);
                        break;
                    case 7:
                        fragment.a(op.c);
                        this.a.l(fragment);
                        break;
                    case 8:
                        this.a.o(fragment);
                        break;
                    case 9:
                        this.a.o(null);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unknown cmd: ");
                        sb.append(op.a);
                        throw new IllegalArgumentException(sb.toString());
                }
            } else {
                fragment.a(op.c);
                this.a.a(fragment, false);
            }
            if (!(this.t || op.a == 1 || fragment == null)) {
                this.a.e(fragment);
            }
        }
        if (!this.t) {
            this.a.a(this.a.l, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            Op op = (Op) this.b.get(size);
            Fragment fragment = op.b;
            if (fragment != null) {
                fragment.a(FragmentManagerImpl.c(this.g), this.h);
            }
            int i2 = op.a;
            if (i2 != 1) {
                switch (i2) {
                    case 3:
                        fragment.a(op.e);
                        this.a.a(fragment, false);
                        break;
                    case 4:
                        fragment.a(op.e);
                        this.a.j(fragment);
                        break;
                    case 5:
                        fragment.a(op.f);
                        this.a.i(fragment);
                        break;
                    case 6:
                        fragment.a(op.e);
                        this.a.l(fragment);
                        break;
                    case 7:
                        fragment.a(op.f);
                        this.a.k(fragment);
                        break;
                    case 8:
                        this.a.o(null);
                        break;
                    case 9:
                        this.a.o(fragment);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unknown cmd: ");
                        sb.append(op.a);
                        throw new IllegalArgumentException(sb.toString());
                }
            } else {
                fragment.a(op.f);
                this.a.h(fragment);
            }
            if (!(this.t || op.a == 3 || fragment == null)) {
                this.a.e(fragment);
            }
        }
        if (!this.t && z) {
            this.a.a(this.a.l, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public Fragment a(ArrayList<Fragment> arrayList, Fragment fragment) {
        Fragment fragment2 = fragment;
        int i2 = 0;
        while (i2 < this.b.size()) {
            Op op = (Op) this.b.get(i2);
            switch (op.a) {
                case 1:
                case 7:
                    arrayList.add(op.b);
                    break;
                case 2:
                    Fragment fragment3 = op.b;
                    int i3 = fragment3.H;
                    Fragment fragment4 = fragment2;
                    int i4 = i2;
                    boolean z = false;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        Fragment fragment5 = (Fragment) arrayList.get(size);
                        if (fragment5.H == i3) {
                            if (fragment5 == fragment3) {
                                z = true;
                            } else {
                                if (fragment5 == fragment4) {
                                    this.b.add(i4, new Op(9, fragment5));
                                    i4++;
                                    fragment4 = null;
                                }
                                Op op2 = new Op(3, fragment5);
                                op2.c = op.c;
                                op2.e = op.e;
                                op2.d = op.d;
                                op2.f = op.f;
                                this.b.add(i4, op2);
                                arrayList.remove(fragment5);
                                i4++;
                            }
                        }
                    }
                    if (z) {
                        this.b.remove(i4);
                        i4--;
                    } else {
                        op.a = 1;
                        arrayList.add(fragment3);
                    }
                    i2 = i4;
                    fragment2 = fragment4;
                    break;
                case 3:
                case 6:
                    arrayList.remove(op.b);
                    if (op.b != fragment2) {
                        break;
                    } else {
                        this.b.add(i2, new Op(9, op.b));
                        i2++;
                        fragment2 = null;
                        break;
                    }
                case 8:
                    this.b.add(i2, new Op(9, fragment2));
                    i2++;
                    fragment2 = op.b;
                    break;
            }
            i2++;
        }
        return fragment2;
    }

    /* access modifiers changed from: 0000 */
    public Fragment b(ArrayList<Fragment> arrayList, Fragment fragment) {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            Op op = (Op) this.b.get(i2);
            int i3 = op.a;
            if (i3 != 1) {
                if (i3 != 3) {
                    switch (i3) {
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            fragment = null;
                            break;
                        case 9:
                            fragment = op.b;
                            break;
                    }
                }
                arrayList.add(op.b);
            }
            arrayList.remove(op.b);
        }
        return fragment;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            if (b((Op) this.b.get(i2))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void a(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            Op op = (Op) this.b.get(i2);
            if (b(op)) {
                op.b.a(onStartEnterTransitionListener);
            }
        }
    }

    private static boolean b(Op op) {
        Fragment fragment = op.b;
        return fragment != null && fragment.t && fragment.R != null && !fragment.K && !fragment.J && fragment.w();
    }

    public String getName() {
        return this.k;
    }

    public boolean isEmpty() {
        return this.b.isEmpty();
    }
}
