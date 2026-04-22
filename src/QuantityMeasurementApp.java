public class QuantityMeasurementApp {

    // 🔹 ENUM
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

        private double toBase() {
            return unit.toBase(value);
        }

        // 🔥 UC6 ADD METHOD
        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {

            if (q1 == null || q2 == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }

            double sumBase = q1.toBase() + q2.toBase();

            // result in first operand unit
            double result = q1.unit.fromBase(sumBase);

            return new QuantityLength(result, q1.unit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toBase() - other.toBase()) < EPSILON;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🔹 MAIN
    public static void main(String[] args) {

        System.out.println(
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(2.0, LengthUnit.FEET)
                )
        );

        System.out.println(
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCHES)
                )
        );

        System.out.println(
                QuantityLength.add(
                        new QuantityLength(12.0, LengthUnit.INCHES),
                        new QuantityLength(1.0, LengthUnit.FEET)
                )
        );
    }
}
