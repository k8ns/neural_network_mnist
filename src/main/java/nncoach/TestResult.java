package nncoach;

public class TestResult {
    private int expectation;
    private int actual;
    private double[] values;

    public TestResult(int e, double[] v) {
        expectation = e;
        values = v;

        double max = Double.MIN_VALUE;
        actual = 0;
        for( int i = 0; i < values.length; i++ ) {
            if (values[i] > max) {
                max = values[i];
                actual = i;
            }
        }
    }

    public boolean isSuccessful() {
        return expectation == actual;
    }

    public int getExpectation() {
        return expectation;
    }

    public void setExpectation(int expectation) {
        this.expectation = expectation;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }
}
