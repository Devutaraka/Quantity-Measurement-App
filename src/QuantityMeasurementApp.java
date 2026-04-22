public class QuantityMeasurementApp {

    // 🔹 ENUM FOR ALL UNITS
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        // Convert to base unit (feet)
        public double toFeet(double value) {
            return value * toFeet;
        }

        // Convert from base unit (feet)
        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    // 🔹 GENERIC QUANTITY CLASS (UC3 + UC4)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            this.value = value;
            this.unit = unit;
        }

        // Convert to base (feet)
        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔹 UC5 INSTANCE METHOD
        public Quantity convertTo(LengthUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = this.toFeet();
            double converted = target.fromFeet(base);

            return new Quantity(converted, target);
        }

        // 🔹 EQUALITY CHECK (UC3)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double EPSILON = 0.0001;
            return Math.abs(this.toFeet() - other.toFeet()) < EPSILON;
        }

        // 🔹 toString (UC5)
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 STATIC CONVERSION API (UC5 MAIN FEATURE)
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        double base = source.toFeet(value);
        return target.fromFeet(base);
    }

    // 🔹 DEMO METHODS (GOOD FOR VIVA)
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    public static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity converted = q.convertTo(to);
        System.out.println(q + " = " + converted);
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {

        // Static conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        // Instance conversion
        Quantity q1 = new Quantity(1.0, LengthUnit.YARD);
        demonstrateLengthConversion(q1, LengthUnit.INCH);

        // Equality
        Quantity q2 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q3 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println("Are equal: " + q2.equals(q3));
    }
}

