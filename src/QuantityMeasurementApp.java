public class QuantityMeasurementApp {

    // 🔹 ENUM (NOW HANDLES CONVERSION LOGIC)
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        // Convert any value to base (feet)
        public double toBase(double value) {
            return value * factor;
        }

        // Convert base (feet) to target unit
        public double fromBase(double baseValue) {
            return baseValue / factor;
        }

        // 🔥 UC8: Direct conversion method (NEW DESIGN)
        public double convert(double value, LengthUnit target) {
            double base = this.toBase(value);
            return target.fromBase(base);
        }
    }

    // 🔹 CLASS
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 0.0001;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            this.value = value;
            this.unit = unit;
        }

        // 🔥 UC8: Conversion using ENUM method
        public QuantityLength convertTo(LengthUnit target) {
            double result = unit.convert(value, target);
            return new QuantityLength(result, target);
        }

        // 🔥 UC8: Addition using ENUM conversion
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {

            if (q1 == null || q2 == null || target == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double v1 = q1.unit.convert(q1.value, target);
            double v2 = q2.unit.convert(q2.value, target);

            return new QuantityLength(v1 + v2, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double base1 = unit.toBase(value);
            double base2 = other.unit.toBase(other.value);

            return Math.abs(base1 - base2) < EPSILON;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🔹 MAIN
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // Conversion
        System.out.println(q1.convertTo(LengthUnit.INCHES));

        // Addition using target
        System.out.println(
                QuantityLength.add(q1, q2, LengthUnit.INCHES)
        );

        System.out.println(
                QuantityLength.add(q1, q2, LengthUnit.YARDS)
        );
    }
}
