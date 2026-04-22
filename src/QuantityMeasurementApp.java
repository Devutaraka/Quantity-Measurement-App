public class QuantityMeasurementApp {

    // 🔹 LENGTH ENUM
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double baseValue) {
            return baseValue / factor;
        }

        public double convert(double value, LengthUnit target) {
            double base = this.toBase(value);
            return target.fromBase(base);
        }
    }

    // 🔹 WEIGHT ENUM (NEW)
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double baseValue) {
            return baseValue / factor;
        }

        public double convert(double value, WeightUnit target) {
            double base = this.toBase(value);
            return target.fromBase(base);
        }
    }

    // 🔹 LENGTH CLASS
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 0.0001;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        public QuantityLength convertTo(LengthUnit target) {
            return new QuantityLength(unit.convert(value, target), target);
        }

        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {
            double v1 = q1.unit.convert(q1.value, target);
            double v2 = q2.unit.convert(q2.value, target);
            return new QuantityLength(v1 + v2, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(unit.toBase(value) - other.unit.toBase(other.value)) < EPSILON;
        }

        @Override
        public String toString() {
            return "Length(" + value + ", " + unit + ")";
        }
    }

    // 🔹 WEIGHT CLASS (NEW)
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;
        private static final double EPSILON = 0.0001;

        public QuantityWeight(double value, WeightUnit unit) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            return new QuantityWeight(unit.convert(value, target), target);
        }

        public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2, WeightUnit target) {
            double v1 = q1.unit.convert(q1.value, target);
            double v2 = q2.unit.convert(q2.value, target);
            return new QuantityWeight(v1 + v2, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;
            return Math.abs(unit.toBase(value) - other.unit.toBase(other.value)) < EPSILON;
        }

        @Override
        public String toString() {
            return "Weight(" + value + ", " + unit + ")";
        }
    }

    // 🔹 MAIN
    public static void main(String[] args) {

        // LENGTH
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(QuantityLength.add(l1, l2, LengthUnit.INCHES));

        // WEIGHT
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println(w1.equals(w2)); // true

        System.out.println(
                QuantityWeight.add(w1, w2, WeightUnit.KILOGRAM)
        );

    }
}
