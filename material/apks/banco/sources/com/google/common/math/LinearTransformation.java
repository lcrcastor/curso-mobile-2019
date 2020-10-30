package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

@GwtIncompatible
@Beta
public abstract class LinearTransformation {

    static final class RegularLinearTransformation extends LinearTransformation {
        final double a;
        final double b;
        @LazyInit
        LinearTransformation c;

        public boolean isVertical() {
            return false;
        }

        RegularLinearTransformation(double d, double d2) {
            this.a = d;
            this.b = d2;
            this.c = null;
        }

        RegularLinearTransformation(double d, double d2, LinearTransformation linearTransformation) {
            this.a = d;
            this.b = d2;
            this.c = linearTransformation;
        }

        public boolean isHorizontal() {
            return this.a == 0.0d;
        }

        public double slope() {
            return this.a;
        }

        public double transform(double d) {
            return (d * this.a) + this.b;
        }

        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.c;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation a2 = a();
            this.c = a2;
            return a2;
        }

        public String toString() {
            return String.format("y = %g * x + %g", new Object[]{Double.valueOf(this.a), Double.valueOf(this.b)});
        }

        private LinearTransformation a() {
            if (this.a == 0.0d) {
                return new VerticalLinearTransformation(this.b, this);
            }
            RegularLinearTransformation regularLinearTransformation = new RegularLinearTransformation(1.0d / this.a, (this.b * -1.0d) / this.a, this);
            return regularLinearTransformation;
        }
    }

    static final class VerticalLinearTransformation extends LinearTransformation {
        final double a;
        @LazyInit
        LinearTransformation b;

        public boolean isHorizontal() {
            return false;
        }

        public boolean isVertical() {
            return true;
        }

        VerticalLinearTransformation(double d) {
            this.a = d;
            this.b = null;
        }

        VerticalLinearTransformation(double d, LinearTransformation linearTransformation) {
            this.a = d;
            this.b = linearTransformation;
        }

        public double slope() {
            throw new IllegalStateException();
        }

        public double transform(double d) {
            throw new IllegalStateException();
        }

        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.b;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation a2 = a();
            this.b = a2;
            return a2;
        }

        public String toString() {
            return String.format("x = %g", new Object[]{Double.valueOf(this.a)});
        }

        private LinearTransformation a() {
            RegularLinearTransformation regularLinearTransformation = new RegularLinearTransformation(0.0d, this.a, this);
            return regularLinearTransformation;
        }
    }

    public static final class LinearTransformationBuilder {
        private final double a;
        private final double b;

        private LinearTransformationBuilder(double d, double d2) {
            this.a = d;
            this.b = d2;
        }

        public LinearTransformation and(double d, double d2) {
            boolean z = false;
            Preconditions.checkArgument(DoubleUtils.b(d) && DoubleUtils.b(d2));
            if (d != this.a) {
                return withSlope((d2 - this.b) / (d - this.a));
            }
            if (d2 != this.b) {
                z = true;
            }
            Preconditions.checkArgument(z);
            return new VerticalLinearTransformation(this.a);
        }

        public LinearTransformation withSlope(double d) {
            Preconditions.checkArgument(!Double.isNaN(d));
            if (DoubleUtils.b(d)) {
                return new RegularLinearTransformation(d, this.b - (this.a * d));
            }
            return new VerticalLinearTransformation(this.a);
        }
    }

    static final class NaNLinearTransformation extends LinearTransformation {
        static final NaNLinearTransformation a = new NaNLinearTransformation();

        public LinearTransformation inverse() {
            return this;
        }

        public boolean isHorizontal() {
            return false;
        }

        public boolean isVertical() {
            return false;
        }

        public double slope() {
            return Double.NaN;
        }

        public String toString() {
            return "NaN";
        }

        public double transform(double d) {
            return Double.NaN;
        }

        private NaNLinearTransformation() {
        }
    }

    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double d);

    public static LinearTransformationBuilder mapping(double d, double d2) {
        Preconditions.checkArgument(DoubleUtils.b(d) && DoubleUtils.b(d2));
        LinearTransformationBuilder linearTransformationBuilder = new LinearTransformationBuilder(d, d2);
        return linearTransformationBuilder;
    }

    public static LinearTransformation vertical(double d) {
        Preconditions.checkArgument(DoubleUtils.b(d));
        return new VerticalLinearTransformation(d);
    }

    public static LinearTransformation horizontal(double d) {
        Preconditions.checkArgument(DoubleUtils.b(d));
        return new RegularLinearTransformation(0.0d, d);
    }

    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.a;
    }
}
